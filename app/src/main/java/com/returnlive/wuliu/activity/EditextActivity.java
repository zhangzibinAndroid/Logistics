package com.returnlive.wuliu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.constant.ConstantNumber;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 作者： 张梓彬
 * 日期： 2017/5/22 0022
 * 时间： 上午 11:57
 * 描述： 姓名，身份证号码，车牌号等页面集成
 */
public class EditextActivity extends AppCompatActivity {
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
        initView();
    }

    private void initView() {
        switch (ConstantNumber.ACTION_PAGE){
            case ConstantNumber.NUMBER_ELEVEN:
                tv_title.setText(getResources().getString(R.string.your_name));
                edt_name.setVisibility(View.VISIBLE);
                break;
            case ConstantNumber.NUMBER_TWELVE:
                break;
            case ConstantNumber.NUMBER_THIRTEEN:
                break;
            case ConstantNumber.NUMBER_FOURTEEN:
                break;
            case ConstantNumber.NUMBER_FIFTEEN:
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
                        intentWithData("name",name,ConstantNumber.NUMBER_ELEVEN);
                        break;
                    case ConstantNumber.NUMBER_TWELVE:
                        break;
                    case ConstantNumber.NUMBER_THIRTEEN:
                        break;
                    case ConstantNumber.NUMBER_FOURTEEN:
                        break;
                    case ConstantNumber.NUMBER_FIFTEEN:
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
