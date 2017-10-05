/**
 * @Copyright base
 */
package com.base.common.framework.job;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.repeatSecondlyForever;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.TriggerKey;

import com.base.common.string.StringUtil;

/**
 * 执行Job的参数对象
 * 
 * @author hhx
 *
 */
public class JobBean {

    /**
     * 日志对象
     */
    private static final Logger logger = Logger.getLogger(JobBean.class);

    /**
     * 分组ID
     */
    private String groupId;

    /**
     * 触发器ID
     */
    private String triggerId;

    /**
     * Job Id
     */
    private String jobId;

    /**
     * 任务类
     */
    private Class<? extends Job> jobClass;

    /**
     * 任务类名:通过任务类名获得任务类
     */
    private String jobClassName;

    /**
     * cron 周期字符串
     */
    private String cron;

    /**
     * 执行周期，单位：秒
     */
    private int roundTime = 0;

    /**
     * jobKey对象
     */
    private JobKey jobKey;

    /**
     * jobKey对象
     */
    private TriggerKey triggerKey;

    /**
     * 任务参数
     */
    private JobDataMap jobDataMap;

    /**
     * 获得任务类
     * 
     * @return
     * @throws ClassNotFoundException
     */
    public Class<? extends Job> getJobClass() {
        if (null != jobClass) {
            return jobClass;
        }

        try {
            return (Class<Job>) Class.forName(jobClassName);
        } catch (ClassNotFoundException e) {
            logger.error("Job类不存在", e);
        } catch (Exception e) {
            logger.error("初始化失败", e);
        }

        return null;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     *            the groupId to set
     */
    public JobBean setGroupId(String groupId) {
        this.groupId = groupId;

        return this;
    }

    /**
     * @return the triggerId
     */
    public String getTriggerId() {
        return triggerId;
    }

    /**
     * @param triggerId
     *            the triggerId to set
     */
    public JobBean setTriggerId(String triggerId) {
        this.triggerId = triggerId;

        return this;
    }

    /**
     * @return the jobId
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * @param jobId
     *            the jobId to set
     */
    public JobBean setJobId(String jobId) {
        this.jobId = jobId;

        return this;
    }

    /**
     * @return the jobClassName
     */
    public String getJobClassName() {
        if (this.jobClass != null) {
            return this.jobClass.getName();
        }

        return jobClassName;
    }

    /**
     * @param jobClassName
     *            the jobClassName to set
     */
    public JobBean setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;

        // 添加运行参数
        this.addParam("JobClassName", jobClassName);

        return this;
    }

    /**
     * @return the cron
     */
    public String getCron() {
        return cron;
    }

    /**
     * 设置执行周期：整型或者cron表达式
     * 
     * @param cron
     *            the cron to set
     */
    public JobBean setCron(String cron) {
        // 转换为整型
        try {
            this.setRoundTime(Integer.parseInt(cron));
            return this;
        } catch (Exception e) {

        }
        this.cron = cron;

        return this;
    }

    /**
     * @return the roundTime 秒数
     */
    public int getRoundTime() {
        return roundTime;
    }

    /**
     * @param roundTime
     *            the roundTime to set
     */
    public JobBean setRoundTime(int roundTime) {
        this.roundTime = roundTime;

        return this;
    }

    /**
     * @param jobClass
     *            the jobClass to set
     */
    public JobBean setJobClass(Class<? extends Job> jobClass) {
        this.jobClass = jobClass;

        // 添加运行参数
        this.addParam("JobClassName", this.getJobClassName());

        return this;
    }

    /**
     * 获得Job key对象
     * 
     * @return
     */
    public JobKey getJobKey() {
        if (jobKey == null) {
            jobKey = new JobKey(this.jobId, this.groupId);
        }
        return jobKey;
    }

    /**
     * 获得Trigger key对象
     * 
     * @return
     */
    public TriggerKey getTriggerKey() {
        if (triggerKey == null) {
            triggerKey = new TriggerKey(this.triggerId, this.groupId);
        }
        return triggerKey;
    }

    /**
     * 通过cron和roundtime确定执行方式
     * 
     * @return
     */
    public ScheduleBuilder<?> getJobSehedule() {
        if (null != cron && !"".equals(cron.trim())) {
            return cronSchedule(cron);
        }

        if (roundTime > 0) {
            return repeatSecondlyForever(roundTime);
        }

        return null;
    }

    /**
     * jobKey是否相同
     * 
     * @param jobKey
     * @return
     */
    public boolean equalsJobKey(JobKey jobKey) {
        if (jobKey == null) {
            return false;
        }

        if (jobKey.getName() == null || jobKey.getGroup() == null) {
            return false;
        }

        if (jobKey.getName().equals(jobId) && jobKey.getGroup().equals(groupId)) {
            return true;
        }

        return false;
    }

    /**
     * 比较两个JobBean是否有相同的jobKey
     * 
     * @param bean
     * @return
     */
    public boolean equalsJobKey(JobBean bean) {
        if (bean == null) {
            return false;
        }

        return this.equalsJobKey(bean.getJobKey());
    }

    /**
     * 判断当前job是否是有效的Job
     * 
     * @return
     */
    public boolean isValidJobBean() {
        if (StringUtil.isEmptyOrNull(cron) && roundTime <= 0) {
            logger.info("Job Bean配置有误：执行周期没有配置");
            return false;
        }

        if (this.jobClass == null && StringUtil.isEmptyOrNull(this.jobClassName)) {
            logger.info("Job Bean配置有误：Job类没有配置");
            return false;
        }

        if (StringUtil.isEmptyOrNull(groupId) || StringUtil.isEmptyOrNull(jobId) || StringUtil.isEmptyOrNull(triggerId)) {
            logger.info("Job Bean配置有误：JobId，groupId， triggerId配置不正确");
            return false;
        }

        return true;
    }

    /**
     * 添加参数
     * 
     * @param key
     * @param obj
     */
    public JobBean addParam(String key, Object obj) {
        if (jobDataMap == null) {
            jobDataMap = new JobDataMap();
        }

        jobDataMap.put(key, obj);

        return this;
    }

    /**
     * 通过key获取一个参数值
     * 
     * @param key
     * @return 参数值
     */
    public Object getParam(String key) {
        if (jobDataMap == null) {
            return null;
        }

        return jobDataMap.get(key);
    }

    /**
     * @return the jobDataMap
     */
    public JobDataMap getJobDataMap() {
        return jobDataMap;
    }
}
