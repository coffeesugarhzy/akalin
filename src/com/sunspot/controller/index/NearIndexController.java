package com.sunspot.controller.index;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunspot.common.HttpUtil;
import com.sunspot.common.Utils;
import com.sunspot.pojo.CustomInfo;
import com.sunspot.pojo.CustomLike;
import com.sunspot.pojo.DataTypes;
import com.sunspot.pojoext.CookbookIndexExt;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.ShopExt;
import com.sunspot.service.CookbookService;
import com.sunspot.service.CustomLikeService;
import com.sunspot.service.DataTypeService;
import com.sunspot.service.NearService;

/**
 * 附近控制层
 * 
 * @author LuoAnDong
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("index/near")
public class NearIndexController {

	/**
	 * 运行日志
	 */
	private final Log runLog = LogFactory.getLog(NearIndexController.class);

	/**
	 * 数据类型
	 */
	@Autowired
	private DataTypeService dataTypeSerivce;

	/**
	 * 默认分页
	 */
	private static final int DEFAULT_PAGE_SIZE = 15;

	/**
	 * 附近业务层
	 */
	@Autowired
	private NearService nearService;

	/**
	 * 注入业务层管理
	 */
	@Autowired
	private CookbookService cookbookService;

	/**
	 * 用户收藏业务层
	 */
	@Autowired
	private CustomLikeService customLikeService;

	/**
	 * 进入地图
	 * 
	 * @author LuoAnDong
	 */
	@RequestMapping("/map")
	public void map(String shopId, HttpServletRequest request) {
		ShopExt shop = nearService.queryMap(shopId);
		HttpUtil.bean(request, shop);
	}

	/**
	 * 附近商品,每个店铺先出两个
	 */
	@ResponseBody
	@RequestMapping("/goods")
	public DataGridModel<CookbookIndexExt> nearGoods(
			HttpServletRequest request, int queryType, String filter, int page) {
		
		HttpSession session = request.getSession();
		String longitude = (String) session.getAttribute("longitude");
		String latitude = (String) session.getAttribute("latitude");

		runLog.info(
				"enter ip = " + request.getRemoteHost() + 
				"longitude = " + longitude + 
				" , latitude = " + latitude + 
				" , page = " + page + 
				" , queryType = " + queryType);
		
		List<ShopExt> shopExtList = nearService.query(page, DEFAULT_PAGE_SIZE,
				longitude, latitude, queryType, filter);

		System.out.println("shopExtList = " + shopExtList);

		return cookbookService.queryGoodsByShopList(shopExtList, 2);
	}

	/**
	 * 附近店铺
	 * 
	 * @author LuoAnDong
	 */
	@RequestMapping("/service")
	public void shop(String shopId, HttpServletRequest request,
			HttpSession session) {
		if (StringUtils.isNoneBlank(shopId)) {
			ShopExt shop = nearService.query(
					(String) session.getAttribute("longitude"),
					(String) session.getAttribute("latitude"), shopId);
			CustomInfo customInfo = HttpUtil.getCustom(session);
			if (customInfo != null && shop != null) {
				// 查询出用户关注的店铺
				CustomLike customLike = customLikeService.queryCustomLike(
						shop.getShopId(), 0, customInfo.getCustomId());
				request.setAttribute("customLike", customLike);
			}
			HttpUtil.bean(request, shop);
		}
	}

	/**
	 * 保存用户收藏<br/>
	 * 0 为店铺　
	 * 
	 * @param shopId
	 *            店铺id
	 * @param customId
	 *            用户的id
	 * @param opt
	 *            操作，0为添加收藏　1为删除收藏　
	 * @return 1 收藏成功<br/>
	 *         0 收藏失败<br/>
	 *         2 用户未登陆<br/>
	 * @author LuoAnDong
	 */
	@ResponseBody
	@RequestMapping("customlike")
	public int saveShopLike(CustomLike customLike, int likeType, int opt,
			HttpSession session) {
		CustomInfo customInfo = HttpUtil.getCustom(session);
		if (customInfo == null || customLike == null)
			return 2;

		if (opt == 1)
			return customLikeService.removeCustomLike(customInfo.getCustomId(),
					likeType, customLike.getGoodsId());

		if (opt == 0) {
			customLike.setLikeType(likeType);
			customLike.setAddDate(Utils.getNowDate());
			customLike.setCustomInfo((CustomInfo) customInfo);
			return customLikeService.saveCustomLike(customLike);
		}

		return 0;
	}

	/**
	 * 得到用户的信息，并设置于Session当中
	 * 
	 * @author LuoAnDong
	 */
	@ResponseBody
	@RequestMapping("/position")
	public void position(HttpServletRequest request, String longitude,
			String latitude) {
		HttpSession session = request.getSession();
		session.setAttribute("longitude", longitude);
		session.setAttribute("latitude", latitude);
	}

	/**
	 * 查询店铺　
	 * 
	 * @return
	 * @author LuoAnDong
	 */
	@ResponseBody
	@RequestMapping("/query")
	public List<ShopExt> query(HttpServletRequest request, int fileType,
			String filter, int currentSize) {
		HttpSession session = request.getSession();
		String longitude = (String) session.getAttribute("longitude");
		String latitude = (String) session.getAttribute("latitude");
		
		runLog.info("fileType =	" + fileType + " , filter = " + filter + " , currentSize = " + currentSize) ; 
		
		List<ShopExt> shopExtList = nearService.query(currentSize,
				DEFAULT_PAGE_SIZE, longitude, latitude, fileType, filter);

		return shopExtList;
	}

	/**
	 * 进入附近首页　
	 * 
	 * @author LuoAnDong
	 */
	@RequestMapping("/index")
	public void index(HttpServletRequest request) {
		// 查询店铺分类　
		List<DataTypes> datatypeList = dataTypeSerivce.queryByName("订桌");
		HttpUtil.list(request, datatypeList);
	}

}
