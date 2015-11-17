package com.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utils.EncryptUtils;
import com.utils.Sha1Util;

@SuppressWarnings("serial")
@WebServlet(name = "MainServlet", urlPatterns = "/wxpay/mainServlet")
public class MainServlet extends HttpServlet {
 
	//网页授权获取用户信息
	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 
		String appid = "wx0015878ab1cbe6ed";
		String backUri = "http://www.limmai.com/wxpay/topayServlet";
		String money = "0.02" ;  
		try {
			money = EncryptUtils.Encrypt3DES(money, "#AdFwEsdf@sdfsa=|#4");
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		String orderNo=appid+Sha1Util.getTimeStamp();
		backUri = backUri+"?userId=b88001&orderNo="+orderNo+"&describe=test&m="+money; 
		backUri = URLEncoder.encode(backUri); 
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
				"appid=" + appid+
				"&redirect_uri=" +
				 backUri+
				"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
		response.sendRedirect(url);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
