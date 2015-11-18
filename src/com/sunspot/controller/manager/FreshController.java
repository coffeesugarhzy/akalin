package com.sunspot.controller.manager;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.pojo.Fresh;
import com.sunspot.pojo.Shop;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.DataTypeService;
import com.sunspot.service.FreshService;
import com.sunspot.service.ShopService;

/**
 * 生鲜设置控制
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("manager/fresh")
public class FreshController
{
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(FreshController.class);

    /**
     * 注入业务层管理
     */
    @Autowired
    private FreshService freshService;

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
     * 跳转到查询生鲜
     */
    @RequestMapping("fresh")
    public void fresh()
    {
    }

    /**
     * 跳转到 查询生鲜套餐
     */
    @RequestMapping("freshPlans")
    public void freshPlans()
    {
    }

    /**
     * 进入修改页面
     */
    @RequestMapping(value = "addFresh", method = RequestMethod.GET)
    public void addFresh(HttpServletRequest request, ModelMap modelMap)
    {
        modelMap.addAttribute("shops", shopService.listByUserId(request));
        modelMap.addAttribute("makeMethod", freshService.queryMakeMethod());
        modelMap.addAttribute("dataTypes",
                dataTypeService.queryByParentId("3001"));
    }

    /**
     * 进入修改页面
     */
    @RequestMapping(value = "addPlans", method = RequestMethod.GET)
    public void addPlans(HttpServletRequest request, ModelMap modelMap)
    {
        modelMap.addAttribute("shops", shopService.listByUserId(request));
        modelMap.addAttribute("dataTypes",
                dataTypeService.queryByParentId("3001"));
    }

    /**
     * 进入添加页面
     */
    @RequestMapping(value = "modFresh", method = RequestMethod.GET)
    public void modFresh(HttpServletRequest request, ModelMap modelMap,
            String id)
    {
        modelMap.addAttribute("shops", shopService.listByUserId(request));
        modelMap.addAttribute("makeMethod", freshService.queryMakeMethod());
        modelMap.addAttribute("dataTypes",
                dataTypeService.queryByParentId("3001"));
        modelMap.addAttribute("fresh", freshService.queryById(id));
    }

    /**
     * 进入添加页面
     */
    @RequestMapping(value = "modPlans", method = RequestMethod.GET)
    public void modPlans(HttpServletRequest request, ModelMap modelMap,
            String id)
    {
        modelMap.addAttribute("shops", shopService.listByUserId(request));
        modelMap.addAttribute("fresh", freshService.queryById(id));
    }

    /**
     * 查询生鲜数据
     * 
     * @param request 请求对象
     * @param list 返回结果
     * @param keyword 查询条件
     * @return list
     */
    @ResponseBody
    @RequestMapping("listJson")
    public DataGridModel<Fresh> listJson(HttpServletRequest request,
            DataGridModel<Fresh> list, String keyword, Integer freshType)
    {

        Shop shop = shopService.queryByUserId(request);
        if (null != shop)
        {
            freshService.queryPage(list, shop.getShopId(), freshType, keyword);
        }
        return list;

    }

    /**
     * 添加实体
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(HttpServletRequest request, Fresh fresh, ModelMap modelMap)
    {
        Integer resultCode = 1;
        try
        {
            freshService.add(request, fresh);
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
    public String modify(HttpServletRequest request, Fresh fresh,
            ModelMap modelMap)
    {
        int resultCode = 1;
        try
        {
            freshService.modify(request, fresh);
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
        modelMap.addAttribute("fresh", freshService.queryById(id));
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
            freshService.delete(id);
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
        modelMap.addAttribute("fresh", freshService.queryById(id));
    }

    /**
     * 通过id删除实体
     * 
     * @param id
     */
    @RequestMapping(value = "sale", method = RequestMethod.POST)
    public String sale(Fresh fresh, ModelMap modelMap)
    {
        int resultCode = 1;
        try
        {
            freshService.setSale(fresh);
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
