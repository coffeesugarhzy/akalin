package com.sunspot.controller.index;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sunspot.common.DateUtil;
import com.sunspot.common.MyUtil;
import com.sunspot.pojo.Comments;
import com.sunspot.pojo.CommentsContent;
import com.sunspot.pojo.CommentsPic;
import com.sunspot.pojo.Orders;
import com.sunspot.service.CommentsService;
import com.sunspot.service.CookbookService;
import com.sunspot.service.FarmService;
import com.sunspot.service.FreshService;
import com.sunspot.service.OrdersService;
import com.sunspot.service.TableInfoService;

/**
 * 评论前端控制层
 * 
 * @author scatlet
 * 
 */
@Controller
@RequestMapping("index/comments")
public class CommentsIndexController {

	/**
	 * 注入业务层管理
	 */
	@Autowired
	private CommentsService commentsService;
	
	@Autowired
	private TableInfoService tableInfoService;
	
	@Autowired
	private CookbookService cookbookService;
	
	@Autowired
	private FreshService freshService;
	
	@Autowired
	private FarmService farmService;
	
	@Autowired
	private OrdersService ordersService;
	/**
	 * 转到添加评论页面
	 * 
	 * @param uid
	 *            用户id
	 * @param cookBookId
	 *            菜单id
	 * @param orderId
	 *            订单id
	 * @param model
	 * @author scatlet
	 */
	@RequestMapping(value = "toAdd")
	public void toAdd(String cookBookId, String orderId, Model model) {
		model.addAttribute("cookBookId", cookBookId);
		model.addAttribute("of_orders_id", orderId);
		Orders orders=ordersService.queryById(orderId);
		model.addAttribute("of_customer_id", orders.getOfCustomId());
	}
	
	/**
	 * 转到追加评论页面
	 * @param cookBookId
	 * @param orderId
	 * @param model
	 */
	@RequestMapping(value = "toAppEnd")
	public void toAppEnd(String cookBookId, String orderId, Model model){
		model.addAttribute("cookBookId", cookBookId);
		model.addAttribute("of_orders_id", orderId);
		Orders orders=ordersService.queryById(orderId);
		model.addAttribute("of_customer_id", orders.getOfCustomId());
		
		Comments comments=commentsService.getById(orderId);
		model.addAttribute("comments_id", comments.getCommentsId());
		System.out.println("sss");
	}
	/**
	 * 添加评论
	 * 
	 * @param model
	 * @param request
	 * @param comments
	 *            评论
	 * @param content
	 *            评论内容
	 * @param pic
	 *            评论图片
	 * @author scatlet
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(Model model, MultipartHttpServletRequest request,
			Comments comments, CommentsContent content, CommentsPic pic) {
		
		
		if (comments.getCommentType() == null)
			comments.setCommentType(0);
		comments.setAddDate(DateUtil.getCurrentDateTime());
		// 有添加图片
		String uploadUrl = MyUtil.upload(request);
		if (StringUtils.isNotBlank(uploadUrl)) {
			pic.setComments(comments);
			pic.setPicPath(uploadUrl);
			pic.setAddDate(DateUtil.getCurrentDateTime());
		}
		content.setComments(comments);
		content.setIsAppend(0);
		content.setAddDate(DateUtil.getCurrentDateTime());
		commentsService.addComments(comments, content, pic);

		return "redirect:/index/orders/ordertakeoutsec.do?status=3";
	}

	/**
	 * 追加评论
	 * 
	 * @param model
	 * @param request
	 * @param content
	 *            评论内容
	 * @param pic
	 *            评论图片
	 */
	@RequestMapping(value ="addAppEnd",method = RequestMethod.POST)
	public String appEnd(Model model, MultipartHttpServletRequest request,
			String commentsId, CommentsContent content, CommentsPic pic) {
		
		System.out.println(request.getParameter("commentsId"));
		Comments comments=commentsService.getByIds(commentsId);
		System.out.println(comments);
		// 有添加图片
		String uploadUrl = MyUtil.upload(request);
			
		if (StringUtils.isNotBlank(uploadUrl)) {
				pic.setComments(comments);
				pic.setPicPath(uploadUrl);
				pic.setAddDate(DateUtil.getCurrentDateTime());
		}
		content.setComments(comments);
		content.setAddDate(DateUtil.getCurrentDateTime());
		commentsService.appEnd(content, pic);
		return "redirect:/index/orders/ordertakeoutsec.do?status=3";
	}
	
}
