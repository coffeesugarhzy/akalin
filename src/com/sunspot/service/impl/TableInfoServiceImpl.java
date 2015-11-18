package com.sunspot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.TableInfo;
import com.sunspot.service.TableInfoService;

/**
 * 订桌业务层实现
 * @author LuoAnDong
 *
 */
@Service
public class TableInfoServiceImpl implements TableInfoService
{
    /**
     * 持久层操作类
     */
    @Autowired
    protected BaseDao baseDao;

    /**
     * 通过id查询订桌
     */
    @Override
    public TableInfo queryById(String tableInfoId)
    { 
        if(tableInfoId == null) return null ; 
        return baseDao.getByHibernate(TableInfo.class, tableInfoId) ; 
    }

}
