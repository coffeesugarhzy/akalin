package com.sunspot.controller.manager;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.pojo.Shop;
import com.sunspot.pojoext.CommentsExt;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.CommentsService;
import com.sunspot.service.ShopService;

/**
 * 评论查询控制层
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("manager/comments")
public class CommentsController
{

    /**
     * 注入业务层管理
     */
    @Autowired
    private CommentsService commentsService;

    /**
     * 注入业务层管理
     */
    @Autowired
    private ShopService shopService;

    /**
     * 跳转评论页面
     */
    @RequestMapping("comments")
    public void commennts(HttpServletRequest request, ModelMap modelMap)
    {
        Shop shop = shopService.queryByUserId(request);
        modelMap.addAttribute("commentsNum",
                (null != shop) ? commentsService.countComment(shop.getShopId())
                        : 0);
        modelMap.addAttribute("shopMarks", (null != shop) ? shop.getMarks() : 0);
    }

    /**
     * 跳转评论页面
     */
    @RequestMapping("detail")
    public void detail(String id, ModelMap modelMap)
    {
        modelMap.addAttribute("comment", commentsService.queryById(id));
    }

    /**
     * 查询生鲜数据
     * 
     * @param request
     *            请求对象
     * @param list
     *            返回结果
     * @param keyword
     *            查询条件
     * @return list
     */
    @ResponseBody
    @RequestMapping("listJson")
    public DataGridModel<CommentsExt> listJson(HttpServletRequest request,
            DataGridModel<CommentsExt> list, Integer queryType)
    {
        Shop shop = shopService.queryByUserId(request);
        if (null != shop)
        {
            commentsService.queryPage(list, shop.getShopId(), queryType);
        }

        return list;
    }

}
