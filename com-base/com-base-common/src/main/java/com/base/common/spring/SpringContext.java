package com.base.common.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringContext {

    /**
     * Spring运行时上下文
     */
    private static ApplicationContext ctx;

    /**
     * 初始化数据库
     * 
     * @return
     */
    public static boolean initContext() {
        ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        /*
         * SqlSessionTemplate sessionTemplate =
         * ctx.getBean(SqlSessionTemplate.class);
         * sessionTemplate.getConfiguration().addInterceptor(new Transfer());
         * commonDaoMapper = ctx.getBean(CommonDaoMapper.class);
         */

        return true;
    }

    /**
     * 初始化数据库
     * 
     * @return
     */
    public static boolean initContext(String contenxtPath) {
        ctx = new ClassPathXmlApplicationContext(contenxtPath);

        return true;
    }

    /**
     * 初始化数据库
     * 
     * @return
     */
    public static boolean initContextByFile(String contenxtPath) {
        ctx = new FileSystemXmlApplicationContext(contenxtPath);

        return true;
    }

    /**
     * 初始化对象
     * 
     * @param clazz
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return ctx.getBean(clazz);
    }

    /**
     * 初始化对象
     * 
     * @param clazz
     * @return
     */
    public static Object getBean(String name) {
        return ctx.getBean(name);
    }

    /**
     * 初始化对象
     * 
     * @param <T>
     * 
     * @param clazz
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return ctx.getBean(name, clazz);
    }
}
