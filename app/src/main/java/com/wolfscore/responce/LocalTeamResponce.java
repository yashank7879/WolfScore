package com.wolfscore.responce;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mindiii on 1/22/19.
 */

public class LocalTeamResponce {
    /**
     * status : success
     * message : Local team list
     * data : {"total_records":"403","team_list":[{"id":"1","team_id":"85","name":"København","country_id":"320","national_team":"0","founded":"1992","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/21/85.png","venue_id":"5660","uefaranking_postion":"43","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"2","team_id":"86","name":"Silkeborg","country_id":"320","national_team":"0","founded":"1917","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/22/86.png","venue_id":"85371","uefaranking_postion":"43","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"21","team_id":"53","name":"Celtic","country_id":"1161","national_team":"0","founded":"1888","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/21/53.png","venue_id":"8909","uefaranking_postion":"47","created_on":"2018-12-28 07:06:38","is_favorite":"0"},{"id":"22","team_id":"62","name":"Rangers","country_id":"1161","national_team":"0","founded":"1873","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/30/62.png","venue_id":"8914","uefaranking_postion":"47","created_on":"2018-12-28 07:06:38","is_favorite":"0"},{"id":"6","team_id":"939","name":"Midtjylland","country_id":"320","national_team":"0","founded":"1999","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/11/939.png","venue_id":"342","uefaranking_postion":"114","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"8","team_id":"1371","name":"Esbjerg","country_id":"320","national_team":"0","founded":"1924","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/27/1371.png","venue_id":"618","uefaranking_postion":"126","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"9","team_id":"1789","name":"OB","country_id":"320","national_team":"0","founded":"1889","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/29/1789.png","venue_id":"214349","uefaranking_postion":"126","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"18","team_id":"1703","name":"Hobro","country_id":"320","national_team":"0","founded":"1913","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/7/1703.png","venue_id":"849","uefaranking_postion":"126","created_on":"2018-12-28 07:05:29","is_favorite":"0"},{"id":"7","team_id":"1020","name":"AaB","country_id":"320","national_team":"0","founded":"1885","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/28/1020.png","venue_id":"85765","uefaranking_postion":"167","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"4","team_id":"293","name":"Brøndby","country_id":"320","national_team":"0","founded":"1964","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/5/293.png","venue_id":"5659","uefaranking_postion":"196","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"5","team_id":"390","name":"SønderjyskE","country_id":"320","national_team":"0","founded":"2004","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/6/390.png","venue_id":"5653","uefaranking_postion":"198","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"10","team_id":"2356","name":"Randers","country_id":"320","national_team":"0","founded":"2003","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/20/2356.png","venue_id":"1414","uefaranking_postion":"199","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"11","team_id":"2394","name":"Nordsjælland","country_id":"320","national_team":"0","founded":"2003","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/26/2394.png","venue_id":"1436","uefaranking_postion":"200","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"12","team_id":"2650","name":"Lyngby","country_id":"320","national_team":"0","founded":"1921","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/26/2650.png","venue_id":"1576","uefaranking_postion":"200","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"13","team_id":"2447","name":"Viborg","country_id":"320","national_team":"0","founded":"1896","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/15/2447.png","venue_id":"1467","uefaranking_postion":"200","created_on":"2018-12-28 06:58:01","is_favorite":"0"},{"id":"15","team_id":"7466","name":"Vejle","country_id":"320","national_team":"0","founded":"1891","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/10/7466.png","venue_id":"4832","uefaranking_postion":"200","created_on":"2018-12-28 07:01:09","is_favorite":"0"},{"id":"19","team_id":"2706","name":"Vendsyssel","country_id":"320","national_team":"0","founded":"1886","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/18/2706.png","venue_id":"289881","uefaranking_postion":"200","created_on":"2018-12-28 07:06:08","is_favorite":"0"},{"id":"36","team_id":"734","name":"St. Johnstone","country_id":"1161","national_team":"0","founded":"1885","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/30/734.png","venue_id":"219","uefaranking_postion":"219","created_on":"2018-12-28 07:07:44","is_favorite":"0"},{"id":"3","team_id":"211","name":"Horsens","country_id":"320","national_team":"0","founded":"1994","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/19/211.png","venue_id":"5661","uefaranking_postion":"222","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"27","team_id":"273","name":"Aberdeen","country_id":"1161","national_team":"0","founded":"1903","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/17/273.png","venue_id":"8928","uefaranking_postion":"236","created_on":"2018-12-28 07:06:38","is_favorite":"0"}]}
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
         * total_records : 403
         * team_list : [{"id":"1","team_id":"85","name":"København","country_id":"320","national_team":"0","founded":"1992","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/21/85.png","venue_id":"5660","uefaranking_postion":"43","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"2","team_id":"86","name":"Silkeborg","country_id":"320","national_team":"0","founded":"1917","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/22/86.png","venue_id":"85371","uefaranking_postion":"43","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"21","team_id":"53","name":"Celtic","country_id":"1161","national_team":"0","founded":"1888","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/21/53.png","venue_id":"8909","uefaranking_postion":"47","created_on":"2018-12-28 07:06:38","is_favorite":"0"},{"id":"22","team_id":"62","name":"Rangers","country_id":"1161","national_team":"0","founded":"1873","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/30/62.png","venue_id":"8914","uefaranking_postion":"47","created_on":"2018-12-28 07:06:38","is_favorite":"0"},{"id":"6","team_id":"939","name":"Midtjylland","country_id":"320","national_team":"0","founded":"1999","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/11/939.png","venue_id":"342","uefaranking_postion":"114","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"8","team_id":"1371","name":"Esbjerg","country_id":"320","national_team":"0","founded":"1924","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/27/1371.png","venue_id":"618","uefaranking_postion":"126","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"9","team_id":"1789","name":"OB","country_id":"320","national_team":"0","founded":"1889","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/29/1789.png","venue_id":"214349","uefaranking_postion":"126","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"18","team_id":"1703","name":"Hobro","country_id":"320","national_team":"0","founded":"1913","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/7/1703.png","venue_id":"849","uefaranking_postion":"126","created_on":"2018-12-28 07:05:29","is_favorite":"0"},{"id":"7","team_id":"1020","name":"AaB","country_id":"320","national_team":"0","founded":"1885","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/28/1020.png","venue_id":"85765","uefaranking_postion":"167","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"4","team_id":"293","name":"Brøndby","country_id":"320","national_team":"0","founded":"1964","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/5/293.png","venue_id":"5659","uefaranking_postion":"196","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"5","team_id":"390","name":"SønderjyskE","country_id":"320","national_team":"0","founded":"2004","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/6/390.png","venue_id":"5653","uefaranking_postion":"198","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"10","team_id":"2356","name":"Randers","country_id":"320","national_team":"0","founded":"2003","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/20/2356.png","venue_id":"1414","uefaranking_postion":"199","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"11","team_id":"2394","name":"Nordsjælland","country_id":"320","national_team":"0","founded":"2003","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/26/2394.png","venue_id":"1436","uefaranking_postion":"200","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"12","team_id":"2650","name":"Lyngby","country_id":"320","national_team":"0","founded":"1921","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/26/2650.png","venue_id":"1576","uefaranking_postion":"200","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"13","team_id":"2447","name":"Viborg","country_id":"320","national_team":"0","founded":"1896","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/15/2447.png","venue_id":"1467","uefaranking_postion":"200","created_on":"2018-12-28 06:58:01","is_favorite":"0"},{"id":"15","team_id":"7466","name":"Vejle","country_id":"320","national_team":"0","founded":"1891","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/10/7466.png","venue_id":"4832","uefaranking_postion":"200","created_on":"2018-12-28 07:01:09","is_favorite":"0"},{"id":"19","team_id":"2706","name":"Vendsyssel","country_id":"320","national_team":"0","founded":"1886","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/18/2706.png","venue_id":"289881","uefaranking_postion":"200","created_on":"2018-12-28 07:06:08","is_favorite":"0"},{"id":"36","team_id":"734","name":"St. Johnstone","country_id":"1161","national_team":"0","founded":"1885","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/30/734.png","venue_id":"219","uefaranking_postion":"219","created_on":"2018-12-28 07:07:44","is_favorite":"0"},{"id":"3","team_id":"211","name":"Horsens","country_id":"320","national_team":"0","founded":"1994","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/19/211.png","venue_id":"5661","uefaranking_postion":"222","created_on":"2018-12-28 06:56:08","is_favorite":"0"},{"id":"27","team_id":"273","name":"Aberdeen","country_id":"1161","national_team":"0","founded":"1903","logo_path":"https://cdn.sportmonks.com/images/soccer/teams/17/273.png","venue_id":"8928","uefaranking_postion":"236","created_on":"2018-12-28 07:06:38","is_favorite":"0"}]
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
             * is_favorite : 0
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
            private String is_favorite;
            private boolean favorite= true;

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

            public String getIs_favorite() {
                return is_favorite;
            }

            public void setIs_favorite(String is_favorite) {
                this.is_favorite = is_favorite;
            }

            public boolean isFavorite() {
                return favorite;
            }

            public void setFavorite(boolean favorite) {
                this.favorite = favorite;
            }



            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                TeamListBean myObject = (TeamListBean) o;

                if (Double.compare(Double.parseDouble(myObject.getId()), Double.parseDouble(id)) != 0) {
                    return false;
                }

                return true;
            }

            private List<TeamListBean> unique(List<TeamListBean> list) {
                List<TeamListBean> uniqueList = new ArrayList<>();
                Set<TeamListBean> uniqueSet = new HashSet<>();
                for (TeamListBean obj : list) {
                    if (uniqueSet.add(obj)) {
                        uniqueList.add(obj);
                    }
                }
                return uniqueList;
            }


        }
    }


}
