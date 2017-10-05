package com.base.common.sql.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.base.common.sql.SqlSessionFactoryManager;

/**
 * 
 * 数据库访问层模板
 * 
 * @author Administrator、Dr.Deng、
 * 
 */
public class DaoTemplate {

    private SqlSessionFactory sqlSessionFactory;

    protected String namespace;

    public DaoTemplate() {
        this(true);
    }

    /**
     * 使用默认的环境
     * 
     * @param environmentDefault
     */
    public DaoTemplate(boolean environmentDefault) {
        this(SqlSessionFactoryManager.getDefaultSqlSessionFactory());
    }

    public DaoTemplate(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * @return the sqlSessionFactory
     */
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    /**
     * @param sqlSessionFactory
     *            the sqlSessionFactory to set
     */
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * @param statement
     *            插入的记录的SQL;
     * @return 新插入记录的主键；
     */
    public Object insert(String statement) {
        SqlSession session = null;
        Object newKey;
        try {
            session = getSqlSessionFactory().openSession(true);
            newKey = session.insert(statement);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return newKey;
    }

    /**
     * @param statement
     *            插入的记录的SQL;
     * @param parameter
     *            SQL中对应的参数，{@link Map}/JavaBean;
     * @return 新插入记录的主键；
     */
    public Object insert(String statement, Object parameter) {
        SqlSession session = null;
        Object newKey;
        try {
            session = getSqlSessionFactory().openSession(true);
            newKey = session.insert(statement, parameter);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return newKey;
    }

    /**
     * 更新记录；
     * 
     * @param statement
     *            更新数据库记录的SQL;
     * @return 影响数据库的记录数；
     */
    public int update(String statement) {
        SqlSession session = null;
        int rows;
        try {
            session = getSqlSessionFactory().openSession(true);
            rows = session.update(statement);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return rows;
    }

    /**
     * @param statement
     *            更新数据库记录的SQL;
     * @param parameter
     *            更新SQL中对应的参数，{@link Map}/JavaBean;
     * @return 影响数据库的记录数；
     */
    public int update(String statement, Object parameter) {
        SqlSession session = null;
        int rows;
        try {
            session = getSqlSessionFactory().openSession(true);
            rows = session.update(statement, parameter);
            session.commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return rows;
    }

    /**
     * 删除记录；
     * 
     * @param statement
     *            指定的删除操作SQL；
     * @return 影响数据库的记录数；
     */
    public int delete(String statement) {
        SqlSession session = null;
        int rows;
        try {
            session = getSqlSessionFactory().openSession(true);
            rows = session.delete(statement);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return rows;
    }

    /**
     * @param statement
     *            指定的删除操作SQL；
     * @param parameter
     *            删除操作SQL中的参数， {@link Map}/JavaBean；
     * @return 影响数据库的记录数；
     */
    public int delete(String statement, Object parameter) {
        SqlSession session = null;
        int rows;
        try {
            session = getSqlSessionFactory().openSession(true);
            rows = session.delete(statement, parameter);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return rows;
    }

    /**
     * 查询；
     * 
     * @param statement
     *            查询记录的SQL
     * @return 记录集合{@link List}，每个元素按配置可为 {@link Map} or JavaBean;
     */
    public List<?> select(String statement) {
        SqlSession session = null;
        List<?> rows;
        try {
            session = getSqlSessionFactory().openSession(true);
            rows = session.selectList(statement);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return rows;
    }

    public SqlSession getSqlSession() {
        try {
            return getSqlSessionFactory().openSession(true);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 条件查询；
     * 
     * @param statement
     *            查询记录的SQL
     * @param parameter
     *            查询SQL的参数；
     * @return 记录集合， {@link List}，每个元素按配置可为 {@link Map} or JavaBean;
     */
    public List<?> select(String statement, Object parameter) {
        SqlSession session = null;
        List<?> rows;
        try {
            session = getSqlSessionFactory().openSession(true);
            rows = session.selectList(statement, parameter);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return rows;
    }

    /**
     * 指定语句查询；
     * 
     * @param statement
     *            查询记录的SQL
     * @return 一条记录， {@link Map} or JavaBean;
     */
    public Object selectOne(String statement) {
        SqlSession session = null;
        Object row;
        try {
            session = getSqlSessionFactory().openSession(true);
            row = session.selectOne(statement);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return row;
    }

    /**
     * 指定条件查询；
     * 
     * @param statement
     *            查询记录的SQL
     * @param parameter
     *            参数，一般包含数据记录的主键；
     * @return 一条记录， {@link Map} or JavaBean;
     */
    public Object selectOne(String statement, Object parameter) {
        SqlSession session = null;
        Object row;
        try {
            session = getSqlSessionFactory().openSession(true);
            row = session.selectOne(statement, parameter);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return row;
    }

}