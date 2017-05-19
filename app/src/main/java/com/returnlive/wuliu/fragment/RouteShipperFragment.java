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
 * Time : 上午 10:49
 * Describe : 货主路线
 */
public class RouteShipperFragment extends Fragment {


    public RouteShipperFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_route_shipper, container, false);
    }

}
