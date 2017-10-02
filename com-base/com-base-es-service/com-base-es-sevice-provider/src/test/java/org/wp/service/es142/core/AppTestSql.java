package org.wp.service.es142.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.service.es142.impl.QueryServiceImpl;
import com.base.service.es142.util.EsEnvFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTestSql 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTestSql( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTestSql.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        EsEnvFactory f = new EsEnvFactory();
		f.setAddress("192.168.1.102:9300");
		f.setClusterName("govdatasearchlocal");
		try {
			f.init();
			QueryServiceImpl impl = new QueryServiceImpl();
			
			String sql = "select * from gt*/tb_gtag_keyword where esid='14231014143940152' limit 10";
			
			System.out.println(impl.select(sql));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    /**
     * Rigourous Test :-)
     */
    public void testAppInsert()
    {
        EsEnvFactory f = new EsEnvFactory();
		f.setAddress("192.168.1.102:9300");
		f.setClusterName("govdatasearchlocal");
		try {
			f.init();
			QueryServiceImpl impl = new QueryServiceImpl();
			
			List<Map<String, Object>> dataList = new ArrayList<>();
			
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("nature", "test");
			dataMap.put("esid", 1213323l);
			dataMap.put("caseid", "test");
			dataMap.put("wordid", "test");
			dataMap.put("word", "test2");
			dataMap.put("_id", "test001");
			
			dataList.add(dataMap);
			
			impl.insert("gtag", "tb_gtag_keyword", dataList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    /**
     * Rigourous Test :-)
     */
    public void testAggSql()
    {
        EsEnvFactory f = new EsEnvFactory();
        f.setAddress("192.168.1.102:9300");
        try {
            f.init();
            QueryServiceImpl impl = new QueryServiceImpl();
            
            String sql = "select a_obj_type_id as type_id, a_obj_id as id, count(*) as count from ce9/tb_object_event_master group by (a_obj_type_id),(a_obj_id) limit 13";

            
            System.out.print(impl.selectGroupBy(sql));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    /**
     * Rigourous Test :-)
     */
    public void testSum()
    {
        EsEnvFactory f = new EsEnvFactory();
        f.setAddress("192.168.3.205:9300");
        try {
            f.init();
            QueryServiceImpl impl = new QueryServiceImpl();
            
            String sql = "select * from ce9/tb_object_event_master group by (a_obj_type_id,terms('field'='a_obj_id','size'=30,'alias'='a_obj_id')) limit 5";

            
            System.out.print(impl.selectGroupBy(sql));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
