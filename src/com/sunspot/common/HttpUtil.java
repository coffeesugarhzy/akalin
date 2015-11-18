package com.sunspot.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.sunspot.pojo.CustomInfo;

/**
 * http页面工具类，用于传值等　
 * @author LuoAnDong
 *
 */
public class HttpUtil
{ 
    /**
     * 从cookie中直接查询地址
     * @param request
     * @return
     */
    public static String getCityName(HttpServletRequest request){
      //根据区域查询
        String areaCookie = Utils.getCookie("cityName", request) ;  
        try
        {
            if(StringUtils.isNoneBlank(areaCookie)) 
                areaCookie = URLDecoder.decode(areaCookie,"utf-8") ;
            return areaCookie ; 
        }
        catch (UnsupportedEncodingException e)
        { 
            return null ; 
        }  
    }
    
    /**
     * 通过Session得到登陆用户　登陆用户的Session名称为"loginCustomer"
     * @param session
     * @return
     * @author LuoAnDong
     */
    public static CustomInfo getCustom(HttpSession session){
        Object customInfo = session.getAttribute("loginCustomer") ;   
        return  customInfo==null?null:(CustomInfo)customInfo ; 
    }
    
    /**
     * 设置request传值到页面　
     * @param req HttpServletRequest对象
     * @param name 名称
     * @param obj 对象
     * @author LuoAnDong
     */
    public static void req(HttpServletRequest request , String name , Object obj){
        request.setAttribute(name, obj) ; 
    }
    
    /**
     * 设置request传值list到页面　
     * @param req HttpServletRequest对象
     * @param obj List对象
     * @author LuoAnDong
     */
    public static void list(HttpServletRequest request ,Object obj){
        req(request ,"beanList" , obj) ; 
    }
    
    /**
     * 设置request传值实体对象到页面　
     * @param req HttpServletRequest对象
     * @param obj 实体类对象
     * @author LuoAnDong
     */
    public static void bean(HttpServletRequest request ,Object obj){
        req(request ,"bean" , obj) ; 
    }
}
