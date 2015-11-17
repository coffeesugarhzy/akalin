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
 * OrdersDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "orders_detail", catalog = "limmai")
public class OrdersDetail implements java.io.Serializable
{

    // Fields

    private String detailId;
    private Orders orders;
    private String cookbookId;
    private Integer saleType;
    private double salePrice;
    private double price;
    private Integer num;

    // Constructors

    /** default constructor */
    public OrdersDetail()
    {
    }

    /** full constructor */
    public OrdersDetail(Orders orders, String cookbookId, Integer saleType,
            double salePrice, double price, Integer num)
    {
        this.orders = orders;
        this.cookbookId = cookbookId;
        this.saleType = saleType;
        this.salePrice = salePrice;
        this.price = price;
        this.num = num;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "DETAIL_ID", unique = true, nullable = false, length = 36)
    public String getDetailId()
    {
        return this.detailId;
    }

    public void setDetailId(String detailId)
    {
        this.detailId = detailId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    public Orders getOrders()
    {
        return this.orders;
    }

    public void setOrders(Orders orders)
    {
        this.orders = orders;
    }

    @Column(name = "COOKBOOK_ID", length = 36)
    public String getCookbookId()
    {
        return this.cookbookId;
    }

    public void setCookbookId(String cookbookId)
    {
        this.cookbookId = cookbookId;
    }

    @Column(name = "SALE_TYPE")
    public Integer getSaleType()
    {
        return saleType;
    }

    public void setSaleType(Integer saleType)
    {
        this.saleType = saleType;
    }

    @Column(name = "SALE_PRICE", precision = 26)
    public double getSalePrice()
    {
        return salePrice;
    }

    public void setSalePrice(double salePrice)
    {
        this.salePrice = salePrice;
    }

    @Column(name = "PRICE", precision = 26)
    public double getPrice()
    {
        return this.price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    @Column(name = "NUM")
    public Integer getNum()
    {
        return this.num;
    }

    public void setNum(Integer num)
    {
        this.num = num;
    }

}