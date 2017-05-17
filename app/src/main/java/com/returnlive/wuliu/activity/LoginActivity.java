package com.returnlive.wuliu.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.returnlive.wuliu.R;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

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

    private AlertDialog.Builder dialog;
    private static final String TAG = "TAG";
    private AlertDialog dia;
    private View viewDilog;
    private Button btn_reg;
    private EditText et_phone;
    private EditText et_password;
    private EditText et_password_again;
    private EditText et_code;
    private AutoRelativeLayout relResgister;
    private String phone;
    private String pwd;

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
                break;
            case R.id.tv_forgetpwds:
                pageJump(ForgetPassWordActivity.class);
                break;
            case R.id.tv_registered:
                showRegisterDialog();
                break;
        }
    }




    /**
     * "注册"对话框
     */
    private void showRegisterDialog() {
        dialog = new AlertDialog.Builder(this);
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
        relResgister = (AutoRelativeLayout) viewDilog.findViewById(R.id.rel_register);
        dialog.setView(viewDilog);


        btn_reg.setClickable(false);
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast(getResources().getString(R.string.pwds_not_same));
                        }
                    });
                } else {
                    relResgister.setVisibility(View.VISIBLE);
                    btn_reg.setClickable(false);

                }
            }
        });
    }


    private void showToast(String text){
        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    public void pageJump(Class<?> cls) {
        Intent intent = new Intent(getApplicationContext(), cls);
        startActivity(intent);
    }
}
