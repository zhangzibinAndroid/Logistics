package com.returnlive.wuliu.map.adapter;

/**
 * Created by 张梓彬 on 2017/6/9 0009.
 */

public class MapMessageEntity {
    public String title;
    public String time;
    public String length;

    public MapMessageEntity(String title, String time, String length) {
        this.title = title;
        this.time = time;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
