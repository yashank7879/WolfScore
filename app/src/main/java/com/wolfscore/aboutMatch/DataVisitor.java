package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class DataVisitor {
    @JsonProperty("twitter")
    private String twitter;
    @JsonProperty("logo_path")
    private String logoPath;
    @JsonProperty("name")
    private String name;
    @JsonProperty("founded")
    private Integer founded;
    @JsonProperty("legacy_id")
    private String legacyId;
    @JsonProperty("national_team")
    private Boolean nationalTeam;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("country_id")
    private Integer countryId;
    @JsonProperty("venue_id")
    private Integer venueId;
    @JsonProperty("short_code")
    private String shortCode;
}