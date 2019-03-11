package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Formations{

	@JsonProperty("visitorteam_formation")
	private String visitorteamFormation;

	@JsonProperty("localteam_formation")
	private String localteamFormation;

	public void setVisitorteamFormation(String visitorteamFormation){
		this.visitorteamFormation = visitorteamFormation;
	}

	public String getVisitorteamFormation(){
		return visitorteamFormation;
	}

	public void setLocalteamFormation(String localteamFormation){
		this.localteamFormation = localteamFormation;
	}

	public String getLocalteamFormation(){
		return localteamFormation;
	}
}