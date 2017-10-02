package com.base.service.es.facade;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.base.service.es.bean.QueryCondition;
import com.base.service.es.bean.SelectResultBean;
import com.base.service.es.bean.StatsResult;

/**
 * 从ES查询数据
 * @author base
 *
 */
public interface QueryService142 {
	/**
	 * 事件表查询统计
	 * @param cond 
	 * @return
	 */
	List<Map<String, Object>> groupByQuery(QueryCondition cond) throws IOException;
	
	/**
	 * 查询数据列表
	 * @param cond 
	 * @return
	 */
	public SelectResultBean select(String sql) throws Exception;
	
	/**
	 * 统计数量
	 * @param cond 
	 * @return
	 */
	public long selectCount(String sql) throws Exception;
	
	/**
     * 查询总和
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public double selectSum(String sql) throws Exception;
    
    /**
     * 查询最大值
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public double selectMax(String sql) throws Exception;
    
    /**
     * 查询最小值
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public double selectMin(String sql) throws Exception;
    
    
    /**
     * 查询最大，最小，平均，总计等值
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public StatsResult selectStats(String sql) throws Exception;
    
    /**
     * 查询平均值
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public double selectAvg(String sql) throws Exception;
    
    /**
     * 分组查询统计
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public List<Map<String, Object>> selectGroupBy(String sql) throws Exception;
    
    /**
     * 插入数据到数据库中
     * 
     * @param indexName 索引名称
     * @param typeName 索引表名
     * @param dataList 数据列表
     * @return
     * @throws Exception
     */
    public int insert(String indexName, String typeName, List<Map<String, Object>> dataList) throws Exception;
    
}
