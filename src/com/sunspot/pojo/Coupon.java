package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Coupon entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "coupon", catalog = "limmai")
public class Coupon implements java.io.Serializable
{

    // Fields

    private String couponId;
    private String ofUserId;
    private String title;
    private double lowValue;
    private double faceValue;
    private String expiredDate;
    private String remark;

    // Constructors

    /** default constructor */
    public Coupon()
    {
    }

    /** full constructor */
    public Coupon(String ofUserId, String title, double lowValue,
            double faceValue, String expiredDate, String remark)
    {
        this.ofUserId = ofUserId;
        this.title = title;
        this.lowValue = lowValue;
        this.faceValue = faceValue;
        this.expiredDate = expiredDate;
        this.remark = remark;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "COUPON_ID", unique = true, nullable = false, length = 36)
    public String getCouponId()
    {
        return this.couponId;
    }

    public void setCouponId(String couponId)
    {
        this.couponId = couponId;
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

    @Column(name = "TITLE", length = 128)
    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Column(name = "LOW_VALUE", precision = 26)
    public double getLowValue()
    {
        return this.lowValue;
    }

    public void setLowValue(double lowValue)
    {
        this.lowValue = lowValue;
    }

    @Column(name = "FACE_VALUE", precision = 26)
    public double getFaceValue()
    {
        return this.faceValue;
    }

    public void setFaceValue(double faceValue)
    {
        this.faceValue = faceValue;
    }

    @Column(name = "EXPIRED_DATE", length = 19)
    public String getExpiredDate()
    {
        return this.expiredDate;
    }

    public void setExpiredDate(String expiredDate)
    {
        this.expiredDate = expiredDate;
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

}