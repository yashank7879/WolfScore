package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class DataSubstiItem {
    @JsonProperty("fixture_id")
    public Integer fixtureId;
    @JsonProperty("player_in_id")
    public Integer playerInId;
    @JsonProperty("player_out_id")
    public Integer playerOutId;
    @JsonProperty("player_out_name")
    public String playerOutName;
    @JsonProperty("injuried")
    public String injuried;
    @JsonProperty("id")
    public Long id;
    @JsonProperty("team_id")
    public String teamId;
    @JsonProperty("extra_minute")
    public String extraMinute;
    @JsonProperty("type")
    public String type;
    @JsonProperty("player_in_name")
    public String playerInName;
    @JsonProperty("minute")
    public Integer minute;
}