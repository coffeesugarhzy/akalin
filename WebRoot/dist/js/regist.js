var InterValObj;
var curCount = 59;
$(function() {
	$('#submitButton').prop('disabled', false);
	$('#submitButton').click(function() {
		var phone = $.trim($('#phone').val());
		if (null === phone || "" === phone) {
			alert("请输入手机号码");
			return;
		}
		var password = $.trim($('#password').val());
		if (null === password || "" === password) {
			alert("请输入密码");
			return;
		}
		var checkCode = $.trim($('#checkCode').val());
		if (null === checkCode || "" === checkCode) {
			alert("请输入验证码");
			return;
		}
	    if($('#accept').attr('checked')!="checked"){
            alert('必须接受协议才能注册成功');
            return; 
		}
		$('#submitButton').prop('disabled', true);
		$.ajax({
			url : '/index/index/register.do',
			type : 'POST',
			dataType : 'json',
			data : {
				'phone' : phone,
				'password' : encrypt(password),
				'checkCode' : checkCode
			},
			success : function(data) {
				$('#submitButton').prop('disabled', false);
				if (data === "checkCodeIsNull") {
					alert('验证码已失效');
				} else if (data === "error") {
					alert('错误的验证码');
				} else if(data=="loginError") {
					alert('注册成功');
					location.href = "/index/index/login.do";
				}else{
					location.href = "/index/index/my.do";
				}
			}
		});
	})
})

function sendCheckCode() { 
	if ("validation" != validation.className) {
		return;
	}
	var phone = $.trim($('#phone').val());
	if (null === phone || "" === phone || isNaN(phone)) {
		alert("请输入手机号码，且只能为数字");
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "/index/index/sendCheckPhone.do",
			data : {
				"phone" : phone
			},
			success : function(data) {
				if (data === "true") {
					alert("该手机号已经注册 ");
					$('#phone').val("");
				} else {
					$.ajax({
						type : "POST",
						url : "/index/index/sendCheckCode.do",
						data : {
							"phone" : phone
						}
					});

					$("#validation").addClass("vali_bg");
					$("#validation").attr("disabled", true).text("59s秒后重新获取");
					//请求成功后
					InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
				}
			}
		});
	}
	return; 
};
//timer处理函数
function SetRemainTime() {
	if (curCount == 0) {
		curCount = 59;
		window.clearInterval(InterValObj); //停止计时器
		$("#validation").removeAttr("disabled").removeClass("vali_bg"); //启用按钮
		$("#validation").text("获取验证码");
	} else {
		curCount--;
		$("#validation").text(curCount + "s秒后重新获取");
	}
}