package com.sunspot.service;

import java.util.List;

import com.sunspot.pojo.Area;
import com.sunspot.pojoext.DataGridModel;

/**
 * 区域业务层接口
 * @author LuoAnDong
 *
 */
public interface AreaService
{ 
    /**
     * 分页查询　
     * @param list
     * @param keyword
     * @return
     * @author LuoAnDong
     */
    List<Area> queryByPage(DataGridModel<Area> list, String keyword);

    /**
     * 添加实体
     * @param t
     * @author LuoAnDong
     * @return 
     */
    int add(Area t);

    /**
     * 通过id查询实体
     * @param id
     * @return
     * @author LuoAnDong
     */
    Area queryById(String id);

    /**
     * 修改实体
     * @param t
     * @author LuoAnDong
     */
    void modify(Area t);

    /**
     * 通过id删除实体
     * @param id
     */
    int delete(String id);

    /**
     * 查询出所有区域
     * @return
     */
    List<Area> queryByNoPage();
 
    /**
     * 根据父类id查询出子类
     * @param object
     * @return
     */
    List<Area> queryByParent(String parentId);
    
    /**
     * 根据父类id查询区域
     * @param object
     * @return
     */
    List<Area> queryByParent(DataGridModel<Area> list, String keyword);

    /**
     * 根据区域的名称查询出域名的子类
     * @param cityName 区域的名称
     * @return 子类区域的集合
     */
    List<Area> listSubArear(String cityName);

}
