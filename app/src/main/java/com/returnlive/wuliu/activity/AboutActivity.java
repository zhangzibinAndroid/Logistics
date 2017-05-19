package com.returnlive.wuliu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.returnlive.wuliu.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
/**
 * @author 张梓彬
 * Data : 2017/5/19 0019
 * Time : 上午 10:20
 * Describe : 关于页面
 */
public class AboutActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_aboutcontext)
    TextView tv_aboutcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        tv_aboutcontext.setText("\u3000\u3000+内容");
    }

    @Event(R.id.img_back)
    private void onClick(View view) {
        finish();
    }
}
