package com.returnlive.wuliu.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.adapter.CityPopWindowAdapter;
import com.returnlive.wuliu.constant.City;
import com.returnlive.wuliu.entity.CityItemEntity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * 作者： 张梓彬
 * 日期： 2017/5/25 0025
 * 时间： 下午 5:01
 * 描述： 重写PopupWindow弹出城市三级联动列表
 */

public class CityListViewPopWindow extends PopupWindow {
    private View view;
    private static final String TAG = "CityListViewPopWindow";
    private ViewHolder viewHolder;
    private CityPopWindowAdapter provinceAdapter;
    private CityPopWindowAdapter cityAdapter;
    private CityPopWindowAdapter countyAdapter;
    private int provincePosition = -1;
    private int cityPosition = -1;
    private int countryPosition = -1;
    private final OnCheckChangeListener mOnCheckChangeListener;


    public CityListViewPopWindow(Context context, OnCheckChangeListener mOnCheckChangeListener) {
        super(context);
        view = View.inflate(context, R.layout.pop_goods_pcd_listview, null);
        this.mOnCheckChangeListener = mOnCheckChangeListener;
        viewHolder = new ViewHolder(view);
        initProvince(context);
        setContentView(view);
        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable());
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }


    private CityItemEntity provinceItem;
    private CityItemEntity cityItem;
    private CityItemEntity countryItem;

    private void initProvince(Context context) {
        provinceAdapter = new CityPopWindowAdapter(context);
        cityAdapter = new CityPopWindowAdapter(context);
        countyAdapter = new CityPopWindowAdapter(context);
        for (int i = 0; i < City.province.length; i++) {
            String provinceName = City.province[i];
            CityItemEntity item = new CityItemEntity(provinceName);
            provinceAdapter.addDATA(item);
        }
        viewHolder.lvProvince.setAdapter(provinceAdapter);
        viewHolder.lvCity.setAdapter(cityAdapter);
        viewHolder.lvDistrict.setAdapter(countyAdapter);
        provinceAdapter.notifyDataSetChanged();
        cityAdapter.notifyDataSetChanged();
        countyAdapter.notifyDataSetChanged();

        //省
        viewHolder.lvProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnCheckChangeListener != null) {

                }
                //清空城市集合
                cityAdapter.removeAllDATA();
                countyAdapter.removeAllDATA();

                if (provincePosition != -1) {
                    provinceItem = (CityItemEntity) parent.getItemAtPosition(provincePosition);
                    provinceItem.isChoose = false;
                }
                provincePosition = position;
                provinceItem = (CityItemEntity) parent.getItemAtPosition(position);
                provinceItem.isChoose = true;
                if (mOnCheckChangeListener != null) {
                    mOnCheckChangeListener.setProvinceText(provinceItem.cityName);
                }

                provinceAdapter.notifyDataSetChanged();
                cityAdapter.notifyDataSetChanged();
                countyAdapter.notifyDataSetChanged();
                cityPosition = -1;//防止角标越界
                addCity(provincePosition);


            }
        });


        viewHolder.lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnCheckChangeListener != null) {

                }
                countyAdapter.removeAllDATA();
                if (cityPosition != -1) {
                    cityItem = (CityItemEntity) parent.getItemAtPosition(cityPosition);
                    cityItem.isChoose = false;
                }
                cityPosition = position;
                cityItem = (CityItemEntity) parent.getItemAtPosition(position);
                cityItem.isChoose = true;
                if (mOnCheckChangeListener != null) {
                    mOnCheckChangeListener.setCityText(cityItem.cityName);

                }

                cityAdapter.notifyDataSetChanged();
                countryPosition = -1;
                if (position==0){
                    CityListViewPopWindow.this.dismiss();
                    mOnCheckChangeListener.setCityText(provinceItem.cityName);
                    provinceItem.isChoose = false;
                    cityAdapter.removeAllDATA();
                    countyAdapter.removeAllDATA();
                }

                addCountry(provincePosition, cityPosition);

            }
        });


        viewHolder.lvDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnCheckChangeListener != null) {

                }
                if (countryPosition != -1) {
                    countryItem = (CityItemEntity) parent.getItemAtPosition(countryPosition);
                    countryItem.isChoose = false;
                }
                countryPosition = position;
                countryItem = (CityItemEntity) parent.getItemAtPosition(position);
                countryItem.isChoose = true;
                countyAdapter.notifyDataSetChanged();

                if (mOnCheckChangeListener != null) {
                    //设置地区
                    mOnCheckChangeListener.setDistrictText(cityItem.cityName + "-" + countryItem.cityName);
                    CityListViewPopWindow.this.dismiss();
                    provinceItem.isChoose = false;
                    cityAdapter.removeAllDATA();
                    countyAdapter.removeAllDATA();
                }

                if (position==0){
                    CityListViewPopWindow.this.dismiss();
                    mOnCheckChangeListener.setDistrictText(cityItem.cityName);
                    provinceItem.isChoose = false;
                    cityAdapter.removeAllDATA();
                    countyAdapter.removeAllDATA();
                }

                countyAdapter.notifyDataSetChanged();



            }
        });
    }

    private void addCity(final int provincePosition) {
        String[] cityName = {};
        for (int i = 0; i < City.city[provincePosition].length; i++) {
            cityName = City.city[provincePosition];
        }
        for (int i = 0; i < cityName.length; i++) {
            String city = cityName[i];
            CityItemEntity item = new CityItemEntity(city);
            cityAdapter.addDATA(item);
        }
        cityAdapter.notifyDataSetChanged();


    }

    private void addCountry(int provincePosition, int cityPosition) {
        String[] countryName = {};
        for (int i = 0; i < City.county[provincePosition][cityPosition].length; i++) {
            countryName = City.county[provincePosition][cityPosition];
        }


        for (int i = 0; i < countryName.length; i++) {
            String country = countryName[i];
            CityItemEntity item = new CityItemEntity(country);
            countyAdapter.addDATA(item);
        }
        countyAdapter.notifyDataSetChanged();

    }

    /**
     * 用来点击选择显示文本的回调
     */
    public interface OnCheckChangeListener {

        void setProvinceText(String text);

        void setCityText(String text);


        void setDistrictText(String text);


    }


    static class ViewHolder {
        @ViewInject(R.id.lv_province)
        ListView lvProvince;
        @ViewInject(R.id.lv_city)
        ListView lvCity;
        @ViewInject(R.id.lv_district)
        ListView lvDistrict;

        ViewHolder(View view) {
            x.view().inject(this, view);
        }
    }
}
