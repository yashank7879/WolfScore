package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Scores {
    @JsonProperty("ht_score")
    private String htScore;
    @JsonProperty("localteam_score")
    private Integer localteamScore;
    @JsonProperty("localteam_pen_score")
    private String localteamPenScore;
    @JsonProperty("visitorteam_score")
    private Integer visitorteamScore;
    @JsonProperty("ft_score")
    private String ftScore;
    @JsonProperty("et_score")
    private String etScore;
    @JsonProperty("visitorteam_pen_score")
    private String visitorteamPenScore;
}