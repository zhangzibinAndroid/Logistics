package com.returnlive.wuliu.entity;


/**
 * 作者： 张梓彬
 * 日期： 2017/5/25 0025
 * 时间： 下午 4:49
 * 描述： 三级联动城市适配器实体类封装
 */
public class CityItemEntity {
    public String cityName;
    public boolean isChoose;

    public CityItemEntity(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
