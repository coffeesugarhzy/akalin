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
 * CustomLike entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "custom_like", catalog = "limmai")
public class CustomLike implements java.io.Serializable
{

    // Fields

    private String likeId;
    private CustomInfo customInfo;
    private Integer likeType;
    private String goodsId;
    private String addDate;

    // Constructors

    /** default constructor */
    public CustomLike()
    {
    }

    /** full constructor */
    public CustomLike(CustomInfo customInfo, Integer likeType, String goodsId,
            String addDate)
    {
        this.customInfo = customInfo;
        this.likeType = likeType;
        this.goodsId = goodsId;
        this.addDate = addDate;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "LIKE_ID", unique = true, nullable = false, length = 36)
    public String getLikeId()
    {
        return this.likeId;
    }

    public void setLikeId(String likeId)
    {
        this.likeId = likeId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOM_ID")
    public CustomInfo getCustomInfo()
    {
        return this.customInfo;
    }

    public void setCustomInfo(CustomInfo customInfo)
    {
        this.customInfo = customInfo;
    }

    @Column(name = "LIKE_TYPE")
    public Integer getLikeType()
    {
        return this.likeType;
    }

    public void setLikeType(Integer likeType)
    {
        this.likeType = likeType;
    }

    @Column(name = "GOODS_ID", length = 36)
    public String getGoodsId()
    {
        return this.goodsId;
    }

    public void setGoodsId(String goodsId)
    {
        this.goodsId = goodsId;
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