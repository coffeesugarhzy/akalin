package com.sunspot.service;

import java.util.List;

import com.sunspot.pojo.Comments;
import com.sunspot.pojo.CommentsContent;
import com.sunspot.pojo.CommentsPic;
import com.sunspot.pojoext.CommentsExt;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.OrdersIndexExt;

/**
 * 评论查询
 * @author LuoAnDong
 *
 */
public interface CommentsService
{
    /**
     * 分页查询管理，默认为15条为一页
     * 
     * @param list 查询对象
     * @param userId 用户ID
     * @param keyword 查询条件值
     */
    void queryPage(DataGridModel<CommentsExt> list, String shopId,Integer queryType);
    
    /**
     * 根椐ID查询 
     * @param commentsId 评论ID
     * @param CommentsExt
     */
    CommentsExt queryById(String commentsId);
    
    /**
     * 统计评论数
     * @param shopId 店铺ID
     * @return Integer
     */
    Integer countComment(String shopId);
    
    /**
     * 添加评论
     * @param comments 评论
     * @param content 评论内容
     * @param pic 评论图片
     */
    void addComments(Comments comments, CommentsContent content,CommentsPic pic);
    
    /**
     * 追加评论
     * @param commentId 评论ID
     * @param content 评论内容
     * @param pic 评论图片
     */
    void appEnd(CommentsContent content,CommentsPic pic);
    
    /**
	 * 7天后自动评论
	 * @param model
	 * @param request
	 * @param comments
	 * @param content
	 * @param pic
	 */
	void autoAddComment(String orderId);
	
	/**
	 * 通过订单id查询评论
	 * @param orderId
	 * @return
	 */
	Comments getById(String orderId);
	
	/**
	 * 通过id获得实体
	 * @param commentId
	 * @return
	 */
	Comments getByIds(String commentId);
	/**
	 * 通过订单id查评论内容
	 * @param orderId
	 * @return
	 */
	List<CommentsContent> getCommentsContentByOrderId(String orderId);
	
	/**
	 * 根据订单id查询评论是否为追加
	 * @param ext
	 * @return
	 */
	String[] queryAppEndByOrderId(List<OrdersIndexExt> exts);
}