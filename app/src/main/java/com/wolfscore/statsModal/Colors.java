package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Colors{

	@JsonProperty("localteam")
	private Localteam localteam;

	@JsonProperty("visitorteam")
	private Visitorteam visitorteam;

	public void setLocalteam(Localteam localteam){
		this.localteam = localteam;
	}

	public Localteam getLocalteam(){
		return localteam;
	}

	public void setVisitorteam(Visitorteam visitorteam){
		this.visitorteam = visitorteam;
	}

	public Visitorteam getVisitorteam(){
		return visitorteam;
	}
}