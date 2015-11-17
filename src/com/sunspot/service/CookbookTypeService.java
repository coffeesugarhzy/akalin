package com.sunspot.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.sunspot.pojo.CookbookType;
import com.sunspot.pojoext.DataGridModel;

public interface CookbookTypeService {

	 public CookbookType queryCookbookType(String id);
	
	 public CookbookType getCookbookTypeById(String id);
	 
	 public void updateCookbookType(CookbookType cookbookType);
	 
	 public void delCookbookTypeById(String id);
	 
	 public Boolean isCookbookExite(CookbookType cook);

	 public void addCookbookType(CookbookType cookbookType);
	 
	 public List<CookbookType> listTypeOfShop(String shopId);
	 
	 void queryPage(DataGridModel<CookbookType> list,HttpSession session,String shopId,String keyword);
}
