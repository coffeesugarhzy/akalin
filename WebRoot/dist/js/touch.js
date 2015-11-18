String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
}
 
$(function() {
	 
	var hasTouch = 'ontouchstart' in window;
	var START_EV = hasTouch ? 'touchstart' : 'mousedown';
	var MOVE_EV = hasTouch ? 'touchmove' : 'mousemove';
	var END_EV = hasTouch ? 'touchend' : 'mouseup';
  
	$(".submit").on(
			END_EV,
			function() {
				$(this).hasClass('current') ? $(this).removeClass('current'): $(this).addClass('current');
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
							.addClass('slideInUp').removeClass('slideOutDown');
				} else {
					window.setTimeout(function() {
						$(".food-choice-list").css({
							visibility : "hidden"
						});
					}, 500);
					$(this).removeClass('current');
					$(".shade").addClass('none');
					$(".food-choice-list").removeClass('slideInUp').addClass(
							'slideOutDown');
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
	
	window.onscroll = function(){ 
		var t = $(this).scrollTop(), h = $(this).height(), dh = $(document).height();
		if (dh < h) return;
		if ((t + h) > (dh - 200)){ 
			if($("input[name='keyword']").val() !=="")
				searchKeyword(0);
			else
				ajaxLoad();
		} 
	};
 
});

var isLoad = true;
function ajaxLoad(){ 
	var _currentSize = $("ul#dataArea li").length;
	var _type = $("input[name='type']").val() ; 
	var _weekday = $("input[name='weekday']").val();
	var _filter = $("input[name='filter']").val() ; 
	var temp ="" ;  
	if(isLoad){
		$("ul#pullup").find("center").html('<img src="/dist/images/loading.gif"/>');
		isLoad=false;
		$.post("/index/fresh/type.do",{currentSize:_currentSize,type:_type,filter:_filter},function(data){ 
			var dataJson = eval('(' + data + ')');
			if(dataJson.length == 0){
				$("ul#pullup").find("center").html("已经是最后一页了");
			}else{
				$(dataJson).each(function(index){
					
					var val = dataJson[index];
					
					temp += "<li class='clearFix'>";
					temp += "<img class='fl' src='"+val.logo+"' width='70' height='70' alt=''>";
					temp += "<p class='name'>"+ val.freshName + "</p>";
					temp += "<p class='intro'>精选自："+val.shopName+"</p>";  
					if(_weekday == val.saleDay){
						temp += "<p class='price'><span class='new-price line-through'>¥"+val.price+"</span>&nbsp;<span class='new-price'>¥"+val.salePrice+"</span></p>" ;
					}else{
						temp += "<p class='price'><span class='new-price'>¥"+val.price+"</span>&nbsp;</p>" ;
					} 
					temp += "<a class='sub' href='javascript:void(0);'></a>";
					temp += "<span class='food-num'>15</span>";
					temp += "<a class='add' href='javascript:void(0);'></a>";
					temp += "</li>"; 
					
				}); 
				
				$('#dataArea').append(temp.replaceAll("null", ""));
				isLoad=true;
				$("ul#pullup").find("center").html('');
			} 
		}); 
		
	} 
	
}

var isSeachLoad = true;
function searchKeyword(opt){
	 
	var _keyword = $.trim($("#searchVals").val()) ;  
	var _filter = $("input[name='filter']").val() ; 
	if(_keyword !== "" && isSeachLoad){
		isSeachLoad=false;
		var temp ="" ; 
		opt==1?$("ul#dataArea").html(""):''; 
		$("ul#pullup").find("center").html('<img src="/dist/images/loading.gif"/>');
		var _currentSize = $("ul#dataArea li").length; 
		$.post("/index/fresh/seach.do",{currentSize:_currentSize,keyword:_keyword,filter:_filter},function(data){ 
			var dataJson = eval('(' + data + ')'); 
			if(dataJson.length == 0){
				$("ul#pullup").find("center").html("搜索结束");
			}else{
				$(dataJson).each(function(index){ 
					var val = dataJson[index]; 
					temp += "<li class='clearFix'>";
					temp += "<img class='fl' src='"+val.logo+"' width='70' height='70' alt=''>";
					temp += "<p class='name'>"+ val.freshName + "</p>";
					temp += "<p class='intro'>精选自："+val.shopName+"</p>"; 
					temp += "<p class='price'><span class='new-price'>"+val.price+"</span>&nbsp;</p>" ; 
					temp += "<a class='sub' href='javascript:void(0);'></a>";
					temp += "<span class='food-num'>15</span>";
					temp += "<a class='add' href='javascript:void(0);'></a>";
					temp += "</li>"; 
					
				});  
				$('#dataArea').append(temp.replaceAll("null", ""));
				isSeachLoad=true;
				$("ul#pullup").find("center").html('');
			} 
			
		}); 
	}
    
}