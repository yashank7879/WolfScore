package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class DataLineUpItem {
    @JsonProperty("fixture_id")
    public Integer fixtureId;
    @JsonProperty("formation_position")
    public Integer formationPosition;
    @JsonProperty("team_id")
    public Integer teamId;
    @JsonProperty("captain")
    public Boolean captain;
    @JsonProperty("posx")
    public Integer posx;
    @JsonProperty("number")
    public Integer number;
    @JsonProperty("posy")
    public Integer posy;
    @JsonProperty("player_id")
    public Integer playerId;
    @JsonProperty("stats")
    public Stats stats;
    @JsonProperty("player_name")
    public String playerName;
    @JsonProperty("position")
    public String position;
    @JsonProperty("player")
    public Player player;
}