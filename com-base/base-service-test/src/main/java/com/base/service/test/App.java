package com.base.service.test;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;;

/**
 * Hello world!
 *
 */
public class App {
	private Logger logger = LoggerFactory.getLogger(App.class); 
	
	//private MysqlOperateService mysqlOperateService;
	//private OrientDbService orientDbService;
	//private ObjectRelationService objectRelationService;
/*	@Autowired
	private QueryService142 queryService;
	//private ImpalaOperateService impalaOperateService;

    public void start() throws IOException{
		String sql = "select count(1) cnt from LBS_APP_WEIXIN";
//    	String sql = "select * from ce9/tb_object_event_master where start_time>=20161130142806 and start_time<=20161208142806 and GEO_DISTANCE(lat_lng, '1000m', 108.63515, 21.985326) limit 10000";
    	 System.out.println("----end------");
    	 try {
			 SelectResultBean a = queryService.select(sql);
    		 System.out.println(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 logger.info(System.getProperty("dubbo.application.logger"));
 
    }
    
    
    public static void main(String[] args) {
    	com.alibaba.dubbo.container.Main.main(args);
   // 	 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/application.xml","META-INF/spring/dubbo-demo-action.xml"});  
     //    context.start();
    }


	public QueryService142 getQueryService() {
		return queryService;
	}


	public void setQueryService(QueryService142 queryService) {
		this.queryService = queryService;
	}*/


}
