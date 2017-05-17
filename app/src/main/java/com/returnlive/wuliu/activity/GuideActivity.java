package com.returnlive.wuliu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.view.Xcircleindicator;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 张梓彬
 * Data : 2017/5/17 0017
 * Time : 下午 4:33
 * Describe : 引导页
 */
public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener {
    @ViewInject(R.id.wel_img)
    SimpleDraweeView welImg;
    @ViewInject(R.id.welcome_viewpager)
    ViewPager viewPager;
    @ViewInject(R.id.Xcircleindicator)
    Xcircleindicator mXcircleindicator;
    @ViewInject(R.id.img_guild)
    ImageView img_guild;
    @ViewInject(R.id.tv_djs)
    TextView tv_djs;
    private static final long TIME = 3;
    private long mTimeRemaining;
    private boolean isEnter = false;
    private CountDownTimer mCountDownTimer;

    //图片。可替换为网络图片
    //用来保存状态，判断出现一张还是四张图片
    private SharedPreferences sharedPreferences;
    //圆点
    private int i;
    private PagerAdapter adapter;
    private List<ImageView> imageList;
    private int[] ims;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这里是SimpleDraweeView的初始化
        Fresco.initialize(this);
        setContentView(R.layout.activity_guide);
        x.view().inject(this);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        //进行获取判断值
        i = sharedPreferences.getInt("welcome", 0);
        init();
    }

    private void init() {

        //判断
        if (i == 0) {
            //把那一张图给gone掉
            welImg.setVisibility(View.GONE);

            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt("welcome", 1);
            edit.commit();
            initData();
            initCtrl();
            mXcircleindicator.setVisibility(View.VISIBLE);
            viewPager.addOnPageChangeListener(this);
        } else {
            //显示一张图
            mXcircleindicator.setVisibility(View.GONE);
            welImg.setVisibility(View.VISIBLE);
            welImg.setImageURI(Uri.parse("res:///" + R.mipmap.guidepage3));
            tv_djs.setVisibility(View.VISIBLE);
            tv_djs.setBackgroundColor(Color.argb(125, 255, 255, 255));
            startTime(TIME);

        }

    }

    //进行viewpager的适配
    private void initCtrl() {
        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(imageList.get(position));
                return imageList.get(position);
            }
        };
        viewPager.setAdapter(adapter);
        //设置总页数
        mXcircleindicator.initData(imageList.size(), 0);
        //设置当前页面
        mXcircleindicator.setCurrentPage(0);
        //这里对viewpager进行监听，从而对圆点进行设置
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mXcircleindicator.setCurrentPage(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //添加图片到list里面
    private void initData() {
        imageList = new ArrayList<ImageView>();
        ims = new int[]{R.mipmap.guidepage1, R.mipmap.guidepage2, R.mipmap.guidepage3};
        for (int i = 0; i < ims.length; i++) {
            SimpleDraweeView imageView = new SimpleDraweeView(this);
            imageView.setImageURI(Uri.parse("res:///" + ims[i]));
            //设置图片的比例   （图片，设置比例，与XY相符）
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageList.add(imageView);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == 2) {
            img_guild.setVisibility(View.VISIBLE);


        } else {
            img_guild.setVisibility(View.GONE);
            tv_djs.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void pageJump(Class<?> cls) {
        Intent intent = new Intent(getApplicationContext(), cls);
        startActivity(intent);
        finish();
    }

    private void startTime(long time) {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(time * 1000, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                mTimeRemaining = ((millisUnitFinished / 1000) + 1);
                tv_djs.setText(mTimeRemaining + " 跳过");
                tv_djs.setClickable(true);

            }

            @Override
            public void onFinish() {
                if (!isEnter){
                    pageJump(LoginActivity.class);
                }
            }
        };
        mCountDownTimer.start();
    }

    @Event(value = {R.id.img_guild, R.id.tv_djs})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_guild:
                pageJump(LoginActivity.class);
                break;
            case R.id.tv_djs:
                isEnter = true;
                pageJump(LoginActivity.class);
                break;
        }
    }
}
