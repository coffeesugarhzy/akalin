package com.sunspot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.common.Utils;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.CashOut;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.CashOutService;

/**
 * 区域管理业务层实体
 * @author LuoAnDong
 *
 */
@Service
public class CashOutServiceImpl implements CashOutService
{
    /**
     * 持久层操作类
     */
    @Autowired
    protected BaseDao baseDao;
    
    /**
     * 区域分页查询
     * @author LuoAnDong
     */ 
    @Override
    public List<CashOut> queryByPage(DataGridModel<CashOut> list , String keyword)
    { 
        if(keyword != null){
            int total = baseDao.queryForIntPage("select count(*) from cash_out where bank_code like ?" , new Object[]{Utils.getKeyword(Utils.transformChar(keyword))});
            list.setCount(total);  
            return baseDao.query("select cashout_id, apply_date, bank_code, bank_name, cashout_date, cashout_money, customer_name, is_always, ci.custom_name , status from( select cashout_id, apply_date, bank_code, bank_name, cashout_date, cashout_money, customer_name, is_always, of_customer_id, status from cash_out where bank_code like ?  order by apply_date desc limit ?,?) c left join custom_info ci on c.of_customer_id=ci.custom_id",
                    new Object[]
                    { Utils.getKeyword(Utils.transformChar(keyword)) , list.getCurNum(), list.getRows() }, CashOut.class);
        }else{
            int total = baseDao.queryForIntPage("select count(*) from cash_out");
            list.setCount(total);  
            return baseDao.query("select cashout_id, apply_date, bank_code, bank_name, cashout_date, cashout_money, customer_name, is_always, ci.custom_name as customer_name , status from( select cashout_id, apply_date, bank_code, bank_name, cashout_date, cashout_money, customer_name, is_always, of_customer_id, status from cash_out order by apply_date desc  limit ?,? ) c left join custom_info ci on c.of_customer_id=ci.custom_id",
                    new Object[]
                    { list.getCurNum(), list.getRows() }, CashOut.class);   
        } 
        
    }

    /**
     * 添加
     * 
     * @author LuoAnDong
     */
    @Override
    public void add(CashOut t)
    {
        baseDao.add(t);
    }

    /**
     * 通过id查询实体
     */
    @Override
    public CashOut queryById(String id)
    {
        return  baseDao.queryForObject("select cashout_id, apply_date, bank_code, bank_name, cashout_date, cashout_money, customer_name, is_always, ci.custom_name , status from( select cashout_id, apply_date, bank_code, bank_name, cashout_date, cashout_money, customer_name, is_always, of_customer_id, status from cash_out where cashout_id=? ) c left join custom_info ci on c.of_customer_id=ci.custom_id", new Object[]{id} , CashOut.class) ;
    }

    /**
     * 修改区域信息
     */
    @Override
    public void modify(CashOut t)
    {    
        baseDao.update(t) ; 
    }

    /**
     * 通过id删除实体
     */
    @Override
    public int delete(String id)
    {
        try{
            baseDao.delete(CashOut.class, id);
            return 1 ; 
        }catch(Exception e){
            return 0 ; 
        }
        
    }

    @Override
    public int modifyStatus(CashOut t)
    {
        return baseDao.update("update cash_out set status=(status+1)%2 where cashout_id=?", new Object[]{t.getCashoutId()}) ; 
    }

}
