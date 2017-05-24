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
 * 日期： 2017/5/22 0022
 * 时间： 上午 11:57
 * 描述： 货主姓名，身份证号码，名片等页面集成
 */
public class ShipperEditextActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.edt_name)
    EditText edt_name;
    @ViewInject(R.id.edt_id_card)
    EditText edt_id_card;
    @ViewInject(R.id.edt_company_name)
    EditText edt_company_name;
    @ViewInject(R.id.edt_company_address)
    EditText edt_company_address;
    @ViewInject(R.id.edt_position)
    EditText edt_position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editext);
        x.view().inject(this);
        getWindow().getAttributes().softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;//设置键盘弹出
        initView();
    }

    private void initView() {
        edt_name.setVisibility(View.GONE);
        edt_id_card.setVisibility(View.GONE);
        edt_company_name.setVisibility(View.GONE);
        edt_company_address.setVisibility(View.GONE);
        edt_position.setVisibility(View.GONE);
        switch (ConstantNumber.ACTION_PAGE){
            case ConstantNumber.NUMBER_ELEVEN:
                tv_title.setText(getResources().getString(R.string.your_name));
                edt_name.setVisibility(View.VISIBLE);
                break;
            case ConstantNumber.NUMBER_TWELVE:
                tv_title.setText(getResources().getString(R.string.id_number));
                edt_id_card.setVisibility(View.VISIBLE);
                break;
            case ConstantNumber.NUMBER_THIRTEEN:
                tv_title.setText(getResources().getString(R.string.company_name));
                edt_company_name.setVisibility(View.VISIBLE);
                break;
            case ConstantNumber.NUMBER_FOURTEEN:
                tv_title.setText(getResources().getString(R.string.company_address));
                edt_company_address.setVisibility(View.VISIBLE);
                break;
            case ConstantNumber.NUMBER_FIFTEEN:
                tv_title.setText(getResources().getString(R.string.your_position));
                edt_position.setVisibility(View.VISIBLE);
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
                switch (ConstantNumber.ACTION_PAGE){
                    case ConstantNumber.NUMBER_ELEVEN:
                        String name = edt_name.getText().toString();
                        if (name.equals("")||name==null){
                            Toast.makeText(this, getResources().getString(R.string.name_not_empty), Toast.LENGTH_SHORT).show();
                        }else {
                            intentWithData("name",name,ConstantNumber.NUMBER_ELEVEN);
                        }
                        break;
                    case ConstantNumber.NUMBER_TWELVE:
                        String idCard = edt_id_card.getText().toString();
                        if (idCard.equals("")||idCard==null){
                            Toast.makeText(this, getResources().getString(R.string.idCard_not_empty), Toast.LENGTH_SHORT).show();
                        }else {
                            intentWithData("idCard",idCard,ConstantNumber.NUMBER_TWELVE);
                        }
                        break;
                    case ConstantNumber.NUMBER_THIRTEEN:
                        String companyName = edt_company_name.getText().toString();
                        if (companyName.equals("")||companyName==null){
                            Toast.makeText(this, getResources().getString(R.string.company_name_not_empty), Toast.LENGTH_SHORT).show();
                        }else {
                            intentWithData("companyName",companyName,ConstantNumber.NUMBER_THIRTEEN);
                        }
                        break;
                    case ConstantNumber.NUMBER_FOURTEEN:
                        String companyAddress = edt_company_address.getText().toString();
                        if (companyAddress.equals("")||companyAddress==null){
                            Toast.makeText(this, getResources().getString(R.string.company_address_not_empty), Toast.LENGTH_SHORT).show();
                        }else {
                            intentWithData("companyAddress",companyAddress,ConstantNumber.NUMBER_FOURTEEN);
                        }

                        break;
                    case ConstantNumber.NUMBER_FIFTEEN:
                        String position = edt_position.getText().toString();
                        if (position.equals("")||position==null){
                            Toast.makeText(this, getResources().getString(R.string.position_not_empty), Toast.LENGTH_SHORT).show();
                        }else {
                            intentWithData("position",position,ConstantNumber.NUMBER_FIFTEEN);
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
