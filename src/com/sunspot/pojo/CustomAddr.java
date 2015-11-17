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
 * CustomAddr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "custom_addr", catalog = "limmai")
public class CustomAddr implements java.io.Serializable
{

    // Fields

    private String addrId;
    private CustomInfo customInfo;
    private String address;
    private Integer isDefault;
    private String addDate;

    // Constructors

    /** default constructor */
    public CustomAddr()
    {
    }

    /** full constructor */
    public CustomAddr(CustomInfo customInfo, String address, Integer isDefault,
            String addDate)
    {
        this.customInfo = customInfo;
        this.address = address;
        this.isDefault = isDefault;
        this.addDate = addDate;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ADDR_ID", unique = true, nullable = false, length = 36)
    public String getAddrId()
    {
        return this.addrId;
    }

    public void setAddrId(String addrId)
    {
        this.addrId = addrId;
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

    @Column(name = "ADDRESS")
    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Column(name = "IS_DEFAULT")
    public Integer getIsDefault()
    {
        return this.isDefault;
    }

    public void setIsDefault(Integer isDefault)
    {
        this.isDefault = isDefault;
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