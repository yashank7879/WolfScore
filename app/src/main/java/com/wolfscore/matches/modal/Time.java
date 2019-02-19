package com.wolfscore.matches.modal;

import java.io.Serializable;

/**
 * Created by mindiii on 5/2/19.
 */

public class Time implements Serializable {
    String status = "";
    String starting_at = "";
    String date_time = "";
    String date = "";
    String time = "";
    int timestamp;
    String timezone = "";
    int minute;
    int second;
    int added_time;
    int extra_minute;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStarting_at() {
        return starting_at;
    }

    public void setStarting_at(String starting_at) {
        this.starting_at = starting_at;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getAdded_time() {
        return added_time;
    }

    public void setAdded_time(int added_time) {
        this.added_time = added_time;
    }

    public int getExtra_minute() {
        return extra_minute;
    }

    public void setExtra_minute(int extra_minute) {
        this.extra_minute = extra_minute;
    }

    public int getInjury_time() {
        return injury_time;
    }

    public void setInjury_time(int injury_time) {
        this.injury_time = injury_time;
    }

    int injury_time;
}
