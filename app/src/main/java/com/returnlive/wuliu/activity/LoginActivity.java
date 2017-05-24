package com.returnlive.wuliu.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.constant.NetworkUrl;
import com.returnlive.wuliu.constant.ReturnCode;
import com.returnlive.wuliu.entity.ErrorCodeEntity;
import com.returnlive.wuliu.entity.LoginSuccessEntity;
import com.returnlive.wuliu.gson.GsonParsing;
import com.returnlive.wuliu.utils.ErrorCode;
import com.returnlive.wuliu.utils.MyCallBack;
import com.returnlive.wuliu.utils.SharedPreferencesUtils;
import com.returnlive.wuliu.utils.XUtil;
import com.zhy.autolayout.AutoRelativeLayout;
import org.json.JSONException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：张梓彬
 * 日期：2017/5/17 0017
 * 时间：下午 8:31
 * 描述：登录页面
 */

public class LoginActivity extends AppCompatActivity {
    @ViewInject(R.id.edt_username)
    EditText edt_username;
    @ViewInject(R.id.edt_password)
    EditText edt_password;

    @ViewInject(R.id.rel_login)
    AutoRelativeLayout rel_login;
    @ViewInject(R.id.img_loging_delect1)
    ImageView userDelect;
    @ViewInject(R.id.img_loging_password_delect)
    ImageView passwordDelect;
    @ViewInject(R.id.btn_login)
    Button btn_login;

    private AlertDialog.Builder dialog;
    private AlertDialog dia;
    private View viewDilog;
    private Button btn_reg;
    private EditText et_phone;
    private EditText et_password;
    private EditText et_password_again;
    private EditText et_code;
    private String phone;
    private String pwd;
    private static final String TAG = "LoginActivity";
    private CountDownTimer mCountDownTimer;
    private long mTimeRemaining;
    private static final long COUNTER_TIME = 120;
    private ProgressDialog pro;
    private SharedPreferencesUtils sharedPreferencesUtils;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        x.view().inject(this);
        initView();
    }




    private void initView() {
        edt_username.setHintTextColor(Color.argb(125, 255, 255, 255));
        edt_password.setHintTextColor(Color.argb(125, 255, 255, 255));
        edt_username.setTextColor(Color.argb(125, 255, 255, 255));
        edt_password.setTextColor(Color.argb(125, 255, 255, 255));
        //设置控件获取焦点监听
        edt_username.addTextChangedListener(textWatcher);
        edt_password.addTextChangedListener(textWatcher);
        sharedPreferencesUtils = new SharedPreferencesUtils(this);
        sharedPreferencesUtils.sharedPreferenceRead();
    }

    @Override
    protected void onResume() {
        super.onResume();
        edt_username.setHintTextColor(Color.argb(125, 255, 255, 255));
        edt_password.setHintTextColor(Color.argb(125, 255, 255, 255));
        edt_username.setTextColor(Color.argb(125, 255, 255, 255));
        edt_password.setTextColor(Color.argb(125, 255, 255, 255));


    }

    /**
     * “登录”控件获取焦点监听
     */
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            /**
             * "登录"设置控件“删除”按钮是否显示(控件是否为空)
             */

            if (edt_username.length() == 0) {
                userDelect.setVisibility(View.GONE);
            } else {
                userDelect.setVisibility(View.VISIBLE);
                userDelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edt_username.setText("");
                    }
                });
            }
            if (edt_password.length() == 0) {
                passwordDelect.setVisibility(View.GONE);
            } else {
                passwordDelect.setVisibility(View.VISIBLE);
                passwordDelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edt_password.setText("");
                    }
                });
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Event(value = {R.id.btn_login, R.id.tv_forgetpwds, R.id.tv_registered})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                rel_login.setVisibility(View.VISIBLE);
                btn_login.setClickable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loginInterface();
                    }
                }).start();
                break;
            case R.id.tv_forgetpwds:
                pageJump(ForgetPassWordActivity.class);
                break;
            case R.id.tv_registered:
                showRegisterDialog();
                break;
        }
    }

    //登录接口
    private void loginInterface() {
        String userName = edt_username.getText().toString().trim();
        String userPwds = edt_password.getText().toString().trim();
        Map<String, Object> map = new HashMap<>();
        map.put("phone", userName);
        map.put("pwd", userPwds);
        XUtil.Post(NetworkUrl.LOGIN_URL, map, new MyCallBack<String>() {

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Message msg = new Message();
                msg.obj = result;
                loginHandler.sendMessage(msg);
                rel_login.setVisibility(View.GONE);
                btn_login.setClickable(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                runOnUiShowToast(getResources().getString(R.string.networ_anomalies));
                rel_login.setVisibility(View.GONE);
                btn_login.setClickable(true);
            }
        });
    }


    /**
     * "注册"对话框
     */
    private void showRegisterDialog() {
        dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        viewDilog = View.inflate(this, R.layout.dialog_register, null);
        Button btn_dismiss = (Button) viewDilog.findViewById(R.id.btn_dismiss);
        btn_reg = (Button) viewDilog.findViewById(R.id.btn_reg);
        //电话号码
        et_phone = (EditText) viewDilog.findViewById(R.id.et_phone);
        //首次输入密码
        et_password = (EditText) viewDilog.findViewById(R.id.et_password);
        //再次输入密码
        et_password_again = (EditText) viewDilog.findViewById(R.id.et_password_again);
        //验证码
        et_code = (EditText) viewDilog.findViewById(R.id.et_code);
        Button btn_code = (Button) viewDilog.findViewById(R.id.btn_code);//获取验证码按钮
        //加载布局
        dialog.setView(viewDilog);

        dia = dialog.show();
        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia.dismiss();
            }
        });
        //验证码
        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = et_phone.getText().toString().trim();
                if (phone == null || "".equals(phone)) {
                    showToast(getResources().getString(R.string.phone_not_empty));
                } else {
                    createTimer(COUNTER_TIME, viewDilog);//验证码倒计时时间方法

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sendCodeInterface();
                        }
                    }).start();
                }
            }
        });
        //注册
        btn_reg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                phone = et_phone.getText().toString().trim();
                pwd = et_password.getText().toString().trim();
                String pwdagain = et_password_again.getText().toString().trim();
                final String msgcode = et_code.getText().toString().trim();
                if (phone == null || "".equals(phone)) {
                    showToast(getResources().getString(R.string.phone_not_empty));
                } else if (!pwd.equals(pwdagain)) {
                    showToast(getResources().getString(R.string.pwds_not_same));
                }else if ((pwd == null || "".equals(pwd))) {
                    showToast(getResources().getString(R.string.pwds_not_empty));
                }else if ((pwdagain == null || "".equals(pwdagain))) {
                    showToast(getResources().getString(R.string.pwds_not_empty));
                } else {
                    pro = new ProgressDialog(LoginActivity.this);
                    pro.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pro.setMessage(getResources().getString(R.string.registering));
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
                            registerInterface();
                        }
                    }).start();


                }
            }
        });
    }

    //短信请求接口
    private void sendCodeInterface() {
        phone = et_phone.getText().toString().trim();
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        XUtil.Post(NetworkUrl.SENDCODE_URL, map, new MyCallBack<String>() {

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Message msg = new Message();
                msg.obj = result;
                sendCodeHandler.sendMessage(msg);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                runOnUiShowToast(getResources().getString(R.string.networ_anomalies));
            }
        });

    }

    //注册接口
    private void registerInterface() {
        phone = et_phone.getText().toString().trim();
        pwd = et_password.getText().toString().trim();
        String msgcode = et_code.getText().toString().trim();
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("pwd", pwd);
        map.put("msgcode", msgcode);
        XUtil.Post(NetworkUrl.REGISTER_URL, map, new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Message msg = new Message();
                msg.obj = result;
                registerHandler.sendMessage(msg);
                edt_username.setText(phone);
                edt_password.setText(pwd);
                pro.dismiss();
                dia.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                runOnUiShowToast(getResources().getString(R.string.networ_anomalies));
                pro.dismiss();
            }
        });

    }


    private void showToast(String text) {
        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    private void runOnUiShowToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void pageJump(Class<?> cls) {
        Intent intent = new Intent(getApplicationContext(), cls);
        startActivity(intent);
    }

    /**
     * 验证码获取时间
     *
     * @param time 验证码获取的总时间
     * @param view 获取的布局控件
     */
    private void createTimer(long time, View view) {
        final Button btn_code = (Button) view.findViewById(R.id.btn_code);
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(time * 1000, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                mTimeRemaining = ((millisUnitFinished / 1000) + 1);
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

    //短信请求handler处理
    private Handler sendCodeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            if (result.indexOf(ReturnCode.SUCCESS) > 0) {
                //短信接口获取成功
                runOnUiShowToast(getResources().getString(R.string.msg_send_success));

            } else {
                //短信接口错误码解析
                errorCode(result);
            }
        }
    };

    //注册请求handler处理
    private Handler registerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            if (result.indexOf(ReturnCode.SUCCESS) > 0) {
                //短信接口获取成功
                runOnUiShowToast(getResources().getString(R.string.register_success));
            } else {
                errorCode(result);
            }
        }
    };

    //登录请求handler处理
    private Handler loginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            Log.e(TAG, "result: "+result);
            if (result.indexOf(ReturnCode.SUCCESS) > 0) {
                runOnUiShowToast(getResources().getString(R.string.login_success));
                LoginSuccessEntity loginSuccessEntity = GsonParsing.gsonLogin(result);
                GsonParsing.uid = loginSuccessEntity.getId();
                try {
                    GsonParsing.mSesson = GsonParsing.gsonLoginSesson(result);
                    Log.e(TAG, "handleMessage: "+GsonParsing.mSesson );
                    pageJump(OwnerMainActivity.class);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            } else {
                errorCode(result);
            }

        }
    };


    //错误码返回值解析并判断
    private void errorCode(String result) {
        ErrorCodeEntity errorCodeEntity = GsonParsing.sendCodeError(result);
        String errorCode = errorCodeEntity.getCode();
        ErrorCode code = new ErrorCode(LoginActivity.this);
        code.judge(errorCode);
    }
}
