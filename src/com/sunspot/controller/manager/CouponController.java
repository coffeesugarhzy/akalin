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

import com.sunspot.pojo.Coupon;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.UserSessionUtil;
import com.sunspot.service.CouponService;

/**
 * 优惠券管理
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("manager/coupon")
public class CouponController
{
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(CouponController.class);

    /**
     * 注入业务层管理
     */
    @Autowired
    private CouponService couponService;

    /**
     * 跳转到 查询农副
     */
    @RequestMapping("coupon")
    public void coupon()
    {
    }

    /**
     * 进入修改页面
     */
    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    public void toAdd(ModelMap modelMap)
    {
    }

    /**
     * 进入添加页面
     */
    @RequestMapping(value = "toMod", method = RequestMethod.GET)
    public void toMod(ModelMap modelMap, String id)
    {
        modelMap.addAttribute("coupon", couponService.queryById(id));
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
    public DataGridModel<Coupon> listJson(HttpServletRequest request,
            DataGridModel<Coupon> list, String keyword)
    {
        couponService.queryPage(list, UserSessionUtil.getUserInfo(request)
                .getUserId(), keyword);
        return list;

    }

    /**
     * 添加实体
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(HttpServletRequest request, Coupon coupon,
            ModelMap modelMap)
    {
        Integer resultCode = 1;
        try
        {
            couponService.add(request, coupon);
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
    public String mod(HttpServletRequest request, Coupon coupon,
            ModelMap modelMap)
    {
        int resultCode = 1;
        try
        {
            couponService.modify(request, coupon);
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
        modelMap.addAttribute("coupon", couponService.queryById(id));
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
            couponService.delete(id);
            return 1;
        }
        catch (Exception e)
        {
            runLog.error(e);
            return 0;
        }
    }
}
