package com.base.common.sql;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

/**
 * 非事物会话管理
 * 
 * @author hhx
 *
 */
public class SqlSessionFactoryManager {

    /**
     * 日志对象
     */
    private static Logger logger = Logger.getLogger(SqlSessionFactoryManager.class);

    /**
     * 默认Session Factory ID
     */
    private static final String DEFAULT_SESSION_FACTORY_ID = "default";

    /**
     * sqlSessionFactory Map
     */
    private static Map<String, SqlSessionFactory> sqlSessionFactorys = Collections
            .synchronizedMap(new HashMap<String, SqlSessionFactory>());

    /**
     * 传入resource路径和Properties对象获得SqlSessionFactory
     * 
     * @param sfId
     *            sqlSessionFactory存储ID
     * @param resource
     *            资源路径
     * @param properties
     *            属性配置
     * @return SqlSessionFactory
     * @throws IOException
     */
    public static SqlSessionFactory getSqlSessionFactory(String sfId, String resource, Properties properties)
            throws IOException {
        return getSqlSessionFactory(sfId, resource, null, properties);
    }

    /**
     * 传入resource路径和Properties对象获得SqlSessionFactory
     * 
     * @param sfId
     *            sqlSessionFactory存储ID
     * @param resource
     *            资源路径
     * @param properties
     *            属性配置
     * @return SqlSessionFactory
     * @throws IOException
     */
    public static SqlSessionFactory getSqlSessionFactory(String sfId, String resource, String envId,
            Properties properties) throws IOException {
        if (sqlSessionFactorys.containsKey(sfId)) {
            return sqlSessionFactorys.get(sfId);
        } else {
            Reader reader = Resources.getResourceAsReader(resource);

            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, envId, properties);
            sqlSessionFactorys.put(sfId, sqlSessionFactory);
            return sqlSessionFactory;
        }
    }

    /**
     * 从列表中获得SqlSessionFactory
     * 
     * @param sfId
     *            sqlSessionFactory存储ID
     * @return SqlSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory(String sfId) {
        if (sqlSessionFactorys.containsKey(sfId)) {
            return sqlSessionFactorys.get(sfId);
        } else {
            logger.warn("SqlSessionFactory 数据库连接对象初始化失败！");
            return null;
        }
    }

    /**
     * 初始化默认SqlSessionFactory
     * 
     * @param resource
     * @param properties
     * @return
     * @throws IOException
     */
    public static SqlSessionFactory initDefaultSqlSessionFactory(String resource, Properties properties)
            throws IOException {
        return getSqlSessionFactory(DEFAULT_SESSION_FACTORY_ID, resource, properties);
    }

    /**
     * 从列表中获得默认SqlSessionFactory
     * 
     * @return SqlSessionFactory
     */
    public static SqlSessionFactory getDefaultSqlSessionFactory() {
        return getSqlSessionFactory(DEFAULT_SESSION_FACTORY_ID);
    }
}
