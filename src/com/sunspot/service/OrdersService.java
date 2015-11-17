package com.sunspot.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sunspot.pojo.CustomInfo;
import com.sunspot.pojo.Orders;
import com.sunspot.pojo.OrdersDetail;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.OrdersExt;
import com.sunspot.pojoext.OrdersIndexExt;

/**
 * 订单业务实现类<br/>
 * 
 * @author LuoAnDong
 * 
 */
public interface OrdersService
{
    /**
     * 分页查询订单扩展类基本信息
     * 
     * @param list
     * @param keyword
     * @return
     * @author LuoAnDong
     */
    void queryByShop(DataGridModel<OrdersExt> list, String keyword, int status);

    /**
     * 分页查询订单扩展类基本信息
     * 
     * @param list
     * @param keyword
     * @return
     * @author LuoAnDong
     */
    void queryTakeout(DataGridModel<OrdersExt> list, String keyword, int status);

    /**
     * 分页查询订单扩展类基本信息
     * 
     * @param list
     * @param keyword
     * @return
     * @author LuoAnDong
     */
    void queryByShop(DataGridModel<OrdersExt> list, String keyword, int status,
            String shopId);

    /**
     * 分页查询订单扩展类基本信息
     * 
     * @param list
     * @param keyword
     * @return
     * @author LuoAnDong
     */
    void queryTakeout(DataGridModel<OrdersExt> list, String keyword,
            int status, String shopId);

    /**
     * 发货
     * 
     * @param orderId 订单ID
     * @param shopId 店铺ID
     */
    int send(String orderId, String shopId);
    
    /**
     * 7天后自动确认收货
     * 
     * @param orderId 订单ID
     * @param shopId 店铺ID
     */
    void autoReceiveGoods(String orderId, String shopId);
    
    /**
     * 退款
     * 
     * @param orderId
     * @return
     */
    int returnmoney(String orderId);

    /**
     * 确认
     * 
     * @param orderId 订单ID
     * @param shopId 店铺ID
     */
    int confirm(String orderId, String shopId);

    /**
     * 确认
     * 
     * @param orderId 订单ID
     * @param shopId 店铺ID
     */
    int consume(double consumeMoney, String orderId, String shopId);

    /**
     * 返利
     * 
     * @param orderId
     */
    void rebate(String orderId);

    /**
     * 查询定单详细
     * 
     * @param orderId 订单ID
     * @return List<OrdersIndexExt>
     */
    OrdersExt queryByDetail(String orderId);

    /**
     * 增加外卖订单
     * 
     * @param orders
     */
    String addTakeout(Orders orders, HttpServletRequest request);

    /**
     * 查询定单状态客户订单
     * 
     * @param status 客户订单
     * @param request HttpServletRequest
     * @return List<OrdersIndexExt>
     */
    List<OrdersIndexExt> queryShopOrder(Integer status,
            HttpServletRequest request);

    /**
     * 查询外卖订单
     * 
     * @param status 订单状态
     * @param request HttpServletRequest
     * @return List<OrdersIndexExt>
     */
    List<OrdersIndexExt> queryTakeoutOrder(Integer status,
            HttpServletRequest request);

    /**
     * 查询外卖订单
     * 
     * @param request HttpServletRequest
     * @return List<OrdersIndexExt>
     */
    List<OrdersIndexExt> queryCancelOrder(HttpServletRequest request);

    /**
     * 查询我的消费
     * 
     * @param orderId 订单ID
     * @return OrdersIndexExt
     */
    List<OrdersIndexExt> queryMyOrder(HttpServletRequest request);

    /**
     * 查询外卖订单详细
     * 
     * @param orderId 订单ID
     * @return OrdersIndexExt
     */
    OrdersIndexExt queryTakeoutOrderDetail(String orderId);

    /**
     * 取消订单
     * 
     * @param orderId 订单号
     * @return 0:成功 1 失败
     */
    Integer cancelOrder(String orderId, HttpServletRequest request);

    /**
     * 添加生鲜/农副产品外卖订单
     * 
     * @param orders 页面购物车Cookie名称　
     * @param request
     * @author LuoAnDong
     */
    String addOrders(Orders orders, Map<String, Orders> orderMap,
            List<OrdersDetail> ordersDetails);

    /**
     * 接收订单
     * 
     * @param orderId 订单号
     * @return 0:成功 1 失败
     */
    Integer receiveOrder(String orderId, HttpServletRequest request);

    /**
     * 解析生鲜/农副产品购物车订单
     * 
     * @param ordersDetails 得到订单的数组
     * @param orders 订单基本信息
     * @param freshCat 订单字符串，页面传入的字符
     * @param customInfo 订单提交的用户　
     * @return
     * @author LuoAnDong
     */
    Map<String, Orders> parseShopCart(List<OrdersDetail> ordersDetails,

    Orders orders, String freshCat, CustomInfo customInfo);

    /**
     * 微信支付　
     * 
     * @param customerId 用户的id
     * @param orderId 订单的id
     * @param balance 　yes 使用余额 no未使用余额
     * @return 1修改成功　0表示修改失败
     * @param localOrderId 数据库中的id
     * @author LuoAnDong
     * 
     */
    int wxPay(String customerId, String orderId, String balance,
            String localOrderId, double balanceValue);
    
    /**
     * 通过id查询订单详情
     * 
     * @param orderId
     * @return
     * @author LuoAnDong
     */
    Orders queryById(String orderId);
    
    /**
     * 计算订单金额
     * @param ordersCode
     * @return 金额
     */
    double countMoney(String ordersCode);

}
