package com.base.common.mybatis.dao.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.base.common.mybatis.dao.mapper.CommonDaoMapper;
import com.base.common.mybatis.plugin.Transfer;

/**
 * 通过Spring框架配置,初始化共通Dao Mapper
 * 
 * @author base
 * 
 */
public class CommonDaoMapperFactory {
    /**
     * 默认键值
     */
    private static final String DEFAULT_KEY = "_default_";
    
    /**
     * mapper对象列表
     */
    private static Map<String, CommonDaoMapper> mappers = new HashMap<String, CommonDaoMapper>();

    /**
     * 通过Spring框架配置,读取classpath:applicationContext.xml;初始化共通Dao Mapper
     * 
     * @return
     */
    public static CommonDaoMapper getDefaultDaoMapper() {
        return mappers.get(CommonDaoMapperFactory.DEFAULT_KEY);
    }
    
    /**
     * 初始化默认Dao Mapper
     * 
     * @param classPath
     * @return
     */
    public static CommonDaoMapper initDefaultDaoMapper(String classPath) {
        return getDaoMapper(CommonDaoMapperFactory.DEFAULT_KEY, classPath);
    }
    
    /**
     * 初始化默认Dao Mapper
     * 
     * @param filePath
     * @return
     */
    public static CommonDaoMapper initDefaultDaoMapperByFilePath(String filePath) {
        return getDaoMapperByFilePath(CommonDaoMapperFactory.DEFAULT_KEY, filePath);
    }

    /**
     * 通过Spring框架配置,初始化共通Dao Mapper
     *
     * @param key 存储Key 
     * @param classPath
     *            类路径
     * @return
     */
    public static CommonDaoMapper getDaoMapper(String key, String classPath) {
        if (mappers.containsKey(key)) {
            return mappers.get(key);
        }

        ApplicationContext ctx = new ClassPathXmlApplicationContext(classPath);

        mappers.put(key, initDaoMapper(ctx));
        return mappers.get(key);
    }

    /**
     * 通过Spring框架配置,初始化共通Dao Mapper
     * 
     * @param key 存储Key
     * @param classPath
     *            文件路径
     * @return
     */
    public static CommonDaoMapper getDaoMapperByFilePath(String key, String filePath) {
        if (mappers.containsKey(key)) {
            return mappers.get(key);
        }
        ApplicationContext ctx = new FileSystemXmlApplicationContext(filePath);

        mappers.put(key, initDaoMapper(ctx));
        return mappers.get(key);
    }

    /**
     * 初始化Dao Mapper
     * 
     * @param ctx
     * @return
     */
    private static CommonDaoMapper initDaoMapper(ApplicationContext ctx) {
        SqlSessionTemplate sessionTemplate = ctx.getBean(SqlSessionTemplate.class);
        sessionTemplate.getConfiguration().addInterceptor(new Transfer());
        return ctx.getBean(CommonDaoMapper.class);
    }
}
