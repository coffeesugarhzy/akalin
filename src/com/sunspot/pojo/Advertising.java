package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Advertising entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "advertising", catalog = "limmai")
public class Advertising implements java.io.Serializable
{

    // Fields

    private String adId;
    private Integer adType;
    private String adTitle;
    private String adLogo;
    private String adUrl;
    private Integer adOrder;
    private String adRemark;
    private String addDate;
    private String updateDate;

    // Constructors

    /** default constructor */
    public Advertising()
    {
    }

    /** full constructor */
    public Advertising(Integer adType, String adTitle, String adLogo,
            String adUrl, Integer adOrder, String adRemark, String addDate,
            String updateDate)
    {
        this.adType = adType;
        this.adTitle = adTitle;
        this.adLogo = adLogo;
        this.adUrl = adUrl;
        this.adOrder = adOrder;
        this.adRemark = adRemark;
        this.addDate = addDate;
        this.updateDate = updateDate;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "AD_ID", unique = true, nullable = false, length = 36)
    public String getAdId()
    {
        return this.adId;
    }

    public void setAdId(String adId)
    {
        this.adId = adId;
    }

    @Column(name = "AD_TYPE")
    public Integer getAdType()
    {
        return this.adType;
    }

    public void setAdType(Integer adType)
    {
        this.adType = adType;
    }

    @Column(name = "AD_TITLE", length = 128)
    public String getAdTitle()
    {
        return this.adTitle;
    }

    public void setAdTitle(String adTitle)
    {
        this.adTitle = adTitle;
    }

    @Column(name = "AD_LOGO")
    public String getAdLogo()
    {
        return this.adLogo;
    }

    public void setAdLogo(String adLogo)
    {
        this.adLogo = adLogo;
    }

    @Column(name = "AD_URL")
    public String getAdUrl()
    {
        return this.adUrl;
    }

    public void setAdUrl(String adUrl)
    {
        this.adUrl = adUrl;
    }

    @Column(name = "AD_ORDER")
    public Integer getAdOrder()
    {
        return this.adOrder;
    }

    public void setAdOrder(Integer adOrder)
    {
        this.adOrder = adOrder;
    }

    @Column(name = "AD_REMARK")
    public String getAdRemark()
    {
        return this.adRemark;
    }

    public void setAdRemark(String adRemark)
    {
        this.adRemark = adRemark;
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

}