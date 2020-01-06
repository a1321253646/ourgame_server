package com.lemeng.ourgame.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by rmiao on 3/14/2017.
 */
public class HelloWorldConfiguration extends Configuration {


	
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";
    @NotEmpty
    private String updateDec ;
    @NotNull
    private long updateVersion;
    @NotNull
    private long updateMust;
    @NotNull
    private long dateVersion;
    @NotNull
    private long rangeTime;
    
    @NotNull
    private long mIsBtgamePlay;
    
    @JsonProperty
    public String getUpdateDec() {
		return updateDec;
	}
    @JsonProperty
	public void setUpdateDec(String updateDec) {
		this.updateDec = updateDec;
	}
	@JsonProperty
	public long getUpdateVersion() {
		return updateVersion;
	}
	@JsonProperty
	public void setUpdateVersion(long updateVersion) {
		this.updateVersion = updateVersion;
	}
	@JsonProperty
	public long getUpdateMust() {
		return updateMust;
	}
	@JsonProperty
	public void setUpdateMust(long updateMust) {
		this.updateMust = updateMust;
	}
	@JsonProperty
	public long getDateVersion() {
		return dateVersion;
	}
	@JsonProperty
	public void setDateVersion(long dateVersion) {
		this.dateVersion = dateVersion;
	}
	@JsonProperty
	public long getRangeTime() {
		return rangeTime;
	}
	@JsonProperty
	public void setRangeTime(long rangeTime) {
		this.rangeTime = rangeTime;
	}

	@JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }
    @JsonProperty
	public long getmIsBtgamePlay() {
		return mIsBtgamePlay;
	}
    @JsonProperty
	public void setmIsBtgamePlay(long mIsBtgamePlay) {
		this.mIsBtgamePlay = mIsBtgamePlay;
	}
    
    
}
