package com.lemeng.ourgame.btgame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BtGameLoginBackBean {
	public int state;
	public String msg;
	public int getState() {
		return state;
	}
	@JsonProperty
	public void setState(int state) {
		this.state = state;
	}
	public String getMsg() {
		return msg;
	}
	@JsonProperty
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
