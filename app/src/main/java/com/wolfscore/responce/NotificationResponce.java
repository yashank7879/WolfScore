package com.wolfscore.responce;

/**
 * Created by mindiii on 1/28/19.
 */

public class NotificationResponce {
    /**
     * status : success
     * message : success
     * data : {"notificationManageId":"2","user_id":"4","goal":"0","red_card":"1","yellow_card":"0","match_reminder":"1","match_start":"0","half_time":"1","full_time_result":"0","created_on":"2019-01-21 01:36:45","updated_on":"2019-01-28 04:27:07"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * notificationManageId : 2
         * user_id : 4
         * goal : 0
         * red_card : 1
         * yellow_card : 0
         * match_reminder : 1
         * match_start : 0
         * half_time : 1
         * full_time_result : 0
         * created_on : 2019-01-21 01:36:45
         * updated_on : 2019-01-28 04:27:07
         */

        private String notificationManageId;
        private String user_id;
        private String goal;
        private String red_card;
        private String yellow_card;
        private String match_reminder;
        private String match_start;
        private String half_time;
        private String full_time_result;
        private String created_on;
        private String updated_on;

        public String getNotificationManageId() {
            return notificationManageId;
        }

        public void setNotificationManageId(String notificationManageId) {
            this.notificationManageId = notificationManageId;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getGoal() {
            return goal;
        }

        public void setGoal(String goal) {
            this.goal = goal;
        }

        public String getRed_card() {
            return red_card;
        }

        public void setRed_card(String red_card) {
            this.red_card = red_card;
        }

        public String getYellow_card() {
            return yellow_card;
        }

        public void setYellow_card(String yellow_card) {
            this.yellow_card = yellow_card;
        }

        public String getMatch_reminder() {
            return match_reminder;
        }

        public void setMatch_reminder(String match_reminder) {
            this.match_reminder = match_reminder;
        }

        public String getMatch_start() {
            return match_start;
        }

        public void setMatch_start(String match_start) {
            this.match_start = match_start;
        }

        public String getHalf_time() {
            return half_time;
        }

        public void setHalf_time(String half_time) {
            this.half_time = half_time;
        }

        public String getFull_time_result() {
            return full_time_result;
        }

        public void setFull_time_result(String full_time_result) {
            this.full_time_result = full_time_result;
        }

        public String getCreated_on() {
            return created_on;
        }

        public void setCreated_on(String created_on) {
            this.created_on = created_on;
        }

        public String getUpdated_on() {
            return updated_on;
        }

        public void setUpdated_on(String updated_on) {
            this.updated_on = updated_on;
        }
    }
}
