package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Passing {
    @JsonProperty("passes")
    private Integer passes;
    @JsonProperty("key_passes")
    private String keyPasses;
    @JsonProperty("passes_accuracy")
    private Integer passesAccuracy;
    @JsonProperty("crosses_accuracy")
    private Integer crossesAccuracy;
    @JsonProperty("total_crosses")
    private Integer totalCrosses;
}