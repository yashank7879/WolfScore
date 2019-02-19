package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Meta {
    @JsonProperty("sports")
    private List<SportsItem> sports;
    @JsonProperty("subscription")
    private Subscription subscription;
    @JsonProperty("plan")
    private Plan plan;
}