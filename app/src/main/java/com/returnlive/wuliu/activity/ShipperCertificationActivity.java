package com.returnlive.wuliu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.returnlive.wuliu.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 作者： 张梓彬
 * 日期： 2017/5/22 0022
 * 时间： 上午 11:47
 * 描述： 货主认证
 */
public class ShipperCertificationActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_yournameship)
    TextView tv_yournameship;
    @ViewInject(R.id.tv_yourIDcardship)
    TextView tv_yourIDcardship;
    @ViewInject(R.id.img_portraitship)
    ImageView img_portraitship;
    @ViewInject(R.id.idCardship1)
    ImageView idCardship1;
    @ViewInject(R.id.idCardship2)
    ImageView idCardship2;
    @ViewInject(R.id.tv_company_name)
    TextView tv_company_name;
    @ViewInject(R.id.tv_company_address)
    TextView tv_company_address;
    @ViewInject(R.id.tv_yourposition)
    TextView tv_yourposition;
    @ViewInject(R.id.img_business_card)
    ImageView img_business_card;
    @ViewInject(R.id.img_door_head)
    ImageView img_door_head;
    @ViewInject(R.id.img_business_license)
    ImageView img_business_license;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_certification);
        x.view().inject(this);

    }


    @Event(value = {R.id.img_back, R.id.lay_yournameship, R.id.lay_yourIDcardship, R.id.lay_portraitship, R.id.lay_idship_certification, R.id.tv_company_name, R.id.tv_company_address, R.id.lay_yourposition, R.id.lay_business_card, R.id.lay_door_head, R.id.lay_business_license, R.id.tv_contactship_customer, R.id.btn_shipsubmit})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.lay_yournameship:
                pageJump(EditextActivity.class);
                break;
            case R.id.lay_yourIDcardship:
                break;
            case R.id.lay_portraitship:
                break;
            case R.id.lay_idship_certification:
                break;
            case R.id.tv_company_name:
                break;
            case R.id.tv_company_address:
                break;
            case R.id.lay_yourposition:
                break;
            case R.id.lay_business_card:
                break;
            case R.id.lay_door_head:
                break;
            case R.id.lay_business_license:
                break;
            case R.id.tv_contactship_customer:
                break;
            case R.id.btn_shipsubmit:
                break;
        }
    }


    public void pageJump(Class<?> cls) {
        Intent intent = new Intent(getApplicationContext(), cls);
        startActivity(intent);
    }
}
