package com.sunspot.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sunspot.pojoext.DataGridModel;

/**
 * 基本业务层接口
 * 
 * @author LuoAnDong
 * 
 * @param <T>
 */
@Service
public interface BaseService<T>
{
    /**
     * 分页查询管理，默认为15条为一页
     * 
     * @param list
     *            查询对象
     * @param keyword
     *            单一查询条件
     * @return List<UserRole> 集合
     */
    List<T> queryByPage(DataGridModel<T> list, String keyword);

    /**
     * 分页查询管理，默认为15条为一页
     * 
     * @param list
     *            查询对象
     * @param conditions
     *            多条件查询
     * @return List<UserRole> 集合
     */
    List<T> queryPage(DataGridModel<T> list, Map<Integer, String> conditions);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 添加实体
     * 
     * @param t
     */
    void add(T obj);

    /**
     * 修改实体
     * 
     * @param t
     */
    void modify(T obj);

    /**
     * 通过id查询实体
     */
    T queryById(String id);

}
