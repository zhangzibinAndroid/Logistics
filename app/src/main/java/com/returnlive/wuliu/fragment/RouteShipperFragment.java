package com.returnlive.wuliu.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.activity.RouteShowActivity;
import com.returnlive.wuliu.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 张梓彬
 * 日期： 2017/6/2 0002
 * 时间： 上午 9:54
 * 描述： 货主路线
 */
public class RouteShipperFragment extends Fragment {
    @ViewInject(R.id.owner_banner)
    Banner owner_banner;
    @ViewInject(R.id.tv_route_unmber)
    TextView tv_route_unmber;
    @ViewInject(R.id.lv_route)
    ListView lv_route;
    @ViewInject(R.id.tv_none)
    TextView tv_none;
    private View view;
    String[] imagePath = {"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496314746405&di=712c87fe1f5d3d72f138463d052be801&imgtype=0&src=http%3A%2F%2Ffile11.zk71.com%2FFile%2FCorpEditInsertImages%2F2015%2F09%2F21%2F0_nanfanghuanyuwu_8899_20150921094138.png",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496314840212&di=769c2013cc77b48f1f22337ec8900aad&imgtype=0&src=http%3A%2F%2Fsc.jb51.net%2Fuploads%2Fallimg%2F150413%2F14-150413142443225.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496314884979&di=66e31f10b84a41b268bd30111ef0ea71&imgtype=0&src=http%3A%2F%2Fimages.quanjing.com%2Fchineseview094%2Fhigh%2Fmhrf-dspd33605f30.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496314910307&di=e7bf291281729d5936af0063ccd59f3e&imgtype=0&src=http%3A%2F%2Ffile07.zk71.com%2FFile%2FCorpProductImages%2F2014%2F08%2F11%2F0_shuanglihuoyun_8888_20140811101939.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496314940163&di=538eb357bdb7e66585aac584fd59a15e&imgtype=0&src=http%3A%2F%2Fimg2016.cn5135.com%2Fuploads%2FPic%2F2016%2F5%2F8%2F425095_20165818416284.png"};
    String[] title = {"长春到武汉", "沈阳到南京", "北京到天津", "这是标题", "南京高淳没车了"};

    private List images;
    private List titles;

    public RouteShipperFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_route_shipper, container, false);
        x.view().inject(this, view);
        initView();
        return view;
    }

    private void initView() {
        images = new ArrayList();
        titles = new ArrayList();
        for (int i = 0; i < imagePath.length; i++) {
            images.add(imagePath[i]);
        }

        for (int i = 0; i < title.length; i++) {
            titles.add(title[i]);
        }
        //设置图片加载器
        owner_banner.setImageLoader(new GlideImageLoader());
        //设置banner样式
        owner_banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片集合
        owner_banner.setImages(images);
        //设置轮播时间
        owner_banner.setDelayTime(2000);
        //设置标题集合（当banner样式有显示title时）
        owner_banner.setBannerTitles(titles);
        owner_banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        owner_banner.start();
    }

    @Event(R.id.tv_rout_add)
    private void onClick(View view) {
        pageJump(RouteShowActivity.class);
    }

    public void pageJump(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }
}
