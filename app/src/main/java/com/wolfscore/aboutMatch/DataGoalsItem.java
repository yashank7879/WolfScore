package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class DataGoalsItem {
    @JsonProperty("fixture_id")
    private Integer fixtureId;
    @JsonProperty("result")
    private String result;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("player_assist_id")
    private Integer playerAssistId;
    @JsonProperty("player_id")
    private Integer playerId;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("team_id")
    private String teamId;
    @JsonProperty("player_name")
    private String playerName;
    @JsonProperty("player_assist_name")
    private String playerAssistName;
    @JsonProperty("extra_minute")
    private Integer extraMinute;
    @JsonProperty("type")
    private String type;
    @JsonProperty("minute")
    private Integer minute;
}