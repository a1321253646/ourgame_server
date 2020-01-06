package com.lemeng.ourgame.sqlbean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lemeng.ourgame.SqlDateBean;

public class SqlDateDeatilBean {

    	
    public long type = -1;
    public long id = -1;
    public String extan = "-1";
    public String token  = null;
    public long goodId =SqlDateBean.DEFAULT_GOOD_ID;
    public long goodType = SqlDateBean.GOOD_TYPE_NOGOOD;
    public long isClean = 1;
    public long action = -1; 
    
    
	public long getAction() {
		return action;
	}
	@JsonProperty
	public void setAction(long action) {
		this.action = action;
	}
	public long getType() {
		return type;
	}
	@JsonProperty
	public void setType(long type) {
		this.type = type;
	}
	public long getId() {
		return id;
	}
	@JsonProperty
	public void setId(long id) {
		this.id = id;
	}
	public String getExtan() {
		return extan;
	}
	@JsonProperty
	public void setExtan(String extan) {
		this.extan = extan;
	}
	public long getGoodId() {
		return goodId;
	}
	@JsonProperty
	public void setGoodId(long goodId) {
		this.goodId = goodId;
	}
	public long getGoodType() {
		return goodType;
	}
	@JsonProperty
	public void setGoodType(long goodType) {
		this.goodType = goodType;
	}
	public long getIsClean() {
		return isClean;
	}
	@JsonProperty
	public void setIsClean(long isClean) {
		this.isClean = isClean;
	}
}
