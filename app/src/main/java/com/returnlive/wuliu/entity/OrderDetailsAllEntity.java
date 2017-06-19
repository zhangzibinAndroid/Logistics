package com.returnlive.wuliu.entity;

/**
 * 作者： 张梓彬
 * 日期： 2017/6/19 0019
 * 时间： 下午 3:08
 * 描述： 订单详情全部页面的item实体类封装
 */

public class OrderDetailsAllEntity {
    private String isFinish,time,startPlace,endPlace;

    public OrderDetailsAllEntity(String isFinish, String time, String startPlace, String endPlace) {
        this.isFinish = isFinish;
        this.time = time;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }
}
