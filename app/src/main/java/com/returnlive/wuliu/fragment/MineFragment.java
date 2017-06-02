package com.returnlive.wuliu.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.activity.DriverCertificationActivity;
import com.returnlive.wuliu.activity.LoginActivity;
import com.returnlive.wuliu.activity.OwnerMainActivity;
import com.returnlive.wuliu.activity.SettingActivity;
import com.returnlive.wuliu.activity.ShipperCertificationActivity;
import com.returnlive.wuliu.constant.ConstantNumber;
import com.returnlive.wuliu.constant.NetworkUrl;
import com.returnlive.wuliu.entity.UserMessageEntity;
import com.returnlive.wuliu.gson.GsonParsing;
import com.returnlive.wuliu.utils.MyCallBack;
import com.returnlive.wuliu.utils.SharedPreferencesUtils;
import com.returnlive.wuliu.utils.XUtil;
import com.returnlive.wuliu.view.RoundImageView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 张梓彬
 * Data : 2017/5/19 0019
 * Time : 上午 10:47
 * Describe : 车主我的页面
 */
public class MineFragment extends Fragment {
    @ViewInject(R.id.tv_version_up)
    TextView tv_version_up;
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
    @ViewInject(R.id.tv_driver_certification)
    TextView tv_driver_certification;
    @ViewInject(R.id.lay_mycertification)
    AutoRelativeLayout lay_mycertification;
    @ViewInject(R.id.lay_my_driver_certification)
    AutoRelativeLayout lay_my_driver_certification;
    @ViewInject(R.id.tv_setting)
    TextView tv_setting;
    @ViewInject(R.id.lay_set)
    AutoRelativeLayout lay_set;
    private View view;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private static final String TAG = "MineFragment";
    private OwnerMainActivity ownerMainActivity;
    private ProgressDialog pro;
    public static boolean isChecked = false;//默认司机版
    private String CERTIFICATION_CODE = "1";

    public MineFragment() {
    }

    protected Handler userMessageHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            UserMessageEntity.UserBean userMessage =  GsonParsing.gsonUserMessage(result);
            if (userMessage.getCar_auth().equals(CERTIFICATION_CODE)){
                tv_driver_certification.setText("（已认证）");
            } else{
                tv_driver_certification.setText("（未认证）");

            }

            if (userMessage.getUser_auth().equals(CERTIFICATION_CODE)) {
                tv_certification.setText("（已认证）");
            }else {
                tv_certification.setText("（未认证）");
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        x.view().inject(this, view);
        ownerMainActivity = new OwnerMainActivity();
        sharedPreferencesUtils = new SharedPreferencesUtils(getActivity());
        sharedPreferencesUtils.sharedPreferenceRead();
        isChecked = sharedPreferencesUtils.loginState;
        initView();
        addUserMessage();
        return view;
    }

    private void addUserMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getUserMessage();
            }
        }).start();

    }

    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new java.util.Date(Long.valueOf(seconds+"000")));
    }

    private void getUserMessage() {
        NetworkUrl networkUrl = new NetworkUrl();
        XUtil.Get(networkUrl.USER_MESSAGE_URL, null, new MyCallBack<String>() {

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Message msg = new Message();
                msg.obj = result;
                userMessageHandler.sendMessage(msg);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);

                runOnUiShowToast(getResources().getString(R.string.networ_anomalies));
            }
        });

    }

    private void runOnUiShowToast(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {

        if (isChecked){
            tv_version_up.setText(getResources().getString(R.string.tv_version_owner));
            tv_driver.setTextColor(getResources().getColor(R.color.orange));
            tv_shipper.setTextColor(getResources().getColor(R.color.gray));
            ownerMainActivity.lay_owner = (AutoLinearLayout) getActivity().findViewById(R.id.lay_owner);
            ownerMainActivity.lay_ship = (AutoLinearLayout) getActivity().findViewById(R.id.lay_ship);
            ownerMainActivity.tv_main_me = (TextView) getActivity().findViewById(R.id.tv_main_me);
            ownerMainActivity.tv_main_me.setSelected(true);
            ownerMainActivity.tv_main_me.setTextColor(getResources().getColor(R.color.textselsecond));
            ownerMainActivity.showLay(true);
            lay_carmasyer.setVisibility(View.VISIBLE);
            lay_shipper.setVisibility(View.GONE);
            lay_mycertification.setVisibility(View.GONE);
            lay_my_driver_certification.setVisibility(View.VISIBLE);
        }else {
            tv_version_up.setText(getResources().getString(R.string.tv_version_shipper));
            tv_driver.setTextColor(getResources().getColor(R.color.gray));
            tv_shipper.setTextColor(getResources().getColor(R.color.orange));
            ownerMainActivity.lay_owner = (AutoLinearLayout) getActivity().findViewById(R.id.lay_owner);
            ownerMainActivity.lay_ship = (AutoLinearLayout) getActivity().findViewById(R.id.lay_ship);
            ownerMainActivity.tv_main_me2 = (TextView) getActivity().findViewById(R.id.tv_main_me2);
            ownerMainActivity.tv_main_me2.setSelected(true);
            ownerMainActivity.tv_main_me2.setTextColor(getResources().getColor(R.color.textselsecond));
            ownerMainActivity.showLay(false);
            lay_carmasyer.setVisibility(View.GONE);
            lay_shipper.setVisibility(View.VISIBLE);
            lay_mycertification.setVisibility(View.VISIBLE);
            lay_my_driver_certification.setVisibility(View.GONE);
        }




    }

    private void setShowVersionsDialogPlus(final String msgNow, final String msgLater, final boolean isCheck) {
        //header头部
        View viewHeader = LayoutInflater.from(getActivity()).inflate(R.layout.header_version, null);
        TextView tvTitle= (TextView) viewHeader.findViewById(R.id.tv_heard_title);
        tvTitle.setText("当前版本："+msgNow);
        //context内容
        View viewContex = LayoutInflater.from(getActivity()).inflate(R.layout.item_version_me, null);
        TextView tvContext= (TextView) viewContex.findViewById(R.id.tv_heard_context);
        tvContext.setText("您确定要切换为："+msgLater+"？");
        //弹窗
        DialogPlus dialogPlus=DialogPlus.newDialog(getActivity())
                .setCancelable(false)
                .setContentHolder(new ViewHolder(viewContex))
                .setHeader(viewHeader)
                .setFooter(R.layout.footer_version)//添加脚布局
                .setInAnimation(R.anim.dialog_enter_anim)//类似于IOS底部出现效果
                .setContentBackgroundResource(R.color.lavenderblush)//设置对话框背景颜色为透明（为了边角有圆角弧度）
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.tv_footer_no:
                                dialog.dismiss();
                                break;
                            case R.id.tv_footer_yes:
                                addUserMessage();
                                dialog.dismiss();
                                showProgressDialog();
                                isChecked = isCheck;
                                initView();
                                sharedPreferencesUtils.sharedPreferenceSave(isCheck);

                                break;
                        }
                    }
                })
                .setGravity(Gravity.CENTER)
                .setExpanded(true)
                .setCancelable(true)
                .create();
        dialogPlus.show();
    }

    private void showProgressDialog() {
        pro = new ProgressDialog(getActivity());
        pro.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pro.setMessage("切换中...");
        pro.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pro.dismiss();
            }
        }).start();
    }

    @Event(value = {R.id.img_driver, R.id.img_shipper, R.id.lay_balance, R.id.lay_topup, R.id.lay_mycertification, R.id.lay_set,R.id.lay_my_driver_certification})
    private void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_driver:
                if (tv_version_up.getText().toString().equals(getResources().getString(R.string.tv_version_shipper))){
                    setShowVersionsDialogPlus("货主版","司机版",true);
                }

                break;
            case R.id.img_shipper:
                if (tv_version_up.getText().toString().equals(getResources().getString(R.string.tv_version_owner))){

                    setShowVersionsDialogPlus("司机版","货主版",false);
                }

                break;
            case R.id.lay_balance:
                break;
            case R.id.lay_topup:
                break;
            case R.id.lay_mycertification:
                pageJump(ShipperCertificationActivity.class);//货主认证
                break;

            case R.id.lay_my_driver_certification:
                pageJump(DriverCertificationActivity.class);//司机认证

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
