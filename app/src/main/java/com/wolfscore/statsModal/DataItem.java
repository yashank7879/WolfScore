package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataItem{

	@JsonProperty("fixture_id")
	public int fixtureId;

	@JsonProperty("fouls")
	public int fouls;

	@JsonProperty("team_id")
	public int teamId;

	@JsonProperty("ball_safe")
	public int ballSafe;

	@JsonProperty("goal_kick")
	public Object goalKick;

	@JsonProperty("corners")
	public int corners;

	@JsonProperty("redcards")
	public int redcards;

	@JsonProperty("goal_attempts")
	public int goalAttempts;

	@JsonProperty("throw_in")
	public Object throwIn;

	@JsonProperty("possessiontime")
	public int possessiontime;

	@JsonProperty("passes")
	public Passes passes;

	@JsonProperty("saves")
	public int saves;

	@JsonProperty("substitutions")
	public int substitutions;

	@JsonProperty("attacks")
	public Attacks attacks;

	@JsonProperty("yellowcards")
	public int yellowcards;

	@JsonProperty("shots")
	public Shots shots;

	@JsonProperty("offsides")
	public int offsides;

	@JsonProperty("free_kick")
	public Object freeKick;

/*	public void setFixtureId(int fixtureId){
		this.fixtureId = fixtureId;
	}

	public int getFixtureId(){
		return fixtureId;
	}

	public void setFouls(int fouls){
		this.fouls = fouls;
	}

	public int getFouls(){
		return fouls;
	}

	public void setTeamId(int teamId){
		this.teamId = teamId;
	}

	public int getTeamId(){
		return teamId;
	}

	public void setBallSafe(int ballSafe){
		this.ballSafe = ballSafe;
	}

	public int getBallSafe(){
		return ballSafe;
	}

	public void setGoalKick(Object goalKick){
		this.goalKick = goalKick;
	}

	public Object getGoalKick(){
		return goalKick;
	}

	public void setCorners(int corners){
		this.corners = corners;
	}

	public int getCorners(){
		return corners;
	}

	public void setRedcards(int redcards){
		this.redcards = redcards;
	}

	public int getRedcards(){
		return redcards;
	}

	public void setGoalAttempts(int goalAttempts){
		this.goalAttempts = goalAttempts;
	}

	public int getGoalAttempts(){
		return goalAttempts;
	}

	public void setThrowIn(Object throwIn){
		this.throwIn = throwIn;
	}

	public Object getThrowIn(){
		return throwIn;
	}

	public void setPossessiontime(int possessiontime){
		this.possessiontime = possessiontime;
	}

	public int getPossessiontime(){
		return possessiontime;
	}

	public void setPasses(Passes passes){
		this.passes = passes;
	}

	public Passes getPasses(){
		return passes;
	}

	public void setSaves(int saves){
		this.saves = saves;
	}

	public int getSaves(){
		return saves;
	}

	public void setSubstitutions(int substitutions){
		this.substitutions = substitutions;
	}

	public int getSubstitutions(){
		return substitutions;
	}

	public void setAttacks(Attacks attacks){
		this.attacks = attacks;
	}

	public Attacks getAttacks(){
		return attacks;
	}

	public void setYellowcards(int yellowcards){
		this.yellowcards = yellowcards;
	}

	public int getYellowcards(){
		return yellowcards;
	}

	public void setShots(Shots shots){
		this.shots = shots;
	}

	public Shots getShots(){
		return shots;
	}

	public void setOffsides(int offsides){
		this.offsides = offsides;
	}

	public int getOffsides(){
		return offsides;
	}

	public void setFreeKick(Object freeKick){
		this.freeKick = freeKick;
	}

	public Object getFreeKick(){
		return freeKick;
	}*/
}