package com.base.service.cache.http.provide.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.base.service.cache.api.WpCacheService;
import com.base.service.cache.http.api.service.WpCacheRestService;
import com.base.service.cache.http.provide.utils.Watch;

@Path("cache")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.TEXT_PLAIN,
		MediaType.APPLICATION_FORM_URLENCODED })
@Produces({ ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8, ContentType.TEXT_PLAIN_UTF_8,
		"application/x-www-form-urlencoded; charset=UTF-8" })
public class WpCacheRestServiceImpl implements WpCacheRestService {

	private static final Logger log = LoggerFactory.getLogger(WpCacheRestServiceImpl.class);

	@Autowired
	private WpCacheService wpCacheService;

	@POST
	@Path("save")
	public Map<String, String> save(@FormParam("key") String key, @FormParam("value") String value) {
		Watch w = new Watch();
		log.info("#save# 参数key=" + key + ",value=" + value);
		String s = "1";
		try {
			if (!wpCacheService.webAdd(key, value)) {
				s = "0";
			}
		} catch (Exception e) {
			log.error(e);
		}
		log.info("#save#" + w.stop());
		Map<String, String> rs = new HashMap<String, String>();
		rs.put("result", s);
		return rs;
	}

	@GET
	@Path("query")
	public Map<String, String> query(@Context UriInfo info) {
		String key = info.getQueryParameters().getFirst("key");
		log.info("#query# 参数key=" + key);
		return wpCacheService.webGetByKey(key);
	}

}
