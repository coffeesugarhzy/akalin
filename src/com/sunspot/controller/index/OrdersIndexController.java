package com.sunspot.controller.index;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunspot.common.HttpUtil;
import com.sunspot.common.Utils;
import com.sunspot.pay.WxConfig;
import com.sunspot.pojo.CustomInfo;
import com.sunspot.pojo.Orders;
import com.sunspot.pojoext.OrdersIndexExt;
import com.sunspot.service.CommentsService;
import com.sunspot.service.CookbookService;
import com.sunspot.service.CustomInfoService;
import com.sunspot.service.FarmService;
import com.sunspot.service.FreshService;
import com.sunspot.service.OrdersService;
import com.sunspot.service.ShopService;

/**
 * 前端订单控制层
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("index/orders")
public class OrdersIndexController
{
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private CookbookService cookbookService;

    @Autowired
    private FreshService freshService;

    @Autowired
    private FarmService farmService;
    
    @Autowired
	private CommentsService commentsService;
    
	/**
	 * 用户业务层
	 */
	@Autowired
	private CustomInfoService customInfoService ; 

    /**
     * 前端首页
     * 
     * @param modelMap
     * @return
     */
    @RequestMapping("/ordershopsec")
    public void ordershopsec(Integer status, ModelMap modelMap,
            HttpServletRequest request)
    {
        status = (null == status) ? 0 : status;
        modelMap.addAttribute("status", status);
        
        List<OrdersIndexExt> orders=ordersService.queryShopOrder(status, request);
        modelMap.addAttribute("orders",orders);
        modelMap.addAttribute("is_appends", commentsService.queryAppEndByOrderId(orders));
        
    }

    @RequestMapping("/ordertakeoutsec")
    public void ordertakeoutsec(Integer status, ModelMap modelMap,
            HttpServletRequest request)
    {
        status = (null == status) ? 1 : status;
        modelMap.addAttribute("status", status);
        
        List<OrdersIndexExt> orders=ordersService.queryTakeoutOrder(status, request);
        modelMap.addAttribute("orders",orders);
        
        modelMap.addAttribute("is_appends", commentsService.queryAppEndByOrderId(orders));
    }

    @RequestMapping("/cancelOrdersec")
    public String cancelOrdersec(Integer orderType, Integer status,
            String orderId, ModelMap modelMap, HttpServletRequest request)
    {
        ordersService.cancelOrder(orderId, request);
        status = (null == status) ? 0 : status;
        modelMap.addAttribute("status", status);
        if (0 == orderType)
        {
            modelMap.addAttribute("orders",
                    ordersService.queryShopOrder(status, request));
            return "/index/orders/ordershopsec";
        }
        else
        {
            status=(0 == status)? 1 : status;
            modelMap.addAttribute("orders",
                    ordersService.queryTakeoutOrder(status, request));
            return "/index/orders/ordertakeoutsec";
        }
    }

    @RequestMapping("/cancelOrderListsec")
    public void cancelOrderListsec(ModelMap modelMap, HttpServletRequest request)
    {
        modelMap.addAttribute("orders", ordersService.queryCancelOrder(request));
    }

    @RequestMapping("/receiveOrdersec")
    public String receiveOrdersec(String orderId, ModelMap modelMap,
            HttpServletRequest request)
    {
        ordersService.receiveOrder(orderId, request);
        int status = 2;
        modelMap.addAttribute("status", status);
        modelMap.addAttribute("orders",
                ordersService.queryTakeoutOrder(status, request));
        return "/index/orders/ordertakeoutsec";
    }

    @RequestMapping("/shopdetail")
    public void shopdetail(String shopId, ModelMap modelMap)
    {
        modelMap.addAttribute("shop", shopService.queryFullById(shopId));
    }

    @RequestMapping("/orderdetailsec")
    public void orderdetailsec(String orderId, ModelMap modelMap,
            HttpServletRequest request)
    {
        modelMap.addAttribute("order",
                ordersService.queryTakeoutOrderDetail(orderId));
    }

    @RequestMapping("/cookbookdetail")
    public void cookbookdetail(String id, Integer orderType, ModelMap modelMap,
            HttpServletRequest request)
    {
        modelMap.addAttribute("orderType", orderType);
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
        switch (orderType)
        {
        case 1:
            modelMap.addAttribute("cookbook", cookbookService.queryById(id));
            break;
        case 2:
            modelMap.addAttribute("cookbook", freshService.queryById(id));
            break;
        case 3:
            modelMap.addAttribute("cookbook", farmService.queryById(id));
            break;
        }

    }
    
    /**
     * 微信路径返回
     * @param result
     * @param orderId 微信生成的订单
     * @param localOrderId 数据库保存的订单
     * @param customerId 客户的id
     * @return
     * @author LuoAndong
     */ 
    @RequestMapping("/notifysec")
    public void notify(String customerId , String orderId , String balance , String localOrderId , HttpSession session){
    	//更新支付
        int changeStatus = ordersService.wxPay(customerId , orderId ,localOrderId, balance,0) ; 
        //更新成功
        if(changeStatus == 1){
            //更新当前用户Session
            CustomInfo customer = HttpUtil.getCustom(session) ; 
            customer.setBalance(0) ;  
        } 
    }
    
    /**
     * 用户微信页面支付跳转
     * @author LuoAnDong
     * @param orderId  订单id
     * @param balance 是否余额支付yes 使用  no不使用
     */
    @RequestMapping("/toPaysec")
    public String toPay(String orderId,String balance,HttpSession session){
    	return pageHandle(orderId , balance ,session ) ;
    }
    
    /**
     * 支付出错
     * @return
     */
    @RequestMapping("/payFailsec")
    public void payFailsec(){ 
    }
    
    /**
     * 支付处理
     * @param orders
     * @param customInfo
     * @return
     * @author LuoAnDong
     */
    private String pageHandle(String orderId ,String balance ,HttpSession session ){
    	 
    	CustomInfo customInfo = HttpUtil.getCustom(session) ; 
    	
        //查询支付金额
        Orders saveOrder = ordersService.queryById(orderId) ; 
        double payMoney = saveOrder.getOrderMoney() ; 
        
        //微信支付(使用余款)ssss
        if("yes".equals(balance)){
            customInfo = customInfoService.queryById(customInfo.getCustomId()) ;
            
            //余额大于支付金额
            if(customInfo.getBalance()>=payMoney){
                int changeStatus = ordersService.wxPay(customInfo.getCustomId(), UUID.randomUUID().toString(), balance, orderId,(customInfo.getBalance()-payMoney)) ; 
                //更新成功
                if(changeStatus == 1){
                    //更新当前用户Session
                   
                	customInfo.setBalance(customInfo.getBalance()-payMoney) ;  
                } 
                return "/index/orders/notify" ; 
            } 
            
            //得到用户的余额
            payMoney -= customInfo.getBalance() ; 
            balance = "yes" ; 
        } 
        
        return "redirect:"+WxConfig.rebackUrl(payMoney+"", customInfo.getCustomId(),balance , orderId);
        
    }
}
