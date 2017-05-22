package com.returnlive.wuliu.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.returnlive.wuliu.R;
import java.util.ArrayList;


/**
 * 作者： 张梓彬
 * 日期： 2017/5/22 0022
 * 时间： 上午 11:44
 * 描述： 司机认证页面
 */
public class DriverCertificationActivity extends AppCompatActivity {
    private ArrayList<String> mListProvince = new ArrayList<String>();
    private String[] city = new String[]{"京", "津", "沪", "渝", "冀", "豫", "云", "辽", "黑", "湘", "皖", "鲁", "新", "苏", "浙", "赣", "鄂", "桂", "甘", "晋", "蒙", "陕", "吉", "闽", "贵", "粤", "青", "藏", "川", "宁", "琼"};
    private static final String TAG = "TAG";
    private String[] models = {"平板", "高栏", "厢式", "保温", "冷藏", "集装箱", "面包车", "危险品", "其他"};
    private String[] carLength = {"4.2米", "5.0米", "6.2米", "6.8米", "7.2米", "7.7米", "7.8米", "8.2米", "8.7米", "9.6米", "12.5米",
            "13.0米", "15.0米", "16.0米", "17.5米", "自定义"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);


        initView();
    }

    /**
     * 初始化配置
     */
    private void initView() {




    }



}
