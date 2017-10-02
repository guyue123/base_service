package com.base.service.cache.conf.api.service;

import java.util.Map;

import javax.ws.rs.core.UriInfo;

public interface CacheConfRestService {

	public Map<String, Object> notify(UriInfo info);
}
