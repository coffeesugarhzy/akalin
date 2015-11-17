function formatMarks(marks) {
	if (marks === 0)
		return "<i></i><i></i><i></i><i></i><i></i>";
	else if (marks === 1)
		return "<i class='active'></i><i></i><i></i><i></i><i></i>";
	else if (marks === 2)
		return "<i class='active'></i><i class='active'></i><i></i><i></i><i></i>";
	else if (marks === 3)
		return "<i class='active'></i><i class='active'></i><i class='active'></i><i></i><i></i>";
	else if (marks === 4)
		return "<i class='active'></i><i class='active'></i><i class='active'></i><i class='active'></i><i></i>";
	else if (marks === 5)
		return "<i class='active'></i><i class='active'></i><i class='active'></i><i class='active'></i><i class='active'></i>";
}
var isPager = false;
var curPage = 1;
function ajaxLoad() {
	var dataParams = {
		"page" : curPage
	};
	var temp = "";
	$
			.ajax({
				type : "post",
				data : dataParams,
				url : "/index/index/popularityList.do",
				dataType : "json",
				beforeSend : function() {
					$("#pullUp")
							.html(
									"<center><img src='/dist/images/loading.gif'></center>");
				},
				success : function(data) {
					var contentLength = data.content.length;
					if (0 === contentLength) {
						isPager = false;
						$("#pullUp").html(
								(1 === data.page) ? "<li class='none-list'>没有数据</li>" : "<li class='none-list'>已经最后一页了</li>");
					} else {
						var content = data.content;
						for ( var i = 0; i < contentLength; i++) {
							temp += "<li class='clearFix'><a href='/index/index/tableDetail.do?id="
									+ content[i].shopId + "'>";
							temp += "<img class='fl' src='" + content[i].logo
									+ "' width='70' height='70' alt=''>";
							temp += '<p class="name">' + content[i].shopName ; 
							
							if(content[i].isBook==0){
								temp += '<i class="d"></i>' ; 
							}
							if(content[i].isDis==0){
								temp += '<i class="h"></i>' ; 
							}
							
							temp += '</p>'
							temp += '<p class="intro absolute">';
							temp += '<span class="start">'
									+ formatMarks(parseInt(content[i].marks));
							+'</span>';
							temp += "<span class='person-price'>&nbsp;&nbsp;¥"
									+ content[i].cost + "/人</span>";
							temp += '</p>';
							temp += "</a></li>";
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