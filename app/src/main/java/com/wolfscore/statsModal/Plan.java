package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Plan{

	@JsonProperty("price")
	private String price;

	@JsonProperty("request_limit")
	private String requestLimit;

	@JsonProperty("name")
	private String name;

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setRequestLimit(String requestLimit){
		this.requestLimit = requestLimit;
	}

	public String getRequestLimit(){
		return requestLimit;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
}