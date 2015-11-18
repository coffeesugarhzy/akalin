package com.sunspot.controller.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.pojo.Cookbook;
import com.sunspot.pojo.CookbookType;
import com.sunspot.pojo.Shop;
import com.sunspot.pojo.UserInfo;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.CookbookService;
import com.sunspot.service.CookbookTypeService;
import com.sunspot.service.DataTypeService;
import com.sunspot.service.ShopService;

/**
 * 家宴设置控制层
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("manager/cookbook")
public class CookbookController
{
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(CookbookController.class);

    /**
     * 注入业务层管理
     */
    @Autowired
    private CookbookService cookbookService;

    /**
     * 注入业务层管理
     */
    @Autowired
    private ShopService shopService;

    /**
     * 注入业务层管理
     */
    @Autowired
    private DataTypeService dataTypeService;

    /**
     * 注入业务层管理
     */
    @Autowired
    private CookbookTypeService cookbookTypeService;
    
    /**
     * 跳转到查询家宴菜谱
     */
    @RequestMapping("cookbook")
    public void cookbook()
    {
    }

    /**
     * 跳转到 查询商品
     */
    @RequestMapping("cookbookType")
    public void cookbookType(HttpServletRequest request,ModelMap modelMap)
    {
        modelMap.addAttribute("shops", shopService.listByUserId(request));
    }
    
    /**
     * 跳转到 查询家宴套餐
     */
    @RequestMapping("cookbookPlans")
    public void cookbookPlans()
    {
    }

    /**
     * 进入增加页面
     */
    @RequestMapping(value = "addCookbook", method = RequestMethod.GET)
    public void addCookbook(HttpServletRequest request, ModelMap modelMap)
    {
        modelMap.addAttribute("shops", shopService.listByUserId(request));
        modelMap.addAttribute("dataTypes",
                dataTypeService.queryByParentId("3001"));
    }

    /**
     * 进入增加页面
     */
    @RequestMapping(value = "addCookbookPlans", method = RequestMethod.GET)
    public void addCookbookPlans(HttpServletRequest request, ModelMap modelMap)
    {
        modelMap.addAttribute("shops", shopService.listByUserId(request));
    }
    
    /**
     * 添加类型
     * @param request
     * @param modelMap
     */
    @RequestMapping(value = "addCookbookType", method = RequestMethod.GET)
    public void addCookbookType(HttpServletRequest request, ModelMap modelMap){
        modelMap.addAttribute("shops", shopService.listByUserId(request));
    }
    
    @RequestMapping(value = "addCookbookType", method = RequestMethod.POST)
    public String addCookbookType(HttpSession session,CookbookType cook,ModelMap modelMap){
    	UserInfo loginUser = (UserInfo) session.getAttribute("loginUser");
    	int resultCode=0;
    	if(loginUser!=null&&cook.getShopId()!=null){
    		cook.setUserId(loginUser.getUserId());
    		if(!cookbookTypeService.isCookbookExite(cook)){
    			cookbookTypeService.addCookbookType(cook);
    			resultCode=1;
    		}
    	}
        modelMap.addAttribute("resultCode", resultCode);
        return "/rsp/submitrsp";
    }
    
    /**
     * 进入修改页面
     */
    @RequestMapping(value = "modCookbook", method = RequestMethod.GET)
    public void modCookbook(HttpServletRequest request, ModelMap modelMap,
            String id)
    {
        modelMap.addAttribute("shops", shopService.listByUserId(request));
        Cookbook cookbook=cookbookService.queryById(id);
        modelMap.addAttribute("cookbook",cookbook);
        if(cookbook!=null){
        	List<CookbookType> ls=cookbookTypeService.listTypeOfShop(cookbook.getOfShopId());
        	modelMap.addAttribute("cookbooktypes", ls);	
        }
       
        modelMap.addAttribute("dataTypes",
                dataTypeService.queryByParentId("3001"));
    }
    
    /**
     * 进入修改页面
     */
    @RequestMapping(value = "modCookbookType", method = RequestMethod.GET)
    public void modCookbookType(HttpServletRequest request, ModelMap modelMap,
            String id)
    {
        modelMap.addAttribute("shops", shopService.listByUserId(request));
        modelMap.addAttribute("cookbooktype", cookbookTypeService.getCookbookTypeById(id));
    }
    
    /**
     * 进入添加页面
     */
    @RequestMapping(value = "modCookbookPlans", method = RequestMethod.GET)
    public void modCookbookPlans(HttpServletRequest request, ModelMap modelMap,
            String id)
    {
        modelMap.addAttribute("shops", shopService.listByUserId(request));
        modelMap.addAttribute("cookbook", cookbookService.queryById(id));
    }

    /**
     * 查询生鲜数据
     * 
     * @param request
     *            请求对象
     * @param list
     *            返回结果
     * @param keyword
     *            查询条件
     * @return list
     */
    @ResponseBody
    @RequestMapping("listJson")
    public DataGridModel<Cookbook> listJson(HttpServletRequest request,
            DataGridModel<Cookbook> list, String keyword, Integer cookType)
    {

        Shop shop = shopService.queryByUserId(request);
        if (null != shop)
        {
            cookbookService
                    .queryPage(list, shop.getShopId(), cookType, keyword);
        }
        return list;

    }

    @ResponseBody
    @RequestMapping("listTypeJson")
    public DataGridModel<CookbookType> listTypeJson(HttpSession session,
            DataGridModel<CookbookType> list, String keyword, String shopId)
    {
    	cookbookTypeService.queryPage(list,session,shopId, keyword);       
        return list;
    }
    
    @ResponseBody
    @RequestMapping("listTypeOfShop")
    public Map<String,List<CookbookType>> listTypeOfShop(String shopId){
    	Map<String,List<CookbookType>> result=new HashMap<String,List<CookbookType>>();
    	result.put("result", cookbookTypeService.listTypeOfShop(shopId));
    	return result;
    }
    
    /**
     * 添加实体
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(HttpServletRequest request, Cookbook cookbook,
            ModelMap modelMap)
    {
        Integer resultCode = 1;
        try
        {
            cookbookService.add(request, cookbook);
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
     * 修改实体
     */
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public String modify(HttpServletRequest request, Cookbook cookbook,
            ModelMap modelMap)
    {
        int resultCode = 1;
        try
        {
            cookbookService.modify(request, cookbook);
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
     * 修改实体
     */
    @RequestMapping(value = "typeModify", method = RequestMethod.POST)
    public String typeModify(HttpServletRequest request, CookbookType cookbookType,
            ModelMap modelMap)
    {
        int resultCode = 1;
        try
        {
        	CookbookType next=cookbookTypeService.getCookbookTypeById(cookbookType.getTypeId());
        	if(next!=null){
        		next.setShopId(cookbookType.getShopId());
        		next.setSort(cookbookType.getSort());
        		next.setTypeName(cookbookType.getTypeName());
        	    cookbookTypeService.updateCookbookType(next);
        	}else{
        		resultCode = 0;
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
    
    /**
     * 查看实体
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public void detail(String id, ModelMap modelMap)
    {
        modelMap.addAttribute("cookbook", cookbookService.queryById(id));
    }

    /**
     * 查看实体
     */
    @RequestMapping(value = "typeDetail", method = RequestMethod.GET)
    public void Typedetail(String id, ModelMap modelMap)
    {
        modelMap.addAttribute("cookbooktype", cookbookTypeService.queryCookbookType(id));
    }
    
    /**
     * 通过id删除实体
     * 
     * @param id
     */
    @ResponseBody
    @RequestMapping("del")
    public int del(String id)
    {
        try
        {
            cookbookService.delete(id);
            return 1;
        }
        catch (Exception e)
        {
            runLog.error(e);
            return 0;
        }
    }

    /**
     * 通过id删除实体
     * @param id
     */
    @ResponseBody
    @RequestMapping("typeDel")
    public int typeDel(String id)
    {
        try
        {
            cookbookTypeService.delCookbookTypeById(id);
            return 1;
        }
        catch (Exception e)
        {
            runLog.error(e);
            return 0;
        }
    }
    
    
    /**
     * 到天天特价页面
     * 
     * @param id
     * @param modelMap
     */
    @RequestMapping("toSale")
    public void toSale(String id, ModelMap modelMap)
    {
        modelMap.addAttribute("cookbook", cookbookService.queryById(id));
    }

    /**
     * 通过id删除实体
     * 
     * @param id
     */
    @RequestMapping(value = "sale", method = RequestMethod.POST)
    public String sale(Cookbook cookbook, ModelMap modelMap)
    {
        int resultCode = 1;
        try
        {
            cookbookService.setSale(cookbook);
        }
        catch (Exception e)
        {
            runLog.error(e);
            resultCode = 0;
        }
        modelMap.addAttribute("resultCode", resultCode);

        return "/rsp/submitrsp";
    }
}
