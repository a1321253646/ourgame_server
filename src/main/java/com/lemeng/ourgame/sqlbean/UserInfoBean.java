package com.lemeng.ourgame.sqlbean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfoBean {

	
	
	private String user;
	private String name;
	private long level ;
	private long time;
	private long register;
	private long luihui;
	private long lasttime;
	private long version;
	private String channel;
	public String getUser() {
		return user;
	}
	@JsonProperty
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	@JsonProperty
	public void setName(String name) {
		this.name = name;
	}
	public long getLevel() {
		return level;
	}
	@JsonProperty
	public void setLevel(long level) {
		this.level = level;
	}
	public long getTime() {
		return time;
	}
	@JsonProperty
	public void setTime(long time) {
		this.time = time;
	}
	public long getRegister() {
		return register;
	}
	@JsonProperty
	public void setRegister(long register) {
		this.register = register;
	}
	public long getLuihui() {
		return luihui;
	}
	@JsonProperty
	public void setLuihui(long luihui) {
		this.luihui = luihui;
	}
	public long getLasttime() {
		return lasttime;
	}
	@JsonProperty
	public void setLasttime(long lasttime) {
		this.lasttime = lasttime;
	}
	public long getVersion() {
		return version;
	}
	@JsonProperty
	public void setVersion(long version) {
		this.version = version;
	}
	public String getChannel() {
		return channel;
	}
	@JsonProperty
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
}
