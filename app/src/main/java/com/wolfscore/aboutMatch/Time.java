package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Time {
    @JsonProperty("added_time")
    public Integer addedTime;
    @JsonProperty("starting_at")
    public StartingAt startingAt;
    @JsonProperty("injury_time")
    public Integer injuryTime;
    @JsonProperty("extra_minute")
    public String extraMinute;
    @JsonProperty("status")
    public String status;
    @JsonProperty("minute")
    public Integer minute;
    @JsonProperty("second")
    public String second;
}