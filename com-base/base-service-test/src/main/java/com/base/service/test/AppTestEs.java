package com.base.service.test;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.base.service.es.facade.QueryService142;

/**
 * Hello world!
 *
 */
public class AppTestEs {
	private Logger logger = LoggerFactory.getLogger(AppTestEs.class); 
    
	@Autowired
	private QueryService142 queryService142;
	
    public static void main(String[] args) {
    	com.alibaba.dubbo.container.Main.main(args);
   // 	 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/application.xml","META-INF/spring/dubbo-demo-action.xml"});  
     //    context.start();
    }

	public void test() {
		try {
			System.out.println(queryService142.select("SELECT /*! HIGHLIGHT(PLACE_ID, pre_tags : ['<b>'], post_tags : ['</b>']) */ *  FROM wifimain/75 where _all ='1393'"));
		} catch (Exception e) {
			logger.error(e);
		}
	}
	

}
