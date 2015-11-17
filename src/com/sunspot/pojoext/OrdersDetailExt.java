package com.sunspot.pojoext;

import com.sunspot.pojo.OrdersDetail;

/**
 * 订单详情扩展
 * 
 * @author LuoAnDong
 * 
 */
@SuppressWarnings("serial")
public class OrdersDetailExt extends OrdersDetail
{
    // 菜谱名
    private String cookName;

    private String cookbookLogo;

    // 类型名
    private String typeName;

    // 评份
    private Integer marks;

    // 菜谱类型
    private Integer cookType;

    // 建议就就餐人数
    private String suggest;
    
    

    public String getCookName()
    {
        return cookName;
    }

    public void setCookName(String cookName)
    {
        this.cookName = cookName;
    }

    public String getCookbookLogo()
    {
        return cookbookLogo;
    }

    public void setCookbookLogo(String cookbookLogo)
    {
        this.cookbookLogo = cookbookLogo;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public Integer getMarks()
    {
        return marks;
    }

    public void setMarks(Integer marks)
    {
        this.marks = marks;
    }

    public Integer getCookType()
    {
        return cookType;
    }

    public void setCookType(Integer cookType)
    {
        this.cookType = cookType;
    }

    public String getSuggest()
    {
        return suggest;
    }

    public void setSuggest(String suggest)
    {
        this.suggest = suggest;
    }

}
