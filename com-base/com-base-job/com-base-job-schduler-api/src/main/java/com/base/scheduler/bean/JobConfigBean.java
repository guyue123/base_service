package com.base.scheduler.bean;

/**
 * 任务对象
 * @author huaxi
 *
 */
public class JobConfigBean {
	
	/**
	 * 任务Id
	 */
	private String jobId;
	
	/**
	 * 触发ID
	 */
	private String triggerId;
	
	/**
	 * 分组ID
	 */
	private String groupId;

	@Override
	public String toString() {
		return "JobConfigBean [jobId=" + jobId + ", triggerId=" + triggerId + ", groupId=" + groupId + "]";
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getTriggerId() {
		return triggerId;
	}

	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
