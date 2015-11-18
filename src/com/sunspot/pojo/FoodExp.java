package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * FoodExp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "food_exp", catalog = "limmai")
public class FoodExp implements java.io.Serializable
{

    // Fields

    private String foodExpId;
    private String title;
    private String logo;
    private String content;
    private String address;
    private String addDate;
    private String updateDate;
    private String ofShopId;

    // Constructors

    /** default constructor */
    public FoodExp()
    {
    }

    /** full constructor */
    public FoodExp(String title, String logo, String content, String address,
            String addDate, String updateDate, String ofShopId)
    {
        this.title = title;
        this.logo = logo;
        this.content = content;
        this.address = address;
        this.addDate = addDate;
        this.updateDate = updateDate;
        this.ofShopId = ofShopId;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "FOOD_EXP_ID", unique = true, nullable = false, length = 36)
    public String getFoodExpId()
    {
        return this.foodExpId;
    }

    public void setFoodExpId(String foodExpId)
    {
        this.foodExpId = foodExpId;
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

    @Column(name = "LOGO")
    public String getLogo()
    {
        return this.logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    @Column(name = "CONTENT", length = 65535)
    public String getContent()
    {
        return this.content;
    }

    public void setContent(String content)
    {
        this.content = content;
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

    @Column(name = "OF_SHOP_ID", length = 36)
    public String getOfShopId()
    {
        return this.ofShopId;
    }

    public void setOfShopId(String ofShopId)
    {
        this.ofShopId = ofShopId;
    }

}