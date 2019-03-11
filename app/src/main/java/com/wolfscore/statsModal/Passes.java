package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Passes{

	@JsonProperty("total")
	public int total;

	@JsonProperty("accurate")
	public int accurate;

	@JsonProperty("percentage")
	public int percentage;

	/*public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setAccurate(int accurate){
		this.accurate = accurate;
	}

	public int getAccurate(){
		return accurate;
	}

	public void setPercentage(int percentage){
		this.percentage = percentage;
	}

	public int getPercentage(){
		return percentage;
	}*/
}