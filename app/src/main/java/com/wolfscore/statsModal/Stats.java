package com.wolfscore.statsModal;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Stats{

	@JsonProperty("data")
	public List<DataItem> data;

	/*public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}*/
}