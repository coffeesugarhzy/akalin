package com.sunspot.controller.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.common.Utils;
import com.sunspot.pojo.ReturnMoney;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.RebateService;

/**
 * 返利控制器<br/>
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("manager/rebate")
public class RebateController
{
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(RebateController.class);
    
    /**
     * 引入业务层
     */
    @Autowired
    private RebateService rebateService;

    /**
     * 进入控制页
     */
    @RequestMapping("list")
    public void list()
    {
    }

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
     * 删除　
     * 
     * @param t
     */
    @ResponseBody
    @RequestMapping(value = "del", method = RequestMethod.POST)
    public int del(String id)
    {
        runLog.warn("delete id = " + id);
        return rebateService.delete(id) ; 
    }

    /**
     * 保存　
     * 
     * @param t
     */
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public int add(ReturnMoney t)
    {
        t.setAddDate(Utils.getNowDate());
        return rebateService.add(t);
    }

    /**
     * 保存修改信息
     * 
     * @param t
     */
    @ResponseBody
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public int modify(ReturnMoney t)
    {
        t.setUpdateDate(Utils.getNowDate());
        return rebateService.modify(t); 

    }

    /**
     * 进入修改页面
     * 
     * @param t
     */
    @RequestMapping(value = "modify", method = RequestMethod.GET)
    public void enterModify(String id, HttpServletRequest request)
    {
        ReturnMoney t = rebateService.queryById(id);
        request.setAttribute("bean", t);
    }

    /**
     * 查询角色管理，返回json数据，结合jqGrid做管理
     * 
     * @author LuoAnDong
     */
    @ResponseBody
    @RequestMapping("listJson")
    public DataGridModel<ReturnMoney> findRole(DataGridModel<ReturnMoney> list,
            String keyword)
    {

        if (list == null)
            return null;
        // 分页查询角色管理
        List<ReturnMoney> roles = rebateService.queryByPage(list, keyword);
        list.setContent(roles);

        return list;

    }
}
