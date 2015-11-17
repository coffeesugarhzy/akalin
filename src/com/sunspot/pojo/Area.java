package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Area entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "area", catalog = "limmai")
public class Area implements java.io.Serializable
{

    // Fields

    private String areaId;
    private String parentId;
    private String areaName;
    private Integer level;
    private Integer areaOrder;
    
    // Constructors

    /** default constructor */
    public Area()
    {
    }

    /** full constructor */
    public Area(String parentId, String areaName, Integer level,
            Integer areaOrder)
    {
        this.parentId = parentId;
        this.areaName = areaName;
        this.level = level;
        this.areaOrder = areaOrder;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "AREA_ID", unique = true, nullable = false, length = 36)
    public String getAreaId()
    {
        return this.areaId;
    }

    public void setAreaId(String areaId)
    {
        this.areaId = areaId;
    }

    @Column(name = "PARENT_ID", length = 36)
    public String getParentId()
    {
        return this.parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    @Column(name = "AREA_NAME", length = 36)
    public String getAreaName()
    {
        return this.areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    @Column(name = "LEVEL")
    public Integer getLevel()
    {
        return this.level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    @Column(name = "AREA_ORDER")
    public Integer getAreaOrder()
    {
        return this.areaOrder;
    }

    public void setAreaOrder(Integer areaOrder)
    {
        this.areaOrder = areaOrder;
    }

}