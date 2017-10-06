package com.base.scheduler.service.impl;

import com.base.scheduler.bean.JobConfigBean;
import com.base.scheduler.bean.ResultMsg;
import com.base.scheduler.config.ConfigParams;
import com.base.scheduler.manager.SchedulerManager;
import com.base.scheduler.service.JobSchedulerService;

public class JobSchedulerServiceImpl implements JobSchedulerService {

	@Override
	public ResultMsg refreshJob(JobConfigBean bean) {
		return SchedulerManager.getInstance(ConfigParams.quartzConfigFilePath).refreshJob(bean);
	}

	@Override
	public ResultMsg deleteJob(JobConfigBean bean) {
		return SchedulerManager.getInstance(ConfigParams.quartzConfigFilePath).deleteJob(bean);
	}

	@Override
	public ResultMsg triggerJob(JobConfigBean bean) {
		return SchedulerManager.getInstance(ConfigParams.quartzConfigFilePath).triggerJob(bean);
	}

}
