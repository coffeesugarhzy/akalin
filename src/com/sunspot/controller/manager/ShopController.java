package com.sunspot.controller.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.common.Utils;
import com.sunspot.pojo.Shop;
import com.sunspot.pojo.TableInfo;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.AreaService;
import com.sunspot.service.DataTypeService;
import com.sunspot.service.ShopService;

/**
 * 店铺<br/>
 * 路径：manager/shop/方法 , 默认添加的方法 del , add(post/get)
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("manager/shop")
public class ShopController
{
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(ShopController.class);

    /**
     * 店铺业务层
     */
    @Autowired
    private ShopService shopService;

    /**
     * 分表业务层
     */
    @Autowired
    private DataTypeService dataTypeService;

    /**
     * 区域业务层
     */
    @Autowired
    private AreaService areaService;

    /**
     * 进入添加页面
     * 
     * @param t
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public void enterAdd()
    {
    }

    /**
     * 查询管理，返回json数据，结合jqGrid做管理
     * 
     * @author LuoAnDong
     */
    @ResponseBody
    @RequestMapping("listJson")
    public DataGridModel<Shop> listJson(DataGridModel<Shop> list, String keyword)
    {

        // 分页查询角色管理
        List<Shop> roles = shopService.queryByPage(list, keyword);
        list.setContent(roles);

        return list;

    }
    
    /**
     * 查询管理，返回json数据，结合jqGrid做管理
     * 
     * @author qkc
     */
    @ResponseBody
    @RequestMapping("listJsonProv")
    public DataGridModel<Shop> listJson(DataGridModel<Shop> list, String keyword,Integer status)
    {
        shopService.queryByPage(list, keyword,status);
        return list;
    }

    /**
     * 进入管理页面　
     * 
     * @author LuoAnDong
     */
    @RequestMapping("list")
    public void list(String keyword, Integer status, ModelMap modelMap) 
    {
        modelMap.addAttribute("keyword", StringUtils.isNotBlank(keyword) ? keyword : "");
        
        modelMap.addAttribute("status", null == status ? 3 : status);
    }
    
	@RequestMapping("listProv")
    public void listtakeoutProv(String keyword, Integer status, ModelMap modelMap)       
    {
        modelMap.addAttribute("keyword", StringUtils.isNotBlank(keyword) ? keyword : "");
               
        modelMap.addAttribute("status", null == status ? 3 : status);
    }
	
    /**
     * 店铺详情 　后台查询店铺信息
     * 
     * @author LuoAnDong
     */
    @RequestMapping("detail")
    public void detail(HttpServletRequest request, ModelMap modelMap, String id)
    {
        modelMap.addAttribute("dataTypes",
                dataTypeService.queryByParentId("1001"));
        modelMap.addAttribute("shop", shopService.queryFullById(id));
    }

    @RequestMapping("modShop")
    public void modShop(HttpServletRequest request, ModelMap modelMap, String id)
    {
      //  modelMap.addAttribute("dataTypes",dataTypeService.queryByParentId("1001")); 
        modelMap.addAttribute("shop", shopService.queryFullById(id));
    } 
    
    @RequestMapping("doModShop")
    public String doModShop(HttpServletRequest request, ModelMap modelMap,Shop shop)
    {
    	Integer resultCode = 1;
    	try{
    		shopService.modShopInfo(shop);
	    }
	    catch (Exception e)
	    {
	        runLog.error(e);
	        resultCode = 0;
	    }
	    modelMap.addAttribute("resultCode", resultCode);
	    return "/rsp/submitrsp";
    } 
    
    
    /**
     * 进入商家店铺页面
     */
    @RequestMapping("shop")
    public void shop(HttpServletRequest request, ModelMap modelMap)
    {
        modelMap.addAttribute("dataTypes",
                dataTypeService.queryByParentId("1001"));
        modelMap.addAttribute("shop", shopService.queryByUserId(request));
        modelMap.addAttribute("areas", Utils.ObjectToJson(areaService.queryByNoPage()));
    }

    /**
     * 刷新店铺信息
     */
    @RequestMapping(value = "addOrMod", method = RequestMethod.POST)
    public String addOrMod(HttpServletRequest request, ModelMap modelMap,
            Shop shop, String[] tableName, Integer[] tableVolume,
            Integer[] tableNum)
    {
        Integer resultCode = 1;
        try
        {
            if (null == shop)
            {
                resultCode = 0;
            }
            else
            {
                shop.setTableInfos(getTableInfo(tableName, tableVolume,
                        tableNum));

                if (StringUtils.isBlank(shop.getShopId()))
                {
                    shopService.add(request, shop);
                }
                else
                {
                    shopService.modify(request, shop);
                }

            }
        }
        catch (Exception e)
        {
            runLog.error(e);
            resultCode = 0;
        }
        modelMap.addAttribute("resultCode", resultCode);
        return "/rsp/submitrsp";

    }
    
    
    @ResponseBody
    @RequestMapping(value = "updateOnlineStatus", method = RequestMethod.POST)
    public Map<String,String> updateShopOline(Shop shop){
    	Map<String,String> result=new HashMap<String,String>();
    	try{
    		shopService.updateShopOnline(shop);
    		result.put("success", "1");
    	}catch(Exception e){
    		result.put("success", "0");
    	}
    	return result;
    }
    
    /**
     * 刷新店铺信息
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.GET)
    public String updateStatus(HttpServletRequest request, ModelMap modelMap,
            String id)
    {
        Integer resultCode = 1;
        try
        {
            shopService.modifyStatus(request, id);
        }
        catch (Exception e)
        {
            runLog.error(e);
            resultCode = 0;
        }
        modelMap.addAttribute("resultCode", resultCode);
        return "/rsp/submitrsp";

    }

    /**
     * 限时优惠
     * 
     * @param id
     * @param modelMap
     */
    @RequestMapping("toDiscount")
    public void toDiscount(String id, ModelMap modelMap)
    {
        modelMap.addAttribute("shop", shopService.queryFullById(id));
    }

    /**
     * 限时优惠
     * 
     * @param id
     */
    @RequestMapping(value = "discount", method = RequestMethod.POST)
    public String discount(Shop shop, ModelMap modelMap)
    {
        int resultCode = 1;
        try
        {
            shopService.setDiscount(shop);
        }
        catch (Exception e)
        {
            runLog.error(e);
            resultCode = 0;
        }
        modelMap.addAttribute("resultCode", resultCode);

        return "/rsp/submitrsp";
    }
    
        /**
         * 商家设置自动关店的时间
         * 
         * @param id
         * @param modelMap
         */
       @RequestMapping(value = "setCloseTime",method = RequestMethod.POST)
        public String SetCloseTime(Shop shop, ModelMap modelMap)
        {
        	int resultCode = 1;
            try
            {
                shopService.setCloseTime(shop);
            }
            catch (Exception e)
            {
                runLog.error(e);
                resultCode = 0;
            }
            modelMap.addAttribute("resultCode", resultCode);
    
            return "/rsp/submitrsp";
        }
        
        /**
    +     * 跳转到商家设置自动关店时间页面
    +     * 
    +     * @param id
    +     * @param modelMap
    +     */
        @RequestMapping("toSetCloseTime")
        public void toSetCloseTime(String id, ModelMap modelMap)
        {
            modelMap.addAttribute("shop", shopService.queryFullById(id));
        }
        
    private List<TableInfo> getTableInfo(String[] tableName,
            Integer[] tableVolume, Integer[] tableNum)
    {
        if (null == tableName || 0 == tableName.length) { return null; }
        List<TableInfo> tableInfos = new ArrayList<TableInfo>();
        TableInfo tableInfo;
        for (int i = 0, len = tableName.length; i < len; i++)
        {
            tableInfo = new TableInfo();
            tableInfo.setTableName(tableName[i]);
            tableInfo.setTableVolume(tableVolume[i]);
            tableInfo.setTableNum(tableNum[i]);
            tableInfos.add(tableInfo);
        }
        return tableInfos;
    }
}
