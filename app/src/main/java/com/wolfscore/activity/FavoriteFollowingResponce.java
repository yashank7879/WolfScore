package com.wolfscore.activity;

import java.util.List;

/**
 * Created by mindiii on 1/30/19.
 */

public class FavoriteFollowingResponce {
    /**
     * status : success
     * message : Favorite list
     * data : {"player_list":{"total_records":"2","player_list":[{"id":"2","player_id":"26722","team_id":"85","common_name":"S. Andersen","full_name":"Stephan Maigaard Andersen","first_name":"Stephan Maigaard","last_name":"Andersen","player_image":"https://cdn.sportmonks.com/images/soccer/players/2/26722.png","team_name":"København","country_name":"Denmark","team_logo":"https://cdn.sportmonks.com/images/soccer/teams/21/85.png","country_flag":"<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"370\" height=\"280\"><path fill=\"#c60c30\" d=\"M0 0h370v280h-370z\"/><path fill=\"#fff\" d=\"M120 0h40v280h-40zM0 120h370v40h-370z\"/><\/svg>\n"},{"id":"6","player_id":"24519","team_id":"85","common_name":"N. Boilesen","full_name":"Nicolai Møller Boilesen","first_name":"Nicolai","last_name":"Møller Boilesen","player_image":"https://cdn.sportmonks.com/images/soccer/players/7/24519.png","team_name":"København","country_name":"Denmark","team_logo":"https://cdn.sportmonks.com/images/soccer/teams/21/85.png","country_flag":"<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"370\" height=\"280\"><path fill=\"#c60c30\" d=\"M0 0h370v280h-370z\"/><path fill=\"#fff\" d=\"M120 0h40v280h-40zM0 120h370v40h-370z\"/><\/svg>\n"}]},"team_list":{"total_records":"3","team_list":[{"id":"1","team_id":"85","name":"København","country_id":"320","national_team":"0","founded":"1992","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/21/85.png","venue_id":"5660","uefaranking_postion":"43","created_on":"2018-12-28 06:56:08"},{"id":"2","team_id":"86","name":"Silkeborg","country_id":"320","national_team":"0","founded":"1917","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/22/86.png","venue_id":"85371","uefaranking_postion":"43","created_on":"2018-12-28 06:56:08"},{"id":"21","team_id":"53","name":"Celtic","country_id":"1161","national_team":"0","founded":"1888","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/21/53.png","venue_id":"8909","uefaranking_postion":"47","created_on":"2018-12-28 07:06:38"}]}}
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
         * player_list : {"total_records":"2","player_list":[{"id":"2","player_id":"26722","team_id":"85","common_name":"S. Andersen","full_name":"Stephan Maigaard Andersen","first_name":"Stephan Maigaard","last_name":"Andersen","player_image":"https://cdn.sportmonks.com/images/soccer/players/2/26722.png","team_name":"København","country_name":"Denmark","team_logo":"https://cdn.sportmonks.com/images/soccer/teams/21/85.png","country_flag":"<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"370\" height=\"280\"><path fill=\"#c60c30\" d=\"M0 0h370v280h-370z\"/><path fill=\"#fff\" d=\"M120 0h40v280h-40zM0 120h370v40h-370z\"/><\/svg>\n"},{"id":"6","player_id":"24519","team_id":"85","common_name":"N. Boilesen","full_name":"Nicolai Møller Boilesen","first_name":"Nicolai","last_name":"Møller Boilesen","player_image":"https://cdn.sportmonks.com/images/soccer/players/7/24519.png","team_name":"København","country_name":"Denmark","team_logo":"https://cdn.sportmonks.com/images/soccer/teams/21/85.png","country_flag":"<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"370\" height=\"280\"><path fill=\"#c60c30\" d=\"M0 0h370v280h-370z\"/><path fill=\"#fff\" d=\"M120 0h40v280h-40zM0 120h370v40h-370z\"/><\/svg>\n"}]}
         * team_list : {"total_records":"3","team_list":[{"id":"1","team_id":"85","name":"København","country_id":"320","national_team":"0","founded":"1992","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/21/85.png","venue_id":"5660","uefaranking_postion":"43","created_on":"2018-12-28 06:56:08"},{"id":"2","team_id":"86","name":"Silkeborg","country_id":"320","national_team":"0","founded":"1917","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/22/86.png","venue_id":"85371","uefaranking_postion":"43","created_on":"2018-12-28 06:56:08"},{"id":"21","team_id":"53","name":"Celtic","country_id":"1161","national_team":"0","founded":"1888","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/21/53.png","venue_id":"8909","uefaranking_postion":"47","created_on":"2018-12-28 07:06:38"}]}
         */

        private PlayerListBeanX player_list;
        private TeamListBeanX team_list;

        public PlayerListBeanX getPlayer_list() {
            return player_list;
        }

        public void setPlayer_list(PlayerListBeanX player_list) {
            this.player_list = player_list;
        }

        public TeamListBeanX getTeam_list() {
            return team_list;
        }

        public void setTeam_list(TeamListBeanX team_list) {
            this.team_list = team_list;
        }

        public static class PlayerListBeanX {
            /**
             * total_records : 2
             * player_list : [{"id":"2","player_id":"26722","team_id":"85","common_name":"S. Andersen","full_name":"Stephan Maigaard Andersen","first_name":"Stephan Maigaard","last_name":"Andersen","player_image":"https://cdn.sportmonks.com/images/soccer/players/2/26722.png","team_name":"København","country_name":"Denmark","team_logo":"https://cdn.sportmonks.com/images/soccer/teams/21/85.png","country_flag":"<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"370\" height=\"280\"><path fill=\"#c60c30\" d=\"M0 0h370v280h-370z\"/><path fill=\"#fff\" d=\"M120 0h40v280h-40zM0 120h370v40h-370z\"/><\/svg>\n"},{"id":"6","player_id":"24519","team_id":"85","common_name":"N. Boilesen","full_name":"Nicolai Møller Boilesen","first_name":"Nicolai","last_name":"Møller Boilesen","player_image":"https://cdn.sportmonks.com/images/soccer/players/7/24519.png","team_name":"København","country_name":"Denmark","team_logo":"https://cdn.sportmonks.com/images/soccer/teams/21/85.png","country_flag":"<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"370\" height=\"280\"><path fill=\"#c60c30\" d=\"M0 0h370v280h-370z\"/><path fill=\"#fff\" d=\"M120 0h40v280h-40zM0 120h370v40h-370z\"/><\/svg>\n"}]
             */

            private String total_records;
            private List<PlayerListBean> player_list;

            public String getTotal_records() {
                return total_records;
            }

            public void setTotal_records(String total_records) {
                this.total_records = total_records;
            }

            public List<PlayerListBean> getPlayer_list() {
                return player_list;
            }

            public void setPlayer_list(List<PlayerListBean> player_list) {
                this.player_list = player_list;
            }

            public static class PlayerListBean {
                /**
                 * id : 2
                 * player_id : 26722
                 * team_id : 85
                 * common_name : S. Andersen
                 * full_name : Stephan Maigaard Andersen
                 * first_name : Stephan Maigaard
                 * last_name : Andersen
                 * player_image : https://cdn.sportmonks.com/images/soccer/players/2/26722.png
                 * team_name : København
                 * country_name : Denmark
                 * team_logo : https://cdn.sportmonks.com/images/soccer/teams/21/85.png
                 * country_flag : <svg xmlns="http://www.w3.org/2000/svg" width="370" height="280"><path fill="#c60c30" d="M0 0h370v280h-370z"/><path fill="#fff" d="M120 0h40v280h-40zM0 120h370v40h-370z"/></svg>

                 */

                private String id;
                private String player_id;
                private String team_id;
                private String common_name;
                private String full_name;
                private String first_name;
                private String last_name;
                private String player_image;
                private String team_name;
                private String country_name;
                private String team_logo;
                private String country_flag;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getPlayer_id() {
                    return player_id;
                }

                public void setPlayer_id(String player_id) {
                    this.player_id = player_id;
                }

                public String getTeam_id() {
                    return team_id;
                }

                public void setTeam_id(String team_id) {
                    this.team_id = team_id;
                }

                public String getCommon_name() {
                    return common_name;
                }

                public void setCommon_name(String common_name) {
                    this.common_name = common_name;
                }

                public String getFull_name() {
                    return full_name;
                }

                public void setFull_name(String full_name) {
                    this.full_name = full_name;
                }

                public String getFirst_name() {
                    return first_name;
                }

                public void setFirst_name(String first_name) {
                    this.first_name = first_name;
                }

                public String getLast_name() {
                    return last_name;
                }

                public void setLast_name(String last_name) {
                    this.last_name = last_name;
                }

                public String getPlayer_image() {
                    return player_image;
                }

                public void setPlayer_image(String player_image) {
                    this.player_image = player_image;
                }

                public String getTeam_name() {
                    return team_name;
                }

                public void setTeam_name(String team_name) {
                    this.team_name = team_name;
                }

                public String getCountry_name() {
                    return country_name;
                }

                public void setCountry_name(String country_name) {
                    this.country_name = country_name;
                }

                public String getTeam_logo() {
                    return team_logo;
                }

                public void setTeam_logo(String team_logo) {
                    this.team_logo = team_logo;
                }

                public String getCountry_flag() {
                    return country_flag;
                }

                public void setCountry_flag(String country_flag) {
                    this.country_flag = country_flag;
                }
            }
        }

        public static class TeamListBeanX {
            /**
             * total_records : 3
             * team_list : [{"id":"1","team_id":"85","name":"København","country_id":"320","national_team":"0","founded":"1992","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/21/85.png","venue_id":"5660","uefaranking_postion":"43","created_on":"2018-12-28 06:56:08"},{"id":"2","team_id":"86","name":"Silkeborg","country_id":"320","national_team":"0","founded":"1917","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/22/86.png","venue_id":"85371","uefaranking_postion":"43","created_on":"2018-12-28 06:56:08"},{"id":"21","team_id":"53","name":"Celtic","country_id":"1161","national_team":"0","founded":"1888","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/21/53.png","venue_id":"8909","uefaranking_postion":"47","created_on":"2018-12-28 07:06:38"}]
             */

            private String total_records;
            private List<TeamListBean> team_list;

            public String getTotal_records() {
                return total_records;
            }

            public void setTotal_records(String total_records) {
                this.total_records = total_records;
            }

            public List<TeamListBean> getTeam_list() {
                return team_list;
            }

            public void setTeam_list(List<TeamListBean> team_list) {
                this.team_list = team_list;
            }

            public static class TeamListBean {
                /**
                 * id : 1
                 * team_id : 85
                 * name : København
                 * country_id : 320
                 * national_team : 0
                 * founded : 1992
                 * logo_path : https://cdn.sportmonks.com/images/soccer/teams/21/85.png
                 * venue_id : 5660
                 * uefaranking_postion : 43
                 * created_on : 2018-12-28 06:56:08
                 */

                private String id;
                private String team_id;
                private String name;
                private String country_id;
                private String national_team;
                private String founded;
                private String logo_path;
                private String venue_id;
                private String uefaranking_postion;
                private String created_on;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTeam_id() {
                    return team_id;
                }

                public void setTeam_id(String team_id) {
                    this.team_id = team_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCountry_id() {
                    return country_id;
                }

                public void setCountry_id(String country_id) {
                    this.country_id = country_id;
                }

                public String getNational_team() {
                    return national_team;
                }

                public void setNational_team(String national_team) {
                    this.national_team = national_team;
                }

                public String getFounded() {
                    return founded;
                }

                public void setFounded(String founded) {
                    this.founded = founded;
                }

                public String getLogo_path() {
                    return logo_path;
                }

                public void setLogo_path(String logo_path) {
                    this.logo_path = logo_path;
                }

                public String getVenue_id() {
                    return venue_id;
                }

                public void setVenue_id(String venue_id) {
                    this.venue_id = venue_id;
                }

                public String getUefaranking_postion() {
                    return uefaranking_postion;
                }

                public void setUefaranking_postion(String uefaranking_postion) {
                    this.uefaranking_postion = uefaranking_postion;
                }

                public String getCreated_on() {
                    return created_on;
                }

                public void setCreated_on(String created_on) {
                    this.created_on = created_on;
                }
            }
        }
    }
}
