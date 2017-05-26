package com.returnlive.wuliu.entity;

import java.util.List;

/**
 * 作者： 张梓彬
 * 日期： 2017/5/26 0026
 * 时间： 下午 2:25
 * 描述： 车辆源列表实体类封装
 */

public class CarsourceListEntity {

    /**
     * state : success
     * date : [{"id":10,"u_id":8,"start":"内蒙古","end":"山西省","car_type":0,"weight":10,"volume":10,"car_time":"1494844009","remarks":"123456","create_time":"1494844010","status":1},{"id":11,"u_id":8,"start":"内蒙古","end":"山西省","car_type":0,"weight":10,"volume":10,"car_time":"1494844009","remarks":"123456","create_time":"1494844010","status":1},{"id":12,"u_id":8,"start":"内蒙古","end":"山西省","car_type":0,"weight":10,"volume":10,"car_time":"1494844009","remarks":"123456","create_time":"1494844010","status":1},{"id":13,"u_id":8,"start":"内蒙古","end":"山西省","car_type":0,"weight":10,"volume":10,"car_time":"1494844009","remarks":"123456","create_time":"1494844010","status":1},{"id":7,"u_id":8,"start":"内蒙古","end":"山西省","car_type":0,"weight":10,"volume":10,"car_time":"1494487237","remarks":"123456","create_time":"1494844009","status":1},{"id":8,"u_id":8,"start":"内蒙古","end":"山西省","car_type":0,"weight":10,"volume":10,"car_time":"1494844009","remarks":"123456","create_time":"1494844009","status":1},{"id":9,"u_id":8,"start":"内蒙古","end":"山西省","car_type":0,"weight":10,"volume":10,"car_time":"1494844009","remarks":"123456","create_time":"1494844009","status":1},{"id":6,"u_id":8,"start":"内蒙古","end":"山西省","car_type":0,"weight":10,"volume":10,"car_time":"1494487237","remarks":"123456","create_time":"1494844008","status":1},{"id":5,"u_id":8,"start":"内蒙古","end":"山西省","car_type":0,"weight":10,"volume":10,"car_time":"1494487237","remarks":"123456","create_time":"1494844004","status":1},{"id":4,"u_id":8,"start":"北京市","end":"山西省大同市","car_type":0,"weight":4,"volume":2,"car_time":"1494487237","remarks":"v哈哈好几回刚回家","create_time":"1494488341","status":1},{"id":3,"u_id":8,"start":"吉林省延边朝鲜族自治州","end":"辽宁省沈阳市","car_type":0,"weight":9999.99,"volume":246,"car_time":"1494487237","remarks":"9479你放假你猜那你想你们","create_time":"1494487989","status":1},{"id":2,"u_id":8,"start":"河北省秦皇岛市北戴河区","end":"辽宁省鞍山市立山区","car_type":0,"weight":8888,"volume":666,"car_time":"1494487237","remarks":"继续继续觉得你男的女的你","create_time":"1494487308","status":1},{"id":1,"u_id":8,"start":"山西省阳泉市","end":"河北省秦皇岛市山海关区","car_type":0,"weight":666,"volume":55,"car_time":"1494487237","remarks":"就到家等级考试","create_time":"1494487237","status":1}]
     */

    private String state;
    private List<CarsourceBean> date;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<CarsourceBean> getDate() {
        return date;
    }

    public void setDate(List<CarsourceBean> date) {
        this.date = date;
    }

    public static class CarsourceBean {
        /**
         * id : 10
         * u_id : 8
         * start : 内蒙古
         * end : 山西省
         * car_type : 0
         * weight : 10
         * volume : 10
         * car_time : 1494844009
         * remarks : 123456
         * create_time : 1494844010
         * status : 1
         */

        private int id;
        private int u_id;
        private String start;
        private String end;
        private int car_type;
        private float weight;
        private float volume;
        private String car_time;
        private String remarks;
        private String create_time;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getU_id() {
            return u_id;
        }

        public void setU_id(int u_id) {
            this.u_id = u_id;
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

        public void setVolume(float volume) {
            this.volume = volume;
        }

        public String getCar_time() {
            return car_time;
        }

        public void setCar_time(String car_time) {
            this.car_time = car_time;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
