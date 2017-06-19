package com.returnlive.wuliu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.adapter.TableAdapter;
import com.returnlive.wuliu.fragment.OrderAllFragment;
import com.returnlive.wuliu.fragment.OrderFinishFragment;
import com.returnlive.wuliu.fragment.OrderUnfinishFragment;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 张梓彬
 * 日期： 2017/6/19 0019
 * 时间： 上午 10:42
 * 描述： 订单详情页面
 */
public class OrderDetailsActivity extends AppCompatActivity {

    @ViewInject(R.id.tab_sel_title)
    TabLayout tab_sel_title;
    @ViewInject(R.id.tab_viewpager)
    ViewPager tab_viewpager;
    private OrderAllFragment orderAllFragment;
    private OrderFinishFragment orderFinishFragment;
    private OrderUnfinishFragment orderUnfinishFragment;

    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;
    private TableAdapter tableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        orderAllFragment = new OrderAllFragment();
        orderFinishFragment = new OrderFinishFragment();
        orderUnfinishFragment = new OrderUnfinishFragment();

        list_fragment = new ArrayList<>();
        list_fragment.add(orderAllFragment);
        list_fragment.add(orderFinishFragment);
        list_fragment.add(orderUnfinishFragment);

        //将名称加载tab名字列表
        list_title = new ArrayList<>();
        list_title.add(getResources().getString(R.string.all));
        list_title.add(getResources().getString(R.string.finish));
        list_title.add(getResources().getString(R.string.unfinish));

        tab_sel_title.setTabMode(TabLayout.MODE_FIXED);        //设置TabLayout的模式

        //为TabLayout添加tab名称
        tab_sel_title.addTab(tab_sel_title.newTab().setText(list_title.get(0)));
        tab_sel_title.addTab(tab_sel_title.newTab().setText(list_title.get(1)));
        tab_sel_title.addTab(tab_sel_title.newTab().setText(list_title.get(2)));
        tableAdapter = new TableAdapter(getSupportFragmentManager(),list_fragment,list_title);

        //viewpager加载adapter
        tab_viewpager.setAdapter(tableAdapter);

        //TabLayout加载viewpager
        tab_sel_title.setupWithViewPager(tab_viewpager);

    }

    @Event(R.id.img_back)
    private void onClick(View view) {
        finish();
    }
}
