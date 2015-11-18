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
 * Gift entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gift", catalog = "limmai")
public class Gift implements java.io.Serializable
{

    // Fields

    private String giftId;
    private GiftType giftType;
    private String giftName;
    private String giftPic;
    private String giftCode;
    private Integer giftInte;
    private double giftPrice;
    private String giftBrand;
    private String giftSpec;
    private Integer giftQty;
    private String giftDesc;
    private String addDate;

    // Constructors

    /** default constructor */
    public Gift()
    {
    }

    /** full constructor */
    public Gift(GiftType giftType, String giftName, String giftPic,
            String giftCode, Integer giftInte, double giftPrice,
            String giftBrand, String giftSpec, Integer giftQty,
            String giftDesc, String addDate)
    {
        this.giftType = giftType;
        this.giftName = giftName;
        this.giftPic = giftPic;
        this.giftCode = giftCode;
        this.giftInte = giftInte;
        this.giftPrice = giftPrice;
        this.giftBrand = giftBrand;
        this.giftSpec = giftSpec;
        this.giftQty = giftQty;
        this.giftDesc = giftDesc;
        this.addDate = addDate;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "GIFT_ID", unique = true, nullable = false, length = 36)
    public String getGiftId()
    {
        return this.giftId;
    }

    public void setGiftId(String giftId)
    {
        this.giftId = giftId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GIFT_TYPE_ID")
    public GiftType getGiftType()
    {
        return this.giftType;
    }

    public void setGiftType(GiftType giftType)
    {
        this.giftType = giftType;
    }

    @Column(name = "GIFT_NAME")
    public String getGiftName()
    {
        return this.giftName;
    }

    public void setGiftName(String giftName)
    {
        this.giftName = giftName;
    }

    @Column(name = "GIFT_PIC")
    public String getGiftPic()
    {
        return this.giftPic;
    }

    public void setGiftPic(String giftPic)
    {
        this.giftPic = giftPic;
    }

    @Column(name = "GIFT_CODE", length = 20)
    public String getGiftCode()
    {
        return this.giftCode;
    }

    public void setGiftCode(String giftCode)
    {
        this.giftCode = giftCode;
    }

    @Column(name = "GIFT_INTE")
    public Integer getGiftInte()
    {
        return this.giftInte;
    }

    public void setGiftInte(Integer giftInte)
    {
        this.giftInte = giftInte;
    }

    @Column(name = "GIFT_PRICE", precision = 26)
    public double getGiftPrice()
    {
        return this.giftPrice;
    }

    public void setGiftPrice(double giftPrice)
    {
        this.giftPrice = giftPrice;
    }

    @Column(name = "GIFT_BRAND", length = 64)
    public String getGiftBrand()
    {
        return this.giftBrand;
    }

    public void setGiftBrand(String giftBrand)
    {
        this.giftBrand = giftBrand;
    }

    @Column(name = "GIFT_SPEC", length = 64)
    public String getGiftSpec()
    {
        return this.giftSpec;
    }

    public void setGiftSpec(String giftSpec)
    {
        this.giftSpec = giftSpec;
    }

    @Column(name = "GIFT_QTY")
    public Integer getGiftQty()
    {
        return this.giftQty;
    }

    public void setGiftQty(Integer giftQty)
    {
        this.giftQty = giftQty;
    }

    @Column(name = "GIFT_DESC", length = 65535)
    public String getGiftDesc()
    {
        return this.giftDesc;
    }

    public void setGiftDesc(String giftDesc)
    {
        this.giftDesc = giftDesc;
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

}