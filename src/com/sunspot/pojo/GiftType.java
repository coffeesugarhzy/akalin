package com.sunspot.pojo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * GiftType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gift_type", catalog = "limmai")
public class GiftType implements java.io.Serializable
{

    // Fields

    private String giftTypeId;
    private String giftTypeName;
    private String addDate;
    private Set<Gift> gifts = new HashSet<Gift>(0);

    // Constructors

    /** default constructor */
    public GiftType()
    {
    }

    /** full constructor */
    public GiftType(String giftTypeName, String addDate, Set<Gift> gifts)
    {
        this.giftTypeName = giftTypeName;
        this.addDate = addDate;
        this.gifts = gifts;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "GIFT_TYPE_ID", unique = true, nullable = false, length = 36)
    public String getGiftTypeId()
    {
        return this.giftTypeId;
    }

    public void setGiftTypeId(String giftTypeId)
    {
        this.giftTypeId = giftTypeId;
    }

    @Column(name = "GIFT_TYPE_NAME", length = 50)
    public String getGiftTypeName()
    {
        return this.giftTypeName;
    }

    public void setGiftTypeName(String giftTypeName)
    {
        this.giftTypeName = giftTypeName;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "giftType")
    public Set<Gift> getGifts()
    {
        return this.gifts;
    }

    public void setGifts(Set<Gift> gifts)
    {
        this.gifts = gifts;
    }

}