package com.lemeng.ourgame.btgame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhonePayBean {
	public String extendsInfo;
	public String getExtendsInfo() {
		return extendsInfo;
	}
	@JsonProperty
	public void setExtendsInfo(String extendsInfo) {
		this.extendsInfo = extendsInfo;
	}	
}
