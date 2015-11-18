package com.sunspot.service;

import com.sunspot.pojo.CustomLike;

/**
 * 客户收藏业务层接口
 * @author LuoAnDong
 *
 */
public interface CustomLikeService
{
    /**
     * 保存用户收藏
     * @param customLike
     * @return 成功返回1 否则失败
     * @author LuoAnDong
     */
    int saveCustomLike(CustomLike customLike) ; 
    
    /**
     * 根据id删除用户收藏
     * @param customLikeId  收藏id
     * @return 成功则返回1 否则失败
     * @author LuoAnDong
     */
    int removeCustomLike(String customLikeId) ;

    /**
     * 根据用户和店铺查询出用户是否收藏此店铺　
     * @param shopId
     * @param customId
     * @param i 类型  0:店铺<br/> 1：家宴菜谱<br/> 2：生鲜菜谱<br/> 3:农副产品 
     * @return 有则返回一个实体，否则返回空
     * @author LuoAnDong 
     */
    CustomLike queryCustomLike(String shopId, int i, String customId);

    /**
     * 根据id删除用户收藏
     * @param customLikeId  收藏id
     * @return 成功则返回1 否则失败
     * @author LuoAnDong
     */
    int removeCustomLike(String customId,int type, String goodsId); 
}
