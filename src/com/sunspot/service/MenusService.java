package com.sunspot.service;

import java.util.List;

import com.sunspot.pojo.UserMenus;
import com.sunspot.pojo.UserRole;

/**
 * 菜单业务层接口
 * @author LuoAnDong 
 */
public interface MenusService {

	/**
	 * 通过角色查询顶级菜单列表 
	 * @param userRole 角色
	 * @return 返回顶级菜单列表 
	 * @param integer  菜单类型 0为系统用户  1为商家
	 * @author LuoAnDong 
	 */
	List<UserMenus> queryMainMenuByRole(UserRole userRole, int isSystem);
	
	/**
	 * 通过角色查询菜单列表 
	 * @param userRole 角色
	 * @return 返回顶级菜单列表 
	 * @param integer  菜单类型 0为系统用户  1为商家
	 * @author LuoAnDong 
	 */
	List<UserMenus> queryMenuByRole(UserRole userRole , int isSystem) ;

	/**
	 * 通过用户权限去查询顶级菜单下的子菜单信息
	 * @param userRole
	 * @param menuId
	 * @return 
	 * @author LuoAnDong
	 */
    List<UserMenus> querySubMenuByParantId(UserRole userRole, String menuId , int isSystem);

    /**
      * 根据菜单id查询菜单信息，这里只是查询出模块的url路径，如果是父类，则查询出 模块名称　
     * @param menuId 菜单id
     * @return 有则返回菜单信息，否则返回null
     * @author LuoAnDong
     */
    UserMenus queryMenuUrlById(String menuId);

    /**
     * 根据角色查询出所有的菜单　
     * @param roleId 角色id
     * @return
     * @author LuoAnDong
     */
    List<UserMenus> queryMenusByRoleId(String roleId);

    /**
     * 查询出所有的菜单　
     * @return
     * @author LuoAnDong
     */
    List<UserMenus> queryAllMenus(); 

}
