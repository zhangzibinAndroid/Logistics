package com.returnlive.wuliu.activity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.constant.NetworkUrl;
import com.returnlive.wuliu.constant.ReturnCode;
import com.returnlive.wuliu.entity.ErrorCodeEntity;
import com.returnlive.wuliu.gson.GsonParsing;
import com.returnlive.wuliu.utils.ErrorCode;
import com.returnlive.wuliu.utils.MyCallBack;
import com.returnlive.wuliu.utils.XUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;


/**
 * 作者：张梓彬
 * 日期：2017/5/17 0017
 * 时间：下午 10:25
 * 描述：忘记密码页面
 */
public class ForgetPassWordActivity extends AppCompatActivity {
    @ViewInject(R.id.ftoolbar)
    Toolbar ftoolbar;
    @ViewInject(R.id.edt_phone)
    EditText edt_phone;
    @ViewInject(R.id.edt_code)
    EditText edt_code;
    @ViewInject(R.id.edt_new_pwds)
    EditText edt_new_pwds;
    @ViewInject(R.id.edt_pwds_ok)
    EditText edt_pwds_ok;

    @ViewInject(R.id.btn_codenumber)
    Button btn_code;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    private CountDownTimer mCountDownTimer;
    private static final long COUNTER_TIME = 120;
    private ProgressDialog pro;
    private String phone;
    private String pwds;
    private String pwds_again;
    private String msgcode;
    private static final String TAG = "ForgetPassWordActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_word);
        x.view().inject(this);
    }


    /**
     * 验证码获取时间
     *
     * @param time 验证码获取的总时间
     */
    private void createTimer(long time) {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(time * 1000, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                long mTimeRemaining = ((millisUnitFinished / 1000) + 1);
                btn_code.setText(mTimeRemaining + "后重新获取");
                btn_code.setClickable(false);
                btn_code.setBackground(getResources().getDrawable(R.drawable.shape_color_pwd));
            }

            @Override
            public void onFinish() {
                //重新获取
                btn_code.setText(getResources().getString(R.string.restart_getcode));
                btn_code.setClickable(true);
                btn_code.setBackground(getResources().getDrawable(R.drawable.shape_color_pwd2));
            }
        };
        mCountDownTimer.start();
    }


    @Event(value = {R.id.img_back, R.id.btn_codenumber, R.id.btn_ok})
    private void onClick(View view) {
        phone  = edt_phone.getText().toString().trim();
        pwds = edt_new_pwds.getText().toString().trim();
        pwds_again = edt_pwds_ok.getText().toString().trim();
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_codenumber:
                if (phone.equals("")||phone == null){
                    showToast(getResources().getString(R.string.phone_not_empty));
                }else {
                    createTimer(COUNTER_TIME);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sendCodeInterface();
                        }
                    }).start();
                }

                break;
            case R.id.btn_ok:
                if (phone.equals("")||phone==null){
                    showToast(getResources().getString(R.string.phone_not_empty));
                }else if (!pwds.equals(pwds_again)) {
                    showToast(getResources().getString(R.string.pwds_not_same));
                }else if ((pwds == null || "".equals(pwds))) {
                    showToast(getResources().getString(R.string.pwds_not_empty));
                }else if ((pwds_again == null || "".equals(pwds_again))) {
                    showToast(getResources().getString(R.string.pwds_not_empty));
                }else {
                    pro = new ProgressDialog(ForgetPassWordActivity.this);
                    pro.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pro.setMessage(getResources().getString(R.string.please_waitting));
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

                            resetPwdInterface();
                        }
                    }).start();
                }




                break;
        }
    }
    //修改密码接口
    private void resetPwdInterface() {
        phone  = edt_phone.getText().toString().trim();
        pwds = edt_new_pwds.getText().toString().trim();
        msgcode = edt_code.getText().toString().trim();
        Log.e(TAG, "resetPwdInterface: "+msgcode);
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("pwd", pwds);
        map.put("msgcode", msgcode);
        XUtil.Post(NetworkUrl.FGORGET_PASSWORD_URL, map, new MyCallBack<String>() {

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Message msg = new Message();
                msg.obj = result;
                resetPwdHandle.sendMessage(msg);
                pro.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                runOnUiShowToast(getResources().getString(R.string.networ_anomalies));
                pro.dismiss();

            }
        });
    }





    //短信接口回调
    private void sendCodeInterface() {
        phone  = edt_phone.getText().toString().trim();
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("type", "2");
        XUtil.Post(NetworkUrl.SENDCODE_SECOND_URL, map, new MyCallBack<String>() {

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Message msg = new Message();
                msg.obj = result;
                sendCodeHandle.sendMessage(msg);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                runOnUiShowToast(getResources().getString(R.string.networ_anomalies));
            }
        });

    }

    private void runOnUiShowToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ForgetPassWordActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showToast(String text) {
        Toast.makeText(ForgetPassWordActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    //短信接口handler处理
    private Handler sendCodeHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            if (result.indexOf(ReturnCode.SUCCESS) > 0) {
                runOnUiShowToast(getResources().getString(R.string.msg_send_success));
            }else {
                errorCode(result);
            }

        }
    };

    //重设密码handler处理
    private Handler resetPwdHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            Log.e(TAG, "result: ="+result );
            if (result.indexOf(ReturnCode.SUCCESS) > 0) {
                runOnUiShowToast(getResources().getString(R.string.pwds_reset_success));
            }else {
                errorCode(result);
            }
        }
    };

    //错误码返回值解析并判断
    private void errorCode(String result) {
        ErrorCodeEntity errorCodeEntity = GsonParsing.sendCodeError(result);
        String errorCode = errorCodeEntity.getCode();
        ErrorCode code = new ErrorCode(ForgetPassWordActivity.this);
        code.judge(errorCode);
    }
}
