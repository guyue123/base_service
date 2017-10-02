/**
 * 
 */
package com.base.service.es142.util;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.cxf.common.util.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.nlpcn.es4sql.exception.SqlParseException;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.base.service.es.bean.SelectResultBean;
import com.base.service.es.bean.StatsResult;

/**
 * @author base
 *
 */
public class EsClientUtil {
    private static Logger logger = LoggerFactory.getLogger(EsClientUtil.class); 
    

    
    /**
     * 查询数据集
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public static SelectResultBean select(String sql) throws Exception {
        checkSql(sql);

        SelectResultBean result = new SelectResultBean();
        SearchHits hits = query(sql);
        Map<String, List<Map<String, Object>>> mapList = EsUtil.convert2MapList(hits);
        result.setResults(mapList);

        // 匹配结果总数
        result.setTotal(hits.getTotalHits());
        
        return result;
    }

    /**
     * 查询总数
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public static long selectCount(String sql) throws Exception {
        checkSql(sql);

        Aggregations result = queryAgg(sql);
        Object count = result.get(result.asList().get(0).getName());

        if (count instanceof ValueCount) {
            return ((ValueCount) count).getValue();
        } else {
            return ((InternalCardinality) count).getValue();
        }
    }

    /**
     * 查询总和
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public static double selectSum(String sql) throws Exception {
        checkSql(sql);

        Aggregations result = queryAgg(sql);

        Sum sum = result.get(result.asList().get(0).getName());
        return sum.getValue();
    }


    /**
     * 检查SQL是否合法
     * 
     * @param sql
     * @throws Exception
     */
    private static void checkSql(String sql) throws Exception {
        // 查询SQL
        if (StringUtils.isEmpty(sql)) {
            throw new Exception("没有传入正确的参数");
        }
    }

    /**
     * 查询最大值
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public static double selectMax(String sql) throws Exception {
        // 查询SQL
        checkSql(sql);

        Aggregations result = queryAgg(sql);

        Max max = result.get(result.asList().get(0).getName());
        return max.getValue();
    }

    /**
     * 查询最小值
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public static double selectMin(String sql) throws Exception {
        // 查询SQL
        checkSql(sql);
        
        Aggregations result = queryAgg(sql);

        Min min = result.get(result.asList().get(0).getName());
        return min.getValue();

    }

    /**
     * 查询最大，最小，平均，总计等值
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public static StatsResult selectStats(String sql) throws Exception {
        // 查询SQL
        checkSql(sql);

        Aggregations result = queryAgg(sql);

        StatsResult set = new StatsResult();
        Stats stats = result.get(result.asList().get(0).getName());
        set.setCount(stats.getCount());
        set.setAvg(stats.getAvg());
        set.setMax(stats.getMax());
        set.setMin(stats.getMin());
        set.setSum(stats.getSum());
        
        return set;

    }

    /**
     * 查询平均值
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public static double selectAvg(String sql) throws Exception {
        // 查询SQL
        checkSql(sql);

        Aggregations result = queryAgg(sql);

        Avg avg = result.get(result.asList().get(0).getName());
        return avg.getValue();

    }

    /**
     * 分组查询统计
     * 
     * @param params
     * @return
     * @throws Exception 
     */
    public static List<Map<String, Object>> selectGroupBy(String sql) throws Exception {
        // 查询SQL
        checkSql(sql);

        Aggregations result = queryAgg(sql);
        // Map<String, Aggregation> aggs = result.asMap();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        
        List<Aggregation> gList = result.asList();
        
        for (Aggregation agg : gList) {
            Terms gender = result.get(agg.getName());
            resultList.addAll(setGroupValue(gender, agg.getName(), new HashMap<String, String>()));
        }

        return resultList;
    }

    /**
     * 查询对象
     * 
     * @param query
     * @return
     * @throws SqlParseException
     * @throws SQLFeatureNotSupportedException
     * @throws SQLFeatureNotSupportedException
     */
    private static SearchHits query(String query) throws SQLFeatureNotSupportedException,
            SQLFeatureNotSupportedException, SqlParseException {
        long startTime = System.currentTimeMillis();
        SearchRequestBuilder select = getSearchRequestBuilder(query);
        select.setSearchType(SearchType.QUERY_THEN_FETCH);
        SearchHits hits = select.get().getHits();
        logger.info("查询消耗时间：" + (System.currentTimeMillis() - startTime) + "毫秒；SQL：" + query);
        return hits;
    }

    /**
     * 查询统计结果
     * 
     * @param query
     * @return
     * @throws SqlParseException
     * @throws SQLFeatureNotSupportedException
     */
    private static Aggregations queryAgg(String query) throws SQLFeatureNotSupportedException, SqlParseException {
        long startTime = System.currentTimeMillis();
        SearchRequestBuilder select = getSearchRequestBuilder(query);
        Aggregations aggs = select.get().getAggregations();
        logger.info("查询统计耗时:" + (System.currentTimeMillis() - startTime) + "毫秒；SQL：" + query);
        return aggs;
    }

    /**
     * 获得查询对象
     * 
     * @param query
     * @return
     * @throws SqlParseException
     * @throws SQLFeatureNotSupportedException
     */
    public static SearchRequestBuilder getSearchRequestBuilder(String query) throws SQLFeatureNotSupportedException, SqlParseException {
    	return (SearchRequestBuilder) EsEnvFactory.getSearchDao().explain(query);
    }

    /**
     * 设置分组统计结果
     * 
     * @param terms
     * @param resultList
     * @param kv
     * @param groupId
     */
    private static List<Map<String, Object>> setGroupValue(Terms terms, String groupId, Map<String, String> groupIdMap) {
        List<Map<String, Object>> rList = new ArrayList<>();
        for (Terms.Bucket genderBucket : terms.getBuckets()) {
            String genderKey = genderBucket.getKey();
            
            List<Aggregation> gList = genderBucket.getAggregations().asList();
            
            if (gList.isEmpty()) {
                Map<String, Object> groupMap = new HashMap<>();
                
                Iterator<String> it = groupIdMap.keySet().iterator();
                while (it.hasNext()) {
                    String k = it.next();
                    groupMap.put(k, groupIdMap.get(k));
                }
                
                groupMap.put(groupId, genderKey);
                groupMap.put("_result_val_", genderBucket.getDocCount());
                
                rList.add(groupMap);
                continue;
            }
            
            
            for (Aggregation aggregation : gList) {
                Map<String, String> idMap = new HashMap<>();
                idMap.putAll(groupIdMap);
                idMap.put(groupId, genderKey);
                if (aggregation instanceof Terms) {
                    rList.addAll(setGroupValue(((Terms) aggregation), aggregation.getName(), idMap));
                } else if (aggregation instanceof ValueCount || aggregation instanceof Min || aggregation instanceof Max
                        || aggregation instanceof Sum || aggregation instanceof Avg || aggregation instanceof Stats) {
                    Map<String, Object> dataMap = new HashMap<String, Object>();
                    dataMap.putAll(idMap);
    
                    if (aggregation instanceof ValueCount) {
                        dataMap.put("_result_val_", ((ValueCount) aggregation).getValue());
                    } else if (aggregation instanceof Min) {
                        dataMap.put("_result_val_", ((Min) aggregation).getValue());
                    } else if (aggregation instanceof Max) {
                        dataMap.put("_result_val_", ((Max) aggregation).getValue());
                    } else if (aggregation instanceof Avg) {
                        dataMap.put("_result_val_", ((Avg) aggregation).getValue());
                    } else if (aggregation instanceof Sum) {
                        dataMap.put("_result_val_", ((Sum) aggregation).getValue());
                    } else if (aggregation instanceof Stats) {
                        Stats stats = (Stats) aggregation;
                        dataMap.put("_result_count_", stats.getCount());
                        dataMap.put("_result_avg_", stats.getAvg());
                        dataMap.put("_result_max_", stats.getMax());
                        dataMap.put("_result_min_", stats.getMin());
                        dataMap.put("_result_sum_", stats.getSum());
                    }
                    rList.add(dataMap);
                }
            }
        }
        
        return rList;
    }
    
    /**
     * 批量添加数据
     * 
     * @param indexName
     * @param typeId
     * @param rows
     * @param esclient
     * 
     * @throws Exception
     */
    public static int bulkAddIndex(String  indexName, String typeId, List<Map<String, Object>> rows)
            throws Exception {
        if (rows == null || rows.isEmpty()) {
            return 0;
        }
        logger.info("【"+ indexName +"/" + typeId + "】批量构建索引");
        
        // 先做原始数据一份
        BulkRequestBuilder bulkRequest = EsEnvFactory.getClient().prepareBulk(); // 创建批量添加数据对象
        boolean hasRequest = false;
        for (Map<String, Object> row : rows) {
        	if (row == null || row.isEmpty()) {
        		continue;
        	}
        	
            XContentBuilder xcb = jsonBuilder();
            xcb.startObject();

            Iterator<String> it = row.keySet().iterator();
            
            while (it.hasNext()) {
            	String k = it.next();
            	xcb.field(k, row.get(k));
            }
            
            xcb.endObject();
            if (null != row.get("_id")) {
                bulkRequest.add(Requests.indexRequest(indexName).type(typeId)
                        .id(row.get("_id").toString()).source(xcb));
            } else {
                bulkRequest.add(Requests.indexRequest(indexName).type(typeId).source(xcb));
            }
            
            hasRequest = true;
        }

        // 是否有正常的数据请求
        BulkResponse br = null;
        if (hasRequest) {
            br = bulkRequest.execute().actionGet();
        } else {
        	logger.error("批量添加资源【" + typeId + "】索引失败，有" + rows.size() + "行数据没有添加成功，请检查配置是否有误！！");
            return 0;
        }

        if (br.hasFailures()) {
            // 如果有失败的话
        	logger.error("【" + typeId + "】批量构建索引存在失败，失败数：" + br.contextSize());
        	String errorMsgs = br.buildFailureMessage();
        	
            // 包含分片错误
            if (!StringUtils.isEmpty(errorMsgs) && errorMsgs.contains("ShardsException")) {
                throw new Exception("存在分片错误，请检查elasticsearch服务状态是否正常");
            } else {
            	throw new Exception(errorMsgs);
            }
        }
        
        
        // 刷新
        EsEnvFactory.getClient().admin().indices().prepareRefresh(indexName).execute().actionGet();
        
        return br.contextSize();
    }

}
