/** 
 *@Project: 公共包
 *@Date: 2015年11月10日 
 *@Copyright: 2015 www.govnet.com.cn Inc. All rights reserved. 
 */
package com.base.scheduler.quartz.impl;

import java.util.Date;

import org.quartz.Calendar;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;

/**
 * 只为单独执行任务类而定义的上下文类
 *
 * @author <a href="mailto:huhuaxiang@govnet.com.cn">hhx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2015年11月10日
 */

public class JobExecutionContext4DataImpl implements JobExecutionContext {

    private JobDetail jobDetail = new JobDetailImpl();

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#get(java.lang.Object)
     */
    @Override
    public Object get(Object arg0) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getCalendar()
     */
    @Override
    public Calendar getCalendar() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getFireInstanceId()
     */
    @Override
    public String getFireInstanceId() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getFireTime()
     */
    @Override
    public Date getFireTime() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getJobDetail()
     */
    @Override
    public JobDetail getJobDetail() {
        return jobDetail;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getJobInstance()
     */
    @Override
    public Job getJobInstance() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getJobRunTime()
     */
    @Override
    public long getJobRunTime() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getMergedJobDataMap()
     */
    @Override
    public JobDataMap getMergedJobDataMap() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getNextFireTime()
     */
    @Override
    public Date getNextFireTime() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getPreviousFireTime()
     */
    @Override
    public Date getPreviousFireTime() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getRecoveringTriggerKey()
     */
    @Override
    public TriggerKey getRecoveringTriggerKey() throws IllegalStateException {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getRefireCount()
     */
    @Override
    public int getRefireCount() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getResult()
     */
    @Override
    public Object getResult() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getScheduledFireTime()
     */
    @Override
    public Date getScheduledFireTime() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getScheduler()
     */
    @Override
    public Scheduler getScheduler() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#getTrigger()
     */
    @Override
    public Trigger getTrigger() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#isRecovering()
     */
    @Override
    public boolean isRecovering() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#put(java.lang.Object,
     * java.lang.Object)
     */
    @Override
    public void put(Object arg0, Object arg1) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.JobExecutionContext#setResult(java.lang.Object)
     */
    @Override
    public void setResult(Object arg0) {

    }

}
