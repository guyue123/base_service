/**
 *  Copyright 2014 govnet, Inc.
 *
 */
package com.base.scheduler.orm.pojo;

import java.util.Date;

import com.base.common.string.StringUtil;

/**
 * 数据库表SCHEDULER_CONFIG对应的对象
 * 
 * @author <a href="mailto:huhuaxiang@govnet.com.cn">hhx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2014年8月28日
 */
public final class SchedulerConfig {

    /**
     * 任务ID
     */
    private String jobId;

    /**
     * 任务组
     */
    private String groupId;

    /**
     * 触发ID
     */
    private String triggerId;

    /**
     * 任务类
     */
    private String jobClass;

    /**
     * 描叙
     */
    private String desc;

    /**
     * 周期
     */
    private String cron;

    /**
     * 策略状态
     */
    private String status;

    /**
     * 插入时间
     */
    private Date insertTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 运行组
     */
    private String runGroup;

    /**
     * 运行参数
     */
    private String runProps;

    /**
     * 插入用户名
     */
    private String insertUser;

    /**
     * 修改用户名
     */
    private String updateUser;

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
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * @return the triggerGroup
     */
    public String getTriggerId() {
        return triggerId;
    }

    /**
     * @param triggerGroup
     *            the triggerGroup to set
     */
    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    /**
     * @return the jobClass
     */
    public String getJobClass() {
        return jobClass;
    }

    /**
     * @param jobClass
     *            the jobClass to set
     */
    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc
     *            the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the cron
     */
    public String getCron() {
        return cron;
    }

    /**
     * @param cron
     *            the cron to set
     */
    public void setCron(String cron) {
        this.cron = cron;
    }

    /**
     * @return the insertTime
     */
    public Date getInsertTime() {
        return insertTime;
    }

    /**
     * @param insertTime
     *            the insertTime to set
     */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     *            the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 任务周期是否是整型
     * 
     * @return 是否为整数
     */
    public boolean isCronInteger() {
        try {
            if (Integer.parseInt(cron) > 0) {
                return true;
            }
        } catch (NumberFormatException e) {

        }

        return false;
    }

    /**
     * 此配置是否有效
     * 
     * @return 是否有效
     */
    public boolean isValid() {
        if (StringUtil.isEmptyOrNull(cron) || StringUtil.isEmptyOrNull(this.jobClass)) {
            return false;
        }

        return true;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the runGroup
     */
    public String getRunGroup() {
        return runGroup;
    }

    /**
     * @param runGroup
     *            the runGroup to set
     */
    public void setRunGroup(String runGroup) {
        this.runGroup = runGroup;
    }

    /**
     * @return the runProps
     */
    public String getRunProps() {
        return runProps;
    }

    /**
     * @param runProps
     *            the runProps to set
     */
    public void setRunProps(String runProps) {
        this.runProps = runProps;
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
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the insertUser
     */
    public String getInsertUser() {
        return insertUser;
    }

    /**
     * @param insertUser
     *            the insertUser to set
     */
    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser;
    }

    /**
     * @return the updateUser
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     *            the updateUser to set
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SchedulerConfig [jobId=" + jobId + ", groupId=" + groupId + ", triggerId=" + triggerId + ", jobClass="
                + jobClass + ", desc=" + desc + ", cron=" + cron + ", status=" + status + ", insertTime=" + insertTime
                + ", updateTime=" + updateTime + ", runGroup=" + runGroup + ", runProps=" + runProps + ", insertUser="
                + insertUser + ", updateUser=" + updateUser + "]";
    }

}
