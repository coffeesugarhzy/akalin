package com.sunspot.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.common.Utils;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.ReturnMoney;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.RebateService;

/**
 * 区域管理业务层实体
 * 
 * @author LuoAnDong
 * 
 */
@Service
public class RebateServiceImpl implements RebateService
{
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(RebateServiceImpl.class);

    /**
     * 持久层操作类
     */
    @Autowired
    protected BaseDao baseDao;

    /**
     * 区域分页查询
     * 
     * @author LuoAnDong
     */
    @Override
    public List<ReturnMoney> queryByPage(DataGridModel<ReturnMoney> list,
            String keyword)
    {
        int total = baseDao
                .queryForIntPage("select count(*) from return_money");
        list.setCount(total);

        if (keyword != null)
        {
            return baseDao
                    .query("select return_money_id, add_date, begin_money, end_money, return_money from return_money where add_date like ? limit ?,?",
                            new Object[]
                            { Utils.getKeyword(Utils.transformChar(keyword)),
                                    list.getCurNum(), list.getRows() },
                            ReturnMoney.class);
        }
        else
        {
            return baseDao
                    .query("select return_money_id, add_date, begin_money, end_money, return_money from return_money order by add_date desc limit ?,?",
                            new Object[]
                            { list.getCurNum(), list.getRows() },
                            ReturnMoney.class);
        }

    }

    /**
     * 返利条件数量
     * 
     * @return
     */
    public int countCondNum()
    {
        return baseDao.queryForIntPage("select count(*) from return_money");
    }

    /**
     * 添加
     * 
     * @author LuoAnDong
     */
    @Override
    public int add(ReturnMoney t)
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
    public ReturnMoney queryById(String id)
    {
        return baseDao
                .queryForObject(
                        "select return_money_id, add_date, begin_money, end_money, return_money from return_money where return_money_id=?",
                        new Object[]
                        { id }, ReturnMoney.class);
    }

    /**
     * 修改区域信息
     */
    @Override
    public int modify(ReturnMoney t)
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
            baseDao.delete(ReturnMoney.class, id);
            return 1;
        }
        catch (Exception e)
        {
            runLog.error(e.getMessage());
            return 0;
        }

    }

}
