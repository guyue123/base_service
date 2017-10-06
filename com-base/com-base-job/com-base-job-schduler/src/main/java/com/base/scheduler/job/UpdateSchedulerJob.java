/** 
 *@Project: 公共包
 *@Date: 2015年6月18日 
 *@Copyright: 2015 www.govnet.com.cn Inc. All rights reserved. 
 */
package com.base.scheduler.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.base.common.framework.job.AbstractJob;
import com.base.common.framework.job.JobScheduler;
import com.base.scheduler.config.Constant;
import com.base.scheduler.config.ConfigParams;
import com.base.scheduler.manager.SchedulerManager;
import com.base.scheduler.orm.pojo.SchedulerConfig;

/**
 * 更新调度器定时任务的任务
 *
 * @author <a href="mailto:huhuaxiang@govnet.com.cn">hhx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2015年6月18日
 */
@DisallowConcurrentExecution
public class UpdateSchedulerJob extends AbstractJob {
    /**
     * 日志对象
     */
    private static Logger logger = Logger.getLogger(UpdateSchedulerJob.class);

    /**
     * quartz的调度器(相当于总控开关)
     */
    public JobScheduler scheduler = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.govnet.common.framework.job.AbstractJob#runJob(org.quartz.
     * JobExecutionContext)
     */
    @Override
    public void runJob(JobExecutionContext context) throws JobExecutionException {
        SchedulerManager service = SchedulerManager.getInstance(ConfigParams.quartzConfigFilePath);
        try {
            scheduler = service.getJobScheduler();
        } catch (SchedulerException e) {
            logger.error("初始化任务调度器失败", e);
            return;
        }
        List<SchedulerConfig> configList = service.getSchedulerConfig();

        // 删除全部调度任务
        if (configList == null) {
            try {
                this.delAllGroupJobs();
            } catch (SchedulerException e) {
                logger.error("删除全部调度任务失败", e);
            }

            return;
        }

        logger.info("有效定时任务数：" + configList.size());

        // 删除任务
        try {
            this.delSchedulerJobs(configList);
        } catch (SchedulerException e) {
            logger.error("删除调度任务失败", e);
        }

        // 更新调度时间更新的任务
        try {
            service.startSchedulerJob(configList);
        } catch (SchedulerException e) {
            logger.error("重启调度任务失败", e);
        }

    }

    /**
     * 重新设置任务调度的JOB
     * 
     * @param configList
     *            配置列表
     * @return
     * @throws SchedulerException
     */
    private void delSchedulerJobs(final List<SchedulerConfig> configList) throws SchedulerException {
        Map<String, List<SchedulerConfig>> groupMap = groupConfigList(configList);

        // 删除不存在组的任务
        delGroupAllJobs(groupMap);

        // 删除分组下的任务
        delGroupJobs(groupMap);
    }

    /**
     * 删除组内部分任务
     * 
     * @param groupMap
     */
    private void delGroupJobs(Map<String, List<SchedulerConfig>> groupMap) {
        Iterator<String> it = groupMap.keySet().iterator();
        while (it.hasNext()) {
            String groupId = it.next();
            // 获取所有分组名：任务ID
            String[] jobNames = getJobNames(groupId);

            // 当前数据库分组数据
            List<SchedulerConfig> configs = groupMap.get(groupId);

            for (int i = 0; i < jobNames.length; i++) {
                // 是否已经删除
                boolean deleted = true;
                for (SchedulerConfig config : configs) {
                    if (config.getJobId().equals(jobNames[i])) {
                        // 存在则不删除
                        deleted = false;
                        break;
                    }
                }

                if (deleted) {
                    scheduler.deleteJob(jobNames[i], groupId);
                }
            }
        }
    }

    /**
     * 删除不存在组的全部任务
     * 
     * @param groupMap
     * @throws SchedulerException
     */
    private void delGroupAllJobs(Map<String, List<SchedulerConfig>> groupMap) throws SchedulerException {
        // 删除分组
        List<String> gpNames = this.scheduler.getJobGroupNames();
        for (String gpName : gpNames) {
            // 保留默认组
            if (Constant.DEFAULT_SCHEDULER_GROUP.equalsIgnoreCase(gpName)) {
                continue;
            }

            if (!groupMap.containsKey(gpName)) {
                // 删除组内的全部任务
                String[] jobNames = getJobNames(gpName);
                for (String jName : jobNames) {
                    scheduler.deleteJob(jName, gpName);
                }
            }
        }
    }

    /**
     * 删除全部任务
     * 
     * @param groupMap
     * @throws SchedulerException
     */
    private void delAllGroupJobs() throws SchedulerException {
        // 删除分组
        List<String> gpNames = this.scheduler.getJobGroupNames();
        for (String gpName : gpNames) {
            // 保留默认组
            if (Constant.DEFAULT_SCHEDULER_GROUP.equalsIgnoreCase(gpName)) {
                continue;
            }

            // 删除组内的全部任务
            String[] jobNames = getJobNames(gpName);
            for (String jName : jobNames) {
                scheduler.deleteJob(jName, gpName);
            }
        }
    }

    /**
     * 按照分组ID重新组织列表
     * 
     * @param configList
     *            配置列表
     */
    public Map<String, List<SchedulerConfig>> groupConfigList(final List<SchedulerConfig> configList) {
        // 按照分组ID重新组织列表
        Map<String, List<SchedulerConfig>> groupConfigList = new HashMap<String, List<SchedulerConfig>>();
        for (SchedulerConfig config : configList) {
            if (groupConfigList.containsKey(config.getGroupId())) {
                groupConfigList.get(config.getGroupId()).add(config);
            } else {
                List<SchedulerConfig> tmpList = new ArrayList<SchedulerConfig>();
                tmpList.add(config);
                groupConfigList.put(config.getGroupId(), tmpList);
            }
        }

        return groupConfigList;
    }

    /**
     * 根据组别获得所有job名
     * 
     * @param group
     *            分组名
     * @return
     */
    private String[] getJobNames(final String group) {
        String[] jobNames = null;
        try {
            List<String> jNames = scheduler.getJobNames(group);

            jobNames = new String[jNames.size()];
            jobNames = jNames.toArray(jobNames);
        } catch (SchedulerException e1) {
            jobNames = new String[0];
        }

        return jobNames;
    }

}
