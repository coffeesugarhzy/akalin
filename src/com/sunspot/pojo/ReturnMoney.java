package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * ReturnMoney entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "return_money", catalog = "limmai")
public class ReturnMoney implements java.io.Serializable
{

    // Fields

    private String returnMoneyId;
    private double beginMoney;
    private double endMoney;
    private double returnMoney;
    private String addDate;
    private String updateDate;

    // Constructors

    /** default constructor */
    public ReturnMoney()
    {
    }

    /** full constructor */
    public ReturnMoney(double beginMoney, double endMoney, double returnMoney,
            String addDate, String updateDate)
    {
        this.beginMoney = beginMoney;
        this.endMoney = endMoney;
        this.returnMoney = returnMoney;
        this.addDate = addDate;
        this.updateDate = updateDate;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "RETURN_MONEY_ID", unique = true, nullable = false, length = 36)
    public String getReturnMoneyId()
    {
        return this.returnMoneyId;
    }

    public void setReturnMoneyId(String returnMoneyId)
    {
        this.returnMoneyId = returnMoneyId;
    }

    @Column(name = "BEGIN_MONEY", precision = 26)
    public double getBeginMoney()
    {
        return this.beginMoney;
    }

    public void setBeginMoney(double beginMoney)
    {
        this.beginMoney = beginMoney;
    }

    @Column(name = "END_MONEY", precision = 26)
    public double getEndMoney()
    {
        return this.endMoney;
    }

    public void setEndMoney(double endMoney)
    {
        this.endMoney = endMoney;
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

}