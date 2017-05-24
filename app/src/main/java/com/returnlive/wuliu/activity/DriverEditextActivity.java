package com.returnlive.wuliu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.constant.ConstantNumber;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 作者： 张梓彬
 * 日期： 2017/5/23 0023
 * 时间： 下午 2:02
 * 描述： 司机姓名，身份证号码，车牌号等页面集成
 */
public class DriverEditextActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.edt_driver_name)
    EditText edt_driver_name;
    @ViewInject(R.id.edt_driver_idcard)
    EditText edt_driver_idcard;
    @ViewInject(R.id.edt_car_wgt)
    EditText edt_car_wgt;
    @ViewInject(R.id.edt_car_nub)
    EditText edt_car_nub;

    private String citySelect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_editext);
        x.view().inject(this);
        getWindow().getAttributes().softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;//设置键盘弹出
        initView();
    }

    private void initView() {
        edt_driver_name.setVisibility(View.GONE);
        edt_driver_idcard.setVisibility(View.GONE);
        edt_car_wgt.setVisibility(View.GONE);
        edt_car_nub.setVisibility(View.GONE);
        switch (ConstantNumber.DRIVER_ACTION_PAGE){
            case ConstantNumber.NUMBER_ONE:
                tv_title.setText(getResources().getString(R.string.your_name));
                edt_driver_name.setVisibility(View.VISIBLE);
                break;
            case ConstantNumber.NUMBER_TWO:
                tv_title.setText(getResources().getString(R.string.id_number));
                edt_driver_idcard.setVisibility(View.VISIBLE);
                break;
            case ConstantNumber.NUMBER_THREE:
                tv_title.setText(getResources().getString(R.string.load));
                edt_car_wgt.setVisibility(View.VISIBLE);
                break;
            case ConstantNumber.NUMBER_SEVENTEEN:
                Intent intent = getIntent();
                citySelect = intent.getStringExtra("cityselect");
                tv_title.setText(getResources().getString(R.string.license_plate_number));
                edt_car_nub.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Event(value = {R.id.img_back, R.id.tv_save})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_save:
                switch (ConstantNumber.DRIVER_ACTION_PAGE){
                    case ConstantNumber.NUMBER_ONE:
                        String name = edt_driver_name.getText().toString().trim();
                        if (name.equals("")||name==null){
                            Toast.makeText(this, getResources().getString(R.string.name_not_empty), Toast.LENGTH_SHORT).show();
                        }else {
                            intentWithData("name",name,ConstantNumber.NUMBER_ONE);
                        }
                        break;
                    case ConstantNumber.NUMBER_TWO:
                        String cardId = edt_driver_idcard.getText().toString().trim();
                        if (cardId.equals("")||cardId==null){
                            Toast.makeText(this, getResources().getString(R.string.idCard_not_empty), Toast.LENGTH_SHORT).show();
                        }else {
                            intentWithData("cardId",cardId,ConstantNumber.NUMBER_TWO);
                        }
                        break;
                    case ConstantNumber.NUMBER_THREE:
                        String carWgt = edt_car_wgt.getText().toString().trim();
                        if (carWgt.equals("")||carWgt==null){
                            Toast.makeText(this, getResources().getString(R.string.car_wgt_not_empty), Toast.LENGTH_SHORT).show();
                        }else {
                            intentWithData("carWgt",carWgt,ConstantNumber.NUMBER_THREE);
                        }
                        break;

                    case ConstantNumber.NUMBER_SEVENTEEN:
                        String number = edt_car_nub.getText().toString();
                        if (!number.equals("")&&number!=null){
                            String licensePlateNumber =citySelect+number;
                            intentWithData("licensePlateNumber",licensePlateNumber,ConstantNumber.NUMBER_SEVENTEEN);
                        }else {
                            Toast.makeText(this, "车牌号不能为空", Toast.LENGTH_SHORT).show();
                        }

                        break;
                }

                break;
        }
    }


    private void intentWithData(String key,String value,int resultCode){
        Intent intent = new Intent();
        intent.putExtra(key,value);
        setResult(resultCode,intent);
        finish();
    }
}
