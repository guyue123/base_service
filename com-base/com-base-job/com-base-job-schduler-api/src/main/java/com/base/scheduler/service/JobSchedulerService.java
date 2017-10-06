package com.base.scheduler.service;

import com.base.scheduler.bean.JobConfigBean;
import com.base.scheduler.bean.ResultMsg;

/**
 * 任务调度服务接口
 * @author huaxi
 *
 */
public interface JobSchedulerService {

	/**
	 * 刷新任务
	 * @param jobMap
	 * @return
	 */
	public ResultMsg refreshJob(JobConfigBean bean);
	
    /**
     * 删除任务
     * <p>
     * 参数：
     * </p>
     * <p>
     * JOB_ID：定时任务ID，必传
     * </p>
     * <p>
     * GROUP_ID：分组ID，必传
     * </p>
     * 
     * @param params
     * @return
     */
    public ResultMsg deleteJob(JobConfigBean bean);
    
    
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
    public ResultMsg triggerJob(JobConfigBean bean);
}
