package com.sunspot.controller.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.common.Utils;
import com.sunspot.pojo.UserMenus;
import com.sunspot.pojo.UserRole;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.MenusService;
import com.sunspot.service.UserRoleService;

/**
 * 角色管理控制器
 * @author LuoAnDong
 *
 */
@Controller
@RequestMapping("manager/sys")
public class RoleController
{
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(RoleController.class);  
    
    /**
     * 菜单管理
     */
    @Autowired
    private MenusService menusService ; 
    
    /**
     * 角色业务层
     */
    @Autowired
    private UserRoleService userRoleService ; 
    
    /**
     * 查询角色管理，返回json数据，结合jqGrid做管理
     * @author LuoAnDong
     */
    @ResponseBody
    @RequestMapping("findRole")
    public DataGridModel<UserRole> findRole(DataGridModel<UserRole> list , String keyword){
         
        //分页查询角色管理
        List<UserRole> roles = userRoleService.queryRoleByPage(list , Utils.transformChar(keyword)) ;   
        list.setContent(roles); 
        
        return list ; 
        
    }
    
    /**
     * 删除权限 
     * @author LuoAnDong
     */
    @ResponseBody
    @RequestMapping(value="delRole", method = RequestMethod.POST)
    public int delRole(String roleId){
        runLog.info("delete role id = " + roleId) ; 
        int optTip = userRoleService.deleteRole(roleId) ;   
        return optTip ; 
    }
    
    /**
     * 更新权限　
     * @return
     */
    @ResponseBody
    @RequestMapping(value="updateRole", method = RequestMethod.POST)
    public void updateRole(String roleId , String powerArr){ 
        runLog.info("update role id = " + roleId + " , power arr = " + powerArr); 
        userRoleService.updateRoleRight(roleId , powerArr) ;  
    }
    
    /**
     * 权限列表　
     * @author LuoAnDong
     */
    @RequestMapping("editPower")
    public void editPower(String roleId , HttpServletRequest request){
        //查询出所有菜单　 
        List<UserMenus> allMenusList = menusService.queryAllMenus() ;  
        List<UserMenus> menusList = menusService.queryMenusByRoleId(roleId) ;
        
        request.setAttribute("allMenusJson", Utils.ObjectToJson(allMenusList)) ; 
        request.setAttribute("menusJson" , Utils.ObjectToJson(menusList)) ; 
        request.setAttribute("roleId", roleId) ; 
        
    } 
    
    /**
     * 角色管理，返回json数据，结合jqGrid做管理
     * @author LuoAnDong
     */
    @ResponseBody
    @RequestMapping("rolesList")
    public DataGridModel<UserRole> roles(DataGridModel<UserRole> list){
        
        //分页查询角色管理
        List<UserRole> roles = userRoleService.queryRoleByPage(list) ;   
       
        list.setContent(roles); 
        
        return list ; 
        
    }
    
    /**
     * 进入到添加角色页面　　
     * @author LuoAnDong
     */
    @RequestMapping(value="addRole" , method = RequestMethod.GET)
    public void enterRolePage(HttpServletRequest request){  
        //查询所有菜单　
        List<UserMenus> allMenusList = menusService.queryAllMenus() ;   
        request.setAttribute("allMenusJson", Utils.ObjectToJson(allMenusList)) ; 
    }
    
    /**
     * 添加角色,保存角色信息
     */ 
    @ResponseBody
    @RequestMapping(value="addRole" , method = RequestMethod.POST)
    public void addRole(UserRole role , String powerArr){   
          
        role.setAddDate(Utils.getNowDate()) ; 
        role.setIsSysrole(1) ;  
        userRoleService.saveRole(role , powerArr) ; 
         
    } 
    
    /**
     * 角色跳转
     */
    @RequestMapping("roles")
    public void roles(){}
}
