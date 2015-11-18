package com.sunspot.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.common.FileUtils;
import com.sunspot.common.GraphicImage;
import com.sunspot.common.Utils;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.Cookbook;
import com.sunspot.pojoext.CookbookExt;
import com.sunspot.pojoext.CookbookIndexExt;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.ShopExt;
import com.sunspot.service.CookbookService;

/**
 * 家宴业务层实现
 * 
 * @author LuoAnDong
 */
@Service
public class CookbookServiceImpl implements CookbookService

{

    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(CookbookServiceImpl.class);

    /**
     * 持久层操作类
     */
    @Autowired
    private BaseDao baseDao;

    // 统计查询
    private static final String COUNT_COOKBOOK = "select count(1) from cookbook a where a.cook_type=? and a.of_shop_id=? and a.cook_name like ?";

    // 无条件统计查询
    private static final String COUNT_COOKBOOK_NOCOND = "select count(1) from cookbook where cook_type=? and of_shop_id=?";

    // 查询
    private static final String QUERY_COOKBOOK = "select a.cookbooks_id,a.logo,a.of_shop_id,a.cook_name,a.cook_type,a.marks,a.price,a.type_name,a.suggest,a.add_date,a.update_date,a.remarks,a.is_sale,a.sale_day,a.sale_price,a.is_dis,a.begin_time,a.end_time,a.dis_price,a.dis_num,a.order_num,a.is_shel,t.typename cookbook_type  from cookbook a LEFT JOIN cookbook_type t on a.cookbook_type=t.TYPE_ID where and cook_type=? and of_shop_id=? and a.cook_name like ? order by a.add_date desc limit ?,?";

    // 根椐ID查询
    private static final String QUERY_COOKBOOK_ID = "select a.cookbooks_id,a.logo,a.of_shop_id,a.cook_name,a.cook_type,a.marks,a.price,a.type_name,a.suggest,a.add_date,a.update_date,a.remarks,a.is_sale,a.sale_day,a.sale_price,a.is_dis,a.begin_time,a.end_time,a.dis_price,a.dis_num,a.order_num,a.is_shel,cookbook_type from cookbook a where a.cookbooks_id =?";

    // 无条件查询
    private static final String QUERY_COOKBOOK_NOCOND = "select a.cookbooks_id,a.logo,a.of_shop_id,a.cook_name,a.cook_type,a.marks,a.price,a.type_name,a.suggest,a.add_date,a.update_date,a.remarks,a.is_sale,a.sale_day,a.sale_price,a.is_dis,a.begin_time,a.end_time,a.dis_price,a.dis_num,a.order_num,a.is_shel,t.typename cookbook_type from cookbook a LEFT JOIN cookbook_type t on a.cookbook_type=t.TYPE_ID where cook_type=? and of_shop_id=? order by a.add_date desc limit ?,?";

    // 统计所有
    private static final String COUNT_ALL_COOKBOOK = "select count(1) from cookbook a left join shop b on a.of_shop_id=b.shop_id left join area c on b.of_area_id = c.area_id";

    // 根椐ID查询菜谱
    private static final String QUERY_COOKBOOK_INDEXID = "select a.cookbooks_id,a.logo,a.of_shop_id,a.cook_name,a.cook_type,a.marks,a.price,a.type_name,a.suggest,a.add_date,a.update_date,a.remarks,a.is_sale,a.sale_day,a.sale_price,a.is_dis,a.begin_time,a.end_time,a.dis_price,a.dis_num,a.order_num,a.is_shel,b.shop_name shopName,b.telphone,b.logo shopLogo,c.area_name,a.is_shel,b.address,b.telphone areaName from cookbook a left join shop b on a.of_shop_id=b.shop_id left join area c on b.of_area_id = c.area_id where a.cookbooks_id=?";

    // 根椐店铺ID查询菜谱
    private static final String QUERY_COOKBOOK_SHOPID = "select a.cookbooks_id,a.logo,a.of_shop_id,a.cook_name,a.cook_type,a.marks,a.price,a.type_name,a.suggest,a.is_sale,a.sale_day,a.sale_price,a.is_dis,a.dis_price,a.begin_time,a.end_time,a.dis_num,a.order_num,a.is_shel,b.shop_name shopName,b.telphone,b.logo shopLogo,c.area_name areaName from cookbook a left join shop b on a.of_shop_id=b.shop_id left join area c on b.of_area_id = c.area_id where a.of_shop_id=? and a.is_shel=1 ";

    // 查询所有
    private static final String QUERY_ALL_COOKBOOK = "select a.cookbooks_id,a.logo,a.of_shop_id,a.cook_name,a.cook_type,a.marks,a.price,a.type_name,a.suggest,a.is_sale,a.sale_day,a.sale_price,a.is_dis,a.dis_price,a.begin_time,a.end_time,a.dis_num,a.order_num,a.is_shel,b.shop_name shopName,b.telphone,b.logo shopLogo,c.area_name areaName from cookbook a left join shop b on a.of_shop_id=b.shop_id left join area c on b.of_area_id = c.area_id";

  
    /**
     * 分页查询管理，默认为15条为一页
     * 
     * @param list 查询对象
     * @param userId 用户ID
     * @param cookType 家宴类型
     * @param keyword 查询条件
     */
    public void queryPage(DataGridModel<Cookbook> list, String userId,
            Integer cookType, String keyword)
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
            { cookType, userId, keywordsFormat };

            queryParams = new Object[]
            { cookType, userId, keywordsFormat, list.getCurNum(),
                    list.getRows() };

            countSql = COUNT_COOKBOOK;
            querySql = QUERY_COOKBOOK;

        }
        else
        {
            countParams = new Object[]
            { cookType, userId };

            queryParams = new Object[]
            { cookType, userId, list.getCurNum(), list.getRows() };

            countSql = COUNT_COOKBOOK_NOCOND;
            querySql = QUERY_COOKBOOK_NOCOND;
        }

        int total = baseDao.queryForIntPage(countSql, countParams);

        // 设置统计数据
        list.setCount(total);

        // 设置查询结果
        list.setContent(baseDao.query(querySql, queryParams, Cookbook.class));
    }

    /**
     * 分页查询
     * 
     * @param list 查询结果
     * @param key 键
     * @param value 值
     */
    /**
     * 分页查询
     * 
     * @param list 查询结果
     * @param queryType 查询类型 1:查询菜谱 2:查询套餐 3：天天特价
     * @param key 键
     * @param value 值
     */
    public void queryPage(DataGridModel<CookbookIndexExt> list,
            Integer queryType, Integer key, String value)
    {
        Object[] countParams = null;
        Object[] queryParams = null;
        String countSql = null;
        String querySql = null;

        countSql = COUNT_ALL_COOKBOOK;
        querySql = QUERY_ALL_COOKBOOK;

        switch (queryType)
        {
        case 1:
            countSql += " where a.cook_type=0 and a.is_shel=1";
            querySql += " where a.cook_type=0 and a.is_shel=1";
            break;
        case 2:
            countSql += " where a.cook_type=1 and a.is_shel=1";
            querySql += " where a.cook_type=1 and a.is_shel=1";
            break;
        case 3:
            Integer weekDay = Utils.getWeekDay();
            countSql += " where a.is_sale=0 and a.is_shel=1 and a.sale_day=" + weekDay;
            querySql += " where a.is_sale=0 and a.is_shel=1 and a.sale_day=" + weekDay;
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
                countSql += " and a.cook_name like ?";
                querySql += " and a.cook_name like ? order by a.add_date desc limit ?,?";
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
                    querySql += (3 == queryType) ? " order by a.sale_price limit ?,?"
                            : " order by a.price limit ?,?";
                }

                if ("htl".equals(value))
                {
                    querySql += (3 == queryType) ? " order by a.sale_price limit ?,?"
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
                countSql += " and a.cook_type=1";
                querySql += " and a.cook_type=1 order by a.sale_price limit ?,?";
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
        list.setContent((null == queryParams) ? baseDao.query(querySql,
                CookbookIndexExt.class) : baseDao.query(querySql, queryParams,
                CookbookIndexExt.class));
    }

    /**
     * 分页查询限时抢购
     * 
     * @param list 查询结果
     */
    public void queryDisPage(DataGridModel<CookbookIndexExt> list)
    {

        String curHour = Utils.getCurDate("HH:mm");
        String countSql = COUNT_ALL_COOKBOOK
                + " where a.is_dis=0 and ? between a.begin_time and a.end_time";
        String querySql = QUERY_ALL_COOKBOOK
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
                CookbookIndexExt.class));
    }

    /**
     * 通过id查询实体
     */
    public Cookbook queryById(String id)
    {
        return baseDao.queryForObject(QUERY_COOKBOOK_ID, new Object[]
        { id }, Cookbook.class);
    }

    /**
     * 通过id查询实体
     */
    public CookbookIndexExt queryByIndexId(String id)
    {
        return baseDao.queryForObject(QUERY_COOKBOOK_INDEXID, new Object[]
        { id }, CookbookIndexExt.class);
    }

    /**
     * 通过店铺id和类型查询菜谱
     */
    public List<CookbookIndexExt> queryShopCbId(Integer queryType, String id,
            String value)
    {
        String execSql = QUERY_COOKBOOK_SHOPID;
        switch (queryType)
        {
        case 0:
            String tempVal = value;
            if (StringUtils.isNotBlank(value))
            {
                tempVal = Utils.getKeyword(Utils.transformChar(value));
                execSql += " and a.cook_name like ?";
                return baseDao.query(execSql, new Object[]
                { id, tempVal }, CookbookIndexExt.class);
            }
            else
            {
                return baseDao.query(execSql, new Object[]
                { id }, CookbookIndexExt.class);
            }
        case 1:
            String curTime = Utils.getCurDate("HH:mm");
            execSql += " and a.dis_num>0 and a.is_dis=0 and ? between a.begin_time and a.end_time";
            return baseDao.query(execSql, new Object[]
            { id, curTime }, CookbookIndexExt.class);
        case 2:
            Integer weekDay = Utils.getWeekDay();
            execSql += " and a.is_sale=0 and a.sale_day=?";
            return baseDao.query(execSql, new Object[]
            { id, weekDay }, CookbookIndexExt.class);
        case 3:
            execSql += " and a.cook_type=1";
            return baseDao.query(execSql, new Object[]
            { id }, CookbookIndexExt.class);
        case 4:
            execSql += " and a.add_date >= ?";
            return baseDao.query(execSql, new Object[]
            { id, Utils.getCurDate("yyyy-MM-dd 00:00:00") },
                    CookbookIndexExt.class);
        default:
            return baseDao.query(execSql, new Object[]
            { id }, CookbookIndexExt.class);
        }
    }

    /**
     * 删除
     */
    public void delete(String id)
    {

        baseDao.delHQL("delete from Cookbook as c where c.cookbooksId=?",
                new String[]
                { id });

        runLog.info("delete cookbook,id=" + id);
    }

    /**
     * 添加实体
     */
    public void add(HttpServletRequest request, Cookbook cookbook)
    {
        // 上传文件
    	String logoUrl=Utils.uploadFile(request);
        cookbook.setLogo(logoUrl);
    	String savePath = request.getSession().getServletContext().getRealPath("/");
    	logoUrl=savePath+logoUrl;
    	GraphicImage.graphicsGeneration(GraphicImage.imageWidth, GraphicImage.imageHeight,logoUrl);
        cookbook.setAddDate(Utils.getCurDate("yyyy-MM-dd HH:mm:ss"));
        baseDao.add(cookbook);
        runLog.info("add Cookbook,id=" + cookbook.getCookbooksId());
    }

    /**
     * 修改实体
     */
    public void modify(HttpServletRequest request, Cookbook cookbook)
    {
        Cookbook tempCookbook = queryById(cookbook.getCookbooksId());

        // 上传文件
        cookbook.setLogo(Utils.uploadFile(request));

        if (StringUtils.isNotBlank(cookbook.getLogo()))
        {
        	/*设置上传固定大小图片*/
        	String logoUrl=cookbook.getLogo();
        	String savePath = request.getSession().getServletContext().getRealPath("/");
        	logoUrl=savePath+logoUrl;
        	GraphicImage.graphicsGeneration(GraphicImage.imageWidth, GraphicImage.imageHeight,logoUrl);

            if (StringUtils.isNotBlank(tempCookbook.getLogo()))
            {
                FileUtils.removeFile(request.getSession().getServletContext()
                        .getRealPath(tempCookbook.getLogo()));
            }
        }
        else
        {
            cookbook.setLogo(tempCookbook.getLogo());
        }

        cookbook.setUpdateDate(Utils.getCurDate("yyyy-MM-dd HH:mm:ss"));
        cookbook.setAddDate(tempCookbook.getAddDate());

        cookbook.setIsSale(tempCookbook.getIsSale());
        cookbook.setSaleDay(tempCookbook.getSaleDay());
        cookbook.setSalePrice(tempCookbook.getSalePrice());
        cookbook.setIsDis(tempCookbook.getIsDis());
        cookbook.setBeginTime(tempCookbook.getBeginTime());
        cookbook.setEndTime(tempCookbook.getEndTime());
        cookbook.setDisPrice(tempCookbook.getDisPrice());
        cookbook.setDisNum(tempCookbook.getDisNum());
        cookbook.setOrderNum(tempCookbook.getOrderNum());
        
        baseDao.update(cookbook);

        runLog.info("modify cookbook,id=" + cookbook.getCookbooksId());
    }

    /**
     * 设置特价
     */
    public void setSale(Cookbook cookbook)
    {
        Cookbook updateCookbook = queryById(cookbook.getCookbooksId());
        updateCookbook.setIsSale(cookbook.getIsSale());
        updateCookbook.setSaleDay(cookbook.getSaleDay());
        updateCookbook.setSalePrice(cookbook.getSalePrice());
        updateCookbook.setIsDis(cookbook.getIsDis());
        updateCookbook.setBeginTime(cookbook.getBeginTime());
        updateCookbook.setEndTime(cookbook.getEndTime());
        updateCookbook.setDisPrice(cookbook.getDisPrice());
        updateCookbook.setDisNum(cookbook.getDisNum());
        baseDao.update(updateCookbook);
        runLog.info("set cookbook sale,id=" + cookbook.getCookbooksId());
    }

    /**
     * 通过id 查询出家宴
     * 
     * @param orderId
     * @return
     * @author LuoAnDong
     */
    @Override
    public List<CookbookExt> queryCookBookByOrder(String orderId)
    {
        if (StringUtils.isBlank(orderId))
            return null;
        try
        {
            List<CookbookExt> cbList = baseDao
                    .query("select cook_name, price , od.num as numext from cookbook c ,(select cookbook_id , num from orders_detail where order_id=?) od where c.cookbooks_id=od.cookbook_id",
                            new Object[]
                            { orderId }, CookbookExt.class); 
            return cbList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 商家得到商品
     * @param shopExtList  店铺集合
     * @param num  商品列表数量
     * @return 商品列表
     */
	@Override
	public DataGridModel<CookbookIndexExt> queryGoodsByShopList(
			List<ShopExt> shopExtList, int num) {
		
		DataGridModel<CookbookIndexExt>  list = new DataGridModel<CookbookIndexExt>() ;  
		List<CookbookIndexExt> content = new ArrayList<CookbookIndexExt>() ; 
		String sql = QUERY_ALL_COOKBOOK +" where of_shop_id=? LIMIT 0,?"; 
		for(ShopExt shop : shopExtList){ 
			List<CookbookIndexExt> shopContent = baseDao.query(sql, new Object[]{shop.getShopId(),num}, CookbookIndexExt.class) ; 
			runLog.info("sql = " + sql + " , shopContent = " + shopContent + " , distance = " + shop.getDistance()) ; 
			for(CookbookIndexExt cbi : shopContent){
				cbi.setDistance(Double.parseDouble(shop.getDistance())) ; 
			}
			content.addAll(shopContent) ; 
		}
		
		list.setContent(content) ; 
		return list ;
	}

	
	@Override
	public List<CookbookIndexExt> queryCookbookShowIndex(String shopId,String keyword, String type) {
		String execSql = QUERY_COOKBOOK_SHOPID;
        if(StringUtils.isBlank(keyword)&&StringUtils.isBlank(type)){
        	 return baseDao.query(execSql,  new Object[]{shopId}, CookbookIndexExt.class);
        }else if(StringUtils.isNotEmpty(keyword)&&StringUtils.isEmpty(type)){
            execSql += " and a.cook_name like ?";
            return baseDao.query(execSql, new Object[]{shopId, Utils.getKeyword(keyword) }, CookbookIndexExt.class);
          
        }else if(StringUtils.isEmpty(keyword)&&StringUtils.isNotEmpty(type)){
        	if("default".equals(type)){
        		 execSql += "  and a.cookbook_type=''";
        		 return baseDao.query(execSql, new Object[]{shopId}, CookbookIndexExt.class);
        	}
        	 execSql += " and a.cookbook_type =?";
        	 return baseDao.query(execSql, new Object[]{shopId,type}, CookbookIndexExt.class);
        }else{
        	execSql += " and a.cook_name like ?";
        	if("default".equals(type)){
        		 execSql += " and a.cookbook_type=''";
        		 return baseDao.query(execSql, new Object[]{shopId, Utils.getKeyword(keyword)}, CookbookIndexExt.class);
        	}else{
        		execSql += " and a.cookbook_type =?";
        		 return baseDao.query(execSql, new Object[]{shopId, Utils.getKeyword(keyword),type}, CookbookIndexExt.class);
        	}
        }
	}
}
