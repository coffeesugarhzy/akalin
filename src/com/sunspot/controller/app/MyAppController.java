package com.sunspot.controller.app;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.common.JsonResult;
import com.sunspot.service.MyAppService;

@Controller
@Scope("prototype")
@RequestMapping("app")
public class MyAppController
{

    @Resource
    MyAppService appService;

    @ResponseBody
    @RequestMapping("appRegister")
    public JsonResult appRegister(String phone, String password,
            String checkCode)
    {
        return appService.appRegister(phone, password, checkCode);
    }

    @ResponseBody
    @RequestMapping("login")
    public JsonResult login(String phone, String password)
    {
        return appService.login(phone, password);
    }
}
