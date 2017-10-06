package com.base.scheduler.bean;

public class ResultMsg {
	
	/**
	 * 结果编码
	 */
	private int code = 0;
	
	/**
	 * 消息内容
	 */
	private String msg;
	
	public ResultMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ResultMsg [code=" + code + ", msg=" + msg + "]";
	}
}
