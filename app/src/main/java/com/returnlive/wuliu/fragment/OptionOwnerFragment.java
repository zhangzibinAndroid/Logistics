package com.returnlive.wuliu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.constant.ConstantNumber;
import com.returnlive.wuliu.view.CityListViewPopWindow;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 作者： 张梓彬
 * 日期： 2017/5/31 0031
 * 时间： 下午 4:18
 * 描述： 车主发布货源页面
 */
public class OptionOwnerFragment extends Fragment {
    @ViewInject(R.id.tv_start)
    TextView tv_start;
    @ViewInject(R.id.cb_need_get_goods)
    CheckBox cb_need_get_goods;
    @ViewInject(R.id.lay_goods_start_area)
    AutoRelativeLayout lay_goods_start_area;
    @ViewInject(R.id.tv_goods_end_area)
    TextView tv_goods_end_area;
    @ViewInject(R.id.cb_need_send_goods)
    CheckBox cb_need_send_goods;
    @ViewInject(R.id.lay_goods_end_area)
    AutoRelativeLayout lay_goods_end_area;
    @ViewInject(R.id.tv_goods_style)
    TextView tv_goods_style;
    @ViewInject(R.id.edt_goods_name)
    EditText edt_goods_name;
    @ViewInject(R.id.edt_goods_wgt)
    EditText edt_goods_wgt;
    @ViewInject(R.id.edt_goods_volume)
    EditText edt_goods_volume;
    @ViewInject(R.id.tv_cars_needs)
    TextView tv_cars_needs;
    @ViewInject(R.id.tv_loading_car_time)
    TextView tv_loading_car_time;
    @ViewInject(R.id.edt_indicative_price)
    EditText edt_indicative_price;
    @ViewInject(R.id.edt_receiver_phone)
    EditText edt_receiver_phone;
    @ViewInject(R.id.edt_goods_note)
    EditText edt_goods_note;
    @ViewInject(R.id.cb_contact)
    CheckBox cb_contact;
    @ViewInject(R.id.btn_publish)
    Button btn_publish;
    private View view;
    private CityListViewPopWindow cityListViewPopWindow;
    public int WHICH = ConstantNumber.NUMBER_ZERO;


    public OptionOwnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_option, container, false);
        x.view().inject(this, view);
        cityListViewPopWindow = new CityListViewPopWindow(getActivity(), mOnCheckChangeListener);

        return view;
    }

    @Event(value = {R.id.tv_start, R.id.tv_goods_end_area, R.id.lay_star_style, R.id.lay_cars_needs, R.id.lay_loading_car_time})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                WHICH = ConstantNumber.NUMBER_ONE;
                cityListViewPopWindow.showAsDropDown(lay_goods_start_area);
                showCityPopWindow();
                break;
            case R.id.tv_goods_end_area:
                WHICH = ConstantNumber.NUMBER_TWO;
                cityListViewPopWindow.showAsDropDown(lay_goods_end_area);
                showCityPopWindow();
                break;
            case R.id.lay_star_style:
                break;
            case R.id.lay_cars_needs:
                break;
            case R.id.lay_loading_car_time:
                break;
        }
    }

    private void showCityPopWindow() {
        cityListViewPopWindow.setOutsideTouchable(true);
        setPopWindowbackgroundAlpha(0.5f);//设置展示对话框背景变暗
        cityListViewPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setPopWindowbackgroundAlpha(1f);
            }
        });
    }

    private void setPopWindowbackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(params);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private CityListViewPopWindow.OnCheckChangeListener mOnCheckChangeListener = new CityListViewPopWindow.OnCheckChangeListener() {
        @Override
        public void setProvinceText(String text) {

        }

        @Override
        public void setCityText(String text) {
            if (WHICH == ConstantNumber.NUMBER_ONE) {
                tv_start.setText(text);
            } else if (WHICH == ConstantNumber.NUMBER_TWO) {
                tv_goods_end_area.setText(text);
            }

        }

        @Override
        public void setDistrictText(String text) {
            if (WHICH == ConstantNumber.NUMBER_ONE) {
                tv_start.setText(text);
            } else if (WHICH == ConstantNumber.NUMBER_TWO) {
                tv_goods_end_area.setText(text);

            }

        }
    };
}
