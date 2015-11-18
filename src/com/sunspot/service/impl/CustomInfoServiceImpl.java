package com.sunspot.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.common.HttpUtil;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.CustomAddr;
import com.sunspot.pojo.CustomInfo;
import com.sunspot.service.CustomInfoService;

/**
 * 客户业务层
 * 
 * @author LuoAnDong
 * 
 */
@Service
public class CustomInfoServiceImpl implements CustomInfoService
{
    /**
     * 引入持久层操作类
     */
    @Autowired
    private BaseDao baseDao;

    /**
     * 通过id查询客户信息
     * 
     * @param ofCustomId
     * @return
     */
    @Override
    public CustomInfo queryById(String ofCustomId)
    {
        return baseDao.getByHibernate(CustomInfo.class, ofCustomId);
    }

    public List<CustomAddr> getAddrs(HttpServletRequest request)
    {
        CustomInfo customInfo = HttpUtil.getCustom(request.getSession());
        return baseDao.query(
                "select address,is_default from custom_addr where custom_id=?",
                new Object[]
                { customInfo.getCustomId() }, CustomAddr.class);
    }

    /**
     * 验证手机是否已经注册 
     * @param phone
     * @return 注册则返回true 否则false
     * @author LuoAnDong
     */
    @Override
    public boolean checkRepeatPhone(String phone)
    {   
        return baseDao.queryForIntPage("select count(custom_id) from custom_info where telphone=? ", new Object[]{phone})>0?true:false;
    }
    
    /**
     * 找回密码
     */
    public int changePwd(String phone,String newPwd){
    	List<CustomInfo> ls=baseDao.query("select * from custom_info where telphone=?", new Object[]{phone}, CustomInfo.class);
    	if(ls!=null&&ls.size()==1){
    		CustomInfo custom=ls.get(0);
    		custom.setLoginPassword(newPwd);
    		baseDao.update(custom);
    		return 1;
    	}
    	return 0;
    }
    
    /**
     * 装填客户状态
     * @param list
     * @return
     * @author scatlet
     */
    @Transactional
    public int statusModify(String id,int status)
    {	try{
    		System.out.println("id--->"+id+"status--->"+status);
    		baseDao.update("update custom_info set status=? where custom_id=?", new Object[]{status,id});
    		return 1;
    	}catch(Exception e){
    		return 0;
    	}
    }
}
