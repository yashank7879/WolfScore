package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Subscription {
    @JsonProperty("started_at")
    private StartedAt startedAt;
    @JsonProperty("trial_ends_at")
    private TrialEndsAt trialEndsAt;
    @JsonProperty("ends_at")
    private String endsAt;
}