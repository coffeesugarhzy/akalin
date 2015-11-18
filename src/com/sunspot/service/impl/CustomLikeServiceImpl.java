package com.sunspot.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.CustomLike;
import com.sunspot.service.CustomLikeService;

/**
 * 客户收藏业务层实现
 * @author LuoAnDong
 *
 */
@Service
@Scope("prototype")
public class CustomLikeServiceImpl implements CustomLikeService
{ 
    /**
     * 持久层操作类
     */
    @Autowired
    protected BaseDao baseDao;
    
    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(CookbookServiceImpl.class);
    
    /**
     * 保存用户收藏
     * @param customLike
     * @return 成功返回1 否则失败
     * @author LuoAnDong
     */
    @Override
    public int saveCustomLike(CustomLike customLike)
    { 
        try{
            if(this.queryCustomLike(customLike.getGoodsId(),customLike.getLikeType(), customLike.getCustomInfo().getCustomId()) != null) return 0 ; 
            baseDao.add(customLike); 
            return 1 ; 
        }catch(Exception e){
            runLog.error(e.getMessage()) ;  
            return 0 ; 
        } 
    }

    /**
     * 根据id删除用户收藏
     * @param customLikeId  收藏id
     * @return 成功则返回1 否则失败
     * @author LuoAnDong
     */
    @Override
    public int removeCustomLike(String customLikeId)
    { 
        try{
            baseDao.delete(CustomLike.class, customLikeId);
            return 1 ; 
        }catch(Exception e){
            runLog.error(e.getMessage()) ;  
            return 0 ; 
        }  
    }

    /**
     * 根据用户和店铺查询出用户是否收藏此店铺<br/>
     * 其中店铺类型代号为0
     * @param shopId --> goodsId　
     * @param customId
     * @return 有则返回一个实体，否则返回空
     * @author LuoAnDong
     */
    @Override
    public CustomLike queryCustomLike(String shopId,int type ,  String customId)
    { 
        List<CustomLike> likeList = baseDao.query("select like_id, like_type, add_date, goods_id, custom_id from custom_like where like_type=? and goods_id=? and custom_id=?", 
                new Object[]{type,shopId,customId}, 
                CustomLike.class) ; 
        return (likeList!=null&&likeList.size()>0)?likeList.get(0):null ;
    }

    /**
     * 根据id删除用户收藏
     * @param customLikeId  收藏id
     * @param type 类型  0:店铺<br/> 1：家宴菜谱<br/> 2：生鲜菜谱 <br/> 3:农副产品
     * @return 成功则返回1 否则失败
     * @author LuoAnDong
     */
    @Override
    public int removeCustomLike(String customId,int type, String goodsId)
    {
        try{
            baseDao.delHQL("delete from CustomLike a where a.likeType=? and a.goodsId=? and a.customInfo.customId=?", new Object[]{type,goodsId,customId}) ; 
            return 1 ; 
        }catch(Exception e){
            runLog.error(e.getMessage()) ;  
            return 0 ; 
        }  
    }

}
