package com.sunspot.service;

import java.util.List;

import com.sunspot.pojoext.ShopExt;

/**
 * 附近店铺服务层接口
 * @author LuoAnDong
 *
 */
public interface NearService
{
    /**
     * 店铺查询　
     * @param currentSize 当面条数
     * @param pageSize 每页显示条数
     * @param longitude 用户经度
     * @param latitude　用户纬度
     * @param fileType  查询类型(如分类、距离、评价)
     * @param filter 过滤条件
     * @return List
     * @author LuoAnDong
     */
    public List<ShopExt> query(int currentSize , int pageSize , String longitude , String latitude , int fileType , String filter) ;

    /**
     * 根据id查询店铺所有信息
     * @param shopId 店铺id
     * @return 
     * @author LuoAnDong
     */
    public ShopExt query(String longitude , String latitude , String shopId);

    /**
     * 通过店铺id查询地图信息
     * @param shopId
     * @return
     * @author LuoAnDong
     */
    public ShopExt queryMap(String shopId); 
}
