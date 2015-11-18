package com.sunspot.controller.index;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sms.Sms;

import com.sunspot.common.Utils;
import com.sunspot.service.CustomInfoService;
import com.sunspot.service.MyIndexService;

/**
 * 用户前端操作控制层
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("index/customer")
public class CustomerIndexController
{ 
	@Resource
    MyIndexService service;
	@Autowired
	private CustomInfoService customInfoService;
    /**
     * 密码找回
     */
    @RequestMapping("forgetPwd")
    public void forgetPwd(){ 
    }
    
    /**
     * 进入修改密码
     */
    @RequestMapping("changePwd")
    public String changePwd(HttpSession session){ 
        if(session.getAttribute("nextStep")== null){
            return "redirect:/index/index/toIndex.do" ;
        }else{
            session.removeAttribute("nextStep");
            return null;
        }  
    }
    
    @ResponseBody
    @RequestMapping("sendCheckCode")
    public boolean sendCheckPhone(HttpSession session,String phone)
    {
        if (StringUtils.isBlank(phone))
            return false;
        else
        {
           if(customInfoService.checkRepeatPhone(phone)) { 
        	   String checkCode = Utils.smsCheckCode();
        	   System.out.println("checkCode "+checkCode);
               Sms.sendSms(phone, checkCode) ; 
               
        	   session.setAttribute("forgetPhone", phone);
        	   session.setAttribute("forgetCode", checkCode);
        	   return true;
           }
        }
        return false;
    }
    
    @ResponseBody
    @RequestMapping(value="checkForgetCode",method=RequestMethod.POST)
    public String checkForgetCode(String checkCode,String telPhone,HttpSession session){
        Object code = session.getAttribute("forgetCode");
        if (null == code) { 
            return "checkCodeIsNull"; 
        }
        if(StringUtils.isBlank(checkCode) || session.getAttribute("forgetCode") == null){
            return "checkCodeError" ; 
        }else{
            boolean tip = checkCode.equals(session.getAttribute("forgetCode"))?true:false ; 
            if(tip){
                session.removeAttribute("checkCode");
                session.setAttribute("nextStep", "updatePwd");
                return "success";
            }else{
            	return "checkCodeError" ; 
            }
           
        }     
    }
    
    
    /**
     * 找回密码
     * @param session
     * @param model
     * @param newPasswd
     * @return
     */
    @ResponseBody
    @RequestMapping(value="doUpdatePwd",method=RequestMethod.POST)
    public Boolean doUpdatePwd(HttpSession session, Model model, String newPasswd)
    {
    	
    	String telPhone = (String)session.getAttribute("forgetPhone");
    	if(telPhone!=null){
        	customInfoService.changePwd(telPhone, newPasswd);
            return true;
    	}
  
    	return false;
    }
    
    
    
    
    /**
     * 验证码验证
     * @param checkCode
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value="checkCode",method=RequestMethod.POST)
    public String checkCode(String checkCode,String telPhone,HttpSession session){
        Object code = session.getAttribute("checkCode");
        session.setAttribute("telPhone", telPhone);
        if (null == code) { 
            return "checkCodeIsNull"; 
        }
        if(StringUtils.isBlank(checkCode) || session.getAttribute("checkCode") == null){
            return "false" ; 
        }else{
            boolean tip = checkCode.equals(session.getAttribute("checkCode"))?true:false ; 
            if(tip){
                session.removeAttribute("checkCode") ; 
                session.setAttribute("changePwdTip", "changeTip") ; 
            }
            return ""+tip;
        }     
    }
    
}
