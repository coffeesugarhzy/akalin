package com.sunspot.controller.manager;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.common.DateUtil;
import com.sunspot.common.MD5;
import com.sunspot.pojo.UserInfo;
import com.sunspot.pojo.UserRole;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.MyService;

@Controller
@Scope("prototype")
@RequestMapping("manager/user")
public class UserInfoController
{

    @Resource
    MyService service;

    @RequestMapping("update")
    public String update(String id, Model model, UserInfo user, String roleId)
    {
        UserInfo update = service.getUserInfo(id);
        String pwd = user.getLoginPassword();
        if (StringUtils.isNotBlank(pwd))
        {
            user.setLoginPassword(MD5.encrypt(pwd));
        }
        else
        {
            user.setLoginPassword(update.getLoginPassword());
        }
        UserRole userRole = service.getUserRoleById(roleId);
        user.setUserRole(userRole);
        user.setUserId(id);
        user.setAddDate(update.getAddDate());
        user.setUpdateDate(DateUtil.getCurrentDateTime());
        BeanUtils.copyProperties(user, update);
        service.update(update);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("add")
    public String add(Model model, UserInfo user, String roleId)
    {
        UserRole userRole = service.getUserRoleById(roleId);
        user.setUserRole(userRole);
        user.setLoginPassword(MD5.encrypt(user.getLoginPassword()));
        user.setAddDate(DateUtil.getCurrentDateTime());
        service.add(user);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("toUpdate")
    public void toUpdate(String id, Model model)
    {
        UserInfo user = service.getUserInfo(id);
        if (null != user)
        {
            model.addAttribute("user", user);
        }
        List<UserRole> list = service.getUserRole();
        if (list.size() > 0)
        {
            model.addAttribute("list", list);
        }
    }

    @RequestMapping("toAdd")
    public void toAdd(Model model)
    {
        List<UserRole> list = service.getUserRole();
        if (list.size() > 0)
        {
            model.addAttribute("list", list);
        }
    }

    @RequestMapping("listUser")
    public void listUser()
    {
    }

    @ResponseBody
    @RequestMapping("queryUser")
    public DataGridModel<UserInfo> queryUser(DataGridModel<UserInfo> list,
            String keyword)
    {
        List<UserInfo> userList = service.queryUserInfo(list, keyword);
        list.setContent(userList);
        list.setRecords(userList.size());
        return list;
    }
}
