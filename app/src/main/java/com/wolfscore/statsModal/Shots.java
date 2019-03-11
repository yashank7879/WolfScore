package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Shots{

	@JsonProperty("total")
	public int total;

	@JsonProperty("insidebox")
	public int insidebox;

	@JsonProperty("blocked")
	public int blocked;

	@JsonProperty("outsidebox")
	public int outsidebox;

	@JsonProperty("ongoal")
	public int ongoal;

	@JsonProperty("offgoal")
	public int offgoal;

/*	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setInsidebox(int insidebox){
		this.insidebox = insidebox;
	}

	public int getInsidebox(){
		return insidebox;
	}

	public void setBlocked(int blocked){
		this.blocked = blocked;
	}

	public int getBlocked(){
		return blocked;
	}

	public void setOutsidebox(int outsidebox){
		this.outsidebox = outsidebox;
	}

	public int getOutsidebox(){
		return outsidebox;
	}

	public void setOngoal(int ongoal){
		this.ongoal = ongoal;
	}

	public int getOngoal(){
		return ongoal;
	}

	public void setOffgoal(int offgoal){
		this.offgoal = offgoal;
	}

	public int getOffgoal(){
		return offgoal;
	}*/
}