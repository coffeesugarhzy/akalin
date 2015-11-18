package com.sunspot.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.common.Utils;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.Area;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.AreaService;

/**
 * 区域管理业务层实体
 * @author LuoAnDong
 *
 */
@Service 
public class AreaServiceImpl implements AreaService
{
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(AreaServiceImpl.class);
    
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
    public List<Area> queryByPage(DataGridModel<Area> list , String keyword)
    { 
        int total = 0 ; 
        List<Area> areaList = null ; 
        if(keyword != null){
            total = baseDao.queryForIntPage("select count(*) from area where area_name like ?" , new Object[]{Utils.getKeyword(Utils.transformChar(keyword))}); 
            areaList = baseDao.query("select area_id, area_name, level,area_order, parent_id from area where area_name like ? limit ?,?",
                    new Object[]
                    { Utils.getKeyword(Utils.transformChar(keyword)) , list.getCurNum(), list.getRows() }, Area.class);
        }else{
            total = baseDao.queryForIntPage("select count(*) from area"); 
            areaList = baseDao.query("select area_id, area_name, level,area_order, parent_id from area limit ?,?",
                    new Object[]
                    { list.getCurNum(), list.getRows() }, Area.class);
        } 
        list.setCount(total); 
        return areaList ; 
        
    }

    /**
     * 添加
     * @author LuoAnDong
     */
    @Override
    public int add(Area t)
    { 
        try{
            int tip = 0 ; 
            String[] areaNameArr = t.getAreaName().split(",") ;  
            Area area = null ; 
            
            for(int i = 0 ; i < areaNameArr.length ; i ++){
                
                Area subArea = new Area() ; 
                subArea.setAreaName(areaNameArr[i]) ;
                subArea.setLevel(i+1) ; 
                subArea.setAreaOrder(t.getAreaOrder()) ; 
                
                if(area != null){
                    subArea.setParentId(area.getAreaId()) ; 
                }else{
                    subArea.setParentId("0"); 
                }
                
                area = isHasArea(areaNameArr[i]) ;  
                
                if(area == null){ 
                    baseDao.add(subArea);
                    area = subArea ; 
                    tip++ ; 
                } 
            }  
            return tip>0?1:0 ; 
            
        }catch(Exception e){
            runLog.error(e.getMessage()) ; 
            return 0 ; 
        }
    }

    /**
     * 查询是否已经存在地区
     * @param string
     * @return
     */
    private Area isHasArea(String areaName)
    { 
        List<Area> areaList = baseDao.query("select area_id from area where area_name like ?" , new Object[]{areaName} , Area.class) ; 
        return areaList.size()>0?areaList.get(0):null ;
    }

    /**
     * 通过id查询实体
     */
    @Override
    public Area queryById(String id)
    {
        try{
            return  baseDao.queryForObject("select area_id, area_name,  level, parent_id , area_order from area where area_id=? ", new Object[]{id} , Area.class) ;
        }catch(Exception e){
            runLog.error(e.getMessage()) ; 
            return null ; 
        }
    }

    /**
     * 修改区域信息
     */
    @Override
    public void modify(Area t)
    {    
        baseDao.update(t) ; 
    }

    /**
     * 通过id删除实体,如果存在子类，则不允许删除　
     */
    @Override
    @Transactional
    public int delete(String id)
    {
        try{
            if(!hasSubArea(id)){
                baseDao.delete(Area.class, id);
                return 1 ; 
            } 
            return 0 ; 
        }catch(Exception e){
            runLog.error(e.getMessage()) ; 
            return 0 ; 
        }
        
    }

    /**
     * 验证是否有子区域
     * @param parentId
     * @return 有则返回true 否则false
     */
    private boolean hasSubArea(String parentId)
    { 
        int total = baseDao.queryForIntPage("select count(*) from area where parent_id=?" , new String[]{parentId}); 
        return total>0?true:false;
    }

    /**
     * 查询出所有区域
     */
    @Override
    public List<Area> queryByNoPage()
    { 
        return  baseDao.query("select area_id, area_name, level, parent_id from area order by area_order", Area.class) ;
    }
 
    /**
     * 根据父类查询子类
     */
    @Override
    public List<Area> queryByParent(String parentId)
    { 
        return  baseDao.query("select area_id, area_name from area where parent_id=? order by area_order", new String[]{parentId}, Area.class) ;
    }

    /**
     * 根据父类id查询区域
     * @param object
     * @return
     */
    @Override
    public List<Area> queryByParent(DataGridModel<Area> list, String parentId)
    {
        int total = 0 ;  
        List<Area> areaList = null ; 
        
        if(!StringUtils.isBlank(parentId)){
            total = baseDao.queryForIntPage("select count(*) from area where parent_id=?" , new String[]{parentId}); 
            areaList = baseDao.query("select area_id, area_name, level, parent_id from area where parent_id=? order by area_order desc limit ?,?",
                    new Object[]
                    { parentId , list.getCurNum(), list.getRows() }, Area.class);
        } 
         
        list.setCount(total); 
        return areaList ; 
    }

    /**
     * 根据区域的名称查询出域名的子类
     * @param cityName 区域的名称
     * @return
     */
    @Override
    public List<Area> listSubArear(String cityName)
    {
        return baseDao.query("select area_name from area where parent_id = (select area_id from area where area_name like ?) ", 
                new Object[]{cityName}, Area.class) ;  
    }

}
