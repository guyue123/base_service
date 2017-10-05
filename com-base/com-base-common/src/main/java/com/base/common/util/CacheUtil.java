/** 
* @Project: 合成研判
* @Date: 2017年5月14日 
* @Copyright: 2017 www.base.com.cn Inc. All rights reserved. 
*/
package com.base.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.base.service.cache.conf.api.service.CacheConfService;

/**
 * 远程缓存服务配置
 *
 * @author <a href="mailto:huhuaxiang@base.com.cn">hhx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2017年5月14日
 */

public class CacheUtil {
    /**
     * 远程缓存服务
     */
    @Autowired
    private CacheConfService cacheConfService;
    
    private static CacheUtil cacheUtil;
    
    @PostConstruct
    public void init() {  
        cacheUtil = this;  
        cacheUtil.cacheConfService = this.cacheConfService;  
    }  
    
    /**
     * 获得缓存数据
     * @param cacheKey
     * @param versionCode
     * @return
     */
    public static Map<String, String> getCache(String cacheKey, String kFieldName, String vFieldName) {
        List<Map<String, Object>> dataList = getCache(cacheKey);
        
        if (dataList == null || dataList.isEmpty()) {
            return null;
        }
        
        Map<String, String> dataMap = new HashMap();
        for (Map<String, Object> data : dataList) {
            if (data.get(kFieldName) != null && data.get(vFieldName) != null) {
                dataMap.put(String.valueOf(data.get(kFieldName)), String.valueOf(data.get(vFieldName)));
            }
        }
        
        return dataMap;
    }
    
    /**
     * 获得缓存数据
     * @param cacheKey
     * @param versionCode
     * @return
     */
    public static Map<String, String> getCache(String cacheKey, String versionCode, String kFieldName, String vFieldName) {
        return getCache(cacheKey + "_" + versionCode, kFieldName, vFieldName);
    }
    
    /**
     * 获得系统字段缓存数据
     * @param cacheKey
     * @param versionCode
     * @return
     */
    public static Map<String, String> getDicCache(String cacheKey, String versionCode) {
        return getCache(cacheKey + "_" + versionCode, "DM", "NAME");
    }
    
    /**
     * 获得系统字段缓存数据
     * @param cacheKey
     * @param versionCode
     * @return
     */
    public static Map<String, String> getDicCache(String cacheKey) {
        return getCache(cacheKey, "DM", "NAME");
    }
    
    /**
     * 获得缓存值
     * @param cacheKey
     * @return
     */
    public static List<Map<String, Object>> getCache(String cacheKey) {
        return cacheUtil.cacheConfService.getByKey(cacheKey);
    }
    
    /**
     * 组合缓存键
     * @param cacheGroup
     * @param key
     * @return
     */
    public static String getCacheKey(String cacheGroup, String key) {
        return cacheGroup + "." + key;
    }
    
    /**
     * 组合缓存键
     * @param cacheGroup
     * @param key
     * @param versionCode
     * @return
     */
    public static String getCacheKey(String cacheGroup, String key, String versionCode) {
        return cacheGroup + "." + key + "_" + versionCode;
    }
    
    /**
     * 更新缓存
     * @param key
     * @param cacheGroup
     */
    public static void refresh(String key, String cacheGroup) {
        cacheUtil.cacheConfService.notify(key, cacheGroup);
    }

    public CacheConfService getCacheConfService() {
        return cacheConfService;
    }

    public void setCacheConfService(CacheConfService cacheConfService) {
        this.cacheConfService = cacheConfService;
    }
}
