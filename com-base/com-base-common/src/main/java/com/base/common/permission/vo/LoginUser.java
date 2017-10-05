package com.base.common.permission.vo;

import org.apache.commons.lang3.StringUtils;
/**
 * 登录用户信息
 * @author Administrator
 *
 */
public class LoginUser {
    
	protected String username;
	
	protected String password;
	

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public boolean isEmpty(){
		return StringUtils.isEmpty(username) || StringUtils.isEmpty(password);
	}

    @Override
    public String toString() {
        return "LoginUser [username=" + username + ", password=" + password + "]";
    }

	
}
