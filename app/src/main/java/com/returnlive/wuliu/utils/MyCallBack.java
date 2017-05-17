package com.returnlive.wuliu.utils;

import org.xutils.common.Callback;


/**
 * 作者：张梓彬
 * 日期：2017/5/17 0017
 * 时间：下午 11:32
 * 描述：Xutil3自定义回调接口
 */
public class MyCallBack<ResultType> implements Callback.CommonCallback<ResultType>{

    @Override
    public void onSuccess(ResultType result) {        //可以根据公司的需求进行统一的请求成功的逻辑处理
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {        //可以根据公司的需求进行统一的请求网络失败的逻辑处理
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override    public void onFinished() {

    }


}
