package com.sunspot.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.sunspot.pojo.Fresh;
import com.sunspot.pojo.MakeMethod;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.FreshExt;
import com.sunspot.pojoext.FreshIndexExt;

/**
 * 生鲜业务接口层
 * 
 * @author LuoAnDong
 * 
 */
@Service
public interface FreshService
{
    /**
     * 分页查询管理，默认为15条为一页
     * 
     * @param list 查询对象
     * @param userId 用户ID
     * @param freshType 生鲜类型
     * @param keyword 查询条件值
     */
    void queryPage(DataGridModel<Fresh> list, String userId, Integer freshType,
            String keyword);

    /**
     * 查询制作工艺
     */
    List<MakeMethod> queryMakeMethod();

    /**
     * 通过id查询实体
     */
    Fresh queryById(String id);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 添加实体
     */
    void add(HttpServletRequest request, Fresh fresh);

    /**
     * 修改实体
     */
    void modify(HttpServletRequest request, Fresh fresh);

    /**
     * 设置特价
     */
    void setSale(Fresh fresh);
    
    /**
     * 根据订单查询生鲜
     * @param orderId
     * @return  生鲜扩展类
     * @author LuoAnDong
     */
    List<FreshExt> queryFreshByOrder(String orderId);

    
    /**
     * 通过类型名称查询生鲜列表　<br/> 
     * @param type 类型名称
     * @param pageNow 当前条数
     * @return 生鲜列表
     * @author LuoAnDong
     */
    List<FreshExt> queryByType(String transformChar, int sizeNow, int pageSize);

    /**
     * 通过生鲜名称搜索
     * @param keyword 关键字
     * @return 
     * @author LuoAnDong
     */
    List<FreshExt> queryByKeyword(String keyword , int sizeNow, int pageSize);

    /**
     * 查询出最新的生鲜菜谱
     * @param pageSize 显示条数
     * @return
     * @author LuoAnDong
     */
    List<FreshExt> queryNewsCookBook(int pageSize);

    /**
     * 查询天天特价
     * @param object
     * @param currentSize
     * @param pageSize
     * @return
     */
    List<FreshExt> queryNewSale(String keyword, int currentSize, int pageSize);

    /**
     * 价格排序
     * @param currentSize 当前条数
     * @param order 　排序方式　
     * @param height 最高
     * @param lower　最低
     * @return
     * @author LuoAnDong
     */
    List<FreshExt> queryByPrice(String order, String height, String lower,
            int currentSize, int pagesize);

    /**
     * 通过店铺id和类型查询菜谱
     */
    void queryPage(DataGridModel<FreshIndexExt> list, Integer queryType,
            Integer key, String value);
 
    /**
     * 查询优惠
     * @param list
     * @author LuoAnDong
     */
    void queryDisPage(DataGridModel<FreshIndexExt> list);
    
    /**
     * 通过id查询实体
     * @author LuoAnDong
     */
    FreshIndexExt queryByIndexId(String id);
    
    /**
     * 通过店铺id和类型查询菜谱
     * @param currentPage 当前显示条数
     * @param shopPageSize  显示条数
     * @author LuoAnDong
     */
    List<FreshIndexExt> queryShopFreshId(Integer queryType,String id,String value,int currentPage, int shopPageSize);

      
}
