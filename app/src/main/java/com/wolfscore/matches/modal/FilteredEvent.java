package com.wolfscore.matches.modal;

import java.io.Serializable;

/**
 * Created by mindiii on 7/2/19.
 */

public class FilteredEvent implements Serializable{
    String league_id="";

    public String getLeague_id() {
        return league_id;
    }

    public void setLeague_id(String league_id) {
        this.league_id = league_id;
    }

    public FilteredEvent() {

    }
    public FilteredEvent(String league_id) {

        this.league_id = league_id;
    }
}
