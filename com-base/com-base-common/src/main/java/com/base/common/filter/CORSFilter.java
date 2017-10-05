/**
 * 
 */
package com.base.common.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
 
public class CORSFilter implements ContainerResponseFilter {  
  
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {  
      //  if (containerRequestContext.getMethod().equals(HttpMethod.GET) || containerRequestContext.getMethod().equals(HttpMethod.POST)) {  
            containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");  
            containerResponseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type,x-requested-with,Authorization,Access-Control-Allow-Origin");  
            containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");  
            containerResponseContext.getHeaders().add("Access-Control-Max-Age" ,"360"); 
     //   }
    }
}