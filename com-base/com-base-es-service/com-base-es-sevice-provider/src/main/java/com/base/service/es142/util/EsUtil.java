/** 
 *@Project: 
 *@Date: 2018年8月6日 
 *@Copyright: 2015 www.base.com Inc. All rights reserved. 
 */
package com.base.service.es142.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

/**
 * elasticsearch访问工具类
 *
 * @author <a href="mailto:base@base.com">base</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2018年8月6日
 */

public class EsUtil {

    /**
     * 将ES结果集转换为List<Map>对象
     * 
     * @param searchHits
     * @return
     */
    public static List<Map<String, Object>> convert2ListMap(SearchHits searchHits) {
        if (searchHits == null) {
            return null;
        }
        SearchHit[] hits = searchHits.getHits();

        if (hits == null || hits.length < 1) {
            return null;
        }

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            Map<String, Object> map = hit.getSource();
            map.put("_id", hit.id());
            resultList.add(map);
        }

        return resultList;
    }

    /**
     * 将ES结果按照type@index集转换为Map<List<Map>>对象
     * 
     * @param searchHits
     *            查询结果集
     * @return
     */
    public static Map<String, List<Map<String, Object>>> convert2MapList(SearchHits searchHits) {
        if (searchHits == null) {
            return null;
        }
        SearchHit[] hits = searchHits.getHits();

        if (hits == null || hits.length < 1) {
            return null;
        }

        Map<String, List<Map<String, Object>>> resultMap = new HashMap<String, List<Map<String, Object>>>();
        for (SearchHit hit : hits) {
            String typeIndex = hit.getType() + "@" + hit.getIndex();
            if (resultMap.containsKey(typeIndex)) {
                Map<String, Object> dataMap = hit.getSource();
                dataMap.put("_id", hit.getId());
                resultMap.get(typeIndex).add(dataMap);
            } else {
                List<Map<String, Object>> tmpList = new ArrayList<Map<String, Object>>();
                Map<String, Object> dataMap = hit.getSource();
                dataMap.put("_id", hit.getId());
                tmpList.add(dataMap);
                resultMap.put(typeIndex, tmpList);
            }
        }

        return resultMap;
    }
}
