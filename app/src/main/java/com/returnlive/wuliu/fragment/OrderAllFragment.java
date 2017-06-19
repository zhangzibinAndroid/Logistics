package com.returnlive.wuliu.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.adapter.OrderDetailsAllAdapter;
import com.returnlive.wuliu.entity.OrderDetailsAllEntity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 作者： 张梓彬
 * 日期： 2017/6/19 0019
 * 时间： 上午 10:48
 * 描述： 订单详情页面的全部订单列表
 */
public class OrderAllFragment extends Fragment {
    @ViewInject(R.id.refresh_list_all)
    PullToRefreshListView refresh_list_all;
    private View view;
    private ListView actualListView;
    private OrderDetailsAllAdapter orderDetailsAllAdapter;


    public OrderAllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_all, container, false);
        x.view().inject(this, view);
        initView();
        return view;
    }

    private void initView() {
        refresh_list_all.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
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

        //得到ListView
        actualListView = refresh_list_all.getRefreshableView();
        actualListView.setSelected(true);
        refresh_list_all.setMode(PullToRefreshBase.Mode.BOTH);//设置可以同时上拉刷新和下拉加载
        //actualListView设置适配器
        orderDetailsAllAdapter = new OrderDetailsAllAdapter(getActivity());
        actualListView.setAdapter(orderDetailsAllAdapter);

        for (int i = 0; i < 5; i++) {
            OrderDetailsAllEntity entity = new OrderDetailsAllEntity("已完成","1492248629","江苏省南京市高淳区","江苏省南京市建邺区");
            orderDetailsAllAdapter.addDATA(entity);
        }

        orderDetailsAllAdapter.notifyDataSetChanged();
    }


    private class GetDataTask extends AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (refresh_list_all.isHeaderShown()) {

            } else if (refresh_list_all.isFooterShown()) {


            }
            refresh_list_all.onRefreshComplete();
            super.onPostExecute(o);
        }

    }

}
