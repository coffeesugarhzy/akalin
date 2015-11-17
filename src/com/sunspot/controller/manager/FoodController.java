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
import com.sunspot.pojo.FoodExp;
import com.sunspot.pojo.Shop;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.MyService;

@Controller
@Scope("prototype")
@RequestMapping("manager/food")
public class FoodController
{

    @Resource
    MyService service;

    @RequestMapping("update")
    public String update(String id, Model model,
            MultipartHttpServletRequest request, FoodExp food)
    {
        FoodExp update = service.getFoodExp(id);
        String uploadUrl = MyUtil.upload(request);
        if (StringUtils.isNotBlank(uploadUrl))
        {
            String delFileName = update.getLogo();
            if (StringUtils.isNotBlank(delFileName))
            {
                MyUtil.removeFile(delFileName, request);
            }
            food.setLogo(uploadUrl);
        }
        else
        {
            food.setLogo(update.getLogo());
        }
        food.setFoodExpId(id);
        food.setAddDate(update.getAddDate());
        food.setUpdateDate(DateUtil.getCurrentDateTime());
        BeanUtils.copyProperties(food, update);
        service.update(update);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("add")
    public String add(Model model, MultipartHttpServletRequest request,
            FoodExp food)
    {
        String uploadUrl = MyUtil.upload(request);
        if (StringUtils.isNotBlank(uploadUrl))
        {
            food.setLogo(uploadUrl);
        }
        food.setAddDate(DateUtil.getCurrentDateTime());
        service.add(food);
        model.addAttribute("resultCode", 1);
        return "rsp/submitrsp";
    }

    @RequestMapping("toUpdate")
    public void toUpdate(String id, Model model)
    {
        FoodExp food = service.getFoodExp(id);
        if (null != food)
        {
            model.addAttribute("food", food);
        }
        List<Shop> shop = service.getShop();
        if (shop.size() > 0)
        {
            model.addAttribute("shop", shop);
        }
    }

    @RequestMapping("toAdd")
    public void toAdd(Model model)
    {
        List<Shop> shop = service.getShop();
        if (shop.size() > 0)
        {
            model.addAttribute("shop", shop);
        }
    }

    @RequestMapping("listFood")
    public void listFood()
    {
    }

    @ResponseBody
    @RequestMapping("queryFood")
    public DataGridModel<FoodExp> queryFood(DataGridModel<FoodExp> list,
            String keyword)
    {
        List<FoodExp> foodList = service.queryFoodExp(list, keyword);
        list.setContent(foodList);
        list.setRecords(foodList.size());
        return list;
    }
}
