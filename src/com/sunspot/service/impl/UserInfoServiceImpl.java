package com.sunspot.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.common.MD5;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.UserInfo;
import com.sunspot.pojo.UserRole;
import com.sunspot.service.UserInfoService;

/**
 * 管理用户接口实现类
 * 
 * @author LuoAndong
 * 
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(UserInfoServiceImpl.class);
    
	/**
	 * 持久层操作类
	 */
	@Autowired
	private BaseDao baseDao;

	/**
	 * 登陆用户登陆
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param loginType
	 *            登陆类型
	 * @return 成功则返回用户信息 否则返回 null
	 * @author LuoAndong
	 */
	@Override
	public UserInfo checkUserLogin(String username, String password,
			String loginType) { 
		try{ 
		     
			List<Map<String, Object>> userInfoMap =  baseDao.query("select user_id,login_name,role_id,user_name,user_type,status from user_info where login_name=? and login_password=? and user_type=? ", 
			        new Object[] { username, MD5.encrypt(password), loginType });
			if(userInfoMap != null && userInfoMap.size() == 1){
			     
			    Map<String,Object> map = userInfoMap.get(0) ; 
			    String userId = map.get("user_id").toString() ; 
			    String loginName = map.get("login_name").toString() ;  
			    
			    //md5加密　mysql大小写不敏感
			    if(!MD5.encrypt(username).equals(MD5.encrypt(loginName))){
			        return null ; 
			    }
			    
			    String userType = map.get("user_type").toString() ;
			    String userName = map.get("user_name")==null?"":map.get("user_name").toString() ;
			    String ofRoleId = map.get("role_id").toString() ; 
			    Integer status=Integer.parseInt(map.get("status").toString());
			    
			    UserRole userRole = new UserRole() ; 
			    userRole.setRoleId(ofRoleId) ; 
			    
			    UserInfo userInfo = new UserInfo() ;
			    userInfo.setUserType(Integer.parseInt(userType)) ; 
			    userInfo.setLoginName(loginName) ; 
			    userInfo.setUserName(userName) ; 
			    userInfo.setUserId(userId) ; 
			    userInfo.setUserRole(userRole) ;
			    userInfo.setStatus(status);
			    
	            return userInfo ; 
	            
			}else{ 
			    return null ; 
			} 
		}catch(Exception e){ 
		    runLog.error(e.getMessage()) ; 
			return null ; 
		}
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
	
    @Override
    @Transactional
	public int modifyUserRole(UserInfo userInfo) {
    	
        UserInfo entity=queryById(userInfo.getUserId());
        entity.setUserRole(userInfo.getUserRole());
        try{
    	    baseDao.update(entity) ; 
		    return 1 ; 
	    }catch(Exception e){
			 return 0;
	    }
	}

}
