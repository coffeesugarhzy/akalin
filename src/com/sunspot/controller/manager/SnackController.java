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
import com.sunspot.pojo.Snack;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.MyService;

@Controller
@Scope("prototype")
@RequestMapping("manager/snack")
public class SnackController
{

    @Resource
    MyService service;

    @RequestMapping("update")
    public String update(String id, Model model,
            MultipartHttpServletRequest request, Snack snack)
    {
        Snack update = service.getSnack(id);
        String uploadUrl = MyUtil.upload(request);
        if (StringUtils.isNotBlank(uploadUrl))
        {
            String delFileName = update.getLogo();
            if (StringUtils.isNotBlank(delFileName))
            {
                MyUtil.removeFile(delFileName, request);
            }
            snack.setLogo(uploadUrl);
        }
        else
        {
            snack.setLogo(update.getLogo());
        }
        snack.setSnackId(id);
        snack.setAddDate(update.getAddDate());
        snack.setUpdateDate(DateUtil.getCurrentDateTime());
        BeanUtils.copyProperties(snack, update);
        service.update(update);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("add")
    public String add(Model model, MultipartHttpServletRequest request,
            Snack snack)
    {
        String uploadUrl = MyUtil.upload(request);
        if (StringUtils.isNotBlank(uploadUrl))
        {
            snack.setLogo(uploadUrl);
        }
        snack.setAddDate(DateUtil.getCurrentDateTime());
        service.add(snack);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("toUpdate")
    public void toUpdate(String id, Model model)
    {
        Snack snack = service.getSnack(id);
        if (null != snack)
        {
            model.addAttribute("snack", snack);
        }
    }

    @RequestMapping("toAdd")
    public void toAdd()
    {

    }

    @RequestMapping("listSnack")
    public void listSnack()
    {
    }

    @ResponseBody
    @RequestMapping("querySnack")
    public DataGridModel<Snack> querySnack(DataGridModel<Snack> list,
            String keyword)
    {
        List<Snack> snackList = service.querySnack(list, keyword);
        list.setContent(snackList);
        list.setRecords(snackList.size());
        return list;
    }
}
