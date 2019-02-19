package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Wind {
    @JsonProperty("degree")
    private Integer degree;
    @JsonProperty("speed")
    private String speed;
}