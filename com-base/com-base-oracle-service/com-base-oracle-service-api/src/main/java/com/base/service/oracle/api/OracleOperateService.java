package com.base.service.oracle.api;

import java.util.List;
import java.util.Map;

/**
 * 
 * oracle操作接口服务
 *
 * @author <a href="mailto:xxxx@xxx.com">xxx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2016-11-7
 */
public interface OracleOperateService {
    /**
     * 查询返回集合
     * @param sql
     * @return
     */
    public List<Map<String,Object>> queryForList(String sql);

    /**
     * 查询返回集合
     * @param sql
     * @param param
     * @return
     */
    public List<Map<String,Object>> queryForList(String sql, Object[] param);
    
    
    /**
     * 查询返回集合
     * @param sql
     * @param classType
     * @return
     */
    public <T> List<T> queryObjectList(String sql, Class<T> classType);
    
    /**
     * 查询返回集合
     * @param sql
     * @param param
     * @return
     */
    public <T> List<T> queryForList(String sql, Class<T> classType, Object[] param);
    
    
    /**
     * 返回一条数据
     * @param sql 查询sql
     * @return
     */
    public Map<String,Object> queryObject(String sql);
    
    /**
     * 返回一条数据
     * @param sql 查询sql
     * @param params 查询条件
     * @return
     */
    public Map<String,Object> queryObject(String sql, Object[] param);
    
    /**
     * 返回一条数据
     * @param sql 查询sql
     * @param classType 查询条件
     * @return
     */
    public <T> T queryItem(String sql, Class<T> classType);
    
    /**
     * 返回一条数据
     * @param sql 查询sql
     * @param params 查询条件
     * @return
     */
    public <T> T queryObject(String sql, Class<T> classType, Object[] param);
    
    /**
     * 查询数据返回INT类型，如查询统计等等。
     * @param sql
     * @return
     */
    public int queryForInt(String sql);
    
    /**
     * 查询数据返回INT类型，如查询统计等等。
     * @param sql
     * @param param
     * @return
     */
    public int queryForInt(String sql, Object[] param);
}
