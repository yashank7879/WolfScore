package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Scores{

	@JsonProperty("ht_score")
	private String htScore;

	@JsonProperty("localteam_score")
	private int localteamScore;

	@JsonProperty("localteam_pen_score")
	private Object localteamPenScore;

	@JsonProperty("visitorteam_score")
	private int visitorteamScore;

	@JsonProperty("ft_score")
	private String ftScore;

	@JsonProperty("et_score")
	private Object etScore;

	@JsonProperty("visitorteam_pen_score")
	private Object visitorteamPenScore;

	public void setHtScore(String htScore){
		this.htScore = htScore;
	}

	public String getHtScore(){
		return htScore;
	}

	public void setLocalteamScore(int localteamScore){
		this.localteamScore = localteamScore;
	}

	public int getLocalteamScore(){
		return localteamScore;
	}

	public void setLocalteamPenScore(Object localteamPenScore){
		this.localteamPenScore = localteamPenScore;
	}

	public Object getLocalteamPenScore(){
		return localteamPenScore;
	}

	public void setVisitorteamScore(int visitorteamScore){
		this.visitorteamScore = visitorteamScore;
	}

	public int getVisitorteamScore(){
		return visitorteamScore;
	}

	public void setFtScore(String ftScore){
		this.ftScore = ftScore;
	}

	public String getFtScore(){
		return ftScore;
	}

	public void setEtScore(Object etScore){
		this.etScore = etScore;
	}

	public Object getEtScore(){
		return etScore;
	}

	public void setVisitorteamPenScore(Object visitorteamPenScore){
		this.visitorteamPenScore = visitorteamPenScore;
	}

	public Object getVisitorteamPenScore(){
		return visitorteamPenScore;
	}
}