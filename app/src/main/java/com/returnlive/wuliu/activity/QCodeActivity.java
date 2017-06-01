package com.returnlive.wuliu.activity;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.returnlive.wuliu.R;
import com.zhy.autolayout.AutoFrameLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * 作者： 张梓彬
 * 日期： 2017/6/1 0001
 * 时间： 下午 6:11
 * 描述： 二维码扫描页面
 */

public class QCodeActivity extends AppCompatActivity implements QRCodeView.Delegate {
    @ViewInject(R.id.scan_barcode)
    TextView scan_barcode;
    @ViewInject(R.id.close_flashlight)
    TextView close_flashlight;
    @ViewInject(R.id.qcode_root)
    AutoFrameLayout qcode_root;
    @ViewInject(R.id.qc_code_zxingview)
    ZXingView qc_code_zxingview;
    private int light=1;//0 关灯 1 开灯
    private boolean isQcode=false;//默认是条形码
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcode);
        x.view().inject(this);
        initView();

    }

    private void initView() {
        qc_code_zxingview.setDelegate(QCodeActivity.this);
        qc_code_zxingview.startSpot();//开始扫描
        qc_code_zxingview.startSpotAndShowRect();//开始识别显示扫描框
        qc_code_zxingview.showScanRect();//显示扫描框
        qc_code_zxingview.startCamera();//开始预览
    }

    @Event(value = {R.id.scan_barcode, R.id.img_back, R.id.close_flashlight})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_barcode:
                if (!isQcode){
                    qc_code_zxingview.changeToScanBarcodeStyle();//条形码
                    scan_barcode.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.ic_qc_code),null,null);
                    scan_barcode.setText("切换二维码");
                    isQcode=true;
                }else if (isQcode){
                    qc_code_zxingview.changeToScanQRCodeStyle();//二维码
                    scan_barcode.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.drawable.ic_qc_code2),null,null);
                    scan_barcode.setText("切换条形码");
                    isQcode=false;
                }
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.close_flashlight:
                switch (light){
                    case 0://关灯
                        qc_code_zxingview.closeFlashlight();
                        close_flashlight.setSelected(false);
                        close_flashlight.setText("开灯");
                        light=1;
                        break;
                    case 1:
                        qc_code_zxingview.openFlashlight();
                        close_flashlight.setSelected(true);
                        close_flashlight.setText("关灯");
                        light=0;
                        break;
                }
                break;
        }
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(QCodeActivity.this, "结果： "+result, Toast.LENGTH_SHORT).show();
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(QCodeActivity.this, "打开相机出错啦！！！", Toast.LENGTH_SHORT).show();
    }
}
