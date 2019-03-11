package com.wolfscore.matches.modal;

/**
 * Created by mindiii on 26/2/19.
 */

public class CommentryDTO {

    int fixture_id = 0;
    boolean important = false;
    int order = 0;
    boolean goal = false;

    String location="",date="";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFixture_id() {
        return fixture_id;
    }

    public void setFixture_id(int fixture_id) {
        this.fixture_id = fixture_id;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isGoal() {
        return goal;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getExtra_minute() {
        return extra_minute;
    }

    public void setExtra_minute(int extra_minute) {
        this.extra_minute = extra_minute;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    int minute = 0;
    int extra_minute = 0;
    String comment = "";
}
