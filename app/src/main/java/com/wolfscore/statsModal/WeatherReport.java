package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherReport{

	@JsonProperty("code")
	private String code;

	@JsonProperty("icon")
	private String icon;

	@JsonProperty("temperature")
	private Temperature temperature;

	@JsonProperty("humidity")
	private String humidity;

	@JsonProperty("clouds")
	private String clouds;

	@JsonProperty("type")
	private String type;

	@JsonProperty("wind")
	private Wind wind;

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setIcon(String icon){
		this.icon = icon;
	}

	public String getIcon(){
		return icon;
	}

	public void setTemperature(Temperature temperature){
		this.temperature = temperature;
	}

	public Temperature getTemperature(){
		return temperature;
	}

	public void setHumidity(String humidity){
		this.humidity = humidity;
	}

	public String getHumidity(){
		return humidity;
	}

	public void setClouds(String clouds){
		this.clouds = clouds;
	}

	public String getClouds(){
		return clouds;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setWind(Wind wind){
		this.wind = wind;
	}

	public Wind getWind(){
		return wind;
	}
}