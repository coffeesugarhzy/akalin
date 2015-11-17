package com.sunspot.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @添加商品分类
 * @author Administrator
 * 2015-11-4 20:35:55
 */
@Entity
@Table(name = "cookbook_type", catalog = "limmai")
public class CookbookType implements Serializable{

	 private static final long serialVersionUID = 1L;
	
	 private String typeId;
	 private String typeName;
	 private String shopId;
	 private String userId;
	 private int sort;
	 private String remark;
	 
     @GenericGenerator(name = "generator", strategy = "uuid2")
     @Id
     @GeneratedValue(generator = "generator")
     @Column(name = "TYPE_ID", unique = true, nullable = false, length = 36)
	 public String getTypeId() {
		return typeId;
	 }
	 public void setTypeId(String typeId) {
		this.typeId = typeId;
	 }
	 
	 @Column(name = "TYPENAME", length = 128)
	 public String getTypeName() {
		return typeName;
	 }
	 public void setTypeName(String typeName) {
		this.typeName = typeName;
	 }
	 
	 @Column(name = "SHOP_ID",nullable = false, length = 36)
	 public String getShopId() {
		return shopId;
	 }
	 public void setShopId(String shopId) {
		this.shopId = shopId;
	 } 
	 
	 @Column(name = "USER_ID", nullable = false, length = 36)
	 public String getUserId() {
		return userId;
	 }
	 public void setUserId(String userId) {
		this.userId = userId;
	 }
	 
	 public int getSort() {
	 	return sort;
	 }
	 public void setSort(int sort) {
		this.sort = sort;
	 }
	 public String getRemark() {
		return remark;
	 }
	 public void setRemark(String remark) {
		this.remark = remark;
	 }
	 
	@Override
	public String toString() {
		return "CookbookType [typeId=" + typeId + ", typeName=" + typeName
				+ ", shopId=" + shopId + ", userId=" + userId + ", sort="
				+ sort + ", remark=" + remark + "]";
	}

}
