package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Standings {
    @JsonProperty("visitorteam_position")
    public Integer visitorteamPosition;
    @JsonProperty("localteam_position")
    public Integer localteamPosition;
}