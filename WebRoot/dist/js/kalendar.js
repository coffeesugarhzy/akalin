$(function() {
	/*************    方法     **************/
	//判断是否是闰年,计算每个月的天数
	function leapYear(year) {
		var isLeap = year % 100 == 0 ? (year % 400 == 0 ? 1 : 0) : (year % 4 == 0 ? 1 : 0);
		return new Array(31, 28 + isLeap, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	}

	//获得某月第一天是周几
	function firstDay(day) {
		return day.getDay();
	}

	//获得当天的相关日期变量
	function dateNoneParam() {
		var day = new Date();
		var today = new Array();
		today['year'] = day.getFullYear();
		today['month'] = day.getMonth();
		today['date'] = day.getDate();
		today['hour'] = day.getHours();
		today['minute'] = day.getMinutes();
		today['second'] = day.getSeconds();
		today['week'] = day.getDay();
		today['firstDay'] = firstDay(new Date(today['year'], today['month'], 1));
		return today;
	}

	//获得所选日期的相关变量
	function dateWithParam(year, month) {
		var day = new Date(year, month);
		var date = new Array();
		date['year'] = day.getFullYear();
		date['month'] = day.getMonth();
		date['date'] = day.getDate();
		date['firstDay'] = firstDay(new Date(date['year'], date['month'], 1));
		return date;
	}

	//生成日历代码 的方法
	//参数依次为 年 月 日 当月第一天(是星期几)
	function kalendarCode(codeYear, codeMonth, codeDay, codeFirst) {
		var kalendar_html = '';
		//修改
		kalendar_html += "<table cellpadding='0' cellspacing='0' class='talbe_calendar margin15'><tr><th class='weedend'>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th class='weedend'>六</th></tr>";
		var N = ["日", "一", "二", "三", "四", "五", "六"];
		var f=0;
		for (var m = 0; m < 6; m++) {
			kalendar_html += "<tr class='days'>";
			for (var n = 0; n < 7; n++) {
				var First = firstDay(new Date(codeYear, codeMonth, (7 * m + n + 1 - codeFirst)));
				if ((7 * m + n) < codeFirst || (7 * m + n) >= (codeFirst + monthDays[codeMonth])) { //某月日历中不存在的日期
					if (m >= Math.ceil((codeFirst + monthDays[codeMonth]) / 7)) {
						//kalendar_html += "<td data-day='2014-05-26' class='null'></td>";
					} else {
						if (codeMonth == 11) {
							if ((7 * m + n + 1 - codeFirst) > 7) {
								f++;
								kalendar_html += "<td data-day='" + (codeYear + 1) + "-" + 1+ "-" + f + "' day='" + f + "' data-First='星期" + N[First] + "' class='tive'>" + f+ "</td>";
							}else{
								kalendar_html += "<td data-day='过期日历' class='null'></td>";
							}
						} else {
							if ((7 * m + n + 1 - codeFirst) >0) {
								f++;
								kalendar_html += "<td data-day='" + (codeYear) + "-" + (codeMonth + 2) + "-" + f + "' day='" + f + "' data-First='星期" + N[First] + "' class='tive'>" + f + "</td>";
							}else{
								kalendar_html += "<td data-day='过期日历' class='null'></td>";
							}
						}
						//kalendar_html += "<td data-day='2014-11-26' class='null'></td>";
					}
				} else {
					if (today['month'] == codeMonth) {
						if (codeDay == (7 * m + n + 1 - codeFirst)) { //如果当前天数等于
							kalendar_html += "<td data-day='" + codeYear + "-" + (codeMonth + 1) + "-" + (7 * m + n + 1 - codeFirst) + "' day='" + (7 * m + n + 1 - codeFirst) + "' data-First='星期" + N[First] + "' class='actives'><span class='today'>今天</span></td>";
						} else {
							if (codeDay >= (7 * m + n + 1 - codeFirst)) { //过期时间
								kalendar_html += "<td data-day='" + codeYear + "-" + (codeMonth + 1) + "-" + (7 * m + n + 1 - codeFirst) + "' day='" + (7 * m + n + 1 - codeFirst) + "' data-First='星期" + N[First] + "' class='cr_f'>" + (7 * m + n + 1 - codeFirst) + "</td>";
							} else {
								if ((7 * m + n + 1 - codeFirst == codeDay) && (((7 * m + n) % 7 == 0) || ((7 * m + n) % 7 == 6))) { //当天是周末
									kalendar_html += "<td data-day='" + codeYear + "-" + (codeMonth + 1) + "-" + (7 * m + n + 1 - codeFirst) + "' day='" + (7 * m + n + 1 - codeFirst) + "' data-First='星期" + N[First] + "' class='active'>" + (7 * m + n + 1 - codeFirst) + "</td>";
								} else if (((7 * m + n) % 7 == 0) || ((7 * m + n) % 7 == 6)) { //仅是周末
									kalendar_html += "<td data-day='" + codeYear + "-" + (codeMonth + 1) + "-" + (7 * m + n + 1 - codeFirst) + "' day='" + (7 * m + n + 1 - codeFirst) + "' data-First='星期" + N[First] + "' class='weedend'>" + (7 * m + n + 1 - codeFirst) + "</td>";
								} else if (7 * m + n + 1 - codeFirst == codeDay && codeDay == today['date']) { //仅是当天
									kalendar_html += "<td data-day='" + codeYear + "-" + (codeMonth + 1) + "-" + (7 * m + n + 1 - codeFirst) + "' day='" + (7 * m + n + 1 - codeFirst) + "'><span class='today'>今天</span></td>";
								} else { //其他日期
									kalendar_html += "<td data-day='" + codeYear + "-" + (codeMonth + 1) + "-" + (7 * m + n + 1 - codeFirst) + "' day='" + (7 * m + n + 1 - codeFirst) + "' data-First='星期" + N[First] + "' class='tive'>" + (7 * m + n + 1 - codeFirst) + "</td>";
								}
							}
						}
					} else {
						if ((7 * m + n + 1 - codeFirst == codeDay) && (((7 * m + n) % 7 == 0) || ((7 * m + n) % 7 == 6))) { //当天是周末
							kalendar_html += "<td data-day='" + codeYear + "-" + (codeMonth + 1) + "-" + (7 * m + n + 1 - codeFirst) + "' day='" + (7 * m + n + 1 - codeFirst) + "' data-First='星期" + N[First] + "' class='active'>" + (7 * m + n + 1 - codeFirst) + "</td>";
						} else if (((7 * m + n) % 7 == 0) || ((7 * m + n) % 7 == 6)) { //仅是周末
							kalendar_html += "<td data-day='" + codeYear + "-" + (codeMonth + 1) + "-" + (7 * m + n + 1 - codeFirst) + "' day='" + (7 * m + n + 1 - codeFirst) + "' data-First='星期" + N[First] + "' class='tive'>" + (7 * m + n + 1 - codeFirst) + "</td>";
						} else if (7 * m + n + 1 - codeFirst == codeDay && codeDay == today['date']) { //仅是当天
							kalendar_html += "<td data-day='" + codeYear + "-" + (codeMonth + 1) + "-" + (7 * m + n + 1 - codeFirst) + "' day='" + (7 * m + n + 1 - codeFirst) + "' data-First='星期" + N[First] + "'><span class='today'>今天</span></td>";
						} else { //其他日期
							kalendar_html += "<td data-day='" + codeYear + "-" + (codeMonth + 1) + "-" + (7 * m + n + 1 - codeFirst) + "' day='" + (7 * m + n + 1 - codeFirst) + "' data-First='星期" + N[First] + "' class='tive'>" + (7 * m + n + 1 - codeFirst) + "</td>";
						}
					}
				}
			}
			kalendar_html += "</tr>\n";
		}
		kalendar_html += "</table>";
		$('.pd_prompt').after(kalendar_html);
	}

	/*************   将日历代码放入相应位置，初始时显示此处内容      **************/
	//获取时间，确定日历显示内容
	var today = dateNoneParam();
	var monthDays = leapYear(today['year']);
	//返回数组，记录每月有多少天
	var month = new Array('一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月');
	var mon = today['month'] + 1; //月
	kalendarCode(today['year'], today['month'], today['date'], today['firstDay']);
	var yue = 12 - mon;
	//	if (today['month'] == 11) {
	//		fuc((today['year'] + 1), 6);
	//	};
	//	function fuc(year, mub) {
	//		for (var i = 0; i < mub; i++) {
	//			var date = dateWithParam(year, i);
	//			kalendarCode(year, i, "", date['firstDay']);
	//		}
	//	}

});