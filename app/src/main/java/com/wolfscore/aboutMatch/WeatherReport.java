package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class WeatherReport {
    @JsonProperty("code")
    private String code;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("temperature")
    private Temperature temperature;
    @JsonProperty("humidity")
    private String humidity;
    @JsonProperty("clouds")
    private String clouds;
    @JsonProperty("type")
    private String type;
    @JsonProperty("wind")
    private Wind wind;
}