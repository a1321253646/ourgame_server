package com.lemeng.ourgame.sqlbean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RanKingListBean {
	private String user;
	private String name;
	private long level;
	private long time;
	private long index;
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
	public long getIndex() {
		return index;
	}
	@JsonProperty
	public void setIndex(long index) {
		this.index = index;
	}
	
}
