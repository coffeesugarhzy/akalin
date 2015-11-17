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
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.FarmProduceIndexExt;
import com.sunspot.service.AreaService;
import com.sunspot.service.CustomInfoService;
import com.sunspot.service.CustomLikeService;
import com.sunspot.service.DataTypeService;
import com.sunspot.service.FarmService;
import com.sunspot.service.OrdersService;

/**
 * 农户种植前端控制层
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("index/farm")
public class FarmIndexController
{
	/**
	 * 注入业务层管理
	 */
	@Autowired
	private FarmService framService;

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
     * 区域业务层
     */
    @Autowired
    private AreaService areaService ; 
	
	/**
     * 订单业务层管理
     */
    @Autowired
    private OrdersService ordersService;
    
    /**
     * 用户收藏业务层
     */
    @Autowired
    private CustomLikeService customLikeService;
	
	/**
	 * 农户业务层
	 * @author LuoAnDong
	 */
	@Autowired
	private FarmService fpService ; 

	/**
     * 设置类型，得到子类型
     * @author LuoAnDong
     */
    private List<DataTypes> getDataTypes(HttpServletRequest request )
    {
        List<DataTypes> dataTypeList = dataTypeService.queryByName("生鲜");  
        return dataTypeList ; 
    }
    
    /**
     * 农副产品详情
     */
    @RequestMapping(value = "farmDetail", method = RequestMethod.GET)
    public void detail(ModelMap modelMap,String farmId,HttpServletRequest request,HttpSession session){ 
        
        request.setAttribute("weekDay", Utils.getWeekDay());
        request.setAttribute("curTime", Utils.getCurDate("HH:mm"));
        FarmProduceIndexExt farmProduce = framService.queryIndexById(farmId) ;  
        request.setAttribute("farmProduce", farmProduce) ; 
        
        //收藏　
        cheskShopLike(modelMap,session , farmId,3) ; 
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
	 * 进入农副产品
	 * 
	 * @param typeName
	 * @param cookType
	 * @param modelMap
	 */ 
    @RequestMapping(value = "farm", method = RequestMethod.GET)
    public void farm(Integer queryType, Integer key, String value,HttpServletRequest request,
            ModelMap modelMap)
    {
         
        value = StringUtils.isBlank(value) ? "" : Utils.transformChar(value);
        modelMap.addAttribute("queryType", queryType);
        modelMap.addAttribute("key", key);
        modelMap.addAttribute("value", value);
        modelMap.addAttribute("weekDay", Utils.getWeekDay());
        modelMap.addAttribute("curTime", Utils.getCurDate("HH:mm"));
        modelMap.addAttribute("dataTypes",getDataTypes(request));
        modelMap.addAttribute("areaList", areaService.listSubArear(HttpUtil.getCityName(request)));
     
	}
	
    /**
     * 查询农副数据
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
    @RequestMapping(value="listfarm",method=RequestMethod.POST)
    public DataGridModel<FarmProduceIndexExt> typeJson(
            HttpServletRequest request, 
            DataGridModel<FarmProduceIndexExt> list,
            Integer queryType, 
            Integer key, 
            String value)
    {   
        framService.queryPage(list, queryType, key, value);
        return list;
    }
    
    /**
     * 跳转农副产品订单确定页面
     */
    @RequestMapping(value = "ordersec")
    public void orders(ModelMap modelMap,HttpServletRequest request)
    {    
        modelMap.addAttribute("addresses",customInfoService.getAddrs(request));
    }
    
    /**
     * 店铺农副产品列表
     */
    @RequestMapping(value = "shopFarm")
    public void shopFarm(String shopId,
            HttpServletRequest request){
        request.setAttribute("weekDay", Utils.getWeekDay());
        request.setAttribute("curTime", Utils.getCurDate("HH:mm"));
        List<FarmProduceIndexExt> farmList = framService.queryByPage(shopId, null, 0, Constants.DEFAULT_SHOP_PAGE_SIZE) ; 
        request.setAttribute("farmList", farmList) ; 
    }
    
    /**
     * 提交订单<br/>
     * 支付类型<br/>　0 微信支付(不使用余款)<br/>　1 货到付款 <br/>  
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
            String farmCart = Utils.getCookie("farmCart", request) ;   
            if (StringUtils.isBlank(farmCart))  return Constants.ORDER_FAIL ;   
            List<OrdersDetail> ordersDetails = new ArrayList<OrdersDetail>();
            Map<String, Orders> orderMap = ordersService.parseShopCart(ordersDetails, orders,farmCart,customInfo);
              
            if(orderMap==null) return Constants.ORDER_FAIL ;  
            //下单
            String orderCodes = ordersService.addOrders(orders,orderMap,ordersDetails);
             
            if(StringUtils.isNoneBlank(orderCodes)){ 
                Utils.delCookie("farmCart", request, response);
                
              //付款类型
                modelMap.addAttribute("payType",orders.getPayType());
                
                //订单号
                modelMap.addAttribute("orderCodes",orderCodes);
                
                return Constants.ORDER_SUCCESS; 
            }
             
            return Constants.ORDER_FAIL;
            
        }
        catch (Exception e)
        {
            return Constants.ORDER_FAIL;
        } 
    }
     
	 
}
