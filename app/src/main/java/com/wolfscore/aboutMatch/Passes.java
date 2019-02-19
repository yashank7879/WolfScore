package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Passes {
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("accurate")
    private Integer accurate;
    @JsonProperty("percentage")
    private Integer percentage;
}