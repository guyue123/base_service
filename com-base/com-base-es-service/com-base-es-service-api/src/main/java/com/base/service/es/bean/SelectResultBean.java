package com.base.service.es.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectResultBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 查询数据结果
	 */
	private Map<String, List<Map<String, Object>>> results;
	
	/**
	 * 结果总数
	 */
	private long total;


	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	
	/**
	 * 添加数据
	 */
	public void addData(String key, List<Map<String, Object>> data) {
		if (results == null) {
			results = new HashMap<>();
		}
		
		results.put(key, data);
	}

	public Map<String, List<Map<String, Object>>> getResults() {
		return results;
	}

	public void setResults(Map<String, List<Map<String, Object>>> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "SelectResultBean [results=" + results + ", total=" + total + "]";
	}
}
