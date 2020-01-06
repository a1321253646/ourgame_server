package com.lemeng.ourgame;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
public class ResponseDateRangeBean {
	public ArrayList<RanKingListBean> date;
	public long status;
	public long time;
	public long version;
	public String token;
	public ArrayList<RanKingListBean> getDate() {
		return date;
	}
	public void setDate(ArrayList<RanKingListBean> date) {
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
