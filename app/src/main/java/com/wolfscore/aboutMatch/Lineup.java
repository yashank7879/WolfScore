package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Lineup {
    private List<com.wolfscore.aboutMatch.DataLineUpItem> data;

    public List<com.wolfscore.aboutMatch.DataLineUpItem> getData() {
        return data;
    }

    public void setData(List<com.wolfscore.aboutMatch.DataLineUpItem> data) {
        this.data = data;
    }
}