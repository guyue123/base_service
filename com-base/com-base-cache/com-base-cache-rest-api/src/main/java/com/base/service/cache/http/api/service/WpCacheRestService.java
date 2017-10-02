package com.base.service.cache.http.api.service;

import java.util.Map;

import javax.ws.rs.core.UriInfo;

public interface WpCacheRestService {

	/**
	 * web添加缓存
	 */
	public Map<String, String> save(String key, String value);

	/**
	 * web查询缓存
	 */
	public Map<String, String> query(UriInfo info);
}
