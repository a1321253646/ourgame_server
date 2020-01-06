package com.lemeng.ourgame.btgame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BtGamePayBean {
	public String orderid;
	public int gameid;
	public int serverid;
	public String username;
	public float amount;
	public int time;
	public String extendsInfo;
	public String sign;
	public String getOrderid() {
		return orderid;
	}
	@JsonProperty
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public int getGameid() {
		return gameid;
	}
	@JsonProperty
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}
	public int getServerid() {
		return serverid;
	}
	@JsonProperty
	public void setServerid(int serverid) {
		this.serverid = serverid;
	}
	public String getUsername() {
		return username;
	}
	@JsonProperty
	public void setUsername(String username) {
		this.username = username;
	}
	public float getAmount() {
		return amount;
	}
	@JsonProperty
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public int getTime() {
		return time;
	}
	@JsonProperty
	public void setTime(int time) {
		this.time = time;
	}
	public String getExtendsInfo() {
		return extendsInfo;
	}
	@JsonProperty
	public void setExtendsInfo(String extendsInfo) {
		this.extendsInfo = extendsInfo;
	}
	public String getSign() {
		return sign;
	}
	@JsonProperty
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
}
