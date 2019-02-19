package com.wolfscore.matches.modal;

import java.io.Serializable;

public class Matches implements Serializable{
    private MatchHeader matchHeader;
    private MatchCell matchCell;
    private LocalTeam localTeam;
    private VisitorTeam visitorTeam;
    private Time time;
    private Score score;

    public Matches(LocalTeam localTeam, VisitorTeam visitorTeam, Time time, Score score) {

        this.localTeam = localTeam;
        this.visitorTeam = visitorTeam;
        this.time = time;
        this.score = score;
    }

    public VisitorTeam getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(VisitorTeam visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public Time getTime() {
        return time;
    }

    public Score getScore() {
        return score;
    }

    public Matches(MatchHeader matchHeader, LocalTeam localTeam, VisitorTeam visitorTeam, Time time, Score score) {
        this.matchHeader = matchHeader;
        this.localTeam = localTeam;
        this.visitorTeam = visitorTeam;

        this.time = time;
        this.score = score;
    }

    public Matches(MatchHeader matchHeader, LocalTeam localTeam, VisitorTeam visitorTeam) {
        this.matchHeader = matchHeader;

        this.localTeam = localTeam;
        this.visitorTeam = visitorTeam;
    }

    public MatchHeader getMatchHeader() {
        return matchHeader;
    }

    public LocalTeam getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(LocalTeam localTeam) {
        this.localTeam = localTeam;
    }

    public void setMatchHeader(MatchHeader matchHeader) {
        this.matchHeader = matchHeader;
    }

    public MatchCell getMatchCell() {
        return matchCell;
    }

    public void setMatchCell(MatchCell matchCell) {
        this.matchCell = matchCell;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    String str="";
    public Matches(MatchHeader matchHeader)
    {
        this.matchHeader = matchHeader;
    }
    public Matches(LocalTeam localTeam, MatchHeader matchHeader) {
        this.localTeam=localTeam;
        this.matchHeader = matchHeader;
    }
    public Matches(String str,  MatchHeader matchHeader) {
        this.str=str;
        this.matchHeader = matchHeader;
    }
    public String getHeaderName() {
        return matchHeader.getName();
    }
    public int getHeaderId() {
        return matchHeader.getId();
    }
}



