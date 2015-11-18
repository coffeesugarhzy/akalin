var dinnerCart = new Array();

// 初始化
function dinner() {
    var tempStr = getCookie("dinnerCart");
    dinnerCart = new Array();
    if (null == tempStr || "" == tempStr || 0 >= tempStr.split("\7").length) {
        setCookie("dinnerCart", "");
        return;
    }
    strToArray(tempStr.split("\7"));
    count();
    arrayToCart();
    var shopArr = arrayToCart();
    arrayToList(shopArr);
}

// 数组转字符串
function arryToStr() {
    var cookiesStr = "";
    for ( var i = 0, len = dinnerCart.length; i < len; i++) {
        if (0 < dinnerCart[i][9]) {
            for (j = 0; j < 10; j++) {
                cookiesStr += dinnerCart[i][j] + "\6";
            }
        }
        cookiesStr += "\7";
    }
    setCookie("dinnerCart", cookiesStr);
}

// 字符串转数组
function strToArray(tempArray) {
    var dinnerInnerArr;
    for ( var i = 0, len = tempArray.length; i < len; i++) {
        if (null != tempArray[i] && "" != tempArray[i]) {
            dinnerInnerArr = tempArray[i].split("\6");
            dinnerCart.push(dinnerInnerArr);
            if (0 < $("#num" + dinnerInnerArr[0]).length) {
                $("#num" + dinnerInnerArr[0]).html(dinnerInnerArr[9]);
                $("#num" + dinnerInnerArr[0]).parent().find('.sub').show();
                $("#num" + dinnerInnerArr[0]).show();
            }
        }
    }
}

// 减操作
function lessFuncCart(cookbooksId) {
    if (0 < $("#num" + cookbooksId).length) {
        $("#num" + cookbooksId).html($("#cart" + cookbooksId).html());
        if(0 == $("#num" + cookbooksId).html()){
            $("#num" + cookbooksId).hide();
            $("#num" + cookbooksId).prev().hide();
        }
    }
    var shopArr = lessFunc(cookbooksId);
    arrayToList(shopArr);
}

// 加操作
function plusFuncCart(cookbooksId, cookName, logo, shopId, shopName, price, saleType, saleprice, suggest) {
    if (0 < $("#num" + cookbooksId).length) {
        $("#num" + cookbooksId).html($("#cart" + cookbooksId).html());
    }
    var shopArr = plusFunc(cookbooksId, cookName, logo, shopId, shopName, price, saleType, saleprice, suggest);
    arrayToList(shopArr);
}

// 减操作
function lessFunc(cookbooksId) {
    var inputnum = (0 < $("#num" + cookbooksId).length) ? $("#num" + cookbooksId).html() : $("#cart" + cookbooksId).html();
    delToArray(parseInt(inputnum), cookbooksId);
    arryToStr();
    count();
    return arrayToCart();
}

// 加操作
function plusFunc(cookbooksId, cookName, logo, shopId, shopName, price, saleType, saleprice, suggest) {
    var inputnum = (0 < $("#num" + cookbooksId).length) ? $("#num" + cookbooksId).html() : $("#cart" + cookbooksId).html();
    addToArray(cookbooksId, cookName, logo, shopId, shopName, price, saleType, saleprice, suggest, parseInt(inputnum));
    arryToStr();
    count();
    return arrayToCart();
}

// 增加数组
function addToArray(cookbooksId, cookName, logo, shopId, shopName, price, saleType, saleprice, suggest, inputnum) {
    if (0 < dinnerCart.length) {
        var isExist = false;
        for ( var i = 0, len = dinnerCart.length; i < len; i++) {
            if (dinnerCart[i][0] == cookbooksId) {
                dinnerCart[i][9] = inputnum;
                isExist = true;
            }
        }
        if (!isExist) {
            dinnerCart.push([ cookbooksId, cookName, logo, shopId, shopName, price, saleType, saleprice, suggest, inputnum ]);
        }
    } else {
        dinnerCart.push([ cookbooksId, cookName, logo, shopId, shopName, price, saleType, saleprice, suggest, inputnum ]);
    }
}

// 删除数组
function delToArray(inputnum, id) {
    for ( var i = 0, len = dinnerCart.length; i < len; i++) {
        if (dinnerCart[i][0] == id) {
            dinnerCart[i][9] = inputnum;
        }
    }
}

// 统计
function count() {
    var num = 0;
    var totalMoney = 0.00;
    if (0 == dinnerCart.length) {
        $("#num").html(num);
        $(".money").html("¥0.00");
        return;
    }

    for ( var i = 0, len = dinnerCart.length; i < len; i++) {
        if (0 < dinnerCart[i][9]) {
            num += parseInt(dinnerCart[i][9]);
            totalMoney += (0 <= dinnerCart[i][6]) ? (dinnerCart[i][9] * dinnerCart[i][7]) : (dinnerCart[i][9] * dinnerCart[i][5]);
        }
    } 
    $("#num").html(num);
    $(".money").html("¥" + totalMoney.toFixed(2));
}

// 数组放入购物车
function arrayToCart() {
    $("#cart").html("");
    var cartHtml = "";
    var tempArr = new Array();
    var haveFlag = false;
    for ( var i = 0, len = dinnerCart.length; i < len; i++) {
        if (0 >= dinnerCart[i][9]) {
            continue;
        }
        haveFlag = false;
        for ( var j = 0, len1 = tempArr.length; j < len1; j++) {
            if (tempArr[j][0] == dinnerCart[i][3]) {
                haveFlag = true;
                break;
            }
        }
        if (!haveFlag) {
            tempArr.push([ dinnerCart[i][3], dinnerCart[i][4] ]);
        }
    }

    var cookbooksId, cookName, logo, shopId, shopName, price, saleType, saleprice, suggest, inputnum;
    for ( var i = 0, len = tempArr.length; i < len; i++) {

        cartHtml += '<li class="shop-name clearFix">';
        cartHtml += '<span class="choice">精选自：</span>';
        cartHtml += '<span class="name">' + tempArr[i][1] + '</span> ';
        cartHtml += '</li>';

        for ( var j = 0, len1 = dinnerCart.length; j < len1; j++) {
            if (tempArr[i][0] == dinnerCart[j][3] && 0 < dinnerCart[j][9]) {

                cookbooksId = dinnerCart[j][0];
                cookName = dinnerCart[j][1];
                logo = dinnerCart[j][2];
                shopId = dinnerCart[j][3];
                shopName = dinnerCart[j][4];
                price = dinnerCart[j][5];
                saleType = dinnerCart[j][6];
                saleprice = dinnerCart[j][7];
                suggest = dinnerCart[j][8];
                inputnum = dinnerCart[j][9];

                cartHtml += '<li class="food">'
                cartHtml += '<span class="name">' + substrVal(cookName, 10) + '</span>';
                
                price = ((0 <= saleType) ? saleprice : price) * inputnum;
                
                cartHtml += '<span class="price">¥' + price.toFixed(2) + '</span>';
                cartHtml += '<a class="sub" ontouchend=lessFuncCart(\"' + cookbooksId + '\");></a>';
                cartHtml += "<span id='cart" + cookbooksId + "' class='food-num'>" + inputnum + "</span>";
                cartHtml += '<a class="add" ontouchend=plusFuncCart(\"' + cookbooksId + '\",\"' + cookName + '\",\"' + logo + '\",\"' + shopId + '\",\"' + shopName + '\",\"' + price + '\",\"' + saleType + '\",\"' + saleprice + '\",\"' + suggest + '\");></a>';
                cartHtml += "</li>";

            }
        }
    }
    $("#cart").html(cartHtml);
    return tempArr;
}

function arrayToList(shopArr) {

    if (0 >= $("#foodsListDiv").length) {
        return;
    }

    $("#foodsListDiv").html("");
    var foodsListHtml = "";

    var cookbooksId, cookName, logo, shopId, shopName, price, saleType, saleprice, suggest, inputnum;
    var subTotal = 0.00, subNum = 0, len = shopArr.length;
    for ( var i = 0; i < len; i++) {
        subTotal = 0.00;
        subNum = 0;
        foodsListHtml += '<h2 class="title">' + shopArr[i][1] + '</h2>';
        foodsListHtml += '<ul>';
        for ( var j = 0, len1 = dinnerCart.length; j < len1; j++) {
            if (shopArr[i][0] == dinnerCart[j][3] && 0 < dinnerCart[j][9]) {

                cookbooksId = dinnerCart[j][0];
                cookName = dinnerCart[j][1];
                logo = dinnerCart[j][2];
                shopId = dinnerCart[j][3];
                shopName = dinnerCart[j][4];
                price = dinnerCart[j][5];
                saleType = dinnerCart[j][6];
                saleprice = dinnerCart[j][7];
                suggest = dinnerCart[j][8];
                inputnum = dinnerCart[j][9];

                subNum += parseInt(inputnum);
                subTotal += ((0 < saleType) ? saleprice : price) * inputnum;

                foodsListHtml += '<li class="clearFix">';
                foodsListHtml += '<img class="fl" src="' + logo + '" alt="" height="70" width="70">';
                foodsListHtml += '<p class="name">' + cookName + '</p>';

                if (null == suggest || "" == suggest) {
                    foodsListHtml += '<p class="intro">&nbsp;</p>';
                } else {
                    foodsListHtml += '<p class="intro">建议就餐人数：' + suggest + '</p>';
                }

                if (-1 == saleType) {
                    foodsListHtml += '<p class="price"><span class="new-price">¥' + price + '</span></p>';
                } else {
                    foodsListHtml += '<p class="price"><span class="new-price">¥' + saleprice + '</span>&nbsp;<span class="last-price">¥' + price + '</span></p>';
                }
                foodsListHtml += '<a class="num" href="javascript:void(0);">x' + inputnum + '</a>';
                foodsListHtml += '</li>';
            }
        }
        foodsListHtml += '</ul>';
        foodsListHtml += '<div class="pay-count">';
        foodsListHtml += '<p style="padding:0px;border:0px"></p>';
        if (1 < len) {
            foodsListHtml += '<p class="count">共<span>' + subNum + '</span>件，小计<span>￥' + subTotal.toFixed(2) + '&nbsp;&nbsp;</span></p>';
        } else {
            foodsListHtml += '<p class="count">共<span>' + subNum + '</span>件，合计<span>￥' + subTotal.toFixed(2) + '&nbsp;&nbsp;</span></p>';
        }
        foodsListHtml += '<p class="moves">运费：￥0&nbsp;&nbsp;</p>';
        foodsListHtml += '</div>';

    }

    if (1 < len) {
        foodsListHtml += '<div class="pay-count">';
        foodsListHtml += '<p style="padding:0px;border:0px"></p>';
        foodsListHtml += '<p class="count">共<span>' + $("#num").html() + '</span>件，合计<span>' + $(".money").html() + '&nbsp;&nbsp;</span></p>';
        foodsListHtml += '<p class="moves">运费：￥0&nbsp;&nbsp;</p>';
        foodsListHtml += '</div>';
    }

    $("#foodsListDiv").html(foodsListHtml);

}

function substrVal(val, len) {
    return val.length > len ? val.substring(0, len) + "..." : val;
}

$(function() {
    var hasTouch = 'ontouchstart' in window;
    var START_EV = hasTouch ? 'touchstart' : 'mousedown';
    var MOVE_EV = hasTouch ? 'touchmove' : 'mousemove';
    var END_EV = hasTouch ? 'touchend' : 'mouseup';

    $(".cart-ico").on(START_EV, function() {
        if (!$(this).hasClass('current')) {
            $(this).addClass('current');
            $(".shade").removeClass('none');
            $(".food-choice-list").css("visibility", "visible").addClass('slideInUp').removeClass('slideOutDown');
        } else {
            window.setTimeout(function() {
                $(".food-choice-list").css({
                    visibility : "hidden"
                });
            }, 500);
            $(this).removeClass('current');
            $(".shade").addClass('none');
            $(".food-choice-list").removeClass('slideInUp').addClass('slideOutDown');
        }
        return false;
    });

    $(".search-items li").on(END_EV, function() {
        var showClass = $(this).attr('data-class');
        if (!$(this).hasClass('current')) {
            $(".search-items li").removeClass('current');
            $(this).addClass('current');
        }
        $(".tab-pane").addClass('none');
        $(".tab-pane." + showClass).removeClass('none');
        $(".foods-list").addClass('none');
    });

    $(".tab-pane .detail-info li").on('click', function() {
        $(".tab-pane").addClass('none');
        $(".foods-list").removeClass('none');
    });

    if (typeof ajaxFunc != 'undefined' && ajaxFunc instanceof Function) {
        ajaxFunc();
        window.onscroll = function() {
            var t = $(this).scrollTop(), h = $(this).height(), dh = $(document).height();
            if (dh < h)
                return;
            if ((t + h) > (dh - 200)) {
                if (idajax) {
                    idajax = false;
                    ajaxFunc();
                }
            }
        };
    }
});

// 跳转到确认订单页面
function toConfirm() {
    var num = $(".num").html();
    if (0 == num) {
        alert("您还没有选择任何商品！");
        return;
    }
    window.location.href = "/index/dinner/ordersec.do";
}