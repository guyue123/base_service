/**
 *  Copyright 2014 govnet, Inc.
 *
 */
package com.base.scheduler.env;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.base.common.file.ConfigUtil;
import com.base.common.sql.SqlSessionFactoryManager;
import com.base.scheduler.config.ConfigParams;
import com.base.scheduler.job.JobManager;

/**
 * 定时任务服务启动程序
 * 
 * @author <a href="mailto:huhuaxiang@govnet.com.cn">hhx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2014年8月28日
 */
public class EnvIniter {
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(EnvIniter.class);

    /**
     * 配置文件目录
     */
    private String configPath = "conf";

    /**
     * 数据库配置文件
     */
    private String mapperProperties = "mapper.properties";

    /**
     * sql mapper
     */
    private String resource = "com/base/scheduler/orm/sqlmap/MapperConfig.xml";

    /**
     * @param args
     */
    public void init() throws Exception {
        // 初始化服务上下文
        if (!initContext()) {
            System.exit(0);
        }

        // 启动默认定时任务
        JobManager.execute();
        logger.info("服务启动成功");
    }

    /**
     * 初始化服务上下文
     */
    private boolean initContext() {
        // 初始化系统参数
        if (!ConfigParams.initParam()) {
            logger.error("初始化系统参数失败，请检查");
            return false;
        }
        logger.info("初始化系统参数成功");

        // 检查数据库连接
        if (!canConnectDb()) {
            logger.error("无法连接到数据库，请检查");
            return false;
        }
        logger.info("数据库连接测试成功");

        return true;
    }

    /**
     * 初始化数据库连接配置
     * 
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void initSqlSessionFactory() throws FileNotFoundException, IOException {
        SqlSessionFactoryManager.initDefaultSqlSessionFactory(resource,
                ConfigUtil.getConfigProperties(configPath, mapperProperties));
    }

    /**
     * 是否能够连接数据库
     * 
     * @return
     */
    public boolean canConnectDb() {
        try {
            initSqlSessionFactory();
        } catch (FileNotFoundException e) {
            logger.error("配置文件不存在", e);
            return false;
        } catch (IOException e) {
            logger.error("读取配置文件失败", e);
            return false;
        }

        /*
         * if (new CommonDao().query("select 1") == null) { return false; }
         */

        return true;
    }
    

    public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	public String getMapperProperties() {
		return mapperProperties;
	}

	public void setMapperProperties(String mapperProperties) {
		this.mapperProperties = mapperProperties;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
}
