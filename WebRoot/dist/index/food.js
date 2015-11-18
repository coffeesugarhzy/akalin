var isPager = false;
var curPage = 1;
function ajaxLoad() {
	var dataParams = {
		"page" : curPage
	};
	var temp = "";
	$.ajax({
		type : "post",
		data : dataParams,
		url : "/index/index/foodList.do",
		dataType : "json",
		beforeSend : function() {
			$("#pullUp").html(
					"<center><img src='/dist/images/loading.gif'></center>");
		},
		success : function(data) {
			var contentLength = data.content.length;
			if (0 === contentLength) {
				isPager = false;
				$("#pullUp").html((1 === data.page) ? "<li class='none-list'>没有数据</li>" : "<li class='none-list'>已经最后一页了</li>");
			} else {
				var content = data.content;
				for ( var i = 0; i < contentLength; i++) {
					temp += "<a href='/index/index/foodDetail.do?id="
							+ content[i].foodExpId + "'><li class='clearFix'>";
					temp += "<img class='fl' src='" + content[i].logo
							+ "' width='70' height='70' alt=''>";
					temp += "<p class='name norwap'>" + content[i].title + "</p>";
					temp += "<p class='name norwap'>" + (content[i].address==null?"&nbsp;":content[i].address) + "</p>";
					temp += "<p class='name norwap'>" + content[i].addDate.substr(0,10) + "</p>";
					temp += "</li></a>";
				}
				$("#dataArea").append(temp);
				if (contentLength < 15) {
					isPager = false;
					$("#pullUp").html("<li class='none-list'>已经最后一页了</li>");
				} else {
					isPager = true;
					curPage = (data.page) + 1;
				}
			}
		},
		error : function() {
		}
	});
}
$(function() {
	ajaxLoad();
	$(window).scroll(
			function() {
				var t = $(this).scrollTop(), h = $(this).height(), dh = $(
						document).height();
				if (dh < h)
					return;
				if ((t + h) > (dh - 200)) {
					if (isPager) {
						isPager = false;
						ajaxLoad();
					}
				}
			});
	var hasTouch = 'ontouchstart' in window;
	var START_EV = hasTouch ? 'touchstart' : 'mousedown';
	var MOVE_EV = hasTouch ? 'touchmove' : 'mousemove';
	var END_EV = hasTouch ? 'touchend' : 'mouseup';
	$(".collect").on(
			END_EV,
			function() {
				$(this).hasClass('current') ? $(this).removeClass('current')
						: $(this).addClass('current');
			});
});