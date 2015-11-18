package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Fresh entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fresh", catalog = "limmai")
public class Fresh implements java.io.Serializable
{

    // Fields

    private String freshId;
    private String ofShopId;
    private String logo;
    private String freshName;
    private Integer freshType;
    private String typeName;
    private Integer marks;
    private double price;
    private String makeMethod;
    private Integer makeTime;
    private String operate;
    private String addDate;
    private String updateDate;
    private String suggest;
    private String remarks;
    private Integer isSale;
    private Integer saleDay;
    private double salePrice;
    private Integer isDis;
    private String beginTime;
    private String endTime;
    private double disPrice;
    private Integer disNum;
    private Integer orderNum;

    // Constructors

    /** default constructor */
    public Fresh()
    {
    }

    /** full constructor */
    public Fresh(String ofShopId, String logo, String freshName,
            Integer freshType, String typeName, Integer marks, double price,
            String makeMethod, Integer makeTime, String operate,
            String addDate, String updateDate, String suggest, String remarks,
            Integer isSale, Integer saleDay, double salePrice, Integer isDis,
            String beginTime, String endTime, double disPrice, Integer disNum,
            Integer orderNum)
    {
        this.ofShopId = ofShopId;
        this.logo = logo;
        this.freshName = freshName;
        this.freshType = freshType;
        this.typeName = typeName;
        this.marks = marks;
        this.price = price;
        this.makeMethod = makeMethod;
        this.makeTime = makeTime;
        this.operate = operate;
        this.addDate = addDate;
        this.updateDate = updateDate;
        this.suggest = suggest;
        this.remarks = remarks;
        this.isSale = isSale;
        this.saleDay = saleDay;
        this.salePrice = salePrice;
        this.isDis = isDis;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.disPrice = disPrice;
        this.disNum = disNum;
        this.orderNum = orderNum;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "FRESH_ID", unique = true, nullable = false, length = 36)
    public String getFreshId()
    {
        return this.freshId;
    }

    public void setFreshId(String freshId)
    {
        this.freshId = freshId;
    }

    @Column(name = "OF_SHOP_ID", length = 36)
    public String getOfShopId()
    {
        return this.ofShopId;
    }

    public void setOfShopId(String ofShopId)
    {
        this.ofShopId = ofShopId;
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

    @Column(name = "FRESH_NAME", length = 36)
    public String getFreshName()
    {
        return this.freshName;
    }

    public void setFreshName(String freshName)
    {
        this.freshName = freshName;
    }

    @Column(name = "FRESH_TYPE")
    public Integer getFreshType()
    {
        return this.freshType;
    }

    public void setFreshType(Integer freshType)
    {
        this.freshType = freshType;
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

    @Column(name = "MARKS")
    public Integer getMarks()
    {
        return this.marks;
    }

    public void setMarks(Integer marks)
    {
        this.marks = marks;
    }

    @Column(name = "PRICE", precision = 26)
    public double getPrice()
    {
        return this.price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    @Column(name = "MAKE_METHOD", length = 36)
    public String getMakeMethod()
    {
        return this.makeMethod;
    }

    public void setMakeMethod(String makeMethod)
    {
        this.makeMethod = makeMethod;
    }

    @Column(name = "MAKE_TIME")
    public Integer getMakeTime()
    {
        return this.makeTime;
    }

    public void setMakeTime(Integer makeTime)
    {
        this.makeTime = makeTime;
    }

    @Column(name = "OPERATE", length = 65535)
    public String getOperate()
    {
        return this.operate;
    }

    public void setOperate(String operate)
    {
        this.operate = operate;
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

    @Column(name = "SUGGEST", length = 36)
    public String getSuggest()
    {
        return this.suggest;
    }

    public void setSuggest(String suggest)
    {
        this.suggest = suggest;
    }

    @Column(name = "REMARKS", length = 65535)
    public String getRemarks()
    {
        return this.remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    @Column(name = "IS_SALE")
    public Integer getIsSale()
    {
        return this.isSale;
    }

    public void setIsSale(Integer isSale)
    {
        this.isSale = isSale;
    }

    @Column(name = "SALE_DAY")
    public Integer getSaleDay()
    {
        return this.saleDay;
    }

    public void setSaleDay(Integer saleDay)
    {
        this.saleDay = saleDay;
    }

    @Column(name = "SALE_PRICE", precision = 26)
    public double getSalePrice()
    {
        return this.salePrice;
    }

    public void setSalePrice(double salePrice)
    {
        this.salePrice = salePrice;
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

    @Column(name = "DIS_PRICE", precision = 26)
    public double getDisPrice()
    {
        return this.disPrice;
    }

    public void setDisPrice(double disPrice)
    {
        this.disPrice = disPrice;
    }

    @Column(name = "DIS_NUM")
    public Integer getDisNum()
    {
        return this.disNum;
    }

    public void setDisNum(Integer disNum)
    {
        this.disNum = disNum;
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

}