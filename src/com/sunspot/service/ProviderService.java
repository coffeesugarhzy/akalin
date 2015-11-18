package com.sunspot.service;

import java.util.List;

import com.sunspot.pojo.UserInfo;
import com.sunspot.pojoext.DataGridModel;

/**
 * 区域业务层接口
 * @author LuoAnDong
 *
 */
public interface ProviderService
{

    /**
     * 分页查询　
     * @param list
     * @param keyword
     * @return
     * @author LuoAnDong
     */
    List<UserInfo> queryByPage(DataGridModel<UserInfo> list, String keyword);

    /**
     * 添加实体 
     * return 
     * @param t
     * @author LuoAnDong
     * @return 添加成功则返回1 否则失败 
     */
    int add(UserInfo t);

    /**
     * 通过id查询实体
     * @param id
     * @return
     * @author LuoAnDong
     */
    UserInfo queryById(String id);

    /**
     * 修改实体
     * @param t
     * @author LuoAnDong
     * @return 
     */
    int modify(UserInfo t);

    /**
     * 通过id删除实体
     * @param id
     */
    int delete(String id);

    /**
     * 查询商家备注信息
     * @param id
     * @return
     * @author LuoAnDong
     */
    UserInfo queryRemark(String id);

    /**
     * 查询商家登陆名是否重复
     * @param loginName
     * @return
     * @author LuoAnDong
     */
    int checkRepeatName(String loginName);

    /**
     * 验证手机是否已经注册 
     * @param phone
     * @return 注册则返回true 否则false
     * @author LuoAnDong
     */
    boolean checkRepeatTelPhone(String Telphone);
    
    /**
     * 装填用户状态
     * @param list
     * @return
     * @author scatlet
     */
    int statusModify(String id,int status);
}
