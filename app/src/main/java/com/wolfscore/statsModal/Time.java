package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Time{

	@JsonProperty("added_time")
	private int addedTime;

	@JsonProperty("starting_at")
	private StartingAt startingAt;

	@JsonProperty("injury_time")
	private int injuryTime;

	@JsonProperty("extra_minute")
	private Object extraMinute;

	@JsonProperty("status")
	private String status;

	@JsonProperty("minute")
	private int minute;

	@JsonProperty("second")
	private Object second;

	public void setAddedTime(int addedTime){
		this.addedTime = addedTime;
	}

	public int getAddedTime(){
		return addedTime;
	}

	public void setStartingAt(StartingAt startingAt){
		this.startingAt = startingAt;
	}

	public StartingAt getStartingAt(){
		return startingAt;
	}

	public void setInjuryTime(int injuryTime){
		this.injuryTime = injuryTime;
	}

	public int getInjuryTime(){
		return injuryTime;
	}

	public void setExtraMinute(Object extraMinute){
		this.extraMinute = extraMinute;
	}

	public Object getExtraMinute(){
		return extraMinute;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setMinute(int minute){
		this.minute = minute;
	}

	public int getMinute(){
		return minute;
	}

	public void setSecond(Object second){
		this.second = second;
	}

	public Object getSecond(){
		return second;
	}
}