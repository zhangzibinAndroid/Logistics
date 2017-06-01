package com.returnlive.wuliu.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.activity.DriverCertificationActivity;
import com.returnlive.wuliu.constant.ConstantNumber;
import com.returnlive.wuliu.constant.NetworkUrl;
import com.returnlive.wuliu.utils.DateUtilsTime;
import com.returnlive.wuliu.utils.MyCallBack;
import com.returnlive.wuliu.utils.XUtil;
import com.returnlive.wuliu.view.CityListViewPopWindow;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    private View view;
    private CityListViewPopWindow cityListViewPopWindow;
    public int WHICH = ConstantNumber.NUMBER_ZERO;
    private AlertDialog.Builder builder,dialogSize,carStyleDialog;
    private String[] models = {"平板", "高栏", "厢式", "保温", "冷藏", "集装箱", "面包车", "危险品", "其他"};
    private String[] carLength = {"4.2米", "5.0米", "6.2米", "6.8米", "7.2米", "7.7米", "7.8米", "8.2米", "8.7米", "9.6米", "12.5米","13.0米", "15.0米", "16.0米", "17.5米", "自定义"};
    private String carModels, carSize;
    private String[] carStyle = {"普通货物","特殊货物","危险货物"};
    private AlertDialog dialogCarStyle;
    private TimePickerView timePickerView;//时间选择器
    private ProgressDialog pro;
    private static final String TAG = "OptionOwnerFragment";

    public OptionOwnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_option, container, false);
        x.view().inject(this, view);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//软键盘覆盖布局
        pro = new ProgressDialog(getActivity());
        pro.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pro.setMessage("正在发布货源信息...");
        pro.setCanceledOnTouchOutside(false);
        pro.setCancelable(false);
        initCustomTimePicker();

        cityListViewPopWindow = new CityListViewPopWindow(getActivity(), mOnCheckChangeListener);
        initViewCarNeedsDialog();
        return view;
    }

    @Event(value = {R.id.tv_start, R.id.tv_goods_end_area, R.id.lay_star_style, R.id.lay_cars_needs, R.id.lay_loading_car_time,R.id.btn_publish})
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
                initViewCarStyleDialog();
                break;
            case R.id.lay_cars_needs:
                builder.show();
                break;
            case R.id.lay_loading_car_time:
                if (timePickerView != null) {
                    timePickerView.show();
                }
                break;

            case R.id.btn_publish:
                release();
                break;
        }
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
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
                String time = format.format(date);
                tv_loading_car_time.setText(time);
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setTextColorCenter(Color.parseColor("#FF4081"))//设置选中文字的颜色#64AE4A
                .setTextColorOut(Color.parseColor("#00A84B"))//设置选中项以外的颜色#64AE4A
                .setLineSpacingMultiplier(2.4f)//设置两横线之间的间隔倍数
                .setDividerColor(Color.parseColor("#24E1E4"))//设置分割线的颜色
                .setDividerType(WheelView.DividerType.WRAP)//设置分割线的类型
                .setBgColor(Color.parseColor("#ffffff"))//背景颜色(必须是16进制) Night mode#2AA2BC
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

    private void release() {
        if (tv_start.getText().equals("")){
            Toast.makeText(getActivity(), "出发地不能为空", Toast.LENGTH_SHORT).show();
        }else if (tv_goods_end_area.getText().equals("")){
            Toast.makeText(getActivity(), "目的地不能为空", Toast.LENGTH_SHORT).show();
        }else if (tv_goods_style.getText().equals("")){
            Toast.makeText(getActivity(), "货物类型不能为空", Toast.LENGTH_SHORT).show();
        }else if (edt_goods_name.getText().equals("")){
            Toast.makeText(getActivity(), "货物名称不能为空", Toast.LENGTH_SHORT).show();
        }else if (edt_goods_wgt.getText().equals("")){
            Toast.makeText(getActivity(), "货物重量不能为空", Toast.LENGTH_SHORT).show();
        }else if (edt_goods_volume.getText().equals("")){
            Toast.makeText(getActivity(), "货物体积不能为空", Toast.LENGTH_SHORT).show();
        }else if (tv_cars_needs.getText().equals("")){
            Toast.makeText(getActivity(), "车辆需求不能为空", Toast.LENGTH_SHORT).show();
        }else if (tv_loading_car_time.getText().equals("")){
            Toast.makeText(getActivity(), "装车时间不能为空", Toast.LENGTH_SHORT).show();
        }else if (edt_indicative_price.getText().equals("")){
            Toast.makeText(getActivity(), "意向价格不能为空", Toast.LENGTH_SHORT).show();
        }else if (edt_receiver_phone.getText().equals("")){
            Toast.makeText(getActivity(), "收货电话不能为空", Toast.LENGTH_SHORT).show();
        }else {
            pro.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        releaseInterface();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    //发布货源接口
    private void releaseInterface() {
        String startArea = tv_start.getText().toString();//出发地
        String endArea = tv_goods_end_area.getText().toString();//目的地
        String goods_style = tv_goods_style.getText().toString();//货物类型
        int GOODS_STYLE = 0;
        for (int i = 0; i < carStyle.length; i++) {
            if (carStyle[i].equals(goods_style)){
                GOODS_STYLE = i;
            }
        }


        String goods_name = edt_goods_name.getText().toString();//货物名称
        String goods_wgt = edt_goods_wgt.getText().toString();//货物重量
        String goods_volume = edt_goods_volume.getText().toString();//货物体积
        String cars_needs = tv_cars_needs.getText().toString();//车辆需求
        int CAR_STYLE = 0;
        for (int i = 0; i < models.length; i++) {
            if (models[i].equals(carModels)){
                CAR_STYLE = i;
            }
        }


        String loading_car_time = tv_loading_car_time.getText().toString();//装车时间
        DateUtilsTime dateUtilsTime = new DateUtilsTime();
        String time = "";

        try {
            time = dateUtilsTime.getGoodsTimeStamp(loading_car_time);
        } catch (ParseException e) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), getResources().getString(R.string.wrong_change_time), Toast.LENGTH_SHORT).show();
                }
            });
        }


        String indicative_price = edt_indicative_price.getText().toString();//意向价格
        String receiver_phone = edt_receiver_phone.getText().toString();//收货电话
        String goods_note = edt_goods_note.getText().toString();//备注
        int is_real = -1;
        boolean is_contact = cb_contact.isChecked();//是否实名
        if (is_contact){
            is_real = 1;
        }else {
            is_real = 0;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("start", startArea);
        map.put("end", endArea);
        map.put("goods_type", GOODS_STYLE);
        map.put("goods_name", goods_name);
        map.put("weight", Float.valueOf(goods_wgt));
        map.put("volume", Float.valueOf(goods_volume));
        map.put("car_type", CAR_STYLE);
        map.put("car_time", time);
        map.put("price", Float.valueOf(indicative_price));
        map.put("receipt_phone", receiver_phone);
        map.put("remarks", goods_note);
        map.put("is_real", is_real);
        NetworkUrl networkUrl = new NetworkUrl();
        XUtil.Post(networkUrl.GOODS_SOURCE_ADD_URL, map, new MyCallBack<String>() {

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), getResources().getString(R.string.release_success), Toast.LENGTH_SHORT).show();
                        pro.dismiss();
                    }
                });

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), getResources().getString(R.string.networ_anomalies), Toast.LENGTH_SHORT).show();
                        pro.dismiss();
                    }
                });

            }
        });

    }


    private void initViewCarStyleDialog(){
        carStyleDialog = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(),R.layout.dialog_car_style,null);
        final RadioButton rb_ordinary = (RadioButton) view.findViewById(R.id.rb_ordinary);
        final RadioButton rb_special = (RadioButton) view.findViewById(R.id.rb_special);
        final RadioButton rb_dangerous = (RadioButton) view.findViewById(R.id.rb_dangerous);
        TextView tv_footer_no = (TextView) view.findViewById(R.id.tv_footer_no);
        TextView tv_footer_yes = (TextView) view.findViewById(R.id.tv_footer_yes);
        carStyleDialog.setView(view);
        tv_footer_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCarStyle.dismiss();
            }
        });

        tv_footer_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_ordinary.isChecked()){
                    tv_goods_style.setText(rb_ordinary.getText().toString());
                }else if (rb_special.isChecked()){
                    tv_goods_style.setText(rb_special.getText().toString());
                }else {
                    tv_goods_style.setText(rb_dangerous.getText().toString());
                }
                dialogCarStyle.dismiss();

            }
        });

        dialogCarStyle = carStyleDialog.show();


    }

    private void initViewCarNeedsDialog(){
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请选择车辆需求");
        builder.setItems(models, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                carModels = models[which]+"";
                dialog.dismiss();
                showDialogeLength();
            }
        });
    }

    private void showDialogeLength() {
        dialogSize = new AlertDialog.Builder(getActivity());
        dialogSize.setTitle("请选择车长");
        dialogSize.setItems(carLength, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                carSize = carLength[which];
                if (carLength[which].equals("自定义")) {
                    showDialoge();
                } else {
                    tv_cars_needs.setText(carModels+" "+carSize);
                }

                dialog.dismiss();
            }
        });
        dialogSize.show();

    }

    private void showDialoge() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.dialog_car_status_layout, null);
        final EditText edt_printName = (EditText) view.findViewById(R.id.edt_print);
        dialog.setView(view);
        dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String length = edt_printName.getText().toString();
                if (!length.equals("") && length != null) {
                    tv_cars_needs.setText(carModels + "  " + length + "米");
                } else {
                    Toast.makeText(getActivity(), "您还没有输入长度", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });
        dialog.setNeutralButton("取消", null);
        dialog.show();

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
