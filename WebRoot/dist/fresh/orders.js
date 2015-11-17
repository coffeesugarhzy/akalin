String.prototype.replaceAll = function(s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}

var submitnum = 0;

// 跳转到确认订单页面
function confirm() {
    
    if(0!=submitnum){
        return;
    }
    
    var num = $(".num").html();
    if (0 == num) {
        alert("您还没有选择任何商品！");
        return;
    }
    //if( 'checks checked' == document.getElementById("wxpay").className){
    //    $("#payType").val(0);
    //}else{
        $("#payType").val(1);
    //}
    
    $("#bookDate").val($("#sendDay").html()+" "+$("#sendTime").html()); 
     
    if(""==document.getElementById("address").value){
    	alert("配送地址不能为空！");
    	return;
    } 
    $("#sendAddr").val(document.getElementById("address").value);
 
    submitnum++;
    
    window.frames["form0"].submit();
}

function getDateStr() {
	
    // 设置日期，当前日期的前七天
    var myDate = new Date(); // 获取今天日期
    var dateTemp;
    var dateList = "";
    for ( var i = 0; i < 10; i++) {
        myDate.setDate(myDate.getDate() + 1);
        dateTemp = myDate.getFullYear() + "-" + formatVal(myDate.getMonth() + 1) + "-" + formatVal(myDate.getDate());
        if (0 == i) {
            $("#sendDay").html(dateTemp);
        }
        dateList += "<span>" + dateTemp + "</span>";
    }
    $("#dayList").html(dateList);
}

$(function() {
    getDateStr();
    farm();
});