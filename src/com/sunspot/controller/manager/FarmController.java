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

import com.sunspot.pojo.FarmProduce;
import com.sunspot.pojo.Shop;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.DataTypeService;
import com.sunspot.service.FarmService;
import com.sunspot.service.ShopService;

/**
 * 农副产品控制层
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("manager/farm")
public class FarmController
{
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(FarmController.class);

    /**
     * 注入业务层管理
     */
    @Autowired
    private FarmService farmService;

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
     * 跳转到 查询农副
     */
    @RequestMapping("farm")
    public void farm()
    {
    }

    /**
     * 进入增加页面
     */
    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    public void toAdd(HttpServletRequest request, ModelMap modelMap)
    {
        modelMap.addAttribute("shops", shopService.listByUserId(request));
        modelMap.addAttribute("dataTypes",
                dataTypeService.queryByParentId("3001"));
    }

    /**
     * 进入修改页面
     */
    @RequestMapping(value = "toMod", method = RequestMethod.GET)
    public void toMod(HttpServletRequest request, ModelMap modelMap, String id)
    {
        modelMap.addAttribute("shops", shopService.listByUserId(request));
        modelMap.addAttribute("farmProduce", farmService.queryById(id));
        modelMap.addAttribute("dataTypes",
                dataTypeService.queryByParentId("3001"));
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
    public DataGridModel<FarmProduce> listJson(HttpServletRequest request,
            DataGridModel<FarmProduce> list, String keyword)
    {

        Shop shop = shopService.queryByUserId(request);
        if (null != shop)
        {
            farmService.queryPage(list, shop.getShopId(), keyword);
        }
        return list;

    }

    /**
     * 添加实体
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(HttpServletRequest request, FarmProduce farmProduce,
            ModelMap modelMap)
    {
        Integer resultCode = 1;
        try
        {
            farmService.add(request, farmProduce);
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
    @RequestMapping(value = "mod", method = RequestMethod.POST)
    public String mod(HttpServletRequest request, FarmProduce farmProduce,
            ModelMap modelMap)
    {
        int resultCode = 1;
        try
        {
            farmService.modify(request, farmProduce);
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
        modelMap.addAttribute("farmProduce", farmService.queryById(id));
    }

    /**
     * 通过id删除实体
     * 
     * @param id
     */
    @ResponseBody
    @RequestMapping("del")
    public int detail(String id)
    {
        try
        {
            farmService.delete(id);
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
        modelMap.addAttribute("farmProduce", farmService.queryById(id));
    }

    /**
     * 通过id删除实体
     * 
     * @param id
     */
    @RequestMapping(value = "sale", method = RequestMethod.POST)
    public String sale(FarmProduce farmProduce, ModelMap modelMap)
    {
        int resultCode = 1;
        try
        {
            farmService.setSale(farmProduce);
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
