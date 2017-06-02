package com.returnlive.wuliu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.constant.NetworkUrl;
import com.returnlive.wuliu.constant.ReturnCode;
import com.returnlive.wuliu.entity.CarsDetailsEntity;
import com.returnlive.wuliu.entity.ErrorCodeEntity;
import com.returnlive.wuliu.gson.GsonParsing;
import com.returnlive.wuliu.utils.DateUtilsTime;
import com.returnlive.wuliu.utils.ErrorCode;
import com.returnlive.wuliu.utils.MyCallBack;
import com.returnlive.wuliu.utils.XUtil;
import com.returnlive.wuliu.view.RoundImageView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者： 张梓彬
 * 日期： 2017/6/2 0002
 * 时间： 下午 3:51
 * 描述： 车源详情页面
 */

public class CarDetailsActivity extends AppCompatActivity {
    @ViewInject(R.id.evaluation)
    RatingBar evaluation;
    @ViewInject(R.id.tv_goods_details_start)
    TextView tv_goods_details_start;
    @ViewInject(R.id.tv_goods_details_start_context)
    TextView tv_goods_details_start_context;
    @ViewInject(R.id.tv_goods_details_end)
    TextView tv_goods_details_end;
    @ViewInject(R.id.tv_goods_details_time)
    TextView tv_goods_details_time;

    @ViewInject(R.id.tv_goods_details_specifications)
    TextView tv_goods_details_specifications;
    @ViewInject(R.id.tv_goods_details_cartype)
    TextView tv_goods_details_cartype;
    @ViewInject(R.id.tv_goods_details_message)
    TextView tv_goods_details_message;
    @ViewInject(R.id.img_goods_details_icon)
    RoundImageView img_goods_details_icon;
    @ViewInject(R.id.tv_goods_phone)
    TextView tv_goods_phone;
    @ViewInject(R.id.tv_goods_details_title)
    TextView tv_goods_details_title;
    @ViewInject(R.id.lay_context)
    ScrollView lay_context;
    @ViewInject(R.id.tv_wrong)
    TextView tv_wrong;
    @ViewInject(R.id.lay_pay)
    AutoLinearLayout lay_pay;
    @ViewInject(R.id.btn_show_navigation)
    Button btn_show_navigation;
    @ViewInject(R.id.lay_goods)
    AutoRelativeLayout lay_goods;
    private String[] models = {"平板", "高栏", "厢式", "保温", "冷藏", "集装箱", "面包车", "危险品", "其他"};
    private ProgressDialog pro;
    private String id;
    private static final String TAG = "CarDetailsActivity";
    private CarsDetailsEntity.CarsDetailsBean carsDetailsBean;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_details);
        x.view().inject(this);

        initView();
    }

    private void initView() {
        lay_goods.setVisibility(View.GONE);
        btn_show_navigation.setVisibility(View.GONE);
        LayerDrawable ld_stars = (LayerDrawable) evaluation.getProgressDrawable();
        ld_stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.orange), PorterDuff.Mode.SRC_ATOP);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        pro = new ProgressDialog(this);
        pro.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pro.setMessage("信息加载中...");
        pro.setCanceledOnTouchOutside(false);
        pro.setCancelable(false);
        pro.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                carsDetailsInterface();

            }
        }).start();

    }

    private void carsDetailsInterface() {
        Log.e(TAG, "id: "+id );
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        NetworkUrl networkUrl = new NetworkUrl();
        Log.e(TAG, "url: "+networkUrl.CARS_DETAIL_SOURCE_ADD_URL );
        XUtil.Get(networkUrl.CARS_DETAIL_SOURCE_ADD_URL,map, new  MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Message msg = new Message();
                msg.obj = result;
                carsDetailsHandler.sendMessage(msg);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pro.dismiss();
                        lay_context.setVisibility(View.GONE);
                        lay_pay.setVisibility(View.GONE);
                        tv_wrong.setVisibility(View.VISIBLE);
                        Toast.makeText(CarDetailsActivity.this, getResources().getString(R.string.networ_anomalies), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Event(value = {R.id.img_back, R.id.btn_details_pay_deposit, R.id.btn_details_Contact_owner})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_details_pay_deposit:
                break;
            case R.id.btn_details_Contact_owner:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+tv_goods_phone.getText().toString()));
                startActivity(intent);
                break;
        }
    }

    private Handler carsDetailsHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            if (result.indexOf(ReturnCode.SUCCESS) > 0) {
                carsDetailsBean = GsonParsing.gsonCarsDetails(result);
                tv_goods_details_start.setText(carsDetailsBean.getStart());
                tv_goods_details_end.setText(carsDetailsBean.getEnd());
                DateUtilsTime timeUtils = new DateUtilsTime();
                String time = timeUtils.getDay(carsDetailsBean.getCar_time());
                tv_goods_details_time.setText(time);
                tv_goods_details_specifications.setText(carsDetailsBean.getWeight() + "吨/" + carsDetailsBean.getVolume() + "方");
                String carType = models[carsDetailsBean.getCar_type()];
                tv_goods_details_cartype.setText(carType + " 车长");
                tv_goods_details_message.setText("备注");
                tv_goods_phone.setText(carsDetailsBean.getPhone());

            }else {
                errorCode(result);
            }
            pro.dismiss();

        }
    };


    //错误码返回值解析并判断
    private void errorCode(String result) {
        ErrorCodeEntity errorCodeEntity = GsonParsing.sendCodeError(result);
        String errorCode = errorCodeEntity.getCode();
        ErrorCode code = new ErrorCode(this);
        code.judge(errorCode);
    }
}
