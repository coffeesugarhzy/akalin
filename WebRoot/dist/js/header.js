$(function() {

	$(".top_menu").click(function() {

		$(this).siblings().removeClass("top_menu on");
		$(this).addClass("top_menu on");
		var url = $(this).find("a").attr("data-href");
		window.parent.frames["homeDisplay"].location = url;

		var positionTxt = $("#nav_menu .on").find("a").html();
		$("input[name='header-title']").val(positionTxt);

	});

	var url = $(".top_menu").find("a").attr("data-href");
	window.parent.frames["homeDisplay"].location = url;
});