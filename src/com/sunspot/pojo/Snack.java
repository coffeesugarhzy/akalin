package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Snack entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "snack", catalog = "limmai")
public class Snack implements java.io.Serializable
{

    // Fields

    private String snackId;
    private String title;
    private String logo;
    private String content;
    private String address;
    private String addDate;
    private String updateDate;

    // Constructors

    /** default constructor */
    public Snack()
    {
    }

    /** full constructor */
    public Snack(String title, String logo, String content, String address,
            String addDate, String updateDate)
    {
        this.title = title;
        this.logo = logo;
        this.content = content;
        this.address = address;
        this.addDate = addDate;
        this.updateDate = updateDate;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "SNACK_ID", unique = true, nullable = false, length = 36)
    public String getSnackId()
    {
        return this.snackId;
    }

    public void setSnackId(String snackId)
    {
        this.snackId = snackId;
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

}