package com.sunspot.controller.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.pojo.CustomInfo;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.MyService;

@Controller
@Scope("prototype")
@RequestMapping("manager/customer")
public class CustomerController
{

    @Resource
    MyService service;

    @RequestMapping("listCustomer")
    public void listCustomer()
    {
    }

    @ResponseBody
    @RequestMapping("queryCustomer")
    public DataGridModel<CustomInfo> queryCustomer(
            DataGridModel<CustomInfo> list, String keyword)
    {
        List<CustomInfo> customerList = service.queryCustomInfo(list, keyword);
        list.setContent(customerList);
        list.setRecords(customerList.size());
        return list;
    }
}
