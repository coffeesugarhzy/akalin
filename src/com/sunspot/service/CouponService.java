package com.sunspot.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.sunspot.pojo.Coupon;
import com.sunspot.pojoext.DataGridModel;

/**
 * 优惠券业务接口层
 * 
 * @author LuoAnDong
 * 
 */
@Service
public interface CouponService
{
    /**
     * 分页查询管理，默认为15条为一页
     * 
     * @param list 查询对象
     * @param userId 用户ID
     * @param keyword 查询条件值
     */
    void queryPage(DataGridModel<Coupon> list, String userId, String keyword);

    /**
     * 通过id查询实体
     */
    List<Coupon> queryByShopId(String id);
    
    /**
     * 通过id查询实体
     */
    Coupon queryById(String id);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 添加实体
     */
    void add(HttpServletRequest request, Coupon coupon);

    /**
     * 修改实体
     */
    void modify(HttpServletRequest request, Coupon coupon);

}
