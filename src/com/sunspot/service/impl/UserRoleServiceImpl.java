package com.sunspot.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.common.Utils;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.UserInfo;
import com.sunspot.pojo.UserMenus;
import com.sunspot.pojo.UserRight;
import com.sunspot.pojo.UserRole;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.UserRoleService;

/**
 * 角色业务层
 * 
 * @author LuoAndong
 * 
 */
@Service
public class UserRoleServiceImpl implements UserRoleService
{

    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(UserRoleServiceImpl.class);

    /**
     * 持久层操作类
     */
    @Autowired
    private BaseDao baseDao;

    /**
     * 通过用户信息查询角色
     * 
     * @param userInfo
     *            用户实体
     * @return 存在则返回 角色信息 否则返回空
     * @author LuoAndong
     */
    @Override
    public UserRole queryRoleByUserInfo(UserInfo userInfo)
    {
        try
        {
            UserRole role = baseDao
                    .queryForObject(
                            "select role_id , role_name from user_role where role_id=?",
                            new Object[]
                            { userInfo.getUserRole().getRoleId() },
                            UserRole.class);

            if (role != null)
            {
                List<UserRight> userRightList = baseDao
                        .query("select menu_id , right_id from user_right where role_id=?",
                                new Object[]
                                { role.getRoleId() }, UserRight.class);
                role.setUserRights(userRightList);
                return role;
            }
        }
        catch (Exception e)
        {
            runLog.error(e.getMessage());
        }
        return null;
    }

    /**
     * 分页查询角色管理，默认为15条为一页
     * 
     * @param list
     * @return List<UserRole> 集合
     */
    @Override
    public List<UserRole> queryRoleByPage(DataGridModel<UserRole> list)
    {
        int total = baseDao.queryForIntPage("select count(*) from user_role");
        list.setCount(total);
        return baseDao.query(
                "select role_id, role_name ,add_date from user_role order by add_date desc limit ?,?",
                new Object[]
                { list.getCurNum(), list.getRows() }, UserRole.class);
    }

    /**
     * 添加角色
     * 
     * @param role
     *            角色实体类
     * @author LuoAndong
     */
    @Override
    public void saveRole(UserRole role, String rights)
    {

        setUserRights(role,rights);
        baseDao.add(role);
    }

    /**
     * 更新用户权限
     * 
     * @author LuoAndong
     */
    @Override
    @Transactional
    public void updateRoleRight(String roleId, String powerArr)
    {
        baseDao.delHQL("delete from UserRight as u where u.userRole.roleId=?",
                new String[]
                { roleId });

        // 保存新的用户数据
        UserRole role = baseDao
                .queryForObject(
                        "select role_id, add_date, is_sysrole, role_name, update_date from user_role where role_id=? ",
                        new Object[]
                        { roleId }, UserRole.class);
        
        setUserRights(role,powerArr); 
        baseDao.update(role);
    }

    /**
     * 设置角色权限
     * @param role 角色信息
     * @param menuIdStr 菜单ID字符串
     */
    private void setUserRights(UserRole role, String menuIdStr)
    {
        if (StringUtils.isBlank(menuIdStr)) { 
            return; 
        }

        String[] menuArr = menuIdStr.split(",");

        List<UserRight> userRights = new ArrayList<UserRight>();
        UserRight userRight = null;
        UserMenus userMenus = null;

        for (String menuId : menuArr)
        {

            if (StringUtils.isBlank(menuId))
            {
                continue;
            }

            userRight = new UserRight();
            userMenus = new UserMenus();
            userMenus.setMenuId(menuId);
            userRight.setUserMenus(userMenus);
            userRight.setUserRole(role);
            userRights.add(userRight);
        }

        role.setUserRights(userRights);
    }

    /**
     * 分页查询角色管理，返回json数据，结合jqGrid做管理
     * 
     * @author LuoAnDong
     */
    @Override
    public List<UserRole> queryRoleByPage(DataGridModel<UserRole> list,
            String keyword)
    {
        int total = baseDao.queryForIntPage(
                "select count(*) from user_role where role_name like ?",
                new Object[]
                { Utils.getKeyword(keyword) });
        list.setCount(total);
        return baseDao
                .query("select role_id, role_name ,add_date from user_role where role_name like ? limit ?,?",
                        new Object[]
                        { Utils.getKeyword(keyword), list.getCurNum(),
                                list.getRows() }, UserRole.class);
    }

    /**
     * 删除角色，如果不存在其它关联用户，则删除，否则不删除
     * @param roleId 角色id
     * @return 删除成功  1 <br/>存在关联用户 2 <br/>删除失败 0
     * @author LuoAnDong
     */
    @Override
    public int deleteRole(String roleId)
    {
        //查询是否查询关联用户
        int relationUser = baseDao.queryForIntPage("select count(*) from user_info where role_id=?" , new Object[]{roleId}) ; 
       
        if(relationUser == 0){
            try{ 
                baseDao.delete(UserRole.class, roleId); 
                return 1 ; 
            }catch(Exception e){
                runLog.error(e.getMessage()) ; 
                return 0 ; 
            }
        }else{
            return 2 ; 
        } 
    }

    /**
     * 查询出所有角色　
     */
    @Override
    public List<UserRole> queryByNoPage()
    {
        return baseDao.query("select role_id, role_name  from user_role order by add_date",UserRole.class) ; 
    }
    
    /**
     * 查询用户角色信息
     */
    public List<UserRole> queryUserRoleByRoleName(String roleName){
        return  baseDao.query("select role_id, role_name ,add_date from user_role where role_name like ?",
                new Object[]{ Utils.getKeyword(roleName)}, UserRole.class);
          
    }
}
