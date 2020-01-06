package com.lemeng.ourgame.sqlbean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserMacBean {
	private String user;
	private String mac;
	
	public String getUser() {
		return user;
	}
	@JsonProperty
	public void setUser(String user) {
		this.user = user;
	}
	public String getMac() {
		return mac;
	}
	@JsonProperty
	public void setMac(String mac) {
		this.mac = mac;
	}
	

	
}
