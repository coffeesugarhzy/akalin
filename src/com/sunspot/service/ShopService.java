package com.sunspot.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sunspot.pojo.Shop;
import com.sunspot.pojoext.DataGridModel;

/**
 * 店铺类型实现类
 * 
 * @author LuoAnDong
 * 
 */
public interface ShopService
{

    /**
     * 分页查询
     * 
     * @param list
     * @param keyword
     * @return
     */
    List<Shop> queryByPage(DataGridModel<Shop> list, String keyword);
    
    
    /**
     *查询商店类别
     * 
     * @param list
     * @param keyword
     * @param status
     */
    void queryByPage(DataGridModel<Shop> list, String keyword,Integer status);
    
    
    /**
     * 删除　
     * 
     * @param id
     *            　类型id
     * @return 成功则返回1 失败返回0
     */
    int delete(String id);

    /**
     * 添加一个实体类
     * 
     * @param shopType
     */
    void add(HttpServletRequest request, Shop shop);

    /**
     * 修改保存　　
     */
    void modify(HttpServletRequest request, Shop shop);

    /**
     * 修改保存　　
     */
    void modifyStatus(HttpServletRequest request, String id);

    /**
     * 根据id查询实体类型
     * 
     * @param id
     * @return
     */
    Shop queryById(String id);

    /**
     * 根椐用户ID查询店铺
     * 
     * @param userId
     *            用户id
     * @return
     */
    Shop queryByUserId(HttpServletRequest request);

    /**
     * 根椐用户ID查询店铺
     * 
     * @param userId
     *            用户id
     * @return
     */
    List<Shop> listByUserId(HttpServletRequest request);

    /**
     * 根椐用户ID查询店铺
     */
    Shop queryFullById(String id);

    /**
     * 设置限时优惠
     */
    void setDiscount(Shop shop);

    /**
     * 通过id查询出所有订单信息
     * 
     * @param shopId
     * @return
     * @author LuoAnDong
     */
    Shop getObjById(String shopId);
    
    
    /**
     * 修改在线状态
     * 
     * @param shop
     * @return
     */
    void updateShopOnline(Shop shop);
    
    
    /**
     * 修改商店配置信息
     * 
     * @param shop
     */
    void modShopInfo(Shop shop);

    /**
     * 设置店铺自动关店时间
     * @param shop
     */
	void setCloseTime(Shop shop);
}
