package com.base.common.util;

import java.util.Properties;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.base.common.file.ConfigUtil;

/**
 * Created by Administrator on 2016/4/13.
 */
public class ContextConfiger {
	private static Logger logger = LoggerFactory.getLogger(ContextConfiger.class); 
	
	// 配置文件路径
	private String configFilePath;
	// 配置属性
	private Properties config;
	
	private static ContextConfiger contextConfiger;
	
	public void init() {
		try {
			config = ConfigUtil.getConfigProperties("", configFilePath);
		} catch (Exception e) {
			logger.error(e);
		}
		contextConfiger = this;
	}

    /**
     * 获取属性
     *
     * @param key
     * @param defaultVal
     * @return
     */
    public static String getConfig(String key, String defaultVal) {
        return contextConfiger.config.getProperty(key, defaultVal);
    }

    /**
     * 获取属性值，带默认值
     *
     * @param key
     * @return
     */
    public static String getConfig(String key) {
        return contextConfiger.config.getProperty(key);
    }

	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}
}
