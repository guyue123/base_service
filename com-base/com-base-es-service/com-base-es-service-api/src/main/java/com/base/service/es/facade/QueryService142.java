package com.base.service.es.facade;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.base.service.es.bean.QueryCondition;
import com.base.service.es.bean.SelectResultBean;
import com.base.service.es.bean.StatsResult;

/**
 * ��ES��ѯ����
 * @author base
 *
 */
public interface QueryService142 {
	/**
	 * �¼����ѯͳ��
	 * @param cond 
	 * @return
	 */
	List<Map<String, Object>> groupByQuery(QueryCondition cond) throws IOException;
	
	/**
	 * ��ѯ�����б�
	 * @param cond 
	 * @return
	 */
	public SelectResultBean select(String sql) throws Exception;
	
	/**
	 * ͳ������
	 * @param cond 
	 * @return
	 */
	public long selectCount(String sql) throws Exception;
	
	/**
     * ��ѯ�ܺ�
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public double selectSum(String sql) throws Exception;
    
    /**
     * ��ѯ���ֵ
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public double selectMax(String sql) throws Exception;
    
    /**
     * ��ѯ��Сֵ
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public double selectMin(String sql) throws Exception;
    
    
    /**
     * ��ѯ�����С��ƽ�����ܼƵ�ֵ
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public StatsResult selectStats(String sql) throws Exception;
    
    /**
     * ��ѯƽ��ֵ
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public double selectAvg(String sql) throws Exception;
    
    /**
     * �����ѯͳ��
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public List<Map<String, Object>> selectGroupBy(String sql) throws Exception;
    
    /**
     * �������ݵ����ݿ���
     * 
     * @param indexName ��������
     * @param typeName ��������
     * @param dataList �����б�
     * @return
     * @throws Exception
     */
    public int insert(String indexName, String typeName, List<Map<String, Object>> dataList) throws Exception;
    
}
