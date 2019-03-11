package com.wolfscore.matches.modal;

import java.io.Serializable;

public class MatchHeader implements Serializable {
    private int id=0;
    private String name="",countryName="";
    private int match_id=0,season_id=0;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public MatchHeader() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public MatchHeader(int id, String name) {
        this.id = id;

        this.name = name;

    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
