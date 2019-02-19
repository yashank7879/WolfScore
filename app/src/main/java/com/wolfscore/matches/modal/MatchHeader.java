package com.wolfscore.matches.modal;

import java.io.Serializable;

public class MatchHeader implements Serializable {
    int id;
    String name;
    int match_id;

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
