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
 * Comments entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "comments", catalog = "limmai")
public class Comments implements java.io.Serializable
{

    // Fields

    private String commentsId;
    private String ofOrdersId;
    private String ofCookbookId;
    private String ofCustomerId;
    private Integer commentType;
    private Integer marks;
    private String addDate;
    private Set<CommentsPic> commentsPics = new HashSet<CommentsPic>(0);
    private Set<CommentsContent> commentsContents = new HashSet<CommentsContent>(
            0);

    // Constructors

    /** default constructor */
    public Comments()
    {
    }

    /** full constructor */
    public Comments(String ofOrdersId, String ofCookbookId,
            String ofCustomerId, Integer commentType, Integer marks,
            String addDate, Set<CommentsPic> commentsPics,
            Set<CommentsContent> commentsContents)
    {
        this.ofOrdersId = ofOrdersId;
        this.ofCookbookId = ofCookbookId;
        this.ofCustomerId = ofCustomerId;
        this.commentType = commentType;
        this.marks = marks;
        this.addDate = addDate;
        this.commentsPics = commentsPics;
        this.commentsContents = commentsContents;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "COMMENTS_ID", unique = true, nullable = false, length = 36)
    public String getCommentsId()
    {
        return this.commentsId;
    }

    public void setCommentsId(String commentsId)
    {
        this.commentsId = commentsId;
    }

    @Column(name = "OF_ORDERS_ID", length = 36)
    public String getOfOrdersId()
    {
        return this.ofOrdersId;
    }

    public void setOfOrdersId(String ofOrdersId)
    {
        this.ofOrdersId = ofOrdersId;
    }

    @Column(name = "OF_COOKBOOK_ID", length = 36)
    public String getOfCookbookId()
    {
        return this.ofCookbookId;
    }

    public void setOfCookbookId(String ofCookbookId)
    {
        this.ofCookbookId = ofCookbookId;
    }

    @Column(name = "OF_CUSTOMER_ID", length = 36)
    public String getOfCustomerId()
    {
        return this.ofCustomerId;
    }

    public void setOfCustomerId(String ofCustomerId)
    {
        this.ofCustomerId = ofCustomerId;
    }

    @Column(name = "COMMENT_TYPE")
    public Integer getCommentType()
    {
        return this.commentType;
    }

    public void setCommentType(Integer commentType)
    {
        this.commentType = commentType;
    }

    @Column(name = "MARKS")
    public Integer getMarks()
    {
        return this.marks;
    }

    public void setMarks(Integer marks)
    {
        this.marks = marks;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "comments")
    public Set<CommentsPic> getCommentsPics()
    {
        return this.commentsPics;
    }

    public void setCommentsPics(Set<CommentsPic> commentsPics)
    {
        this.commentsPics = commentsPics;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "comments")
    public Set<CommentsContent> getCommentsContents()
    {
        return this.commentsContents;
    }

    public void setCommentsContents(Set<CommentsContent> commentsContents)
    {
        this.commentsContents = commentsContents;
    }

}