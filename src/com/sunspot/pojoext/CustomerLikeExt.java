package com.sunspot.pojoext;

import java.io.Serializable;

/**
 * @我的收藏
 * @author QinKeChun
 * @2015-11-3 22:30:34
 */
public class CustomerLikeExt implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String goodId;
	private int type;
    private String name;
	private String logo;
	private double cost;
    private String remark;
    private int marks;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}
    
}
