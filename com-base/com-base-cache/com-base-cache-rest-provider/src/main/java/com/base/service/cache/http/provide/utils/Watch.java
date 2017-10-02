package com.base.service.cache.http.provide.utils;

public class Watch {

	private long startTimeMillis;

	public Watch() {
		this.startTimeMillis = System.currentTimeMillis();
	}

	public String stop() {
		return " running time (sec)=" + (System.currentTimeMillis() - this.startTimeMillis) / 1000.0;
	}
}