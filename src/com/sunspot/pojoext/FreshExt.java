package com.sunspot.pojoext;

import com.sunspot.pojo.Fresh;

/**
 * 生鲜扩展类
 * 
 * @author LuoAnDong
 * 
 */
@SuppressWarnings("serial")
public class FreshExt extends Fresh
{
    /**
     *  产品数量 
     */
    private String numExt;
    
    /**
     * 店铺名称 
     */
    private String shopName ; 
 
    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public String getNumExt()
    {
        return numExt;
    }

    public void setNumExt(String numExt)
    {
        this.numExt = numExt;
    }
 
}
