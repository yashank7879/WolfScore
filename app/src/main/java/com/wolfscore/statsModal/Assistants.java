package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;
//import javax.annotation.Generated;

//@Generated("com.robohorse.robopojogenerator")
public class Assistants{

	@JsonProperty("fourth_official_id")
	private int fourthOfficialId;

	@JsonProperty("second_assistant_id")
	private int secondAssistantId;

	@JsonProperty("first_assistant_id")
	private int firstAssistantId;

	public void setFourthOfficialId(int fourthOfficialId){
		this.fourthOfficialId = fourthOfficialId;
	}

	public int getFourthOfficialId(){
		return fourthOfficialId;
	}

	public void setSecondAssistantId(int secondAssistantId){
		this.secondAssistantId = secondAssistantId;
	}

	public int getSecondAssistantId(){
		return secondAssistantId;
	}

	public void setFirstAssistantId(int firstAssistantId){
		this.firstAssistantId = firstAssistantId;
	}

	public int getFirstAssistantId(){
		return firstAssistantId;
	}
}