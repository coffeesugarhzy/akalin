package com.sunspot.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Shop entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shop", catalog = "limmai")
public class Shop implements java.io.Serializable
{

    // Fields
    private String shopId;
    private String ofUserId;
    private String shopName;
    private String typeName;
    private String ofAreaId;
    private String linkMan;
    private String telphone;
    private String openTime;
    private double cost;
    private String had;
    private String remark;
    private String logo;
    private Integer isBook;
    private Integer isFull;
    private Integer isDis;
    private String address;
    private String beginTime;
    private String endTime;
    private Integer marks;
    private String disContent;
    private double longitude;
    private double latitude;
    private String addDate;
    private String updateDate;
    private Integer orderNum;
    private Integer isToday;//1今日推荐  
    private Integer isWeekly;//2本周推荐
    private Integer online;//在线状态：0下线  1在线
    private List<TableInfo> tableInfos = new ArrayList<TableInfo>(0);

    // Constructors

    /** default constructor */
    public Shop()
    {
    }

    /** full constructor */
    public Shop(String ofUserId, String shopName, String typeName,
            String ofAreaId, String linkMan, String telphone, String openTime,
            double cost, String had, String remark, String logo,
            Integer isBook, Integer isFull, Integer isDis, String address,
            String beginTime, String endTime, Integer marks, String disContent,
            double longitude, double latitude, String addDate,
            String updateDate, Integer orderNum, List<TableInfo> tableInfos)
    {
        this.ofUserId = ofUserId;
        this.shopName = shopName;
        this.typeName = typeName;
        this.ofAreaId = ofAreaId;
        this.linkMan = linkMan;
        this.telphone = telphone;
        this.openTime = openTime;
        this.cost = cost;
        this.had = had;
        this.remark = remark;
        this.logo = logo;
        this.isBook = isBook;
        this.isFull = isFull;
        this.isDis = isDis;
        this.address = address;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.marks = marks;
        this.disContent = disContent;
        this.longitude = longitude;
        this.latitude = latitude;
        this.addDate = addDate;
        this.updateDate = updateDate;
        this.tableInfos = tableInfos;
        this.orderNum = orderNum;
    }


	public Shop(String shopId, String ofUserId, String shopName,
			String typeName, String ofAreaId, String linkMan, String telphone,
			String openTime, double cost, String had, String remark,
			String logo, Integer isBook, Integer isFull, Integer isDis,
			String address, String beginTime, String endTime, Integer marks,
			String disContent, double longitude, double latitude,
			String addDate, String updateDate, Integer orderNum,
			Integer isToday, Integer isWeekly, Integer online,
			List<TableInfo> tableInfos) {
		super();
		this.shopId = shopId;
		this.ofUserId = ofUserId;
		this.shopName = shopName;
		this.typeName = typeName;
		this.ofAreaId = ofAreaId;
		this.linkMan = linkMan;
		this.telphone = telphone;
		this.openTime = openTime;
		this.cost = cost;
		this.had = had;
		this.remark = remark;
		this.logo = logo;
		this.isBook = isBook;
		this.isFull = isFull;
		this.isDis = isDis;
		this.address = address;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.marks = marks;
		this.disContent = disContent;
		this.longitude = longitude;
		this.latitude = latitude;
		this.addDate = addDate;
		this.updateDate = updateDate;
		this.orderNum = orderNum;
		this.isToday = isToday;
		this.isWeekly = isWeekly;
		this.online = online;
		this.tableInfos = tableInfos;
	}

	// Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "SHOP_ID", unique = true, nullable = false, length = 36)
    public String getShopId()
    {
        return this.shopId;
    }

    public void setShopId(String shopId)
    {
        this.shopId = shopId;
    }

    @Column(name = "OF_USER_ID", length = 36)
    public String getOfUserId()
    {
        return this.ofUserId;
    }

    public void setOfUserId(String ofUserId)
    {
        this.ofUserId = ofUserId;
    }

    @Column(name = "SHOP_NAME", length = 64)
    public String getShopName()
    {
        return this.shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    @Column(name = "OF_AREA_ID", length = 36)
    public String getOfAreaId()
    {
        return this.ofAreaId;
    }

    public void setOfAreaId(String ofAreaId)
    {
        this.ofAreaId = ofAreaId;
    }

    @Column(name = "TYPE_NAME", length = 36)
    public String getTypeName()
    {
        return this.typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    @Column(name = "LINK_MAN", length = 36)
    public String getLinkMan()
    {
        return this.linkMan;
    }

    public void setLinkMan(String linkMan)
    {
        this.linkMan = linkMan;
    }

    @Column(name = "TELPHONE", length = 20)
    public String getTelphone()
    {
        return this.telphone;
    }

    public void setTelphone(String telphone)
    {
        this.telphone = telphone;
    }

    @Column(name = "OPEN_TIME", length = 36)
    public String getOpenTime()
    {
        return this.openTime;
    }

    public void setOpenTime(String openTime)
    {
        this.openTime = openTime;
    }

    @Column(name = "COST", precision = 26)
    public double getCost()
    {
        return this.cost;
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }

    @Column(name = "HAD", length = 128)
    public String getHad()
    {
        return this.had;
    }

    public void setHad(String had)
    {
        this.had = had;
    }

    @Column(name = "REMARK", length = 65535)
    public String getRemark()
    {
        return this.remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    @Column(name = "LOGO")
    public String getLogo()
    {
        return this.logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    @Column(name = "IS_BOOK")
    public Integer getIsBook()
    {
        return this.isBook;
    }

    public void setIsBook(Integer isBook)
    {
        this.isBook = isBook;
    }

    @Column(name = "IS_FULL")
    public Integer getIsFull()
    {
        return this.isFull;
    }

    public void setIsFull(Integer isFull)
    {
        this.isFull = isFull;
    }

    @Column(name = "IS_DIS")
    public Integer getIsDis()
    {
        return this.isDis;
    }

    public void setIsDis(Integer isDis)
    {
        this.isDis = isDis;
    }

    @Column(name = "ADDRESS")
    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Column(name = "BEGIN_TIME", length = 19)
    public String getBeginTime()
    {
        return this.beginTime;
    }

    public void setBeginTime(String beginTime)
    {
        this.beginTime = beginTime;
    }

    @Column(name = "END_TIME", length = 19)
    public String getEndTime()
    {
        return this.endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    @Column(name = "MARKS")
    public Integer getMarks()
    {
        return this.marks;
    }

    public void setMarks(Integer marks)
    {
        this.marks = marks;
    }

    @Column(name = "DIS_CONTENT", length = 400)
    public String getDisContent()
    {
        return this.disContent;
    }

    public void setDisContent(String disContent)
    {
        this.disContent = disContent;
    }

    @Column(name = "LONGITUDE", precision = 26)
    public double getLongitude()
    {
        return this.longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    @Column(name = "LATITUDE", precision = 26)
    public double getLatitude()
    {
        return this.latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    @Column(name = "ADD_DATE", length = 19)
    public String getAddDate()
    {
        return this.addDate;
    }

    public void setAddDate(String addDate)
    {
        this.addDate = addDate;
    }

    @Column(name = "UPDATE_DATE", length = 19)
    public String getUpdateDate()
    {
        return this.updateDate;
    }

    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shop")
    public List<TableInfo> getTableInfos()
    {
        return this.tableInfos;
    }

    public void setTableInfos(List<TableInfo> tableInfos)
    {
        this.tableInfos = tableInfos;
    }

    @Column(name = "ORDER_NUM")
    public Integer getOrderNum()
    {
        return this.orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    @Column(name = "ONLINE")
	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}
    @Column(name = "IS_TODAY")
	public Integer getIsToday() {
		return isToday;
	}

	public void setIsToday(Integer isToday) {
		this.isToday = isToday;
	}
	
    @Column(name = "IS_WEEKLY")
	public Integer getIsWeekly() {
		return isWeekly;
	}

	public void setIsWeekly(Integer isWeekly) {
		this.isWeekly = isWeekly;
	}

}