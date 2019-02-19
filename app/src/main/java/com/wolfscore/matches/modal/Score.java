package com.wolfscore.matches.modal;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by mindiii on 5/2/19.
 */

public class Score implements Serializable {
       int localteam_score;
    int visitorteam_score;
    int localteam_pen_score;
    int visitorteam_pen_score;
    int ht_score;
    int ft_score;
    int et_score;

    public int getLocalteam_score() {
        return localteam_score;
    }

    public void setLocalteam_score(int localteam_score) {
        this.localteam_score = localteam_score;
    }

    public int getVisitorteam_score() {
        return visitorteam_score;
    }

    public void setVisitorteam_score(int visitorteam_score) {
        this.visitorteam_score = visitorteam_score;
    }

    public int getLocalteam_pen_score() {
        return localteam_pen_score;
    }

    public void setLocalteam_pen_score(int localteam_pen_score) {
        this.localteam_pen_score = localteam_pen_score;
    }

    public int getVisitorteam_pen_score() {
        return visitorteam_pen_score;
    }

    public void setVisitorteam_pen_score(int visitorteam_pen_score) {
        this.visitorteam_pen_score = visitorteam_pen_score;
    }

    public int getHt_score() {
        return ht_score;
    }

    public void setHt_score(int ht_score) {
        this.ht_score = ht_score;
    }

    public int getFt_score() {
        return ft_score;
    }

    public void setFt_score(int ft_score) {
        this.ft_score = ft_score;
    }

    public int getEt_score() {
        return et_score;
    }

    public void setEt_score(int et_score) {
        this.et_score = et_score;
    }
}
