package com.sunspot.controller.manager;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.pojo.Shop;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.OrdersExt;
import com.sunspot.service.OrdersService;
import com.sunspot.service.RebateService;
import com.sunspot.service.ShopService;

/**
 * 订单管理
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("manager/orders/")
public class OrdersController
{
    /**
     * 业务层
     */
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private RebateService rebateService;

    @RequestMapping("listshop")
    public void listshop(String keyword, Integer status, ModelMap modelMap)
    {
        modelMap.addAttribute("keyword",
                StringUtils.isNotBlank(keyword) ? keyword : "");
        modelMap.addAttribute("status", null == status ? 0 : status);
        modelMap.addAttribute("rebateNum", rebateService.countCondNum());
    }

    /**
     * 分页查询所有订单
     * 
     * @param list
     * @return
     */
    @ResponseBody
    @RequestMapping("listShopJson")
    public DataGridModel<OrdersExt> listShopJson(DataGridModel<OrdersExt> list,
            String keyword, int status)
    {
        ordersService.queryByShop(list, keyword, status);
        return list;
    }

    /**
     * 分页查询所有订单
     * 
     * @param list
     * @return
     */
    @RequestMapping("listtakeout")
    public void listtakeout(String keyword, Integer status, ModelMap modelMap)
    {
        modelMap.addAttribute("keyword",
                StringUtils.isNotBlank(keyword) ? keyword : "");
        modelMap.addAttribute("status", null == status ? 1 : status);
        modelMap.addAttribute("rebateNum", rebateService.countCondNum());
    }

    @RequestMapping("rebate")
    public String rebate(String keyword, String orderId, Integer pageType,
            ModelMap modelMap)
    {

        modelMap.addAttribute("keyword",
                StringUtils.isNotBlank(keyword) ? keyword : "");
        modelMap.addAttribute("rebateNum", rebateService.countCondNum());

        ordersService.rebate(orderId);
        if (0 == pageType)
        {
            modelMap.addAttribute("status", 2);
            return "manager/orders/listshop";
        }
        else
        {
            modelMap.addAttribute("status", 3);
            return "manager/orders/listtakeout";
        }
    }

    /**
     * 分页查询所有订单
     * 
     * @param list
     * @return
     */
    @ResponseBody
    @RequestMapping("listTakeoutJson")
    public DataGridModel<OrdersExt> listTakeoutJson(
            DataGridModel<OrdersExt> list, String keyword, Integer status)
    {
        ordersService.queryTakeout(list, keyword, status);
        return list;
    }

    @RequestMapping("listshopProv")
    public void listshopProv(String keyword, Integer status, ModelMap modelMap)
    {
        modelMap.addAttribute("keyword",
                StringUtils.isNotBlank(keyword) ? keyword : "");
        modelMap.addAttribute("status", null == status ? 0 : status);
    }

    /**
     * 分页查询所有订单
     * 
     * @param list
     * @return
     */
    @ResponseBody
    @RequestMapping("listShopProvJson")
    public DataGridModel<OrdersExt> listShopProvJson(
            DataGridModel<OrdersExt> list, String keyword, Integer status,
            HttpServletRequest request)
    {
        Shop shop = shopService.queryByUserId(request);
        if (null != shop)
        {
            ordersService.queryByShop(list, keyword, status, shop.getShopId());
        }
        return list;
    }

    /**
     * 分页查询所有订单
     * 
     * @param list
     * @return
     */
    @RequestMapping("listtakeoutProv")
    public void listtakeoutProv(String keyword, Integer status,
            ModelMap modelMap)
    {
        modelMap.addAttribute("keyword",
                StringUtils.isNotBlank(keyword) ? keyword : "");
        modelMap.addAttribute("status", null == status ? 1 : status);
    }

    /**
     * 分页查询所有订单
     * 
     * @param list
     * @return
     */
    @ResponseBody
    @RequestMapping("listTakeoutProvJson")
    public DataGridModel<OrdersExt> listTakeoutProvJson(
            DataGridModel<OrdersExt> list, String keyword, Integer status,
            HttpServletRequest request)
    {
        Shop shop = shopService.queryByUserId(request);
        if (null != shop)
        {
            ordersService.queryTakeout(list, keyword, status, shop.getShopId());
        }
        return list;
    }

    @RequestMapping("send")
    public String send(String keyword, String orderId,
            HttpServletRequest request, ModelMap modelMap)
    {
        Shop shop = shopService.queryByUserId(request);
        if (null != shop)
        {
            ordersService.send(orderId, shop.getShopId());
            //七天自动确认收货
            ordersService.autoReceiveGoods(orderId, shop.getShopId());
        }
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("status", 1);
        return "/manager/orders/listtakeoutProv";
    }

    @RequestMapping("returnmoney")
    public String returnmoney(String keyword, String orderId,ModelMap modelMap)
    {
        ordersService.returnmoney(orderId);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("status", 4);
        return "/manager/orders/listtakeout";
    }

    @RequestMapping("confirm")
    public String confirm(String keyword, String orderId,
            HttpServletRequest request, ModelMap modelMap)
    {
        Shop shop = shopService.queryByUserId(request);
        if (null != shop)
        {
            ordersService.confirm(orderId, shop.getShopId());
        }
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("status", 0);
        return "/manager/orders/listshopProv";
    }

    @RequestMapping("toConsume")
    public void toConsume(String keyword, String orderId, ModelMap modelMap)
    {
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("orderId", orderId);
        modelMap.addAttribute("status", 1);
    }

    @RequestMapping("consume")
    public String consume(String keyword, String orderId, double consumeMoney,
            ModelMap modelMap, HttpServletRequest request)
    {
        Shop shop = shopService.queryByUserId(request);
        int resultCode = 1;
        try
        {
            if (null != shop)
            {
                ordersService.consume(consumeMoney, orderId, shop.getShopId());
            }
        }
        catch (Exception e)
        {
            resultCode = 0;
        }
        modelMap.addAttribute("resultCode", resultCode);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("status", 1);
        return "/rsp/submitrsp";
    }

    @RequestMapping("detail")
    public void detail(String orderId, ModelMap modelMap)
    {
        modelMap.addAttribute("orders", ordersService.queryByDetail(orderId));
    }
}
