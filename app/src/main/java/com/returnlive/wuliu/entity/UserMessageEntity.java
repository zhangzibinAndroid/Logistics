package com.returnlive.wuliu.entity;

/**
 * 作者： 张梓彬
 * 日期： 2017/5/24 0024
 * 时间： 下午 1:38
 * 描述： 用户信息实体类封装
 */
public class UserMessageEntity {

    /**
     * state : success
     * code : {"phone":"18795934365","user_auth":"1","car_auth":"1","create_time":"1493281669"}
     */

    private String state;
    private UserBean code;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public UserBean getCode() {
        return code;
    }

    public void setCode(UserBean code) {
        this.code = code;
    }

    public static class UserBean {
        /**
         * phone : 18795934365
         * user_auth : 1
         * car_auth : 1
         * create_time : 1493281669
         */

        private String phone;
        private String user_auth;
        private String car_auth;
        private String create_time;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUser_auth() {
            return user_auth;
        }

        public void setUser_auth(String user_auth) {
            this.user_auth = user_auth;
        }

        public String getCar_auth() {
            return car_auth;
        }

        public void setCar_auth(String car_auth) {
            this.car_auth = car_auth;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
