package com.sunspot.service;

import java.util.List;

import com.sunspot.pojo.Advertising;
import com.sunspot.pojo.AppVersion;
import com.sunspot.pojo.CustomInfo;
import com.sunspot.pojo.Feedback;
import com.sunspot.pojo.FoodExp;
import com.sunspot.pojo.Gift;
import com.sunspot.pojo.GiftExchange;
import com.sunspot.pojo.GiftType;
import com.sunspot.pojo.Shop;
import com.sunspot.pojo.Snack;
import com.sunspot.pojo.UserInfo;
import com.sunspot.pojo.UserRole;
import com.sunspot.pojoext.DataGridModel;

public interface MyService
{
    public List<Advertising> queryAd(DataGridModel<Advertising> list,
            String keyword);

    public void add(Advertising t);

    public void update(Advertising t);

    public void del(Advertising t);

    public Advertising getAdvertising(String id);

    //
    public List<Snack> querySnack(DataGridModel<Snack> list, String keyword);

    public void add(Snack t);

    public void update(Snack t);

    public void del(Snack t);

    public Snack getSnack(String id);

    //
    public List<FoodExp> queryFoodExp(DataGridModel<FoodExp> list,
            String keyword);

    public void add(FoodExp t);

    public void update(FoodExp t);

    public void del(FoodExp t);

    public FoodExp getFoodExp(String id);

    //
    public List<GiftType> queryGiftType(DataGridModel<GiftType> list,
            String keyword);

    public void add(GiftType t);

    public void update(GiftType t);

    public void del(GiftType t);

    public GiftType getGiftType(String id);

    public List<GiftType> getGiftTypeList();

    //
    public List<Gift> queryPresent(DataGridModel<Gift> list, String keyword);

    public void add(Gift t);

    public void update(Gift t);

    public void del(Gift t);

    public Gift getGift(String id);

    //
    public List<GiftExchange> queryGiftExchange(
            DataGridModel<GiftExchange> list, String keyword);

    public void add(GiftExchange t);

    public void update(GiftExchange t);

    public void del(GiftExchange t);

    public GiftExchange getGiftExchange(String id);

    public CustomInfo getCustomInfoById(String customerId);

    //
    public List<CustomInfo> queryCustomInfo(DataGridModel<CustomInfo> list,
            String keyword);

    public void add(CustomInfo t);

    public void update(CustomInfo t);

    public void del(CustomInfo t);

    public CustomInfo getCustomInfo(String id);

    //
    public List<UserInfo> queryUserInfo(DataGridModel<UserInfo> list,
            String keyword);

    public void add(UserInfo t);

    public void update(UserInfo t);

    public void del(UserInfo t);

    public UserInfo getUserInfo(String id);

    public List<UserRole> getUserRole();

    public UserRole getUserRoleById(String id);

    //
    public List<AppVersion> queryAppVersion(DataGridModel<AppVersion> list,
            String keyword);

    public void add(AppVersion t);

    public void update(AppVersion t);

    public void del(AppVersion t);

    public AppVersion getAppVersion(String id);

    //
    public List<Feedback> queryFeedback(DataGridModel<Feedback> list,
            String keyword);

    public List<Shop> getShop();
}
