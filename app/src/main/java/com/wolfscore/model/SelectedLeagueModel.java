package com.wolfscore.model;

/**
 * Created by mindiii on 2/6/19.
 */

public class SelectedLeagueModel {
    private String matchDate ="";
    private String selectedLegue="";

    public SelectedLeagueModel(String s) {
        this.selectedLegue = s;
    }

    public String getSelectedLegue() {
        return selectedLegue;
    }

    public void setSelectedLegue(String selectedLegue) {
        this.selectedLegue = selectedLegue;
    }
  /*  public SelectedLeagueModel(String value) {
        this.selectedLegue = value;
    }*/

    @Override
    public String toString() {
        return selectedLegue;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }
}
