package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Attacks{

	@JsonProperty("dangerous_attacks")
	private int dangerousAttacks;

	@JsonProperty("attacks")
	private int attacks;

	public void setDangerousAttacks(int dangerousAttacks){
		this.dangerousAttacks = dangerousAttacks;
	}

	public int getDangerousAttacks(){
		return dangerousAttacks;
	}

	public void setAttacks(int attacks){
		this.attacks = attacks;
	}

	public int getAttacks(){
		return attacks;
	}
}