package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "valid", catalog = "limmai")
public class Valid implements java.io.Serializable {
	
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 36)
	private String id; //编号
    
    @Column(name = "uid", nullable = false, length = 36)
	private String uid;//商家ID
    
    @Column(name = "shopname", nullable = false, length = 36)
    private String shopname;//商家名称
    
	private int type;//验证类型
	
	private int isValid;//是否通过验证 0未 1通过 2不通过
	
    private String  frontcover;//上面
    
    private String backcover;//背面

	@Column(length = 26)
	private String addDate;//提交验证时间
	
	@Column(length = 36)
	private String validor;//验证者
	
	@Column(length = 26)
	private String validDate;
	
	@Column(length = 65535)
	private String remark;//不通过原因

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public String getFrontcover() {
		return frontcover;
	}

	public void setFrontcover(String frontcover) {
		this.frontcover = frontcover;
	}

	public String getBackcover() {
		return backcover;
	}

	public void setBackcover(String backcover) {
		this.backcover = backcover;
	}

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getValidor() {
		return validor;
	}

	public void setValidor(String validor) {
		this.validor = validor;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public Valid() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Valid [id=" + id + ", uid=" + uid + ", shopname=" + shopname
				+ ", type=" + type + ", isValid=" + isValid + ", frontcover="
				+ frontcover + ", backcover=" + backcover + ", addDate="
				+ addDate + ", remark=" + remark + "]";
	}

	public Valid(String uid, String shopname, int type, int isValid,
			String frontcover, String backcover, String addDate, String remark) {
		super();
		this.uid = uid;
		this.shopname = shopname;
		this.type = type;
		this.isValid = isValid;
		this.frontcover = frontcover;
		this.backcover = backcover;
		this.addDate = addDate;
		this.remark = remark;
	}
}
