package com.sunspot.common;

/**
 * 常量
 * 
 * @author LuoAnDong
 * 
 */
public interface Constants
{
    // begin:返回码
    // 成功
    int SUCCESS = 0;

    // 失败
    int ERROR = 1;

    // 用户名或密码为空
    int USER_OR_PASS_NULL = 1001;

    // 用户名或密码错误
    int USER_OR_PASS_ERR = 1002;

    // 验证码错误
    int CHECKCODE_ERR = 1003;
    // end:返回码
    
    /**
     * 店铺默认分页条数
     */
    int DEFAULT_SHOP_PAGE_SIZE = 1200 ; 
    
    /**
     * 订单提交失败页面
     */
    String ORDER_FAIL = "/index/fresh/submitfail" ; 
    
    /**
     * 订单提交成功页面
     */
    String ORDER_SUCCESS = "/index/fresh/submitOrder" ; 

}
