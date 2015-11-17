package com.sunspot.controller.index;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.common.Constants;
import com.sunspot.common.HttpUtil;
import com.sunspot.common.Utils;
import com.sunspot.pojo.CustomInfo;
import com.sunspot.pojo.CustomLike;
import com.sunspot.pojo.DataTypes;
import com.sunspot.pojo.Orders;
import com.sunspot.pojo.OrdersDetail;
import com.sunspot.pojo.Shop;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.FreshExt;
import com.sunspot.pojoext.FreshIndexExt;
import com.sunspot.service.AreaService;
import com.sunspot.service.CouponService;
import com.sunspot.service.CustomInfoService;
import com.sunspot.service.CustomLikeService;
import com.sunspot.service.DataTypeService;
import com.sunspot.service.FarmService;
import com.sunspot.service.FreshService;
import com.sunspot.service.OrdersService;
import com.sunspot.service.ShopService;

/**
 * 生鲜前端控制层
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("index/fresh")
public class FreshIndexController
{
	/**
	 * 注入业务层管理
	 */
	@Autowired
	private FreshService freshService;
	
	/**
	 * 用户业务层
	 */
	@Autowired
    private CustomInfoService customInfoService;
    
	/**
	 * 注入业务层管理
	 */
	@Autowired
	private DataTypeService dataTypeService;
	 
    /**
     * 用户收藏业务层
     */
    @Autowired
    private CustomLikeService customLikeService;
    
    /**
     * 区域业务层
     */
    @Autowired
    private AreaService areaService ; 

    /**
     * 返利业务层管理
     */
    @Autowired
    private CouponService couponService;

    /**
     * 店铺业务层管理
     */
    @Autowired
    private ShopService shopService;

    /**
     * 订单业务层管理
     */
    @Autowired
    private OrdersService ordersService;
    
    /**
     * 农副产品业务层　
     */
    @Autowired
    private FarmService farmService ; 

    /**
     * 进入生鲜首页,这里为生鲜模块
     * 
     * @param modelMap
     * @return
     * @author LuoAnDong
     */
    private List<DataTypes> getDataTypes(HttpServletRequest request )
    {
        List<DataTypes> dataTypeList = dataTypeService.queryByName("生鲜");
        HttpUtil.list(request, dataTypeList);
        return dataTypeList ; 
    }
    
    /**
     * 前端首页
     * 
     * @param modelMap
     * @return
     */
    @RequestMapping("/index")
    public void fresh(HttpServletRequest request)
    {       
        request.setAttribute("tip", "fresh");
        // 分类
        getDataTypes(request);
        
        //天天特价
        List<FreshExt> freshExt = freshService.queryNewSale(Utils.getWeekDay().toString(),0,1) ;   
        if(freshExt != null && freshExt.size()==1)
            request.setAttribute("saleFresh", freshExt.get(0)) ; 
        
    }

    /**
     * 跳转到查询列表页面
     * 
     * @param typeName
     * @param cookType
     * @param modelMap
     */
    @RequestMapping(value = "fresh", method = RequestMethod.GET)
    public String cookbooks(Integer queryType, Integer key, String value,HttpServletRequest request , 
            ModelMap modelMap)
    {
        value = StringUtils.isBlank(value) ? "" : Utils.transformChar(value);
        modelMap.addAttribute("queryType", queryType);
        modelMap.addAttribute("key", key);
        modelMap.addAttribute("value", value);
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
        modelMap.addAttribute("dataTypes",dataTypeService.queryByName("生鲜"));
        modelMap.addAttribute("areaList", areaService.listSubArear(HttpUtil.getCityName(request)));
        
        switch (queryType)
        {
        case 1:
            return "/index/fresh/fresh";
        case 2:
            return "/index/fresh/plans";
        case 3:
            return "/index/fresh/sale";
        default:
            return "/index/fresh/fresh";
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
	@RequestMapping("listfresh")
	public DataGridModel<FreshIndexExt> listfresh(
	        HttpServletRequest request, DataGridModel<FreshIndexExt> list,
	        Integer queryType, Integer key, String value)
	{
	    freshService.queryPage(list, queryType, key, value);
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
     * @param request 请求对象
     * @param list 返回结果
     * @param key 查询条件
     * @param value 值
     * @return list
     */
    @ResponseBody
    @RequestMapping("listFlashsale")
    public DataGridModel<FreshIndexExt> listFlashsale(
            HttpServletRequest request, DataGridModel<FreshIndexExt> list,
            Integer key, String value)
    { 
        freshService.queryDisPage(list); 
        return list;
       
    }

    /**
     * 跳转到限时抢购表页面
     */
    @RequestMapping(value = "detail")
    public void detail(String freshId, ModelMap modelMap)
    {
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm")); 
        modelMap.addAttribute("fresh",freshService.queryByIndexId(freshId));
    }

    /**
     * 进入到查询店铺
     * @author LuoAnDong
     */
    @RequestMapping(value = "entershop")
    public void entershop(String shopId, ModelMap modelMap,HttpSession session)
    {
        int shopPageSize = 4 ; 
        
        modelMap.addAttribute("flashsales",freshService.queryShopFreshId(1, shopId, null,0,shopPageSize));
        modelMap.addAttribute("sales", freshService.queryShopFreshId(2, shopId, null,0,shopPageSize));
        modelMap.addAttribute("plans", freshService.queryShopFreshId(3, shopId, null,0,shopPageSize)); 
         
        //农副产品　 
        modelMap.addAttribute("farms", farmService.queryByPage(shopId,null,0,shopPageSize));
         
        modelMap.addAttribute("shop", shopService.queryFullById(shopId));
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
        
        //收藏
        cheskShopLike(modelMap , session ,shopId,0);
    }

    /**
     * 店铺收藏
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
     * 进入生鲜限时抢购页面
     */
    @RequestMapping(value = "shopflash")
    public void shopflash(String shopId, ModelMap modelMap)
    {
        
        modelMap.addAttribute("flashsales", freshService.queryShopFreshId(1, shopId, null,0,Constants.DEFAULT_SHOP_PAGE_SIZE));
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
    }

    /**
     * 跳转到查询列表页面
     */
    @RequestMapping(value = "shopsales")
    public void shopsales(String shopId, ModelMap modelMap)
    { 
        modelMap.addAttribute("sales", freshService.queryShopFreshId(2, shopId, null,0,Constants.DEFAULT_SHOP_PAGE_SIZE));
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
    }

    /**
     * 进入生鲜套餐列表页面
     */
    @RequestMapping(value = "shopplans")
    public void shopplans(String shopId, ModelMap modelMap)
    {
        modelMap.addAttribute("plans",
                freshService.queryShopFreshId(3, shopId, null,0,Constants.DEFAULT_SHOP_PAGE_SIZE));
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
    }

    /**
     * 进入店铺简介页面
     */
    @RequestMapping(value = "shopdetail")
    public void shopdetail(String shopId, ModelMap modelMap,HttpSession session)
    {
        CustomInfo customInfo = HttpUtil.getCustom(session);
        Shop shop = shopService.queryFullById(shopId);
        modelMap.addAttribute("shop", shop);
        if (customInfo != null && shop != null)
        {
            // 查询出用户关注的店铺
            CustomLike customLike = customLikeService.queryCustomLike(
                    shop.getShopId(),0, customInfo.getCustomId());  
            modelMap.addAttribute("customLike", customLike) ; 
        }
    }

    /**
     * 生鲜菜谱详情页面
     */
    @RequestMapping(value = "freshDetail")
    public void shopFreshdetail(String freshId, ModelMap modelMap,HttpSession session)
    {
        FreshIndexExt fresh = freshService.queryByIndexId(freshId) ; 
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
        modelMap.addAttribute("fresh", fresh);
        CustomInfo customInfo = HttpUtil.getCustom(session);
         
        if (customInfo != null && fresh != null)
        {
            // 查询出用户关注的店铺
            CustomLike customLike = customLikeService.queryCustomLike(fresh.getFreshId(),2, customInfo.getCustomId());   
            modelMap.addAttribute("customLike", customLike) ; 
        }
    }

    /**
     * 生鲜店铺全部
     */
    @RequestMapping(value = "shopall")
    public void shopall(String value, String shopId, ModelMap modelMap)
    {
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
        modelMap.addAttribute("freshs", freshService.queryShopFreshId(0, shopId, value,0,Constants.DEFAULT_SHOP_PAGE_SIZE));
    }

    /**
     * 跳转到限时抢购表页面
     */
    @RequestMapping(value = "shopnew")
    public void shopnew(String shopId, ModelMap modelMap)
    {
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
        modelMap.addAttribute("freshs", freshService.queryShopFreshId(4, shopId, null,0,Constants.DEFAULT_SHOP_PAGE_SIZE));
    }

    /**
     * 跳转订单确定页面
     */
    @RequestMapping(value = "ordersec")
    public void orders(ModelMap modelMap,HttpServletRequest request)
    {   
        modelMap.addAttribute("addresses",customInfoService.getAddrs(request));
    }
  
    /**
     * 提交订单
     * 
     * @param orders
     */
    @RequestMapping(value = "submitOrdersec",method=RequestMethod.POST)
    public String submitOrder(Orders orders, 
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,ModelMap modelMap)
    {  
        //用户未登陆
        CustomInfo customInfo = HttpUtil.getCustom(session) ; 
        if(customInfo==null) return Constants.ORDER_FAIL ; 
         
        try
        {
            //解析订单
            String freshCat = Utils.getCookie("freshCart", request) ; 
            if (StringUtils.isBlank(freshCat))  return Constants.ORDER_FAIL ;  
            List<OrdersDetail> ordersDetails = new ArrayList<OrdersDetail>();
            Map<String, Orders> orderMap = ordersService.parseShopCart(ordersDetails, orders,freshCat,customInfo);
             
            if(orderMap==null) return Constants.ORDER_FAIL ; 
            
            //下单
            String orderCodes = ordersService.addOrders(orders,orderMap,ordersDetails);
            if(StringUtils.isNoneBlank(orderCodes)){
                Utils.delCookie("freshCart", request, response);
                
                //付款类型
                modelMap.addAttribute("payType",orders.getPayType());
                
                //订单号
                modelMap.addAttribute("orderCodes",orderCodes);
                
                return Constants.ORDER_SUCCESS;
            }
            else
                return Constants.ORDER_FAIL;
            
           
        }
        catch (Exception e)
        {
            return Constants.ORDER_FAIL;
        }
    }
}
