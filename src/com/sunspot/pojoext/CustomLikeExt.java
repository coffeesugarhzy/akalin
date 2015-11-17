package com.sunspot.pojoext;

import java.io.Serializable;
import java.util.List;

import com.sunspot.pojo.Cookbook;
import com.sunspot.pojo.CustomLike;
import com.sunspot.pojo.FarmProduce;
import com.sunspot.pojo.Fresh;
import com.sunspot.pojo.Shop;

public class CustomLikeExt extends CustomLike implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    List<Shop> shopList;

    List<Cookbook> cookbookList;

    List<Fresh> freshList;

    List<FarmProduce> farmProduceList;

    public List<FarmProduce> getFarmProduceList()
    {
        return farmProduceList;
    }

    public void setFarmProduceList(List<FarmProduce> farmProduceList)
    {
        this.farmProduceList = farmProduceList;
    }

    public List<Shop> getShopList()
    {
        return shopList;
    }

    public void setShopList(List<Shop> shopList)
    {
        this.shopList = shopList;
    }

    public List<Cookbook> getCookbookList()
    {
        return cookbookList;
    }

    public void setCookbookList(List<Cookbook> cookbookList)
    {
        this.cookbookList = cookbookList;
    }

    public List<Fresh> getFreshList()
    {
        return freshList;
    }

    public void setFreshList(List<Fresh> freshList)
    {
        this.freshList = freshList;
    }

}
