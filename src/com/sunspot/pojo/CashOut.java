package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * CashOut entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cash_out", catalog = "limmai")
public class CashOut implements java.io.Serializable
{

    // Fields

    private String cashoutId;
    private String ofCustomerId;
    private String bankName;
    private String bankCode;
    private String customerName;
    private double cashoutMoney;
    private String applyDate;
    private Integer isAlways;
    private Integer status;
    private String cashoutDate;

    // Constructors

    /** default constructor */
    public CashOut()
    {
    }

    /** full constructor */
    public CashOut(String ofCustomerId, String bankName, String bankCode,
            String customerName, double cashoutMoney, String applyDate,
            Integer isAlways, Integer status, String cashoutDate)
    {
        this.ofCustomerId = ofCustomerId;
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.customerName = customerName;
        this.cashoutMoney = cashoutMoney;
        this.applyDate = applyDate;
        this.isAlways = isAlways;
        this.status = status;
        this.cashoutDate = cashoutDate;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "CASHOUT_ID", unique = true, nullable = false, length = 36)
    public String getCashoutId()
    {
        return this.cashoutId;
    }

    public void setCashoutId(String cashoutId)
    {
        this.cashoutId = cashoutId;
    }

    @Column(name = "OF_CUSTOMER_ID", length = 36)
    public String getOfCustomerId()
    {
        return this.ofCustomerId;
    }

    public void setOfCustomerId(String ofCustomerId)
    {
        this.ofCustomerId = ofCustomerId;
    }

    @Column(name = "BANK_NAME", length = 36)
    public String getBankName()
    {
        return this.bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    @Column(name = "BANK_CODE", length = 36)
    public String getBankCode()
    {
        return this.bankCode;
    }

    public void setBankCode(String bankCode)
    {
        this.bankCode = bankCode;
    }

    @Column(name = "CUSTOMER_NAME", length = 36)
    public String getCustomerName()
    {
        return this.customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    @Column(name = "CASHOUT_MONEY", precision = 26)
    public double getCashoutMoney()
    {
        return this.cashoutMoney;
    }

    public void setCashoutMoney(double cashoutMoney)
    {
        this.cashoutMoney = cashoutMoney;
    }

    @Column(name = "APPLY_DATE", length = 19)
    public String getApplyDate()
    {
        return this.applyDate;
    }

    public void setApplyDate(String applyDate)
    {
        this.applyDate = applyDate;
    }

    @Column(name = "IS_ALWAYS")
    public Integer getIsAlways()
    {
        return this.isAlways;
    }

    public void setIsAlways(Integer isAlways)
    {
        this.isAlways = isAlways;
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

    @Column(name = "CASHOUT_DATE", length = 19)
    public String getCashoutDate()
    {
        return this.cashoutDate;
    }

    public void setCashoutDate(String cashoutDate)
    {
        this.cashoutDate = cashoutDate;
    }

}