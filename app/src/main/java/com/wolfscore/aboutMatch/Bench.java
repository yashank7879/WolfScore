package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Bench {
    @JsonProperty("data")
    public List<DataLineUpItem> dataBench;
}