package com.base.job.test;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.base.common.framework.job.AbstractJob;

@DisallowConcurrentExecution
public class TestJob extends AbstractJob {
	
	private static Logger logger = LoggerFactory.getLogger(TestJob.class);

	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		logger.warn("测试任务正在运行，运行参数：" + context.getJobDetail().getJobDataMap());
	}

}
