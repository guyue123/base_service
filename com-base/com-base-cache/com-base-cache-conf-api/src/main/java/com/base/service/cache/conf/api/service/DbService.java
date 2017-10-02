package com.base.service.cache.conf.api.service;

import java.util.List;
import java.util.Map;

public interface DbService {

	public List<Map<String, Object>> execute(String sql);

	public void init(String driverName, String url, String username, String password);

	public void close();
}
