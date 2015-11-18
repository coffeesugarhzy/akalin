package com.sunspot.controller.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.pojo.Area;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.AreaService;

/**
 * 区域控制器<br/>
 * 路径：manager/area/方法 , 默认添加的方法 del , add(post/get)
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("manager/area")
public class AreaController
{
    /**
     * 引入业务层
     */
    @Autowired
    private AreaService areaService;

    /**
     * 查询出所有城市　
     */
    @ResponseBody
    @RequestMapping("listCity")
    public List<Area> listCity(String parentId)
    {
        List<Area> citys = areaService.queryByParent(parentId);
        return citys;
    }

    /**
     * 进入控制页
     */
    @RequestMapping("list")
    public void list(HttpServletRequest request, String parentId)
    {
        // 查询出所有省
        List<Area> provinces = areaService.queryByParent("0");
        request.setAttribute("provinces", provinces);
    }

    /**
     * 搜索管理，返回json数据，结合jqGrid做管理
     * 
     * @author LuoAnDong
     */
    @ResponseBody
    @RequestMapping("search")
    public DataGridModel<Area> search(DataGridModel<Area> list, String parentId)
    {

        if (list == null)
            return null;
        // 分页查询角色管理
        List<Area> roles = areaService.queryByParent(list, parentId);
        list.setContent(roles);

        return list;

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
     * 添加实体
     * 
     * @param t
     */
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public int add(Area t)
    {
        return areaService.add(t);
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
        return areaService.delete(id);
    }

    /**
     * 保存修改信息
     * 
     * @param t
     */
    @ResponseBody
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public int modify(Area t)
    {
        try
        {
            areaService.modify(t);
            return 1;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    /**
     * 进入修改页面
     * 
     * @param t
     */
    @RequestMapping(value = "modify", method = RequestMethod.GET)
    public void enterModify(String id, HttpServletRequest request)
    {
        Area t = areaService.queryById(id);
        request.setAttribute("bean", t);
    }

    /**
     * 查询角色管理，返回json数据，结合jqGrid做管理
     * 
     * @author LuoAnDong
     */
    @ResponseBody
    @RequestMapping("listJson")
    public DataGridModel<Area> findArea(DataGridModel<Area> list, String keyword)
    {

        if (list == null)
            return null;
        // 分页查询角色管理
        List<Area> roles = areaService.queryByPage(list, keyword);
        list.setContent(roles);

        return list;

    }
}
