package com.sunspot.service;

import org.springframework.stereotype.Service;

import com.sunspot.pojo.CustomCoupon;

/**
 *　优惠券接口层
 * 
 * @author LuoAnDong
 * 
 */
@Service
public interface CustomCouponService
{ 
    /**
     * 通过id查询实体
     */
    CustomCoupon queryById(String id); 

}
