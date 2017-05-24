package com.returnlive.wuliu.entity;

import android.widget.ImageView;

/**
 * 作者： 张梓彬
 * 日期： 2017/5/24 0024
 * 时间： 下午 5:22
 * 描述： 货源适配器的实体类封装
 */

public class GoodsAdapterEntity {
    ImageView imgHead;
    String name;
    String time;
    String goods_send_number;
    String deal_number;
    String goods_start;
    String goods_end;
    String goods_remark;
    String goods_cartype;
    String goods_carlength;
    String goods_weight;
    String goods_traveltime;

    public GoodsAdapterEntity(String name) {
        this.name = name;
    }

    /*public GoodsAdapterEntity(ImageView imgHead, String name, String time, String goods_send_number, String deal_number, String goods_start, String goods_end, String goods_remark, String goods_cartype, String goods_carlength, String goods_weight, String goods_traveltime) {
        this.imgHead = imgHead;
        this.name = name;
        this.time = time;
        this.goods_send_number = goods_send_number;
        this.deal_number = deal_number;
        this.goods_start = goods_start;
        this.goods_end = goods_end;
        this.goods_remark = goods_remark;
        this.goods_cartype = goods_cartype;
        this.goods_carlength = goods_carlength;
        this.goods_weight = goods_weight;
        this.goods_traveltime = goods_traveltime;
    }*/

    public ImageView getImgHead() {
        return imgHead;
    }

    public void setImgHead(ImageView imgHead) {
        this.imgHead = imgHead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGoods_send_number() {
        return goods_send_number;
    }

    public void setGoods_send_number(String goods_send_number) {
        this.goods_send_number = goods_send_number;
    }

    public String getDeal_number() {
        return deal_number;
    }

    public void setDeal_number(String deal_number) {
        this.deal_number = deal_number;
    }

    public String getGoods_start() {
        return goods_start;
    }

    public void setGoods_start(String goods_start) {
        this.goods_start = goods_start;
    }

    public String getGoods_end() {
        return goods_end;
    }

    public void setGoods_end(String goods_end) {
        this.goods_end = goods_end;
    }

    public String getGoods_remark() {
        return goods_remark;
    }

    public void setGoods_remark(String goods_remark) {
        this.goods_remark = goods_remark;
    }

    public String getGoods_cartype() {
        return goods_cartype;
    }

    public void setGoods_cartype(String goods_cartype) {
        this.goods_cartype = goods_cartype;
    }

    public String getGoods_carlength() {
        return goods_carlength;
    }

    public void setGoods_carlength(String goods_carlength) {
        this.goods_carlength = goods_carlength;
    }

    public String getGoods_weight() {
        return goods_weight;
    }

    public void setGoods_weight(String goods_weight) {
        this.goods_weight = goods_weight;
    }

    public String getGoods_traveltime() {
        return goods_traveltime;
    }

    public void setGoods_traveltime(String goods_traveltime) {
        this.goods_traveltime = goods_traveltime;
    }
}
