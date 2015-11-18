package com.sunspot.service;

import com.sunspot.pojo.UserInfo;

/**
 * 管理用户接口
 * @author LuoAnDong
 *
 */
public interface UserInfoService {

	/**
	 * 登陆用户登陆 
	 * @param username 用户名
	 * @param password 密码
	 * @param loginType 登陆类型
	 * @return 成功则返回用户信息 否则返回 null 
	 */
	UserInfo checkUserLogin(String username, String password , String loginType );

	/**
	 * 获取用户信息
	 * @param id
	 * @return
	 */
	public UserInfo queryById(String id);
	
   /**
    * 修改用户角色信息 
    * @param userInfo
    * @return
    */
	public int modifyUserRole(UserInfo userInfo);
}
