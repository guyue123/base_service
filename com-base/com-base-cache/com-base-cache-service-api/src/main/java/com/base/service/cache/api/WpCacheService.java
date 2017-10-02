package com.base.service.cache.api;

import java.util.Map;

public interface WpCacheService {

	/**
	 * 添加缓存
	 */
	public boolean add(String key, String value);

	/**
	 * 删除缓存
	 */
	public boolean deleteByKey(String key);

	/**
	 * 查询缓存
	 */
	public Map<String, String> getByKey(String key);

	/**
	 * web添加缓存
	 */
	public boolean webAdd(String key, String value);

	/**
	 * web删除缓存
	 */
	public boolean webDeleteByKey(String key);

	/**
	 * web查询缓存
	 */
	public Map<String, String> webGetByKey(String key);

}
