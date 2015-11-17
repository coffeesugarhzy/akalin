package com.sunspot.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sunspot.common.DateUtil;
import com.sunspot.common.HttpUtil;
import com.sunspot.common.ListUtil;
import com.sunspot.common.MyUtil;
import com.sunspot.common.Utils;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.Advertising;
import com.sunspot.pojo.Area;
import com.sunspot.pojo.Cookbook;
import com.sunspot.pojo.Coupon;
import com.sunspot.pojo.CustomAddr;
import com.sunspot.pojo.CustomCoupon;
import com.sunspot.pojo.CustomInfo;
import com.sunspot.pojo.CustomLike;
import com.sunspot.pojo.DataTypes;
import com.sunspot.pojo.FarmProduce;
import com.sunspot.pojo.FoodExp;
import com.sunspot.pojo.Fresh;
import com.sunspot.pojo.Orders;
import com.sunspot.pojo.Shop;
import com.sunspot.pojo.Snack;
import com.sunspot.pojo.TableInfo;
import com.sunspot.pojoext.CustomLikeExt;
import com.sunspot.pojoext.CustomerLikeExt;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.ShopExt;
import com.sunspot.service.CustomLikeService;
import com.sunspot.service.DataTypeService;
import com.sunspot.service.MyIndexService;

@Service
@Scope("prototype")
public class MyIndexServiceImpl implements MyIndexService
{

    public static final String SHOP = "select * from (select a.shop_id,a.is_dis,a.dis_content,a.begin_time,a.end_time, a.type_name,a.is_full, a.of_user_id, a.link_man, a.telphone, a.open_time, a.cost, a.marks, a.logo , a.is_book, a.remark, a.address, a.add_date, a.update_date, a.shop_name , sqrt(power(((? - a.longitude) * 3.1415926 * 6371229 * cos(((a.latitude + ?)/2)*3.1415926/180)/180),2) + power(((? - a.latitude) * 3.1415926 * 6371229 / 180),2))/1000 as distance from shop a) a ";

    @Resource
    BaseDao baseDao;

    @Resource
    private DataTypeService dataTypeSerivce;

    @Override
    public List<Advertising> adList()
    {
        return baseDao.query(
                "select ad_id,ad_logo,ad_url from advertising where ad_type=0",
                Advertising.class);
    }

    @Override
    public List<Area> areaList()
    {
        return baseDao
                .query("select area_name,area_id from area where level=2 order by area_name asc",
                        Area.class);
    }

    @Override
    public DataGridModel<Snack> snackList(DataGridModel<Snack> list,
            String keyword)
    {
        Object[] countParams = null;
        Object[] queryParams = null;
        if (StringUtils.isNotBlank(keyword))
        {
            keyword = MyUtil.getKeyword(MyUtil.transformChar(keyword));
            countParams = new Object[]
            { keyword };
            queryParams = new Object[]
            { keyword, list.getCurNum(), list.getRows() };
            list.setContent(baseDao
                    .query("select snack_id,title,logo,content,address,add_date from snack where title like ? limit ?,?",
                            queryParams, Snack.class));
        }
        else
        {
            queryParams = new Object[]
            { list.getCurNum(), list.getRows() };
            list.setContent(baseDao
                    .query("select snack_id,title,logo,content,address,add_date from snack limit ?,?",
                            queryParams, Snack.class));
        }
        int total = (null == countParams) ? baseDao
                .queryForIntPage("select count(snack_id) from snack") : baseDao
                .queryForIntPage(
                        "select count(snack_id) from snack where title like ?",
                        countParams);
        list.setCount(total);
        return list;
    }

    @Override
    public Snack getSnack(String id)
    {
        return baseDao.getByHibernate(Snack.class, id);
    }

    @Override
    public DataGridModel<Shop> shopList(DataGridModel<Shop> list)
    {
        Object[] queryParams = new Object[]
        { list.getCurNum(), list.getRows() };
        list.setContent(baseDao
                .query("select shop_id,shop_name,logo,begin_time,end_time,dis_content from shop where is_dis=0 limit ?,?",
                        queryParams, Shop.class));
        int total = baseDao
                .queryForIntPage("select count(shop_id) from shop where is_dis=0");
        list.setCount(total);
        return list;
    }

    @Override
    public Shop getShop(String id)
    {
        return baseDao.getByHibernate(Shop.class, id);
    }

    @Override
    public void orderShopSuccess(String shopId, String bookDate,
            CustomInfo customer, String ofTableId, Model model)
    {
        int orderNum = 0;
        Shop shop = getShop(shopId);
        Integer num = shop.getOrderNum();
        if (num == null)
        {
            num = 0;
        }
        num++;
        orderNum += num;
        shop.setOrderNum(orderNum);
        baseDao.update(shop);
        Orders orders = new Orders();
        orders.setAddDate(DateUtil.getCurrentDateTime());
        orders.setBookDate(bookDate);
        orders.setOfCustomId(customer.getCustomId());
        orders.setOfShopId(shopId);
        orders.setOfTableId(ofTableId);
        String orderCode = Utils.getMyOrderCode();
        orders.setOrdersCode(orderCode);
        orders.setOrderType(0);
        orders.setStatus(0);
        baseDao.add(orders);
        model.addAttribute("orderCode", orderCode);
    }

    @Override
    public List<CustomAddr> getAddressByCustomerId(String customerId)
    {
        return baseDao
                .query("select addr_id,address,is_default from custom_addr where custom_id=?",
                        new Object[]
                        { customerId }, CustomAddr.class);
    }

    @Override
    public void toOrdersShop(String shopId, Model model, CustomInfo customer)
    {
        Shop shop = baseDao.getByHibernate(Shop.class, shopId);
        model.addAttribute("shop", shop);
        List<TableInfo> tableList = baseDao.query(
                "select * from table_info where shop_id=?", new Object[]
                { shopId }, TableInfo.class);
        if (tableList.size() > 0)
        {
            model.addAttribute("tableList", tableList);
        }
    }

    @Override
    public boolean customerLogin(String phone, String password,
            HttpSession session)
    {
        List<CustomInfo> list = baseDao.query(
                "select * from custom_info where telphone=?", new Object[]
                { phone }, CustomInfo.class);
        if (list.size() > 0)
        {
            CustomInfo customInfo = list.get(0);
            if (!password.equalsIgnoreCase(customInfo.getLoginPassword())) { return false; }
            session.setAttribute("loginCustomer", customInfo);
        }
        else
        {
            return false;
        }
        return true;
    }

    @Override
    public <T> void add(T t)
    {
        baseDao.add(t);
    }

    @Override
    public void doUpdateHead(HttpSession session, Model model,
            MultipartHttpServletRequest request, CustomInfo customer)
    {
        String fileName = MyUtil.upload(request);
        if (StringUtils.isNotBlank(fileName))
        {
            MyUtil.removeFile(customer.getCustomHead(), request);
            customer.setCustomHead(fileName);
            baseDao.update(customer);
        }
    }

    @Override
    public <T> void update(T t)
    {
        baseDao.update(t);
    }

    @Override
    public <T> T getByHibernate(Class<T> t, Serializable id)
    {
        return baseDao.getByHibernate(t, id);
    }

    @Override
    public <T> void del(T t)
    {
        baseDao.delete(t);
    }

    @Override
    public List<Coupon> getCustomCoupon(String customerId)
    {
        return baseDao.query("select * from coupon where of_user_id=?",
                new Object[]
                { customerId }, Coupon.class);
    }

    public List<CustomerLikeExt> myLike(String customerId,String like_type,int currentSize,int pageSize){
    	String querySql=" select COOKBOOKS_ID goodId,PRICE cost,c.COOK_NAME `name`,c.logo,remarks,l.like_type type,SHOP_NAME remark,like_id id from cookbook c,custom_like l,shop s where c.COOKBOOKS_ID=l.GOODS_ID and c.of_shop_id=s.shop_id  and l.CUSTOM_ID=? order by l.ADD_DATE desc  limit ?,?";
        if(!"0".equals(like_type)){
    		querySql=" select c.SHOP_ID goodId,cost,c.SHOP_NAME `name`,logo,remark,l.like_type type,'' remarks,like_id id  from shop c,custom_like l where c.SHOP_ID=l.GOODS_ID  and l.CUSTOM_ID=? order by l.ADD_DATE desc  limit ?,?";
    	}
    	return  baseDao.query(querySql, new Object[]{customerId,currentSize,pageSize}, CustomerLikeExt.class);
    }
    
    @Override
    public Cookbook getCookbook(String id)
    {
        return baseDao.getByHibernate(Cookbook.class, id);
    }

    @Override
    public Fresh getFresh(String id)
    {
        return baseDao.getByHibernate(Fresh.class, id);
    }

    @Override
    public <T> void del(Class<T> t, Serializable id)
    {
        baseDao.delete(t, id);
    }

    @Override
    public int update(String sql, Object[] param)
    {
        return baseDao.update(sql, param);
    }

    @Override
    public CustomCoupon couponDetail(String customerId)
    {
        return baseDao.queryForObject(
                "select * from custom_coupon where custom_id=?", new Object[]
                { customerId }, CustomCoupon.class);
    }

    @Override
    public DataGridModel<FoodExp> foodList(DataGridModel<FoodExp> list)
    {
        Object[] queryParams = new Object[]
        { list.getCurNum(), list.getRows() };
        list.setContent(baseDao
                .query("select food_exp_id,title,logo,content,address,add_date from food_exp limit ?,?",
                        queryParams, FoodExp.class));
        int total = baseDao
                .queryForIntPage("select count(food_exp_id) from food_exp");
        list.setCount(total);
        return list;
    }

    @Override
    public FoodExp getFoodExp(String id)
    {
        return baseDao.getByHibernate(FoodExp.class, id);
    }

    @Override
    public void queryTableByType(String type, Model model)
    {
        List<DataTypes> datatypeList = dataTypeSerivce.queryByName("订桌");
        if (datatypeList.size() > 0)
        {
            model.addAttribute("beanList", datatypeList);
        }
        if (StringUtils.isNotBlank(type))
        {
            model.addAttribute("typeName", MyUtil.transformChar(type));
        }
    }

    @Override
    public List<ShopExt> showByType(int currentSize, int pageSize,
            String longitude, String latitude, int filterType, String filter,
            String typeName)
    {
        List<ShopExt> shopList = null;
        // 分类
        if (filterType == 1)
        {
            if (StringUtils.isNotBlank(filter))
            {
                String sql = SHOP
                        + " where a.type_name=? order by a.distance asc limit ?,?";
                shopList = baseDao.query(sql,
                        new Object[]
                        { longitude, latitude, latitude, filter, currentSize,
                                pageSize }, ShopExt.class);
            }
            else
            {
                String sql = SHOP
                        + " where a.type_name=? order by a.distance asc limit ?,?";
                shopList = baseDao.query(sql, new Object[]
                { longitude, latitude, latitude, typeName, currentSize,
                        pageSize }, ShopExt.class);
            }
        }
        // 距离
        else if (filterType == 2)
        {
            if (StringUtils.isBlank(filter))
            {
                filter = "0";
            }
            String sql = SHOP
                    + " where a.distance > ?  order by a.distance asc limit ?,?";
            shopList = baseDao.query(sql, new Object[]
            { longitude, latitude, latitude, filter, currentSize, pageSize },
                    ShopExt.class);
        }
        // 评价
        else if (filterType == 3)
        {
            /**
             * 1 为desc（高到低） 0 为asc （低到高）
             */
            filter = (StringUtils.isBlank(filter)) ? "desc" : (filter
                    .equals("1") ? "desc" : "asc");
            String sql = SHOP + " order by a.marks " + filter + " limit ?,?";
            shopList = baseDao.query(sql, new Object[]
            { longitude, latitude, latitude, currentSize, pageSize },
                    ShopExt.class);
        }
        // 搜索
        else
        {
            if (StringUtils.isBlank(filter))
            {
                filter = "%";
            }
            String sql = SHOP + " where a.shop_name like ? limit ?,?";
            shopList = baseDao.query(sql, new Object[]
            { longitude, latitude, latitude, MyUtil.getKeyword(filter),
                    currentSize, pageSize }, ShopExt.class);
        }
        return shopList;
    }

    @Override
    public void tableDetail(Model model, String longitude, String latitude,
            String shopId, HttpSession session,
            CustomLikeService customLikeService)
    {
        ShopExt shop = baseDao.queryForObject(SHOP + " where a.shop_id=?",
                new Object[]
                { longitude, latitude, latitude, shopId }, ShopExt.class);
        model.addAttribute("bean", shop);

        CustomInfo customInfo = HttpUtil.getCustom(session);
        if (customInfo != null && shop != null)
        {
            // 查询出用户关注的店铺
            CustomLike customLike = customLikeService.queryCustomLike(
                    shop.getShopId(), 0, customInfo.getCustomId());
            model.addAttribute("customLike", customLike);
        }
    }

    @Override
    public DataGridModel<Shop> popularityList(DataGridModel<Shop> list)
    {
        Object[] queryParams = new Object[]
        { list.getCurNum(), list.getRows() };
        list.setContent(baseDao
                .query("select shop_id,shop_name,logo,telphone,cost,marks,is_full,is_dis,is_book,address from shop order by order_num desc limit ?,?",
                        queryParams, Shop.class));
        int total = baseDao.queryForIntPage("select count(shop_id) from shop");
        list.setCount(total);
        return list;
    }

    @Override
    public void popularityDetail(Model model, String shopId)
    {
        model.addAttribute("bean", baseDao.getByHibernate(Shop.class, shopId));
    }

    @Override
    public DataGridModel<Shop> hotList(DataGridModel<Shop> list)
    {
        Object[] queryParams = new Object[]
        { list.getCurNum(), list.getRows() };
        list.setContent(baseDao
                .query("select shop_id,shop_name,logo,telphone,cost,marks,is_full,is_dis,is_book,address from shop order by marks desc limit ?,?",
                        queryParams, Shop.class));
        int total = baseDao.queryForIntPage("select count(shop_id) from shop");
        list.setCount(total);
        return list;
    }

}
