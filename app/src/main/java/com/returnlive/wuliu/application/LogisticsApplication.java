package com.returnlive.wuliu.application;

import android.app.Application;
import com.zhy.autolayout.config.AutoLayoutConifg;
import org.xutils.x;

/**
 * @author 张梓彬
 * Data : 2017/5/17 0017
 * Time : 下午 2:52
 * Describe : Xutils初始化，autolayout初始化
 */

public class LogisticsApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AutoLayoutConifg.getInstance().useDeviceSize();
        x.Ext.init(this);
    }
}
