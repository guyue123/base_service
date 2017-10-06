/**
 *  Copyright 2014-2014 govnet, Inc.
 *
 */
package com.base.scheduler.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

import com.base.common.framework.job.AbstractJob;
import com.base.common.framework.job.JobBean;
import com.base.common.framework.job.JobScheduler;
import com.base.common.string.StringUtil;
import com.base.common.time.TimeUtil;
import com.base.scheduler.bean.JobConfigBean;
import com.base.scheduler.bean.ResultMsg;
import com.base.scheduler.config.ConfigParams;
import com.base.scheduler.orm.dao.SchedulerConfigDao;
import com.base.scheduler.orm.pojo.SchedulerConfig;
import com.base.scheduler.quartz.impl.JobExecutionContext4DataImpl;

/**
 * 启动定时任务
 * 
 * @author <a href="mailto:huhuaxiang@govnet.com.cn">hhx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2014年8月28日
 */
public class SchedulerManager {
    /**
     * 日志对象
     */
    private static Logger logger = Logger.getLogger(SchedulerManager.class);

    /**
     * quartz配置文件路径
     */
    private String quartzFileName;

    // 对象列表
    private static Map<String, SchedulerManager> serviceMap;

    /**
     * 
     */
    private SchedulerManager(String quartzFileName) {
        this.quartzFileName = quartzFileName;
    }

    /**
     * 获得对象实例
     * 
     * @param quartzFileName
     * @return
     */
    public synchronized static SchedulerManager getInstance(String quartzFileName) {
        if (serviceMap == null) {
            serviceMap = new HashMap<String, SchedulerManager>();
            serviceMap.put(quartzFileName, new SchedulerManager(quartzFileName));
        } else if (serviceMap.get(quartzFileName) == null) {
            serviceMap.put(quartzFileName, new SchedulerManager(quartzFileName));
        }

        return serviceMap.get(quartzFileName);
    }

    /**
     * 启动全部定时任务
     * 
     * @throws SchedulerException
     *             调度异常
     */
    public void startJob() throws SchedulerException {
        startSchedulerJob(getSchedulerConfig());
    }

    /**
     * 启动调度任务
     * 
     * @param configList
     * @throws SchedulerException
     */
    public JobScheduler startSchedulerJob(List<SchedulerConfig> configList) throws SchedulerException {
        if (configList == null) {
            return null;
        }

        // 调度Job
        JobScheduler jobScheduler = getJobScheduler();
        for (SchedulerConfig schedulerConfig : configList) {
            addJob(jobScheduler, schedulerConfig);
        }

        logger.info("开始执行定时任务");
        jobScheduler.execute();

        return jobScheduler;
    }

    /**
     * 查询全部有效的调度配置
     * 
     * @return 配置列表
     */
    public List<SchedulerConfig> getSchedulerConfig() {
        SchedulerConfigDao dao = new SchedulerConfigDao();
        Map<String, String> params = new HashMap<String, String>();
        params.put("STATUS", "1");

        List<SchedulerConfig> configList = dao.selectConfig(params);

        if (configList == null || configList.isEmpty()) {
            return null;
        }

        return configList;
    }

    /**
     * 添加定时调度任务
     * 
     * @param jobScheduler
     *            调度器
     * @param schedulerConfig
     *            配置
     */
    private void addJob(JobScheduler jobScheduler, SchedulerConfig schedulerConfig) {
        if (schedulerConfig.isValid() && isInRunGroup(schedulerConfig.getRunGroup())) {
            // 先删除再增加（针对重启时使用）
            jobScheduler.deleteJob(schedulerConfig.getJobId(), schedulerConfig.getGroupId());

            // 初始化任务对象
            JobBean jobBean = new JobBean();
            jobBean.setGroupId(schedulerConfig.getGroupId()).setJobClassName(schedulerConfig.getJobClass())
                    .setJobId(schedulerConfig.getJobId()).setTriggerId(schedulerConfig.getTriggerId());

            // 运行周期
            if (schedulerConfig.isCronInteger()) {
                jobBean.setRoundTime(Integer.parseInt(schedulerConfig.getCron()));
            } else {
                jobBean.setCron(schedulerConfig.getCron());
            }

            // 运行参数
            if (!StringUtil.isEmptyOrNull(schedulerConfig.getRunProps())) {
                String[] props = schedulerConfig.getRunProps().split(ConfigParams.propsSplit);
                for (String prop : props) {
                    String[] kv = prop.split("=");
                    if (kv.length == 2) {
                        jobBean.addParam(kv[0].trim(), kv[1].trim());
                    }
                }
            }

            jobScheduler.addJobBean(jobBean);
        }
    }

    /**
     * 根据系统参数判断任务是否需要运行
     * 
     * @param group
     *            任务运行组
     * @return 是否属于运行组
     */
    private boolean isInRunGroup(String group) {
        if (StringUtil.isEmptyOrNull(ConfigParams.runGroup) && StringUtil.isEmptyOrNull(ConfigParams.notrunGroup)) {
            return true;
        }

        // 运行组
        if (!StringUtil.isEmptyOrNull(ConfigParams.runGroup)) {
            String[] groups = ConfigParams.runGroup.split(",");

            for (String g : groups) {
                if (g != null && g.trim().equalsIgnoreCase(group)) {
                    return true;
                }
            }

            return false;
        }

        // 非运行组
        if (!StringUtil.isEmptyOrNull(ConfigParams.notrunGroup)) {
            String[] groups = ConfigParams.notrunGroup.split(",");

            for (String g : groups) {
                if (g != null && g.trim().equalsIgnoreCase(group)) {
                    return false;
                }
            }

            return true;
        }

        return true;
    }

    /**
     * 刷新指定的任务，比如执行周期等
     * 
     * @param set
     *            <p>
     *            参数：
     *            </p>
     *            <p>
     *            JOB_ID：定时任务ID，必传
     *            </p>
     *            <p>
     *            TRIGGER_ID：触发ID，必传
     *            </p>
     *            <p>
     *            GROUP_ID：分组ID，必传
     *            </p>
     * 
     * @return 执行结果
     */
    public ResultMsg refreshJob(JobConfigBean bean) {
        logger.info("刷新任务参数：" + bean);
        String jobId = bean.getJobId();
        String triggerId = bean.getTriggerId();
        String groupId = bean.getTriggerId();

        if (StringUtil.isEmptyOrNull(jobId) || StringUtil.isEmptyOrNull(groupId) || StringUtil.isEmptyOrNull(triggerId)) {
            return new ResultMsg(-1, "输入参数不正确");
        }

        // 查询指定任务
        Map<String, String> params = new HashMap<String, String>();
        params.put("JOB_ID", jobId);
        params.put("TRIGGER_ID", triggerId);
        params.put("GROUP_ID", groupId);

        SchedulerConfigDao dao = new SchedulerConfigDao();
        List<SchedulerConfig> configList = dao.selectConfig(params);

        if (configList == null || configList.isEmpty()) {
            return new ResultMsg(-1, "没有找到此定时任务");
        }

        // 取得配置信息
        SchedulerConfig config = configList.get(0);

        // 调度Job
        JobScheduler jobScheduler = null;
        try {
            jobScheduler = getJobScheduler();
        } catch (SchedulerException e) {
            logger.error("任务调取失败", e);
            return new ResultMsg(-1, "任务管理器启动失败！");
        }
        // 无效状态则删除，否则添加
        if ("0".equals(config.getStatus())) {
            jobScheduler.deleteJob(jobId, groupId);
        } else {
            // 先删除
            jobScheduler.deleteJob(jobId, groupId);

            // 再添加
            addJob(jobScheduler, config);
        }

        try {
            jobScheduler.execute();
        } catch (SchedulerException e) {
            logger.error("任务调度失败", e);
            return new ResultMsg(-1, "任务管理器启动失败！");
        }

        return new ResultMsg(0, "OK");
    }

    /**
     * @return
     * @throws SchedulerException
     */
    public JobScheduler getJobScheduler() throws SchedulerException {
        return JobScheduler.getInstance(quartzFileName);
    }

    /**
     * 刷新指定的任务，比如执行周期等
     * 
     * @param set
     *            <p>
     *            参数：
     *            </p>
     *            <p>
     *            JOB_ID：定时任务ID，必传
     *            </p>
     *            <p>
     *            GROUP_ID：分组ID，必传
     *            </p>
     * 
     * @return 执行结果
     */
    public ResultMsg deleteJob(JobConfigBean bean) {
        logger.info("删除任务参数：" + bean);
        String jobId = bean.getJobId();
        String groupId = bean.getGroupId();

        if (StringUtil.isEmptyOrNull(jobId) || StringUtil.isEmptyOrNull(groupId)) {
            return new ResultMsg(-1, "输入参数不正确");
        }

        // 调度Job
        JobScheduler jobScheduler = null;
        try {
            jobScheduler = getJobScheduler();
        } catch (SchedulerException e) {
            logger.error("任务调取失败", e);
            return new ResultMsg(-1, "任务管理器启动失败！");
        }

        jobScheduler.deleteJob(jobId, groupId);

        try {
            jobScheduler.execute();
        } catch (SchedulerException e) {
            logger.error("任务调度失败", e);
            return new ResultMsg(-1, "任务管理器启动失败！");
        }

        return new ResultMsg(0, "OK");
    }

    /**
     * 触发任务执行
     * 
     * @param set
     *            <p>
     *            参数：
     *            </p>
     *            <p>
     *            JOB_ID：定时任务ID，必传
     *            </p>
     *            <p>
     *            TRIGGER_ID：触发ID，必传
     *            </p>
     *            <p>
     *            GROUP_ID：分组ID，必传
     *            </p>
     * 
     * @return 执行结果
     */
    public ResultMsg triggerJob(JobConfigBean bean) {
        logger.info("触发任务参数：" + bean);
        long startTime = System.currentTimeMillis();

        String jobId = bean.getJobId();
        String triggerId = bean.getTriggerId();
        String groupId = bean.getTriggerId();

        if (StringUtil.isEmptyOrNull(jobId) || StringUtil.isEmptyOrNull(groupId) || StringUtil.isEmptyOrNull(triggerId)) {
            return new ResultMsg(-1, "输入参数不正确");
        }

        // 查询指定任务
        Map<String, String> params = new HashMap<String, String>();
        params.put("JOB_ID", jobId);
        params.put("TRIGGER_ID", triggerId);
        params.put("GROUP_ID", groupId);

        SchedulerConfigDao dao = new SchedulerConfigDao();
        List<SchedulerConfig> configList = dao.selectConfig(params);

        if (configList == null || configList.isEmpty()) {
            return new ResultMsg(-1, "没有找到此定时任务");
        }

        // 取得配置信息
        SchedulerConfig config = configList.get(0);
        logger.info("触发任务配置：" + config);
        try {
            @SuppressWarnings("unchecked")
            Class<? extends AbstractJob> jobClass = (Class<? extends AbstractJob>) Class.forName(config.getJobClass());
            AbstractJob job = jobClass.newInstance();

            JobExecutionContext context = new JobExecutionContext4DataImpl();
            // 运行参数
            if (!StringUtil.isEmptyOrNull(config.getRunProps())) {
                String[] props = config.getRunProps().split(ConfigParams.propsSplit);
                for (String prop : props) {
                    String[] kv = prop.split("=");
                    if (kv.length == 2) {
                        context.getJobDetail().getJobDataMap().put(kv[0].trim(), kv[1].trim());
                    }
                }
            }

            job.runJob(context);
        } catch (Exception e) {
            logger.error("错误：", e);
            return new ResultMsg(-1, "初始化任务类失败：" + e);
        }

        logger.info("触发任务执行时间：" + TimeUtil.consumeTime(startTime) + "毫秒");
        return new ResultMsg(0, "OK");
    }
}
