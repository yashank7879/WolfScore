package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coaches{

	@JsonProperty("visitorteam_coach_id")
	private int visitorteamCoachId;

	@JsonProperty("localteam_coach_id")
	private int localteamCoachId;

	public void setVisitorteamCoachId(int visitorteamCoachId){
		this.visitorteamCoachId = visitorteamCoachId;
	}

	public int getVisitorteamCoachId(){
		return visitorteamCoachId;
	}

	public void setLocalteamCoachId(int localteamCoachId){
		this.localteamCoachId = localteamCoachId;
	}

	public int getLocalteamCoachId(){
		return localteamCoachId;
	}
}