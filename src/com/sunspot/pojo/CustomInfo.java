package com.sunspot.pojo;

import java.util.HashSet;
import java.util.Set;

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
 * CustomInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "custom_info", catalog = "limmai")
public class CustomInfo implements java.io.Serializable
{

    // Fields

    private String customId;
    private String customName;
    private Integer customSex;
    private String customHead;
    private double balance;
    private double integral;
    private String telphone;
    private String loginPassword;
    private String addDate;
    private String updateDate;
    private Integer status;
    private Set<CustomAddr> customAddrs = new HashSet<CustomAddr>(0);
    private Set<CustomCoupon> customCoupons = new HashSet<CustomCoupon>(0);
    private Set<CustomLike> customLikes = new HashSet<CustomLike>(0);

    // Constructors

    /** default constructor */
    public CustomInfo()
    {
    }
    
    /** @author scatlet*/
    public CustomInfo(String customId, String customName, Integer customSex,
			String customHead, double balance, double integral,
			String telphone, String loginPassword, String addDate,
			String updateDate, Integer status, Set<CustomAddr> customAddrs,
			Set<CustomCoupon> customCoupons, Set<CustomLike> customLikes) 
    {

		this.customId = customId;
		this.customName = customName;
		this.customSex = customSex;
		this.customHead = customHead;
		this.balance = balance;
		this.integral = integral;
		this.telphone = telphone;
		this.loginPassword = loginPassword;
		this.addDate = addDate;
		this.updateDate = updateDate;
		this.status = status;
		this.customAddrs = customAddrs;
		this.customCoupons = customCoupons;
		this.customLikes = customLikes;
	}

	/** full constructor */
    /*public CustomInfo(String customName, Integer customSex, String customHead,
            double balance, double integral, String telphone,
            String loginPassword, String addDate, String updateDate,
            Set<CustomAddr> customAddrs, Set<CustomCoupon> customCoupons,
            Set<CustomLike> customLikes)
    {
        this.customName = customName;
        this.customSex = customSex;
        this.customHead = customHead;
        this.balance = balance;
        this.integral = integral;
        this.telphone = telphone;
        this.loginPassword = loginPassword;
        this.addDate = addDate;
        this.updateDate = updateDate;
        this.customAddrs = customAddrs;
        this.customCoupons = customCoupons;
        this.customLikes = customLikes;
    }*/
    

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "CUSTOM_ID", unique = true, nullable = false, length = 36)
    public String getCustomId()
    {
        return this.customId;
    }

    public void setCustomId(String customId)
    {
        this.customId = customId;
    }

    @Column(name = "CUSTOM_NAME", length = 36)
    public String getCustomName()
    {
        return this.customName;
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    @Column(name = "CUSTOM_SEX")
    public Integer getCustomSex()
    {
        return this.customSex;
    }

    public void setCustomSex(Integer customSex)
    {
        this.customSex = customSex;
    }

    @Column(name = "CUSTOM_HEAD")
    public String getCustomHead()
    {
        return this.customHead;
    }

    public void setCustomHead(String customHead)
    {
        this.customHead = customHead;
    }

    @Column(name = "BALANCE", precision = 26)
    public double getBalance()
    {
        return this.balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    @Column(name = "INTEGRAL", precision = 26)
    public double getIntegral()
    {
        return this.integral;
    }

    public void setIntegral(double integral)
    {
        this.integral = integral;
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

    @Column(name = "LOGIN_PASSWORD", length = 36)
    public String getLoginPassword()
    {
        return this.loginPassword;
    }

    public void setLoginPassword(String loginPassword)
    {
        this.loginPassword = loginPassword;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customInfo")
    public Set<CustomAddr> getCustomAddrs()
    {
        return this.customAddrs;
    }

    public void setCustomAddrs(Set<CustomAddr> customAddrs)
    {
        this.customAddrs = customAddrs;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customInfo")
    public Set<CustomCoupon> getCustomCoupons()
    {
        return this.customCoupons;
    }

    public void setCustomCoupons(Set<CustomCoupon> customCoupons)
    {
        this.customCoupons = customCoupons;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customInfo")
    public Set<CustomLike> getCustomLikes()
    {
        return this.customLikes;
    }

    public void setCustomLikes(Set<CustomLike> customLikes)
    {
        this.customLikes = customLikes;
    }
    
    @Column(name="STATUS")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}