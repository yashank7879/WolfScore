package com.wolfscore.statsModal;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta{

	@JsonProperty("sports")
	private List<SportsItem> sports;

	@JsonProperty("subscription")
	private Subscription subscription;

	@JsonProperty("plan")
	private Plan plan;

	public void setSports(List<SportsItem> sports){
		this.sports = sports;
	}

	public List<SportsItem> getSports(){
		return sports;
	}

	public void setSubscription(Subscription subscription){
		this.subscription = subscription;
	}

	public Subscription getSubscription(){
		return subscription;
	}

	public void setPlan(Plan plan){
		this.plan = plan;
	}

	public Plan getPlan(){
		return plan;
	}
}