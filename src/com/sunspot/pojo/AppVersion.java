package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * AppVersion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_version", catalog = "limmai")
public class AppVersion implements java.io.Serializable
{

    // Fields

    private String versionId;
    private String updatesoftaddress;
    private String versioncode;
    private String versionname;
    private String addDate;

    // Constructors

    /** default constructor */
    public AppVersion()
    {
    }

    /** full constructor */
    public AppVersion(String updatesoftaddress, String versioncode,
            String versionname, String addDate)
    {
        this.updatesoftaddress = updatesoftaddress;
        this.versioncode = versioncode;
        this.versionname = versionname;
        this.addDate = addDate;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "VERSION_ID", unique = true, nullable = false, length = 36)
    public String getVersionId()
    {
        return this.versionId;
    }

    public void setVersionId(String versionId)
    {
        this.versionId = versionId;
    }

    @Column(name = "UPDATESOFTADDRESS")
    public String getUpdatesoftaddress()
    {
        return this.updatesoftaddress;
    }

    public void setUpdatesoftaddress(String updatesoftaddress)
    {
        this.updatesoftaddress = updatesoftaddress;
    }

    @Column(name = "VERSIONCODE", length = 36)
    public String getVersioncode()
    {
        return this.versioncode;
    }

    public void setVersioncode(String versioncode)
    {
        this.versioncode = versioncode;
    }

    @Column(name = "VERSIONNAME", length = 36)
    public String getVersionname()
    {
        return this.versionname;
    }

    public void setVersionname(String versionname)
    {
        this.versionname = versionname;
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