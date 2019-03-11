package com.wolfscore.responce;

import java.util.List;

/**
 * Created by mindiii on 2/5/19.
 */

public class GetLeagueResponce {
    /**
     * status : success
     * message : League list
     * data : {"total_records":"858","league_list":[{"league_id":"8","country_id":"462","league_name":"Premier League","league_flag":"https://cdn.sportmonks.com/images/soccer/leagues/8.png","is_cup":"0","current_season_id":"12962","current_round_id":"147730","current_stage_id":"0","live_standings":"1","is_selected":"1","country_name":"England"},{"league_id":"860","country_id":"1424","league_name":"Botola Pro","league_flag":"http://dev.wolfscore.info/uploads/placeholders/ws_general_placeholder.png","is_cup":"0","current_season_id":"13108","current_round_id":"150655","current_stage_id":"0","live_standings":"1","is_selected":"0","country_name":"Morocco"},{"league_id":"507","country_id":"1161","league_name":"Scottish Cup","league_flag":"http://dev.wolfscore.info/uploads/placeholders/ws_general_placeholder.png","is_cup":"1","current_season_id":"13111","current_round_id":"0","current_stage_id":"0","live_standings":"0","is_selected":"0","country_name":"Scotland"}]}
     */

    private String status;
    private String message;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * total_records : 858
         * league_list : [{"league_id":"8","country_id":"462","league_name":"Premier League","league_flag":"https://cdn.sportmonks.com/images/soccer/leagues/8.png","is_cup":"0","current_season_id":"12962","current_round_id":"147730","current_stage_id":"0","live_standings":"1","is_selected":"1","country_name":"England"},{"league_id":"860","country_id":"1424","league_name":"Botola Pro","league_flag":"http://dev.wolfscore.info/uploads/placeholders/ws_general_placeholder.png","is_cup":"0","current_season_id":"13108","current_round_id":"150655","current_stage_id":"0","live_standings":"1","is_selected":"0","country_name":"Morocco"},{"league_id":"507","country_id":"1161","league_name":"Scottish Cup","league_flag":"http://dev.wolfscore.info/uploads/placeholders/ws_general_placeholder.png","is_cup":"1","current_season_id":"13111","current_round_id":"0","current_stage_id":"0","live_standings":"0","is_selected":"0","country_name":"Scotland"}]
         */

        private String total_records;
        private List<LeagueListBean> league_list;

        public List<LeagueListBean> getPopular_league() {
            return popular_league;
        }

        public void setPopular_league(List<LeagueListBean> popular_league) {
            this.popular_league = popular_league;
        }

        private List<LeagueListBean> popular_league;

        public String getTotal_records() {
            return total_records;
        }

        public void setTotal_records(String total_records) {
            this.total_records = total_records;
        }

        public List<LeagueListBean> getLeague_list() {
            return league_list;
        }

        public void setLeague_list(List<LeagueListBean> league_list) {
            this.league_list = league_list;
        }

        public static class LeagueListBean {
            /**
             * league_id : 8
             * country_id : 462
             * league_name : Premier League
             * league_flag : https://cdn.sportmonks.com/images/soccer/leagues/8.png
             * is_cup : 0
             * current_season_id : 12962
             * current_round_id : 147730
             * current_stage_id : 0
             * live_standings : 1
             * is_selected : 1
             * country_name : England
             */

            private String league_id;
            private String country_id;
            private String league_name;
            private String league_flag;

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            private String is_cup;
            private String current_season_id;
            private String current_round_id;
            private String current_stage_id;
            private String live_standings;

            public String getIs_favorite() {
                return is_favorite;
            }

            public void setIs_favorite(String is_favorite) {
                this.is_favorite = is_favorite;
            }

            private String is_selected;
            private String is_favorite;
            private String country_name;

            boolean isChecked=false;

            public String getLeague_id() {
                return league_id;
            }

            public void setLeague_id(String league_id) {
                this.league_id = league_id;
            }

            public String getCountry_id() {
                return country_id;
            }

            public void setCountry_id(String country_id) {
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

            public String getIs_cup() {
                return is_cup;
            }

            public void setIs_cup(String is_cup) {
                this.is_cup = is_cup;
            }

            public String getCurrent_season_id() {
                return current_season_id;
            }

            public void setCurrent_season_id(String current_season_id) {
                this.current_season_id = current_season_id;
            }

            public String getCurrent_round_id() {
                return current_round_id;
            }

            public void setCurrent_round_id(String current_round_id) {
                this.current_round_id = current_round_id;
            }

            public String getCurrent_stage_id() {
                return current_stage_id;
            }

            public void setCurrent_stage_id(String current_stage_id) {
                this.current_stage_id = current_stage_id;
            }

            public String getLive_standings() {
                return live_standings;
            }

            public void setLive_standings(String live_standings) {
                this.live_standings = live_standings;
            }

            public String getIs_selected() {
                return is_selected;
            }

            public void setIs_selected(String is_selected) {
                this.is_selected = is_selected;
            }

            public String getCountry_name() {
                return country_name;
            }

            public void setCountry_name(String country_name) {
                this.country_name = country_name;
            }
        }
    }
}
