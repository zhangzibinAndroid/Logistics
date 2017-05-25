package com.returnlive.wuliu.fragment;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.adapter.GoodsAdapter;
import com.returnlive.wuliu.constant.ConstantNumber;
import com.returnlive.wuliu.entity.GoodsAdapterEntity;
import com.returnlive.wuliu.view.CityListViewPopWindow;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    @ViewInject(R.id.ly_goods_car_start)
    AutoRelativeLayout ly_goods_car_start;
    private View view;
    private GoodsAdapter goodsAdapter;
    private CityListViewPopWindow cityListViewPopWindow;
    private static final String TAG = "CarGoodsFragment";
    public int WHICH = ConstantNumber.NUMBER_ZERO;
    private TimePickerView timePickerView;//时间选择器


    public CarGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_car_goods, container, false);
        x.view().inject(this, view);
        initCustomTimePicker();
        initView();
        return view;
    }

    private void initView() {
        toobar_goods_car_title_address.setSelected(true);
        toobar_goods_car_title_nearby.setSelected(false);
        cityListViewPopWindow = new CityListViewPopWindow(getActivity(), mOnCheckChangeListener);
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
            GoodsAdapterEntity goodsAdapterEntity = new GoodsAdapterEntity("司机版_" + (i + 1));
            goodsAdapter.addDATA(goodsAdapterEntity);
        }
        goodsAdapter.notifyDataSetChanged();


    }


    private CityListViewPopWindow.OnCheckChangeListener mOnCheckChangeListener = new CityListViewPopWindow.OnCheckChangeListener() {
        @Override
        public void setProvinceText(String text) {

        }

        @Override
        public void setCityText(String text) {
            if (WHICH==ConstantNumber.NUMBER_ONE){
                tv_goods_car_start_email.setText(text);
            }else if (WHICH==ConstantNumber.NUMBER_TWO){
                tv_goods_car_end_email.setText(text);
            }

        }

        @Override
        public void setDistrictText(String text) {
            if (WHICH==ConstantNumber.NUMBER_ONE){
                tv_goods_car_start_email.setText(text);
            }else if (WHICH==ConstantNumber.NUMBER_TWO){
                tv_goods_car_end_email.setText(text);

            }

        }
    };


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
            if (pull_refresh_list.isHeaderShown()) {
                ArrayList<GoodsAdapterEntity> newList = new ArrayList<>();
                GoodsAdapterEntity goodsAdapterEntity = new GoodsAdapterEntity("下拉得到数据");
                newList.add(goodsAdapterEntity);

                for (int i = 0; i < goodsAdapter.getList().size(); i++) {
                    newList.add(goodsAdapter.getList().get(i));
                }
                goodsAdapter.removeAllDATA();
                goodsAdapter.addAllDataToMyadapter(newList);
                newList.clear();


            } else if (pull_refresh_list.isFooterShown()) {
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
                toobar_goods_car_title_address.setSelected(true);
                toobar_goods_car_title_nearby.setSelected(false);
                break;
            case R.id.toobar_goods_car_title_nearby:
                toobar_goods_car_title_address.setSelected(false);
                toobar_goods_car_title_nearby.setSelected(true);
                break;
            case R.id.ly_goods_car_start:
                WHICH = ConstantNumber.NUMBER_ONE;
                showCityPopWindow();
                break;
            case R.id.ly_goods_car_end:
                WHICH = ConstantNumber.NUMBER_TWO;
                showCityPopWindow();
                break;
            case R.id.ly_goods_car_cartime:
                if (timePickerView != null) {
                    timePickerView.show();
                }
                break;
            case R.id.layout_goods_car_more:
                break;
        }
    }


    /**
     * 显示“时间”的初始化
     */
    private void initCustomTimePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();//开始时间
        Calendar endDate = Calendar.getInstance();//结束时间
        startDate.set(2010, 1, 1);
        endDate.set(2200, 1, 28);
        timePickerView = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                //可根据需要自行截取数据显示在控件上  yyyy-MM-dd HH:mm:ss  或yyyy-MM-dd
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String time = format.format(date);
                tv_goods_car_car_time.setText(time);
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setTextColorCenter(Color.parseColor("#FF4081"))//设置选中文字的颜色#64AE4A
                .setTextColorOut(Color.parseColor("#717171"))//设置选中项以外的颜色#64AE4A
                .setLineSpacingMultiplier(2.4f)//设置两横线之间的间隔倍数
                .setDividerColor(Color.parseColor("#24E1E4"))//设置分割线的颜色
                .setDividerType(WheelView.DividerType.WRAP)//设置分割线的类型
                .setBgColor(Color.parseColor("#FFFFFF"))//背景颜色(必须是16进制) Night mode#2AA2BC
                .gravity(Gravity.CENTER)//设置控件显示位置 default is center*/
                .isDialog(true)//设置显示位置
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvCancel = (TextView) v.findViewById(R.id.iv_cancel);
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                timePickerView.returnData();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                timePickerView.dismiss();
                            }
                        });
                    }
                })
                .build();
    }


    private void showCityPopWindow() {
        cityListViewPopWindow.showAsDropDown(ly_goods_car_start);
        cityListViewPopWindow.setOutsideTouchable(true);
        setPopWindowbackgroundAlpha(0.5f);//设置展示对话框背景变暗
        cityListViewPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setPopWindowbackgroundAlpha(1f);
            }
        });
    }

    private void setPopWindowbackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(params);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

}
