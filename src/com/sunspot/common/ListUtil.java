package com.sunspot.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
public class ListUtil
{
    public static List<Double> getDoubleList()
    {
        return new ArrayList<Double>();
    }

    public static <T> List<T> getObjectList()
    {
        return new ArrayList<T>();
    }

    public static List<String> getStringList()
    {
        return new ArrayList<String>();
    }

    public static <T> Set<T> getHashSet()
    {
        return new HashSet<T>();
    }

    public static Map<String, Object> getHashMap()
    {
        return new HashMap<String, Object>();
    }
}
