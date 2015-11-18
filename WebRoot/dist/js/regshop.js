/**
 * 
 */
var InterValObj;
var curCount = 59;
var pattern = new RegExp("[~'!@#$￥%^&*()--+_=:—]"); 
$(function() {
	$('#submitButton').prop('disabled', false);
	
	$("input[name='loginName']").change(function(){
		  var loginName = $(this).val() ; 
		  if(pattern.test(loginName)){
				 alert("商家登录名有非法字符！");  
			     $(this).attr("value",""); 
				 $(this).focus();
				 return;
		  }
		  if(loginName!=null&&loginName!=''){
			  $.post("/index/index/checkRepeatName.do",{loginName:loginName},function(data){
				  if(data==="1"){
					  alert("用户名重复") ; 
					  $("input[name='loginName']").val("") ; 
				  }
			  }) ; 
		  }
	});

	$('#submitButton').click(function() {

	    var file = $('#file').val();
        if (null === file || "" === file) {
            alert('请上传LOGO');
            return;
        }
        
	    var userName =  $.trim($('#userName').val());
		if (null === userName || "" === userName) {
			alert("请输入商家名称");
			return;
		}
		else if(pattern.test(userName)){
			alert("商家名称有非法字符！");  
			$('#userName').attr("value",""); 
			$('#userName').focus();
			return;
		}

	    var loginName =  $.trim($('#loginName').val());
		if (null === loginName || "" === loginName) {
			alert("请输入商家登录名");
			return;
		}
		else if(pattern.test(loginName)){
			alert("商家登录名有非法字符！");  
			$('#loginName').attr("value",""); 
			$('#loginName').focus();
			return;
		}
		
		var phone = $.trim($('#telphone').val());
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
			url : '/index/index/regShop.do',
			type : 'POST',
			dataType : 'json',
			data : {
				'userName':userName,
				'loginName':loginName,
				'telphone' : phone,
				'loginPassword' : encrypt(password),
				'checkCode' : checkCode,
				'logo':file
			},
			success : function(data) {
				$('#submitButton').prop('disabled', false);
				if (data === "checkCodeIsNull") {
					alert('验证码已失效');
				} else if (data === "error") {
					alert('错误的验证码');
				}else if(data=="sysNullRoles"){
					alert('系统未添加对应角色用户！');
				} else {
					alert('注册成功');
					location.href = "/login.do";
				}
			}
		});
	})
})
function loadUploadImg() { 
	var photo = $.trim($('#photo').val());
	$.ajaxFileUpload({
		url: './uploadShopLogo.do',
		secureuri: false,
		fileElementId: $('#photo').attr('id'),
		dataType:'json',
		data: {'ajax':'upload-photo'},
		success: function(result, status){
			if(result.code =='1'){
				$('#imgView').attr('src', result.url);
				$('#file').val(result.url)
			}
		}
	});
}
function sendCheckCode() { 
	if ("validation" != validation.className) {
		return;
	}
	var phone = $.trim($('#telphone').val());
	if (null === phone || "" === phone || isNaN(phone)) {
		alert("请输入手机号码，且只能为数字");
		$('#telphone').val("");
		return;
	}
	else if (!phone.match(/^(?:13\d|15\d|18\d)\d{5}(\d{3}|\*{3})$/)) { 
		alert("手机号码格式不正确！请重新输入！"); 
		$("#telphone").focus(); 
		return false; 
	} 
	else {
		$.ajax({
			type : "POST",
			url : "/index/index/sendCheckTelPhone.do",
			data : {
				"telPhone" : phone
			},
			success : function(data) {
				if (data === "true") {
					alert("该手机号已经注册 ");
					$('#telphone').val("");
				} else {
					$.ajax({
						type : "POST",
						url : "/index/index/sendCheckShopCode.do",
						data : {
							"telPhone" : phone
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