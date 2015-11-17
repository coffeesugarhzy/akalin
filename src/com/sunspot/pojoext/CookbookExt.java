package com.sunspot.pojoext;

import com.sunspot.pojo.Cookbook;

/**
 * 家宴扩展类
 * @author LuoAnDong
 *
 */
@SuppressWarnings("serial")
public class CookbookExt extends Cookbook
{
    /**
     * 产品数量 
     */
    private String numExt ;

    public String getNumExt()
    {
        return numExt;
    }

    public void setNumExt(String numExt)
    {
        this.numExt = numExt;
    }
 
    
}
