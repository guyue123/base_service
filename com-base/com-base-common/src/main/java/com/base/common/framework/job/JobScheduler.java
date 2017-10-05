/**
 * @Copyright base
 */
package com.base.common.framework.job;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

/**
 * job调度类，通过统一的方式管理任务调度工作
 * 
 * 现在实现了两种方式的定时任务：Cron和时间间隔
 * 
 * @author hhx
 * 
 */
public class JobScheduler {
    /**
     * 日志对象
     */
    private static final Logger logger = Logger.getLogger(JobScheduler.class);

    /**
     * jobBean列表
     */
    private List<JobBean> jobBeans;

    /**
     * 标识需要删除的Job key
     */
    private List<JobKey> deletingJobKeys;
    /**
     * quartz的调度器(相当于总控开关)
     */
    private Scheduler scheduler = null;

    /**
     * 唯一对象实例
     */
    private static Map<String, JobScheduler> instances;

    /**
     * 终止调度
     */
    private boolean stopped = false;

    /**
     * 构造函数
     * 
     * @throws SchedulerException
     * 
     */
    private JobScheduler(String fileName) throws SchedulerException {
        // 初始化
        init(fileName);
    }

    /**
     * 单例模式，获得对象实例
     * 
     * @return 调度对象
     * @throws SchedulerException
     */
    public static JobScheduler getInstance() throws SchedulerException {
        return getInstance(null);
    }

    /**
     * 单例模式，获得对象实例
     * 
     * @return 调度对象
     * @throws SchedulerException
     */
    public synchronized static JobScheduler getInstance(String fileName) throws SchedulerException {
        if (fileName == null) {
            fileName = "";
        }

        if (instances == null) {
            instances = new HashMap<String, JobScheduler>();
            instances.put(fileName, new JobScheduler(fileName));
        } else if (instances.get(fileName) == null) {
            instances.put(fileName, new JobScheduler(fileName));
        }

        return instances.get(fileName);
    }

    /**
     * 启动工作调度
     */
    private void start() {
        try {
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            logger.error("Job schedule启动失败", e);
        }
    }

    /**
     * 初始化调度
     * 
     * @throws SchedulerException
     */
    private void init(String fileName) throws SchedulerException {
        if (scheduler == null) {
            logger.info("初始化job调度器实例");
            if (fileName != null && !"".equals(fileName) && (new File(fileName)).exists()) {
                scheduler = new StdSchedulerFactory(fileName).getScheduler();
            } else {
                scheduler = new StdSchedulerFactory().getScheduler();
            }
        }
    }

    /**
     * 运行所有Job
     * 
     * @throws SchedulerException
     */
    public void execute() throws SchedulerException {
        if (jobBeans == null || jobBeans.isEmpty()) {
            logger.info("不存在Job!");
            return;
        }

        // 从调度删除Job
        deleteJobs();

        // 执行Job
        scheduleJob();

        // 清空数据
        clear();

        // 启动调度
        this.start();
    }

    /**
     * 调度执行Job
     * 
     * @throws SchedulerException
     */
    private void scheduleJob() throws SchedulerException {
        for (JobBean bean : jobBeans) {
            if (bean.isValidJobBean()) {
                int status = getScheduleJobStatus(bean);
                // 存在且没有更新
                if (status == 0) {
                    continue;
                }

                // 已经存在，但有更新
                if (status == 1) {
                    // 先删除job
                    this.deleteSchedulerJob(bean.getJobKey());
                }

                // 构建并添加job
                Class<? extends Job> clazz = bean.getJobClass();
                if (clazz == null) {
                    continue;
                }

                JobDetail job = newJob(clazz).withIdentity(bean.getJobId(), bean.getGroupId()).build();
                Trigger trigger = newTrigger().withIdentity(bean.getTriggerId(), bean.getGroupId())
                        .forJob(bean.getJobId(), bean.getGroupId()).withSchedule(bean.getJobSehedule()).build();

                // 设置参数
                if (bean.getJobDataMap() != null && !bean.getJobDataMap().isEmpty()) {
                    Set<String> set = bean.getJobDataMap().keySet();
                    Iterator<String> it = set.iterator();
                    while (it.hasNext()) {
                        String key = it.next();
                        job.getJobDataMap().put(key, bean.getParam(key));
                    }
                }

                this.scheduler.scheduleJob(job, trigger);
            }
        }
    }

    /**
     * 删除指定Job
     * 
     * @throws SchedulerException
     */
    private void deleteJobs() throws SchedulerException {
        if (this.deletingJobKeys != null) {
            for (JobKey jk : deletingJobKeys) {
                this.deleteSchedulerJob(jk);
            }
        }
    }

    /**
     * 判读指定的Job是否存在，调度周期是否更新
     * 
     * @param bean
     * @throws SchedulerException
     * @return 当前状态 0:不需要更新 1:需要更新 -1：不存在
     */
    private int getScheduleJobStatus(JobBean bean) throws SchedulerException {
        // 是否存在
        if (this.scheduler.checkExists(bean.getTriggerKey())) {
            Trigger trigger = this.scheduler.getTrigger(bean.getTriggerKey());
            if (trigger instanceof CronTrigger) {
                if (((CronTrigger) trigger).getCronExpression().equals(bean.getCron())) {
                    return 0;
                }

                return 1;
            } else if (trigger instanceof SimpleTrigger) {
                if (((SimpleTrigger) trigger).getRepeatInterval() == bean.getRoundTime() * 1000) {
                    return 0;
                }

                return 1;
            } else {
                return 0;
            }
        }

        return -1;
    }

    /**
     * 工作调用结束后，清空当前执行中的数据
     */
    private void clear() {
        if (this.deletingJobKeys != null) {
            this.deletingJobKeys.clear();
        }
    }

    /**
     * 添加jobBean
     * 
     * @param bean
     *            JobBean对象
     * @return 是否添加成功
     */
    public boolean addJobBean(JobBean bean) {
        if (stopped) {
            return false;
        }

        if (jobBeans == null) {
            jobBeans = new ArrayList<JobBean>();
        }

        if (bean.isValidJobBean()) {
            jobBeans.add(bean);

            return true;
        }

        logger.error("添加定时Job失败， 请检查！");
        return false;
    }

    /**
     * 通过jobId和groupId删除job
     * 
     * @param jobId
     *            jobId
     * @param groupId
     *            groupId
     */
    public void deleteJob(String jobId, String groupId) {
        this.deleteJob(new JobKey(jobId, groupId));
    }

    /**
     * 通过jobId和groupId删除job
     * 
     * @param jobId
     *            jobId
     * @param groupId
     *            groupId
     */
    public void deleteJob(JobKey jobKey) {
        if (deletingJobKeys == null) {
            deletingJobKeys = new ArrayList<JobKey>();
        }

        deletingJobKeys.add(jobKey);

        // 从Job列表中删除
        this.deleteJobFromList(jobBeans, jobKey);
    }

    /**
     * 通过triggerId和groupId获得Trigger对象
     * 
     * @param triggerId
     *            triggerId
     * @param groupId
     *            groupId
     * @return Trigger对象
     * @throws SchedulerException
     */
    /*
     * public Trigger getTriggerById(String triggerId, String groupId) throws
     * SchedulerException { if (scheduler == null) { return null; } return
     * this.scheduler.getTrigger(new TriggerKey(triggerId, groupId)); }
     */

    /**
     * Job列表中是否存在JobKey的Job
     * 
     * @param bean
     * @return
     */
    /*
     * private boolean containsJob(List<JobBean> jobBeans, JobBean bean) { if
     * (bean == null) { return false; }
     * 
     * return this.containsJob(jobBeans, bean.getJobKey()); }
     */

    /**
     * Job列表中是否存在JobKey的Job
     * 
     * @param bean
     * @return
     */
    /*
     * private boolean containsJob(List<JobBean> jobBeans, JobKey jobKey) { if
     * (jobBeans == null || jobBeans.isEmpty() || jobKey == null) { return
     * false; }
     * 
     * for (JobBean jbean : jobBeans) { if (jbean.equalsJobKey(jobKey)) { return
     * true; } }
     * 
     * return false; }
     */

    /**
     * Job列表中是否存在JobKey的Job
     * 
     * @param bean
     * @return
     */
    private boolean deleteJobFromList(List<JobBean> jobBeans, JobKey jobKey) {
        if (jobBeans == null || jobBeans.isEmpty() || jobKey == null) {
            return false;
        }

        for (JobBean jbean : jobBeans) {
            if (jbean.equalsJobKey(jobKey)) {
                jobBeans.remove(jbean);
                return true;
            }
        }

        return false;
    }

    /**
     * 从调度中删除Job
     * 
     * @param bean
     * @throws SchedulerException
     */
    /*
     * private void deleteJob(JobBean bean) throws SchedulerException { if
     * (this.scheduler == null || bean ==null) { return; }
     * 
     * this.deleteJob(bean.getJobKey()); }
     */

    /**
     * 从调度中删除Job
     * 
     * @param bean
     * @throws SchedulerException
     */
    private boolean deleteSchedulerJob(JobKey jobKey) throws SchedulerException {
        if (this.scheduler == null || jobKey == null) {
            return false;
        }

        return this.scheduler.deleteJob(jobKey);
    }

    /**
     * 获得已经执行的Job数量
     * 
     * @return Jobs数量
     */
    public int getNumberOfJobsExecuted() {
        if (this.scheduler == null) {
            return 0;
        }

        try {
            return this.scheduler.getMetaData().getNumberOfJobsExecuted();
        } catch (SchedulerException e) {
            logger.error("", e);
            return 0;
        }
    }

    /**
     * 获得线程数量
     * 
     * @return Jobs数量
     */
    public int getThreadPoolSize() {
        if (this.scheduler == null) {
            return 0;
        }

        try {
            return this.scheduler.getMetaData().getThreadPoolSize();
        } catch (SchedulerException e) {
            logger.error("", e);
            return 0;
        }
    }

    /**
     * 根据group获得全部JobKey
     * 
     * @param group
     *            分组名
     * @return JobKey集合
     * @throws SchedulerException
     */
    private Set<JobKey> getJobKeysByGroup(final String group) throws SchedulerException {
        GroupMatcher<JobKey> gm = GroupMatcher.groupEquals(group);
        return scheduler.getJobKeys(gm);
    }

    /**
     * 根据group获得全部Job Name
     * 
     * @param group
     *            分组名
     * @return JobKey集合
     * @throws SchedulerException
     */
    public List<String> getJobNames(final String group) throws SchedulerException {
        Set<JobKey> jobKeySet = getJobKeysByGroup(group);

        Iterator<JobKey> it = jobKeySet.iterator();

        List<String> jNames = new ArrayList<String>();
        while (it.hasNext()) {
            JobKey jk = it.next();
            jNames.add(jk.getName());
        }

        return jNames;
    }

    /**
     * 终止调度程序运行
     * 
     * @param waitComplete
     *            是否等待全部任务执行结束
     * @return
     * @throws SchedulerException
     * @throws InterruptedException
     */
    public boolean shutdown(boolean waitComplete) throws SchedulerException, InterruptedException {
        stopped = true;
        if (scheduler == null) {
            return true;
        }

        this.scheduler.shutdown(waitComplete);
        logger.info("正在停止任务调度【" + scheduler.getSchedulerName() + "】...............");
        while (!this.scheduler.isShutdown()) {
            Thread.sleep(100);
        }

        return true;
    }

    /**
     * 终止全部调度程序运行
     * 
     * @param waitComplete
     *            是否等待全部任务执行结束
     * @throws InterruptedException
     * @throws SchedulerException
     */
    public static void shutdownAll(boolean waitComplete) throws SchedulerException, InterruptedException {
        if (instances == null) {
            return;
        }

        Iterator<String> keys = instances.keySet().iterator();

        while (keys.hasNext()) {
            instances.get(keys.next()).shutdown(waitComplete);
        }
    }

    /**
     * 查询全部分组名
     * 
     * @throws SchedulerException
     * 
     */
    public List<String> getJobGroupNames() throws SchedulerException {
        return scheduler.getJobGroupNames();
    }
}
