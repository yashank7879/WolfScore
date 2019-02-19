package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class OddsItem {
    @JsonProperty("total")
    private String total;
    @JsonProperty("winning")
    private Boolean winning;
    @JsonProperty("stop")
    private Boolean stop;
    @JsonProperty("probability")
    private String probability;
    @JsonProperty("dp3")
    private String dp3;
    @JsonProperty("handicap")
    private String handicap;
    @JsonProperty("last_update")
    private LastUpdate lastUpdate;
    @JsonProperty("american")
    private Integer american;
    @JsonProperty("factional")
    private String factional;
    @JsonProperty("label")
    private String label;
    @JsonProperty("value")
    private String value;
}