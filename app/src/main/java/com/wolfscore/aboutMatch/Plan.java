package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Plan {
    @JsonProperty("price")
    private String price;
    @JsonProperty("request_limit")
    private String requestLimit;
    @JsonProperty("name")
    private String name;
}