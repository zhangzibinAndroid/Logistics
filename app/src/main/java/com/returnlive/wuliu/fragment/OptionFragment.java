package com.returnlive.wuliu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.returnlive.wuliu.R;

/**
 * @author 张梓彬
 * Data : 2017/5/17 0017
 * Time : 下午 5:36
 * Describe : 车主车源页面
 */
public class OptionFragment extends Fragment {


    public OptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_option, container, false);
    }

}
