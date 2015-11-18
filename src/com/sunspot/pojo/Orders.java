package com.sunspot.pojo;

import java.util.ArrayList;
import java.util.List;

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
 * Orders entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "orders", catalog = "limmai")
public class Orders implements java.io.Serializable
{

    // Fields

    private String orderId;
    private String ordersCode;
    private String ofShopId;
    private String ofCustomId;
    private Integer peopleNum;
    private String ofTableId;
    private Integer orderType;
    private Integer status;
    private String addDate;
    private String bookDate;
    private String updateDate;
    private double consumeMoney;
    private String ofCouponId;
    private double orderMoney;
    private Integer payType;
    private Integer isPay;
    private double payMoney;
    private double returnMoney;
    private Integer sendStatus;
    private String sendDate;
    private String sendAddr;
    private Integer isComment;
    private String remark;
    private List<OrdersDetail> ordersDetails = new ArrayList<OrdersDetail>(0);

    // Constructors

    /** default constructor */
    public Orders()
    {
    }

    /** full constructor */
    public Orders(String ordersCode, String ofShopId, String ofCustomId,
            Integer peopleNum, String ofTableId, Integer orderType,
            Integer status, String addDate, String bookDate, String updateDate,
            double consumeMoney, String ofCouponId, double orderMoney,
            Integer payType, Integer isPay, double payMoney,
            double returnMoney, Integer sendStatus, String sendDate,
            String sendAddr, Integer isComment, String remark,
            List<OrdersDetail> ordersDetails)
    {
        this.ordersCode = ordersCode;
        this.ofShopId = ofShopId;
        this.ofCustomId = ofCustomId;
        this.peopleNum = peopleNum;
        this.ofTableId = ofTableId;
        this.orderType = orderType;
        this.status = status;
        this.addDate = addDate;
        this.bookDate = bookDate;
        this.updateDate = updateDate;
        this.consumeMoney = consumeMoney;
        this.ofCouponId = ofCouponId;
        this.orderMoney = orderMoney;
        this.payType = payType;
        this.isPay = isPay;
        this.payMoney = payMoney;
        this.returnMoney = returnMoney;
        this.sendStatus = sendStatus;
        this.sendDate = sendDate;
        this.sendAddr = sendAddr;
        this.isComment = isComment;
        this.remark = remark;
        this.ordersDetails = ordersDetails;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ORDER_ID", unique = true, nullable = false, length = 36)
    public String getOrderId()
    {
        return this.orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    @Column(name = "ORDERS_CODE", length = 12)
    public String getOrdersCode()
    {
        return this.ordersCode;
    }

    public void setOrdersCode(String ordersCode)
    {
        this.ordersCode = ordersCode;
    }

    @Column(name = "OF_SHOP_ID", length = 36)
    public String getOfShopId()
    {
        return ofShopId;
    }

    public void setOfShopId(String ofShopId)
    {
        this.ofShopId = ofShopId;
    }

    @Column(name = "OF_CUSTOM_ID", length = 36)
    public String getOfCustomId()
    {
        return this.ofCustomId;
    }

    public void setOfCustomId(String ofCustomId)
    {
        this.ofCustomId = ofCustomId;
    }

    @Column(name = "PEOPLE_NUM")
    public Integer getPeopleNum()
    {
        return this.peopleNum;
    }

    public void setPeopleNum(Integer peopleNum)
    {
        this.peopleNum = peopleNum;
    }

    @Column(name = "OF_TABLE_ID", length = 36)
    public String getOfTableId()
    {
        return this.ofTableId;
    }

    public void setOfTableId(String ofTableId)
    {
        this.ofTableId = ofTableId;
    }

    @Column(name = "ORDER_TYPE")
    public Integer getOrderType()
    {
        return this.orderType;
    }

    public void setOrderType(Integer orderType)
    {
        this.orderType = orderType;
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

    @Column(name = "BOOK_DATE", length = 32)
    public String getBookDate()
    {
        return this.bookDate;
    }

    public void setBookDate(String bookDate)
    {
        this.bookDate = bookDate;
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

    @Column(name = "CONSUME_MONEY", precision = 26)
    public double getConsumeMoney()
    {
        return this.consumeMoney;
    }

    public void setConsumeMoney(double consumeMoney)
    {
        this.consumeMoney = consumeMoney;
    }

    @Column(name = "OF_COUPON_ID", length = 36)
    public String getOfCouponId()
    {
        return this.ofCouponId;
    }

    public void setOfCouponId(String ofCouponId)
    {
        this.ofCouponId = ofCouponId;
    }

    @Column(name = "ORDER_MONEY", precision = 26)
    public double getOrderMoney()
    {
        return this.orderMoney;
    }

    public void setOrderMoney(double orderMoney)
    {
        this.orderMoney = orderMoney;
    }

    @Column(name = "PAY_TYPE")
    public Integer getPayType()
    {
        return this.payType;
    }

    public void setPayType(Integer payType)
    {
        this.payType = payType;
    }

    @Column(name = "IS_PAY")
    public Integer getIsPay()
    {
        return this.isPay;
    }

    public void setIsPay(Integer isPay)
    {
        this.isPay = isPay;
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

    @Column(name = "RETURN_MONEY", precision = 26)
    public double getReturnMoney()
    {
        return this.returnMoney;
    }

    public void setReturnMoney(double returnMoney)
    {
        this.returnMoney = returnMoney;
    }

    @Column(name = "SEND_STATUS")
    public Integer getSendStatus()
    {
        return this.sendStatus;
    }

    public void setSendStatus(Integer sendStatus)
    {
        this.sendStatus = sendStatus;
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

    @Column(name = "SEND_ADDR")
    public String getSendAddr()
    {
        return this.sendAddr;
    }

    public void setSendAddr(String sendAddr)
    {
        this.sendAddr = sendAddr;
    }

    @Column(name = "IS_COMMENT")
    public Integer getIsComment()
    {
        return this.isComment;
    }

    public void setIsComment(Integer isComment)
    {
        this.isComment = isComment;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "orders")
    public List<OrdersDetail> getOrdersDetails()
    {
        return this.ordersDetails;
    }

    public void setOrdersDetails(List<OrdersDetail> ordersDetails)
    {
        this.ordersDetails = ordersDetails;
    }

}