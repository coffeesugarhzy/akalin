package com.sunspot.controller.manager;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunspot.service.MenusService;
import com.sunspot.service.UserRoleService;

/**
 * 积分管理
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("manager/integral/")
public class IntegralController
{

    /**
     * 角色业务层
     */
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 菜单业务层
     */
    @Autowired
    private MenusService menusService;

    /**
     * 进入积分管理首页
     * 
     * @author LuoAnDong
     */
    @RequestMapping("list")
    public void list(ModelMap modelMap,String menuId , HttpServletRequest request){ 
        
    }

}
