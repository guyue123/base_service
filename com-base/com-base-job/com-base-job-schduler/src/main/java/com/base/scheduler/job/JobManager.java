/**
 * 
 * Copyright (c) 2008-2009 PISOFT, All rights reserved.
 * 本源代码文件仅限于在能讯科技有限责任公司内部交流与使用，任何组织和个*人在未经允许下不得复制、传播、更改。能讯科技有限责任公司对源代码文件
 *保留一切权力。
 */
package com.base.scheduler.job;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

import com.base.common.file.ConfigUtil;
import com.base.common.framework.job.JobBean;
import com.base.common.framework.job.JobScheduler;
import com.base.scheduler.config.Constant;
import com.base.scheduler.config.ConfigParams;

/**
 * 负责文件的管理
 * 
 * @author hhx
 */
public class JobManager {
    /**
     * 日志对象
     */
    private static Logger logger = Logger.getLogger(JobManager.class);

    private JobManager() {
    }

    /**
     * 执行Job
     */
    public static boolean execute() {
        // 调度Job
        JobScheduler jobScheduler;
        try {
            jobScheduler = JobScheduler.getInstance(ConfigUtil.getConfigFile("conf", "quartz.properties"));
        } catch (SchedulerException e) {
            logger.error("任务初始化失败", e);
            return false;
        }

        // 更新定时任务的任务
        JobBean jobBean = new JobBean();
        jobBean.setCron(ConfigParams.defaultSchedulerCron).setGroupId(Constant.DEFAULT_SCHEDULER_GROUP)
                .setJobClass(UpdateSchedulerJob.class).setJobId("_DEFAULT_SCHEDULER_JOB_")
                .setTriggerId("_DEFAULT_SCHEDULER_TRIGGER_");
        jobScheduler.addJobBean(jobBean);

        try {
            jobScheduler.execute();
        } catch (SchedulerException e) {
            logger.error("定时任务启动失败", e);
            return false;
        }

        return true;
    }

}
