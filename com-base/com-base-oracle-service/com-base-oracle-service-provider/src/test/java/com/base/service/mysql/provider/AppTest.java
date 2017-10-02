package com.base.service.mysql.provider;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.base.service.oracle.api.OracleOperateService;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    public static void main( String[] args ) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/service-provider.xml");  
        context.start();
//        System.in.read();
        //select * from gv_cis_wordtype t
        //  com.alibaba.dubbo.container.Main.main(args);
        OracleOperateService oracleOperateService = (OracleOperateService)context.getBean("oracleOperateService");
        List<Map<String,Object>> list = oracleOperateService.queryForList("select * from gv_cis_wordtype t", null);
        for(Map<String,Object> map:list){
            System.out.println(map.get("ID"));
        }
    }
}
