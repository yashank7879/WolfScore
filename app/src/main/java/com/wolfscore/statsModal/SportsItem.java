package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SportsItem{

	@JsonProperty("current")
	private boolean current;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

	public void setCurrent(boolean current){
		this.current = current;
	}

	public boolean isCurrent(){
		return current;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}