package com.base.service.cache.conf.api.service;

import java.util.List;
import java.util.Map;

public interface CacheConfService {

	public Map<String, Object> notify(String cacheId, String cacheGroup);
	
	public List<Map<String, Object>> getByKey(String key);
}
