package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * GiftExchange entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gift_exchange", catalog = "limmai")
public class GiftExchange implements java.io.Serializable
{

    // Fields

    private String exchangeId;
    private String giftCode;
    private Integer exchangeQty;
    private String exchangeDate;
    private Integer isSend;
    private String sendDate;
    private String address;
    private String customerId;

    // Constructors

    /** default constructor */
    public GiftExchange()
    {
    }

    /** full constructor */
    public GiftExchange(String giftCode, Integer exchangeQty,
            String exchangeDate, Integer isSend, String sendDate,
            String address, String customerId)
    {
        this.giftCode = giftCode;
        this.exchangeQty = exchangeQty;
        this.exchangeDate = exchangeDate;
        this.isSend = isSend;
        this.sendDate = sendDate;
        this.address = address;
        this.customerId = customerId;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "EXCHANGE_ID", unique = true, nullable = false, length = 36)
    public String getExchangeId()
    {
        return this.exchangeId;
    }

    public void setExchangeId(String exchangeId)
    {
        this.exchangeId = exchangeId;
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

    @Column(name = "EXCHANGE_QTY")
    public Integer getExchangeQty()
    {
        return this.exchangeQty;
    }

    public void setExchangeQty(Integer exchangeQty)
    {
        this.exchangeQty = exchangeQty;
    }

    @Column(name = "EXCHANGE_DATE", length = 19)
    public String getExchangeDate()
    {
        return this.exchangeDate;
    }

    public void setExchangeDate(String exchangeDate)
    {
        this.exchangeDate = exchangeDate;
    }

    @Column(name = "IS_SEND")
    public Integer getIsSend()
    {
        return this.isSend;
    }

    public void setIsSend(Integer isSend)
    {
        this.isSend = isSend;
    }

    @Column(name = "SEND_DATE", length = 19)
    public String getSendDate()
    {
        return this.sendDate;
    }

    public void setSendDate(String sendDate)
    {
        this.sendDate = sendDate;
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

    @Column(name = "CUSTOMER_ID", length = 36)
    public String getCustomerId()
    {
        return this.customerId;
    }

    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

}