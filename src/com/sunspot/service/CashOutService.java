package com.sunspot.service;

import java.util.List;

import com.sunspot.pojo.CashOut;
import com.sunspot.pojoext.DataGridModel;

/**
 * 区域业务层接口
 * @author LuoAnDong
 *
 */
public interface CashOutService
{

    /**
     * 分页查询　
     * @param list
     * @param keyword
     * @return
     * @author LuoAnDong
     */
    List<CashOut> queryByPage(DataGridModel<CashOut> list, String keyword);

    /**
     * 添加实体
     * @param t
     * @author LuoAnDong
     */
    void add(CashOut t);

    /**
     * 通过id查询实体
     * @param id
     * @return
     * @author LuoAnDong
     */
    CashOut queryById(String id);

    /**
     * 修改实体
     * @param t
     * @author LuoAnDong
     */
    void modify(CashOut t);

    /**
     * 通过id删除实体
     * @param id
     */
    int delete(String id);

    /**
     * 修改状态
     * @param t
     * @return
     */
    int modifyStatus(CashOut t);

}
