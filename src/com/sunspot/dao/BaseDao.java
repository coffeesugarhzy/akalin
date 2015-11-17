package com.sunspot.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

/**
 * 数据操作基础接口
 * 
 * @author LuoAnDong
 * 
 */
public interface BaseDao
{
    <T> void add(T t);

    <T> void update(T t);

    int update(String sql, Object[] param);
    
    int delHQL(String sql, Object[] param);

    <T> void delete(T t);

    <T> void delete(Class<T> t, Serializable id);
    
    <T> T getByHibernate(Class<T> t, Serializable id);

    List<Map<String, Object>> query(String sql, Object[] param);

    List<Map<String, Object>> query(String sql);

    <T> List<T> query(String sql, Object[] param, Class<T> requiredType);

    <T> List<T> query(String sql, Class<T> requiredType);

    <T> List<T> query(String sql, RowMapper<T> rowMapper);

    <T> List<T> query(String sql, Object[] param, RowMapper<T> rowMapper);

    <T> T queryForObject(String sql, Class<T> requiredType);

    <T> T queryForObject(String sql, Object[] param, Class<T> requiredType);

    <T> T queryForObjectHQL(String sql,  Object[] param);
    
    Map<String, Object> queryForMap(String sql, Object[] param);

    int queryForIntPage(String sql, int maxResults);

    int queryForIntPage(String sql, Object[] param);

    int queryForIntPage(String sql);

    int queryForIntPage(String sql, Object[] param, int maxResults);

}
