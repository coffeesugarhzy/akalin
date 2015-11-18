package com.sunspot.pojoext;

import com.sunspot.pojo.FarmProduce;

/**
 * 农产品扩展
 * @author LuoAnDong
 *
 */
@SuppressWarnings("serial")
public class FarmProduceExt extends FarmProduce
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
