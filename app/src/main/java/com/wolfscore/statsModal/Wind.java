package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Wind{

	@JsonProperty("degree")
	private int degree;

	@JsonProperty("speed")
	private String speed;

	public void setDegree(int degree){
		this.degree = degree;
	}

	public int getDegree(){
		return degree;
	}

	public void setSpeed(String speed){
		this.speed = speed;
	}

	public String getSpeed(){
		return speed;
	}
}