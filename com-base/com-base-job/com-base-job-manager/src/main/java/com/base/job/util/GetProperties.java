package com.base.job.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class GetProperties {

    /**
     * 日志
     */
    private final static Logger log = Logger.getLogger(GetProperties.class);

    public static final String PATH = "conf/job.properties";

    static Properties prop = null;
    static FileInputStream fis = null;

    static {
        try {
            prop = new Properties();
            fis = new FileInputStream(new File(PATH));
            prop.load(fis);
        } catch (Exception e) {
            log.error(e);
        }

    }

    /**
     * 获得配置值
     * 
     * @return
     * @throws Exception
     */
    public static String getProp(String key){
        String str = prop.getProperty(key);
        try {
            fis.close();
        } catch (IOException e) {
            log.error(e);
        }
        return str;
    }

    /**
     * 获得配置值
     * 
     * @return
     * @throws Exception
     */
    public static int getIntProp(String key) throws Exception {
        int str = Integer.parseInt(prop.getProperty(key));
        fis.close();
        return str;
    }

    /**
     * 获得布尔型变量，值为1时输出true，否则false
     * 
     * @return
     * @throws Exception
     */
    public static boolean getBooleanProp(String key) throws Exception {
        boolean v = "1".equals(prop.getProperty(key));
        fis.close();
        return v;
    }
}
