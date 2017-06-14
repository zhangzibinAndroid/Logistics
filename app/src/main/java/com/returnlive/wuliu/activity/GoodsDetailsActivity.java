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
import com.returnlive.wuliu.entity.ErrorCodeEntity;
import com.returnlive.wuliu.entity.GoodsDetailsEntity;
import com.returnlive.wuliu.gson.GsonParsing;
import com.returnlive.wuliu.map.RouteActivity;
import com.returnlive.wuliu.utils.DateUtilsTime;
import com.returnlive.wuliu.utils.ErrorCode;
import com.returnlive.wuliu.utils.MyCallBack;
import com.returnlive.wuliu.utils.XUtil;
import com.returnlive.wuliu.view.RoundImageView;
import com.zhy.autolayout.AutoLinearLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者： 张梓彬
 * 日期： 2017/5/26 0026
 * 时间： 上午 11:43
 * 描述： 货源详情页面
 */
public class GoodsDetailsActivity extends AppCompatActivity {
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
    @ViewInject(R.id.tv_goods_details_goods)
    TextView tv_goods_details_goods;
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
    @ViewInject(R.id.btn_show_navigation)
    Button btn_show_navigation;
    @ViewInject(R.id.lay_pay)
    AutoLinearLayout lay_pay;

    private String id;
    private GoodsDetailsEntity.GoodsDetailsBean goodsDetailsBean;
    private String[] models = {"平板", "高栏", "厢式", "保温", "冷藏", "集装箱", "面包车", "危险品", "其他"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        x.view().inject(this);
        initView();

    }

    private void initView() {
        LayerDrawable ld_stars = (LayerDrawable) evaluation.getProgressDrawable();
        ld_stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.orange), PorterDuff.Mode.SRC_ATOP);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        new Thread(new Runnable() {
            @Override
            public void run() {
                goodsDetailsInterface();

            }
        }).start();

    }

    //货源详情列表接口
    private void goodsDetailsInterface() {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        NetworkUrl networkUrl = new NetworkUrl();
        XUtil.Get(networkUrl.GOODS_DETAIL_SOURCE_ADD_URL, map, new MyCallBack<String>() {

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Message msg = new Message();
                msg.obj = result;
                goodsDetailsHandler.sendMessage(msg);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lay_context.setVisibility(View.GONE);
                        lay_pay.setVisibility(View.GONE);
                        tv_wrong.setVisibility(View.VISIBLE);
                        Toast.makeText(GoodsDetailsActivity.this, getResources().getString(R.string.networ_anomalies), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Event(value = {R.id.img_back, R.id.btn_show_navigation, R.id.btn_goods_callphone, R.id.btn_details_pay_deposit, R.id.btn_details_Contact_owner})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_show_navigation://导航
                Intent intent = new Intent(this, RouteActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_goods_callphone://查看评价
                break;
            case R.id.btn_details_pay_deposit://支付定金
                break;
            case R.id.btn_details_Contact_owner://联系货主
                Intent intentphone = new Intent(Intent.ACTION_DIAL);
                intentphone.setData(Uri.parse("tel:"+tv_goods_phone.getText().toString()));
                startActivity(intentphone);
                break;
        }
    }

    private Handler goodsDetailsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            if (result.indexOf(ReturnCode.SUCCESS) > 0) {
                goodsDetailsBean = GsonParsing.gsonGoodsDetails(result);
                tv_goods_details_start.setText(goodsDetailsBean.getStart());//出发地
                tv_goods_details_end.setText(goodsDetailsBean.getEnd());//目的地
                DateUtilsTime timeUtils = new DateUtilsTime();
                String time = timeUtils.getDay(goodsDetailsBean.getCar_time());
                tv_goods_details_time.setText(time);//装车时间
                tv_goods_details_goods.setText(goodsDetailsBean.getGoods_name());//货品名称
                tv_goods_details_specifications.setText(goodsDetailsBean.getWeight() + "吨/" + goodsDetailsBean.getVolume() + "方");//规格
                String carType = models[goodsDetailsBean.getCar_type()];
                tv_goods_details_cartype.setText(carType + " 车长");//车型
                tv_goods_details_message.setText(goodsDetailsBean.getRemarks());
                tv_goods_phone.setText(goodsDetailsBean.getPhone());

            } else {
                errorCode(result);
            }
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
