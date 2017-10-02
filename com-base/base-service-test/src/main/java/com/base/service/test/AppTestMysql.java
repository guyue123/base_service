package com.base.service.test;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.base.service.mysql.api.MysqlOperateService;

/**
 * Hello world!
 *
 */
public class AppTestMysql {
	private Logger logger = LoggerFactory.getLogger(AppTestMysql.class); 
    
	@Autowired
	private MysqlOperateService mysqlOperateService;
	
    public static void main(String[] args) {
    	com.alibaba.dubbo.container.Main.main(args);
   // 	 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/application.xml","META-INF/spring/dubbo-demo-action.xml"});  
     //    context.start();
    }

	public void test() {
		try {
			String sql = "select * from sys_user limit 10";
			System.out.println(mysqlOperateService.queryForList(sql));
		} catch (Exception e) {
			logger.error(e);
		}
	}
	

}
