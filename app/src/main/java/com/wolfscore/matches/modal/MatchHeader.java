package com.wolfscore.matches.modal;

public class MatchHeader {
    int id;
    String name;
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
