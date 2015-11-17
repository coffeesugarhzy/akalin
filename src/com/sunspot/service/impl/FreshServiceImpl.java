package com.sunspot.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.common.FileUtils;
import com.sunspot.common.Utils;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.Fresh;
import com.sunspot.pojo.MakeMethod;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.FreshExt;
import com.sunspot.pojoext.FreshIndexExt;
import com.sunspot.service.FreshService;

/**
 * 生鲜业务层实现
 * 
 * @author LuoAnDong
 * 
 * @param <T>
 */
@Service
public class FreshServiceImpl implements FreshService

{

    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(FreshServiceImpl.class);
    
    /**
     * 菜谱、套餐
     */
    private static final String COOKBOOK_TYPE="1" ; 
    
    /**
     * 配菜
     */
    @SuppressWarnings("unused")
    private static final String FRESH_TYPE="0" ; 

    /**
     * 持久层操作类
     */
    @Autowired
    private BaseDao baseDao;

    // 统计查询
    private static final String COUNT_FRESH = "select count(1) from fresh a where a.fresh_type=? and a.of_shop_id=? and a.fresh_name like ?";

    // 无条件统计查询
    private static final String COUNT_FRESH_NOCOND = "select count(1) from fresh where fresh_type=? and of_shop_id=?";

    // 查询
    private static final String QUERY_FRESH = "select a.fresh_id,a.of_shop_id,a.logo,a.fresh_name,a.fresh_type,a.type_name,a.price,a.suggest,a.make_method,a.make_time,a.operate,a.add_date,a.update_date,a.remarks,a.is_sale,a.sale_day,a.sale_price,a.is_dis,a.begin_time,a.end_time,a.dis_price,a.dis_num,a.order_num from fresh a  where fresh_type=? and of_shop_id=? and a.fresh_name like ? order by a.add_date desc limit ?,?";

    // 根椐ID查询
    private static final String QUERY_FRESH_ID = "select a.fresh_id,a.of_shop_id,a.logo,a.fresh_name,a.fresh_type,a.type_name,a.price,a.suggest,a.make_method,a.make_time,a.operate,a.add_date,a.update_date,a.remarks,a.is_sale,a.sale_day,a.sale_price,a.is_dis,a.begin_time,a.end_time,a.dis_price,a.dis_num,a.order_num from fresh a where a.fresh_id =?";

    // 无条件查询
    private static final String QUERY_FRESH_NOCOND = "select a.fresh_id,a.of_shop_id,a.logo,a.fresh_name,a.fresh_type,a.type_name,a.price,a.suggest,a.make_method,a.make_time,a.operate,a.add_date,a.update_date,a.remarks,a.is_sale,a.sale_day,a.sale_price,a.is_dis,a.begin_time,a.end_time,a.dis_price,a.dis_num,a.order_num from fresh a  where fresh_type=? and of_shop_id=? order by a.add_date desc limit ?,?";

    // 查询制作工艺
    private static final String QUERY_MAKE_METHOD = "select method_id,method_name from make_method order by method_id";

    /**
     * 分页查询管理，默认为15条为一页
     * 
     * @param list 查询对象
     * @param userId 用户ID
     * @param freshType 生鲜类型
     * @param keyword 查询条件
     */
    public void queryPage(DataGridModel<Fresh> list, String userId,
            Integer freshType, String keyword)
    {

        Object[] countParams = null;
        Object[] queryParams = null;
        String countSql = null;
        String querySql = null;

        // 设置查询参数
        if (StringUtils.isNotBlank(keyword))
        {
            String keywordsFormat = Utils.getKeyword(Utils
                    .transformChar(keyword));

            countParams = new Object[]
            { freshType, userId, keywordsFormat };

            queryParams = new Object[]
            { freshType, userId, keywordsFormat, list.getCurNum(),
                    list.getRows() };

            countSql = COUNT_FRESH;
            querySql = QUERY_FRESH;

        }
        else
        {
            countParams = new Object[]
            { freshType, userId };

            queryParams = new Object[]
            { freshType, userId, list.getCurNum(), list.getRows() };

            countSql = COUNT_FRESH_NOCOND;
            querySql = QUERY_FRESH_NOCOND;
        }

        int total = baseDao.queryForIntPage(countSql, countParams);

        // 设置统计数据
        list.setCount(total);

        // 设置查询结果
        list.setContent(baseDao.query(querySql, queryParams, Fresh.class));
    }

    /**
     * 查询制作工艺
     * 
     * @param list 查询对象
     */
    public List<MakeMethod> queryMakeMethod()
    {
        return baseDao.query(QUERY_MAKE_METHOD, MakeMethod.class);
    }

    /**
     * 通过id查询实体
     */
    public Fresh queryById(String id)
    {
        return baseDao.queryForObject(QUERY_FRESH_ID, new Object[]
        { id }, Fresh.class);
    }

    /**
     * 删除
     */
    public void delete(String id)
    {

        baseDao.delHQL("delete from Fresh as f where f.freshId=?", new String[]
        { id });

        runLog.info("delete fresh,id=" + id);
    }

    /**
     * 添加实体
     */
    public void add(HttpServletRequest request, Fresh fresh)
    {
        // 上传文件
        fresh.setLogo(Utils.uploadFile(request));
        fresh.setAddDate(Utils.getCurDate("yyyy-MM-dd HH:mm:ss"));
        baseDao.add(fresh);
        runLog.info("add fresh,id=" + fresh.getFreshId());
    }

    /**
     * 修改实体
     */
    public void modify(HttpServletRequest request, Fresh fresh)
    {
        Fresh tempFresh = queryById(fresh.getFreshId());

        // 上传文件
        fresh.setLogo(Utils.uploadFile(request));

        if (StringUtils.isNotBlank(fresh.getLogo()))
        {
            if (StringUtils.isNotBlank(tempFresh.getLogo()))
            {
                FileUtils.removeFile(request.getSession().getServletContext()
                        .getRealPath(tempFresh.getLogo()));
            }
        }
        else
        {
            fresh.setLogo(tempFresh.getLogo());
        }

        fresh.setUpdateDate(Utils.getCurDate("yyyy-MM-dd HH:mm:ss"));
        fresh.setAddDate(tempFresh.getAddDate());

        fresh.setIsSale(tempFresh.getIsSale());
        fresh.setSaleDay(tempFresh.getSaleDay());
        fresh.setSalePrice(tempFresh.getSalePrice());
        fresh.setIsDis(tempFresh.getIsDis());
        fresh.setBeginTime(tempFresh.getBeginTime());
        fresh.setEndTime(tempFresh.getEndTime());
        fresh.setDisPrice(tempFresh.getDisPrice());
        fresh.setDisNum(tempFresh.getDisNum());
        fresh.setOrderNum(tempFresh.getOrderNum());

        baseDao.update(fresh);

        runLog.info("modify fresh,id=" + fresh.getFreshId());
    }

    /**
     * 设置特价
     */
    public void setSale(Fresh fresh)
    {
        Fresh updateFresh = queryById(fresh.getFreshId());
        updateFresh.setIsSale(fresh.getIsSale());
        updateFresh.setSaleDay(fresh.getSaleDay());
        updateFresh.setSalePrice(fresh.getSalePrice());
        updateFresh.setIsDis(fresh.getIsDis());
        updateFresh.setBeginTime(fresh.getBeginTime());
        updateFresh.setEndTime(fresh.getEndTime());
        updateFresh.setDisPrice(fresh.getDisPrice());
        updateFresh.setDisNum(fresh.getDisNum());
        baseDao.update(updateFresh);
        runLog.info("set fresh sale,id=" + fresh.getFreshId());
    }

    /**
     * 根据订单查询生鲜
     * 
     * @param orderId
     * @return
     * @author LuoAnDong
     */
    @Override
    public List<FreshExt> queryFreshByOrder(String orderId)
    {
        if (StringUtils.isBlank(orderId))
            return null;
        try
        {
            List<FreshExt> cbList = baseDao
                    .query("select fresh_name, price , od.num as num from fresh c ,(select cookbook_id , num from orders_detail where order_id=?) od where c.fresh_id=od.cookbook_id",
                            new Object[]
                            { orderId }, FreshExt.class);
            ;
            return cbList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过类型名称查询生鲜列表　<br/>
     * 
     * @param type 类型名称
     * @param pageNow 当前条数
     * @return 生鲜列表
     * @author LuoAnDong
     */
    @Override
    public List<FreshExt> queryByType(String type, int sizeNow, int pageSize)
    {
        if (StringUtils.isBlank(type))
            type = "%";
        List<FreshExt> list = baseDao
                .query("select f.fresh_id, f.fresh_name,f.dis_num, f.logo,f.price ,s.shop_name from ( select fresh_id, fresh_name, logo, of_shop_id, dis_num, price from fresh where type_name like ? order by add_date desc limit ?,? ) f left join shop s on f.of_shop_id=s.shop_id",
                        new Object[]
                        { type, sizeNow, pageSize }, FreshExt.class);
        return list;
    }

    /**
     * 通过生鲜名称搜索
     * 
     * @param keyword 关键字
     * @return
     * @author LuoAnDong
     */
    @Override
    public List<FreshExt> queryByKeyword(String keyword, int sizeNow,
            int pageSize)
    {
        if (StringUtils.isBlank(keyword))
            keyword = "%";
        List<FreshExt> list = baseDao
                .query("select f.fresh_id, f.fresh_name,f.dis_num, f.logo,f.price ,s.shop_name from ( select fresh_id, fresh_name, logo, of_shop_id, dis_num, price from fresh where fresh_name like ? order by add_date desc limit ?,? ) f left join shop s on f.of_shop_id=s.shop_id",
                        new Object[]
                        { Utils.getKeyword(keyword), sizeNow, pageSize },
                        FreshExt.class);
        return list;
    }

    /**
     * 查询出最新的生鲜菜谱
     * @param pageSize 显示条数
     * @return
     * @author LuoAnDong
     */
    @Override
    public List<FreshExt> queryNewsCookBook(int pageSize)
    { 
        List<FreshExt> list = baseDao.query("select fresh_id , fresh_name , logo from fresh where fresh_type='"+COOKBOOK_TYPE+"' order by add_date desc limit 0,?",
                new Object[]{pageSize} , 
                FreshExt.class) ; 
        return list ;
    }

    /**
     * 查询天天特价
     * @param day 特产星期日
     * @param currentSize
     * @param pageSize
     * @return
     */
    @Override
    public List<FreshExt> queryNewSale(String day, int currentSize,
            int pageSize)
    {  
        List<FreshExt> list = baseDao.query("select f.fresh_id, f.sale_price, f.fresh_name,f.dis_num, f.is_sale,f.sale_day , f.logo,f.price ,s.shop_name from ( select is_sale,sale_day,fresh_id,sale_price, fresh_name, logo, of_shop_id, dis_num, price from fresh where sale_day like ? and is_sale='0' order by add_date desc limit ?,? ) f left join shop s on f.of_shop_id=s.shop_id", 
                new Object[]{day ,currentSize ,pageSize} , 
                FreshExt.class) ; 
        return list ;
    }

    /**
     * 价格排序
     * @param currentSize 当前条数
     * @param order 　排序方式　
     * @param height 最高
     * @param lower　最低
     * @return
     * @author LuoAnDong
     */
    @Override
    public List<FreshExt> queryByPrice(String order, 
            String height,
            String lower, 
            int currentSize, 
            int pagesize)
    { 
        List<FreshExt> list = null ; 
        if(StringUtils.isNoneBlank(order)){
            order = (StringUtils.isNoneBlank(order))?order:"desc" ; 
            list = baseDao.query("select f.fresh_id,f.sale_price, f.fresh_name,f.dis_num,f.sale_day, f.logo,f.price ,f.is_sale,f.sale_price,s.shop_name from ( select sale_day,fresh_id, fresh_name, logo,sale_price,is_sale ,of_shop_id, dis_num, price from fresh where type_name like ? order by price "+order+" limit ?,? ) f left join shop s on f.of_shop_id=s.shop_id", 
                    new Object[]{order , currentSize , pagesize}, 
                    FreshExt.class) ; 
        }else{
            
        }
         
        return list;
    }
    
    // 统计所有
    private static final String COUNT_ALL_FRESH = "select count(1) from fresh a left join shop b on a.of_shop_id=b.shop_id left join area c on b.of_area_id = c.area_id";

    // 查询所有
    private static final String QUERY_ALL_FRESH = "select a.fresh_id,a.logo,a.of_shop_id,a.fresh_name,a.fresh_type,a.marks,a.price,a.type_name,a.suggest,a.add_date,a.update_date,a.remarks,a.is_sale,a.sale_day,a.sale_price,a.is_dis,a.begin_time,a.end_time,a.dis_price,a.dis_num,a.order_num,b.shop_name shopName,b.telphone,b.logo shopLogo,c.area_name areaName from fresh a left join shop b on a.of_shop_id=b.shop_id left join area c on b.of_area_id = c.area_id";

    @Override
    public void queryPage(
            DataGridModel<FreshIndexExt> list,
            Integer queryType, 
            Integer key, 
            String value)
    { 
        Object[] countParams = null;
        Object[] queryParams = null;
        String countSql = null;
        String querySql = null;
         
        countSql = COUNT_ALL_FRESH;
        querySql = QUERY_ALL_FRESH;

        switch (queryType)
        {
            case 1:
                countSql += " where a.fresh_type=0";
                querySql += " where a.fresh_type=0";
                break;
            case 2:
                countSql += " where a.fresh_type=1";
                querySql += " where a.fresh_type=1";
                break;
            case 3:
                Integer weekDay = Utils.getWeekDay();
                countSql += " where a.is_sale=0 and a.sale_day=" + weekDay;
                querySql += " where a.is_sale=0 and a.sale_day=" + weekDay;
                break;
            default:
                return;

        }

        if (null != key && StringUtils.isNotBlank(value))
        {
            switch (key)
            {
            // 菜谱搜索
            case 1:
                value = Utils.getKeyword(value);
                countSql += " and a.fresh_name like ?";
                querySql += " and a.fresh_name like ? order by a.add_date desc limit ?,?";
                countParams = new Object[]
                { value };
                queryParams = new Object[]
                { value, list.getCurNum(), list.getRows() };
                break;
            // 分类
            case 2:
                value = Utils.getKeyword(value);
                countSql += " and a.type_name like ?";
                querySql += " and a.type_name like ? order by a.add_date desc limit ?,?";
                countParams = new Object[]
                { value };
                queryParams = new Object[]
                { value, list.getCurNum(), list.getRows() };
                break;
            // 区域过滤
            case 3:
                value = Utils.getKeyword(value);
                countSql += " and c.area_name like ?";
                querySql += " and c.area_name like ? order by a.price limit ?,?";
                countParams = new Object[]
                { value };
                queryParams = new Object[]
                { value, list.getCurNum(), list.getRows() };
                break;
            // 最高销量
            case 4:
                querySql += " order by a.order_num desc limit ?,?";
                queryParams = new Object[]
                { list.getCurNum(), list.getRows() };
                break;
            // 最高评分
            case 5:
                querySql += " order by a.marks desc limit ?,?";
                queryParams = new Object[]
                { list.getCurNum(), list.getRows() };
                break;
            // 价格排序
            case 6:
                if ("lth".equals(value))
                {  
                    querySql += (3 == queryType) ? " order by a.price asc limit ?,?"
                            : " order by a.price asc limit ?,?";
                }

                if ("htl".equals(value))
                {
                    querySql += (3 == queryType) ? " order by a.sale_price desc limit ?,?"
                            : " order by a.price desc limit ?,?";
                }

                if ("0".equals(value))
                {
                    countSql += (3 == queryType) ? " and a.sale_price <= 30"
                            : " and a.price <= 30";
                    querySql += (3 == queryType) ? " and a.sale_price <= 30 order by a.sale_price limit ?,?"
                            : " and a.price <= 30 order by a.price limit ?,?";
                }

                if ("30".equals(value))
                {
                    countSql += (3 == queryType) ? " and a.sale_price between 31 and 50"
                            : " and a.price between 31 and 50";
                    querySql += (3 == queryType) ? " and a.sale_price between 31 and 50 order by a.sale_price limit ?,?"
                            : " and a.price between 31 and 50 order by a.price limit ?,?";
                }

                if ("50".equals(value))
                {
                    countSql += (3 == queryType) ? " and a.sale_price between 51 and 100"
                            : " and a.price between 51 and 100";
                    querySql += (3 == queryType) ? " and a.sale_price between 51 and 100 order by a.sale_price limit ?,?"
                            : " and a.price between 51 and 100 order by a.price limit ?,?";
                }

                if ("100".equals(value))
                {
                    countSql += (3 == queryType) ? " and a.sale_price>100"
                            : " and a.price>100";
                    querySql += (3 == queryType) ? " and a.sale_price>100 order by a.sale_price limit ?,?"
                            : " and a.price>100 order by a.price limit ?,?";
                }
                queryParams = new Object[]
                { list.getCurNum(), list.getRows() };
                break;
            // 特价套餐
            case 7:
                countSql += " and a.fresh_type=1";
                querySql += " and a.fresh_type=1 order by a.sale_price limit ?,?";
                queryParams = new Object[]
                { list.getCurNum(), list.getRows() };
                break;
            default:
                querySql += " order by a.add_date desc limit ?,?";
                queryParams = new Object[]
                { list.getCurNum(), list.getRows() };
            }
        }
        else
        {
            querySql += " order by a.add_date desc limit ?,?";
            queryParams = new Object[]
            { list.getCurNum(), list.getRows() };
        }

        int total = (null == countParams) ? baseDao.queryForIntPage(countSql)
                : baseDao.queryForIntPage(countSql, countParams);

        // 设置统计数据
        list.setCount(total);

        // 设置查询结果
        list.setContent((null == queryParams) ? baseDao.query(querySql,FreshIndexExt.class):baseDao.query(querySql, queryParams, FreshIndexExt.class));
         
    }

    /**
     * 查询限时抢购
     * @author LuoAnDong
     */
    @Override
    public void queryDisPage(DataGridModel<FreshIndexExt> list)
    {
        String curHour = Utils.getCurDate("HH:mm");
         
        String countSql = COUNT_ALL_FRESH
                + " where a.is_dis=0 and ? between a.begin_time and a.end_time";
        String querySql = QUERY_ALL_FRESH
                + " where a.is_dis=0 and ? between a.begin_time and a.end_time order by a.dis_price limit ?,?";

        Object[] countParams = new Object[]
        { curHour };
        Object[] queryParams = new Object[]
        { curHour, list.getCurNum(), list.getRows() };

        int total = baseDao.queryForIntPage(countSql, countParams);

        // 设置统计数据
        list.setCount(total);

        // 设置查询结果
        list.setContent(baseDao.query(querySql, queryParams,
                FreshIndexExt.class));
    }
     
 
    /**
     * 根据id查询生鲜的基本信息
     * @author LuoAnDong
     */
    @Override
    public FreshIndexExt queryByIndexId(String id)
    { 
        return baseDao.queryForObject("select a.fresh_id, a.logo, a.of_shop_id, a.fresh_name, a.fresh_type , a.marks, a.price, a.type_name, a.suggest, a.add_date , a.update_date, a.remarks, a.is_sale, a.sale_day, a.sale_price , a.is_dis, a.begin_time, a.end_time, a.dis_price, a.dis_num , a.order_num, b.shop_name as shopname, b.telphone, b.logo as shoplogo, c.area_name , b.address, b.telphone as areaname from fresh a left join shop b on a.of_shop_id = b.shop_id left join area c on b.of_area_id = c.area_id where a.fresh_id = ?", 
                new Object[]{id}, 
                FreshIndexExt.class);
    }
    
 // 根椐店铺ID查询菜谱
    private static final String QUERY_FRESH_SHOPID = "select a.fresh_id,a.logo,a.of_shop_id,a.fresh_name,a.fresh_type,a.marks,a.price,a.type_name,a.suggest,a.is_sale,a.sale_day,a.sale_price,a.is_dis,a.dis_price,a.begin_time,a.end_time,a.dis_num,a.order_num,b.shop_name shopName,b.telphone,b.logo shopLogo,c.area_name areaName from fresh a left join shop b on a.of_shop_id=b.shop_id left join area c on b.of_area_id = c.area_id where a.of_shop_id=?";
 
    @Override
    public List<FreshIndexExt> queryShopFreshId(Integer queryType, String id, String value,int currentPage,int pageSize)
    {
        String execSql = QUERY_FRESH_SHOPID;
        switch (queryType)
        {
        case 0:
            String tempVal = value;
            if (StringUtils.isNotBlank(value))
            {
                tempVal = Utils.getKeyword(Utils.transformChar(value));
                execSql += " and a.fresh_name like ? limit ?,?";
                return baseDao.query(execSql, new Object[]
                { id, tempVal,currentPage, pageSize }, FreshIndexExt.class);
            }
            else
            {
                return baseDao.query(execSql, new Object[]
                { id }, FreshIndexExt.class);
            }
        case 1:
            String curTime = Utils.getCurDate("HH:mm");
            execSql += " and a.dis_num>0 and a.is_dis=0 and ? between a.begin_time and a.end_time limit ?,?";
            return baseDao.query(execSql, new Object[]
            { id, curTime,currentPage, pageSize}, FreshIndexExt.class);
        case 2:
            Integer weekDay = Utils.getWeekDay();
            weekDay=0;
            execSql += " and a.is_sale=0 and a.sale_day=? limit ?,?";
            return baseDao.query(execSql, new Object[]
            { id, weekDay,currentPage, pageSize}, FreshIndexExt.class);
        case 3:
            execSql += " and a.fresh_type=1 limit ?,?";
            return baseDao.query(execSql, new Object[]
            { id,currentPage, pageSize}, FreshIndexExt.class);
        case 4:
            execSql += " and a.add_date >= ?";
            return baseDao.query(execSql, new Object[]
            { id, Utils.getCurDate("yyyy-MM-dd 00:00:00") },
            FreshIndexExt.class);
        default:
            return baseDao.query(execSql, new Object[]
            { id }, FreshIndexExt.class);
        }
    }
}
