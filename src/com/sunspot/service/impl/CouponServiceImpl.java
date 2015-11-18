package com.sunspot.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.common.Utils;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.Coupon;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.UserSessionUtil;
import com.sunspot.service.CouponService;

/**
 * 优惠券业务层实现
 * 
 * @author LuoAnDong
 * 
 * @param <T>
 */
@Service
public class CouponServiceImpl implements CouponService

{

    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(CouponServiceImpl.class);

    /**
     * 持久层操作类
     */
    @Autowired
    private BaseDao baseDao;

    // 统计查询
    private static final String COUNT_COUPON = "select count(1) from coupon where of_user_id=? and title like ?";

    // 无条件统计查询
    private static final String COUNT_COUPON_NOCOND = "select count(1) from coupon where of_user_id=?";

    // 查询
    private static final String QUERY_COUPON = "select coupon_id,of_user_id,title,low_value,face_value,expired_date,remark from coupon where of_user_id=? and title like ? order by expired_date desc limit ?,?";

    // 根椐ID查询
    private static final String QUERY_COUPON_ID = "select coupon_id,of_user_id,title,low_value,face_value,expired_date,remark from coupon where coupon_id =?";

    // 无条件查询
    private static final String QUERY_COUPON_NOCOND = "select coupon_id,of_user_id,title,low_value,face_value,expired_date,remark from coupon where of_user_id=? order by expired_date desc limit ?,?";

    // 查询指定商家的优惠券
    private static final String QUERY_COUPON_SHOPID = "select coupon_id,of_user_id,title,low_value,face_value,expired_date,remark from coupon where of_user_id in (select of_user_id from shop where shop_id=?)";

    /**
     * 分页查询管理，默认为15条为一页
     * 
     * @param list 查询对象
     * @param userId 用户ID
     * @param keyword 查询条件
     */
    public void queryPage(DataGridModel<Coupon> list, String userId,
            String keyword)
    {

        Object[] countParams = null;
        Object[] queryParams = null;
        String countSql = null;
        String querySql = null;

        // 设置查询参数
        if (StringUtils.isNotBlank(keyword))
        {
            String keywordsFormat = Utils.getKeyword(Utils
                    .transformChar(keyword));

            countParams = new Object[]
            { userId, keywordsFormat };

            queryParams = new Object[]
            { userId, keywordsFormat, list.getCurNum(), list.getRows() };

            countSql = COUNT_COUPON;
            querySql = QUERY_COUPON;

        }
        else
        {
            countParams = new Object[]
            { userId };

            queryParams = new Object[]
            { userId, list.getCurNum(), list.getRows() };

            countSql = COUNT_COUPON_NOCOND;
            querySql = QUERY_COUPON_NOCOND;
        }

        int total = baseDao.queryForIntPage(countSql, countParams);

        // 设置统计数据
        list.setCount(total);

        // 设置查询结果
        list.setContent(baseDao.query(querySql, queryParams, Coupon.class));
    }

    /**
     * 通过id查询实体
     */
    public List<Coupon> queryByShopId(String id)
    {
        return baseDao.query(QUERY_COUPON_SHOPID, new Object[]
        { id }, Coupon.class);
    }

    /**
     * 通过id查询实体
     */
    public Coupon queryById(String id)
    {
        return baseDao.queryForObject(QUERY_COUPON_ID, new Object[]
        { id }, Coupon.class);
    }

    /**
     * 删除
     */
    public void delete(String id)
    {

        baseDao.delHQL("delete from Coupon as f where f.couponId=?",
                new String[]
                { id });

        runLog.info("delete coupon,id=" + id);
    }

    /**
     * 添加实体
     */
    public void add(HttpServletRequest request, Coupon coupon)
    {
        // 上传文件
        coupon.setOfUserId(UserSessionUtil.getUserInfo(request).getUserId());
        baseDao.add(coupon);
        runLog.info("add coupon,id=" + coupon.getCouponId());
    }

    /**
     * 修改实体
     */
    public void modify(HttpServletRequest request, Coupon coupon)
    {
        coupon.setOfUserId(UserSessionUtil.getUserInfo(request).getUserId());
        baseDao.update(coupon);
        runLog.info("modify coupon,id=" + coupon.getCouponId());
    }

}
