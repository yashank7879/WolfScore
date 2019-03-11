package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Temperature{

	@JsonProperty("temp")
	private double temp;

	@JsonProperty("unit")
	private String unit;

	public void setTemp(double temp){
		this.temp = temp;
	}

	public double getTemp(){
		return temp;
	}

	public void setUnit(String unit){
		this.unit = unit;
	}

	public String getUnit(){
		return unit;
	}
}