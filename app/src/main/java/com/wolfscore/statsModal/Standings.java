package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Standings{

	@JsonProperty("visitorteam_position")
	private int visitorteamPosition;

	@JsonProperty("localteam_position")
	private int localteamPosition;

	public void setVisitorteamPosition(int visitorteamPosition){
		this.visitorteamPosition = visitorteamPosition;
	}

	public int getVisitorteamPosition(){
		return visitorteamPosition;
	}

	public void setLocalteamPosition(int localteamPosition){
		this.localteamPosition = localteamPosition;
	}

	public int getLocalteamPosition(){
		return localteamPosition;
	}
}