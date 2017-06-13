package com.returnlive.wuliu.map.utils;

import java.text.DecimalFormat;

/**
 * Created by 张梓彬 on 2017/6/9 0009.
 */

public class MapMessageUtils {

    public static String getTime(String seconds){
        String time ="";
        int tt = Integer.valueOf(seconds);
        if (tt>3600){
            time = (tt/3600)+"小时"+((tt%3600)/60)+"分钟";
            return time;
        }else {
            time = (tt/60)+"分钟";
            return time;
        }

    }


    public static String getLength(String length){
        String route = "";
        float ss = Float.valueOf(length);
        float number = ss/1000;
        DecimalFormat df = new DecimalFormat("0.0");
        route = df.format(number)+"公里";
        return route;
    }

}
