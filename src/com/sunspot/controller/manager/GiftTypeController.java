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
import com.sunspot.pojo.GiftType;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.MyService;

@Controller
@Scope("prototype")
@RequestMapping("manager/giftType")
public class GiftTypeController
{

    @Resource
    MyService service;

    @RequestMapping("update")
    public String update(String id, Model model, GiftType type)
    {
        GiftType update = service.getGiftType(id);
        type.setGiftTypeId(id);
        type.setAddDate(update.getAddDate());
        BeanUtils.copyProperties(type, update);
        service.update(update);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("add")
    public String add(Model model, GiftType type)
    {
        type.setAddDate(DateUtil.getCurrentDateTime());
        service.add(type);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("toUpdate")
    public void toUpdate(String id, Model model)
    {
        GiftType type = service.getGiftType(id);
        if (null != type)
        {
            model.addAttribute("type", type);
        }
    }

    @RequestMapping("toAdd")
    public void toAdd()
    {

    }

    @RequestMapping("listGiftType")
    public void listGiftType()
    {
    }

    @ResponseBody
    @RequestMapping("queryGiftType")
    public DataGridModel<GiftType> queryGiftType(DataGridModel<GiftType> list,
            String keyword)
    {
        List<GiftType> giftTypeList = service.queryGiftType(list, keyword);
        list.setContent(giftTypeList);
        list.setRecords(giftTypeList.size());
        return list;
    }
}
