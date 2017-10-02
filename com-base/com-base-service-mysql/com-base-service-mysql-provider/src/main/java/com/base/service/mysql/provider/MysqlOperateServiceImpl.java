package com.base.service.mysql.provider;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.base.service.mysql.api.MysqlOperateService;

public class MysqlOperateServiceImpl implements MysqlOperateService{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> queryForList(String sql, Object[] param) {
        return jdbcTemplate.queryForList(sql, param);
    }
    
    /**
     * 查询返回集合
     * @param sql
     * @param param
     * @return
     */
    public <T> List<T> queryForList(String sql,Class<T> classType,Object[] param){
        return jdbcTemplate.queryForList(sql, classType, param);
    }

    public Map<String, Object> queryObject(String sql, Object[] param) {
        List<Map<String,Object>> list = queryForList(sql, param);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
    
    /**
     * 返回一条数据
     * @param sql 查询sql
     * @param params 查询条件
     * @return
     */
    public <T> T queryObject(String sql,Class<T> classType,Object[] param){
        return jdbcTemplate.queryForObject(sql, classType,param);
    }
    
    /**
     * 查询数据返回INT类型，如查询统计等等。
     * @param sql
     * @param param
     * @return
     */
    public int queryForInt(String sql,Object[] param){
        return jdbcTemplate.queryForInt(sql, param);
    }

}