String.prototype.replaceAll = function(s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}
var idajax = false;
var curPage = 0;

function filterQuery(key, value) { 
	if(key==1&&$("#searchVal").val()==="")return ;
    $("#key").val(key);
    $("#value").val(value);
    if (1 != key) {
        $("#searchVal").val("");
    }
    $('#dataArea').html("");
    curPage = 0;
    idajax = false;
    ajaxFunc();
}

function loadGif(){
	$('#pullup').html('<center><img src="/dist/images/loading.gif"/></center>');
}

function getLocation() { 
		loadGif() ; 
		var geolocation = new BMap.Geolocation(); 
		geolocation.getCurrentPosition(function(position) {
			_longitude = (null == position) ? 0 : position.point.lng;
			_latitude = (null == position) ? 0 : position.point.lat;
			$.ajax({
				type : "post",
				cache : false,
				url : "/index/near/position.do",
				data : {
					longitude : _longitude,
					latitude : _latitude
				},
				dataType : "json",
				success : function(data) {
					//alert("this is a test") ; 
					//filterQuery(1, null, 0);
					loadGif();
					filterQuery('6','lth') ;  
				},
				error : function(data) {
					alert("网络传输错误");
					loadGif();
				}
			});
		});
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
            "page" : curPage
        };
    }

    var temp = "";
    $.ajax({
        type : 'post',
        //url : '/index/dinner/listDinner.do', 
        url:'/index/near/goods.do',
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
                var shopId, shopName, cookbooksId, cookName, logo, price, saleprice, saleType, suggest;
                for ( var i = 0; i < len; i++) { 
                    cookbooksId = content[i].cookbooksId;
                    cookName = content[i].cookName;
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
                    temp += "<li class='clearFix' ontouchend=javascript:this.style.background=''  onclick=javascript:window.location.href='/index/dinner/detail.do?cookbooksId="+cookbooksId+"'>";
                    temp += "<img class='fl' src='" + logo + "' width='70' height='70' alt=''>";
                    temp += "<p class='name'>" + cookName + "</p>";
                    temp += "<p class='intro'>精选自：" + shopName + "</p>";
                    // 判断是否特价
                    if (0 <= saleType) {
                        temp += "<p class='price'><span class='new-price'>¥" + saleprice + "</span>&nbsp;<span class='last-price'>¥" + price + "</span>&nbsp;</p>";
                    } else {
                        temp += "<p class='price'><span class='new-price'>¥" + price + "</span>&nbsp;</p>";
                    } 
                    temp += "<span id='num" + cookbooksId + "' class=food-num style='display:block;font-size:13px;color:#a9a9a9;right:10px;width:auto;'>"+parseFloat(content[i].distance).toFixed(2)+" km</span>";
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
           // dinner();
        },
        error : function() {
        }
    });
}

 