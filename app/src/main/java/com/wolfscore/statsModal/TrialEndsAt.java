package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrialEndsAt{

	@JsonProperty("date")
	private String date;

	@JsonProperty("timezone")
	private String timezone;

	@JsonProperty("timezone_type")
	private int timezoneType;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setTimezone(String timezone){
		this.timezone = timezone;
	}

	public String getTimezone(){
		return timezone;
	}

	public void setTimezoneType(int timezoneType){
		this.timezoneType = timezoneType;
	}

	public int getTimezoneType(){
		return timezoneType;
	}
}