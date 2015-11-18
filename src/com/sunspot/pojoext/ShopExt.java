package com.sunspot.pojoext;

import com.sunspot.pojo.Shop;

/**
 * 店铺扩展类
 * @author LuoAnDong
 *
 */
@SuppressWarnings("serial")
public class ShopExt extends Shop
{ 
    /**
     * 用户距离店铺的距离
     */
    private String distance ;

    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String distance)
    {
        this.distance = distance;
    } 
    
}
