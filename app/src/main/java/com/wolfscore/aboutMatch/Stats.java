package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Stats {
    @JsonProperty("fouls")
    private Fouls fouls;
    @JsonProperty("other")
    private Other other;
    @JsonProperty("cards")
    public Cards cards;
    @JsonProperty("dribbles")
    private Dribbles dribbles;
    @JsonProperty("passing")
    private Passing passing;
    @JsonProperty("shots")
    private Shots shots;
    @JsonProperty("duels")
    private Duels duels;
    @JsonProperty("goals")
    private Goals goals;
}