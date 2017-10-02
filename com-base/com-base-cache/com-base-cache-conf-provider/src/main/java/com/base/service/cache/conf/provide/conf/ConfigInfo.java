package com.base.service.cache.conf.provide.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("configInfo")
public class ConfigInfo {

	@Value("${jdbc.mysql.driver}")
	private String mysqlDriver;

	@Value("${jdbc.mysql.url}")
	private String mysqlUrl;

	@Value("${jdbc.mysql.username}")
	private String mysqlUsername;

	@Value("${jdbc.mysql.password}")
	private String mysqlPassword;

	@Value("${jdbc.db.type}")
	private String dbType;

	@Value("${jdbc.oracle.driver}")
	private String oracleDriver;

	@Value("${jdbc.oracle.url}")
	private String oracleUrl;

	@Value("${jdbc.oracle.username}")
	private String oracleUsername;

	@Value("${jdbc.oracle.password}")
	private String oraclePassword;
	
	@Value("${cache.entity.size}")
	private String entitySize;
	
	@Value("${cache.memory.size}")
	private String memorySize;

	public String getMysqlDriver() {
		return mysqlDriver;
	}

	public void setMysqlDriver(String mysqlDriver) {
		this.mysqlDriver = mysqlDriver;
	}

	public String getMysqlUrl() {
		return mysqlUrl;
	}

	public void setMysqlUrl(String mysqlUrl) {
		this.mysqlUrl = mysqlUrl;
	}

	public String getMysqlUsername() {
		return mysqlUsername;
	}

	public void setMysqlUsername(String mysqlUsername) {
		this.mysqlUsername = mysqlUsername;
	}

	public String getMysqlPassword() {
		return mysqlPassword;
	}

	public void setMysqlPassword(String mysqlPassword) {
		this.mysqlPassword = mysqlPassword;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getOracleDriver() {
		return oracleDriver;
	}

	public void setOracleDriver(String oracleDriver) {
		this.oracleDriver = oracleDriver;
	}

	public String getOracleUrl() {
		return oracleUrl;
	}

	public void setOracleUrl(String oracleUrl) {
		this.oracleUrl = oracleUrl;
	}

	public String getOracleUsername() {
		return oracleUsername;
	}

	public void setOracleUsername(String oracleUsername) {
		this.oracleUsername = oracleUsername;
	}

	public String getOraclePassword() {
		return oraclePassword;
	}

	public void setOraclePassword(String oraclePassword) {
		this.oraclePassword = oraclePassword;
	}

	public String getEntitySize() {
		return entitySize;
	}

	public void setEntitySize(String entitySize) {
		this.entitySize = entitySize;
	}

	public String getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(String memorySize) {
		this.memorySize = memorySize;
	}

}
