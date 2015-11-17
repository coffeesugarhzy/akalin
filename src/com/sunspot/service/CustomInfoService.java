package com.sunspot.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sunspot.pojo.CustomAddr;
import com.sunspot.pojo.CustomInfo;

/**
 * 客户管理
 * 
 * @author LuoAnDong
 * 
 */
public interface CustomInfoService
{

    /**
     * 通过id查询客户信息
     * 
     * @param ofCustomId
     * @return
     */
    CustomInfo queryById(String ofCustomId);

    List<CustomAddr> getAddrs(HttpServletRequest request);

    /**
     * 验证手机是否已经注册 
     * @param phone
     * @return 注册则返回true 否则false
     * @author LuoAnDong
     */
    boolean checkRepeatPhone(String phone);

	int changePwd(String telPhone, String newPasswd);
}
