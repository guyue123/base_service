/**
 * 
 */
package com.base.common.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
 
public class CORSRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		requestContext.getHeaders().add("Access-Control-Allow-Origin", requestContext.getHeaderString("Origin"));
		requestContext.getHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		requestContext.getHeaders().add("Access-Control-Max-Age", "0");
		requestContext.getHeaders().add("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
		requestContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		requestContext.getHeaders().add("XDomainRequestAllowed","1");
	}  
  

}