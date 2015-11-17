package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * DataTypes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "data_types", catalog = "limmai")
public class DataTypes implements java.io.Serializable
{

    // Fields

    private String typeId;
    private String parentId;
    private String typeName;
    private Integer typeOrder;
    private Integer isSys;

    // Constructors

    /** default constructor */
    public DataTypes()
    {
    }

    /** full constructor */
    public DataTypes(String parentId, String typeName, Integer typeOrder,
            Integer isSys)
    {
        this.parentId = parentId;
        this.typeName = typeName;
        this.typeOrder = typeOrder;
        this.isSys = isSys;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "TYPE_ID", unique = true, nullable = false, length = 36)
    public String getTypeId()
    {
        return this.typeId;
    }

    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
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

    @Column(name = "TYPE_NAME", length = 36)
    public String getTypeName()
    {
        return this.typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    @Column(name = "TYPE_ORDER")
    public Integer getTypeOrder()
    {
        return this.typeOrder;
    }

    public void setTypeOrder(Integer typeOrder)
    {
        this.typeOrder = typeOrder;
    }

    @Column(name = "IS_SYS")
    public Integer getIsSys()
    {
        return this.isSys;
    }

    public void setIsSys(Integer isSys)
    {
        this.isSys = isSys;
    }

}