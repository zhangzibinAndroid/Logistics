package com.returnlive.wuliu.fragment;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.constant.ConstantNumber;
import com.returnlive.wuliu.constant.NetworkUrl;
import com.returnlive.wuliu.utils.MyCallBack;
import com.returnlive.wuliu.utils.XUtil;
import com.returnlive.wuliu.view.CityListViewPopWindow;
import com.zhy.autolayout.AutoRelativeLayout;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者： 张梓彬
 * 日期： 2017/5/27 0027
 * 时间： 下午 6:17
 * 描述： 货主发布车源页面
 */
public class OptionShipperFragment extends Fragment {
    @ViewInject(R.id.ftoolbar)
    Toolbar ftoolbar;
    @ViewInject(R.id.tv_start_area)
    TextView tv_start_area;

    @ViewInject(R.id.tv_end_area)
    TextView tv_end_area;
    @ViewInject(R.id.tv_car_style)
    TextView tv_car_style;
    @ViewInject(R.id.tv_wgt)
    EditText tv_wgt;
    @ViewInject(R.id.tv_enter_volume)
    EditText tv_enter_volume;
    @ViewInject(R.id.tv_sel_start_time)
    TextView tv_sel_start_time;
    @ViewInject(R.id.tv_note)
    EditText tv_note;
    @ViewInject(R.id.lay_start_area)
    AutoRelativeLayout lay_start_area;
    @ViewInject(R.id.lay_end_area)
    AutoRelativeLayout lay_end_area;
    private View view;
    private TimePickerView timePickerView;//时间选择器
    private CityListViewPopWindow cityListViewPopWindow;
    public int WHICH = ConstantNumber.NUMBER_ZERO;
    private static final String TAG = "OptionShipperFragment";
    private String[] models = {"平板", "高栏", "厢式", "保温", "冷藏", "集装箱", "面包车", "危险品", "其他"};
    private AlertDialog.Builder dialog;
    public OptionShipperFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_option_shipper, container, false);
        x.view().inject(this, view);
        ftoolbar.setTitle("");//防止标题冲突
        cityListViewPopWindow = new CityListViewPopWindow(getActivity(), mOnCheckChangeListener);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//软键盘覆盖布局
        initCustomTimePicker();
        initDialoge();
        return view;
    }

    @Event(value = {R.id.lay_start_area, R.id.lay_end_area, R.id.lay_sel_car_style, R.id.lay_sel_start_time, R.id.btn_release})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.lay_start_area://出发地
                WHICH = ConstantNumber.NUMBER_ONE;
                cityListViewPopWindow.showAsDropDown(lay_start_area);
                showCityPopWindow();
                break;
            case R.id.lay_end_area://目的地
                WHICH = ConstantNumber.NUMBER_TWO;
                cityListViewPopWindow.showAsDropDown(lay_end_area);
                showCityPopWindow();
                break;
            case R.id.lay_sel_car_style://车辆类型
                dialog.show();
                break;
            case R.id.lay_sel_start_time://发车时间
                if (timePickerView != null) {
                    timePickerView.show();
                }
                break;
            case R.id.btn_release://发布

                if (tv_start_area.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "出发地不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }else if (tv_end_area.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "目的地不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }else if (tv_car_style.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "车辆类型不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }else if (tv_end_area.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "发车时间不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            releaseInterface();
                        }
                    }).start();
                }

                break;
        }
    }


    private void initDialoge() {
        dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("选择车型");
        dialog.setItems(models, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_car_style.setText(models[which]);
                dialog.dismiss();

            }
        });

    }

    //发布接口
    private void releaseInterface() {
        String startArea = tv_start_area.getText().toString();
        String endArea = tv_end_area.getText().toString();
        String carStyle = tv_car_style.getText().toString();
        float wgt = Float.valueOf(tv_wgt.getText().toString());
        float volume = Float.valueOf(tv_enter_volume.getText().toString());
        String carStartTime = tv_sel_start_time.getText().toString();
        //转型处理


        Map<String, Object> map = new HashMap<>();
        /*map.put("start", userName);
        map.put("end", userPwds);
        map.put("car_type", userPwds);
        map.put("weight", userPwds);
        map.put("volume", userPwds);
        map.put("car_time", userPwds);
        map.put("remarks", userPwds);*/
        NetworkUrl networkUrl = new NetworkUrl();
        XUtil.Post(networkUrl.CAR_SOURCE_ADD_URL, map, new MyCallBack<String>() {

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Message msg = new Message();
                msg.obj = result;

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), getResources().getString(R.string.networ_anomalies), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }

    /**
     * 显示“时间”的初始化
     */
    private void initCustomTimePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();//开始时间
        Calendar endDate = Calendar.getInstance();//结束时间
        startDate.set(2000, 1, 23);
        endDate.set(2038, 1, 28);
        timePickerView = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                //可根据需要自行截取数据显示在控件上  yyyy-MM-dd HH:mm:ss  或yyyy-MM-dd
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                tv_sel_start_time.setText(time);
            }
        })
                .setType(TimePickerView.Type.ALL)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setTextColorCenter(Color.parseColor("#FF4081"))//设置选中文字的颜色#64AE4A
                .setTextColorOut(Color.parseColor("#717171"))//设置选中项以外的颜色#64AE4A
                .setLineSpacingMultiplier(2.4f)//设置两横线之间的间隔倍数
                .setDividerColor(Color.parseColor("#24E1E4"))//设置分割线的颜色
                .setDividerType(WheelView.DividerType.WRAP)//设置分割线的类型
                .setBgColor(Color.parseColor("#FFFFFF"))//背景颜色(必须是16进制) Night mode#2AA2BC
                .gravity(Gravity.CENTER)//设置控件显示位置 default is center*/
                .isDialog(true)//设置显示位置
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvCancel = (TextView) v.findViewById(R.id.iv_cancel);
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                timePickerView.returnData();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                timePickerView.dismiss();
                            }
                        });
                    }
                })
                .build();
    }




    private CityListViewPopWindow.OnCheckChangeListener mOnCheckChangeListener = new CityListViewPopWindow.OnCheckChangeListener() {
        @Override
        public void setProvinceText(String text) {

        }

        @Override
        public void setCityText(String text) {
            if (WHICH == ConstantNumber.NUMBER_ONE) {
                tv_start_area.setText(text);
            } else if (WHICH == ConstantNumber.NUMBER_TWO) {
                tv_end_area.setText(text);
            }

        }

        @Override
        public void setDistrictText(String text) {
            if (WHICH == ConstantNumber.NUMBER_ONE) {
                tv_start_area.setText(text);
            } else if (WHICH == ConstantNumber.NUMBER_TWO) {
                tv_end_area.setText(text);
            }

        }
    };


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
}
