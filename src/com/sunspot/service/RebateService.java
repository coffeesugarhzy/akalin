package com.sunspot.service;

import java.util.List;

import com.sunspot.pojo.ReturnMoney;
import com.sunspot.pojoext.DataGridModel;

/**
 * 区域业务层接口
 * @author LuoAnDong
 *
 */
public interface RebateService
{

    /**
     * 分页查询　
     * @param list
     * @param keyword
     * @return
     * @author LuoAnDong
     */
    List<ReturnMoney> queryByPage(DataGridModel<ReturnMoney> list, String keyword);

    /**
     * 返利条件数量
     * @return
     */
    int countCondNum();
    
    /**
     * 添加实体
     * @param t
     * @author LuoAnDong
     */
    int add(ReturnMoney t);

    /**
     * 通过id查询实体
     * @param id
     * @return
     * @author LuoAnDong
     */
    ReturnMoney queryById(String id);

    /**
     * 修改实体
     * @param t
     * @author LuoAnDong
     * @return 
     */
    int modify(ReturnMoney t);

    /**
     * 通过id删除实体
     * @param id
     */
    int delete(String id);

}
