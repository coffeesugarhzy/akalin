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
import com.sunspot.pojo.FarmProduce;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.FarmProduceExt;
import com.sunspot.pojoext.FarmProduceIndexExt;
import com.sunspot.service.FarmService;

/**
 * 农副业务层实现
 * 
 * @author LuoAnDong
 * 
 * @param <T>
 */
@Service
public class FarmServiceImpl implements FarmService

{

    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(FarmServiceImpl.class);

    /**
     * 持久层操作类
     */
    @Autowired
    private BaseDao baseDao;

    // 统计查询
    private static final String COUNT_FARM = "select count(1) from farm_produce a where a.of_shop_id=? and a.farm_name like ?";

    // 无条件统计查询
    private static final String COUNT_FARM_NOCOND = "select count(1) from farm_produce where of_shop_id=?";

    // 查询
    private static final String QUERY_FARM = "select a.farm_id,a.of_shop_id,a.logo,a.farm_name,a.type_name,a.marks,a.price,a.spec,a.produce_code,a.ground_time,a.add_date,a.update_date,a.remarks,a.is_sale,a.sale_day,a.sale_price,a.is_dis,a.begin_time,a.end_time,a.dis_price,a.dis_num,a.order_num from farm_produce a where of_shop_id=? and a.farm_name like ? order by a.add_date desc limit ?,?";

    // 根椐ID查询
    private static final String QUERY_FARM_ID = "select a.farm_id,a.of_shop_id,a.logo,a.farm_name,a.type_name,a.marks,a.price,a.spec,a.produce_code,a.ground_time,a.add_date,a.update_date,a.remarks,a.is_sale,a.sale_day,a.sale_price,a.is_dis,a.begin_time,a.end_time,a.dis_price,a.dis_num,a.order_num from farm_produce a where a.farm_id =?";

    // 无条件查询
    private static final String QUERY_FARM_NOCOND = "select a.farm_id,a.of_shop_id,a.logo,a.farm_name,a.type_name,a.marks,a.price,a.spec,a.produce_code,a.ground_time,a.add_date,a.update_date,a.remarks,a.is_sale,a.sale_day,a.sale_price,a.is_dis,a.begin_time,a.end_time,a.dis_price,a.dis_num,a.order_num from farm_produce a where of_shop_id=? order by a.add_date desc limit ?,?";

    /**
     * 分页查询管理，默认为15条为一页
     * 
     * @param list 查询对象
     * @param userId 用户ID
     * @param keyword 查询条件
     */
    public void queryPage(DataGridModel<FarmProduce> list, String userId,
            String keyword)
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
            { userId, keywordsFormat };

            queryParams = new Object[]
            { userId, keywordsFormat, list.getCurNum(), list.getRows() };

            countSql = COUNT_FARM;
            querySql = QUERY_FARM;

        }
        else
        {
            countParams = new Object[]
            { userId };

            queryParams = new Object[]
            { userId, list.getCurNum(), list.getRows() };

            countSql = COUNT_FARM_NOCOND;
            querySql = QUERY_FARM_NOCOND;
        }

        int total = baseDao.queryForIntPage(countSql, countParams);

        // 设置统计数据
        list.setCount(total);

        // 设置查询结果
        list.setContent(baseDao.query(querySql, queryParams, FarmProduce.class));
    }

    /**
     * 通过id查询实体
     */
    public FarmProduce queryById(String id)
    {
        return baseDao.queryForObject(QUERY_FARM_ID, new Object[]
        { id }, FarmProduce.class);
    }

    /**
     * 删除
     */
    public void delete(String id)
    {

        baseDao.delHQL("delete from FarmProduce as f where f.farmId=?",
                new String[]
                { id });

        runLog.info("delete farmProduce,id=" + id);
    }

    /**
     * 添加实体
     */
    public void add(HttpServletRequest request, FarmProduce farmProduce)
    {
        // 上传文件
        farmProduce.setLogo(Utils.uploadFile(request));
        farmProduce.setAddDate(Utils.getCurDate("yyyy-MM-dd HH:mm:ss"));
        baseDao.add(farmProduce);
        runLog.info("add farmProduce,id=" + farmProduce.getFarmId());
    }

    /**
     * 修改实体
     */
    public void modify(HttpServletRequest request, FarmProduce farmProduce)
    {
        FarmProduce tempfarmProduce = queryById(farmProduce.getFarmId());

        // 上传文件
        farmProduce.setLogo(Utils.uploadFile(request));

        if (StringUtils.isNotBlank(farmProduce.getLogo()))
        {
            if (StringUtils.isNotBlank(tempfarmProduce.getLogo()))
            {
                FileUtils.removeFile(request.getSession().getServletContext()
                        .getRealPath(tempfarmProduce.getLogo()));
            }
        }
        else
        {
            farmProduce.setLogo(tempfarmProduce.getLogo());
        }

        farmProduce.setUpdateDate(Utils.getCurDate("yyyy-MM-dd HH:mm:ss"));
        farmProduce.setAddDate(tempfarmProduce.getAddDate());

        farmProduce.setIsSale(tempfarmProduce.getIsSale());
        farmProduce.setSaleDay(tempfarmProduce.getSaleDay());
        farmProduce.setSalePrice(tempfarmProduce.getSalePrice());
        farmProduce.setIsDis(tempfarmProduce.getIsDis());
        farmProduce.setBeginTime(tempfarmProduce.getBeginTime());
        farmProduce.setEndTime(tempfarmProduce.getEndTime());
        farmProduce.setDisPrice(tempfarmProduce.getDisPrice());
        farmProduce.setDisNum(tempfarmProduce.getDisNum());
        farmProduce.setOrderNum(tempfarmProduce.getOrderNum());

        baseDao.update(farmProduce);

        runLog.info("modify farmProduce,id=" + farmProduce.getFarmId());
    }

    /**
     * 设置特价
     */
    public void setSale(FarmProduce farmProduce)
    {
        FarmProduce updateFarmProduce = queryById(farmProduce.getFarmId());
        updateFarmProduce.setIsSale(farmProduce.getIsSale());
        updateFarmProduce.setSaleDay(farmProduce.getSaleDay());
        updateFarmProduce.setSalePrice(farmProduce.getSalePrice());
        updateFarmProduce.setIsDis(farmProduce.getIsDis());
        updateFarmProduce.setBeginTime(farmProduce.getBeginTime());
        updateFarmProduce.setEndTime(farmProduce.getEndTime());
        updateFarmProduce.setDisPrice(farmProduce.getDisPrice());
        updateFarmProduce.setDisNum(farmProduce.getDisNum());
        baseDao.update(updateFarmProduce);
        runLog.info("set farmProduce sale,id=" + farmProduce.getFarmId());
    }

    /**
     * 根据订单查询农副产品　
     * 
     * @param orderId
     * @return
     * @author LuoAnDong
     */
    @Override
    public List<FarmProduceExt> queryFarmByOrder(String orderId)
    {
        if (StringUtils.isBlank(orderId))
            return null;
        try
        {
            List<FarmProduceExt> cbList = baseDao
                    .query("select farm_name, price , od.num as numext from farm_produce c ,(select cookbook_id , num from orders_detail where order_id=?) od where c.farm_id=od.cookbook_id",
                            new Object[]
                            { orderId }, FarmProduceExt.class);
            ;
            return cbList;
        }
        catch (Exception e)
        {
            runLog.error(e.getMessage());
        }
        return null;
    }

    
    // 统计所有
    private static final String COUNT_ALL_FRAM = "select count(1) from farm_produce a left join shop b on a.of_shop_id=b.shop_id left join area c on b.of_area_id = c.area_id";

    // 查询所有
    private static final String QUERY_ALL_FRAM = "select a.farm_id, a.add_date,a.remarks, a.begin_time, b.address,b.logo as shoplogo, b.telphone as shoptelphone , a.dis_num, a.dis_price, a.end_time, a.farm_name, a.ground_time, a.is_dis, a.is_sale, a.logo, a.marks, a.of_shop_id, a.order_num, a.price, a.produce_code, a.remarks, a.sale_day, a.sale_price, a.spec, a.type_name, a.update_date , b.shop_name as shopname, b.telphone, b.logo as shoplogo, c.area_name as areaname from farm_produce a left join shop b on a.of_shop_id = b.shop_id left join area c on b.of_area_id = c.area_id";

    
    /**
     * queryType <br/>1　根据类型名称查询<br/>2 套餐设置<br/>  3 天天特价查询<br/>
     * 7 表示为天天物价
     * @author LuoAnDong
     */
    @Override
    public void queryPage(DataGridModel<FarmProduceIndexExt> list, 
            Integer queryType,
            Integer key, 
            String value)
    { 
        Object[] countParams = null;
        Object[] queryParams = null;
        String countSql = null;
        String querySql = null;

        countSql = COUNT_ALL_FRAM;
        querySql = QUERY_ALL_FRAM;
        
        
        /**
         * 天天特价
         */
        if(key != null && key == 7){
            Integer weekDay = Utils.getWeekDay();
            countSql += " where a.is_sale=0 and a.sale_day=" + weekDay + " and a.farm_name like ?";
            querySql += " where a.is_sale=0 and a.sale_day=" + weekDay + " and a.farm_name like ? limit ?,?";
            countParams = new Object[]{ StringUtils.isBlank(value)?"%":Utils.getKeyword(value) }; 
            queryParams = new Object[]{ StringUtils.isBlank(value)?"%":Utils.getKeyword(value), list.getCurNum(), list.getRows()};
            int total = baseDao.queryForIntPage(countSql, countParams); 
            list.setCount(total); 
            list.setContent(baseDao.query(querySql, queryParams,FarmProduceIndexExt.class));
       
            return ; 
        }
         
        if(key !=null){
            switch (queryType)
            {
                case 1:
                    countSql += " where a.type_name=0";
                    querySql += " where a.type_name=0";
                    break; 
                case 2:
                    countSql += " where a.farm_type=1";
                    querySql += " where a.farm_type=1";
                    break; 
                default:
                    return;
    
            }
        } 
 
        if (null != key && StringUtils.isNotBlank(value))
        {
            switch (key)
            {  
            // 菜谱搜索
            case 1:
                value = Utils.getKeyword(value);
                countSql += " and a.FARM_NAME like ?";
                querySql += " and a.FARM_NAME like ? order by a.add_date desc limit ?,?";
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
                    querySql += (3 == queryType) ? " order by a.sale_price limit ?,?": " order by a.price asc limit ?,?";
                }

                if ("htl".equals(value))
                {
                    querySql += (3 == queryType) ? " order by a.sale_price limit ?,?" : " order by a.price desc limit ?,?";
                }

                if ("0".equals(value))
                {
                    countSql += (3 == queryType) ? " and a.sale_price <= 30" : " and a.price <= 30";
                    querySql += (3 == queryType) ? " and a.sale_price <= 30 order by a.sale_price limit ?,?" : " and a.price <= 30 order by a.price limit ?,?";
                }

                if ("30".equals(value))
                {
                    countSql += (3 == queryType) ? " and a.sale_price between 31 and 50" : " and a.price between 31 and 50";
                    querySql += (3 == queryType) ? " and a.sale_price between 31 and 50 order by a.sale_price limit ?,?" : " and a.price between 31 and 50 order by a.price limit ?,?";
                }

                if ("50".equals(value))
                {
                    countSql += (3 == queryType) ? " and a.sale_price between 51 and 100" : " and a.price between 51 and 100";
                    querySql += (3 == queryType) ? " and a.sale_price between 51 and 100 order by a.sale_price limit ?,?" : " and a.price between 51 and 100 order by a.price limit ?,?";
                }

                if ("100".equals(value))
                {
                    countSql += (3 == queryType) ? " and a.sale_price>100" : " and a.price>100";
                    querySql += (3 == queryType) ? " and a.sale_price>100 order by a.sale_price limit ?,?" : " and a.price>100 order by a.price limit ?,?";
                }
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

        int total = (null == countParams) ? baseDao.queryForIntPage(countSql): baseDao.queryForIntPage(countSql, countParams);

        // 设置统计数据
        list.setCount(total);

        // 设置查询结果
        list.setContent((null == queryParams) ? baseDao.query(querySql,FarmProduceIndexExt.class) : baseDao.query(querySql, queryParams,FarmProduceIndexExt.class));
         
    }

    /**
     * 根据id查询出农副产品的详情 
     * @param farmId
     * @return
     * @author LuoAnDong
     */
    @Override
    public FarmProduceIndexExt queryIndexById(String farmId)
    { 
        return baseDao.queryForObject(QUERY_ALL_FRAM+" where farm_id=?", 
                new Object[]{farmId}, 
                FarmProduceIndexExt.class);
    }

    /**
     * 根据店铺查询农副产品信息，主要用于放置于店铺首页中<br/>首页显示条数以默认4条显示，<br/>提高店铺首页
     * 进入时，图片加载的速度
     * @param shopId 店铺id
     * @param keyword 关键词，用于搜索
     * @param currentSize 当前条数
     * @param shopPageSize 店铺首页显示条数，默认为4条
     * @return
     * @author LuoAnDong
     */
    @Override
    public List<FarmProduceIndexExt> queryByPage(String shopId, 
            String keyword,
            int currentSize, 
            int shopPageSize)
    { 
        if(StringUtils.isBlank(keyword)) keyword = "%" ;
        return baseDao.query(QUERY_ALL_FRAM+" where a.of_shop_id=? and a.farm_name like ? limit ?,?", new Object[]{shopId,keyword,currentSize,shopPageSize}, FarmProduceIndexExt.class) ;  
    }
 
}
