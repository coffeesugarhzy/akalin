package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * FarmProduce entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "farm_produce", catalog = "limmai")
public class FarmProduce implements java.io.Serializable
{

    // Fields

    private String farmId;
    private String ofShopId;
    private String logo;
    private String farmName;
    private String typeName;
    private Integer marks;
    private double price;
    private String spec;
    private String produceCode;
    private String groundTime;
    private String addDate;
    private String updateDate;
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
    public FarmProduce()
    {
    }

    /** full constructor */
    public FarmProduce(String ofShopId, String logo, String farmName,
            String typeName, Integer marks, double price, String spec,
            String produceCode, String groundTime, String addDate,
            String updateDate, String remarks, Integer isSale, Integer saleDay,
            double salePrice, Integer isDis, String beginTime, String endTime,
            double disPrice, Integer disNum, Integer orderNum)
    {
        this.ofShopId = ofShopId;
        this.logo = logo;
        this.farmName = farmName;
        this.typeName = typeName;
        this.marks = marks;
        this.price = price;
        this.spec = spec;
        this.produceCode = produceCode;
        this.groundTime = groundTime;
        this.addDate = addDate;
        this.updateDate = updateDate;
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
    @Column(name = "FARM_ID", unique = true, nullable = false, length = 36)
    public String getFarmId()
    {
        return this.farmId;
    }

    public void setFarmId(String farmId)
    {
        this.farmId = farmId;
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

    @Column(name = "FARM_NAME", length = 36)
    public String getFarmName()
    {
        return this.farmName;
    }

    public void setFarmName(String farmName)
    {
        this.farmName = farmName;
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

    @Column(name = "SPEC", length = 36)
    public String getSpec()
    {
        return this.spec;
    }

    public void setSpec(String spec)
    {
        this.spec = spec;
    }

    @Column(name = "PRODUCE_CODE", length = 36)
    public String getProduceCode()
    {
        return this.produceCode;
    }

    public void setProduceCode(String produceCode)
    {
        this.produceCode = produceCode;
    }

    @Column(name = "GROUND_TIME", length = 19)
    public String getGroundTime()
    {
        return this.groundTime;
    }

    public void setGroundTime(String groundTime)
    {
        this.groundTime = groundTime;
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