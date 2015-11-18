package com.sunspot.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.sunspot.common.DateUtil;
import com.sunspot.common.JsonResult;
import com.sunspot.common.MD5;
import com.sunspot.common.MyConstants;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.CustomInfo;
import com.sunspot.service.MyAppService;

@Service
@Scope("prototype")
public class MyAppServiceImpl implements MyAppService
{
    @Resource
    BaseDao baseDao;

    @Override
    public JsonResult appRegister(String phone, String password,
            String checkCode)
    {
        List<CustomInfo> check = baseDao.query(
                "select TELPHONE from custom_info where TELPHONE=?",
                new String[]
                { phone }, CustomInfo.class);
        if (check.size() > 0) { return new JsonResult(MyConstants.ERROR); }
        CustomInfo customer = new CustomInfo();
        customer.setAddDate(DateUtil.getCurrentDateTime());
        customer.setTelphone(phone);
        customer.setLoginPassword(MD5.encrypt(password));
        baseDao.add(customer);
        return new JsonResult(MyConstants.SUCCESS);
    }

    @Override
    public JsonResult login(String phone, String password)
    {
        List<CustomInfo> list = baseDao.query(
                "select * from custom_info where telphone=?", new Object[]
                { phone }, CustomInfo.class);
        if (list.size() > 0)
        {
            CustomInfo customInfo = list.get(0);
            if (!MD5.encrypt(password).equalsIgnoreCase(
                    customInfo.getLoginPassword())) { return new JsonResult(
                    "false"); }
            return new JsonResult(customInfo);
        }
        else
        {
            return new JsonResult("false");
        }
    }
}
