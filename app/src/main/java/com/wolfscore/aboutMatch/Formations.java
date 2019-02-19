package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Formations {
    private String visitorteam_formation;
    private String localteam_formation;


    public String getVisitorteam_formation() {
        return visitorteam_formation;
    }

    public void setVisitorteam_formation(String visitorteam_formation) {
        this.visitorteam_formation = visitorteam_formation;
    }

    public String getLocalteam_formation() {
        return localteam_formation;
    }

    public void setLocalteam_formation(String localteam_formation) {
        this.localteam_formation = localteam_formation;
    }
}