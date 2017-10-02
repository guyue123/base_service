package com.base.service.cache.provide;

import java.util.HashMap;
import java.util.Map;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.base.service.cache.api.WpCacheService;
import com.base.service.cache.utils.Watch;

public class EhCacheServiceImpl implements WpCacheService {

	private static final Logger log = LoggerFactory.getLogger(EhCacheServiceImpl.class);

	private static CacheManager cacheManager = null;

	static {
		XmlConfiguration config = new XmlConfiguration(EhCacheServiceImpl.class.getResource("/wpehcache.xml"));
		cacheManager = CacheManagerBuilder.newCacheManager(config);
		cacheManager.init();
		log.info("初始化ehcache缓存完成");
	}

	@Override
	public boolean add(String key, String value) {
		Watch w = new Watch();
		boolean rs = true;
		Cache<String, String> cache = cacheManager.getCache("wpServiceCache", String.class, String.class);
		try {
			cache.put(key, value);
		} catch (Exception e) {
			log.error(e);
			rs = false;
		}
		log.info("#add# " + key + w.stop());
		return rs;
	}

	@Override
	public boolean deleteByKey(String key) {
		Watch w = new Watch();
		boolean rs = true;
		Cache<String, String> cache = cacheManager.getCache("wpServiceCache", String.class, String.class);
		try {
			cache.remove(key);
		} catch (Exception e) {
			log.error(e);
			rs = false;
		}
		log.info("#deleteByKey# " + key + w.stop());
		return rs;
	}

	@Override
	public Map<String, String> getByKey(String key) {
		Watch w = new Watch();
		Cache<String, String> cache = cacheManager.getCache("wpServiceCache", String.class, String.class);
		Map<String, String> rs = new HashMap<String, String>();
		try {
			String[] arr = key.split(",");
			for (String k : arr) {
				rs.put(k, cache.get(k));
			}
		} catch (Exception e) {
			log.error(e);
		}
		log.info("#getByKey# " + key + w.stop());
		return rs;
	}

	@Override
	public boolean webAdd(String key, String value) {
		Watch w = new Watch();
		boolean rs = true;
		Cache<String, String> cache = cacheManager.getCache("wpWebCache", String.class, String.class);
		try {
			cache.put(key, value);
		} catch (Exception e) {
			log.error(e);
			rs = false;
		}
		log.info("#webAdd# " + key + w.stop());
		return rs;
	}

	@Override
	public boolean webDeleteByKey(String key) {
		Watch w = new Watch();
		boolean rs = true;
		Cache<String, String> cache = cacheManager.getCache("wpWebCache", String.class, String.class);
		try {
			cache.remove(key);
		} catch (Exception e) {
			log.error(e);
			rs = false;
		}
		log.info("#webDeleteByKey# " + key + w.stop());
		return rs;
	}

	@Override
	public Map<String, String> webGetByKey(String key) {
		Watch w = new Watch();
		Cache<String, String> cache = cacheManager.getCache("wpWebCache", String.class, String.class);
		Map<String, String> rs = new HashMap<String, String>();
		try {
			String[] arr = key.split(",");
			for (String k : arr) {
				rs.put(k, cache.get(k));
			}
		} catch (Exception e) {
			log.error(e);
		}
		log.info("#webGetByKey# " + key + w.stop());
		return rs;
	}

}
