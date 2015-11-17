package com.sunspot.service;

import java.util.List;

import com.sunspot.pojo.UserInfo;
import com.sunspot.pojo.UserRole;
import com.sunspot.pojoext.DataGridModel;

/**
 * 角色业务层
 * @author LuoAndong
 *
 */
public interface UserRoleService {

	/**
	 * 通过用户信息查询角色
	 * @param userInfo 用户实体
	 * @return 存在则返回  角色信息  否则返回空
	 * @author LuoAndong
	 */
	UserRole queryRoleByUserInfo(UserInfo userInfo);

	/**
	 * 分页查询角色管理，默认为15条为一页
	 * @param list 
	 * @return List<UserRole> 集合
	 */
    List<UserRole> queryRoleByPage(DataGridModel<UserRole> list);

    /**
     * 添加角色
     * @param role 角色实体类
     * @param 权限列表
     * @author LuoAndong 
     */
    void saveRole(UserRole role, String powerArr);

    /**
     * 更新用户权限
     * @param roleId
     * @param powerArr
     */
    void updateRoleRight(String roleId, String powerArr);

    /**
     * 关键字查询角色管理　
     * @param list
     * @param keyword
     * @return
     */
    List<UserRole> queryRoleByPage(DataGridModel<UserRole> list, String keyword);

    /**
     * 删除角色，如果不存在其它关联用户，则删除，否则不删除
     * @param roleId 角色id
     * @return 删除成功则返回1 存在关联用户则返回2 删除失败则返回0
     * @author LuoAnDong
     */
    int deleteRole(String roleId);

    /**
     * 查询出所有角色　
     * @return
     */
    List<UserRole> queryByNoPage();

    
    /**
     * 由角色名字 查询角色信息
     * @param roleName
     * @return
     */
    List<UserRole> queryUserRoleByRoleName(String roleName);
}
