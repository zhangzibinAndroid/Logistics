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
 * Time : 上午 10:30
 * Describe : 帮助页面
 */
public class HelpCenterActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_helptitle)
    TextView tv_helptitle;
    @ViewInject(R.id.tv_helpcontext)
    TextView tv_helpcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        tv_helptitle.setText("标题");
        tv_helpcontext.setText("\u3000\u3000 +首行缩进");
    }


    @Event(R.id.img_back)
    private void onClick(View view) {
        finish();
    }
}
