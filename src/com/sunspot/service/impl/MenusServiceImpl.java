package com.sunspot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.UserMenus;
import com.sunspot.pojo.UserRole;
import com.sunspot.service.MenusService;

/**
 * 菜单业务层接口实现类
 * 
 * @author LuoAnDong
 * 
 */
@Service
public class MenusServiceImpl implements MenusService {
    
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(MenusServiceImpl.class);
    
	/**
	 * 持久层操作类
	 */
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 通过角色查询顶级菜单列表
	 * 
	 * @param userRole
	 *            角色
	 * @return 返回顶级菜单列表
	 * @author LuoAnDong
	 */
	@Override
	public List<UserMenus> queryMainMenuByRole(UserRole userRole , int isSystem ) { 
	    return filterMenus(queryMenuByRole(userRole , isSystem) , ""+isSystem) ; 
	}

	/**
	 * 通过角色查询菜单列表 
	 * @param userRole 角色
	 * @return 返回顶级菜单列表 
	 * @param integer  菜单类型 0为系统用户  1为商家
	 * @author LuoAnDong 
	 */
	@Override
	public List<UserMenus> queryMenuByRole(UserRole userRole, int isSystem) {
		String sql = "select m2.menu_id as menu_id ,m2.menu_name as menu_name , m2.parent_id , m2.menu_url from ( select m.menu_id, m.menu_name ,m.menu_type, m.parent_id , m.menu_url , m.menu_order from (select menu_id from user_right where role_id=?) r1 left join user_menus m on m.menu_id=r1.menu_id ) m2 where m2.menu_type=? order by m2.menu_order asc;";
		return baseDao.query(sql, new Object[]{userRole.getRoleId() , isSystem}, UserMenus.class) ; 
	}
	
	/**
	 * 过滤菜单信息
	 * @param parantId 顶级菜单默认父类id为0
	 * @return 菜单集合
	 */
	private List<UserMenus> filterMenus(List<UserMenus> menus , String parantId){
	    if(menus == null || menus.size() == 0) return null ; 
	    List<UserMenus> topMenus = new ArrayList<UserMenus>() ;  
        for(UserMenus m : menus){  
            //得到顶级菜单 
            if(parantId.equals(m.getParentId())){
                topMenus.add(m) ; 
            }
        } 
        return topMenus ; 
	}

	/**
     * 通过用户权限去查询顶级菜单下的子菜单信息
     * @param userRole
     * @param menuId
     * @return 
     * @author LuoAnDong
     */
    @Override
    public List<UserMenus> querySubMenuByParantId(UserRole userRole, String parantId,
            int isSystem)
    { 
        return filterMenus(queryMenuByRole(userRole , isSystem) , parantId) ; 
    }

    /**
     * 根据菜单id查询菜单信息，这里只是查询出模块的url路径，如果是父类，则查询出 模块名称　
     * @param menuId 菜单id
     * @return 有则返回菜单信息，否则返回null
     * @author LuoAnDong
     */
    @Override
    public UserMenus queryMenuUrlById(String menuId)
    {
        try{
            return baseDao.queryForObject("select menu_url from user_menus where menu_id=?", new Object[]{menuId},  UserMenus.class) ; 
        }catch(Exception e){
            runLog.error(e.getMessage()) ; 
        }
        return null;
    }

    /**
     * 根据角色查询出所有的菜单　
     * @param roleId 角色id
     * @return
     * @author LuoAnDong
     */
    @Override
    public List<UserMenus> queryMenusByRoleId(String roleId)
    { 
        return baseDao.query("select r.menu_id, m.menu_name, m.parent_id from (select menu_id from user_right where role_id=? ) r left join user_menus m on r.menu_id = m.menu_id", new Object[]{roleId}, UserMenus.class) ;
    }

    /**
     * 查询出所有的菜单　
     */
    @Override
    public List<UserMenus> queryAllMenus()
    {
        return baseDao.query("select menu_id, menu_name, menu_type, parent_id from user_menus order by menu_order",  UserMenus.class) ;
    }

}
