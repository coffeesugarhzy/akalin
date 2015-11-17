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
 * CommentsContent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "comments_content", catalog = "limmai")
public class CommentsContent implements java.io.Serializable
{

    // Fields

    private String contentId;
    private Comments comments;
    private String content;
    private Integer isAppend;
    private String addDate;

    // Constructors

    /** default constructor */
    public CommentsContent()
    {
    }

    /** full constructor */
    public CommentsContent(Comments comments, String content, Integer isAppend,
            String addDate)
    {
        this.comments = comments;
        this.content = content;
        this.isAppend = isAppend;
        this.addDate = addDate;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "CONTENT_ID", unique = true, nullable = false, length = 36)
    public String getContentId()
    {
        return this.contentId;
    }

    public void setContentId(String contentId)
    {
        this.contentId = contentId;
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

    @Column(name = "CONTENT", length = 65535)
    public String getContent()
    {
        return this.content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    @Column(name = "IS_APPEND")
    public Integer getIsAppend()
    {
        return this.isAppend;
    }

    public void setIsAppend(Integer isAppend)
    {
        this.isAppend = isAppend;
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