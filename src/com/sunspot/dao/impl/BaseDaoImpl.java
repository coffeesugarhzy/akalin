package com.sunspot.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sunspot.dao.BaseDao;

/**
 * 数据操作基础接口实现层
 * 
 * @author LuoAnDong
 * 
 */
@Scope("prototype")
@Repository
public class BaseDaoImpl implements BaseDao
{
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactory = null;

    private Session session = null;

    private Transaction transaction = null;

    public <T> void add(T t)
    {
        try
        {
            session = this.openSession();
            transaction = session.beginTransaction();
            if (null != t)
            {
                session.save(t);
                transaction.commit();

            }
        }
        catch (Exception e)
        {
            transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            this.closeSession(session);
        }
    }

    public <T> void update(T t)
    {
        try
        {
            session = this.openSession();
            transaction = session.beginTransaction();
            if (null != t)
            {
                session.update(t);
                transaction.commit();

            }
        }
        catch (Exception e)
        {
            transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            this.closeSession(session);
        }
    }

    public int update(String sql, Object[] param)
    {
        return this.jdbcTemplate.update(sql, param);
    }

    public <T> void delete(Class<T> t, Serializable id)
    {
        try
        {
            session = this.openSession();
            Object object = session.get(t, id);
            transaction = session.beginTransaction();
            if (null != object)
            {
                session.delete(object);
                transaction.commit();
            }
        }
        catch (Exception e)
        {
            transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            this.closeSession(session);
        }
    }

    public int delHQL(String sql, Object[] param)
    {
        Query query = null;
        try
        {
            session = this.openSession();
            query = session.createQuery(sql);
            if (null != param && 0 < param.length)
            {
                for (int i = 0; i < param.length; i++)
                {
                    query.setParameter(i, param[i]);
                }
            }

            query.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            this.closeSession(session);
        }
        return 0;
    }

    
    public Map<String, Object> queryForMap(String sql, Object[] param)
    {
        return this.jdbcTemplate.queryForMap(sql, param);
    }

    public <T> void delete(T t)
    {
        try
        {
            session = this.openSession();
            transaction = session.beginTransaction();
            if (null != t)
            {
                session.delete(t);
                transaction.commit();
            }
        }
        catch (Exception e)
        {
            transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            this.closeSession(session);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getByHibernate(Class<T> t, Serializable id)
    {
        try
        {
            session = this.openSession();
            return (T) session.get(t, id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            this.closeSession(session);
        }
    }

    public <T> List<T> query(String sql, Object[] param, Class<T> requiredType)
    {
        return this.jdbcTemplate.query(sql, param,
                new BeanPropertyRowMapper<T>(requiredType));
    }

    public <T> List<T> query(String sql, Class<T> requiredType)
    {
        return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(
                requiredType));
    }

    public <T> List<T> query(String sql, Object[] param, RowMapper<T> rowMapper)
    {
        return this.jdbcTemplate.query(sql, param, rowMapper);
    }

    public <T> T queryForObject(String sql, Object[] param,
            Class<T> requiredType)
    {
        return (T) this.jdbcTemplate.queryForObject(sql, param,
                new BeanPropertyRowMapper<T>(requiredType));
    }

    @SuppressWarnings("unchecked")
    public <T> T queryForObjectHQL(String sql, Object[] param)
    {
        Query query = null;
        try
        {
            session = this.openSession();
            query = session.createQuery(sql);
            if (null != param && 0 < param.length)
            {
                for (int i = 0; i < param.length; i++)
                {
                    query.setParameter(i, param[i]);
                }
            }

            Object[] objects = (Object[]) query.uniqueResult();
            if (null != objects && 0 < objects.length) { return (T) objects[0]; }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            this.closeSession(session);
        }
        return null;
    }

    public <T> T queryForObject(String sql, Class<T> requiredType)
    {
        return this.jdbcTemplate.queryForObject(sql, requiredType);
    }

    public List<Map<String, Object>> query(String sql, Object[] param)
    {
        return this.jdbcTemplate.queryForList(sql, param);
    }

    public List<Map<String, Object>> query(String sql)
    {
        return this.jdbcTemplate.queryForList(sql);
    }

    public int queryForIntPage(String sql, int maxResults)
    {
        int all = this.jdbcTemplate.queryForObject(sql, Integer.class);
        return all % maxResults == 0 ? all / maxResults : all / maxResults + 1;
    }

    public int queryForIntPage(String sql, Object[] param)
    {
        int all = this.jdbcTemplate.queryForObject(sql, Integer.class, param);
        return all;
    }

    public int queryForIntPage(String sql)
    {
        int all = this.jdbcTemplate.queryForObject(sql, Integer.class);
        return all;
    }

    public int queryForIntPage(String sql, Object[] param, int maxResults)
    {
        int all = this.jdbcTemplate.queryForObject(sql, Integer.class, param);
        return all % maxResults == 0 ? all / maxResults : all / maxResults + 1;
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper)
    {
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    private Session openSession()
    {
        return sessionFactory.openSession();
    }

    private void closeSession(Session session)
    {
        if (null != session)
        {
            session.close();
        }
    }
}
