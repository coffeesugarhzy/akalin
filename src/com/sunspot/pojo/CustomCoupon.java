package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * CustomCoupon entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "custom_coupon", catalog = "limmai")
public class CustomCoupon implements java.io.Serializable
{

    // Fields

    private String customCouponId;
    private CustomInfo customInfo;
    private Integer status;
    private String addDate;
    private String useDate;

    // Constructors

    /** default constructor */
    public CustomCoupon()
    {
    }

    /** full constructor */
    public CustomCoupon(CustomInfo customInfo, Integer status, String addDate,
            String useDate)
    {
        this.customInfo = customInfo;
        this.status = status;
        this.addDate = addDate;
        this.useDate = useDate;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "CUSTOM_COUPON_ID", unique = true, nullable = false, length = 36)
    public String getCustomCouponId()
    {
        return this.customCouponId;
    }

    public void setCustomCouponId(String customCouponId)
    {
        this.customCouponId = customCouponId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOM_ID")
    public CustomInfo getCustomInfo()
    {
        return this.customInfo;
    }

    public void setCustomInfo(CustomInfo customInfo)
    {
        this.customInfo = customInfo;
    }

    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return this.status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
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

    @Column(name = "USE_DATE", length = 19)
    public String getUseDate()
    {
        return this.useDate;
    }

    public void setUseDate(String useDate)
    {
        this.useDate = useDate;
    }

}