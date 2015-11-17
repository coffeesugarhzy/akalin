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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sunspot.common.DateUtil;
import com.sunspot.common.MyUtil;
import com.sunspot.pojo.Gift;
import com.sunspot.pojo.GiftType;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.MyService;

@Controller
@Scope("prototype")
@RequestMapping("manager/present")
public class PresentController
{

    @Resource
    MyService service;

    @RequestMapping("update")
    public String update(String id, Model model,
            MultipartHttpServletRequest request, Gift gift, String giftTypeId)
    {
        Gift update = service.getGift(id);
        GiftType giftType = service.getGiftType(giftTypeId);
        if (null != giftType)
        {
            gift.setGiftType(giftType);
        }
        String uploadUrl = MyUtil.upload(request);
        if (StringUtils.isNotBlank(uploadUrl))
        {
            String delFileName = update.getGiftPic();
            if (StringUtils.isNotBlank(delFileName))
            {
                MyUtil.removeFile(delFileName, request);
            }
            gift.setGiftPic(uploadUrl);
        }
        else
        {
            gift.setGiftPic(update.getGiftPic());
        }
        gift.setGiftId(id);
        gift.setAddDate(update.getAddDate());
        BeanUtils.copyProperties(gift, update);
        service.update(update);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("add")
    public String add(Model model, MultipartHttpServletRequest request,
            Gift gift, String giftTypeId)
    {
        GiftType giftType = service.getGiftType(giftTypeId);
        if (null != giftType)
        {
            gift.setGiftType(giftType);
        }
        String uploadUrl = MyUtil.upload(request);
        if (StringUtils.isNotBlank(uploadUrl))
        {
            gift.setGiftPic(uploadUrl);
        }
        gift.setAddDate(DateUtil.getCurrentDateTime());
        service.add(gift);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("toUpdate")
    public void toUpdate(String id, Model model)
    {
        List<GiftType> typeList = service.getGiftTypeList();
        if (typeList.size() > 0)
        {
            model.addAttribute("typeList", typeList);
        }
        Gift gift = service.getGift(id);
        if (null != gift)
        {
            model.addAttribute("gift", gift);
        }
    }

    @RequestMapping("toAdd")
    public void toAdd(Model model)
    {
        List<GiftType> typeList = service.getGiftTypeList();
        if (typeList.size() > 0)
        {
            model.addAttribute("typeList", typeList);
        }
    }

    @RequestMapping("listPresent")
    public void listPresent()
    {
    }

    @ResponseBody
    @RequestMapping("queryPresent")
    public DataGridModel<Gift> queryPresent(DataGridModel<Gift> list,
            String keyword)
    {
        List<Gift> giftList = service.queryPresent(list, keyword);
        list.setContent(giftList);
        list.setRecords(giftList.size());
        return list;
    }
}
