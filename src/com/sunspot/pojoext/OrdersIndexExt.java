package com.sunspot.pojoext;

import java.util.List;

import com.sunspot.pojo.Orders;

/**
 * 订单扩展类
 * 
 * @author LuoAnDong
 * 
 */
@SuppressWarnings("serial")
public class OrdersIndexExt extends Orders
{
    // 店铺名称
    private String shopName;

    // 店铺LOGO
    private String shopLogo;

    // 店铺地址
    private String address;
    
    //人均消费
    private String cost;

    // 店铺电话
    private String telphone;

    // 店铺评份
    private Integer marks;

    // 餐桌信息
    private String tableName;

    // 餐桌
    private Integer tableVolume;

    private List<OrdersDetailExt> orderdetailsext;

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

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCost()
    {
        return cost;
    }

    public void setCost(String cost)
    {
        this.cost = cost;
    }

    public String getTelphone()
    {
        return telphone;
    }

    public void setTelphone(String telphone)
    {
        this.telphone = telphone;
    }

    public Integer getMarks()
    {
        return marks;
    }

    public void setMarks(Integer marks)
    {
        this.marks = marks;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public Integer getTableVolume()
    {
        return tableVolume;
    }

    public void setTableVolume(Integer tableVolume)
    {
        this.tableVolume = tableVolume;
    }

    public List<OrdersDetailExt> getOrderdetailsext()
    {
        return orderdetailsext;
    }

    public void setOrderdetailsext(List<OrdersDetailExt> orderdetailsext)
    {
        this.orderdetailsext = orderdetailsext;
    }

}
