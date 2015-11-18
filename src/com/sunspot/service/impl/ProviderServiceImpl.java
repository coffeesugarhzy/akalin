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
import com.sunspot.controller.manager.ProviderController;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.UserInfo;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.ProviderService;

/**
 * 区域管理业务层实体
 * @author LuoAnDong
 *
 */
@Service
public class ProviderServiceImpl implements ProviderService
{
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(ProviderController.class);
    
    /**
     * 持久层操作类
     */
    @Autowired
    protected BaseDao baseDao;
    
    /**
     * 区域分页查询
     * @author LuoAnDong
     */ 
    @Override
    public List<UserInfo> queryByPage(DataGridModel<UserInfo> list , String keyword)
    {
        
        if(keyword != null){
            int total = baseDao.queryForIntPage("select count(*) from user_info where user_type=1 and user_name like ?" , new Object[]{Utils.getKeyword(Utils.transformChar(keyword)) });
            list.setCount(total); 
            
            return baseDao.query("select user_id, logo, remark,login_name, telphone, user_name,status from user_info where user_type=1 and user_name like ? limit ?,?",
                    new Object[]
                    { Utils.getKeyword(Utils.transformChar(keyword)) , list.getCurNum(), list.getRows() }, UserInfo.class);
        }else{
            int total = baseDao.queryForIntPage("select count(*) from user_info where user_type=1");
            list.setCount(total); 
            
            return baseDao.query("select user_id, logo, remark,login_name, telphone, user_name,status from user_info where user_type=1 order by add_date desc limit ?,?",
                    new Object[]
                    { list.getCurNum(), list.getRows() }, UserInfo.class);
        } 
        
    }

    /**
     * 添加
     * return 添加成功则返回1 否则失败
     * @author LuoAnDong
     */
    @Override
    @Transactional
    public int add(UserInfo t)
    {
        try{ 
            //查询角色是否是系统管理员  
            t.setIsSysuser(getRolePower(t.getUserRole().getRoleId())) ; 
            baseDao.add(t);
            return 1 ; 
        }catch(Exception e){
            runLog.error(e.getMessage()) ; 
            return 0 ; 
        }
        
    }
     
    /**
     * 根据角色查询是否是系统管理员，系统管理员不可以删除，而普通管理员可以删除　
     * @param roleId
     * @return 0则为系统管理员　1为普通管理员
     * @author LuoAnDong
     */
    private int getRolePower(String roleId){
        int rolePower = baseDao.queryForIntPage("select is_sysrole from user_role where role_id=? ", new Object[]{roleId}) ; 
        return rolePower ; 
    }

    
    
    /**
     * 通过id查询实体
     */
    @Override
    public UserInfo queryById(String id)
    { 
        UserInfo userInfo = baseDao.getByHibernate(UserInfo.class, id) ;  
        return userInfo ; 
    }

    /**
     * 修改信息
     */
    @Override
    @Transactional
    public int modify(UserInfo t)
    {     
        try{          
            //根据id查询用信息
            UserInfo modifyUserInfo = baseDao.getByHibernate(UserInfo.class, t.getUserId()) ;
            
            modifyUserInfo.setUpdateDate(Utils.getNowDate()) ; 
            modifyUserInfo.setUserName(t.getUserName()) ; 
            modifyUserInfo.setLoginName(t.getLoginName()) ; 
            modifyUserInfo.setTelphone(t.getTelphone()) ; 
            if(t.getLoginPassword() != null && !t.getLoginPassword().trim().equals(""))
                modifyUserInfo.setLoginPassword(t.getLoginPassword()) ; 
            modifyUserInfo.setUserRole(t.getUserRole()) ; 
            modifyUserInfo.setRemark(t.getRemark()) ; 
            if(t.getLogo() != null && !t.getLogo().trim().equals(""))
                modifyUserInfo.setLogo(t.getLogo()) ; 
            
            /**
             * 更新角色
             */
            modifyUserInfo.setUserRole(t.getUserRole()) ; 
            
            baseDao.update(modifyUserInfo) ; 
            return 1 ; 
        }catch(Exception e){
            return 0 ; 
        } 
    }
    
    /**
     * 根据用户查询是否是系统管理员，系统管理员不可以删除，而普通管理员可以删除　
     * @param roleId
     * @return 0则为系统管理员　1为普通管理员
     * @author LuoAnDong
     */
    private int getUserPower(String userId){
        int rolePower = baseDao.queryForIntPage("select is_sysrole from user_info where user_id=? ", new Object[]{userId}) ; 
        return rolePower ; 
    }


    /**
     * 通过id删除实体
     * @author LuoAnDng
     */
    @Override
    @Transactional
    public int delete(String id)
    {
        try{
            //如果是系统管理员则不删除
            int userId = getUserPower(id) ; 
            if(userId==0){
                return 0 ; 
            } 
            baseDao.delete(UserInfo.class, id); 
            baseDao.delHQL("delete from Shop as s where s.shopId=?", new Object[]{id}) ; 
            return 1 ; 
        }catch(Exception e){
            runLog.error(e.getMessage()) ; 
            return 0 ; 
        }
        
    } 
    
    /**
     * 查询商家备注信息
     * @param id
     * @return
     * @author LuoAnDong
     */
    @Override
    public UserInfo queryRemark(String id)
    {  
       try{
           UserInfo userInfo = baseDao.queryForObject("select user_name , remark from user_info where user_id=?", new Object[]{id}, UserInfo.class) ;  
           return userInfo ; 
       }catch(Exception e){
           runLog.error(e.getMessage()) ; 
       }
       return null ; 
    }

    /**
     * 查询商家登陆名是否重复
     * @param loginName
     * @return  0为不重复　1为重复
     * @author LuoAnDong
     */
    @Override
    public int checkRepeatName(String loginName)
    {
        if(StringUtils.isBlank(loginName)) return 0 ;
        int count = baseDao.queryForIntPage("select count(*) from user_info where login_name=? ", new Object[]{loginName}) ; 
        return count==0?0:1 ; 
    }
    
    /**
     * 验证手机是否已经注册 
     * @param phone
     * @return 注册则返回true 否则false
     * @author LuoAnDong
     */
    public boolean checkRepeatTelPhone(String telPhone)
    {
        return baseDao.queryForIntPage("select count(user_id) from user_info where telphone=? ", new Object[]{telPhone})>0?true:false;
    }  
    
    /**
     * 装填用户状态
     * @param list
     * @return
     * @author scatlet
     */
    @Transactional
    public int statusModify(String id,int status)
    {	try{
    		System.out.println("id--->"+id+"status--->"+status);
    		baseDao.update("update user_info set status=? where user_id=?", new Object[]{status,id});
    		return 1;
    	}catch(Exception e){
    		return 0;
    	}
    }
}
