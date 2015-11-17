package com.sunspot.controller.index;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.common.HttpUtil;
import com.sunspot.common.MyUtil;
import com.sunspot.common.Utils;
import com.sunspot.pojo.CookbookType;
import com.sunspot.pojo.CustomInfo;
import com.sunspot.pojo.CustomLike;
import com.sunspot.pojo.Orders;
import com.sunspot.pojo.Shop;
import com.sunspot.pojoext.CookbookIndexExt;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.AreaService;
import com.sunspot.service.CookbookService;
import com.sunspot.service.CookbookTypeService;
import com.sunspot.service.CustomInfoService;
import com.sunspot.service.CustomLikeService;
import com.sunspot.service.DataTypeService;
import com.sunspot.service.OrdersService;
import com.sunspot.service.ShopService;

import java.sql.*;
/**
 * 家宴前端控制层
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("index/dinner")
public class DinnerIndexController
{
    /**
     * 注入业务层管理
     */
    @Autowired
    private CookbookService cookbookService;
    
    /**
     * 注入业务层管理
     */
    @Autowired
    private CookbookTypeService cookbookTypeService;
    
    /**
     * 区域业务层
     */
    @Autowired
    private AreaService areaService ; 

    /**
     * 注入业务层管理
     */
    @Autowired
    private DataTypeService dataTypeService;

    /**
     * 注入业务层管理
     */
    @Autowired
    private ShopService shopService;
    
    /**
     * 用户收藏业务层
     */
    @Autowired
    private CustomLikeService customLikeService;
    
    /**
     * 注入业务层管理
     */
    @Autowired
    private OrdersService ordersService;
    
    @Autowired
    private CustomInfoService customInfoService;
    
    /**
     * 前端首页
     * 
     * @param modelMap
     * @return
     */
    @RequestMapping("/dinner")
    public void dinner(HttpServletRequest request)
    {
        request.setAttribute("tip", "dinner");
    }

    /**
     * 跳转到查询列表页面
     * 
     * @param typeName
     * @param cookType
     * @param modelMap
     */
    @RequestMapping(value = "cookbooks", method = RequestMethod.GET)
    public String cookbooks(Integer queryType, Integer key, String value,HttpServletRequest request,
            ModelMap modelMap)
    { 
        value = StringUtils.isBlank(value) ? "" : Utils.transformChar(value);
        modelMap.addAttribute("queryType", queryType);
        modelMap.addAttribute("key", key);
        modelMap.addAttribute("value", value);
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
        modelMap.addAttribute("dataTypes", dataTypeService.queryByParentId("3001")); 
        modelMap.addAttribute("areaList", areaService.listSubArear(HttpUtil.getCityName(request)));
        
        switch (queryType)
        {
        case 1:
            return "/index/dinner/cookbook";
        case 2:
            return "/index/dinner/plans";
        case 3:
            return "/index/dinner/sale";
        default:
            return "/index/dinner/dinner";
        }
    }

    /**
     * 查询生鲜数据
     * 
     * @param request
     *            请求对象
     * @param list
     *            返回结果
     * @param key
     *            查询条件
     * @param value
     *            值
     * @return list
     */
    @ResponseBody
    @RequestMapping("listDinner")
    public DataGridModel<CookbookIndexExt> listDinner(
            HttpServletRequest request, DataGridModel<CookbookIndexExt> list,
            Integer queryType, Integer key, String value)
    {
        cookbookService.queryPage(list, queryType, key, value);
        return list;
    }

    /**
     * 跳转到查询列表页面
     */
    @RequestMapping(value = "flashsale")
    public void flashsale()
    {

    }

    /**
     * 查询限时抢购
     * 
     * @param request
     *            请求对象
     * @param list
     *            返回结果
     * @param key
     *            查询条件
     * @param value
     *            值
     * @return list
     */
    @ResponseBody
    @RequestMapping("listFlashsale")
    public DataGridModel<CookbookIndexExt> listFlashsale(
            HttpServletRequest request, DataGridModel<CookbookIndexExt> list,
            Integer key, String value)
    {
        cookbookService.queryDisPage(list);
        return list;
    }

    /**
     * 跳转到限时抢购表页面
     */
    @RequestMapping(value = "detail")
    public void detail(String cookbooksId, ModelMap modelMap,HttpSession session)
    {
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
        modelMap.addAttribute("cookbook",
                cookbookService.queryByIndexId(cookbooksId));
        
        //收藏　
        cheskShopLike(modelMap, session, cookbooksId, 1) ; 
    }

    /**
     * 跳转到查询列表页面
     */
    @RequestMapping(value = "entershop")
    public void entershop(String value,
    		              String shopId, 
    		              ModelMap modelMap,
    		              @RequestParam(value="cooktype",required=false,defaultValue="") String cooktype,
    		              HttpSession session)
    {
    	value=MyUtil.transformChar(value==null?"":value);
        modelMap.addAttribute("flashsales",
                cookbookService.queryShopCbId(1, shopId, null));
        modelMap.addAttribute("sales",
                cookbookService.queryShopCbId(2, shopId, null));
        modelMap.addAttribute("plans",
                cookbookService.queryShopCbId(3, shopId, null));
        Shop shop = shopService.queryFullById(shopId) ; 
        modelMap.addAttribute("shop",shop );
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
        List<CookbookType> ls=cookbookTypeService.listTypeOfShop(shopId);
        modelMap.addAttribute("cookbookTypes",ls) ; 
        modelMap.addAttribute("cooktype",cooktype); 
        modelMap.addAttribute("value",value); 
        modelMap.addAttribute("cookbooks",
                cookbookService.queryCookbookShowIndex(shopId, value, cooktype));
        
        cheskShopLike(modelMap , session ,shop.getShopId(),0);
    }

    /**
     * 跳转到查询列表页面
     */
    @RequestMapping(value = "shopflash")
    public void shopflash(String shopId, ModelMap modelMap)
    {
        modelMap.addAttribute("flashsales",
                cookbookService.queryShopCbId(1, shopId, null));
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
    }

    /**
     * 跳转到查询列表页面
     */
    @RequestMapping(value = "shopsales")
    public void shopsales(String shopId, ModelMap modelMap)
    {
        modelMap.addAttribute("sales",
                cookbookService.queryShopCbId(2, shopId, null));
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
    }

    /**
     * 跳转到查询列表页面
     */
    @RequestMapping(value = "shopplans")
    public void shopplans(String shopId, ModelMap modelMap)
    {
        modelMap.addAttribute("plans",
                cookbookService.queryShopCbId(3, shopId, null));
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
    }
    
    /**
     * 收藏
     * @param modelMap
     * @param session
     * @param shop
     */
    private void cheskShopLike(ModelMap modelMap,HttpSession session,String likeId , int type){
        CustomInfo customInfo = HttpUtil.getCustom(session);
        if (customInfo != null && likeId != null)
        {
            // 查询出用户关注的店铺
            CustomLike customLike = customLikeService.queryCustomLike(likeId,type, customInfo.getCustomId());   
            modelMap.addAttribute("customLike", customLike) ; 
        }
    }

    /**
     * 跳转到限时抢购表页面
     */
    @RequestMapping(value = "shopdetail")
    public void shopdetail(String shopId, ModelMap modelMap,HttpSession session)
    {
        Shop shop = shopService.queryFullById(shopId); 
        modelMap.addAttribute("shop", shop);
        cheskShopLike(modelMap , session ,shop.getShopId(),0);
    }

    /**
     * 跳转到限时抢购表页面
     */
    @RequestMapping(value = "shopcbdetail")
    public void shopcbdetail(String cookbooksId, ModelMap modelMap,HttpSession session)
    {
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
        CookbookIndexExt cb = cookbookService.queryByIndexId(cookbooksId) ; 
        modelMap.addAttribute("cookbook", cb );
        
        //收藏　
        cheskShopLike(modelMap, session, cookbooksId, 1) ; 
    }

    /**
     * 跳转到限时抢购表页面
     */
    @RequestMapping(value = "shopall")
    public void shopall(String value, String shopId, ModelMap modelMap)
    { 
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
        modelMap.addAttribute("cookbooks",
                cookbookService.queryShopCbId(0, shopId, value));
    }

    /**
     * 跳转到限时抢购表页面
     */
    @RequestMapping(value = "shopnew")
    public void shopnew(String shopId, ModelMap modelMap)
    {
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
        modelMap.addAttribute("cookbooks",
                cookbookService.queryShopCbId(4, shopId, null));
    }

    /**
     * 跳转订单确定页面
     */
    @RequestMapping(value = "ordersec")
    public void ordersec(ModelMap modelMap,HttpServletRequest request)
    {
        modelMap.addAttribute("addresses",customInfoService.getAddrs(request));
    }

    /**
     * 提交订单
     * 
     * @param orders
     */
    @RequestMapping(value = "submitOrdersec")
    public String submitOrdersec(Orders orders, HttpServletRequest request,
            HttpServletResponse response,ModelMap modelMap)
    {
        try
        {
            modelMap.addAttribute("fromwhere", "/index/dinner/ordersec.do");
           
            String orderCodes = ordersService.addTakeout(orders, request);
            
            //付款类型
            modelMap.addAttribute("payType",orders.getPayType());
            
            //订单号
            modelMap.addAttribute("orderCodes",orderCodes);
            
            try {

                Class.forName("com.mysql.jdbc.Driver").newInstance();
                String url = "jdbc:mysql://localhost:3306/limmai?useUnicode=true&characterEncoding=UTF8";
                Connection conn = DriverManager.getConnection(url, "root", "admin");//Limmai007
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select order_id,of_custom_id,order_money from orders WHERE orders_code='"+orderCodes+"'");
                while (rs.next()) {
                    modelMap.addAttribute("order_id", rs.getString(1));
                    modelMap.addAttribute("user_id", rs.getString(2));
                    modelMap.addAttribute("money", rs.getString(3));
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Utils.delCookie("dinnerCart", request, response);
            return "/index/dinner/submitOrder";
        }
        catch (Exception e)
        {
            return "/index/dinner/submitfail";
        }
    }
}
