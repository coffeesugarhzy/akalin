package com.sunspot.common;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
 
public class DateUtil
{
    public static String getCurrentDateTime()
    {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentDate()
    {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
    }

    public static void main(String[] args)
    {
        System.out.println(getCurrentDateTime());
    }
}
