package com.sunspot.controller.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.common.Utils;
import com.sunspot.pojo.UserInfo;
import com.sunspot.pojo.UserRole;
import com.sunspot.pojo.Valid;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.UserSessionUtil;
import com.sunspot.pojoext.ValidExt;
import com.sunspot.service.ProviderService;
import com.sunspot.service.UserInfoService;
import com.sunspot.service.UserRoleService;
import com.sunspot.service.ValidService;

/**
 * 商家控制器
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@RequestMapping("manager/provider/")
public class ProviderController
{

    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(ProviderController.class);

    /**
     * 商家业务层
     */
    @Autowired
    private ProviderService providerService;

    /**
     * 角色业务层
     */
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 商家信息层
     */
    @Autowired
    private UserInfoService userInfoService;
    
    /**
     * 验证信息
     */
    @Autowired
    private ValidService validService;
    
    /**
     * 进入控制页
     */
    @RequestMapping("list")
    public void list()
    {
    }

    /**
     *未认证商家展示页面
     *@author qkc
     */
    @RequestMapping("vali")
    public void vali(HttpServletRequest request){
    	String uid=UserSessionUtil.getUserInfo(request).getUserId();
        UserInfo t = providerService.queryById(uid);
        Valid valid=new Valid();
        valid.setUid(uid);
        request.setAttribute("bean", t);
        valid.setType(1);//个人认证
        request.setAttribute("personValid", validService.queryValid(valid));
        valid.setType(2);//商家认证
        request.setAttribute("shopValid", validService.queryValid(valid));
        valid.setType(3);//店铺认证
        request.setAttribute("placeValid", validService.queryValid(valid));
    }
    
    /**
     *验证页面
     *@author qkc
     */
    @RequestMapping(value="valid",method=RequestMethod.GET)
    public void valid(HttpServletRequest request,Integer type,String id){
        UserInfo t = providerService.queryById(UserSessionUtil.getUserInfo(request).getUserId());
        request.setAttribute("id", id);
    	request.setAttribute("type", type);
    	request.setAttribute("bean", t);
    }
    
    /**
     *提交验证页面
     *@author qkc
     */
    @RequestMapping(value="valid",method=RequestMethod.POST)
    public String doValid(HttpServletRequest request,Valid valid){
        // 修改的实体
    	int tip =0;
        UserInfo t = providerService.queryById(UserSessionUtil.getUserInfo(request).getUserId());
        if(t!=null){
            validService.add(request, valid);
            tip =1;
            request.setAttribute("resultCode", tip);
        }     
        return "rsp/submitrsp";
    }
    
    /**
     * 分页查询所有认证信息
     * @author qkc
     * @param list
     * @return
     */
    @RequestMapping("listtakeout")
    public void listtakeout(String keyword, Integer status, ModelMap modelMap)
    {
        modelMap.addAttribute("keyword", StringUtils.isNotBlank(keyword) ? keyword : "");
        modelMap.addAttribute("status", null == status ? 3 : status);
    }
    
    /**
     * 分页查询所有认证信息
     * @author qkc
     * @param list
     * @return
     */
    @RequestMapping("listtakeoutProv")
    public void listtakeoutProv(String keyword, Integer status,
            ModelMap modelMap)
    {
        modelMap.addAttribute("keyword", StringUtils.isNotBlank(keyword) ? keyword : "");
               
        modelMap.addAttribute("status", null == status ? 3 : status);
    }
    
    /**
     * 分页查询所有认证信息
     * @author qkc
     * @param list
     * @return
     */
    @ResponseBody
    @RequestMapping("listTakeoutProvJson")
    public DataGridModel<ValidExt> listTakeoutProvJson(
            DataGridModel<ValidExt> list, String keyword, Integer status,
            HttpServletRequest request)
    {
        validService.queryTakeout(list, keyword, status);
        return list;
    }
    
    /**
     * 查看信息
     * @param id
     * @param request
     */
    @RequestMapping("validDetail")
    public void validExtDetail(String id, HttpServletRequest request)
    {
        // 修改的实体
        ValidExt t = validService.queryExtById(id);
        request.setAttribute("bean", t);
    }
    
    /**
     * 审核失败
     * @param validId
     * @param request
     */
    @RequestMapping("validRefuse")
    public void validRefuse(String id, HttpServletRequest request){
        ValidExt t = validService.queryExtById(id);
        request.setAttribute("bean", t);
    }
    
    /**
     * 验证信息
     * @param request
     * @param valid
     * @return
     */
    @RequestMapping("modifyValid")
    public String modifyValid(HttpServletRequest request,Valid valid){
    	String id=valid.getId();
    	Valid entity=validService.queryById(id);
    	if(entity!=null){
    		int isValid=valid.getIsValid();
			UserInfo user=userInfoService.queryById(entity.getUid());
			
    		entity.setRemark(valid.getRemark());
    		entity.setIsValid(valid.getIsValid());
    		entity.setValidDate(Utils.getCurDate("yyyy-MM-dd HH:mm:ss"));
    		entity.setValidor(UserSessionUtil.getUserInfo(request).getUserName());
    		validService.modify(entity);
			
    		if(isValid==0||isValid==2){
    			//当商店或者地点认证被取消认证/拒绝认证
    			if(entity.getType()==2||entity.getType()==3){
    				String uid=entity.getUid();
    				boolean flag= validService.isUserPassedValid(uid);
        			if(!flag){ //没有通过别的认证
                    	UserRole userRole=userRoleService.queryUserRoleByRoleName("未认证商家").get(0);
                    	user.setUserRole(userRole);
                    	userInfoService.modifyUserRole(user);
        			}
    			}
    		}
    		
    		//认证通过操作
    		if(isValid==1){
    			 //没有通过别的认证
    			if(validService.isUserPassedValid(entity.getUid())){
    				UserRole role= userRoleService.queryRoleByUserInfo(user);
	                if("未认证商家".equals(role.getRoleName())){
	                	//将用户设置为 "邻卖商家"
	                	UserRole userRole=userRoleService.queryUserRoleByRoleName("邻卖商家").get(0);
	                	user.setUserRole(userRole);
	                	userInfoService.modifyUserRole(user);
	                }
    			}
    		}

	        request.setAttribute("resultCode", "1");
    	}else{
	        request.setAttribute("resultCode", "0");
    	}
        return "rsp/submitrsp";	
    }
    
    /**
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkRepeatName", method = RequestMethod.POST)
    public int checkRepeatName(String loginName)
    {
        return providerService.checkRepeatName(loginName);
    }

    /**
     * 删除
     * 
     * @param t
     */
    @ResponseBody
    @RequestMapping(value = "del", method = RequestMethod.POST)
    public int del(String id)
    {
        // 查询出所有角色
        return providerService.delete(id);
    }

    /**
     * 添加
     * 
     * @param t
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(HttpServletRequest request, UserInfo userInfo)
    {

        // 如果登陆名重复则直接返回　
        if (providerService.checkRepeatName(userInfo.getLoginName()) == 1)
        {
            request.setAttribute("resultCode", "0");
            return "rsp/submitrsp";
        }

        // 查询出所有角色
        String logoPath = Utils.uploadFile(request);
        runLog.info("upload path : " + logoPath);

        userInfo.setLogo(logoPath);
        userInfo.setAddDate(Utils.getNowDate());
        userInfo.setIsSysuser(1);
        userInfo.setUserType(1);
        int tip = providerService.add(userInfo);

        request.setAttribute("resultCode", tip);
        return "rsp/submitrsp";
    }

    /**
     * 进入添加页面
     * 
     * @param t
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public void enterAdd(HttpServletRequest request)
    {
        rolesList(request);
    }

    /**
     * 查询出所有角色
     * 
     * @param request
     */
    private void rolesList(HttpServletRequest request)
    {
        // 查询出所有角色
        List<UserRole> beans = userRoleService.queryByNoPage();
        request.setAttribute("beans", beans);
    }

    /**
     * 保存修改信息
     * 
     * @param t
     */
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public String modify(UserInfo t, HttpServletRequest request)
    {
        // 查询出所有角色
        String logoPath = Utils.uploadFile(request);
        t.setLogo(logoPath);
        int tip = providerService.modify(t);
        request.setAttribute("resultCode", tip);
        return "rsp/submitrsp";
    }

    /**
     * 进入修改页面
     * 
     * @param t
     */
    @RequestMapping(value = "modify", method = RequestMethod.GET)
    public void enterModify(String id, HttpServletRequest request)
    {
        // 修改的实体
        UserInfo t = providerService.queryById(id);
        request.setAttribute("bean", t);
        // 角色
        rolesList(request);
    }

    /**
     * 查询角色管理，返回json数据，结合jqGrid做管理
     * 
     * @author LuoAnDong
     */
    @ResponseBody
    @RequestMapping("listJson")
    public DataGridModel<UserInfo> findRole(DataGridModel<UserInfo> list,
            String keyword)
    {

        if (list == null)
            return null;
        // 分页查询角色管理
        List<UserInfo> roles = providerService.queryByPage(list, keyword);
        list.setContent(roles);

        return list;

    }

    /**
     * 进入备注页面　
     * 
     * @author LuoAnDong
     */
    @RequestMapping(value = "remark", method = RequestMethod.GET)
    public void remark(String id, HttpServletRequest request)
    {
        UserInfo userInfo = providerService.queryRemark(id);
        request.setAttribute("bean", userInfo);
    }

    /**
     * 进入详情页面　
     * 
     * @param t
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public void detail(HttpServletRequest request)
    {
        // 修改的实体
        UserInfo t = providerService.queryById(UserSessionUtil.getUserInfo(
                request).getUserId());
        request.setAttribute("bean", t);
    }

    /**
     * 进入详情页面　
     * 
     * @param t
     */
    @RequestMapping(value = "detail1", method = RequestMethod.GET)
    public void detail1(String id, HttpServletRequest request)
    {
        // 修改的实体
        UserInfo t = providerService.queryById(id);
        request.setAttribute("bean", t);
    }
}
