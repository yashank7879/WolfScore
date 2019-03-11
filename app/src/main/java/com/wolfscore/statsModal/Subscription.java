package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Subscription{

	@JsonProperty("started_at")
	private StartedAt startedAt;

	@JsonProperty("trial_ends_at")
	private TrialEndsAt trialEndsAt;

	@JsonProperty("ends_at")
	private Object endsAt;

	public void setStartedAt(StartedAt startedAt){
		this.startedAt = startedAt;
	}

	public StartedAt getStartedAt(){
		return startedAt;
	}

	public void setTrialEndsAt(TrialEndsAt trialEndsAt){
		this.trialEndsAt = trialEndsAt;
	}

	public TrialEndsAt getTrialEndsAt(){
		return trialEndsAt;
	}

	public void setEndsAt(Object endsAt){
		this.endsAt = endsAt;
	}

	public Object getEndsAt(){
		return endsAt;
	}
}