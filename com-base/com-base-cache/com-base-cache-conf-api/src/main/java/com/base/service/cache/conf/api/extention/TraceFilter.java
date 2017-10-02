/**
 * Copyright 1999-2014 dangdang.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.base.service.cache.conf.api.extention;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

/**
 * @author lishen
 */
@Priority(Priorities.USER)
public class TraceFilter implements ContainerRequestFilter, ContainerResponseFilter {
	private Logger logger = LoggerFactory.getLogger(TraceFilter.class);

    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("Request filter invoked");
    }

    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        System.out.println("Response filter invoked");
        
        MultivaluedMap<String, Object> heads = containerResponseContext.getHeaders();
    	logger.info("设置允许跨域访问权限" + heads);
    	heads.add("Access-Control-Allow-Origin", "*");
    	heads.add("Access-Control-Allow-Headers","x-requested-with, ssi-token");  
    	heads.add("Access-Control-Max-Age", "3600");  
    	heads.add("Access-Control-Allow-Methods","GET,POST,PUT,DELETE,OPTIONS"); 
    	
    	
    }
}