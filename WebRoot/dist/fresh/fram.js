String.prototype.replaceAll = function(s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}

var idajax = false;
var curPage = 1;

function filterQuery(key, value) {
	if(key==1&&$("#searchVal").val()==="")return ;
    $("#key").val(key);
    $("#value").val(value);
    $("#searchVal").val("");
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
            "queryType" :1,
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
        url : '/index/fram/type.do',
        data : dataParams,
        dataType : 'json',
        beforeSend : function() {
            $('#pullup').html('<center><img src="/dist/images/loading.gif"/></center>');
        },
        success : function(data) {
            var len = data.content.length;
            if (0 == len) {
                idajax = false;
                document.getElementById('pullup').innerHTML = (1 == data.page) ? "没有数据" : "已经最后一页了";
            } else {
                var content = data.content;
                var weekDay = $("#weekDay").val();
                var orderNum = 0;
                for ( var i = 0; i < len; i++) {
                    temp += "<li class='clearFix'>";
                    temp += "<img class='fl' src='" + content[i].logo + "' width='70' height='70' alt=''>";
                    temp += "<p class='name'>" + content[i].farmName + "</p>";
                    temp += "<p class='intro'>精选自：" + content[i].shopName + "</p>";

                    orderNum = (null == content[i].orderNum) ? 0 : content[i].orderNum;
                    
                    // 判断是否特价
                    if (weekDay == content[i].saleDay && 0 == content[i].isSale) {
                        temp += "<p class='price'><span class='new-price'>¥" + content[i].salePrice + "</span>&nbsp;<span class='last-price'>¥" + content[i].price + "</span>&nbsp;</p>";
                    } else {
                        temp += "<p class='price'><span class='new-price'>¥" + content[i].price + "</span>&nbsp;</p>";
                    }
                    
                    temp += "<a class='sub' href='javascript:void(0);'></a>";
                    temp += "<span class='food-num'>0</span>";
                    temp += "<a class='add' href='javascript:void(0);'></a>";
                    temp += "</li>";
                }

                $('#dataArea').append(temp.replaceAll("null", ""));

                if (len < 15) {
                    idajax = false;
                    document.getElementById('pullup').innerHTML = "已经最后一页了";
                } else {
                    idajax = true;
                    curPage = (data.page) + 1;
                }
            }
        },
        error : function() {
        }
    });
}

//减操作
function lessFunc(id, type) {
    var inputnum = $("#" + id + type);
    if (inputnum.val() <= 0) {
        inputnum.val(0);
    } else {
        inputnum.val(parseInt(inputnum.val()) - 1);
    }
    delToArray(parseInt(inputnum.val()), id);
    arryToStr();
    count();
}

// 加操作
function plusFunc(id, pic, title, price, suggest, type) {
    var inputnum = $("#" + id + type);
    if (parseInt(inputnum.val()) > 49) {
        inputnum.val(50);
    } else {
        inputnum.val(parseInt(inputnum.val()) + 1);
    }
    addToArray(parseInt(inputnum.val()), id, pic, title, price, suggest, type);
    arryToStr();
    count();
}

// 增加数组
function addToArray(inputnum, id, pic, title, price, suggest, type) {
    if (0 < dinnerCart.length) {
        var isExist = false;
        for ( var i = 0, len = dinnerCart.length; i < len; i++) {
            if (dinnerCart[i][0] == id) {
                dinnerCart[i][6] = inputnum;
                isExist = true;
            }
        }
        if (!isExist) {
            dinnerCart.push([ id, pic, title, price, suggest, type, inputnum ]);
        }
    } else {
        dinnerCart.push([ id, pic, title, price, suggest, type, inputnum ]);
    }
}

// 删除数组
function delToArray(inputnum, id) {
    for ( var i = 0, len = dinnerCart.length; i < len; i++) {
        if (dinnerCart[i][0] == id) {
            dinnerCart[i][6] = inputnum;
        }
    }
}

// 统计
function count() {
    var num = 0;
    var totalMoney = 0.00;
    if (0 == dinnerCart.length) {
        document.getElementById("num").innerText = num;
        document.getElementById("totalMoney").innerText = totalMoney;
        return;
    }

    for ( var i = 0, len = dinnerCart.length; i < len; i++) {
        num += parseInt(dinnerCart[i][6]);
        totalMoney += dinnerCart[i][3] * dinnerCart[i][6];
    }

    document.getElementById("num").innerText = num;
    document.getElementById("totalMoney").innerText = totalMoney.toFixed(2);
}


$(function() {
	
    var hasTouch = 'ontouchstart' in window;
    var START_EV = hasTouch ? 'touchstart' : 'mousedown';
    var MOVE_EV = hasTouch ? 'touchmove' : 'mousemove';
    var END_EV = hasTouch ? 'touchend' : 'mouseup';
    
    $(".submit").on(END_EV, function() {
        $(this).hasClass('current') ? $(this).removeClass('current') : $(this).addClass('current');
    });
    $(".foods-list .sub").on(START_EV, function() {
    });
    $(".food-choice-list").on(START_EV, function() {
        return false;
    });
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

    var key = $("#key").val();
    if (null == key || "" == key || "2" == key || "3" == key) {
        $("#d1").addClass("current");
    }

    if ("9" == key) {
        $("#d4").addClass("current");
    }

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
    } ;
});