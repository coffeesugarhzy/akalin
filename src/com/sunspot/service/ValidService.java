package com.sunspot.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.sunspot.pojo.Valid;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.ValidExt;

/**
 * 商家认证
 * 
 * @author qinkechun
 * 
 */
@Service
public interface ValidService {
	
	  /**
	   * 添加认证信息
	   */
	  public void add(HttpServletRequest request,Valid valid);
	  
	  /**
	   * 获取认证信息
	   * @param valid
	   * @return
	   */
	  public Valid queryValid(Valid valid);
	  
	  /**
      * 通过id查询实体
      */
	  public Valid queryById(String id);
	  
	  /**
	   * 查看验证信息信息
	   * @param id
	   * @return
	   */
	  public ValidExt queryExtById(String id);
	  
      /**
      * 修改信息
      */
	  public int modify(Valid t);
	  
	  /**
	   * 管理查询
	   * @param list
	   * @param keyword
	   * @param status
	   */
	  public void queryTakeout(DataGridModel<ValidExt> list, String keyword,  int status);
	  
	  
	  /**
	   * 查询
	   * @param uid
	   * @return
	   */
	  public List<Valid> isValidResult(Valid valid);
	  
	  /**
	   * 查询用户是否通过验证
	   * @param uid
	   * @return
	   */
	  public Boolean isUserPassedValid(String uid);
}
