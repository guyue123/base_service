/**
 *  Copyright 2014 govnet, Inc.
 *
 */
package com.base.scheduler.config;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.base.common.file.ConfigUtil;

/**
 * 配置文件属性加载
 *
 * @author <a href="mailto:huhuaxiang@govnet.com.cn">hhx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2014年8月28日
 */
public final class ConfigParams {
    /**
     * 日志对象
     */
    private static final Logger logger = Logger.getLogger(ConfigParams.class);

    public static String charset; // 字符集
    public static String quartzConfigFilePath; // 缓存服务配置文件目录
    public static String runGroup; // 运行组ID
    public static String notrunGroup; // 非运行组ID
    public static String propsSplit; // 属性分割符号
    public static String defaultSchedulerCron;// 默认定时任务执行周期

    /**
     * 私有构造函数
     */
    private ConfigParams() {
    }

    /**
     * 初始化运行参数
     * 
     * @return
     */
    public static boolean initParam() {
        try {
            Properties config = ConfigUtil.getConfigProperties("conf", "config.properties");

            charset = config.getProperty("Charset", "UTF8");
            quartzConfigFilePath = config.getProperty("quartz.configfile.path", "");
            runGroup = config.getProperty("scheduler.run.group", "");
            notrunGroup = config.getProperty("scheduler.notrun.group", "");
            propsSplit = config.getProperty("scheduler.run.props.split", "\\n");
            defaultSchedulerCron = config.getProperty("default.scheduler.cron", "1800");

            logger.info(" charset:=" + charset);
            logger.info(" quartzConfigFilePath:=" + quartzConfigFilePath);

            logger.info(" 运行组ID:=" + runGroup);
            logger.info(" 非运行组ID:=" + notrunGroup);
            logger.info(" 属性分割符号:=" + propsSplit);
            logger.info(" 默认定时任务执行周期:=" + defaultSchedulerCron);
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
        return true;
    }
}
