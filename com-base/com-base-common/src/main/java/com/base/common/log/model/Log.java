package com.base.common.log.model;


import java.util.Date;

/**
 *
 * @classname Log
 * @date 2016年6月15日 下午5:03:18   
 */
public class Log {

	/**
	 */
	private static final long serialVersionUID = 1L;
	private String logId;
	private String logType;				//controller日志为1，service日志为2
	private String description;
	private String requestUri;
	private String module;				//模块
	private String job;					//作业
	private String opt;					//操作
	private String targetClass;
	private String method;
	private String methodParams;
	private String methodResult;
	private String operateType;			//操作类别，1新增，2删除，3修改，4查询，5跳转
	private String sessionId;
	private String requestId;
	private String requestIp;
	private String userId;
	private String userName;
	private String userUnitCode;
	private String userUnitName;
	private String userPhone;
	private String userCardid;
	private Date createDate;
	private String areaCode; // 操作用户区域编码
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	public String getRequestUri() {
		return requestUri;
	}
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}
	public String getTargetClass() {
		return targetClass;
	}
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getMethodParams() {
		return methodParams;
	}
	public void setMethodParams(String methodParams) {
		this.methodParams = methodParams;
	}
	public String getMethodResult() {
		return methodResult;
	}
	public void setMethodResult(String methodResult) {
		this.methodResult = methodResult;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getRequestIp() {
		return requestIp;
	}
	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserUnitCode() {
		return userUnitCode;
	}
	public void setUserUnitCode(String userUnitCode) {
		this.userUnitCode = userUnitCode;
	}
	public String getUserUnitName() {
		return userUnitName;
	}
	public void setUserUnitName(String userUnitName) {
		this.userUnitName = userUnitName;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserCardid() {
		return userCardid;
	}
	public void setUserCardid(String userCardid) {
		this.userCardid = userCardid;
	}

    public String getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    @Override
    public String toString() {
        return "Log [logId=" + logId + ", logType=" + logType + ", description=" + description + ", requestUri="
                + requestUri + ", module=" + module + ", job=" + job + ", opt=" + opt + ", targetClass=" + targetClass
                + ", method=" + method + ", methodParams=" + methodParams + ", methodResult=" + methodResult
                + ", operateType=" + operateType + ", sessionId=" + sessionId + ", requestId=" + requestId
                + ", requestIp=" + requestIp + ", userId=" + userId + ", userName=" + userName + ", userUnitCode="
                + userUnitCode + ", userUnitName=" + userUnitName + ", userPhone=" + userPhone + ", userCardid="
                + userCardid + ", createDate=" + createDate + ", areaCode=" + areaCode + "]";
    }
	
}

