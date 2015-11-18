package com.sunspot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import com.sunspot.common.DateUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunspot.common.Utils;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.CustomInfo;
import com.sunspot.pojo.Orders;
import com.sunspot.pojo.OrdersDetail;
import com.sunspot.pojo.PayLog;
import com.sunspot.pojo.ReturnMoney;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.OrdersDetailExt;
import com.sunspot.pojoext.OrdersExt;
import com.sunspot.pojoext.OrdersIndexExt;
import com.sunspot.service.CommentsService;
import com.sunspot.service.OrdersService;


/**
 * 店铺实现
 * 
 * @author LuoAnDong
 * 
 */
@Service
public class OrdersServiceImpl implements OrdersService
{
    /**
     * 引入持久层操作类
     */
    @Autowired
    private BaseDao baseDao;
    
    @Autowired
    private CommentsService commentsService;
    
    Orders orders = new Orders();
    
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(AreaServiceImpl.class);

    /**
     * 分页查询订单扩展类基本信息
     * 
     * @param list
     * @param keyword
     * @return
     * @author LuoAnDong
     */
    public void queryByShop(DataGridModel<OrdersExt> list, String keyword,
            int status)
    {
        String countsql = "select count(*) from orders a left join custom_info b on a.of_custom_id = b.custom_id where a.order_type=0";
        String querysql = "select a.order_id,a.orders_code,a.of_shop_id,a.return_money,a.book_date,a.status,a.add_date,a.update_date,a.consume_money,a.order_money,a.is_comment,a.remark,b.custom_name customerName,b.telphone customerPhone from orders a left join custom_info b on a.of_custom_id = b.custom_id where a.order_type=0";
        Object[] countParam = null;
        Object[] queryParam = null;

        if (StringUtils.isNotBlank(keyword) && 0 <= status)
        {
            String keyfmt = Utils.getKeyword(keyword);
            countsql += " and a.status=? and (b.telphone like ? or b.custom_name like ? or a.orders_code like ? )";
            querysql += " and a.status=? and (b.telphone like ? or b.custom_name like ? or a.orders_code like ?) order by a.add_date desc limit ?,?";
            countParam = new Object[]
            { status, keyfmt, keyfmt, keyfmt };
            queryParam = new Object[]
            { status, keyfmt, keyfmt, keyfmt, list.getCurNum(), list.getRows() };
        }
        else if (StringUtils.isNotBlank(keyword))
        {
            String keyfmt = Utils.getKeyword(keyword);
            countsql += " and (b.telphone like ? or b.custom_name like ? or a.orders_code like ? )";
            querysql += " and (b.telphone like ? or b.custom_name like ? or a.orders_code like ?) order by a.update_date desc,a.add_date desc limit ?,?";
            countParam = new Object[]
            { keyfmt, keyfmt, keyfmt };
            queryParam = new Object[]
            { keyfmt, keyfmt, keyfmt, list.getCurNum(), list.getRows() };
        }
        else if (0 <= status)
        {
            countsql += " and a.status=?";
            querysql += " and a.status=? order by a.add_date desc limit ?,?";
            countParam = new Object[]
            { status };
            queryParam = new Object[]
            { status, list.getCurNum(), list.getRows() };
        }
        else
        {
            querysql += " order by a.add_date desc limit ?,?";
            queryParam = new Object[]
            { list.getCurNum(), list.getRows() };
        }

        int total = (null == countsql) ? baseDao.queryForIntPage(countsql)
                : baseDao.queryForIntPage(countsql, countParam);

        list.setCount(total);

        // 查询返利
        List<OrdersExt> ordersExtList = baseDao.query(querysql, queryParam,
                OrdersExt.class);
        list.setContent(ordersExtList);
    }

    /**
     * 分页查询订单扩展类基本信息
     * 
     * @param list
     * @param keyword
     * @return
     * @author LuoAnDong
     */
    public void queryTakeout(DataGridModel<OrdersExt> list, String keyword,
            int status)
    {
        String countsql = "select count(*) from orders a left join custom_info b on a.of_custom_id = b.custom_id where a.order_type>0";
        String querysql = "select a.order_id,a.of_shop_id,a.orders_code,a.order_type,a.return_money,a.book_date,a.status,a.add_date,a.update_date,a.send_date,a.pay_type,a.is_pay,a.pay_money,a.send_status,a.send_addr,a.order_money,a.is_comment,a.remark,b.custom_name customerName,b.telphone customerPhone from orders a left join custom_info b on a.of_custom_id = b.custom_id where a.order_type>0";
        Object[] countParam = null;
        Object[] queryParam = null;

        switch (status)
        {
        case 0:
            countsql += " and a.status<3 and a.pay_type=0 and a.is_pay=0 and a.send_status=0";
            querysql += " and a.status<3 and a.pay_type=0 and a.is_pay=0 and a.send_status=0";
            break;
        case 1:
            countsql += " and a.status<3 and (a.pay_type=1 or a.is_pay=1) and a.send_status=0";
            querysql += " and a.status<3 and (a.pay_type=1 or a.is_pay=1) and a.send_status=0";
            break;
        case 2:
            countsql += " and a.status<3 and (a.pay_type=1 or a.is_pay=1) and a.send_status=1";
            querysql += " and a.status<3 and (a.pay_type=1 or a.is_pay=1) and a.send_status=1";
            break;
        case 3:
            countsql += " and a.status<3 and (a.pay_type=1 or a.is_pay=1) and a.send_status=2";
            querysql += " and a.status<3 and (a.pay_type=1 or a.is_pay=1) and a.send_status=2";
            break;
        case 4:
            countsql += " and a.status>=3";
            querysql += " and a.status>=3";
            break;
        }

        if (StringUtils.isNotBlank(keyword))
        {
            String keyfmt = Utils.getKeyword(keyword);
            countsql += " and (b.telphone like ? or b.custom_name like ? or a.orders_code like ?)";
            querysql += " and (b.telphone like ? or b.custom_name like ? or a.orders_code like ?) order by a.update_date desc,a.add_date desc limit ?,?";
            countParam = new Object[]
            { keyfmt, keyfmt, keyfmt };
            queryParam = new Object[]
            { keyfmt, keyfmt, keyfmt, list.getCurNum(), list.getRows() };
        }
        else
        {
            querysql += " order by a.add_date limit ?,?";
            queryParam = new Object[]
            { list.getCurNum(), list.getRows() };
        }

        int total = (null == countsql) ? baseDao.queryForIntPage(countsql)
                : baseDao.queryForIntPage(countsql, countParam);

        list.setCount(total);

        // 查询返利
        List<OrdersExt> ordersExtList = baseDao.query(querysql, queryParam,
                OrdersExt.class);
        list.setContent(ordersExtList);
    }

    /**
     * 分页查询订单扩展类基本信息
     * 
     * @param list
     * @param keyword
     * @return
     * @author LuoAnDong
     */
    public void queryByShop(DataGridModel<OrdersExt> list, String keyword,
            int status, String shopId)
    {
        String countsql = "select count(*) from orders a left join custom_info b on a.of_custom_id = b.custom_id where  a.of_shop_id=? and a.order_type=0";
        String querysql = "select a.order_id,a.orders_code,a.of_shop_id,a.book_date,a.status,a.add_date,a.update_date,a.consume_money,a.order_money,a.is_comment,a.remark,b.custom_name customerName,b.telphone customerPhone from orders a left join custom_info b on a.of_custom_id = b.custom_id where  a.of_shop_id=? and a.order_type=0";
        Object[] countParam = null;
        Object[] queryParam = null;

        if (StringUtils.isNotBlank(keyword) && 0 <= status)
        {
            String keyfmt = Utils.getKeyword(keyword);
            countsql += " and a.status=? and (b.telphone like ? or b.custom_name like ? or a.orders_code like ? )";
            querysql += " and a.status=? and (b.telphone like ? or b.custom_name like ? or a.orders_code like ?) order by a.update_date desc,a.add_date desc limit ?,?";
            countParam = new Object[]
            { shopId, status, keyfmt, keyfmt, keyfmt };
            queryParam = new Object[]
            { shopId, status, keyfmt, keyfmt, keyfmt, list.getCurNum(),
                    list.getRows() };
        }
        else if (StringUtils.isNotBlank(keyword))
        {
            String keyfmt = Utils.getKeyword(keyword);
            countsql += " and (b.telphone like ? or b.custom_name like ? or a.orders_code like ? )";
            querysql += " and (b.telphone like ? or b.custom_name like ? or a.orders_code like ?) order by a.add_date desc limit ?,?";
            countParam = new Object[]
            { shopId, keyfmt, keyfmt, keyfmt };
            queryParam = new Object[]
            { shopId, keyfmt, keyfmt, keyfmt, list.getCurNum(), list.getRows() };
        }
        else if (0 <= status)
        {
            countsql += " and a.status=?";
            querysql += " and a.status=? order by a.update_date desc,a.add_date desc limit ?,?";
            countParam = new Object[]
            { shopId, status };
            queryParam = new Object[]
            { shopId, status, list.getCurNum(), list.getRows() };
        }
        else
        {
            querysql += " order by a.add_date desc limit ?,?";
            countParam = new Object[]
            { shopId };
            queryParam = new Object[]
            { shopId, list.getCurNum(), list.getRows() };
        }

        int total = baseDao.queryForIntPage(countsql, countParam);

        list.setCount(total);

        // 查询返利
        List<OrdersExt> ordersExtList = baseDao.query(querysql, queryParam,
                OrdersExt.class);
        list.setContent(ordersExtList);
    }

    /**
     * 分页查询订单扩展类基本信息
     * 
     * @param list
     * @param keyword
     * @return
     * @author LuoAnDong
     */
    public void queryTakeout(DataGridModel<OrdersExt> list, String keyword,
            int status, String shopId)
    {
        String countsql = "select count(*) from orders a left join custom_info b on a.of_custom_id = b.custom_id where a.of_shop_id=? and a.order_type>0";
        String querysql = "select a.order_id,a.of_shop_id,a.orders_code,a.order_type,a.book_date,a.status,a.add_date,a.update_date,a.send_date,a.pay_type,a.is_pay,a.pay_money,a.send_status,a.send_addr,a.order_money,a.is_comment,a.remark,b.custom_name customerName,b.telphone customerPhone from orders a left join custom_info b on a.of_custom_id = b.custom_id where  a.of_shop_id=? and a.order_type>0";
        Object[] countParam = null;
        Object[] queryParam = null;

        switch (status)
        {
        case 0:
            countsql += " and a.status<3 and a.pay_type=0 and a.is_pay=0 and a.send_status=0";
            querysql += " and a.status<3 and a.pay_type=0 and a.is_pay=0 and a.send_status=0";
            break;
        case 1:
            countsql += " and a.status<3 and (a.pay_type=1 or a.is_pay=1) and a.send_status=0";
            querysql += " and a.status<3 and (a.pay_type=1 or a.is_pay=1) and a.send_status=0";
            break;
        case 2:
            countsql += " and a.status<3 and (a.pay_type=1 or a.is_pay=1) and a.send_status=1";
            querysql += " and a.status<3 and (a.pay_type=1 or a.is_pay=1) and a.send_status=1";
            break;
        case 3:
            countsql += " and a.status<3 and (a.pay_type=1 or a.is_pay=1) and a.send_status=2";
            querysql += " and a.status<3 and (a.pay_type=1 or a.is_pay=1) and a.send_status=2";
            break;
        case 4:
            countsql += " and a.status>=3";
            querysql += " and a.status>=3";
            break;
        }

        if (StringUtils.isNotBlank(keyword))
        {
            String keyfmt = Utils.getKeyword(keyword);
            countsql += " and (b.telphone like ? or b.custom_name like ? or a.orders_code like ?)";
            querysql += " and (b.telphone like ? or b.custom_name like ? or a.orders_code like ?) order by a.add_date desc limit ?,?";
            countParam = new Object[]
            { shopId, keyfmt, keyfmt, keyfmt };
            queryParam = new Object[]
            { shopId, keyfmt, keyfmt, keyfmt, list.getCurNum(), list.getRows() };
        }
        else
        {
            querysql += " order by a.add_date desc limit ?,?";
            countParam = new Object[]
            { shopId };
            queryParam = new Object[]
            { shopId, list.getCurNum(), list.getRows() };
        }

        int total = baseDao.queryForIntPage(countsql, countParam);

        list.setCount(total);

        // 查询返利
        List<OrdersExt> ordersExtList = baseDao.query(querysql, queryParam,
                OrdersExt.class);
        list.setContent(ordersExtList);
    }

    /**
     * 发货
     * 
     * @param orderId 订单ID
     * @param shopId 店铺ID
     */
    public int send(String orderId, String shopId)
    {
        int resultCode = 1;
        try
        {
            baseDao.update(
                    "update orders set send_status=1,update_date=? where of_shop_id=? and order_id=?",
                    new Object[]
                    { Utils.getNowDate(), shopId, orderId });
            resultCode = 0;
            commentsService.autoAddComment(orderId);//7天后自动评论
        }
        catch (Exception e)
        {
            return resultCode;
        }
        return resultCode;
    }

    
    /**
     * 发货后七天自动确认收货
     * 
     * @param orderId 订单ID
     * @param shopId 店铺ID
     */
    public void autoReceiveGoods(String orderId, String shopId) {
    	orders.setOrderId(orderId);
		orders.setOfShopId(shopId);
//		Date date = new Date();
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String time = format.format(date);
		System.out.println(DateUtil.getCurrentDateTime()+" 执行前的时间");
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				String orderId = orders.getOrderId();
				String shopId = orders.getOfShopId();
				Orders orders = baseDao.getByHibernate(Orders.class, orderId);
				if(orders.getSendStatus() == 1 && orders.getStatus() == 1){
			        try
			        {
			            baseDao.update(
			                    "update orders set send_status=2,status=2,update_date=? where of_shop_id=? and order_id=?",
			                    new Object[]
			                    { Utils.getNowDate(), shopId, orderId });
			            
			        }
			        catch (Exception e)
			        {
			           
			        }
				}
				System.out.println(DateUtil.getCurrentDateTime()+"执行后的时间");
				this.cancel();
			}
		}, 7*24*60*60*1000);//七天
		
	}
    
    /**
     * 退款
     * 
     * @param orderId
     * @return
     */
    public int returnmoney(String orderId)
    {
        int resultCode = 1;
        try
        {
            baseDao.update(
                    "update orders set status=4,update_date=? where order_id=?",
                    new Object[]
                    { Utils.getNowDate(), orderId });
            resultCode = 0;
        }
        catch (Exception e)
        {
            return resultCode;
        }
        return resultCode;
    }

    /**
     * 确认
     * 
     * @param orderId 订单ID
     * @param shopId 店铺ID
     */
    public int confirm(String orderId, String shopId)
    {
        int resultCode = 1;
        try
        {
            baseDao.update(
                    "update orders set status=1,update_date=? where of_shop_id=? and order_id=?",
                    new Object[]
                    { Utils.getNowDate(), shopId, orderId });
            resultCode = 0;
            commentsService.autoAddComment(orderId);//7天后自动评论
        }
        catch (Exception e)
        {
            return resultCode;
        }
        return resultCode;
    }

    /**
     * 消费
     * 
     * @param orderId 订单ID
     * @param shopId 店铺ID
     */
    public int consume(double consumeMoney, String orderId, String shopId)
    {
        int resultCode = 1;
        try
        {
            baseDao.update(
                    "update orders set order_money=?,consume_money=?,update_date=?,status=2 where of_shop_id=? and order_id=?",
                    new Object[]
                    { consumeMoney, consumeMoney, Utils.getNowDate(), shopId,
                            orderId });
            resultCode = 0;
        }
        catch (Exception e)
        {
            return resultCode;
        }
        return resultCode;
    }

    /**
     * 返利
     * 
     * @param orderId
     */
    @Transactional
    public void rebate(String orderId)
    {
        Orders orders = baseDao
                .queryForObject(
                        "select a.order_id,a.of_custom_id,a.order_type,a.status,a.is_pay,a.send_status,a.order_money from orders a where a.order_id=?",
                        new Object[]
                        { orderId }, Orders.class);
        if (null == orders)
        {
            return;
        }

        List<ReturnMoney> list = baseDao.query(
                "select begin_money,end_money,return_money from return_money",
                ReturnMoney.class);

        if (null == list || 0 == list.size())
        {
            return;
        }

        double returnMoney = countRebate(orders.getOrderMoney(), list);

        if ((0 == orders.getOrderType() && 2 == orders.getStatus())
                || (1 == orders.getIsPay() && 2 == orders.getSendStatus()))
        {
            baseDao.update("update orders set return_money=? where order_id=?",
                    new Object[]
                    { returnMoney, orderId });
            baseDao.update(
                    "update custom_info set balance=IFNULL(balance,0.00)+? where custom_id=?",
                    new Object[]
                    { returnMoney, orders.getOfCustomId() });
        }
    }

    private double countRebate(double orderMoney, List<ReturnMoney> list)
    {

        for (ReturnMoney returnMoney : list)
        {
            if (returnMoney.getBeginMoney() <= orderMoney
                    && orderMoney < returnMoney.getEndMoney())
            {
                return 0 < returnMoney.getReturnMoney() ? returnMoney
                        .getReturnMoney() : -1;
            }
        }
        return -1;
    }

    /**
     * 查询定单详细
     * 
     * @param orderId 订单ID
     * @return List<OrdersIndexExt>
     */
    public OrdersExt queryByDetail(String orderId)
    {

        OrdersExt ordersExt = baseDao
                .queryForObject(
                        "select a.order_id,a.of_shop_id,a.orders_code,a.consume_money,a.return_money,a.order_type,a.book_date,a.status,a.add_date,a.update_date,a.send_date,a.pay_type,a.is_pay,a.pay_money,a.send_status,a.send_addr,a.order_money,a.is_comment,a.remark,b.shop_name shopName,b.cost,b.address,b.telphone,b.marks,c.table_name tableName,c.table_volume tableVolume,d.custom_name customerName,d.telphone customerPhone from orders a left join shop b on a.of_shop_id=b.shop_id left join table_info c on a.of_table_id=c.table_info_id left join custom_info d on a.of_custom_id = d.custom_id where a.order_id=?",
                        new Object[]
                        { orderId }, OrdersExt.class);

        int orderType = ordersExt.getOrderType();
        switch (orderType)
        {
        case 1:
            ordersExt
                    .setOrderdetailsext(baseDao
                            .query("select a.detail_id,a.cookbook_id,a.sale_type,a.sale_price,a.price,a.num,b.logo cookbookLogo,b.cook_name cookName,b.type_name typeName,b.marks,b.cook_type cookType,b.suggest from orders_detail a left join cookbook b on a.cookbook_id=b.cookbooks_id where a.order_id=? order by a.sale_type",
                                    new Object[]
                                    { ordersExt.getOrderId() },
                                    OrdersDetailExt.class));
            break;
        case 2:
            ordersExt
                    .setOrderdetailsext(baseDao
                            .query("select a.detail_id,a.cookbook_id,a.sale_type,a.sale_price,a.price,a.num,b.logo cookbookLogo,b.fresh_name cookName,b.type_name typeName,b.marks,b.fresh_type cookType,b.suggest from orders_detail a left join fresh b on a.cookbook_id=b.fresh_id where a.order_id=? order by a.sale_type",
                                    new Object[]
                                    { ordersExt.getOrderId() },
                                    OrdersDetailExt.class));
            break;
        case 3:
            ordersExt
                    .setOrderdetailsext(baseDao
                            .query("select a.detail_id,a.cookbook_id,a.sale_type,a.sale_price,a.price,a.num,b.logo cookbookLogo,b.farm_name cookName,b.type_name typeName,b.marks,0 cookType from orders_detail a left join farm_produce b on a.cookbook_id=b.farm_id where a.order_id=? order by a.sale_type",
                                    new Object[]
                                    { ordersExt.getOrderId() },
                                    OrdersDetailExt.class));
            break;
        }
        return ordersExt;
    }

    /**
     * 增加外卖订单
     * 
     * @param orders
     */
    @Transactional
    public String addTakeout(Orders orders, HttpServletRequest request)
    {
        List<OrdersDetail> ordersDetails = new ArrayList<OrdersDetail>();
        Map<String, Orders> orderMap = parseDinnerCart(ordersDetails, orders,
                request);
        countOrderMoney(orderMap);

        StringBuffer orderCodes = new StringBuffer("");
        for (Entry<String, Orders> entry : orderMap.entrySet())
        {
            baseDao.add(entry.getValue());
            orderCodes.append(entry.getValue().getOrdersCode()).append(",");
        }

        for (OrdersDetail ordersDetail : ordersDetails)
        {
            baseDao.update(
                    "update cookbook set order_num=IFNULL(order_num,0)+1 where cookbooks_id=?",
                    new Object[]
                    { ordersDetail.getCookbookId() });
        }
        
        return 0 < orderCodes.length() ? orderCodes.substring(0, orderCodes.length()-1) : "";
    }

    /**
     * 查询定单状态客户订单
     * 
     * @param status 客户订单
     * @param request HttpServletRequest
     * @return List<OrdersIndexExt>
     */
    public List<OrdersIndexExt> queryShopOrder(Integer status,
            HttpServletRequest request)
    {
        CustomInfo customInfo = (CustomInfo) request.getSession().getAttribute(
                "loginCustomer");
        return baseDao
                .query("select a.order_id,a.orders_code,a.of_shop_id,a.book_date,a.status,a.add_date,a.update_date,a.consume_money,a.order_money,a.is_comment,a.remark,b.shop_name shopName,b.cost,b.logo shopLogo,b.address,b.telphone,b.marks,c.table_name tableName,c.table_volume tableVolume from orders a left join shop b on a.of_shop_id=b.shop_id left join table_info c on a.of_table_id=c.table_info_id where a.order_type=0 and a.of_custom_id=? and a.status=? order by a.add_date desc",
                        new Object[]
                        { customInfo.getCustomId(), status },
                        OrdersIndexExt.class);
    }

    /**
     * 查询外卖订单
     * 
     * @param status 订单状态
     * @param request HttpServletRequest
     * @return List<OrdersIndexExt>
     */
    public List<OrdersIndexExt> queryTakeoutOrder(Integer status,
            HttpServletRequest request)
    {
        CustomInfo customInfo = (CustomInfo) request.getSession().getAttribute(
                "loginCustomer");

        String querySql = "select a.order_id,a.of_shop_id,a.orders_code,a.order_type,a.book_date,a.status,a.add_date,a.update_date,a.send_date,a.pay_type,a.is_pay,a.pay_money,a.send_status,a.send_addr,a.order_money,a.is_comment,a.remark,b.shop_name shopName,b.logo shopLogo,b.address,b.telphone,b.marks from orders a left join shop b on a.of_shop_id=b.shop_id where a.of_custom_id=? and a.order_type>0";
        switch (status)
        {
        // 待付款
        case 0:
            querySql += " and a.status<3 and a.pay_type=0 and a.is_pay=0 and a.send_status=0 order by a.add_date desc";
            break;
        // 待发货
        case 1:
            querySql += " and a.status<3 and (a.pay_type=1 or a.is_pay=1) and a.send_status=0 order by a.update_date desc,a.add_date desc";
            break;
        // 待收货
        case 2:
            querySql += " and a.status<3 and (a.pay_type=1 or a.is_pay=1) and a.send_status=1 order by a.update_date desc,a.add_date desc";
            break;
        // 评价晒单
        case 3:
            querySql += " and a.status<3 and a.is_pay=1 and a.send_status=2 order by a.update_date desc,a.add_date desc";
            break;
        default:
            return null;
        }
        List<OrdersIndexExt> results = baseDao.query(querySql, new Object[]
        { customInfo.getCustomId() }, OrdersIndexExt.class);

        int orderType = 0;
        for (OrdersIndexExt ordersIndexExt : results)
        {
            orderType = ordersIndexExt.getOrderType();
            switch (orderType)
            {
            case 1:
                ordersIndexExt
                        .setOrderdetailsext(baseDao
                                .query("select a.detail_id,a.cookbook_id,a.sale_type,a.sale_price,a.price,a.num,b.logo cookbookLogo,b.cook_name cookName,b.type_name typeName,b.marks,b.cook_type cookType,b.suggest from orders_detail a left join cookbook b on a.cookbook_id=b.cookbooks_id where a.order_id=? order by a.sale_type",
                                        new Object[]
                                        { ordersIndexExt.getOrderId() },
                                        OrdersDetailExt.class));
                for(OrdersDetailExt detailExt:ordersIndexExt.getOrderdetailsext()){
                	if(detailExt.getCookbookLogo()==null){
                		detailExt.setCookbookLogo("/images/welcome_p.png");
                	}
                	if(detailExt.getCookName()==null){
                		detailExt.setCookName("老板很懒，什么都没写！");
                	}
                	if(detailExt.getCookType()==null){
                		detailExt.setCookType(0);
                		detailExt.setTypeName("菜品");
                	}
                }
                
                break;
            case 2:
                ordersIndexExt
                        .setOrderdetailsext(baseDao
                                .query("select a.detail_id,a.cookbook_id,a.sale_type,a.sale_price,a.price,a.num,b.logo cookbookLogo,b.fresh_name cookName,b.type_name typeName,b.marks,b.fresh_type cookType,b.suggest from orders_detail a left join fresh b on a.cookbook_id=b.fresh_id where a.order_id=? order by a.sale_type",
                                        new Object[]
                                        { ordersIndexExt.getOrderId() },
                                        OrdersDetailExt.class));
                break;
            case 3:
                ordersIndexExt
                        .setOrderdetailsext(baseDao
                                .query("select a.detail_id,a.cookbook_id,a.sale_type,a.sale_price,a.price,a.num,b.logo cookbookLogo,b.farm_name cookName,b.type_name typeName,b.marks,0 cookType from orders_detail a left join farm_produce b on a.cookbook_id=b.farm_id where a.order_id=? order by a.sale_type",
                                        new Object[]
                                        { ordersIndexExt.getOrderId() },
                                        OrdersDetailExt.class));
                
                break;
            }
        }

        return results;
    }

    /**
     * 查询外卖订单
     * 
     * @param request HttpServletRequest
     * @return List<OrdersIndexExt>
     */
    public List<OrdersIndexExt> queryCancelOrder(HttpServletRequest request)
    {
        CustomInfo customInfo = (CustomInfo) request.getSession().getAttribute(
                "loginCustomer");

        String querySql = "select a.order_id,a.of_shop_id,a.orders_code,a.order_type,a.book_date,a.status,a.add_date,a.update_date,a.send_date,a.pay_type,a.is_pay,a.pay_money,a.send_status,a.send_addr,a.order_money,a.is_comment,a.remark,b.shop_name shopName,b.logo shopLogo,b.address,b.telphone,b.marks,b.cost from orders a left join shop b on a.of_shop_id=b.shop_id where a.of_custom_id=? and a.status>=3 order by a.update_date desc";

        List<OrdersIndexExt> results = baseDao.query(querySql, new Object[]
        { customInfo.getCustomId() }, OrdersIndexExt.class);

        return results;
    }

    /**
     * 查询我的消费
     * 
     * @param orderId 订单ID
     * @return OrdersIndexExt
     */
    public List<OrdersIndexExt> queryMyOrder(HttpServletRequest request)
    {
        CustomInfo customInfo = (CustomInfo) request.getSession().getAttribute(
                "loginCustomer");

        String querySql = "select a.order_id,a.of_shop_id,a.orders_code,a.order_type,a.book_date,a.status,a.add_date,a.update_date,a.send_date,a.update_date,a.pay_type,a.is_pay,a.pay_money,a.send_status,a.send_addr,a.order_money,a.is_comment,a.remark,b.shop_name shopName,b.logo shopLogo,b.address,b.telphone,b.marks,b.cost from orders a left join shop b on a.of_shop_id=b.shop_id where a.of_custom_id=? and (a.order_type=0 and a.status=2) or a.send_status=2 order by a.update_date desc";

        List<OrdersIndexExt> results = baseDao.query(querySql, new Object[]
        { customInfo.getCustomId() }, OrdersIndexExt.class);

        return results;
    }

    /**
     * 查询外卖订单详细
     * 
     * @param orderId 订单ID
     * @return OrdersIndexExt
     */
    public OrdersIndexExt queryTakeoutOrderDetail(String orderId)
    {
        String querySql = "select a.order_id,a.of_shop_id,a.orders_code,a.order_type,a.book_date,a.status,a.add_date,a.update_date,a.send_date,a.pay_type,a.is_pay,a.pay_money,a.send_status,a.send_addr,a.order_money,a.is_comment,a.remark,b.shop_name shopName,b.logo shopLogo,b.address,b.telphone,b.marks,b.cost from orders a left join shop b on a.of_shop_id=b.shop_id where a.order_id=?";

        OrdersIndexExt ordersIndexExt = baseDao.queryForObject(querySql,
                new Object[]
                { orderId }, OrdersIndexExt.class);

        if (null == ordersIndexExt)
        {
            return null;
        }

        int orderType = ordersIndexExt.getOrderType();
        switch (orderType)
        {
        case 1:
            ordersIndexExt
                    .setOrderdetailsext(baseDao
                            .query("select a.detail_id,a.cookbook_id,a.sale_type,a.sale_price,a.price,a.num,b.logo cookbookLogo,b.cook_name cookName,b.type_name typeName,b.marks,b.cook_type cookType,b.suggest from orders_detail a left join cookbook b on a.cookbook_id=b.cookbooks_id where a.order_id=? order by a.sale_type",
                                    new Object[]
                                    { ordersIndexExt.getOrderId() },
                                    OrdersDetailExt.class));
            break;
        case 2:
            ordersIndexExt
                    .setOrderdetailsext(baseDao
                            .query("select a.detail_id,a.cookbook_id,a.sale_type,a.sale_price,a.price,a.num,b.logo cookbookLogo,b.fresh_name cookName,b.type_name typeName,b.marks,b.fresh_type cookType,b.suggest from orders_detail a left join fresh b on a.cookbook_id=b.fresh_id where a.order_id=? order by a.sale_type",
                                    new Object[]
                                    { ordersIndexExt.getOrderId() },
                                    OrdersDetailExt.class));
            break;
        case 3:
            ordersIndexExt
                    .setOrderdetailsext(baseDao
                            .query("select a.detail_id,a.cookbook_id,a.sale_type,a.sale_price,a.price,a.num,b.logo cookbookLogo,b.farm_name cookName,b.type_name typeName,b.marks,0 cookType from orders_detail a left join farm_produce b on a.cookbook_id=b.farm_id where a.order_id=? order by a.sale_type",
                                    new Object[]
                                    { ordersIndexExt.getOrderId() },
                                    OrdersDetailExt.class));
            break;
        }

        return ordersIndexExt;
    }

    /**
     * 统计订单金额
     * 
     * @param orderMap
     */
    private void countOrderMoney(Map<String, Orders> orderMap)
    {
        Orders tempOrder;
        double money = 0.00;
        for (Entry<String, Orders> entry : orderMap.entrySet())
        {
            money = 0.00;
            tempOrder = entry.getValue();
            for (OrdersDetail orderDetail : tempOrder.getOrdersDetails())
            {
                money += ((0 <= orderDetail.getSaleType()) ? orderDetail
                        .getSalePrice() : orderDetail.getPrice())
                        * orderDetail.getNum();
            }

            tempOrder.setOrderMoney(money);
            for (OrdersDetail orderDetail : tempOrder.getOrdersDetails())
            {
                orderDetail.getOrders().setOrderMoney(money);
            }
        }
    }

    /**
     * 解析购物车
     * 
     * @param orders
     * @param request
     * @return
     */
    private static Map<String, Orders> parseDinnerCart(
            List<OrdersDetail> ordersDetails, Orders orders,
            HttpServletRequest request)
    {
        String dinnerCart = Utils.getCookie("dinnerCart", request);
        CustomInfo customInfo = (CustomInfo) request.getSession().getAttribute(
                "loginCustomer");

        // 判断cookie是否为空
        if (StringUtils.isBlank(dinnerCart))
        {
            return null;
        }

        dinnerCart = Utils.unescape(dinnerCart);

        String[] tempArr = dinnerCart.split("\7");

        if (0 >= tempArr.length)
        {
            return null;
        }

        Map<String, Orders> orderMap = new HashMap<String, Orders>();

        Orders tempOrder;
        OrdersDetail orderDetail;
        String[] dinnerArr = null;

        for (String tempStr : tempArr)
        {
            if (StringUtils.isBlank(tempStr))
            {
                continue;
            }
            dinnerArr = tempStr.split("\6");
            if (10 > dinnerArr.length || 0 >= Utils.objToInt(dinnerArr[9]))
            {
                continue;
            }
            if (orderMap.containsKey(dinnerArr[3]))
            {
                tempOrder = orderMap.get(dinnerArr[3]);
            }
            else
            {
                tempOrder = new Orders();
                tempOrder.setOfCustomId(customInfo.getCustomId());
                tempOrder.setAddDate(Utils.getNowDate());
                tempOrder.setRemark(orders.getRemark());
                tempOrder.setOrderType(orders.getOrderType());
                tempOrder.setPayType(orders.getPayType());
                tempOrder.setBookDate(orders.getBookDate());
                tempOrder.setSendAddr(orders.getSendAddr());
                tempOrder.setOrdersCode(Utils.getMyOrderCode());
                tempOrder.setOfShopId(dinnerArr[3]);
                tempOrder.setIsPay(0);
                tempOrder.setIsComment(0);
                tempOrder.setSendStatus(0);
                tempOrder.setStatus(1);

                orderMap.put(tempOrder.getOfShopId(), tempOrder);
            }

            orderDetail = new OrdersDetail();

            orderDetail.setCookbookId(dinnerArr[0]);
            orderDetail.setPrice(Utils.objToDouble(dinnerArr[5]));
            orderDetail.setSaleType(Utils.objToIntSp(dinnerArr[6]));
            orderDetail.setSalePrice(Utils.objToDouble(dinnerArr[7]));
            orderDetail.setNum(Utils.objToInt(dinnerArr[9]));
            orderDetail.setOrders(tempOrder);
            tempOrder.getOrdersDetails().add(orderDetail);
            ordersDetails.add(orderDetail);

        }
        return orderMap;

    }

    /**
     * 添加生鲜外卖订单
     * 
     * @param orders 订单列表
     * @author LuoAnDong
     */
    @Override
    public String addOrders(Orders orders, Map<String, Orders> orderMap,
            List<OrdersDetail> ordersDetails)
    {
        String orderCodes = null;
        try
        {
            countOrderMoney(orderMap);
            try
            {
                countOrderMoney(orderMap);
                for (Entry<String, Orders> entry : orderMap.entrySet())
                {
                    baseDao.add(entry.getValue());
                    orderCodes += entry.getValue().getOrdersCode()+",";
                }

                for (OrdersDetail ordersDetail : ordersDetails)
                {
                    baseDao.update(
                            "update fresh set order_num=IFNULL(order_num,0)+1 where fresh_id=?",
                            new Object[]
                            { ordersDetail.getCookbookId() });

                }
                return  0 < orderCodes.length() ? orderCodes.substring(0, orderCodes.length()-1) : "";
            }
            catch (Exception e)
            {
                return null;
            }
        }
        catch (Exception e)
        {
            return null;
        }

    }

    /**
     * 解析生鲜购物车订单
     * 
     * @param ordersDetails
     * @param orders
     * @param freshCat 订单字符串，页面传入的字符
     * @return
     * @author LuoAnDong
     */
    @Override
    public Map<String, Orders> parseShopCart(List<OrdersDetail> ordersDetails,
            Orders orders, String freshCat, CustomInfo customInfo)
    {
        freshCat = Utils.unescape(freshCat);
        String[] tempArr = freshCat.split("\7");

        if (0 >= tempArr.length)
        {
            return null;
        }

        Map<String, Orders> orderMap = new HashMap<String, Orders>();

        Orders tempOrder;
        OrdersDetail orderDetail;
        String[] dinnerArr = null;

        for (String tempStr : tempArr)
        {
            if (StringUtils.isBlank(tempStr))
            {
                continue;
            }
            dinnerArr = tempStr.split("\6");
            if (10 > dinnerArr.length || 0 >= Utils.objToInt(dinnerArr[9]))
            {
                continue;
            }
            if (orderMap.containsKey(dinnerArr[3]))
            {
                tempOrder = orderMap.get(dinnerArr[3]);
            }
            else
            {
                tempOrder = new Orders();
                tempOrder.setOfCustomId(customInfo.getCustomId());
                tempOrder.setAddDate(Utils.getNowDate());
                tempOrder.setRemark(orders.getRemark());
                tempOrder.setOrderType(orders.getOrderType());
                tempOrder.setPayType(orders.getPayType());
                tempOrder.setBookDate(orders.getBookDate());
                tempOrder.setSendAddr(orders.getSendAddr());
                tempOrder.setOrdersCode(Utils.getMyOrderCode());
                tempOrder.setOfShopId(dinnerArr[3]);
                tempOrder.setIsPay(0);
                tempOrder.setIsComment(0);
                tempOrder.setSendStatus(0);
                tempOrder.setStatus(1);

                orderMap.put(tempOrder.getOfShopId(), tempOrder);
            }

            orderDetail = new OrdersDetail();

            orderDetail.setCookbookId(dinnerArr[0]);
            orderDetail.setPrice(Utils.objToDouble(dinnerArr[5]));
            orderDetail.setSaleType(Utils.objToIntSp(dinnerArr[6]));
            orderDetail.setSalePrice(Utils.objToDouble(dinnerArr[7]));
            orderDetail.setNum(Utils.objToInt(dinnerArr[9]));
            orderDetail.setOrders(tempOrder);
            tempOrder.getOrdersDetails().add(orderDetail);
            ordersDetails.add(orderDetail);

        }
        return orderMap;
    }

    @Override
    public Integer cancelOrder(String orderId, HttpServletRequest request)
    {
        int resultCode = 1;
        CustomInfo customInfo = (CustomInfo) request.getSession().getAttribute(
                "loginCustomer");
        String querySql = "select a.order_id,a.orders_code,a.order_type,a.book_date,a.status,a.add_date,a.update_date,a.send_date,a.pay_type,a.is_pay,a.pay_money,a.send_status,a.send_addr,a.order_money,a.is_comment,a.remark from orders a where a.of_custom_id=? and a.order_id=?";
        Orders orders = baseDao.queryForObject(querySql, new Object[]
        { customInfo.getCustomId(), orderId }, Orders.class);
        if (null == orders)
        {
            return resultCode;
        }
        try
        {
            if (2 > orders.getStatus()
                    && (null == orders.getSendStatus() || 2 > orders
                            .getSendStatus()))
            {
                baseDao.update(
                        "update orders set status=3,update_date=? where of_custom_id=? and order_id=?",
                        new Object[]
                        { Utils.getNowDate(), customInfo.getCustomId(), orderId });
            }
            resultCode = 0;
        }
        catch (Exception e)
        {
            return resultCode;
        }
        return resultCode;
    }

    /**
     * 接收订单
     * 
     * @param orderId 订单号
     * @return 0:成功 1 失败
     */
    public Integer receiveOrder(String orderId, HttpServletRequest request)
    {
        int resultCode = 1;
        CustomInfo customInfo = (CustomInfo) request.getSession().getAttribute(
                "loginCustomer");
        String querySql = "select a.order_id,a.orders_code,a.order_type,a.book_date,a.status,a.add_date,a.update_date,a.send_date,a.pay_type,a.is_pay,a.pay_money,a.send_status,a.send_addr,a.order_money,a.is_comment,a.remark from orders a where a.of_custom_id=? and a.order_id=?";
        Orders orders = baseDao.queryForObject(querySql, new Object[]
        { customInfo.getCustomId(), orderId }, Orders.class);
        if (null == orders)
        {
            return resultCode;
        }
        try
        {
            if (0 < orders.getOrderType()
                    && (1 == orders.getPayType() || 1 == orders.getIsPay())
                    && 1 == orders.getSendStatus())
            {
                baseDao.update(
                        "update orders set is_pay=1,send_status=2,update_date=? where of_custom_id=? and order_id=?",
                        new Object[]
                        { Utils.getNowDate(), customInfo.getCustomId(), orderId });
            }
            resultCode = 0;
        }
        catch (Exception e)
        {
            return resultCode;
        }
        return resultCode;
    }

    /**
     * 微信支付　
     * 
     * @param customerId 用户的id
     * @param wxOrderId 微信支付订单的id
     * @param balance 　yes 使用余额 no未使用余额
     * @return 1修改成功　0表示修改失败
     * @param localOrderId 数据库中的id
     * @param balanceValue 余额
     * @author LuoAnDong
     */
    @Transactional
    @Override
    public int wxPay(String customerId, String wxOrderId, String localOrderId,
            String balance, double balanceValue)
    {
        try
        {
            // 更新订单信息
            Orders orders = baseDao.getByHibernate(Orders.class, localOrderId);
            orders.setIsPay(1);
            orders.setPayMoney(orders.getOrderMoney());
            orders.setUpdateDate(Utils.getNowDate());
            baseDao.update(orders);

            // 付款日志
            PayLog payLog = new PayLog();
            payLog.setAlipayOrderId(wxOrderId);
            payLog.setOfOrderId(localOrderId);
            payLog.setPayDate(Utils.getNowDate());
            payLog.setPayMoney(orders.getOrderMoney());
            payLog.setPayUser(customerId);
            baseDao.add(payLog);

            if ("yes".equals(balance))
            {
                // 更新用户余额
                CustomInfo customer = baseDao.getByHibernate(CustomInfo.class,
                        customerId);
                customer.setBalance(balanceValue);
                baseDao.update(customer);
            }
            return 1;
        }
        catch (Exception e)
        {
            runLog.error(e.getMessage());
        }

        return 0;
    }

    /**
     * 通过id查询订单详情
     * 
     * @param orderId
     * @return
     * @author LuoAnDong
     */
    @Override
    public Orders queryById(String orderId)
    {
        return baseDao.getByHibernate(Orders.class, orderId);
    }
    
    /**
     * 计算订单金额
     * @param ordersCode
     * @return 金额
     */
    public double countMoney(String ordersCode)
    {
        List<Orders> orders = baseDao.query("select ifnull(order_money,0) order_money from orders where orders_code in (?)",new Object[]{ordersCode},Orders.class);
        double countMoney = 0.00;
        for(Orders order:orders){
            countMoney += order.getOrderMoney();
        }
        return countMoney;
    }
}
