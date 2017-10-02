package com.base.service.mysql.api;

import java.util.List;
import java.util.Map;

/**
 * 
 * mysql操作接口服务
 *
 * @author <a href="mailto:xxx@163.com.cn">lsc</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2016-11-7
 */
public interface MysqlOperateService {

    /**
     * 查询返回集合
     * @param sql
     * @param param
     * @return
     */
    public List<Map<String,Object>> queryForList(String sql,Object[] param);
    
    /**
     * 查询返回集合
     * @param sql
     * @param param
     * @return
     */
    public <T> List<T> queryForList(String sql,Class<T> classType,Object[] param);
    
    /**
     * 返回一条数据
     * @param sql 查询sql
     * @param params 查询条件
     * @return
     */
    public Map<String,Object> queryObject(String sql,Object[] param);
    
    /**
     * 返回一条数据
     * @param sql 查询sql
     * @param params 查询条件
     * @return
     */
    public <T> T queryObject(String sql,Class<T> classType,Object[] param);
    
    /**
     * 查询数据返回INT类型，如查询统计等等。
     * @param sql
     * @param param
     * @return
     */
    public int queryForInt(String sql,Object[] param);
}
