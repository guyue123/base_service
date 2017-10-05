package com.base.common.permission.constant;
/**
 * 登录模块常量
 * @author <a href="mailto:zhuzhoucheng@base.com.cn">朱周城</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2015年6月19日
 */
public final class PermissionConstant {
	
    public static final String LOGIN_INFO = "loginInfo";
    
	public static final String LOGIN_URL = "/toLogin";
	
	public static final String MESSAGE_URL = "/message";
	
	public static final String NO_SESSION_URL = "/login/noSession";
	
	//角色类型
	public final class RoleTYPE{
	    //管理员
	    public static final String  ADMIN = "ADMIN";
	    //普通角色
	    public static final String  COMMON = "COMMON";
	    //试用角色
	    public static final String  TRIAL = "TRAIL";
	}
	
}
