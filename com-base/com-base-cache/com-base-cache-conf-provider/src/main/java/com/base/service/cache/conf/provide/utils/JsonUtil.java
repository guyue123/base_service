package com.base.service.cache.conf.provide.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {

	public static String objectToString(Object obj) {
		String s = "";
		try {
			ObjectMapper mp = new ObjectMapper();
			s = mp.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> stringToMap(String s) {
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			ObjectMapper mp = new ObjectMapper();
			m = mp.readValue(s, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> stringToList(String s) {
		List<Map<String, Object>> arr = new ArrayList<Map<String, Object>>();
		try {
			ObjectMapper mp = new ObjectMapper();
			arr = mp.readValue(s, List.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}

}
