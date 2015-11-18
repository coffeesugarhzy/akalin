package com.sunspot.service;

import java.util.List;

import com.sunspot.pojo.DataTypes;
import com.sunspot.pojoext.DataGridModel;

/**
 * 菜谱类型业务层接口
 * 
 * @author LuoAnDong
 * 
 */
public interface DataTypeService
{

    /**
     * 根椐类型名查询类型
     * 
     * @param parentId 父节点
     * @return List<CookbookType>
     * @author LuoAnDong
     */
    List<DataTypes> queryByParentId(String parentId);
    
    /**
     * 不分页查询　
     * 
     * @param list
     * @return
     * @author LuoAnDong
     */
    List<DataTypes> queryByNoPage();

    /**
     * 分页查询　
     * 
     * @param list
     * @param keyword
     * @return
     * @author LuoAnDong
     */
    List<DataTypes> queryByPage(DataGridModel<DataTypes> list,
            String keyword);
   
    /**
     * 添加实体
     * 
     * @param t
     * @author LuoAnDong
     * @return
     */
    int add(DataTypes t);

    /**
     * 通过id查询实体
     * 
     * @param id
     * @return
     * @author LuoAnDong
     */
    DataTypes queryById(String id);

    /**
     * 修改实体
     * 
     * @param t
     * @author LuoAnDong
     * @return
     */
    int modify(DataTypes t);

    /**
     * 通过id删除实体
     * 
     * @param id
     */
    int delete(String id);

    /**
     * 通过类型名称查询id信息
     * @param typeName 类型的名称
     * @return 生鲜的实体类（只包含实体类id）
     * @author LuoAnDong
     */
    List<DataTypes> queryByName(String typeName);

}
