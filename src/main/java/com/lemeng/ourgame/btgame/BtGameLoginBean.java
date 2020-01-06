package com.lemeng.ourgame.btgame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BtGameLoginBean {
	public String username;
	public String token;
	public int pid;
	public String sign;
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	public int getPid() {
		return pid;
	}
	
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getSign() {
		return sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
}
