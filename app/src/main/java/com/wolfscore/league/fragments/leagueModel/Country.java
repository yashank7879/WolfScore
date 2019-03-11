package com.wolfscore.league.fragments.leagueModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mindiii on 27/2/19.
 */

public class Country implements Serializable{

    private int country_id = 0;

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_flag() {
        return country_flag;
    }

    public void setCountry_flag(String country_flag) {
        this.country_flag = country_flag;
    }

    public ArrayList<League> getLeagueArrayList() {
        return leagueArrayList;
    }

    public void setLeagueArrayList(ArrayList<League> leagueArrayList) {
        this.leagueArrayList = leagueArrayList;
    }

    private String country_name = "";
    private String country_flag = "";
    private ArrayList<League>  leagueArrayList=new ArrayList<>();

    public static class League implements Serializable{
        private int league_id = 0;
        private int country_id = 0;
        private String league_name = "";
        private String league_flag = "";
        private int is_cup = 0;

        public int getLeague_id() {
            return league_id;
        }

        public void setLeague_id(int league_id) {
            this.league_id = league_id;
        }

        public int getCountry_id() {
            return country_id;
        }

        public void setCountry_id(int country_id) {
            this.country_id = country_id;
        }

        public String getLeague_name() {
            return league_name;
        }

        public void setLeague_name(String league_name) {
            this.league_name = league_name;
        }

        public String getLeague_flag() {
            return league_flag;
        }

        public void setLeague_flag(String league_flag) {
            this.league_flag = league_flag;
        }

        public int getIs_cup() {
            return is_cup;
        }

        public void setIs_cup(int is_cup) {
            this.is_cup = is_cup;
        }

        public int getIs_selected() {
            return is_selected;
        }

        public void setIs_selected(int is_selected) {
            this.is_selected = is_selected;
        }

        private int is_selected = 0;

        public int getIs_favorite() {
            return is_favorite;
        }

        public void setIs_favorite(int is_favorite) {
            this.is_favorite = is_favorite;
        }

        private int is_favorite = 0;

    }


}
