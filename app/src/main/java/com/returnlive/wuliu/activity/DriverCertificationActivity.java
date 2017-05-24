package com.returnlive.wuliu.activity;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.constant.ConstantNumber;
import com.returnlive.wuliu.constant.NetworkUrl;
import com.returnlive.wuliu.utils.ImageUtils;
import com.returnlive.wuliu.utils.MyCallBack;
import com.returnlive.wuliu.utils.XUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 作者： 张梓彬
 * 日期： 2017/5/22 0022
 * 时间： 上午 11:44
 * 描述： 司机认证页面
 */
public class DriverCertificationActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_yourname)
    TextView tv_yourname;
    @ViewInject(R.id.tv_yourIDcard)
    TextView tv_yourIDcard;
    @ViewInject(R.id.tv_yourCardId)
    TextView tv_yourCardId;
    @ViewInject(R.id.tv_yourCarStatus)
    TextView tv_yourCarStatus;
    @ViewInject(R.id.tv_yourcarWeight)
    TextView tv_yourcarWeight;
    @ViewInject(R.id.img_portrait)
    ImageView img_portrait;
    @ViewInject(R.id.idCard1)
    ImageView idCard1;
    @ViewInject(R.id.idCard2)
    ImageView idCard2;
    @ViewInject(R.id.img_driverPic)
    ImageView img_driverPic;
    @ViewInject(R.id.img_drivingPic)
    ImageView img_drivingPic;
    @ViewInject(R.id.img_carPic)
    ImageView img_carPic;
    private ArrayList<String> mListProvince = new ArrayList<String>();
    private String[] city = new String[]{"京", "津", "沪", "渝", "冀", "豫", "云", "辽", "黑", "湘", "皖", "鲁", "新", "苏", "浙", "赣", "鄂", "桂", "甘", "晋", "蒙", "陕", "吉", "闽", "贵", "粤", "青", "藏", "川", "宁", "琼"};
    private static final String TAG = "DriverCertificationActivity";
    private String[] models = {"平板", "高栏", "厢式", "保温", "冷藏", "集装箱", "面包车", "危险品", "其他"};
    private String[] carLength = {"4.2米", "5.0米", "6.2米", "6.8米", "7.2米", "7.7米", "7.8米", "8.2米", "8.7米", "9.6米", "12.5米","13.0米", "15.0米", "16.0米", "17.5米", "自定义"};

    protected static final int CHOOSE_PICTURE = ConstantNumber.NUMBER_ZERO;
    protected static final int TAKE_PICTURE = ConstantNumber.NUMBER_ONE;
    private static final int CROP_SMALL_PICTURE = ConstantNumber.NUMBER_TWO;
    protected static Uri tempUri;
    public static String imagePath = "",portraitPath = "",driveLicensePath = "",drivingLicensePath = "",headstockAccordingPath = "",cardPath1 = "",cardPath2 = "";
    private boolean isPortraitSet,isDriveLicense,isDrivingLicense,isHeadstockAccording,isSetIdCard = false;
    public static Bitmap portraitBitmap,driveLicenseBitmap,drivingLicenseBitmap,headstockAccordingBitmap,idCard_positive_bitmap,idCard_reverse_bitmap;
    private OptionsPickerView<String> mOpv;
    private ArrayList number = new ArrayList<>();
    private String carModels, carSize;
    private String name,idCardNumber,carNumber,carStatus,carWeight,carWgt;
    private int carStatusPosition;
    private ProgressDialog pro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        x.view().inject(this);
        initView();
    }

    /**
     * 初始化配置
     */
    private void initView() {
        File file = new File(Environment.getExternalStorageDirectory()+"/wuliu");
        if (!file.exists()) {
            file.mkdir();
        }

        for (int i = 0; i < city.length; i++) {
            mListProvince.add(city[i]);
        }
        String a[] = new String[26];
        char num[] = new char[26];
        char words = 'A';
        for (int i = 0; i < 26; i++) {
            num[i] = (char) (words + i);
            a[i] = String.valueOf(num[i]);
        }
        for (int i = 0; i < a.length; i++) {
            number.add(a[i]);
        }

        mOpv = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = mListProvince.get(options1) + number.get(options2);
                ConstantNumber.DRIVER_ACTION_PAGE = ConstantNumber.NUMBER_SEVENTEEN;
                Intent intent = new Intent(DriverCertificationActivity.this, DriverEditextActivity.class);
                intent.putExtra("cityselect", tx);
                startActivityForResult(intent, ConstantNumber.NUMBER_SEVENTEEN);
            }
        })
                .setTitleText("选择车牌号")
                .setContentTextSize(20)//设置滚轮文字大小
                .setSelectOptions(0,0)//默认选中项
                .build();

        mOpv.setNPicker(mListProvince, number,null);


    }


    private void showDialoge() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("选择车型");
        dialog.setItems(models, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                carModels = models[which];
                dialog.dismiss();
                showDialogeLength();

            }
        });
        dialog.show();
    }

    private void showDialogeLength() {
        AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
        dialog2.setTitle("选择长度");
        dialog2.setItems(carLength, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                carSize = carLength[which];
                if (carLength[which].equals("自定义")) {
                    showDialoge3();
                } else {
                    tv_yourCarStatus.setText(carModels + "  " + carSize);
                }

            }
        });
        dialog2.show();
    }


    private void showDialoge3() {
        AlertDialog.Builder dialog3 = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_car_status_layout, null);
        final EditText edt_printName = (EditText) view.findViewById(R.id.edt_print);
        dialog3.setView(view);
        dialog3.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String length = edt_printName.getText().toString();
                if (!length.equals("") && length != null) {
                    tv_yourCarStatus.setText(carModels + "  " + length + "米");
                } else {
                    Toast.makeText(DriverCertificationActivity.this, "您还没有输入长度", Toast.LENGTH_SHORT).show();
                }


            }
        });
        dialog3.setNeutralButton("取消", null);
        dialog3.show();
    }

    @Event(value = {R.id.img_back, R.id.lay_yourname, R.id.lay_yourIDcard, R.id.lay_yourCardId, R.id.lay_yourCarStatus, R.id.lay_yourcarWeight, R.id.lay_portrait, R.id.lay_identity_certification, R.id.lay_drive_license, R.id.lay_driving_license, R.id.lay_headstock_according, R.id.tv_contact_customer_service, R.id.btn_submit})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.lay_yourname:
                ConstantNumber.DRIVER_ACTION_PAGE = ConstantNumber.NUMBER_ONE;
                pageJumpWithData(DriverEditextActivity.class,ConstantNumber.NUMBER_ONE);
                break;
            case R.id.lay_yourIDcard:
                ConstantNumber.DRIVER_ACTION_PAGE = ConstantNumber.NUMBER_TWO;
                pageJumpWithData(DriverEditextActivity.class,ConstantNumber.NUMBER_TWO);

                break;
            case R.id.lay_yourCardId:
                mOpv.show();
                break;
            case R.id.lay_yourCarStatus:
                showDialoge();
                break;
            case R.id.lay_yourcarWeight:
                ConstantNumber.DRIVER_ACTION_PAGE = ConstantNumber.NUMBER_THREE;
                pageJumpWithData(DriverEditextActivity.class,ConstantNumber.NUMBER_THREE);

                break;
            case R.id.lay_portrait:
                ConstantNumber.DRIVER_ACTION_PAGE = ConstantNumber.NUMBER_FOUR;
                if (!isPortraitSet){
                    showChoosePicDialog("设置头像");
                }else {
                    pageJumpWithData(DriverPhoneActivity.class,ConstantNumber.NUMBER_FOUR);
                    portraitBitmap = ((BitmapDrawable) img_portrait.getDrawable()).getBitmap();
                }
                break;
            case R.id.lay_identity_certification:
                if (!isSetIdCard){
                    pageJumpWithData(DriverIdCardActivity.class,ConstantNumber.NUMBER_FIVE);
                }else {
                    ConstantNumber.DRIVER_ACTION_PAGE = ConstantNumber.NUMBER_FIVE;
                    DriverIdCardActivity.ID_CARD_POSITIVE = 1;
                    DriverIdCardActivity.ID_CARD_REVERSE = 1;
                    idCard_positive_bitmap = ((BitmapDrawable)idCard1.getDrawable()).getBitmap();
                    idCard_reverse_bitmap = ((BitmapDrawable)idCard2.getDrawable()).getBitmap();
                    pageJumpWithData(DriverIdCardActivity.class,ConstantNumber.NUMBER_FIVE);

                }

                break;
            case R.id.lay_drive_license:
                ConstantNumber.DRIVER_ACTION_PAGE = ConstantNumber.NUMBER_SIX;
                if (!isDriveLicense){
                    showChoosePicDialog("设置驾驶证");
                }else {
                    pageJumpWithData(DriverPhoneActivity.class,ConstantNumber.NUMBER_SIX);
                    driveLicenseBitmap = ((BitmapDrawable) img_driverPic.getDrawable()).getBitmap();
                }

                break;
            case R.id.lay_driving_license:
                ConstantNumber.DRIVER_ACTION_PAGE = ConstantNumber.NUMBER_SEVEN;
                if (!isDrivingLicense){
                    showChoosePicDialog("设置行驶证");
                }else {
                    pageJumpWithData(DriverPhoneActivity.class,ConstantNumber.NUMBER_SEVEN);
                    drivingLicenseBitmap = ((BitmapDrawable) img_drivingPic.getDrawable()).getBitmap();
                }

                break;
            case R.id.lay_headstock_according:
                ConstantNumber.DRIVER_ACTION_PAGE = ConstantNumber.NUMBER_EIGHT;
                if (!isHeadstockAccording){
                    showChoosePicDialog("设置车头照");
                }else {
                    pageJumpWithData(DriverPhoneActivity.class,ConstantNumber.NUMBER_EIGHT);
                    headstockAccordingBitmap = ((BitmapDrawable) img_carPic.getDrawable()).getBitmap();
                }
                break;
            case R.id.tv_contact_customer_service:

                break;
            case R.id.btn_submit:
                name = tv_yourname.getText().toString();
                idCardNumber = tv_yourIDcard.getText().toString();
                carNumber = tv_yourCardId.getText().toString();
                carStatus = tv_yourCarStatus.getText().toString();
                carWeight = tv_yourcarWeight.getText().toString();

                if (name.equals("姓名")){
                    Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                }else if (idCardNumber.equals("身份证号")){
                    Toast.makeText(this, "身份证号码不能为空", Toast.LENGTH_SHORT).show();
                }else if (carNumber.equals("车牌号")){
                    Toast.makeText(this, "车牌号不能为空", Toast.LENGTH_SHORT).show();
                } else if (carStatus.equals("车型")) {
                    Toast.makeText(this, "车型不能为空", Toast.LENGTH_SHORT).show();
                } else if (carWeight.equals("载重")) {
                    Toast.makeText(this, "载重不能为空", Toast.LENGTH_SHORT).show();
                }else if (portraitPath.equals("")||portraitPath==null){
                    Toast.makeText(this, "头像未设置", Toast.LENGTH_SHORT).show();
                }else if (driveLicensePath.equals("")||driveLicensePath==null){
                    Toast.makeText(this, "驾驶证未设置", Toast.LENGTH_SHORT).show();
                }else if (drivingLicensePath.equals("")||drivingLicensePath==null){
                    Toast.makeText(this, "行驶证未设置", Toast.LENGTH_SHORT).show();
                }else if (headstockAccordingPath.equals("")||headstockAccordingPath==null){
                    Toast.makeText(this, "车头照未设置", Toast.LENGTH_SHORT).show();
                }else if (cardPath1.equals("")||cardPath1==null){
                    Toast.makeText(this, "身份证正面照未设置", Toast.LENGTH_SHORT).show();
                }else if (cardPath2.equals("")||cardPath2==null){
                    Toast.makeText(this, "身份证反面照未设置", Toast.LENGTH_SHORT).show();
                }else {
                    pro = new ProgressDialog(this);
                    pro.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pro.setMessage("信息上传中...");
                    pro.setCanceledOnTouchOutside(false);
                    pro.setCancelable(false);
                    pro.show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            uploadfile();
                        }
                    }).start();
                }



                break;
        }
    }

    private void uploadfile() {
        File portraitFile = new File(portraitPath);//头像
        File driveLicenseFile = new File(driveLicensePath);//驾驶证
        File drivingLicenseFil = new File(drivingLicensePath);//行驶证
        File headstockAccordingFile = new File(headstockAccordingPath);//车头照
        File fileIDCard1 = new File(cardPath1);//身份证正面照
        File fileIDCard2 = new File(cardPath2);//身份证反面照
        Map<String,Object> map=new HashMap<>();        //传入自己的相应参数

        for (int i = 0; i < models.length; i++) {
            if (models[i]==carModels){
                carStatusPosition = i+1;
            }
        }

        map.put("name", name);
        map.put("img", portraitFile);
        map.put("card_id", idCardNumber);
        map.put("card_img[]", fileIDCard1);
        map.put("card_img[]", fileIDCard2);
        map.put("plate", carNumber);
        map.put("driver_img",driveLicenseFile);
        map.put("car_type", carStatusPosition);
        map.put("car_lenth", carSize);
        map.put("travel_img", drivingLicenseFil);
        map.put("load", Float.valueOf(carWgt));
        map.put("front", headstockAccordingFile);
        NetworkUrl networkUrl = new NetworkUrl();

        XUtil.UpLoadFile(networkUrl.DRIVER_CERTIFICATION_URL, map, new MyCallBack<File>(){
            @Override
            public void onSuccess(File result) {
                super.onSuccess(result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pro.dismiss();
                        Toast.makeText(DriverCertificationActivity.this, getResources().getString(R.string.up_load_success), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pro.dismiss();
                        Toast.makeText(DriverCertificationActivity.this, getResources().getString(R.string.networ_anomalies), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

    }


    public void pageJumpWithData(Class<?> cls,int requestCode) {
        Intent intent = new Intent(getApplicationContext(), cls);
        startActivityForResult(intent,requestCode);
    }

    protected void showChoosePicDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        String pathName = "";
                        switch (ConstantNumber.DRIVER_ACTION_PAGE){
                            case ConstantNumber.NUMBER_FOUR:
                                pathName = "driver_head_portrait.jpg";
                                break;
                            case ConstantNumber.NUMBER_SIX:
                                pathName = "driver_license.jpg";

                                break;
                            case ConstantNumber.NUMBER_SEVEN:
                                pathName = "driving_license.jpg";

                                break;
                            case ConstantNumber.NUMBER_EIGHT:
                                pathName = "driver_headstock_according.jpg";
                                break;
                        }

                        tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/wuliu", pathName));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        switch (ConstantNumber.DRIVER_ACTION_PAGE){
                            case ConstantNumber.NUMBER_FOUR:
                                setImageToView(data,img_portrait,"img_portrait"); // 让刚才选择裁剪得到的图片显示在界面上
                                isPortraitSet = true;
                                portraitPath = imagePath;

                                break;
                            case ConstantNumber.NUMBER_SIX:
                                setImageToView(data,img_driverPic,"img_driverPic");
                                isDriveLicense = true;
                                driveLicensePath = imagePath;

                                break;
                            case ConstantNumber.NUMBER_SEVEN:
                                setImageToView(data,img_drivingPic,"img_drivingPic");
                                isDrivingLicense = true;
                                drivingLicensePath = imagePath;

                                break;
                            case ConstantNumber.NUMBER_EIGHT:
                                setImageToView(data,img_carPic,"img_carPic");
                                isHeadstockAccording = true;
                                headstockAccordingPath = imagePath;

                                break;
                        }
                    }
                    break;
            }
        }else if (requestCode == ConstantNumber.NUMBER_FOUR && resultCode == ConstantNumber.NUMBER_FOUR){
            img_portrait.setImageBitmap(DriverPhoneActivity.portraitBitmap);
        }else if (requestCode == ConstantNumber.NUMBER_SIX && resultCode == ConstantNumber.NUMBER_SIX){
            img_driverPic.setImageBitmap(DriverPhoneActivity.driveLicenseBitmap);
        }else if (requestCode == ConstantNumber.NUMBER_SEVEN && resultCode == ConstantNumber.NUMBER_SEVEN){
            img_drivingPic.setImageBitmap(DriverPhoneActivity.drivingLicenseBitmap);
        }else if (requestCode == ConstantNumber.NUMBER_EIGHT && resultCode == ConstantNumber.NUMBER_EIGHT){
            img_carPic.setImageBitmap(DriverPhoneActivity.headstockAccordingBitmap);
        }else if (requestCode == ConstantNumber.NUMBER_FIVE && resultCode == ConstantNumber.NUMBER_FIVE){
            idCard1.setImageBitmap(DriverIdCardActivity.idCard_positive_bitmap);
            idCard2.setImageBitmap(DriverIdCardActivity.idCard_reverse_bitmap);
            isSetIdCard = true;
        }else if (requestCode == ConstantNumber.NUMBER_SEVENTEEN && resultCode == ConstantNumber.NUMBER_SEVENTEEN){
            String licensePlateNumber = data.getStringExtra("licensePlateNumber");
            tv_yourCardId.setText(licensePlateNumber);
        }else if (requestCode == ConstantNumber.NUMBER_ONE && resultCode == ConstantNumber.NUMBER_ONE){
            String name = data.getStringExtra("name");
            tv_yourname.setText(name);
        }else if (requestCode == ConstantNumber.NUMBER_TWO && resultCode == ConstantNumber.NUMBER_TWO){
            String idNumber = data.getStringExtra("cardId");
            tv_yourIDcard.setText(idNumber);
        }else if (requestCode == ConstantNumber.NUMBER_THREE && resultCode == ConstantNumber.NUMBER_THREE){
            carWgt = data.getStringExtra("carWgt");
            tv_yourcarWeight.setText(Float.valueOf(carWgt)+"吨");
        }
    }



    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop3", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX3", 1);
        intent.putExtra("aspectY3", 1);
        switch (ConstantNumber.DRIVER_ACTION_PAGE){
            case ConstantNumber.NUMBER_FOUR:
                intent.putExtra("outputX", 150);
                intent.putExtra("outputY", 150);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_SMALL_PICTURE);
                break;
            case ConstantNumber.NUMBER_SIX:
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_SMALL_PICTURE);
                break;
            case ConstantNumber.NUMBER_SEVEN:
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_SMALL_PICTURE);
                break;
            case ConstantNumber.NUMBER_EIGHT:
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_SMALL_PICTURE);
                break;
        }

    }

    protected void setImageToView(Intent data,ImageView imageView,String fileName) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            imageView.setImageBitmap(photo);
            uploadPic(photo,fileName);

        }
    }

    private void uploadPic(Bitmap bitmap,String fileName) {
        imagePath = ImageUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory()+"/wuliu", fileName);
    }
}
