package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class DataItem {
    @JsonProperty("fixture_id")
    public Integer fixtureId;
    @JsonProperty("fouls")
    public Integer fouls;
    @JsonProperty("team_id")
    public Integer teamId;
    @JsonProperty("ball_safe")
    public Integer ballSafe;
    @JsonProperty("goal_kick")
    public String goalKick;
    @JsonProperty("corners")
    public Integer corners;
    @JsonProperty("redcards")
    public Integer redcards;
    @JsonProperty("goal_attempts")
    public Integer goalAttempts;
    @JsonProperty("throw_in")
    public String throwIn;
    @JsonProperty("possessiontime")
    public Integer possessiontime;
    @JsonProperty("passes")
    public Passes passes;
    @JsonProperty("saves")
    public Integer saves;
    @JsonProperty("substitutions")
    public Integer substitutions;
    @JsonProperty("attacks")
    public Attacks attacks;
    @JsonProperty("yellowcards")
    public Integer yellowcards;
    @JsonProperty("shots")
    public Shots shots;
    @JsonProperty("offsides")
    public Integer offsides;
    @JsonProperty("free_kick")
    public String freeKick;



    ///////////////
    public Long id;
    public String type;
    public Integer fixture_id;
    public Integer player_id;
    public String player_name;
    public Integer related_player_id;
    public String related_player_name;
    public Integer  minute;
    public Integer extra_minute;
    public String reason;
    public Boolean injuried;
    public String result;
}