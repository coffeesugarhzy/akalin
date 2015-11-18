package com.sunspot.service;

import com.sunspot.pojo.TableInfo;

/**
 * 订桌接口业务层
 * @author LuoAnDong
 *
 */
public interface TableInfoService
{

    /**
     * 通过id查询实体信息
     * @param tableInfoId
     * @return
     */
    TableInfo queryById(String tableInfoId);

}
