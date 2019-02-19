package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class DataBenchItem {
    @JsonProperty("fixture_id")
    private Integer fixtureId;
    @JsonProperty("posx")
    private int posx;
    @JsonProperty("number")
    private Integer number;
    @JsonProperty("posy")
    private int posy;
    @JsonProperty("player_id")
    private Integer playerId;
    @JsonProperty("stats")
    private Stats stats;
    @JsonProperty("formation_position")
    private int formationPosition;
    @JsonProperty("team_id")
    private Integer teamId;
    @JsonProperty("player_name")
    private String playerName;
    @JsonProperty("position")
    private String position;
    @JsonProperty("captain")
    private Boolean captain;
    @JsonProperty("additional_position")
    private int additionalPosition;
}