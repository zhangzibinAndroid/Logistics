package com.returnlive.wuliu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.returnlive.wuliu.R;

/**
 * @author 张梓彬
 * Data : 2017/5/19 0019
 * Time : 上午 10:50
 * Describe : 货主发布车源页面
 */
public class OptionShipperFragment extends Fragment {


    public OptionShipperFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_option_shipper, container, false);
    }

}
