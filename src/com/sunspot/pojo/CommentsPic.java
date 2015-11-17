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
 * CommentsPic entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "comments_pic", catalog = "limmai")
public class CommentsPic implements java.io.Serializable
{

    // Fields

    private String picId;
    private Comments comments;
    private String picPath;
    private String addDate;

    // Constructors

    /** default constructor */
    public CommentsPic()
    {
    }

    /** full constructor */
    public CommentsPic(Comments comments, String picPath, String addDate)
    {
        this.comments = comments;
        this.picPath = picPath;
        this.addDate = addDate;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "PIC_ID", unique = true, nullable = false, length = 36)
    public String getPicId()
    {
        return this.picId;
    }

    public void setPicId(String picId)
    {
        this.picId = picId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENTS_ID")
    public Comments getComments()
    {
        return this.comments;
    }

    public void setComments(Comments comments)
    {
        this.comments = comments;
    }

    @Column(name = "PIC_PATH")
    public String getPicPath()
    {
        return this.picPath;
    }

    public void setPicPath(String picPath)
    {
        this.picPath = picPath;
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