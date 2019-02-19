package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Attacks {
    @JsonProperty("dangerous_attacks")
    private Integer dangerousAttacks;
    @JsonProperty("attacks")
    private Integer attacks;
}