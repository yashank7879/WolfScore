package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Goals {
    @JsonProperty("conceded")
    private Integer conceded;
    @JsonProperty("assists")
    private Integer assists;
    @JsonProperty("scored")
    private Integer scored;
}