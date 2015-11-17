package com.sunspot.service;

import com.sunspot.common.JsonResult;

public interface MyAppService
{
    public JsonResult appRegister(String phone, String password,
            String checkCode);

    public JsonResult login(String phone, String password);
}
