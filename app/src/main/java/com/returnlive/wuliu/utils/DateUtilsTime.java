package com.returnlive.wuliu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者： 张梓彬
 * 日期： 2017/5/26 0026
 * 时间： 下午 2:31
 * 描述： 时间戳转换
 */
public class DateUtilsTime {

    public String getDate(String timestamp){
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(Long.valueOf(timestamp+"000")));
        return date;
    }

    public String getTimestamp(String date) throws ParseException {

        Date epoch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        long timestamp = (epoch.getTime()/1000);
        return timestamp+"";
    }

}
