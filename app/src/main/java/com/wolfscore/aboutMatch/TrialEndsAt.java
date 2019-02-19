package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class TrialEndsAt {
    @JsonProperty("date")
    private String date;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("timezone_type")
    private Integer timezoneType;
}