package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Us entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "us", catalog = "limmai")
public class Us implements java.io.Serializable
{

    // Fields

    private String usId;
    private String remark;
    private String contact;
    private String versions;

    // Constructors

    /** default constructor */
    public Us()
    {
    }

    /** full constructor */
    public Us(String remark, String contact, String versions)
    {
        this.remark = remark;
        this.contact = contact;
        this.versions = versions;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "US_ID", unique = true, nullable = false, length = 36)
    public String getUsId()
    {
        return this.usId;
    }

    public void setUsId(String usId)
    {
        this.usId = usId;
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

    @Column(name = "CONTACT", length = 36)
    public String getContact()
    {
        return this.contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    @Column(name = "VERSIONS", length = 10)
    public String getVersions()
    {
        return this.versions;
    }

    public void setVersions(String versions)
    {
        this.versions = versions;
    }

}