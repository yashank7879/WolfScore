package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Odds {
    @JsonProperty("data")
    private List<DataItem> data;
}