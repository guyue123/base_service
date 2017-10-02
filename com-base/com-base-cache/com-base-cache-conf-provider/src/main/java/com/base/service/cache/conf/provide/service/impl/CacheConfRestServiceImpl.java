package com.base.service.cache.conf.provide.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.base.service.cache.conf.api.service.CacheConfRestService;
import com.base.service.cache.conf.api.service.CacheConfService;

@Path("cacheconf")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.TEXT_PLAIN,
		MediaType.APPLICATION_FORM_URLENCODED })
@Produces({ ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8, ContentType.TEXT_PLAIN_UTF_8,
		"application/x-www-form-urlencoded; charset=UTF-8" })
public class CacheConfRestServiceImpl implements CacheConfRestService {

	private static final Logger log = LoggerFactory.getLogger(CacheConfRestServiceImpl.class);

	@Autowired
	private CacheConfService cacheConfService;

	@GET
	@Path("notify")
	public Map<String, Object> notify(@Context UriInfo info) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("success", true);
		String cacheId = info.getQueryParameters().getFirst("cacheId");
		String cacheGroup = info.getQueryParameters().getFirst("cacheGroup");
		log.info("#notify# cacheId=" + cacheId + ",  cacheGroup=" + cacheGroup);
		cacheConfService.notify(cacheId, cacheGroup);
		return rs;
	}

}
