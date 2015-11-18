package com.sunspot.pojoext;

import com.sunspot.pojo.FarmProduce;

/**
 * 家宴扩展类
 * 
 * @author LuoAnDong
 * 
 */
@SuppressWarnings("serial")
public class FarmProduceIndexExt extends FarmProduce
{
    // 店铺名称
    private String shopName;

    // 店铺LOGO
    private String shopLogo;
    
    //店铺电话
    private String shopTelPhone ; 

    // 区域名称
    private String areaName;

    // 店铺地址
    private String address;

    // 距离
    private double distance;

    // 店铺电话
    private String telphone;
    

    public String getShopTelPhone()
    {
        return shopTelPhone;
    }

    public void setShopTelPhone(String shopTelPhone)
    {
        this.shopTelPhone = shopTelPhone;
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public String getShopLogo()
    {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo)
    {
        this.shopLogo = shopLogo;
    }

    public String getAreaName()
    {
        return areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public double getDistance()
    {
        return distance;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    public String getTelphone()
    {
        return telphone;
    }

    public void setTelphone(String telphone)
    {
        this.telphone = telphone;
    }

}
