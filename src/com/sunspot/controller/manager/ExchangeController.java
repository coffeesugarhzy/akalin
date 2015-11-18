package com.sunspot.controller.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.pojo.CustomInfo;
import com.sunspot.pojo.GiftExchange;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.MyService;

@Controller
@Scope("prototype")
@RequestMapping("manager/exchange")
public class ExchangeController
{

    @Resource
    MyService service;

    @RequestMapping("update")
    public String update(String id, Model model, GiftExchange giftExchange)
    {
        GiftExchange update = service.getGiftExchange(id);
        giftExchange.setExchangeId(id);
        BeanUtils.copyProperties(giftExchange, update);
        service.update(update);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("add")
    public String add(Model model, GiftExchange giftExchange)
    {
        service.add(giftExchange);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("toShow")
    public void toShow(String id, Model model)
    {
        GiftExchange giftExchange = service.getGiftExchange(id);
        if (null != giftExchange)
        {
            CustomInfo customer = service.getCustomInfoById(giftExchange
                    .getCustomerId());
            if (null != customer)
            {
                model.addAttribute("customer", customer);
            }
            model.addAttribute("giftExchange", giftExchange);
        }
    }

    @RequestMapping("toAdd")
    public void toAdd()
    {

    }

    @RequestMapping("listGiftExchange")
    public void listGiftExchange()
    {
    }

    @ResponseBody
    @RequestMapping("queryGiftExchange")
    public DataGridModel<GiftExchange> queryGiftType(
            DataGridModel<GiftExchange> list, String keyword)
    {
        List<GiftExchange> giftExchangeList = service.queryGiftExchange(list,
                keyword);
        list.setContent(giftExchangeList);
        list.setRecords(giftExchangeList.size());
        return list;
    }
}
