package com.wolfscore.matches.modal;

import java.io.Serializable;

public class LocalTeam implements Serializable {
    int id;
    int legacy_id;
    String name="";
    String short_code="";
    String twitter="";
    int country_id;
    Boolean national_team=false;
    int founded;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTeam() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    String logo_path="";
    int venue_id;

    public LocalTeam(int id, int legacy_id, String name, String short_code, String twitter, int country_id, Boolean national_team, int founded, String logo_path, int venue_id) {
        this.id = id;
        this.legacy_id = legacy_id;
        this.name = name;
        this.short_code = short_code;
        this.twitter = twitter;
        this.country_id = country_id;
        this.national_team = national_team;
        this.founded = founded;
        this.logo_path = logo_path;
        this.venue_id = venue_id;
    }
}
