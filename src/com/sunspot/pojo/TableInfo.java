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
 * TableInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "table_info", catalog = "limmai")
public class TableInfo implements java.io.Serializable
{

    // Fields

    private String tableInfoId;
    private Shop shop;
    private String tableName;
    private Integer tableVolume;
    private Integer tableNum;

    // Constructors

    /** default constructor */
    public TableInfo()
    {
    }

    /** full constructor */
    public TableInfo(Shop shop, String tableName, Integer tableVolume,
            Integer tableNum)
    {
        this.shop = shop;
        this.tableName = tableName;
        this.tableVolume = tableVolume;
        this.tableNum = tableNum;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "TABLE_INFO_ID", unique = true, nullable = false, length = 36)
    public String getTableInfoId()
    {
        return this.tableInfoId;
    }

    public void setTableInfoId(String tableInfoId)
    {
        this.tableInfoId = tableInfoId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_ID")
    public Shop getShop()
    {
        return this.shop;
    }

    public void setShop(Shop shop)
    {
        this.shop = shop;
    }

    @Column(name = "TABLE_NAME", length = 36)
    public String getTableName()
    {
        return this.tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    @Column(name = "TABLE_VOLUME")
    public Integer getTableVolume()
    {
        return this.tableVolume;
    }

    public void setTableVolume(Integer tableVolume)
    {
        this.tableVolume = tableVolume;
    }

    @Column(name = "TABLE_NUM")
    public Integer getTableNum()
    {
        return this.tableNum;
    }

    public void setTableNum(Integer tableNum)
    {
        this.tableNum = tableNum;
    }

}