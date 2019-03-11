package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StartingAt{

	@JsonProperty("date")
	private String date;

	@JsonProperty("date_time")
	private String dateTime;

	@JsonProperty("timezone")
	private String timezone;

	@JsonProperty("time")
	private String time;

	@JsonProperty("timestamp")
	private int timestamp;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setTimezone(String timezone){
		this.timezone = timezone;
	}

	public String getTimezone(){
		return timezone;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setTimestamp(int timestamp){
		this.timestamp = timestamp;
	}

	public int getTimestamp(){
		return timestamp;
	}
}