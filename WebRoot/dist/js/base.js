$(function(){
    var hasTouch = 'ontouchstart' in window;
    var START_EV = hasTouch ? 'touchstart' : 'mousedown';
    var MOVE_EV = hasTouch ? 'touchmove' : 'mousemove';
    var END_EV = hasTouch ? 'touchend' : 'mouseup';
    function _fresh(){
        var endtime=new Date($(".timer").attr("data-times"));
        var nowtime = new Date();
        var leftsecond=parseInt((endtime.getTime()-nowtime.getTime())/1000);
        var h=parseInt((leftsecond/3600)%24);
        var m=parseInt((leftsecond/60)%60);
        var s=parseInt(leftsecond%60);
        var time = changeNum(h)+":"+changeNum(m)+":"+changeNum(s);
        innerTimes(time,$(".timer"));
        if(leftsecond<=0){
            time =
                innerTimes("00:00:00",$(".timer"));
            clearInterval(sh);
        }
    }
    _fresh()
    var sh=setInterval(_fresh,1000);

    function changeNum(num){
        if(num<10){
            num = "0"+num;
        }
        return num;
    }

    function innerTimes(time,obj){
        obj.find("span").each(function(i){
            $(this).html(time[i]);
        });
    }
    ~function(){
        $(".head-menu ul li").css({width:$(".contents").innerWidth()/4}).parent("ul").css({width:($(".contents").innerWidth()/4)*4});
        var showHig = $(window).innerHeight() - 139;
        $(".tab-pane .class-name,.tab-pane .detail-info").css({height:showHig+'px'});
    }();


    $(".shade").on(START_EV,function(){
        window.setTimeout(function(){$(".food-choice-list").css({visibility:"hidden"});},500);
        $(".cart-ico").removeClass('current');
        $(this).addClass('none');
        $(".food-choice-list").removeClass('slideInUp').addClass('slideOutDown');
        return false;
    });

    $(".checks").on(END_EV,function(){
        $(".checks").removeClass("checked");
        $(this).addClass("checked");
    })
    
    $(document).on(END_EV,".slide",function(e){
        if($(e.target).hasClass("slide") || $(e.target).parent("a").hasClass("slide")){
            $(".list").removeClass("down");
            $(this).find(".list").addClass("down");
        }
    });
    $(document).on(END_EV,".list span",function(){
        $(this).parent(".list").prev("span").html($(this).html());
        $(".list").removeClass("down");
    });

//    返回顶部
    $(".gotop").click(function(){
        $('html,body').animate({scrollTop: 0});
    });
    $(".choice-ico").on(END_EV,function(){
        $(this).hasClass('current') ? $(this).removeClass('current') : $(this).addClass('current');
    });
    $(".search").on(START_EV,function(){
        if(!$(this).hasClass('current')){
            $(this).addClass('current').animate({right:'70%'},500,'swing');
            $(".page-head .shop-name").addClass('none');
            $(".search-frame").animate({width:'70%'},500,'swing');
        }
    });
    
    $(".add").live(START_EV,function(){
        var $num = $(this).prev();
        var num;
        $num.html() == '' ? num = 1 : num = parseInt($num.html()) + 1;
        $num.html(num);
        $(this).parent().find('.sub').show();
        $(this).prev().show();
    });
    $(".sub").live(START_EV,function(){
        var $num = $(this).next();
        var num = parseInt($num.html()) - 1;       
        if(num <= 0){
            $(this).hide();
            $(this).next().hide();
        }
        $num.html((num>=0)?num:0);      
    });
    
    
    
    $('.search-frame input').focus(function(){
        var _this = $(this).parents(".page-head");
        var noInputViewHeight = $(window).height() - $(_this).height();
        var contentHeight = $(document).height() - $(_this).height();
        contentHeight = contentHeight > noInputViewHeight ? contentHeight : noInputViewHeight;
        setTimeout(function(){
            var startScrollY = $(window).scrollTop();
            var inputTopHeight =startScrollY- $(_this).offset().top;
            var inputTopPos = $(_this).offset().top + inputTopHeight;
            inputTopPos = inputTopPos > contentHeight ? contentHeight : inputTopPos;
            $(_this).css({'position':'absolute', 'top':inputTopPos });
            $(".search-items").css({'position':'absolute', 'top':inputTopPos +44 });
            $(window).bind('scroll', function(){
                if (inputTopHeight != noInputViewHeight) {
                    var offset = $(this).scrollTop() - startScrollY;
                    var afterScrollTopPos = inputTopPos + offset;
                    $(_this).css({'position':'absolute', 'top':afterScrollTopPos });
                    $(".search-items").css({'position':'absolute', 'top':afterScrollTopPos +44 });
                }
            });
        }, 10);
    }).blur(function(){//输入框失焦后还原初始状态
    	    $(this).parents(".page-head").removeAttr('style');
            $(".search-items").removeAttr('style');
            $(window).unbind('scroll');
        }); 
});

//写cookies
function setCookie(name, value) {
    var exp = new Date();
    exp.setTime(exp.getTime() + 86400000);
    document.cookie = name + "=" + escape(value) + ";expires="
            + exp.toGMTString();
}

// 读取cookies
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
        return unescape(arr[2]);
    } else {
        return null;
    }
}

// 删除cookies
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null) {
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }
}
 
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function getQueryStr(str){   
	var LocString=String(window.document.location.href);  
    var rs = new RegExp("(^|)"+str+"=([^/&]*)(/&|$)","gi").exec(LocString), tmp;    
    if(tmp=rs){   
        return tmp[2];   
    }     
    return "";   
}   

function formatVal(val) {
    if (val < 10) {
        return "0" + val;
    }
    return val;
}

function formatStr(str){
    if(str.length>8){
        return str.substr(0,8)+"...";
    }
    return str;
}