package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Temperature {
    @JsonProperty("temp")
    private Double temp;
    @JsonProperty("unit")
    private String unit;
}