package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Feedback entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "feedback", catalog = "limmai")
public class Feedback implements java.io.Serializable
{

    // Fields

    private String feebackId;
    private String content;
    private String contact;
    private String addDate;

    // Constructors

    /** default constructor */
    public Feedback()
    {
    }

    /** full constructor */
    public Feedback(String content, String contact, String addDate)
    {
        this.content = content;
        this.contact = contact;
        this.addDate = addDate;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "FEEBACK_ID", unique = true, nullable = false, length = 36)
    public String getFeebackId()
    {
        return this.feebackId;
    }

    public void setFeebackId(String feebackId)
    {
        this.feebackId = feebackId;
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

    @Column(name = "CONTACT", length = 36)
    public String getContact()
    {
        return this.contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
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