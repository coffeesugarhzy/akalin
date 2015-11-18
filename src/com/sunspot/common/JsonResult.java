package com.sunspot.common;

import com.sunspot.pojo.CustomInfo;

public class JsonResult
{

    public JsonResult(CustomInfo customer)
    {
        this.customer = customer;
    }

    private CustomInfo customer;

    public CustomInfo getCustomer()
    {
        return customer;
    }

    public void setCustomer(CustomInfo customer)
    {
        this.customer = customer;
    }

    public JsonResult(String result)
    {
        this.result = result;
    }

    private String result;

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

}
