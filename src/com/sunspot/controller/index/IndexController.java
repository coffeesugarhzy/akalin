package com.sunspot.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 舌尖前端
 * 
 * @author LuoAnDong
 * 
 */
@Controller
public class IndexController
{
   /**
    * 前端首页
    * @param modelMap
    * @return
    */
    @RequestMapping("/index")
    public String index(ModelMap modelMap)
    {
        return "redirect:/index/index/toIndex.do";
    }

    /**
     * 前端首页
     * @param modelMap
     * @return
     */
     @RequestMapping("/indexsec")
     public String indexsec(ModelMap modelMap)
     {
         return "/index/index";
     }
}
