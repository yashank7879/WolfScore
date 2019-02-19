package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class StartingAt {
    @JsonProperty("date")
    public String date;
    @JsonProperty("date_time")
    public String dateTime;
    @JsonProperty("timezone")
    public String timezone;
    @JsonProperty("time")
    public String time;
    @JsonProperty("timestamp")
    public Integer timestamp;
}