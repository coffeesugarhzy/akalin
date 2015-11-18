package com.sunspot.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * MakeMethod entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "make_method", catalog = "limmai")
public class MakeMethod implements java.io.Serializable
{

    // Fields

    private String methodId;
    private String methodName;

    // Constructors

    /** default constructor */
    public MakeMethod()
    {
    }

    /** full constructor */
    public MakeMethod(String methodName)
    {
        this.methodName = methodName;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "METHOD_ID", unique = true, nullable = false, length = 36)
    public String getMethodId()
    {
        return this.methodId;
    }

    public void setMethodId(String methodId)
    {
        this.methodId = methodId;
    }

    @Column(name = "METHOD_NAME", length = 10)
    public String getMethodName()
    {
        return this.methodName;
    }

    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

}