package com.sunspot.service;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sunspot.pojo.Advertising;
import com.sunspot.pojo.Area;
import com.sunspot.pojo.Cookbook;
import com.sunspot.pojo.Coupon;
import com.sunspot.pojo.CustomAddr;
import com.sunspot.pojo.CustomCoupon;
import com.sunspot.pojo.CustomInfo;
import com.sunspot.pojo.FoodExp;
import com.sunspot.pojo.Fresh;
import com.sunspot.pojo.Shop;
import com.sunspot.pojo.Snack;
import com.sunspot.pojoext.CustomerLikeExt;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.ShopExt;

public interface MyIndexService
{

    public <T> void add(T t);

    public List<Advertising> adList();

    public List<Area> areaList();

    public DataGridModel<Snack> snackList(DataGridModel<Snack> list,
            String keyword);

    public Snack getSnack(String id);

    public DataGridModel<Shop> shopList(DataGridModel<Shop> list);

    public Shop getShop(String id);

    public void orderShopSuccess(String shopId, String bookDate,
            CustomInfo customer, String ofTableId, Model model);

    public void toOrdersShop(String shopId, Model model, CustomInfo customer);

    public List<CustomAddr> getAddressByCustomerId(String customerId);

    public boolean customerLogin(String phone, String password,
            HttpSession session);

    public void doUpdateHead(HttpSession session, Model model,
            MultipartHttpServletRequest request, CustomInfo customer);

    public <T> void update(T t);

    public <T> T getByHibernate(Class<T> t, Serializable id);

    public <T> void del(T t);

    public <T> void del(Class<T> t, Serializable id);

    public List<Coupon> getCustomCoupon(String customerId);

    //public void myLike(String customerId, Model model);
    public List<CustomerLikeExt> myLike(String customerId,String like_type,int currentSize,int page);

    public Cookbook getCookbook(String id);

    public Fresh getFresh(String id);

    public int update(String sql, Object[] param);

    public CustomCoupon couponDetail(String customerId);

    public DataGridModel<FoodExp> foodList(DataGridModel<FoodExp> list);

    public FoodExp getFoodExp(String id);

    public void queryTableByType(String type, Model model);

    public List<ShopExt> showByType(int currentSize, int pageSize,
            String longitude, String latitude, int filterType, String filter,
            String typeName);

    public void tableDetail(Model model, String longitude, String latitude,
            String shopId, HttpSession session,
            CustomLikeService customLikeService);

    public DataGridModel<Shop> popularityList(DataGridModel<Shop> list);

    public DataGridModel<Shop> hotList(DataGridModel<Shop> list);

    public void popularityDetail(Model model, String shopId);

}
