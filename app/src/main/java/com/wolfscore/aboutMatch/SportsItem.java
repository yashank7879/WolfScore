package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class SportsItem {
    @JsonProperty("current")
    private Boolean current;
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private Integer id;
}