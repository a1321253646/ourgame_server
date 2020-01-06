package com.lemeng.ourgame;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseDateStringBean {
	private String date;
	private long status;
	private long time;
	private long version;
	private String token;
	private long ismust;
	
    public long getIsmust() {
		return ismust;
	}

	public void setIsmust(long ismust) {
		this.ismust = ismust;
	}

	public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
	
	
	public static ResponseDateStringBean getErrorDate(String msg) {
		return getErrorDate(1,msg);
	}
	public static ResponseDateStringBean getErrorDate(long statu,String msg) {
		ResponseDateStringBean date = new ResponseDateStringBean();
		date.status = statu;
		date.date = msg;
		date.time =  System.currentTimeMillis();
		return date;
	}
	
	public static ResponseDateStringBean getUpdateDate(String token) {
		ResponseDateStringBean date = new ResponseDateStringBean();
		date.status = 0;
		date.token = token;
		date.time =  System.currentTimeMillis();
		return date;
	}
	
	public String toString() {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		String json = mapper.writeValueAsString(this);
    		return json;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return "error";
	}
}
