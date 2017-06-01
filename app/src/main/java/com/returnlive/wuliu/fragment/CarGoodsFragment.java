package com.returnlive.wuliu.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.adapter.CarSourceAdapter;
import com.returnlive.wuliu.constant.ConstantNumber;
import com.returnlive.wuliu.constant.NetworkUrl;
import com.returnlive.wuliu.constant.ReturnCode;
import com.returnlive.wuliu.entity.CarsourceListEntity;
import com.returnlive.wuliu.entity.ErrorCodeEntity;
import com.returnlive.wuliu.entity.CarMessageEventBus;
import com.returnlive.wuliu.gson.GsonParsing;
import com.returnlive.wuliu.utils.SourceList;
import com.returnlive.wuliu.utils.ErrorCode;
import com.returnlive.wuliu.utils.MyCallBack;
import com.returnlive.wuliu.utils.XUtil;
import com.returnlive.wuliu.view.CityListViewPopWindow;
import com.zhy.autolayout.AutoRelativeLayout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @ViewInject(R.id.layout_goods_car_more)
    AutoRelativeLayout layout_goods_car_more;
    private View view;
    private CarSourceAdapter carSourceAdapter;
    private CityListViewPopWindow cityListViewPopWindow;
    public int WHICH = ConstantNumber.NUMBER_ZERO;
    private TimePickerView timePickerView;//时间选择器
    private PopupWindow popMoreWindow;//用来显示popupwindow的parent
    private int PAGE = ConstantNumber.NUMBER_ONE;
    private List<CarsourceListEntity.CarsourceBean> list;
    private ListView actualListView;
    public CarGoodsFragment() {
    }


    private Handler carsourceHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            if (result.indexOf(ReturnCode.SUCCESS) > 0) {
                list = GsonParsing.gsonCarsource(result);
                if (list==null){
                    Toast.makeText(getActivity(), "没有更多数据了哦！", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < list.size(); i++) {
                    CarsourceListEntity.CarsourceBean carsourceBean = list.get(i);
                    carSourceAdapter.addDATA(carsourceBean);
                }
                SourceList.cacheCarList = carSourceAdapter.getList();
                carSourceAdapter.notifyDataSetChanged();

            } else {
                errorCode(result);
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_car_goods, container, false);
        x.view().inject(this, view);
        EventBus.getDefault().register(this);
        initCustomTimePicker();
        showPopMoreWindow();
        initView();
        if (SourceList.cacheCarList == null) {
            addData();
        } else {
            addDataFromCache();
        }

        return view;
    }

    private void addDataFromCache() {
        for (int i = 0; i < SourceList.cacheCarList.size(); i++) {
            CarsourceListEntity.CarsourceBean carsourceBean = SourceList.cacheCarList.get(i);
            carSourceAdapter.addDATA(carsourceBean);
        }
        carSourceAdapter.notifyDataSetChanged();

    }


    private void addData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                PAGE = ConstantNumber.NUMBER_ONE;
                carsourceInterface(ConstantNumber.NUMBER_ONE);
            }
        }).start();

    }


    //获取车辆列表接口
    private void carsourceInterface(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("page", page+"");
        NetworkUrl networkUrl = new NetworkUrl();
        XUtil.Get(networkUrl.CAR_SOURCE_URL, map, new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Message msg = new Message();
                msg.obj = result;
                carsourceHandler.sendMessage(msg);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), getResources().getString(R.string.networ_anomalies), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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


        //得到ListView
        actualListView = pull_refresh_list.getRefreshableView();
        actualListView.setSelected(true);


        pull_refresh_list.setMode(PullToRefreshBase.Mode.BOTH);//设置可以同时上拉刷新和下拉加载

        carSourceAdapter = new CarSourceAdapter(getActivity());
        actualListView.setAdapter(carSourceAdapter);

        actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "点击了第" + (position) + "个", Toast.LENGTH_SHORT).show();
//                pageJump(GoodsDetailsActivity.class);
            }
        });


    }





    public void pageJump(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    //错误码返回值解析并判断
    private void errorCode(String result) {
        ErrorCodeEntity errorCodeEntity = GsonParsing.sendCodeError(result);
        String errorCode = errorCodeEntity.getCode();
        ErrorCode code = new ErrorCode(getActivity());
        code.judge(errorCode);
    }


    private CityListViewPopWindow.OnCheckChangeListener mOnCheckChangeListener = new CityListViewPopWindow.OnCheckChangeListener() {
        @Override
        public void setProvinceText(String text) {

        }

        @Override
        public void setCityText(String text) {
            if (WHICH == ConstantNumber.NUMBER_ONE) {
                tv_goods_car_start_email.setText(text);
            } else if (WHICH == ConstantNumber.NUMBER_TWO) {
                tv_goods_car_end_email.setText(text);
            }

        }

        @Override
        public void setDistrictText(String text) {
            if (WHICH == ConstantNumber.NUMBER_ONE) {
                tv_goods_car_start_email.setText(text);
            } else if (WHICH == ConstantNumber.NUMBER_TWO) {
                tv_goods_car_end_email.setText(text);

            }

        }
    };


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
            if (pull_refresh_list.isHeaderShown()) {
                SourceList.cacheCarList.clear();
                carSourceAdapter.removeAllDATA();
                addData();
            } else if (pull_refresh_list.isFooterShown()) {
                if (list==null) {
                    SourceList.cacheCarList = carSourceAdapter.getList();
                    Toast.makeText(getActivity(), "没有更多数据了哦！", Toast.LENGTH_SHORT).show();
                } else {
                    PAGE = PAGE + ConstantNumber.NUMBER_ONE;
                    carsourceInterface(PAGE);
                }

            }

            carSourceAdapter.notifyDataSetChanged();
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
                //点击弹出pop对话框(用来显示popupwindow的parent  ID，以该位置为参照物设置具体位置)
                popMoreWindow.showAsDropDown(layout_goods_car_more, 0, 5);
                setPopWindowbackgroundAlpha(0.5f);//设置背景变暗
                break;
        }
    }


    //滑动到最顶端刷新
    private void scrollToRefresh() {
        actualListView.smoothScrollToPosition(0);//listView返回顶部
        actualListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            boolean isNeedToRefresh = true;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (actualListView.getLastVisiblePosition() == (actualListView.getCount() - 1)) {
                        }
                        // 判断滚动到顶部
                        if (actualListView.getFirstVisiblePosition() == 0) {
                            if (isNeedToRefresh) {
                                pull_refresh_list.onRefreshComplete();
                                pull_refresh_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                pull_refresh_list.setRefreshing(true);
                                SourceList.cacheCarList.clear();
                                carSourceAdapter.removeAllDATA();
                                addData();
                                pull_refresh_list.setMode(PullToRefreshBase.Mode.BOTH);
                                isNeedToRefresh = false;
                            }
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(CarMessageEventBus event){
        if (event.msg.equals("refresh")){
            scrollToRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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


    /**
     * 显示"更多"对话框
     */
    private void showPopMoreWindow() {
        View popview = LayoutInflater.from(getActivity()).inflate(R.layout.goods_scrollview_more, null);
        //WRAP_CONTENT:是.xml中的布局宽、高，wrap_content包裹
        popMoreWindow = new PopupWindow(popview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popMoreWindow.setBackgroundDrawable(new BitmapDrawable());
        popMoreWindow.getContentView().setFocusable(true);//为是否可以获得焦点
        //设置可触摸PopupWindow之外的地方关闭
        popMoreWindow.setOutsideTouchable(true);
        setPopWindowbackgroundAlpha(1f);
        //点击其它地方对话框关闭的时候，将背景还原
        popMoreWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                setPopWindowbackgroundAlpha(1f);
            }
        });

        popMoreWindow.setAnimationStyle(R.style.dialogWindowAnim_style);//设置动画
        // TODO: 2017/3/28 控件点击待完成
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
