package com.lemeng.ourgame;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
public class ResponseDateSqlBeanBean {
	public ArrayList<SqlDateBean> date;
	public long status;
	public long time;
	public long version;
	public String token;
	public boolean isNew;
	public ArrayList<SqlDateBean> getDate() {
		return date;
	}
	public void setDate(ArrayList<SqlDateBean> date) {
		this.date = date;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
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
