package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * PayLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pay_log", catalog = "limmai")
public class PayLog implements java.io.Serializable
{

    // Fields

    private String paylogId;
    private String ofOrderId;
    private String payUser;
    private String payAccount;
    private double payMoney;
    private String payDate;
    private double receiveMoney;
    private String receiveDate;
    private String alipayOrderId;
    private String goodsName;

    // Constructors

    /** default constructor */
    public PayLog()
    {
    }

    /** full constructor */
    public PayLog(String ofOrderId, String payUser, String payAccount,
            double payMoney, String payDate, double receiveMoney,
            String receiveDate, String alipayOrderId, String goodsName)
    {
        this.ofOrderId = ofOrderId;
        this.payUser = payUser;
        this.payAccount = payAccount;
        this.payMoney = payMoney;
        this.payDate = payDate;
        this.receiveMoney = receiveMoney;
        this.receiveDate = receiveDate;
        this.alipayOrderId = alipayOrderId;
        this.goodsName = goodsName;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "PAYLOG_ID", unique = true, nullable = false, length = 36)
    public String getPaylogId()
    {
        return this.paylogId;
    }

    public void setPaylogId(String paylogId)
    {
        this.paylogId = paylogId;
    }

    @Column(name = "OF_ORDER_ID", length = 36)
    public String getOfOrderId()
    {
        return this.ofOrderId;
    }

    public void setOfOrderId(String ofOrderId)
    {
        this.ofOrderId = ofOrderId;
    }

    @Column(name = "PAY_USER", length = 36)
    public String getPayUser()
    {
        return this.payUser;
    }

    public void setPayUser(String payUser)
    {
        this.payUser = payUser;
    }

    @Column(name = "PAY_ACCOUNT")
    public String getPayAccount()
    {
        return this.payAccount;
    }

    public void setPayAccount(String payAccount)
    {
        this.payAccount = payAccount;
    }

    @Column(name = "PAY_MONEY", precision = 26)
    public double getPayMoney()
    {
        return this.payMoney;
    }

    public void setPayMoney(double payMoney)
    {
        this.payMoney = payMoney;
    }

    @Column(name = "PAY_DATE", length = 19)
    public String getPayDate()
    {
        return this.payDate;
    }

    public void setPayDate(String payDate)
    {
        this.payDate = payDate;
    }

    @Column(name = "RECEIVE_MONEY", precision = 26)
    public double getReceiveMoney()
    {
        return this.receiveMoney;
    }

    public void setReceiveMoney(double receiveMoney)
    {
        this.receiveMoney = receiveMoney;
    }

    @Column(name = "RECEIVE_DATE", length = 19)
    public String getReceiveDate()
    {
        return this.receiveDate;
    }

    public void setReceiveDate(String receiveDate)
    {
        this.receiveDate = receiveDate;
    }

    @Column(name = "ALIPAY_ORDER_ID", length = 60)
    public String getAlipayOrderId()
    {
        return this.alipayOrderId;
    }

    public void setAlipayOrderId(String alipayOrderId)
    {
        this.alipayOrderId = alipayOrderId;
    }

    @Column(name = "GOODS_NAME")
    public String getGoodsName()
    {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName)
    {
        this.goodsName = goodsName;
    }

}