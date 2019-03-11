package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Localteam{

	@JsonProperty("kit_colors")
	private Object kitColors;

	@JsonProperty("color")
	private Object color;

	public void setKitColors(Object kitColors){
		this.kitColors = kitColors;
	}

	public Object getKitColors(){
		return kitColors;
	}

	public void setColor(Object color){
		this.color = color;
	}

	public Object getColor(){
		return color;
	}
}