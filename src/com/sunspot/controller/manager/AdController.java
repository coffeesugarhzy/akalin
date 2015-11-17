package com.sunspot.controller.manager;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.sunspot.pojo.Advertising;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.MyService;

@Controller
@Scope("prototype")
@RequestMapping("manager/ad")
public class AdController
{

    @Resource
    MyService service;

    @RequestMapping("update")
    public String update(String id, Model model,
            MultipartHttpServletRequest request, Advertising ad)
    {
        Advertising update = service.getAdvertising(id);
        String uploadUrl = MyUtil.upload(request);
        if (StringUtils.isNotBlank(uploadUrl))
        {
            String delFileName = update.getAdLogo();
            if (StringUtils.isNotBlank(delFileName))
            {
                MyUtil.removeFile(delFileName, request);
            }
            ad.setAdLogo(uploadUrl);
        }
        else
        {
            ad.setAdLogo(update.getAdLogo());
        }
        ad.setAdId(id);
        ad.setAddDate(update.getAddDate());
        ad.setUpdateDate(DateUtil.getCurrentDateTime());
        BeanUtils.copyProperties(ad, update);
        service.update(update);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("del")
    public String del(Model model, String id, HttpServletRequest request)
    {
        Advertising ad = service.getAdvertising(id);
        MyUtil.removeFile(ad.getAdLogo(), request);
        service.del(ad);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("add")
    public String add(Model model, MultipartHttpServletRequest request,
            Advertising ad)
    {
        String uploadUrl = MyUtil.upload(request);
        if (StringUtils.isNotBlank(uploadUrl))
        {
            ad.setAdLogo(uploadUrl);
        }
        ad.setAddDate(DateUtil.getCurrentDateTime());
        service.add(ad);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("toUpdate")
    public void toUpdate(String id, Model model)
    {
        Advertising ad = service.getAdvertising(id);
        if (null != ad)
        {
            model.addAttribute("ad", ad);
        }
    }

    @RequestMapping("toAdd")
    public void toAdd()
    {

    }

    @RequestMapping("listAd")
    public void listAd()
    {
    }

    @ResponseBody
    @RequestMapping("queryAd")
    public DataGridModel<Advertising> queryAd(DataGridModel<Advertising> list,
            String keyword)
    {
        List<Advertising> adList = service.queryAd(list, keyword);
        list.setContent(adList);
        list.setRecords(adList.size());
        return list;
    }
}
