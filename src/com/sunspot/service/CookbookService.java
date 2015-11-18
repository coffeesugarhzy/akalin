package com.sunspot.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.sunspot.pojo.Cookbook;
import com.sunspot.pojoext.CookbookExt;
import com.sunspot.pojoext.CookbookIndexExt;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.ShopExt;

/**
 * 生鲜业务接口层
 * 
 * @author LuoAnDong
 * 
 */
@Service
public interface CookbookService
{
    /**
     * 分页查询管理，默认为15条为一页
     * 
     * @param list 查询对象
     * @param userId 用户ID
     * @param cookType 家宴类型
     * @param keyword 查询条件值
     */
    void queryPage(DataGridModel<Cookbook> list, String userId,
            Integer cookType, String keyword);

    /**
     * 分页查询
     * 
     * @param list 查询结果
     * @param queryType 查询类型 1:查询菜谱 2:查询套餐 3：天天特价
     * @param key 键
     * @param value 值
     */
    void queryPage(DataGridModel<CookbookIndexExt> list, Integer queryType,
            Integer key, String value);

    /**
     * 分页查询限时抢购
     * 
     * @param list 查询结果
     */
    void queryDisPage(DataGridModel<CookbookIndexExt> list);

    /**
     * 通过id查询实体
     */
    Cookbook queryById(String id);

    /**
     * 通过id查询实体
     */
    CookbookIndexExt queryByIndexId(String id);

    /**
     * 通过店铺id和类型查询菜谱
     */
    List<CookbookIndexExt> queryShopCbId(Integer queryType,String id,String value);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 添加实体
     */
    void add(HttpServletRequest request, Cookbook cookbook);

    /**
     * 修改实体
     */
    void modify(HttpServletRequest request, Cookbook cookbook);

    /**
     * 设置特价
     */
    void setSale(Cookbook cookbook);

    /**
     * 根据订单查询家宴
     * 
     * @param orderId
     * @return
     * @author LuoAnDong
     */
    List<CookbookExt> queryCookBookByOrder(String orderId);

    /**
     * 商家得到商品
     * @param shopExtList  店铺集合
     * @param num  商品列表数量
     * @return 商品列表
     */
	DataGridModel<CookbookIndexExt> queryGoodsByShopList(
			List<ShopExt> shopExtList, int num);
 

	List<CookbookIndexExt> queryCookbookShowIndex(String shopId,String keyword,String type);

}
