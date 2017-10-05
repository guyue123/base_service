/**
 * @Copyright base
 */
package com.base.common.framework.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.base.common.time.TimeUtil;

/**
 * job抽象类
 * 
 * @author hhx
 *
 */
public abstract class AbstractJob implements Job {

    /**
     * 日志对象
     */
    private static final Logger logger = Logger.getLogger(JobBean.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 执行开始时间
        long startTime = System.currentTimeMillis();

        runJob(context);

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        logger.info("Job" + jobDataMap.getString("JobClassName") + "，执行时间：" + TimeUtil.consumeTime(startTime) + "毫秒");
    }

    /**
     * 运行工作
     * 
     * @param context
     */
    public abstract void runJob(JobExecutionContext context) throws JobExecutionException;
}
