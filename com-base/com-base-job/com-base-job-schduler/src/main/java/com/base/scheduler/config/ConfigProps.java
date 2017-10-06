/** 
* @Project: 
* @Date: 2017年4月20日 
* @Copyright: 2017 www.govnet.com.cn Inc. All rights reserved. 
*/
package com.base.scheduler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 运行时参数
 *
 * @author <a href="mailto:huhuaxiang@govnet.com.cn">hhx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2017年4月20日
 */
@Component("configProps")
public class ConfigProps {
    /**
     * 字符集
     */
    @Value("${Charset}")
    private String charset;
    public String getCharset() {
		return charset;
	}

	/**
     * 
     */
    @Value("${quartz.configfile.path}")
    private String quartzConfigfilePath;
    
    /**
     * 运行组
     */
    @Value("${scheduler.run.group}")
    private String schedulerRunGroup;
    
    /**
     * 非运行组
     */
    @Value("${scheduler.notrun.group}")
    private String schedulerNotRunGroup;
    
    /**
     * 分隔符
     */
    @Value("${scheduler.run.props.split}")
    private String schedulerRunPropsSplit;
    
    /**
     * 默认运行周期
     */
    @Value("${default.scheduler.cron}")
    private String defaultSchedulerCron;
 
    

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getQuartzConfigfilePath() {
		return quartzConfigfilePath;
	}

	public void setQuartzConfigfilePath(String quartzConfigfilePath) {
		this.quartzConfigfilePath = quartzConfigfilePath;
	}

	public String getSchedulerRunGroup() {
		return schedulerRunGroup;
	}

	public void setSchedulerRunGroup(String schedulerRunGroup) {
		this.schedulerRunGroup = schedulerRunGroup;
	}

	public String getSchedulerNotRunGroup() {
		return schedulerNotRunGroup;
	}

	public void setSchedulerNotRunGroup(String schedulerNotRunGroup) {
		this.schedulerNotRunGroup = schedulerNotRunGroup;
	}

	public String getSchedulerRunPropsSplit() {
		return schedulerRunPropsSplit;
	}

	public void setSchedulerRunPropsSplit(String schedulerRunPropsSplit) {
		this.schedulerRunPropsSplit = schedulerRunPropsSplit;
	}

	public String getDefaultSchedulerCron() {
		return defaultSchedulerCron;
	}

	public void setDefaultSchedulerCron(String defaultSchedulerCron) {
		this.defaultSchedulerCron = defaultSchedulerCron;
	}

}
