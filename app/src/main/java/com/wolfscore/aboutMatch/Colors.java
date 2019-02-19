package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Colors {
    @JsonProperty("localteam")
    private Localteam localteam;
    @JsonProperty("visitorteam")
    private Visitorteam visitorteam;
}