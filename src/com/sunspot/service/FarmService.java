package com.sunspot.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.sunspot.pojo.FarmProduce;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.FarmProduceExt;
import com.sunspot.pojoext.FarmProduceIndexExt;

/**
 * 农副业务接口层
 * 
 * @author LuoAnDong
 * 
 */
@Service
public interface FarmService
{
    /**
     * 分页查询管理，默认为15条为一页
     * 
     * @param list 查询对象
     * @param userId 用户ID
     * @param keyword 查询条件值
     */
    void queryPage(DataGridModel<FarmProduce> list, String userId,
            String keyword);

    /**
     * 通过id查询实体
     */
    FarmProduce queryById(String id);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 添加实体
     */
    void add(HttpServletRequest request, FarmProduce farmProduce);

    /**
     * 修改实体
     */
    void modify(HttpServletRequest request, FarmProduce farmProduce);

    /**
     * 设置特价
     */
    void setSale(FarmProduce farmProduce);

    /**
     * 根据订单查询农副产品　
     * 
     * @param orderId
     * @return
     * @author LuoAnDong
     */
    List<FarmProduceExt> queryFarmByOrder(String orderId);

    /**
     * 页面查询农副产品　
     * @param orderId
     * @return
     * @author LuoAnDong
     */
    void queryPage(DataGridModel<FarmProduceIndexExt> list, Integer queryType,Integer key, String value);

    /**
     * 根据id查询出农副产品的详情 
     * @param farmId
     * @return
     * @author LuoAnDong
     */
    FarmProduceIndexExt queryIndexById(String farmId);

    /**
     * 根据店铺查询农副产品信息，主要用于放置于店铺首页中<br/>首页显示条数以默认4条显示，<br/>提高店铺首页
     * 进入时，图片加载的速度
     * @param shopId 店铺id
     * @param keyword 关键词，用于搜索
     * @param currentSize 当前条数
     * @param shopPageSize 店铺首页显示条数，默认为4条
     * @return
     * @author LuoAnDong
     */
    List<FarmProduceIndexExt> queryByPage(String shopId, String keyword, int currentSize, int shopPageSize);
  
}
