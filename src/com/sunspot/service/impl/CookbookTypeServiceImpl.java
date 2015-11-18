package com.sunspot.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunspot.common.Utils;
import com.sunspot.dao.BaseDao;
import com.sunspot.pojo.CookbookType;
import com.sunspot.pojo.UserInfo;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.service.CookbookTypeService;

@Service
public class CookbookTypeServiceImpl implements CookbookTypeService{

	private final Log runLog = LogFactory.getLog(CookbookTypeServiceImpl.class);
	
    /**
     * 持久层操作类
     */
    @Autowired
    private BaseDao baseDao;
    
	@Override
	public CookbookType getCookbookTypeById(String id) {
		return baseDao.getByHibernate(CookbookType.class, id);
	}

	@Override
	public void updateCookbookType(CookbookType cook) {
		CookbookType next=this.getCookbookTypeById(cook.getTypeId());
        if(next!=null){
        	next.setShopId(cook.getShopId());
        	next.setSort(cook.getSort());
        	next.setTypeName(cook.getTypeName());
        	baseDao.update(next);
        }
	}

	@Override
	public void delCookbookTypeById(String id) {
		baseDao.delete(CookbookType.class, id);
	}

	@Override
	public Boolean isCookbookExite(CookbookType cook) {
		String querySql="select * from cookbook_type where typename=? and shop_id=? ";
		List<CookbookType> ls=baseDao.query(querySql,new Object[]{cook.getTypeName(),cook.getShopId()}, CookbookType.class);
		return ls.size()>0?true:false;
	}

	@Override
	public void addCookbookType(CookbookType cookbookType) {
		baseDao.add(cookbookType);
	}

	@Override
	public void queryPage(DataGridModel<CookbookType> list,HttpSession session,String shopId,String keyword) {
		UserInfo loginUser = (UserInfo) session.getAttribute("loginUser");
		if(loginUser==null){
			return;
		}
		String uid=loginUser.getUserId();
		
		Object[] countParams = null;
        Object[] queryParams = null;
        String countSql = null;
        String querySql = null;
        
        if(StringUtils.isNotBlank(keyword)){
            String keywordsFormat = Utils.getKeyword(Utils.transformChar(keyword));
                    
        	if(StringUtils.isNotBlank(shopId)){
        		countSql=" select count(1) from cookbook_type  where SHOP_ID=? and USER_ID=? and TYPENAME like ?";
        		querySql=" select type_id,TYPENAME,SHOP_NAME remark from cookbook_type t ,shop s  where t.SHOP_ID=s.SHOP_ID and s.SHOP_ID=? and USER_ID=? and  TYPENAME like ? order by sort limit ?,?";
                countParams = new Object[]{ shopId,uid, keywordsFormat};
                queryParams = new Object[]{ shopId,uid,keywordsFormat, list.getCurNum(), list.getRows() };
                        
                               
        	}else{
        		countSql=" select count(1) from cookbook_type  where  USER_ID=? and  TYPENAME like ?";
        		querySql=" select type_id,TYPENAME,SHOP_NAME remark from cookbook_type t ,shop s  where t.SHOP_ID=s.SHOP_ID and  t.USER_ID=? and  TYPENAME like ?  order by sort limit ?,?";
                countParams = new Object[]{uid,keywordsFormat};
                queryParams = new Object[]{uid,keywordsFormat, list.getCurNum(), list.getRows() };
        	}
        	
        }else{
        	
        	if(StringUtils.isNotBlank(shopId)){
        		countSql=" select count(1) from cookbook_type  where  USER_ID=? and SHOP_ID=? ";
        		querySql=" select type_id,TYPENAME,SHOP_NAME remark from cookbook_type t ,shop s  where t.SHOP_ID=s.SHOP_ID and  s.SHOP_ID=? and  USER_ID=?  order by sort limit ?,?";
        	    countParams = new Object[]{uid, shopId};
                queryParams = new Object[]{ shopId,uid,list.getCurNum(), list.getRows() };
        	}else{
        		countSql=" select count(1) from cookbook_type where  USER_ID=? ";
        		querySql=" select type_id,TYPENAME,SHOP_NAME remark from cookbook_type t ,shop s  where t.SHOP_ID=s.SHOP_ID and  t.USER_ID=?  order by sort limit ?,?";
        	    countParams = new Object[]{uid};
        		queryParams = new Object[]{uid,list.getCurNum(), list.getRows() };
        	}
        	
        }
        int total = baseDao.queryForIntPage(countSql, countParams);
        list.setCount(total);
        list.setContent(baseDao.query(querySql, queryParams,CookbookType.class));
	}

	@Override
	public CookbookType queryCookbookType(String id) {
		String querySql="select type_id,TYPENAME,SHOP_NAME SHOP_ID, t.remark,sort from cookbook_type t ,shop s  where t.SHOP_ID=s.SHOP_ID and  t.type_id=?";
		List<CookbookType> ls=baseDao.query(querySql, new Object[]{id},CookbookType.class);
		if(ls.size()==1){
			return ls.get(0);
		}
		
		return null;
	}

	@Override
	public List<CookbookType> listTypeOfShop(String shopId) {
		String querySql="select type_id,TYPENAME,SHOP_NAME SHOP_ID, t.remark,sort from cookbook_type t ,shop s  where t.SHOP_ID=s.SHOP_ID and  t.SHOP_ID=?";
		return baseDao.query(querySql, new Object[]{shopId},CookbookType.class);
	}

}
