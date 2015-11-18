	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
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
			if ((t + h) > (dh - 100)) {
				if (isLoad) {
					filterQuery($("input[name='type']").val(), $("ul#dataArea li").length);
				}
			}
		};
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
	
	function setFilter(type,page){
		if(page==0){
			$("input[name='type']").val(type) ;
		}
	}

	function loadGif() {
		$("div#load").toggle();
	}
	
	function filterQuery(_fileType, _currentSize) {

		setFilter(_fileType,_currentSize);
		isLoad = false;
		loadGif();
		if (_currentSize == 0)
			$("ul#dataArea").html("");
			$.ajax({
					type : 'post',
					url : '/index/index/queryMyLike.do',
					data : {
						type : _fileType,
						curSize : _currentSize
					},
					dataType : 'json',
					success : function(data) { 
						loadGif();
						var dataJson = data;
						var temp = "";
						if (data == null || dataJson.length == 0) {
							temp = "<li class='clearFix' style='text-align:center;height:20px;border-bottom:0px'>没有查询到数据</li>";
						} else {
							$(dataJson).each(
									function(index) {
										var val = dataJson[index];
										if(val.type==0){
											temp += "<li class='clearFix it"+val.id+"'><a href='/index/dinner/entershop.do?shopId="+val.goodId+"'>";
										}else{
											temp += "<li class='clearFix it"+val.id+"'><a href='/index/dinner/detail.do?cookbooksId="+val.goodId+"'>";
										}
										
										temp += "<img class='fl' src='"+val.logo+"' width='70' height='70' alt=''>";
										temp += '<p class="name">'+val.name+'';
										if(val.isBook==0){
											temp += '<i class="d"></i>' ; 
										}
										if(val.isDis==0){
											temp += '<i class="h"></i>' ; 
										}
										temp += '</p>'
										temp += '<p class="intro absolute">' ; 
										if(val.type==0){
											temp += '<span class="start">'+formatMarks(parseInt(val.marks));+'</span><br>';
										}else{
											temp += '<span class="intro">'+val.remark+'</span>';
										}
										
										temp += "<span class='person-price'>&nbsp;&nbsp;¥"+val.cost+"/人</span>" ;
									
										temp += "</p>" ; 
										temp += "</a>";
										temp += "<span class='k-meter' level='"+val.id+"'>删除</span>" ;
										temp += "</li>" ;
									});
							isLoad = true;
						}
						;
						$('#dataArea').append(temp.replaceAll("null", ""));
						$('.k-meter').click(function(){
							    var id=$(this).attr("level");
							    $.ajax({
									type : 'post',
									url : '/index/index/delShopLike.do',
									data : {
										id : id,
									},
									dataType : 'json',
									success : function(data) { 
										
										if(data=="success"){
											 $('.it'+id).hide();
										}
									  }
									});
						});

					},
					error : function(data) {
						alert("<center>网络传输错误</center>");
						loadGif();
					}
				});
	}
	

$(function() {
	
	 init();
	 filterQuery(0,0);
	 pageLoad();

});