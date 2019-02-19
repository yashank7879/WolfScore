package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Shots {
    @JsonProperty("shots_on_goal")
    private Integer shotsOnGoal;
    @JsonProperty("shots_total")
    private Integer shotsTotal;
}