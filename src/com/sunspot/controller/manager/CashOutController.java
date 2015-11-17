package com.sunspot.controller.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.pojo.CashOut;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.CashOutService;

/**
 * 提现
 * @author LuoAnDong
 *
 */
@Controller
@RequestMapping("manager/cashout")
public class CashOutController
{
    /**
     * 提取业务　
     */
    @Autowired 
    private CashOutService cashOutService ;
    
    /**
     * 进入控制页
     */
    @RequestMapping("list")
    public void list(){}
     
    /**
     * 添加实体
     * @param t
     */
    @RequestMapping(value="add" , method = RequestMethod.POST)
    public void add(CashOut t){
        cashOutService.add(t) ; 
    }
    
    /**
     * 进入添加页面
     * @param t
     */
    @RequestMapping(value="add" , method = RequestMethod.GET)
    public void enterAdd(){ 
    }
    
    /**
     * 修改提取状态　
     * @param t
     */
    @ResponseBody
    @RequestMapping(value="modify" , method = RequestMethod.POST)
    public int modify(CashOut t){  
        return cashOutService.modifyStatus(t) ; 
    }
    
    /**
     * 进入修改页面
     * @param t
     */
    @RequestMapping(value="modify" , method = RequestMethod.GET)
    public void enterModify(String id , HttpServletRequest request){ 
        CashOut t = cashOutService.queryById(id) ; 
        request.setAttribute("bean", t) ; 
    }

    /**
     * 查询角色管理，返回json数据，结合jqGrid做管理
     * @author LuoAnDong
     */
    @ResponseBody
    @RequestMapping("listJson")
    public DataGridModel<CashOut> findRole(DataGridModel<CashOut> list , String keyword){
        
        if(list == null) return null ;  
        //分页查询角色管理
        List<CashOut> roles = cashOutService.queryByPage(list , keyword) ;   
        list.setContent(roles); 
        
        return list ; 
        
    }
}
