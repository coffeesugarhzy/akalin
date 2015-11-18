$(function() { 
	changeNav("curPlace"); 
	$(".sub_menu").click(function() {
		$(this).siblings().removeClass("sub_menu on");
		var modelText = $(this).html();
		$(this).addClass("sub_menu on");

		var subTitle = $(this).find("a").html();
		$("#curPlace").html(top.getTopNavTxt() + " >> " + subTitle);
	});
	var windowHeight = $(window).height() - 125;
	document.getElementById('rightMain').style.height = windowHeight;
	$("iframe#rightMain").css({
		"height" : windowHeight
	});

});

/**
 * 得到主标题
 * @param id
 */
function changeNav(id) {
	var topNav = top.getTopNavTxt();
	document.getElementById(id).innerHTML = topNav;
}
