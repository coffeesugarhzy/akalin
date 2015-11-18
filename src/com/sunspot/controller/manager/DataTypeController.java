package com.sunspot.controller.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.pojo.DataTypes;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.DataTypeService;

/**
 * 菜谱类型控制器
 * 
 * @author LuoAnDong
 */
@Controller
@Scope("prototype")
@RequestMapping("manager/datatype")
public class DataTypeController
{
    /**
     * 引入业务层
     */
    @Autowired
    private DataTypeService dataTypeService;

    /**
     * 进入修改页面
     * 
     * @param t
     */
    @RequestMapping(value = "modify", method = RequestMethod.GET)
    public void enterModify(String id, HttpServletRequest request)
    {
        listAllCbType(request);
        DataTypes t = dataTypeService.queryById(id);
        request.setAttribute("cbbean", t);
    }

    /**
     * 添加实体
     * 
     * @param t
     */
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public int add(DataTypes t)
    {
        return dataTypeService.add(t);
    }

    /**
     * 进入添加页面
     * 
     * @param t
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public void enterAdd(HttpServletRequest request)
    {
        listAllCbType(request);
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
        return dataTypeService.delete(id);
    }

    /**
     * 列举出所有菜谱信息
     * 
     * @param request
     * @return
     */
    private List<DataTypes> listAllCbType(HttpServletRequest request)
    {
        List<DataTypes> cbTypeList = dataTypeService.queryByNoPage();
        if (request != null)
            request.setAttribute("beans", cbTypeList);
        return cbTypeList;
    }

    /**
     * 保存修改信息
     * 
     * @param t
     */
    @ResponseBody
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public int modify(DataTypes t)
    {
        return dataTypeService.modify(t);
    }

    /**
     * 进入管理页面　
     * 
     * @author LuoAnDong
     */
    @RequestMapping("list")
    public void list()
    {
    }

    /**
     * 查询角色管理，返回json数据，结合jqGrid做管理
     * 
     * @author LuoAnDong
     */
    @ResponseBody
    @RequestMapping("searchJson")
    public DataGridModel<DataTypes> findJson(DataGridModel<DataTypes> list,
            String keyword)
    {

        // 分页查询角色管理
        List<DataTypes> roles = dataTypeService.queryByPage(list, keyword);
        list.setContent(roles);
        return list;

    }

    /**
     * 查询管理，返回json数据，结合jqGrid做管理
     * 
     * @author LuoAnDong
     */
    @ResponseBody
    @RequestMapping("listJson")
    public DataGridModel<DataTypes> findJson(DataGridModel<DataTypes> list)
    {

        List<DataTypes> cbTypeList = listAllCbType(null);
        list.setContent(cbTypeList);

        return list;

    }
}
