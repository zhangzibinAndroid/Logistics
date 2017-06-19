package com.returnlive.wuliu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.returnlive.wuliu.R;

/**
 * 作者： 张梓彬
 * 日期： 2017/6/19 0019
 * 时间： 上午 10:50
 * 描述： 订单详情未完成页面
 */
public class OrderUnfinishFragment extends Fragment {


    public OrderUnfinishFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_unfinish, container, false);
    }

}
