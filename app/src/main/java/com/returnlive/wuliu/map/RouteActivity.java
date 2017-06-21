package com.returnlive.wuliu.map;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapCarInfo;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapRestrictionInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.map.adapter.MapMessageAdapter;
import com.returnlive.wuliu.map.utils.AMapUtil;
import com.returnlive.wuliu.map.utils.Utils;
import com.suke.widget.SwitchButton;
import com.zhy.autolayout.AutoLinearLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RouteActivity extends AppCompatActivity implements AMapNaviListener {

    @ViewInject(R.id.tv_start_place)
    AutoCompleteTextView tvStartPlace;
    @ViewInject(R.id.tv_end_place)
    AutoCompleteTextView tvEndPlace;
    @ViewInject(R.id.switch_button)
    SwitchButton switchButton;
    @ViewInject(R.id.map)
    MapView mMapView;
    private Dialog bottomDialog,mapDialog;
    private AMap aMap;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private MarkerOptions markerOptions;
    private String cityName;

    private LatLonPoint startPoint = null;//起点坐标
    private LatLonPoint endPoint = null;//终点坐标
    private LatLonPoint myplacePoint = null;//我的位置
    private AMapNavi mAMapNavi;
    /**
     * 选择终点Aciton标志位
     */
    private boolean mapClickEndReady;
    private NaviLatLng startLatlng;
    private NaviLatLng endLatlng;
    private List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
    /**
     * 途径点坐标集合
     */
    private List<NaviLatLng> wayList = new ArrayList<NaviLatLng>();
    /**
     * 终点坐标集合［建议就一个终点］
     */
    private List<NaviLatLng> endList = new ArrayList<NaviLatLng>();
    /**
     * 保存当前算好的路线
     */
    private SparseArray<RouteOverLay> routeOverlays = new SparseArray<RouteOverLay>();

    /**
     * 当前用户选中的路线，在下个页面进行导航
     */
    private int routeIndex;
    /**
     * 路线的权值，重合路线情况下，权值高的路线会覆盖权值低的路线
     **/
    private int zindex = 1;
    /**
     * 路线计算成功标志位
     */
    private static final String TAG = "RouteActivity";

    private List<MapMessageEntity> messageList = new ArrayList<>();
    private MapMessageAdapter adapter;
    private TextView tv_light;
    private ArrayAdapter<String> mAdapter;
    private boolean isMapSave = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);
        x.view().inject(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        mapDialog = new Dialog(this, R.style.BottomDialog);

        mMapView.onCreate(savedInstanceState);
        tvStartPlace.addTextChangedListener(startListener);
        tvEndPlace.addTextChangedListener(endListener);

        init();

    }

    @Event(value ={R.id.tv_start_place, R.id.lay_my_place, R.id.tv_end_place,R.id.lay_start_place,R.id.lay_end_place})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.lay_start_place:

                bottomDialog.dismiss();
                mapDialog.dismiss();

                break;
            case R.id.lay_end_place:
                bottomDialog.dismiss();
                mapDialog.dismiss();
                break;
            case R.id.tv_start_place:
                bottomDialog.dismiss();
                mapDialog.dismiss();
                break;

            case R.id.lay_my_place:
                startPoint = myplacePoint;
                tvStartPlace.setText("我的位置");

                break;
            case R.id.tv_end_place:
                bottomDialog.dismiss();
                mapDialog.dismiss();
                break;
        }
    }


    private void init() {
        aMap = mMapView.getMap();
        markerOptions = new MarkerOptions();
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        locationClient.startLocation();
        //获取AMapNavi实例
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
//添加监听回调，用于处理算路成功
        mAMapNavi.addAMapNaviListener(this);
    }

    /**
     * 默认的定位参数
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    private void initDialog() {
        Window dialogWindow = bottomDialog.getWindow();
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        View view = View.inflate(RouteActivity.this, R.layout.bottom_dialog, null);
        tv_light = (TextView) view.findViewById(R.id.tv_light);
        AutoLinearLayout lay_start_navigation = (AutoLinearLayout) view.findViewById(R.id.lay_start_navigation);
        Button btn_open_gaodeMap = (Button) view.findViewById(R.id.btn_open_gaodeMap);
        RecyclerView rv_message = (RecyclerView) view.findViewById(R.id.rv_message);
        bottomDialog.setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_message.setLayoutManager(linearLayoutManager);
        adapter = new MapMessageAdapter(messageList);
        rv_message.setAdapter(adapter);
        if (routeIndex >= routeOverlays.size())
            routeIndex = 0;
        final int routeID = routeOverlays.keyAt(routeIndex);
        changeRoute(routeID);
        adapter.setOnItemClickListener(new MapMessageAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(View view, int position) {
                adapter.changeSelected(position);
                changeRoute(position+routeID);
            }
        });
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        lay_start_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intnet = new Intent(RouteActivity.this,DaoHangActivity.class);
                intnet.putExtra("gps", true);
                startActivity(intnet);
            }
        });

        btn_open_gaodeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.map_listview_item);
                if (isAppInstalled(getApplicationContext(),"com.tencent.map")){
                    mAdapter.add("腾讯地图");
                    isMapSave = true;
                }

                if (isAppInstalled(getApplicationContext(),"com.baidu.BaiduMap")){
                    mAdapter.add("百度地图");
                    isMapSave = true;

                }

                if (isAppInstalled(getApplicationContext(),"com.here.app.maps")){
                    mAdapter.add("HERE地图");
                    isMapSave = true;

                }

                if (isAppInstalled(getApplicationContext(),"com.sogou.map.android.maps")){
                    mAdapter.add("搜狗地图");
                    isMapSave = true;

                }

                if (isAppInstalled(getApplicationContext(),"com.autonavi.minimap")){
                    mAdapter.add("高德地图");
                    isMapSave = true;

                }

                if (isMapSave){
                    mAdapter.add("取消");

                }

                Log.e(TAG, "mAdapter: "+mAdapter.getCount() );
                if (mAdapter.getCount()==0){
                    Toast.makeText(RouteActivity.this, "您未安装其他地图应用", Toast.LENGTH_SHORT).show();
                    return;
                }



                    showMapDialog();


            }
        });
    }

    private void showMapDialog() {
        bottomDialog.dismiss();
        Window dialogWindow = mapDialog.getWindow();
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        View view = View.inflate(RouteActivity.this, R.layout.map_list_dialog, null);
        ListView mapListView = (ListView) view.findViewById(R.id.lv_map);
        mapDialog.setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);

        mapListView.setAdapter(mAdapter);
        mapDialog.getWindow().setGravity(Gravity.BOTTOM);
        mapDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        mAdapter.notifyDataSetChanged();
        mapDialog.show();
        mapListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mAdapter.getItem(position).equals("腾讯地图")){
                    doStartApplicationWithPackageName("com.tencent.map");
                }else if (mAdapter.getItem(position).equals("百度地图")){
                    doStartApplicationWithPackageName("com.baidu.BaiduMap");

                }else if (mAdapter.getItem(position).equals("HERE地图")){
                    doStartApplicationWithPackageName("com.here.app.maps");

                }else if (mAdapter.getItem(position).equals("搜狗地图")){
                    doStartApplicationWithPackageName("com.sogou.map.android.maps");

                }else if (mAdapter.getItem(position).equals("高德地图")){
                    doStartApplicationWithPackageName("com.autonavi.minimap");

                }else if (mAdapter.getItem(position).equals("取消")){
                    mapDialog.dismiss();
                    bottomDialog.show();
                }




            }
        });
    }


    //腾讯地图com.tencent.map
    //百度地图com.baidu.BaiduMap
    //HERE地图com.here.app.maps
    //搜狗地图com.sogou.map.android.maps
    //高德地图com.autonavi.minimap
    //谷歌地图和高德地图包名一致，但在国内无法使用
    //根据包名判断地图app是否安装
    public boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        startList.clear();
        wayList.clear();
        endList.clear();
        routeOverlays.clear();
        messageList.clear();

        /**
         * 当前页面只是展示地图，activity销毁后不需要再回调导航的状态
         */
        mAMapNavi.removeAMapNaviListener(this);
        mAMapNavi.destroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    //出发地POI检索
    private TextWatcher startListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String newText = s.toString().trim();
            if (!AMapUtil.IsEmptyOrNullString(newText)) {
                final InputtipsQuery inputquery = new InputtipsQuery(newText, cityName);
                Inputtips inputTips = new Inputtips(RouteActivity.this, inputquery);

                inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
                    @Override
                    public void onGetInputtips(List<Tip> tipList, int rCode) {
                        Tip tip = null;
                        if (rCode == AMapException.CODE_AMAP_SUCCESS) {

                            // 正确返回
                            List<String> listString = new ArrayList<String>();
                            for (int i = 0; i < tipList.size(); i++) {
                                listString.add(tipList.get(i).getName());
                                tip = tipList.get(i);

                            }
                            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                                    getApplicationContext(),
                                    R.layout.route_inputs, listString);
                            tvStartPlace.setAdapter(aAdapter);
                            if (!tvStartPlace.getText().toString().equals("我的位置")) {
                                startPoint = tip.getPoint();
                            }
                            tvStartPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                }
                            });
                            aAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(RouteActivity.this, rCode, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                inputTips.requestInputtipsAsyn();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    //目的地POI检索
    private TextWatcher endListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String newText = s.toString().trim();
            if (!AMapUtil.IsEmptyOrNullString(newText)) {
                InputtipsQuery inputquery = new InputtipsQuery(newText, cityName);
                Inputtips inputTips = new Inputtips(RouteActivity.this, inputquery);
                inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
                    @Override
                    public void onGetInputtips(List<Tip> tipList, int rCode) {
                        Tip tip = null;
                        if (rCode == AMapException.CODE_AMAP_SUCCESS) {// 正确返回
                            List<String> listString = new ArrayList<String>();
                            for (int i = 0; i < tipList.size(); i++) {
                                listString.add(tipList.get(i).getName());
                                tip = tipList.get(i);

                            }
                            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                                    getApplicationContext(),
                                    R.layout.route_inputs, listString);
                            tvEndPlace.setAdapter(aAdapter);
                            tvEndPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                    messageList.clear();
                                    addRoute();


                                }
                            });
                            endPoint = tip.getPoint();
                            aAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(RouteActivity.this, rCode, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                inputTips.requestInputtipsAsyn();

            }




        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void addRoute() {
        clearRoute();
        startLatlng = new NaviLatLng(startPoint.getLatitude(), startPoint.getLongitude());
        endLatlng = new NaviLatLng(endPoint.getLatitude(), endPoint.getLongitude());
        startList.clear();
        startList.add(startLatlng);
        endList.clear();
        endList.add(endLatlng);
        int strategy=0;
        try {
                                                 //躲避拥堵、不走高速、避免收费、高速优先、多路径
            strategy = mAMapNavi.strategyConvert(false, false, false, false, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (strategy >= 0) {
            String carNumber = "津DFZ582";
            AMapCarInfo carInfo = new AMapCarInfo();
            //设置车牌
            carInfo.setCarNumber(carNumber);
            //设置车牌是否参与限行算路
            carInfo.setRestriction(true);
            mAMapNavi.setCarInfo(carInfo);
            mAMapNavi.calculateDriveRoute(startList, endList, wayList, strategy);

        }
    }

    /**
     * 清除当前地图上算好的路线
     */
    private void clearRoute() {
        for (int i = 0; i < routeOverlays.size(); i++) {
            RouteOverLay routeOverlay = routeOverlays.valueAt(i);
            routeOverlay.removeFromMap();
        }
        routeOverlays.clear();
    }

    private void drawRoutes(int routeId, AMapNaviPath path) {
        aMap.moveCamera(CameraUpdateFactory.changeTilt(0));
        RouteOverLay routeOverLay = new RouteOverLay(aMap, path, this);
        routeOverLay.setTrafficLine(false);
        routeOverLay.addToMap();
        routeOverlays.put(routeId, routeOverLay);
        routeOverLay.zoomToSpan();
    }

    public void changeRoute(int routeID) {
        /**
         * 计算出来的路径只有一条
         */
        if (routeOverlays.size() == 1) {
            //必须告诉AMapNavi 你最后选择的哪条路
            mAMapNavi.selectRouteId(routeOverlays.keyAt(0));
//            Toast.makeText(this, "导航距离:" + (mAMapNavi.getNaviPath()).getAllLength() + "m" + "\n" + "导航时间:" + (mAMapNavi.getNaviPath()).getAllTime() + "s", Toast.LENGTH_SHORT).show();
            return;
        }

        /*if (routeIndex >= routeOverlays.size())
            routeIndex = 0;
        int routeID = routeOverlays.keyAt(routeIndex);*/
        //突出选择的那条路
        for (int i = 0; i < routeOverlays.size(); i++) {
            int key = routeOverlays.keyAt(i);
            routeOverlays.get(key).setTransparency(0.4f);
        }
        routeOverlays.get(routeID).setTransparency(1);
        /**把用户选择的那条路的权值弄高，使路线高亮显示的同时，重合路段不会变的透明**/
        routeOverlays.get(routeID).setZindex(zindex++);

        //必须告诉AMapNavi 你最后选择的哪条路
        mAMapNavi.selectRouteId(routeID);
//        Toast.makeText(this, "路线标签:" + mAMapNavi.getNaviPath().getLabels(), Toast.LENGTH_SHORT).show();
        int a = 0;
        for (int i = 0; i < mAMapNavi.getNaviPath().getSteps().size(); i++) {
           a = a+ mAMapNavi.getNaviPath().getSteps().get(i).getTrafficLightNumber();
        }
        tv_light.setText("红绿灯"+a+"个");

        routeIndex++;

        /**选完路径后判断路线是否是限行路线**/
        AMapRestrictionInfo info = mAMapNavi.getNaviPath().getRestrictionInfo();
        if (!TextUtils.isEmpty(info.getRestrictionTitle())) {
            Toast.makeText(this, info.getRestrictionTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");
                    LatLng localLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    LatLonPoint startLonPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());
                    startPoint = startLonPoint;
                    myplacePoint = startLonPoint;
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localLatLng, 13));
                    //添加marker
                    markerOptions.position(localLatLng);
                    aMap.addMarker(markerOptions);
                    cityName = location.getCity();
                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    tvStartPlace.setText("我的位置");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    //定位完成的时间
                    sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                //定位之后的回调时间
                sb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

                //解析定位结果，
                String result = sb.toString();
                Log.e(TAG, "result: " + result);
                locationClient.stopLocation();//定位成功后停止定位
            } else {
//                Log.e(TAG, "定位失败: ");
            }
        }
    };

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteSuccess() {
/**
 * 清空上次计算的路径列表。
 */
        routeOverlays.clear();
        AMapNaviPath path = mAMapNavi.getNaviPath();
        /**
         * 单路径不需要进行路径选择，直接传入－1即可
         */
        drawRoutes(-1, path);
    }

    @Override
    public void onCalculateRouteFailure(int i) {
//        calculateSuccess = false;
        Toast.makeText(getApplicationContext(), "计算路线失败，errorcode＝" + i, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {
//清空上次计算的路径列表。
        routeOverlays.clear();
        HashMap<Integer, AMapNaviPath> paths = mAMapNavi.getNaviPaths();
        for (int i = 0; i < ints.length; i++) {
            AMapNaviPath path = paths.get(ints[i]);
            MapMessageEntity  mapMessageEntity = new MapMessageEntity(path.getLabels()+"",path.getAllTime()+"",path.getAllLength()+"");
            messageList.add(mapMessageEntity);
            if (path != null) {
                drawRoutes(ints[i], path);
            }
        }

        initDialog();
        bottomDialog.show();
    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }


    private void doStartApplicationWithPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            Toast.makeText(this, "您未安装高德地图", Toast.LENGTH_SHORT).show();
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            startActivity(intent);
        }
    }


}
