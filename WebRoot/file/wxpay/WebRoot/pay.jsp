<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String appId = request.getParameter("appid");
String timeStamp = request.getParameter("timeStamp");
String nonceStr = request.getParameter("nonceStr");
String packageValue = request.getParameter("package");
String paySign = request.getParameter("sign");

String orderId = request.getParameter("orderId") ; 
String userId = request.getParameter("userId") ; 
String balance = request.getParameter("balance") ; 
String localOrderId = request.getParameter("o") ; 
 
 
%>
<!doctype html>
<html lang="en">
  <head>
    <base href="<%=basePath%>"> 
    <title>舌尖上的柳州--微信支付 </title> 
    <meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1,width=device-width" />
  </head> 
  <body onload="callpay()">  
  			<div style="text-align:center;size:50px;">
  			<input type="button" style="font-size:25px;width:100%;height:200px;"
  			 value="微信支付" ontouchend="callpay()">
  			 </div> 
  </body>
  <script type="text/javascript">  
  	function callpay(){ 
		 WeixinJSBridge.invoke('getBrandWCPayRequest',{
  		 "appId" : "<%=appId%>","timeStamp" : "<%=timeStamp%>", "nonceStr" : "<%=nonceStr%>", "package" : "<%=packageValue%>","signType" : "MD5", "paySign" : "<%=paySign%>" 
   			},function(res){
				WeixinJSBridge.log(res.err_msg); 
				alert(res.err_code + res.err_desc + res.err_msg);
	            if(res.err_msg == "get_brand_wcpay_request:ok"){  
	            	alert("微信支付成功! 订单号为  userid = <%=userId%> + 用户名为<%=userId%>" );  
	            	location.href="http://172.17.20.201:8080/index/orders/notify.do?customerId=<%=userId%>&orderId=<%=orderId%>&balance=<%=balance%>&localOrderId=<%=localOrderId%>" ; 
	            }else if(res.err_msg == "get_brand_wcpay_request:cancel"){  
	                alert("用户取消支付!");  
	            }else{  
	               alert("支付失败!");  
	            }  
			})
		}
  </script>
  
</html>
