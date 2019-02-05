package com.wolfscore.responce;

/**
 * Created by mindiii on 1/22/19.
 */

public class GuestUserResponce {
    /**
     * status : success
     * message : User authenticated successfully.
     * data : {"userId":"43","name":"","email":"","profile_photo":"","is_photo_url":"0","social_id":"","social_type":"","auth_token":"c3dSwsq0HPJbihmVoIYtDKkAOjnNW6","device_type":"1","device_token":"896e9c23c3e93b69","status":"1","created_on":"2019-01-22 13:17:43","updated_on":"2019-01-22 13:17:43"}
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
         * userId : 43
         * name :
         * email :
         * profile_photo :
         * is_photo_url : 0
         * social_id :
         * social_type :
         * auth_token : c3dSwsq0HPJbihmVoIYtDKkAOjnNW6
         * device_type : 1
         * device_token : 896e9c23c3e93b69
         * status : 1
         * created_on : 2019-01-22 13:17:43
         * updated_on : 2019-01-22 13:17:43
         */

        private String userId;
        private String name;
        private String email;
        private String profile_photo;
        private String is_photo_url;
        private String social_id;
        private String social_type;
        private String auth_token;
        private String device_type;
        private String device_token;
        private String status;
        private String created_on;
        private String updated_on;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getProfile_photo() {
            return profile_photo;
        }

        public void setProfile_photo(String profile_photo) {
            this.profile_photo = profile_photo;
        }

        public String getIs_photo_url() {
            return is_photo_url;
        }

        public void setIs_photo_url(String is_photo_url) {
            this.is_photo_url = is_photo_url;
        }

        public String getSocial_id() {
            return social_id;
        }

        public void setSocial_id(String social_id) {
            this.social_id = social_id;
        }

        public String getSocial_type() {
            return social_type;
        }

        public void setSocial_type(String social_type) {
            this.social_type = social_type;
        }

        public String getAuth_token() {
            return auth_token;
        }

        public void setAuth_token(String auth_token) {
            this.auth_token = auth_token;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
