package com.sunspot.controller.manager;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.pojo.CustomInfo;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.CustomInfoService;
import com.sunspot.service.MyService;

@Controller
@Scope("prototype")
@RequestMapping("manager/customer")
public class CustomerController
{

    @Resource
    MyService service;
    
    @Resource
    CustomInfoService customInfoService;

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
    
    /**
     * 进入状态修改页面
     * @param id
     * @param status
     * @param request
     */
    @RequestMapping(value = "statusModify", method = RequestMethod.GET)
    public void statusModify(String id,String status,HttpServletRequest request)
    {
    	request.setAttribute("customId", id);
    	int flag=Integer.parseInt(status);
    	request.setAttribute("status", flag);
    }
    
    /**
     * 状态修改
     * @param id
     * @param status
     * @param request
     */
    @RequestMapping(value = "statusModify", method = RequestMethod.POST)
    public String statusModify(String id,int status,HttpServletRequest request)
    {
    	int flag=customInfoService.statusModify(id, status);
    	request.setAttribute("resultCode", flag);
    	return "rsp/submitrsp";
    }
}
