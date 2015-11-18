﻿String.prototype.replaceAll = function(s1, s2) {
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
            "queryType" : 3,
            "key" : key,
            "value" : value
        };
    } else {
        dataParams = {
            "queryType" : 3,
            "page" : curPage
        };
    }

    var temp = "";
    
    $.ajax({
        type : 'post',
        url : '/index/fresh/listfresh.do',
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
                var shopId, shopName, freshId, freshName, logo, price, saleprice, saleType, suggest;
                for ( var i = 0; i < len; i++) {
                    freshId = content[i].freshId;
                    freshName = content[i].freshName;
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
                    temp += "<img class='fl' src='" + logo + "' width='70' height='70' alt=''  onclick=javascript:window.location.href='/index/fresh/freshDetail.do?freshId="+freshId+"'>";
                    temp += "<p class='name'>" + freshName + "</p>";

                    if (0 == content[i].cookType) {
                        temp += "<p class='intro'>精选自：" + shopName + "</p>";
                    } else {
                        temp += "<p class='intro'>建议用餐人数：" + suggest + "</p>";
                    }
                    
                    if(0 <= saleType){
                        temp += "<p class='price'><span class='new-price'>¥" + saleprice + "</span>&nbsp;<span class='last-price'>¥" + price + "</span></p>";
                    }else{
                        temp += "<p class='price'><span class='new-price'>¥" + price + "</span></p>";
                    }
                    temp += '<a class="sub" ontouchend=lessFunc(\"' + freshId + '\");></a>';
                    temp += "<span id='num" + freshId + "' class=food-num>0</span>";
                    temp += '<a class="add" ontouchend=plusFunc(\"' + freshId + '\",\"' + $.trim(freshName.replace(/\s+/g,"")) + '\",\"' + logo + '\",\"' + shopId + '\",\"' + $.trim(shopName.replace(/\s+/g,"")) + '\",\"' + price + '\",\"' + saleType + '\",\"' + saleprice + '\",\"' + suggest + '\");></a>';
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
                fresh();
            }
        },
        error : function() {
        }
    });
}