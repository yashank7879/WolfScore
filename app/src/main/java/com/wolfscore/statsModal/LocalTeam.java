package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocalTeam{

	@JsonProperty("data")
	private Data data;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}
}