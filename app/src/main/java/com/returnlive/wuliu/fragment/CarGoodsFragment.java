package com.returnlive.wuliu.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.adapter.GoodsAdapter;
import com.returnlive.wuliu.entity.GoodsAdapterEntity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * @author 张梓彬
 *         Data : 2017/5/19 0019
 *         Time : 上午 10:43
 *         Describe : 货主车源页面
 */
public class CarGoodsFragment extends Fragment {
    @ViewInject(R.id.toobar_goods_car_title_address)
    TextView toobar_goods_car_title_address;
    @ViewInject(R.id.toobar_goods_car_title_nearby)
    TextView toobar_goods_car_title_nearby;
    @ViewInject(R.id.tv_goods_car_start_email)
    TextView tv_goods_car_start_email;
    @ViewInject(R.id.tv_goods_car_end_email)
    TextView tv_goods_car_end_email;
    @ViewInject(R.id.tv_goods_car_car_time)
    TextView tv_goods_car_car_time;
    @ViewInject(R.id.tv_goods_car_more)
    TextView tv_goods_car_more;
    @ViewInject(R.id.pull_refresh_list)
    PullToRefreshListView pull_refresh_list;
    private View view;
    private GoodsAdapter goodsAdapter;


    public CarGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_car_goods, container, false);
        x.view().inject(this, view);
        initView();
        return view;
    }

    private void initView() {
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //得到当前刷新的时间
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                //设置更新时间
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                new GetDataTask().execute();
            }
        });

        //设置监听最后一条
        pull_refresh_list.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                Toast.makeText(getActivity(), "上拉加载更多哦!", Toast.LENGTH_SHORT).show();
            }
        });

        //得到ListView
        ListView actualListView = pull_refresh_list.getRefreshableView();
        pull_refresh_list.setMode(PullToRefreshBase.Mode.BOTH);//设置可以同时上拉刷新和下拉加载
        //添加刷新事件并且发出声音
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(getActivity());
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        pull_refresh_list.setOnPullEventListener(soundListener);
        goodsAdapter = new GoodsAdapter(getActivity());
        actualListView.setAdapter(goodsAdapter);
        for (int i = 0; i < 1; i++) {
            GoodsAdapterEntity goodsAdapterEntity = new GoodsAdapterEntity("司机版_"+(i+1));
            goodsAdapter.addDATA(goodsAdapterEntity);
        }
        goodsAdapter.notifyDataSetChanged();


    }



    private class GetDataTask extends AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... params) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (pull_refresh_list.isHeaderShown()){
                ArrayList<GoodsAdapterEntity> newList = new ArrayList<>();
                GoodsAdapterEntity goodsAdapterEntity = new GoodsAdapterEntity("下拉得到数据");
                newList.add(goodsAdapterEntity);

                for (int i = 0; i < goodsAdapter.getList().size(); i++) {
                    newList.add(goodsAdapter.getList().get(i));
                }
                goodsAdapter.removeAllDATA();
                goodsAdapter.addAllDataToMyadapter(newList);
                newList.clear();


            }else if (pull_refresh_list.isFooterShown()){
                GoodsAdapterEntity goodsAdapterEntity = new GoodsAdapterEntity("上拉得到数据");
                goodsAdapter.addDATA(goodsAdapterEntity);
            }

            goodsAdapter.notifyDataSetChanged();
            pull_refresh_list.onRefreshComplete();
            super.onPostExecute(o);
        }

    }

    @Event(value = {R.id.toobar_goods_car_title_address, R.id.toobar_goods_car_title_nearby, R.id.ly_goods_car_start, R.id.ly_goods_car_end, R.id.ly_goods_car_cartime, R.id.layout_goods_car_more})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.toobar_goods_car_title_address:
                break;
            case R.id.toobar_goods_car_title_nearby:
                break;
            case R.id.ly_goods_car_start:
                break;
            case R.id.ly_goods_car_end:
                break;
            case R.id.ly_goods_car_cartime:
                break;
            case R.id.layout_goods_car_more:
                break;
        }
    }
}
