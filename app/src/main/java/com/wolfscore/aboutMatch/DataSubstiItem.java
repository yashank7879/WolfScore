package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class DataSubstiItem {
    @JsonProperty("fixture_id")
    private Integer fixtureId;
    @JsonProperty("player_in_id")
    private Integer playerInId;
    @JsonProperty("player_out_id")
    private Integer playerOutId;
    @JsonProperty("player_out_name")
    private String playerOutName;
    @JsonProperty("injuried")
    private String injuried;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("team_id")
    private String teamId;
    @JsonProperty("extra_minute")
    private String extraMinute;
    @JsonProperty("type")
    private String type;
    @JsonProperty("player_in_name")
    private String playerInName;
    @JsonProperty("minute")
    private Integer minute;
}