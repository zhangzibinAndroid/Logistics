package com.returnlive.wuliu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.constant.ConstantNumber;
import com.returnlive.wuliu.view.CityListViewPopWindow;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 作者： 张梓彬
 * 日期： 2017/6/1 0001
 * 时间： 下午 5:04
 * 描述： 添加路线activity
 */

public class RouteShowActivity extends AppCompatActivity {
    @ViewInject(R.id.edt_route_start)
    Button btn_start_area;
    @ViewInject(R.id.edt_route_end)
    Button btn_end_area;
    private CityListViewPopWindow cityListViewPopWindow;
    public int WHICH = ConstantNumber.NUMBER_ZERO;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        cityListViewPopWindow = new CityListViewPopWindow(this, mOnCheckChangeListener);

        x.view().inject(this);
    }

    @Event(value = {R.id.img_back, R.id.edt_route_start, R.id.edt_route_end, R.id.btn_route_no, R.id.btn_route_yes})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.edt_route_start:
               WHICH = ConstantNumber.NUMBER_ONE;
                showCityPopWindow();
                break;
            case R.id.edt_route_end:
                WHICH = ConstantNumber.NUMBER_TWO;
                showCityPopWindow();
                break;
            case R.id.btn_route_no:
                btn_start_area.setText("");
                btn_end_area.setText("");
                break;
            case R.id.btn_route_yes:
                if (btn_start_area.getText().toString().equals("")){
                    Toast.makeText(this, "出发地不能为空", Toast.LENGTH_SHORT).show();
                }else if (btn_end_area.getText().toString().equals("")){
                    Toast.makeText(this, "目的地不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    finish();
                }

                break;
        }
    }

    private CityListViewPopWindow.OnCheckChangeListener mOnCheckChangeListener = new CityListViewPopWindow.OnCheckChangeListener() {
        @Override
        public void setProvinceText(String text) {

        }

        @Override
        public void setCityText(String text) {
            if (WHICH == ConstantNumber.NUMBER_ONE) {
                btn_start_area.setText(text);
            } else if (WHICH == ConstantNumber.NUMBER_TWO) {
                btn_end_area.setText(text);
            }

        }

        @Override
        public void setDistrictText(String text) {
            if (WHICH == ConstantNumber.NUMBER_ONE) {
                btn_start_area.setText(text);
            } else if (WHICH == ConstantNumber.NUMBER_TWO) {
                btn_end_area.setText(text);

            }

        }
    };

    private void showCityPopWindow() {
        cityListViewPopWindow.showAsDropDown(btn_start_area);
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
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = bgAlpha;
        getWindow().setAttributes(params);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
