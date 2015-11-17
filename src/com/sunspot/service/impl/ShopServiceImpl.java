package com.sunspot.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunspot.common.FileUtils;
import com.sunspot.common.Utils;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.Shop;
import com.sunspot.pojo.TableInfo;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.UserSessionUtil;
import com.sunspot.service.ShopService;

/**
 * 店铺实现
 * 
 * @author LuoAnDong
 * 
 */
@Service
public class ShopServiceImpl implements ShopService
{
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(ShopServiceImpl.class);

    // 根椐用户ID查询店铺
    private final String QUERY_SHOP_USERID = "select shop_id, of_user_id, shop_name,type_name, link_man, telphone, open_time, cost, had, remark, logo, is_book,is_full, is_dis,address,begin_time,end_time,marks,dis_content, longitude, latitude, add_date,update_date,order_num,of_area_id,online,is_today,is_weekly from shop where of_user_id=?";

    // 根椐用户ID查询店铺
    private final String QUERY_SHOP_USERID_SIMPLE = "select shop_id, of_user_id, shop_name,online,is_today,is_weekly from shop where of_user_id=?";

    // 椐椐店铺ID店铺
    private final String QUERY_SHOP_ID = "select shop_id, of_user_id, shop_name,type_name, link_man, telphone, open_time, cost, had, remark, logo, is_book,is_full, is_dis,address,begin_time,end_time,marks,dis_content, longitude, latitude, add_date,update_date,order_num,of_area_id,online,is_today,is_weekly from shop where shop_id=?";

    /**
     * 引入持久层操作类
     */
    @Autowired
    private BaseDao baseDao;

    /**
     * 分页查询
     * 
     * @param list
     * @param keyword
     * @return
     */
    @Override
    public List<Shop> queryByPage(DataGridModel<Shop> list, String keyword)
    {
        if (StringUtils.isNotBlank(keyword))
        {
            int total = baseDao.queryForIntPage(
                    "select count(*) from shop where shop_name like ?",
                    new Object[]
                    { Utils.getKeyword(Utils.transformChar(keyword)) });
            list.setCount(total);
            return baseDao
                    .query("select shop_id, address, is_book, is_full, link_man, s.logo, type_name, u.user_name as of_user_id, open_time, shop_name, s.telphone,online,is_today,is_weekly from (select shop_id, address, is_book, is_full, link_man, logo, type_name, of_user_id, open_time, shop_name, telphone,online,is_today,is_weekly from shop where shop_name like ? order by add_date limit ?,?) s left join user_info u on s.of_user_id = u.user_id",
                            new Object[]
                            { Utils.getKeyword(Utils.transformChar(keyword)),
                                    list.getCurNum(), list.getRows() },
                            Shop.class);
        }
        else
        {
            int total = baseDao.queryForIntPage("select count(*) from shop");
            list.setCount(total);
            return baseDao
                    .query("select shop_id, address,is_book,is_full,link_man,s.logo, type_name,u.user_name as of_user_id,open_time,shop_name,s.telphone,online,is_today,is_weekly from (select shop_id, address, is_book, is_full, link_man, logo, type_name, of_user_id, open_time, shop_name, telphone,online,is_today,is_weekly from shop order by add_date limit ?,?) s left join user_info u on s.of_user_id = u.user_id",
                            new Object[]
                            { list.getCurNum(), list.getRows() }, Shop.class);
        }
    }

    
    /**
     *查询商店类别
     * 
     * @param list
     * @param keyword
     * @param status
     */
    public void queryByPage(DataGridModel<Shop> list, String keyword,Integer status){
        String countsql = "select count(*) from shop where 1=1 ";
        String querysql = "select shop_id, address, is_book, is_full, link_man, s.logo, type_name, u.user_name as of_user_id, open_time, shop_name, s.telphone,online,is_today,is_weekly from (select shop_id, address, is_book, is_full, link_man, logo, type_name, of_user_id, open_time, shop_name, telphone,online,is_today,is_weekly from shop where 1=1 ";
        Object[] countParam = null;
        Object[] queryParam = null;
        
        switch (status)
        {
			case 0:
				countsql += "and online=1 ";
				querysql += "and online=1 ";
				break;
			case 1:
				countsql += "and is_today=1 ";
				querysql += "and is_today=1 ";
				break;
			case 2:
				countsql += "and is_weekly=1 ";
				querysql += "and is_weekly=1 ";
				break;
			default:
				break;
        }
        
        
        if (StringUtils.isNotBlank(keyword))
        {
        	String keyfmt = Utils.getKeyword(keyword);
        	countsql +=" and shop_name like ? ";
			querysql +=" and shop_name like ? order by add_date limit ?,?) s left join user_info u on s.of_user_id = u.user_id";
            countParam = new Object[]
            { "%"+keyfmt+"%"};
            queryParam = new Object[]
            {  "%"+keyfmt+"%",list.getCurNum(), list.getRows() };
        }
        else
        {
			querysql +=" order by add_date limit ?,?) s left join user_info u on s.of_user_id = u.user_id";
            queryParam = new Object[]
            { list.getCurNum(), list.getRows() };
        }

        int total = (null == countsql) ? baseDao.queryForIntPage(countsql)
                : baseDao.queryForIntPage(countsql, countParam);

        list.setCount(total);
        List<Shop> shopList = baseDao.query(querysql, queryParam,Shop.class);    
        list.setContent(shopList);
    }
    
    
    /**
     * 删除　
     * 
     * @param id
     *            　类型id
     * @return 成功则返回1 失败返回0
     */
    @Override
    public int delete(String id)
    {
        try
        {
            baseDao.delete(Shop.class, id);
            return 1;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    /**
     * 添加一个实体类
     * 
     * @param Shop
     */
    public void add(HttpServletRequest request, Shop shop)
    {
        shop.setShopId(null);
        // 上传文件
        shop.setLogo(Utils.uploadFile(request));
        shop.setOfUserId(UserSessionUtil.getUserInfo(request).getUserId());
        shop.setIsFull(0);
        shop.setMarks(0);
        shop.setOrderNum(0);
        shop.setAddDate(Utils.getCurDate("yyyy-MM-dd HH:mm:ss"));
        resetShop(shop);
        baseDao.add(shop);
        runLog.info("add shop,id=" + shop.getShopId());
    }

    /**
     * 修改店铺类型
     */
    @Transactional
    public void modify(HttpServletRequest request, Shop shop)
    {
        Shop tempShop = queryFullById(shop.getShopId());

        // 上传文件
        shop.setLogo(Utils.uploadFile(request));

        if (StringUtils.isNotBlank(shop.getLogo()))
        {
            if (StringUtils.isNotBlank(tempShop.getLogo()))
            {
                FileUtils.removeFile(request.getSession().getServletContext()
                        .getRealPath(tempShop.getLogo()));
            }
        }
        else
        {
            shop.setLogo(tempShop.getLogo());
        }

        shop.setUpdateDate(Utils.getCurDate("yyyy-MM-dd HH:mm:ss"));
        shop.setAddDate(tempShop.getAddDate());
        shop.setOfUserId(tempShop.getOfUserId());
        shop.setIsFull(tempShop.getIsFull());
        shop.setMarks(tempShop.getMarks());
        shop.setIsDis(tempShop.getIsDis());
        shop.setBeginTime(tempShop.getBeginTime());
        shop.setEndTime(tempShop.getEndTime());
        shop.setDisContent(tempShop.getDisContent());
        shop.setOrderNum(tempShop.getOrderNum());
        resetShop(shop);

        baseDao.delHQL("delete from TableInfo t where t.shop.shopId=?",
                new Object[]
                { shop.getShopId() });
        baseDao.update(shop);

        runLog.info("modify shop,id=" + shop.getShopId());
    }

    /**
     * 重设置shop
     * 
     * @param shop
     */
    private void resetShop(Shop shop)
    {

        List<TableInfo> tableInfos = shop.getTableInfos();
        if (null == tableInfos || 0 == tableInfos.size()) { return; }
        for (TableInfo tableInfo : tableInfos)
        {
            tableInfo.setShop(shop);
        }
    }

    /**
     * 修改保存　　
     */
    public void modifyStatus(HttpServletRequest request, String id)
    {
        Shop shop = queryFullById(id);
        Integer isFull = shop.getIsFull();
        baseDao.update("update shop set is_full=? where shop_id=?",
                new Object[]
                { (0 == isFull) ? 1 : 0, id });
    }

    /**
     * 根据id查询实体类型
     * 
     * @param id
     * @return 有则返回实体　否则返回NULL
     */
    @Override
    public Shop queryById(String id)
    {
        try
        {
            return baseDao
                    .queryForObject(
                            "select shop_type_id, shop_type_name from shop_type where shop_type_id=?",
                            new Object[]
                            { id }, Shop.class);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 根椐用户ID查询店铺
     * 
     * @param userId
     *            用户id
     * @return
     */
    public Shop queryByUserId(HttpServletRequest request)
    {
        String userId = UserSessionUtil.getUserInfo(request).getUserId();

        List<Shop> shops = baseDao.query(QUERY_SHOP_USERID, new Object[]
        { userId }, Shop.class);

        if (null == shops || 0 == shops.size())
        {
            return null;
        }
        else
        {
            Shop shop = shops.get(0);
            setTableInfos(shop);
            return shop;
        }
    }

    /**
     * 根椐用户ID查询店铺
     * 
     * @param userId
     *            用户id
     * @return
     */
    public List<Shop> listByUserId(HttpServletRequest request)
    {
        String userId = UserSessionUtil.getUserInfo(request).getUserId();

        return baseDao.query(QUERY_SHOP_USERID_SIMPLE, new Object[]
        { userId }, Shop.class);
    }

    /**
     * 根椐用户ID查询店铺
     */
    public Shop queryFullById(String id)
    {
        Shop shop = baseDao.queryForObject(QUERY_SHOP_ID, new Object[]
        { id }, Shop.class);
        return shop;
    }

    /**
     * 设置餐台信息
     * 
     * @param shop
     */
    private void setTableInfos(Shop shop)
    {

        if (null == shop) { return; }

        List<TableInfo> tableInfos = baseDao
                .query("select table_info_id,shop_id,table_name,table_volume,table_num from table_info where shop_id=? order by table_volume,table_num",
                        new Object[]
                        { shop.getShopId() }, TableInfo.class);
        shop.setTableInfos(tableInfos);
    }

    /**
     * 设置限时优惠
     */
    public void setDiscount(Shop shop)
    {
        Shop updateShop = queryFullById(shop.getShopId());
        updateShop.setIsDis(shop.getIsDis());
        updateShop.setBeginTime(shop.getBeginTime());
        updateShop.setEndTime(shop.getEndTime());
        updateShop.setDisContent(shop.getDisContent());
        baseDao.update(updateShop);
        runLog.info("set shop discount,id=" + shop.getShopId());
    }

    /**
     * 通过id查询出所有订单信息
     * 
     * @param shopId
     * @return
     * @author LuoAnDong
     */
    @Override
    public Shop getObjById(String shopId)
    {
        return baseDao.getByHibernate(Shop.class, shopId);
    }
    
    /**
     * 修改在线状态
     * 
     * @param shop
     * @return
     */
    public void updateShopOnline(Shop shop){
        Shop updateShop = queryFullById(shop.getShopId());
        updateShop.setOnline(shop.getOnline());
        baseDao.update(updateShop);
    }


    /**
     * 修改商店配置信息
     * 
     * @param shop
     */
	public void modShopInfo(Shop shop) {
		Shop updateShop = queryFullById(shop.getShopId());
		updateShop.setOnline(shop.getOnline());
		updateShop.setIsToday(shop.getIsToday());
		updateShop.setIsWeekly(shop.getIsWeekly());
		baseDao.update(updateShop);
	}
}
