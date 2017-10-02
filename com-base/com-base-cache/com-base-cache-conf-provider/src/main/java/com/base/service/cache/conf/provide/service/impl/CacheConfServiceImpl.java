package com.base.service.cache.conf.provide.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Expirations;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.base.service.cache.conf.api.service.CacheConfService;
import com.base.service.cache.conf.api.service.DbService;
import com.base.service.cache.conf.provide.conf.ConfigInfo;
import com.base.service.cache.conf.provide.utils.JsonUtil;

public class CacheConfServiceImpl implements CacheConfService {

	private static final Logger log = LoggerFactory.getLogger(CacheConfServiceImpl.class);
	
	private static CacheManager cacheManager = null;

	static {
		cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
		cacheManager.init();
		log.info("初始化ehcache缓存完成");
	}
	
	private Cache<String, String> getInstance(String cacheName, long entrySize, long memorySize) {
		Cache<String, String> cache = cacheManager.getCache(cacheName, String.class, String.class);
		if (cache == null) {
			CacheConfiguration<String, String> cfg = CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().heap(entrySize, EntryUnit.ENTRIES).offheap(memorySize, MemoryUnit.MB)).withExpiry(Expirations.noExpiration()).build();
			cache = cacheManager.createCache(cacheName, cfg);
		}
		return cache;
	}
	
	@Autowired
	private ConfigInfo configInfo;
	
	@Override
	public Map<String, Object> notify(String cacheId, String cacheGroup) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("success", true);
		log.info("#notify# cacheId=" + cacheId + ",  cacheGroup=" + cacheGroup);
		String driverName = null;
		String url = null;
		String username = null;
		String password = null;
		String dbType = configInfo.getDbType();
		DbService db = null;
		if ("1".equals(dbType)) {
			driverName = configInfo.getMysqlDriver();
			url = configInfo.getMysqlUrl();
			username = configInfo.getMysqlUsername();
			password = configInfo.getMysqlPassword();
			db = new MysqlServiceImpl();
		} else {
			driverName = configInfo.getOracleDriver();
			url = configInfo.getOracleUrl();
			username = configInfo.getOracleUsername();
			password = configInfo.getOraclePassword();
			db = new OracleServiceImpl();
		}
		log.info("#notify#  , driver=" + driverName + ", url=" + url + ", username=" + username + ", password=" + password);
		db.init(driverName, url, username, password);
		List<Map<String, Object>> arr = db.execute("SELECT T1.*, T2.ENTRY_SIZE,T2.MEMORY_SIZE FROM CACHE_POLICY_CONF T1 join CACHE_GROUP_CONF T2 ON (T1.CACHE_GROUP = T2.GROUP_NAME) WHERE T1.CACHE_ID = '" + cacheId + "' and T1.CACHE_GROUP = '" + cacheGroup + "'");
		if (arr != null && !arr.isEmpty()) {
			run(arr.get(0));
		}
		db.close();
		return rs;
	}

	@Override
	public List<Map<String, Object>> getByKey(String key) {
		List<Map<String, Object>> rs = new ArrayList<Map<String, Object>>();
		String group = key.substring(0, key.indexOf("."));
		Cache<String,String> cache = getInstance(group, 1, 1);
		rs = JsonUtil.stringToList(cache.get(key));
		return rs;
	}
	
	private void run(Map<String, Object> info) {
		log.info("#run# info=" + info);
		DbService db = null;
		String type = (String) info.get("DB_TYPE");
		String cacheId = (String) info.get("CACHE_ID");
		String cacheGroup = (String) info.get("CACHE_GROUP");
		String entrySize = String.valueOf(info.get("ENTRY_SIZE"));
		String memorySize = String.valueOf(info.get("MEMORY_SIZE"));
//		String policyType = (String) info.get("policy_type");
		String policy = (String) info.get("POLICY");
		String a = (String) info.get("DB_INFO");
		Map<String, String> m = new HashMap<String, String>();
		for (String s : a.split("#")) {
			String[] temarr = s.split("=");
			m.put(temarr[0], temarr[1]);
		}
		String driverName = m.get("driver");
		String url = m.get("url");
		String username = m.get("username");
		String password = m.get("password");
		Cache<String,String> cache = getInstance(cacheGroup, Long.parseLong(entrySize), Long.parseLong(memorySize));
		switch (type) {
		case "1":
			db = new MysqlServiceImpl();
			db.init(driverName, url, username, password);
			try {
				cache.put(cacheGroup + "." + cacheId, JsonUtil.objectToString(db.execute(policy)));
			} catch (Exception e) {
			}
			db.close();
			break;
		case "2":
			db = new OracleServiceImpl();
			db.init(driverName, url, username, password);
			try {
				cache.put(cacheGroup + "." + cacheId, JsonUtil.objectToString(db.execute(policy)));
			} catch (Exception e) {
			}
			db.close();
			break;
		default:
			break;
		}
	}
	
	public void cacheAll() {
		String driverName = null;
		String url = null;
		String username = null;
		String password = null;
		String dbType = configInfo.getDbType();
		DbService db = null;
		if ("1".equals(dbType)) {
			driverName = configInfo.getMysqlDriver();
			url = configInfo.getMysqlUrl();
			username = configInfo.getMysqlUsername();
			password = configInfo.getMysqlPassword();
			db = new MysqlServiceImpl();
		} else {
			driverName = configInfo.getOracleDriver();
			url = configInfo.getOracleUrl();
			username = configInfo.getOracleUsername();
			password = configInfo.getOraclePassword();
			db = new OracleServiceImpl();
		}
		log.info("#cacheAll#  , driver=" + driverName + ", url=" + url + ", username=" + username + ", password=" + password);
		db.init(driverName, url, username, password);
		List<Map<String, Object>> arr = db.execute("SELECT T1.*, T2.ENTRY_SIZE,T2.MEMORY_SIZE FROM CACHE_POLICY_CONF T1 join CACHE_GROUP_CONF T2 ON (T1.CACHE_GROUP = T2.GROUP_NAME) WHERE T1.POLICY_STATUS = 1");
		if (arr != null) {
			for (Map<String, Object> info : arr) {
				run(info);
			}
		}
		db.close();
	}

}
