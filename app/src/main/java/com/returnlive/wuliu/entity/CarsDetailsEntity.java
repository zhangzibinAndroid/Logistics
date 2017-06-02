package com.returnlive.wuliu.entity;

/**
 * 作者： 张梓彬
 * 日期： 2017/6/2 0002
 * 时间： 下午 4:24
 * 描述： 车源详情页面
 */

public class CarsDetailsEntity {

    /**
     * state : success
     * info : {"id":18,"start":"秦皇岛市-海港区","end":"大同市-城区","car_type":5,"weight":12.9,"volume":13,"car_time":"1496205207","create_time":"1496205286","phone":"18795934366"}
     */

    private String state;
    private CarsDetailsBean info;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public CarsDetailsBean getInfo() {
        return info;
    }

    public void setInfo(CarsDetailsBean info) {
        this.info = info;
    }

    public static class CarsDetailsBean {
        /**
         * id : 18
         * start : 秦皇岛市-海港区
         * end : 大同市-城区
         * car_type : 5
         * weight : 12.9
         * volume : 13
         * car_time : 1496205207
         * create_time : 1496205286
         * phone : 18795934366
         */

        private int id;
        private String start;
        private String end;
        private int car_type;
        private float weight;
        private float volume;
        private String car_time;
        private String create_time;
        private String phone;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public int getCar_type() {
            return car_type;
        }

        public void setCar_type(int car_type) {
            this.car_type = car_type;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }

        public float getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public String getCar_time() {
            return car_time;
        }

        public void setCar_time(String car_time) {
            this.car_time = car_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
