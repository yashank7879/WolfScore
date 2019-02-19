package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class DataCornerItem {
    @JsonProperty("fixture_id")
    private Integer fixtureId;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("team_id")
    private Integer teamId;
    @JsonProperty("extra_minute")
    private String extraMinute;
    @JsonProperty("minute")
    private Integer minute;
}