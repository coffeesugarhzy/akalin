package com.sunspot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.common.Utils;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.DataTypes;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.DataTypeService;

/**
 * 类型管理业务层实体
 * 
 * @author LuoAnDong
 * 
 */
@Service("dataTypeService")
public class DataTypeServiceImpl implements DataTypeService
{
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(DataTypeServiceImpl.class);

    /**
     * 持久层操作类
     */
    @Autowired
    protected BaseDao baseDao;

    /**
     * 根椐类型名查询类型
     * 
     * @param parentId
     *            父节点ID
     * @return List<CookbookType>
     * @author LuoAnDong
     */
    public List<DataTypes> queryByParentId(String parentId)
    {
        List<DataTypes> list = baseDao.query(
                "select type_id,type_name from data_types where parent_id=?",
                new Object[]
                { parentId }, DataTypes.class);
        return list;
    }

    /**
     * 添加
     * 
     * @author LuoAnDong
     */
    @Override
    public int add(DataTypes t)
    {
        try
        {
            baseDao.add(t);
            return 1;
        }
        catch (Exception e)
        {
            runLog.error(e.getMessage());
            return 0;
        }

    }

    /**
     * 通过id查询实体
     */
    @Override
    public DataTypes queryById(String id)
    {
        DataTypes userInfo = baseDao.getByHibernate(DataTypes.class, id);
        return userInfo;
    }

    /**
     * 修改信息
     */
    @Override
    @Transactional
    public int modify(DataTypes t)
    {
        try
        {
            baseDao.update(t);
            return 1;
        }
        catch (Exception e)
        {
            runLog.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 通过id删除实体
     */
    @Override
    public int delete(String id)
    {
        try
        {
            // 查询是否有子类
            if (isHasSubType(id))
                return 0;
            baseDao.delete(DataTypes.class, id);
            return 1;
        }
        catch (Exception e)
        {
            runLog.error(e.getMessage());
            return 0;
        }

    }

    /**
     * 根据id查询是否有子类
     * 
     * @param id
     * @return 有则返回true 否则false
     */
    private boolean isHasSubType(String id)
    {
        int tip = baseDao.queryForIntPage(
                "select count(*) from data_types where parent_id=?",
                new Object[]
                { id });
        return tip > 0 ? true : false;
    }

    /**
     * 不分页查询　顶级父类为0
     * 
     * @param list
     * @return
     * @author LuoAnDong
     */
    @Override
    public List<DataTypes> queryByNoPage()
    {
        List<DataTypes> resultList = new ArrayList<DataTypes>();
        int level = 0;
        List<DataTypes> list = baseDao
                .query("select type_id, type_name, type_order, parent_id,is_sys from data_types",
                        DataTypes.class);

        for (DataTypes c : list)
        {
            if (c.getParentId().equals("0"))
            {
                listSub(resultList, list, c, level);
            }
        }

        return resultList;
    }

    /**
     * 递归调用，列举出子类
     */
    public void listSub(List<DataTypes> myList, List<DataTypes> list,
            DataTypes cb, int level)
    {
        if (cb != null)
        {
            if (isParent(list, cb))
            {
                cb.setTypeName(blank(level++) + cb.getTypeName());
                myList.add(cb);
                List<DataTypes> subCookBook = listSubType(cb, list);
                for (DataTypes c : subCookBook)
                {
                    listSub(myList, list, c, level);
                }
            }
            else
            {
                cb.setTypeName(blank(level++) + cb.getTypeName());
                myList.add(cb);
            }
        }
    }

    /**
     * 打印空格
     * 
     * @param level
     * @return
     */
    public String blank(int level)
    {
        String str = "";
        for (int i = 0; i < level; i++)
        {

            if (i == level - 1)
            {
                str += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─ ";
            }
            else
            {
                str += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            }
        }
        return str;
    }

    /**
     * 得到子类
     * 
     * @param cb
     * @return
     */
    private List<DataTypes> listSubType(DataTypes cb, List<DataTypes> list)
    {
        List<DataTypes> subList = new ArrayList<DataTypes>();
        for (DataTypes c : list)
        {
            if (c.getParentId().equals(cb.getTypeId()))
            {
                subList.add(c);
            }
        }
        return subList.size() == 0 ? null : subList;
    }

    /**
     * 是否为父类
     * 
     * @param list
     * @param cb
     * @return
     */
    private boolean isParent(List<DataTypes> list, DataTypes cb)
    {
        int i = 0;
        for (DataTypes c : list)
        {
            if (c.getParentId().equals(cb.getTypeId()))
            {
                i = 1;
                break;
            }
        }
        return i == 0 ? false : true;
    }

    /**
     * 搜索查询
     * 
     * @author LuoAnDong
     */
    @Override
    public List<DataTypes> queryByPage(DataGridModel<DataTypes> list,
            String keyword)
    {
        int total = baseDao.queryForIntPage(
                "select count(*) from data_types where type_name like ?",
                new Object[]
                { Utils.getKeyword(Utils.transformChar(keyword)) });
        List<DataTypes> areaList = baseDao
                .query("select type_id, type_name, type_order, parent_id,is_sys from data_types where type_name like ? limit ?,?",
                        new Object[]
                        { Utils.getKeyword(Utils.transformChar(keyword)),
                                list.getCurNum(), list.getRows() },
                        DataTypes.class);
        list.setCount(total);
        return areaList;
    }

    /**
     * 通过类型的子类（这里只显示一级）
     * 
     * @param typeName
     *            类型的名称
     * @return 生鲜的实体类（只包含实体类id）
     * @author LuoAnDong
     */
    @Override
    public List<DataTypes> queryByName(String typeName)
    {
        try
        {
            List<Map<String, Object>> idMap = baseDao.query(
                    "select type_id from data_types where type_name like ?",
                    new Object[]
                    { typeName });
            if (idMap != null && idMap.size() == 1)
            {
                String parentTypeId = idMap.get(0).get("type_id").toString();
                List<DataTypes> dataTypes = baseDao.query("select type_id , type_name from data_types where parent_id =? order by type_order asc", new String[]
                { parentTypeId }, DataTypes.class);
                return dataTypes ; 
            }
        }
        catch (Exception e)
        {
            runLog.error(e.getMessage());
        }
        return null;
    }
}
