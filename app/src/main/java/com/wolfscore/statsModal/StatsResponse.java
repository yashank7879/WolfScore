package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatsResponse{

	@JsonProperty("data")
	public Data data;

	@JsonProperty("message")
	public String message;

	@JsonProperty("status")
	public String status;

/*	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}*/
}