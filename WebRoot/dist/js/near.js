	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	}
	var longitude = 0;
	var latitude = 0;
	var isLoad = true;
	 
	function getLocation() {
		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(position) {
			_longitude = (null == position) ? 0 : position.point.lng;
			_latitude = (null == position) ? 0 : position.point.lat;
			 
			$.ajax({
				type : "post",
				cache : false,
				url : "/index/near/position.do",
				data : {
					longitude : _longitude,
					latitude : _latitude
				},
				dataType : "json",
				success : function(data) {
					filterQuery(1, null, 0);
					loadGif();
				},
				error : function(data) {
					alert("网络传输错误");
					loadGif();
				}
			});
		});
	}
	 
	function formatMarks(marks){
    	if(marks == 0) return '<i></i><i></i><i></i><i></i><i></i>' ; 
    	else if(marks == 1) return '<i class="active"></i><i></i><i></i><i></i><i></i>' ; 
    	else if(marks == 2) return '<i class="active"></i><i class="active"></i><i></i><i></i><i></i>' ; 
    	else if(marks == 3) return '<i class="active"></i><i class="active"></i><i class="active"></i><i></i><i></i>' ; 
    	else if(marks == 4) return '<i class="active"></i><i class="active"></i><i class="active"></i><i class="active"></i><i></i>' ; 
    	else if(marks == 5) return '<i class="active"></i><i class="active"></i><i class="active"></i><i class="active"></i><i class="active"></i>' ; 
    }

	function pageLoad() {
		window.onscroll = function() {
			var t = $(this).scrollTop(), h = $(this).height(), dh = $(document)
					.height();
			if (dh < h)
				return;
			if ((t + h) > (dh - 200)) {
				if (isLoad) {
					filterQuery($("input[name='type']").val(), $("input[name='filter']").val(), $("ul#dataArea li").length);
				}
			}
		};
	}
	
	function setFilter(type,filter,page){
		if(page==0){
			$("input[name='type']").val(type) ; 
			$("input[name='filter']").val(filter) ; 
		}
	}

	function loadGif() {
		$("div#load").toggle();
	}

	$(function() {
		//getLocation();
		init();
		//loadGif();
		//getLocation();
		filterQuery(1,'',0);
		pageLoad();
	});

	function filterQuery(_fileType, _filter, _currentSize) {
		 
		if (_filter === ""&&_fileType==4) {
			return;
		}
		setFilter(_fileType,_filter,_currentSize);
		isLoad = false;
		loadGif();
		if (_currentSize == 0)
			$("ul#dataArea").html("");
			$.ajax({
					type : 'post',
					url : '/index/near/query.do',
					data : {
						fileType : _fileType,
						filter : _filter,
						currentSize : _currentSize
					},
					dataType : 'json',
					success : function(data) { 
						loadGif();
						var dataJson = data;
						var temp = "";
						if (data == null || dataJson.length == 0) {
							temp = "<li class='clearFix' style='text-align:center;height:20px;border-bottom:0px'>没有查询到数据</li>";
						} else {
							$(dataJson)
									.each(
											function(index) {
												var val = dataJson[index];
												temp += "<li class='clearFix'><a href='/index/dinner/entershop.do?shopId="+val.shopId+"'>";
												temp += "<img class='fl' src='"+val.logo+"' width='70' height='70' alt=''>";
												temp += '<p class="name">'+val.shopName+'';
												if(val.isBook==0){
													temp += '<i class="d"></i>' ; 
												}
												if(val.isDis==0){
													temp += '<i class="h"></i>' ; 
												}
												temp += '</p>'
												temp += '<p class="intro absolute">' ; 
												temp += '<span class="start">'+formatMarks(parseInt(val.marks));+'</span>';
												temp += "<span class='person-price'>&nbsp;&nbsp;¥"+val.cost+"/人</span>" ;
												temp += '</p>' ; 
												temp += '<span class="k-meter">'+ parseFloat(val.distance).toFixed(2)+'km</span>' ; 
												temp += "</a></li>";
											});
							isLoad = true;
						}
						;
						$('#dataArea').append(temp.replaceAll("null", ""));

					},
					error : function(data) {
						alert("<center>网络传输错误</center>");
						loadGif();
					}
				});
	}
	function init() {
		var hasTouch = 'ontouchstart' in window;
		var START_EV = hasTouch ? 'touchstart' : 'mousedown';
		var MOVE_EV = hasTouch ? 'touchmove' : 'mousemove';
		var END_EV = hasTouch ? 'touchend' : 'mouseup';
		$(".submit").on(
				END_EV,
				function() {
					$(this).hasClass('current') ? $(this)
							.removeClass('current') : $(this).addClass(
							'current');
				});
		$(".foods-list .sub").on(START_EV, function() {
		});
		$(".food-choice-list").on(START_EV, function() {
			return false;
		});
		$(".cart-ico").on(
				START_EV,
				function() {
					if (!$(this).hasClass('current')) {
						$(this).addClass('current');
						$(".shade").removeClass('none');
						$(".food-choice-list").css("visibility", "visible")
								.addClass('slideInUp').removeClass(
										'slideOutDown');
					} else {
						window.setTimeout(function() {
							$(".food-choice-list").css({
								visibility : "hidden"
							});
						}, 500);
						$(this).removeClass('current');
						$(".shade").addClass('none');
						$(".food-choice-list").removeClass('slideInUp')
								.addClass('slideOutDown');
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
			//$(".foods-list").addClass('none');
		});
		
		$(".tab-pane .detail-info li").on('click', function() {
			$(".tab-pane").addClass('none');
			//$(".foods-list").removeClass('none');
		});
	}

	function hiddenMenu() {
		filterQuery(4, $('#keyword').val(), 0);
		$(".tab-pane").addClass('none');
		//$(".foods-list").removeClass('none');
	}