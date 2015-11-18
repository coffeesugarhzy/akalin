package com.sunspot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sms.Sms;

import com.sunspot.common.DateUtil;
import com.sunspot.common.MD5;
import com.sunspot.common.Utils;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.Comments;
import com.sunspot.pojo.CommentsContent;
import com.sunspot.pojo.CommentsPic;
import com.sunspot.pojoext.CommentsExt;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.OrdersIndexExt;
import com.sunspot.service.CommentsService;

/**
 * 评论查询
 * 
 * @author LuoAnDong
 * 
 */
@Service
public class CommentsServiceImpl implements CommentsService {

	/**
	 * 运行日志
	 */
	private final Log runLog = LogFactory.getLog(CommentsServiceImpl.class);
	/**
	 * 持久层操作类
	 */
	@Autowired
	private BaseDao baseDao;

	private static final String QUERY_COMMENTS_COUNT = "select count(1) from comments c left join orders o on c.of_orders_id=o.order_id where o.of_shop_id=? ";

	private static final String QUERY_COMMENTS = "select c.comments_id,ci.custom_name,ci.telphone,o.orders_code,o.pay_money,c.comment_type,c.marks,c.add_date from comments c left join orders o on c.of_orders_id=o.order_id left join custom_info ci on c.of_customer_id=ci.custom_id where o.of_shop_id=? ";

	private static final String QUERY_COMMENTSBYID = "select c.comments_id,ci.custom_name,ci.telphone,o.orders_code,o.pay_money,c.comment_type,c.marks,c.add_date from comments c left join orders o on c.of_orders_id=o.order_id left join custom_info ci on c.of_customer_id=ci.custom_id where c.comments_id=? ";

	/**
	 * 分页查询管理，默认为15条为一页
	 * 
	 * @param list
	 *            查询对象
	 * @param userId
	 *            用户ID
	 * @param keyword
	 *            查询条件值
	 */
	public void queryPage(DataGridModel<CommentsExt> list, String shopId,
			Integer queryType) {

		Object[] countParams = null;
		Object[] queryParams = null;
		String countSql = null;
		String querySql = null;

		// 设置查询参数
		if (null == queryType || -1 == queryType) {
			countParams = new Object[] { shopId };

			queryParams = new Object[] { shopId, list.getCurNum(),
					list.getRows() };

			countSql = QUERY_COMMENTS_COUNT;
			querySql = QUERY_COMMENTS + "order by c.add_date desc limit ?,?";

		} else {

			countParams = new Object[] { shopId, queryType };

			queryParams = new Object[] { shopId, queryType, list.getCurNum(),
					list.getRows() };

			countSql = QUERY_COMMENTS_COUNT + "and c.comment_type=?";
			querySql = QUERY_COMMENTS
					+ "and c.comment_type=? order by c.add_date desc limit ?,?";
		}

		int total = baseDao.queryForIntPage(countSql, countParams);

		// 设置统计数据
		list.setCount(total);

		List<Map<String, Object>> results = baseDao
				.query(querySql, queryParams);

		if (null == results || 0 == results.size()) {
			return;
		}
		List<CommentsExt> list0 = new ArrayList<CommentsExt>();
		CommentsExt commentsExt;
		for (Map<String, Object> map : results) {
			commentsExt = new CommentsExt();
			commentsExt.setCommentsId(Utils.objToStr(map.get("comments_id")));
			commentsExt.setCustomName(Utils.objToStr(map.get("custom_name")));
			commentsExt.setTelphone(Utils.objToStr(map.get("telphone")));
			commentsExt.setOrdersCode(Utils.objToStr(map.get("orders_code")));
			commentsExt.setPayMoney(Utils.objToDouble(map.get("pay_money")));
			commentsExt.setCommentType(Utils.objToInt(map.get("comment_type")));
			commentsExt.setMarks(Utils.objToInt(map.get("marks")));
			commentsExt.setAddDate(Utils.objToStr(map.get("add_date")));
			list0.add(commentsExt);
		}

		// 设置查询结果
		list.setContent(list0);
	}

	/**
	 * 根椐ID查询
	 * 
	 * @param commentsId
	 *            评论ID
	 * @param CommentsExt
	 */
	public CommentsExt queryById(String commentsId) {
			Map<String, Object> results = baseDao.queryForMap(
					QUERY_COMMENTSBYID, new Object[] { commentsId });

			CommentsExt commentsExt = new CommentsExt();
			commentsExt.setCommentsId(Utils.objToStr(results.get("comments_id")));
			commentsExt.setCustomName(Utils.objToStr(results.get("custom_name")));
			commentsExt.setTelphone(Utils.objToStr(results.get("telphone")));
			commentsExt.setOrdersCode(Utils.objToStr(results.get("orders_code")));
			commentsExt.setPayMoney(Utils.objToDouble(results.get("pay_money")));
			commentsExt.setCommentType(Utils.objToInt(results.get("comment_type")));
			commentsExt.setMarks(Utils.objToInt(results.get("marks")));
			commentsExt.setAddDate(Utils.objToStr(results.get("add_date")));
			commentsExt
					.setCommentsContents(baseDao
							.query("select content_id,comments_id,content,is_append,add_date from comments_content where comments_id=? order by is_append ",
									new Object[] { commentsId },
									CommentsContent.class));
			commentsExt
					.setCommentsPics(baseDao
							.query("select pic_id,comments_id,pic_path,add_date from comments_pic where comments_id=?",
									new Object[] { commentsId },
									CommentsPic.class));
			return commentsExt;
		
	}

	/**
	 * 统计评论数
	 * 
	 * @param shopId
	 *            店铺ID
	 * @return Integer
	 */
	public Integer countComment(String shopId) {
		int total = baseDao.queryForIntPage(QUERY_COMMENTS_COUNT,
				new Object[] { shopId });
		return total;
	}

	/**
	 * 添加评论
	 * 
	 * @param comments
	 * @param content
	 * @param pic
	 * @author scatlet
	 */
	public void addComments(Comments comments, CommentsContent content,
			CommentsPic pic) {
		if(comments.getCommentType()==null) comments.setCommentType(0);
		baseDao.add(comments);
		runLog.info("add comments,id=" + comments.getCommentsId());
		if (content != null) {
			content.setIsAppend(0);
			baseDao.add(content);
			runLog.info("add commentsContent,id=" + content.getContentId());
		}
		if (pic != null) {
			baseDao.add(pic);
			runLog.info("add commentsPic,id=" + pic.getPicId());
		}
		baseDao.update("update orders set is_comment=1 where order_id=?", new Object[]{comments.getOfOrdersId()});
	}
	
	/**
	 * 追加评论
	 * @param content 评论内容
	 * @param pic 评论图片
	 */
	public void appEnd(CommentsContent content,CommentsPic pic) {
		content.setIsAppend(1);
		baseDao.add(content);
		runLog.info("add commentsContent,id=" + content.getContentId());
		if (pic != null) {
			baseDao.add(pic);
			runLog.info("add commentsPic,id=" + pic.getPicId());
		}
	}
	
	/**
	 * 7天后自动评论
	 * @param model
	 * @param request
	 * @param comments
	 * @param content
	 * @param pic
	 */
	public void autoAddComment(String orderId){
		Timer timer=new Timer(orderId);
		final String id=orderId;
		final String commentsId=MD5.encrypt(Sms.createCode());
		final String contentId=MD5.encrypt(Sms.createCode());
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Comments comments=new Comments();
				Map<String,Object> result=baseDao.queryForMap("select * from comments where of_orders_id=?", new Object[]{id});
				if(result!=null) return;
				comments.setCommentsId(commentsId);
				comments.setOfOrdersId(id);
				comments.setMarks(5);
				comments.setAddDate(DateUtil.getCurrentDateTime());
				
				CommentsContent content=new CommentsContent();
				content.setComments(comments);
				content.setContentId(contentId);
				content.setAddDate(DateUtil.getCurrentDateTime());
				content.setContent("好评！！");
				
				baseDao.add(comments);
				runLog.info("add comments,id=" + comments.getCommentsId());
				baseDao.add(content);
				runLog.info("add commentsContent,id=" + content.getContentId());
				baseDao.update("update orders set is_comment=1 where orders_id=?", new Object[]{id});
				System.out.println("comments!");
				this.cancel();
			}
		},7*24*60*60*1000);
	}
	
	/**
	 * 通过id获得实体
	 * @param commentId
	 * @return
	 */
	public Comments getByIds(String commentId){
		return baseDao.getByHibernate(Comments.class, commentId);
	}
	
	/**
	 * 根据订单ID查询评论
	 */
	public Comments getById(String orderId){
		Comments comments = new Comments();
		List<Map<String, Object>> rs = baseDao
				.query("select comments_id,of_orders_id,of_customer_id,comment_type,marks,of_cookbook_id,add_date from comments where of_orders_id=?",
						new Object[] { orderId });
		for (Map<String, Object> results : rs) {
			comments.setCommentsId(Utils.objToStr(results.get("comments_id")));
			comments.setOfOrdersId(Utils.objToStr(results.get("of_orders_id")));
			comments.setOfCookbookId(Utils.objToStr(results
					.get("of_cookbook_id")));
			comments.setOfCustomerId(Utils.objToStr(results.get("of_customer_id")));
			comments.setCommentType(Integer.valueOf(Utils.objToStr(results
					.get("comment_type"))));
			comments.setMarks(Integer.parseInt(Utils.objToStr(results
					.get("marks"))));
			comments.setAddDate(Utils.objToStr(results.get("add_date")));
		}
		return comments;
	}
	
	/**
	 * 根据id查询
	 * @param commentsId
	 * @return
	 */
	public List<CommentsContent> getCommentsContentByOrderId(String commentsId){
		List<CommentsContent> list = new ArrayList<CommentsContent>();
		list = baseDao
				.query("select content_id,content,is_append,add_date,comments_id from comments_content where comments_id=?",
						new Object[] { commentsId }, CommentsContent.class);
		return list;
	}
	
	/**
	 * 根据订单id查询评论是否为追加
	 * @param ext
	 * @return
	 */
	public String[] queryAppEndByOrderId(List<OrdersIndexExt> exts){
		String[] is_appEnd=new String[exts.size()];//匹配订单的评论是否存在和是否为追加
		int count=0;
		for(OrdersIndexExt ext:exts){
			Comments comments=new Comments();
			comments=getById(ext.getOrderId());
			if(comments.getCommentsId()==null){
				is_appEnd[count]="0";
				count+=1;
			} else {
				String str=comments.getCommentsId();
				List<CommentsContent> content=getCommentsContentByOrderId(str);
				boolean flag=false;
				is_appEnd[count]="1";
				for(CommentsContent c:content){
					if(c.getIsAppend()==1)flag=true;
				}
				if(flag)is_appEnd[count]="2";
				count+=1;
			}
		}
		return is_appEnd;
	}
}