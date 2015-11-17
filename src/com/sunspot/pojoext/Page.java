package com.sunspot.pojoext;

/**
 * 分页信息
 * 
 * @author LuoAnDong
 * 
 */
public class Page
{
    // 默认当前页
    private static final Integer DEFAULT_CURPAGE = 1;

    // 默认每页记录条数
    private static final Integer DEFAULT_PAGENUM = 10;

    // 当前页
    private Integer curPage;

    // 每页记录数
    private Integer pageNum;

    // 总记录数
    private Integer totalPage;

    // 总页数
    private Integer totalNum;

    /**
     * 构造函数
     */
    public Page()
    {
        curPage = DEFAULT_CURPAGE;
        pageNum = DEFAULT_PAGENUM;
    }

    /**
     * 获取当前查询记录序号
     * 
     * @return 序号值
     */
    public Integer getCurRecordSeq()
    {
        return (this.getCurPage() - 1) * this.getPageNum();
    }

    public Integer getCurPage()
    {
        curPage = isNullByInt(curPage) ? DEFAULT_CURPAGE : curPage;
        return curPage;
    }

    public void setCurPage(Integer curPage)
    {
        this.curPage = curPage;
    }

    public Integer getPageNum()
    {
        pageNum = isNullByInt(pageNum) ? DEFAULT_PAGENUM : pageNum;
        return pageNum;
    }

    public void setPageNum(Integer pageNum)
    {
        this.pageNum = pageNum;
    }

    public Integer getTotalPage()
    {
        if (0 >= getTotalNum())
        {
            return 0;
        }

        totalPage = (int) Math.ceil((float) getTotalNum()
                / (float) getPageNum());

        return totalPage;
    }

    public void setTotalPage(int totalPage)
    {
        this.totalPage = totalPage;
    }

    public Integer getTotalNum()
    {
        totalNum = isNullByInt(totalNum) ? 0 : totalNum;
        return totalNum;
    }

    public void setTotalNum(Integer totalNum)
    {
        this.totalNum = totalNum;
    }

    /**
     * 判断数值对象是否为空
     * 
     * @param value
     *            值
     * @return true | false
     */
    private boolean isNullByInt(Integer value)
    {
        if (null == value || 0 >= value)
        {
            return true;
        }

        return false;
    }
}
