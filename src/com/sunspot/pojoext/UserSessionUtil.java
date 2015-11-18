package com.sunspot.pojoext;

import javax.servlet.http.HttpServletRequest;

import com.sunspot.pojo.UserInfo;

/**
 * 用户Session实现操作类， 方便提取与验证用户Session值 
 * @author LuoAnDong
 *
 */
public class UserSessionUtil {
	/**
	 * 根据用户Sesion(loginUser)得到用户在Session中的信息
	 * @param request
	 * @return  如果存在则返回 用户信息 否则返回空
	 * @author LuoAndong
	 */
	public static UserInfo getUserInfo(HttpServletRequest request){
		Object obj = request.getSession().getAttribute("loginUser") ; 
		if(obj != null){
			return (UserInfo) obj ; 
		}
		return null ; 
	}
}
