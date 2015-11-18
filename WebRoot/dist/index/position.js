$(function() {
		$('.selectCity').click(function() {
			var city = $.trim($(this).html());
			$('#whichCity').html(city);
			$.ajax({
				type : 'POST',
				async : false,
				url : '/index/index/selectCity.do',
				data : {
					cityName : city
				}, 
				beforeSend : function() {
				},
				complete : function() {
				},
				success : function(data) {
					window.location.href = document.referrer;
				},
				error : function() {
					return;
				}
			});
		});
		var winHeight = $(window).innerHeight() - 150;
		$(".position-nav a").css({
			height : winHeight / 24
		});
	});