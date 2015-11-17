String.prototype.replaceAll = function(s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}

var idajax = false;
var curPage = 1;

function ajaxFunc() {
    var dataParams = {
        "page" : curPage
    };

    var temp = "";
    $.ajax({
        type : 'post',
        url : '/index/fresh/listFlashsale.do',
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
                for ( var i = 0; i < len; i++) {
                    if(0 >= content[i].disNum){
                        continue;
                    }
                    temp += "<li class='clearFix'>";
                    temp += "<img class='fl' src='" + content[i].logo + "' width='70' height='70' alt=''>";
                    temp += "<p class='name'>" + content[i].freshName + "</p>";
                    temp += "<p class='price'><span class='new-price'>¥" + content[i].disPrice + "</span>&nbsp;<span class='last-price'>¥" + content[i].price + "</span></p>";
                    temp += "<p class='info'>剩余：<span>" + content[i].disNum + "</span>份</p>";
                    temp += "<a class='rush-purchase' href='/index/fresh/detail.do?fresh=" + content[i].freshId + "'>马上抢</a>";
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
        },
        error : function() {
        }
    });
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
});