package com.returnlive.wuliu.entity;

import java.util.List;
/**
 * 作者： 张梓彬
 * 日期： 2017/5/31 0031
 * 时间： 下午 2:35
 * 描述： 货源列表实体类封装
 */
public class GoodsSourceListEntity {

    /**
     * state : success
     * date : [{"id":5,"u_id":4,"start":"北京市北京市东城区","end":"北京市北京市东城区","goods_type":0,"goods_name":"as","weight":13,"volume":123,"car_type":0,"car_time":"2017-04-20  16:5","price":123,"receipt_phone":"11111111111","remarks":"","is_real":0,"create_time":"1492678629","status":1},{"id":2,"u_id":4,"start":"北京市北京市东城区","end":"北京市北京市东城区","goods_type":0,"goods_name":"as","weight":13,"volume":123,"car_type":0,"car_time":"2017-04-20  16:5","price":123,"receipt_phone":"11111111111","remarks":"","is_real":0,"create_time":"1492678596","status":1}]
     */

    private String state;
    private List<GoosdsBean> date;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<GoosdsBean> getDate() {
        return date;
    }

    public void setDate(List<GoosdsBean> date) {
        this.date = date;
    }

    public static class GoosdsBean {
        /**
         * id : 5
         * u_id : 4
         * start : 北京市北京市东城区
         * end : 北京市北京市东城区
         * goods_type : 0
         * goods_name : as
         * weight : 13
         * volume : 123
         * car_type : 0
         * car_time : 1492678629
         * price : 123
         * receipt_phone : 11111111111
         * remarks :
         * is_real : 0
         * create_time : 1492678629
         * status : 1
         */

        private int id;
        private int u_id;
        private String start;
        private String end;
        private int goods_type;
        private String goods_name;
        private float weight;
        private float volume;
        private int car_type;
        private String car_time;
        private float price;
        private String receipt_phone;
        private String remarks;
        private int is_real;
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

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public float getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public int getCar_type() {
            return car_type;
        }

        public void setCar_type(int car_type) {
            this.car_type = car_type;
        }

        public String getCar_time() {
            return car_time;
        }

        public void setCar_time(String car_time) {
            this.car_time = car_time;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getReceipt_phone() {
            return receipt_phone;
        }

        public void setReceipt_phone(String receipt_phone) {
            this.receipt_phone = receipt_phone;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getIs_real() {
            return is_real;
        }

        public void setIs_real(int is_real) {
            this.is_real = is_real;
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
