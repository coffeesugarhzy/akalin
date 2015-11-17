package com.sunspot.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.sunspot.common.MyUtil;
import com.sunspot.dao.BaseDao;
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
import com.sunspot.service.MyService;

@Service
@Scope("prototype")
public class MyServiceImpl implements MyService
{

    @Resource
    BaseDao baseDao;

    @Override
    public List<Advertising> queryAd(DataGridModel<Advertising> list,
            String keyword)
    {
        int total = 0;
        List<Advertising> adList = null;
        if (StringUtils.isNotBlank(keyword))
        {
            total = baseDao
                    .queryForIntPage(
                            "select count(ad_id) from advertising where ad_title like ?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)) });
            adList = baseDao
                    .query("select ad_id,ad_type,ad_title,ad_logo,ad_url,ad_order,ad_remark,add_date from advertising where ad_title like ? limit ?,?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)),
                                    list.getCurNum(), list.getRows() },
                            Advertising.class);
        }
        else
        {
            total = baseDao
                    .queryForIntPage("select count(ad_id) from advertising");
            adList = baseDao
                    .query("select ad_id,ad_type,ad_title,ad_logo,ad_url,ad_order,ad_remark,add_date from advertising limit ?,?",
                            new Object[]
                            { list.getCurNum(), list.getRows() },
                            Advertising.class);
        }
        list.setCount(total);
        return adList;
    }

    @Override
    public void add(Advertising t)
    {
        baseDao.add(t);
    }

    @Override
    public void update(Advertising t)
    {
        baseDao.update(t);
    }

    @Override
    public void del(Advertising t)
    {
        baseDao.delete(t);
    }

    @Override
    public Advertising getAdvertising(String id)
    {
        return baseDao.getByHibernate(Advertising.class, id);
    }

    @Override
    public List<Snack> querySnack(DataGridModel<Snack> list, String keyword)
    {
        int total = 0;
        List<Snack> snackList = null;
        if (StringUtils.isNotBlank(keyword))
        {
            total = baseDao.queryForIntPage(
                    "select count(snack_id) from snack where title like ?",
                    new Object[]
                    { MyUtil.getKeyword(MyUtil.transformChar(keyword)) });
            snackList = baseDao
                    .query("select snack_id,title,logo,content,address,add_date from snack where title like ? limit ?,?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)),
                                    list.getCurNum(), list.getRows() },
                            Snack.class);
        }
        else
        {
            total = baseDao
                    .queryForIntPage("select count(snack_id) from snack");
            snackList = baseDao
                    .query("select snack_id,title,logo,content,address,add_date from snack limit ?,?",
                            new Object[]
                            { list.getCurNum(), list.getRows() }, Snack.class);
        }
        list.setCount(total);
        return snackList;
    }

    @Override
    public void add(Snack t)
    {
        baseDao.add(t);
    }

    @Override
    public void update(Snack t)
    {
        baseDao.update(t);
    }

    @Override
    public void del(Snack t)
    {
        baseDao.delete(t);
    }

    @Override
    public Snack getSnack(String id)
    {
        return baseDao.getByHibernate(Snack.class, id);
    }

    @Override
    public List<FoodExp> queryFoodExp(DataGridModel<FoodExp> list,
            String keyword)
    {
        int total = 0;
        List<FoodExp> foodExpList = null;
        if (StringUtils.isNotBlank(keyword))
        {
            total = baseDao
                    .queryForIntPage(
                            "select count(food_exp_id) from food_exp where title like ?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)) });
            foodExpList = baseDao
                    .query("select food_exp_id,title,logo,content,address,add_date from food_exp where title like ? limit ?,?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)),
                                    list.getCurNum(), list.getRows() },
                            FoodExp.class);
        }
        else
        {
            total = baseDao
                    .queryForIntPage("select count(food_exp_id) from food_exp");
            foodExpList = baseDao
                    .query("select food_exp_id,title,logo,content,address,add_date from food_exp limit ?,?",
                            new Object[]
                            { list.getCurNum(), list.getRows() }, FoodExp.class);
        }
        list.setCount(total);
        return foodExpList;
    }

    @Override
    public void add(FoodExp t)
    {
        baseDao.add(t);
    }

    @Override
    public void update(FoodExp t)
    {
        baseDao.update(t);
    }

    @Override
    public void del(FoodExp t)
    {
        baseDao.delete(t);
    }

    @Override
    public FoodExp getFoodExp(String id)
    {
        return baseDao.getByHibernate(FoodExp.class, id);
    }

    @Override
    public List<GiftType> queryGiftType(DataGridModel<GiftType> list,
            String keyword)
    {
        int total = 0;
        List<GiftType> giftTypeList = null;
        if (StringUtils.isNotBlank(keyword))
        {
            total = baseDao
                    .queryForIntPage(
                            "select count(gift_type_id) from gift_type where gift_type_name like ?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)) });
            giftTypeList = baseDao
                    .query("select gift_type_id,gift_type_name,add_date from gift_type where gift_type_name like ? limit ?,?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)),
                                    list.getCurNum(), list.getRows() },
                            GiftType.class);
        }
        else
        {
            total = baseDao
                    .queryForIntPage("select count(gift_type_id) from gift_type");
            giftTypeList = baseDao
                    .query("select gift_type_id,gift_type_name,add_date from gift_type limit ?,?",
                            new Object[]
                            { list.getCurNum(), list.getRows() },
                            GiftType.class);
        }
        list.setCount(total);
        return giftTypeList;
    }

    @Override
    public void add(GiftType t)
    {
        baseDao.add(t);
    }

    @Override
    public void update(GiftType t)
    {
        baseDao.update(t);
    }

    @Override
    public void del(GiftType t)
    {
        baseDao.delete(t);
    }

    @Override
    public GiftType getGiftType(String id)
    {
        return baseDao.getByHibernate(GiftType.class, id);
    }

    @Override
    public List<Gift> queryPresent(DataGridModel<Gift> list, String keyword)
    {
        int total = 0;
        List<Gift> giftList = null;
        if (StringUtils.isNotBlank(keyword))
        {
            total = baseDao.queryForIntPage(
                    "select count(gift_id) from gift where gift_name like ?",
                    new Object[]
                    { MyUtil.getKeyword(MyUtil.transformChar(keyword)) });
            giftList = baseDao.query(
                    "select * from gift where gift_name like ? limit ?,?",
                    new Object[]
                    { MyUtil.getKeyword(MyUtil.transformChar(keyword)),
                            list.getCurNum(), list.getRows() }, Gift.class);
        }
        else
        {
            total = baseDao.queryForIntPage("select count(gift_id) from gift");
            giftList = baseDao.query("select * from gift limit ?,?",
                    new Object[]
                    { list.getCurNum(), list.getRows() }, Gift.class);
        }
        list.setCount(total);
        return giftList;
    }

    @Override
    public void add(Gift t)
    {
        baseDao.add(t);
    }

    @Override
    public void update(Gift t)
    {
        baseDao.update(t);
    }

    @Override
    public void del(Gift t)
    {
        baseDao.delete(t);
    }

    @Override
    public Gift getGift(String id)
    {
        return baseDao.getByHibernate(Gift.class, id);
    }

    @Override
    public List<GiftType> getGiftTypeList()
    {
        return baseDao.query(
                "select gift_type_id,gift_type_name from gift_type",
                GiftType.class);
    }

    @Override
    public List<GiftExchange> queryGiftExchange(
            DataGridModel<GiftExchange> list, String keyword)
    {
        int total = 0;
        List<GiftExchange> giftExchangeList = null;
        if (StringUtils.isNotBlank(keyword))
        {
            total = baseDao
                    .queryForIntPage(
                            "select count(exchange_id) from gift_exchange where gift_code like ?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)) });
            giftExchangeList = baseDao
                    .query("select * from gift_exchange where gift_code like ? limit ?,?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)),
                                    list.getCurNum(), list.getRows() },
                            GiftExchange.class);
        }
        else
        {
            total = baseDao
                    .queryForIntPage("select count(exchange_id) from gift_exchange");
            giftExchangeList = baseDao.query(
                    "select * from gift_exchange limit ?,?", new Object[]
                    { list.getCurNum(), list.getRows() }, GiftExchange.class);
        }
        list.setCount(total);
        return giftExchangeList;
    }

    @Override
    public void add(GiftExchange t)
    {
        baseDao.add(t);
    }

    @Override
    public void update(GiftExchange t)
    {
        baseDao.update(t);
    }

    @Override
    public void del(GiftExchange t)
    {
        baseDao.delete(t);
    }

    @Override
    public GiftExchange getGiftExchange(String id)
    {
        return baseDao.getByHibernate(GiftExchange.class, id);
    }

    @Override
    public CustomInfo getCustomInfoById(String customerId)
    {
        if (StringUtils.isNotBlank(customerId)) { return baseDao
                .getByHibernate(CustomInfo.class, customerId); }
        return null;
    }

    @Override
    public List<CustomInfo> queryCustomInfo(DataGridModel<CustomInfo> list,
            String keyword)
    {
        int total = 0;
        List<CustomInfo> customInfoList = null;
        if (StringUtils.isNotBlank(keyword))
        {
            total = baseDao
                    .queryForIntPage(
                            "select count(custom_id) from custom_info where custom_name like ?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)) });
            customInfoList = baseDao
                    .query("select * from custom_info where custom_name like ? limit ?,?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)),
                                    list.getCurNum(), list.getRows() },
                            CustomInfo.class);
        }
        else
        {
            total = baseDao
                    .queryForIntPage("select count(custom_id) from custom_info");
            customInfoList = baseDao.query(
                    "select * from custom_info limit ?,?", new Object[]
                    { list.getCurNum(), list.getRows() }, CustomInfo.class);
        }
        list.setCount(total);
        return customInfoList;
    }

    @Override
    public void add(CustomInfo t)
    {
        baseDao.add(t);
    }

    @Override
    public void update(CustomInfo t)
    {
        baseDao.update(t);
    }

    @Override
    public void del(CustomInfo t)
    {
        baseDao.delete(t);
    }

    @Override
    public CustomInfo getCustomInfo(String id)
    {
        return baseDao.getByHibernate(CustomInfo.class, id);
    }

    @Override
    public List<UserInfo> queryUserInfo(DataGridModel<UserInfo> list,
            String keyword)
    {
        int total = 0;
        List<UserInfo> userInfoList = null;
        if (StringUtils.isNotBlank(keyword))
        {
            total = baseDao
                    .queryForIntPage(
                            "select count(user_id) from user_info where user_name like ? and user_type=0",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)) });
            userInfoList = baseDao
                    .query("select user_id,user_name,user_type,login_name,telphone,add_date from user_info where user_name like ? and user_type=0 limit ?,?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)),
                                    list.getCurNum(), list.getRows() },
                            UserInfo.class);
        }
        else
        {
            total = baseDao
                    .queryForIntPage("select count(user_id) from user_info where user_type=0");
            userInfoList = baseDao
                    .query("select user_id,user_name,user_type,login_name,telphone,add_date from user_info where user_type=0 limit ?,?",
                            new Object[]
                            { list.getCurNum(), list.getRows() },
                            UserInfo.class);
        }
        list.setCount(total);
        return userInfoList;
    }

    @Override
    public void add(UserInfo t)
    {
        baseDao.add(t);
    }

    @Override
    public void update(UserInfo t)
    {
        baseDao.update(t);
    }

    @Override
    public void del(UserInfo t)
    {
        baseDao.delete(t);
    }

    @Override
    public UserInfo getUserInfo(String id)
    {
        return baseDao.getByHibernate(UserInfo.class, id);
    }

    @Override
    public List<UserRole> getUserRole()
    {
        return baseDao.query("select role_id,role_name from user_role",
                UserRole.class);
    }

    @Override
    public UserRole getUserRoleById(String id)
    {
        return baseDao.getByHibernate(UserRole.class, id);
    }

    @Override
    public List<AppVersion> queryAppVersion(DataGridModel<AppVersion> list,
            String keyword)
    {
        int total = 0;
        List<AppVersion> appList = null;
        if (StringUtils.isNotBlank(keyword))
        {
            total = baseDao
                    .queryForIntPage(
                            "select count(version_id) from app_version where versionname like ?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)) });
            appList = baseDao
                    .query("select * from app_version where versionname like ? limit ?,?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)),
                                    list.getCurNum(), list.getRows() },
                            AppVersion.class);
        }
        else
        {
            total = baseDao
                    .queryForIntPage("select count(version_id) from app_version");
            appList = baseDao.query("select * from app_version limit ?,?",
                    new Object[]
                    { list.getCurNum(), list.getRows() }, AppVersion.class);
        }
        list.setCount(total);
        return appList;
    }

    @Override
    public void add(AppVersion t)
    {
        baseDao.add(t);
    }

    @Override
    public void update(AppVersion t)
    {
        baseDao.update(t);
    }

    @Override
    public AppVersion getAppVersion(String id)
    {
        return baseDao.getByHibernate(AppVersion.class, id);
    }

    @Override
    public void del(AppVersion t)
    {
        baseDao.delete(t);
    }

    @Override
    public List<Feedback> queryFeedback(DataGridModel<Feedback> list,
            String keyword)
    {
        int total = 0;
        List<Feedback> feedbackList = null;
        if (StringUtils.isNotBlank(keyword))
        {
            total = baseDao
                    .queryForIntPage(
                            "select count(feeback_id) from feedback where content like ?",
                            new Object[]
                            { MyUtil.getKeyword(MyUtil.transformChar(keyword)) });
            feedbackList = baseDao.query(
                    "select * from feedback where content like ? limit ?,?",
                    new Object[]
                    { MyUtil.getKeyword(MyUtil.transformChar(keyword)),
                            list.getCurNum(), list.getRows() }, Feedback.class);
        }
        else
        {
            total = baseDao
                    .queryForIntPage("select count(feeback_id) from feedback");
            feedbackList = baseDao.query("select * from feedback limit ?,?",
                    new Object[]
                    { list.getCurNum(), list.getRows() }, Feedback.class);
        }
        list.setCount(total);
        return feedbackList;
    }

    @Override
    public List<Shop> getShop()
    {
        return baseDao.query("select shop_id,shop_name from shop", Shop.class);
    }

}
