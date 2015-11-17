package com.sunspot.controller.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.common.DateUtil;
import com.sunspot.pojo.AppVersion;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.MyService;

@Controller
@Scope("prototype")
@RequestMapping("manager/app")
public class AppController
{

    @Resource
    MyService service;

    @RequestMapping("del")
    public String del(Model model, String id)
    {
        service.del(service.getAppVersion(id));
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("update")
    public String update(String id, Model model, AppVersion version)
    {
        AppVersion update = service.getAppVersion(id);
        version.setVersionId(id);
        version.setAddDate(update.getAddDate());
        BeanUtils.copyProperties(version, update);
        service.update(update);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("add")
    public String add(Model model, AppVersion version)
    {
        version.setAddDate(DateUtil.getCurrentDateTime());
        service.add(version);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("toUpdate")
    public void toUpdate(String id, Model model)
    {
        AppVersion version = service.getAppVersion(id);
        if (null != version)
        {
            model.addAttribute("version", version);
        }
    }

    @RequestMapping("toAdd")
    public void toAdd()
    {

    }

    @RequestMapping("listAppVer")
    public void listAppVer()
    {
    }

    @ResponseBody
    @RequestMapping("queryAppVer")
    public DataGridModel<AppVersion> queryAppVer(
            DataGridModel<AppVersion> list, String keyword)
    {
        List<AppVersion> versionList = service.queryAppVersion(list, keyword);
        list.setContent(versionList);
        list.setRecords(versionList.size());
        return list;
    }
}
