package com.returnlive.wuliu.application;

import android.app.Activity;
import android.app.Application;
import com.zhy.autolayout.config.AutoLayoutConifg;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张梓彬
 * Data : 2017/5/17 0017
 * Time : 下午 2:52
 * Describe : Xutils初始化，autolayout初始化,统一关闭多个activity
 */

public class LogisticsApplication extends Application{
    private static List<Activity> lists = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        AutoLayoutConifg.getInstance().useDeviceSize();
        x.Ext.init(this);
    }



    public static void addActivity(Activity activity) {
        lists.add(activity);
    }

    public static void clearActivity() {
        if (lists != null) {
            for (Activity activity : lists) {
                activity.finish();
            }

            lists.clear();
        }
    }
}
