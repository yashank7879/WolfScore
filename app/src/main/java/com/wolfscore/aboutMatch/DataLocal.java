package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class DataLocal {
    @JsonProperty("twitter")
    public String twitter;
    @JsonProperty("logo_path")
    public String logoPath;
    @JsonProperty("name")
    public String name;
    @JsonProperty("founded")
    public Integer founded;
    @JsonProperty("legacy_id")
    public Integer legacyId;
    @JsonProperty("national_team")
    public Boolean nationalTeam;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("country_id")
    public Integer countryId;
    @JsonProperty("venue_id")
    public Integer venueId;
    @JsonProperty("short_code")
    public String shortCode;
}