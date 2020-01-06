package com.lemeng.ourgame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequiteBean {
	String user = null ;
	String token = null ;
	String channel = "base";
	long version ;
	SqlDateBean[] date;
    public String getUser() {
        return user;
    }
	@JsonProperty
    public void setUser(String user) {
        this.user = user;
    }

    public String getChannel() {
        return channel;
    }
	@JsonProperty
    public void setChannel(String channel) {
        this.channel = channel;
    }
    
    public String getToken() {
        return token;
    }
    @JsonProperty
    public void setToken(String token) {
        this.token = token;
    }
    
    public Long getVersion() {
        return version;
    }
    @JsonProperty
    public void setVersion(Long version) {
        this.version = version;
    }
	public SqlDateBean[] getDate() {
		return date;
	}
	@JsonProperty
	public void setDate(SqlDateBean[] date) {
		this.date = date;
	}

    
    
}
