package com.returnlive.wuliu.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.activity.SettingActivity;
import com.returnlive.wuliu.view.RoundImageView;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 张梓彬
 *         Data : 2017/5/17 0017
 *         Time : 下午 5:35
 *         Describe : 车主我的页面
 */
public class MineFragment extends Fragment {
    @ViewInject(R.id.tv_version)
    TextView tv_version;
    @ViewInject(R.id.img_driver)
    RoundImageView img_driver;
    @ViewInject(R.id.img_switch)
    ImageView img_switch;
    @ViewInject(R.id.img_shipper)
    RoundImageView img_shipper;
    @ViewInject(R.id.tv_driver)
    TextView tv_driver;
    @ViewInject(R.id.tv_me_change)
    TextView tv_me_change;
    @ViewInject(R.id.tv_shipper)
    TextView tv_shipper;
    @ViewInject(R.id.img_personal)
    RoundImageView img_personal;
    @ViewInject(R.id.tv_kind)
    TextView tv_kind;
    @ViewInject(R.id.tv_level)
    TextView tv_level;
    @ViewInject(R.id.tv_drivername)
    TextView tv_drivername;
    @ViewInject(R.id.tv_getorder)
    TextView tv_getorder;
    @ViewInject(R.id.tv_sendcar)
    TextView tv_sendcar;
    @ViewInject(R.id.lay_carmasyer)
    AutoRelativeLayout lay_carmasyer;
    @ViewInject(R.id.img_personalshipper)
    RoundImageView img_personalshipper;
    @ViewInject(R.id.tv_shipperkind)
    TextView tv_shipperkind;
    @ViewInject(R.id.tv_levelshipper)
    TextView tv_levelshipper;
    @ViewInject(R.id.tv_shippername)
    TextView tv_shippername;
    @ViewInject(R.id.tv_getordershipper)
    TextView tv_getordershipper;
    @ViewInject(R.id.tv_sendcarshipper)
    TextView tv_sendcarshipper;
    @ViewInject(R.id.lay_shipper)
    AutoRelativeLayout lay_shipper;
    @ViewInject(R.id.tv_clinchdeal)
    TextView tv_clinchdeal;
    @ViewInject(R.id.tv_alclinchdeal)
    TextView tv_alclinchdeal;
    @ViewInject(R.id.tv_receivegoods)
    TextView tv_receivegoods;
    @ViewInject(R.id.tv_alreadyfinish)
    TextView tv_alreadyfinish;
    @ViewInject(R.id.tv_balance)
    TextView tv_balance;
    @ViewInject(R.id.lay_balance)
    AutoRelativeLayout lay_balance;
    @ViewInject(R.id.lay_topup)
    AutoRelativeLayout lay_topup;
    @ViewInject(R.id.mycertification)
    TextView mycertification;
    @ViewInject(R.id.tv_certification)
    TextView tv_certification;
    @ViewInject(R.id.lay_mycertification)
    AutoRelativeLayout lay_mycertification;
    @ViewInject(R.id.tv_setting)
    TextView tv_setting;
    @ViewInject(R.id.lay_set)
    AutoRelativeLayout lay_set;
    private View view;


    public MineFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        x.view().inject(this, view);
        return view;
    }

    @Event(value = {R.id.img_driver, R.id.img_shipper, R.id.lay_balance, R.id.lay_topup, R.id.lay_mycertification, R.id.lay_set})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_driver:
                break;
            case R.id.img_shipper:
                break;
            case R.id.lay_balance:
                break;
            case R.id.lay_topup:
                break;
            case R.id.lay_mycertification:
                break;
            case R.id.lay_set:
                pageJump(SettingActivity.class);
                break;
        }
    }


    public void pageJump(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }
}
