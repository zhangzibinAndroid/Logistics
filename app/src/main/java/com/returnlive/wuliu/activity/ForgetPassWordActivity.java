package com.returnlive.wuliu.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.returnlive.wuliu.R;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


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
    @ViewInject(R.id.rel_loading)
    AutoRelativeLayout rel_loading;
    @ViewInject(R.id.btn_codenumber)
    Button btn_code;
    private CountDownTimer mCountDownTimer;
    private static final long COUNTER_TIME = 120;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_word);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(ftoolbar);
        //给左上角加一个返回图标,需要重写菜单点击事件，否则点击无效
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Event(value = {R.id.btn_codenumber, R.id.btn_ok})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_codenumber:
                createTimer(COUNTER_TIME);
                break;
            case R.id.btn_ok:
                break;
        }
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

    //给左上角加一个返回图标,需要重写菜单点击事件，否则点击无效
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
