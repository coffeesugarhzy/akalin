package com.sunspot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.CustomCoupon;
import com.sunspot.service.CustomCouponService;

/**
 * 农副业务层实现
 * 
 * @author LuoAnDong
 * 
 * @param <T>
 */
@Service
public class CustomCouponServiceImpl implements CustomCouponService
{ 
    /**
     * 持久层操作类
     */
    @Autowired
    private BaseDao baseDao;

  
    /**
     * 通过id查询实体
     */
    public CustomCoupon queryById(String id)
    {
        try{
            if(id != null)
            return baseDao.getByHibernate(CustomCoupon.class, id) ;
            else return null ; 
        }catch(Exception e){
            return null ; 
        }
    }

    
}
