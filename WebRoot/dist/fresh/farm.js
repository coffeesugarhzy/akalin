String.prototype.replaceAll = function(s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}
var idajax = false;
var curPage = 1;
 
function filterQuery(key, value) { 
	if(key==1&&$("#searchVal").val()==="")return ;
    $("#key").val(key); 
    $("#value").val(value);
    if (1 != key) {
        $("#searchVal").val("");
    }
    $('#dataArea').html("");
    curPage = 1;
    idajax = false;
    
    ajaxFunc();
}

function ajaxFunc() {
    var dataParams = null;
    var key = $("#key").val();  
    var value = $("#value").val();
    if (null != key && "" != key.replaceAll(" ", "") && null != value && "" != value.replaceAll(" ", "")) {
        dataParams = {
            "page" : curPage,
            "queryType" : 1,
            "key" : key,
            "value" : value
        };
    } else {
        dataParams = {
            "queryType" : 1,
            "page" : curPage,
            "key":key==7?key:null
        };
    } 
    var temp = "";
    $.ajax({
        type : 'post',
        url : '/index/farm/listfarm.do',
        data : dataParams,
        dataType : 'json',
        beforeSend : function() {
            $('#pullup').html('<center><img src="/dist/images/loading.gif"/></center>');
        },
        success : function(data) {
        	
            var len = data.content.length;
            if (0 == len) {
                idajax = false;
                document.getElementById('pullup').innerHTML = (1 == data.page) ? "<li class='none-list'>没有数据</li>" : "<li class='none-list'>已经最后一页了</li>";
            } else {
                var content = data.content;
                var weekDay = $("#weekDay").val();
                var curTime = $("#curTime").val();
                var shopId, shopName, farmId, farmName, logo, price, saleprice, saleType, suggest;
                 
                for ( var i = 0; i < len; i++) {
                    farmId = content[i].farmId; 
                    farmName = content[i].farmName;
                    logo = content[i].logo;
                    shopId = content[i].ofShopId;
                    shopName = content[i].shopName; 
                    price = content[i].price;
                     
                    if (0 == content[i].isSale && weekDay == content[i].saleDay) {
                        saleType = 0;
                        saleprice = content[i].salePrice;
                    } else if (0 == content[i].isDis && content[i].beginTime<=curTime && curTime<content[i].endTime && 0 < content[i].disNum) {
                        saleType = 1;
                        saleprice = content[i].disPrice;
                    } else {
                        saleType = -1;
                        saleprice = 0;
                    }
                     
                    suggest = (0 == content[i].cookType) ? "" : content[i].suggest;
                     
                    temp += "<li class='clearFix' ontouchend=javascript:this.style.background=''  ontouchstart=javascript:this.style.background='#f0f0f0'>";
                    temp += "<img class='fl' src='" + logo + "' width='70' height='70' alt='' onclick=javascript:window.location.href='/index/farm/farmDetail.do?farmId="+farmId+"'>";
                    temp += "<p class='name'>" + farmName + "</p>";
                    temp += "<p class='intro'>精选自：" + shopName + "</p>";
 
                    // 判断是否特价
                    if (0 <= saleType) {
                        temp += "<p class='price'><span class='new-price'>¥" + saleprice + "</span>&nbsp;<span class='last-price'>¥" + price + "</span>&nbsp;</p>";
                        price = saleprice ;  
                    } else {
                        temp += "<p class='price'><span class='new-price'>¥" + price + "</span>&nbsp;</p>";
                    }
                    temp += '<a class="sub" ontouchend=lessFunc(\"' + farmId + '\");></a>';
                    temp += "<span id='num" + farmId + "' class=food-num>0</span>";
                    temp += '<a class="add" ontouchend=plusFunc(\"'+farmId+'\",\"'+$.trim(farmName.replace(/\s+/g,""))+'\",\"' + logo + '\",\"' + shopId + '\",\"' + $.trim(shopName) + '\",\"' + price + '\",\"' + saleType + '\",\"' + saleprice + '\",\"' + suggest + '\");></a>';
                    temp += "</li>";
                }

                $('#dataArea').append(temp.replaceAll("null", ""));

                if (len < 15) {
                    idajax = false;
                    document.getElementById('pullup').innerHTML = "<li class='none-list'>已经最后一页了</li>";
                } else {
                    idajax = true;
                    curPage = (data.page) + 1;
                }
            }
            farm();
        },
        error : function() {
        	document.getElementById('pullup').innerHTML = "<li class='none-list'>网络加载失败</li>";
        }
    });
}

// 跳转到确认订单页面
/*function toConfirm() {
    var num = $(".num").html();
    if (0 == num) {
        alert("您还没有选择任何商品！");
        return;
    }
    window.location.href = "/index/fresh/orders.do";
}*/