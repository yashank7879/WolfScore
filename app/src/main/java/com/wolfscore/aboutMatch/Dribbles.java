package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Dribbles {
    @JsonProperty("success")
    private Integer success;
    @JsonProperty("dribbled_past")
    private Integer dribbledPast;
    @JsonProperty("attempts")
    private Integer attempts;
}