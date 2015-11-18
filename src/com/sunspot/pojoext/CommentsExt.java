package com.sunspot.pojoext;

import java.util.ArrayList;
import java.util.List;

import com.sunspot.pojo.CommentsContent;
import com.sunspot.pojo.CommentsPic;

public class CommentsExt
{

    private String commentsId;

    private String customName;

    private String telphone;

    private String ordersCode;

    private double payMoney;
   
    private Integer commentType;

    private Integer marks;

    private String addDate;

    private List<CommentsPic> commentsPics = new ArrayList<CommentsPic>(0);

    private List<CommentsContent> commentsContents = new ArrayList<CommentsContent>(
            0);

    public String getCommentsId()
    {
        return commentsId;
    }

    public void setCommentsId(String commentsId)
    {
        this.commentsId = commentsId;
    }

    public String getCustomName()
    {
        return customName;
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    public String getTelphone()
    {
        return telphone;
    }

    public void setTelphone(String telphone)
    {
        this.telphone = telphone;
    }

    public String getOrdersCode()
    {
        return ordersCode;
    }

    public void setOrdersCode(String ordersCode)
    {
        this.ordersCode = ordersCode;
    }

    public double getPayMoney()
    {
        return payMoney;
    }

    public void setPayMoney(double payMoney)
    {
        this.payMoney = payMoney;
    }

    public Integer getCommentType()
    {
        return commentType;
    }

    public void setCommentType(Integer commentType)
    {
        this.commentType = commentType;
    }

    public Integer getMarks()
    {
        return marks;
    }

    public void setMarks(Integer marks)
    {
        this.marks = marks;
    }

    public String getAddDate()
    {
        return addDate;
    }

    public void setAddDate(String addDate)
    {
        this.addDate = addDate;
    }

    public List<CommentsPic> getCommentsPics()
    {
        return commentsPics;
    }

    public void setCommentsPics(List<CommentsPic> commentsPics)
    {
        this.commentsPics = commentsPics;
    }

    public List<CommentsContent> getCommentsContents()
    {
        return commentsContents;
    }

    public void setCommentsContents(List<CommentsContent> commentsContents)
    {
        this.commentsContents = commentsContents;
    }

}
