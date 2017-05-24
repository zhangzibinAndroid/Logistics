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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;

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
    protected static final int CHOOSE_PICTURE = ConstantNumber.NUMBER_ZERO;
    protected static final int TAKE_PICTURE = ConstantNumber.NUMBER_ONE;
    private static final int CROP_SMALL_PICTURE = ConstantNumber.NUMBER_TWO;
    protected static Uri tempUri;
    private static final String TAG = "ShipperCertificationAct";
    public static String imagePath = "",imgPath = "",businessPath = "",doorPath = "",licensePath = "",cardPath1 = "",cardPath2 = "";
    private boolean isSetHead,isSetBusinessCard,isSetDoorPhone,isSetBusinessLicense,isSetIdCard = false;
    public static Bitmap head_portrait_bitmap,businessCardBitmap,doorPhoneBitmap,businessLicenseBitmap;
    public static Bitmap idCard_positive_bitmap,idCard_reverse_bitmap;
    private ProgressDialog pro;
    private String name,idCard,company_name,company_address,position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_certification);
        x.view().inject(this);
        File file = new File(Environment.getExternalStorageDirectory()+"/wuliu");
        if (!file.exists()) {
            file.mkdir();
        }


    }


    @Event(value = {R.id.img_back, R.id.lay_yournameship, R.id.lay_yourIDcardship, R.id.lay_portraitship, R.id.lay_idship_certification, R.id.tv_company_name, R.id.tv_company_address, R.id.lay_yourposition, R.id.lay_business_card, R.id.lay_door_head, R.id.lay_business_license, R.id.tv_contactship_customer, R.id.btn_shipsubmit})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.lay_yournameship:
                ConstantNumber.ACTION_PAGE = ConstantNumber.NUMBER_ELEVEN;
                pageJumpWithData(ShipperEditextActivity.class,ConstantNumber.NUMBER_ELEVEN);
                break;
            case R.id.lay_yourIDcardship:
                ConstantNumber.ACTION_PAGE = ConstantNumber.NUMBER_TWELVE;
                pageJumpWithData(ShipperEditextActivity.class,ConstantNumber.NUMBER_TWELVE);

                break;
            case R.id.lay_portraitship:
                ConstantNumber.CAMERA_TYPE = ConstantNumber.NUMBER_THREE;
                if (!isSetHead){
                    showChoosePicDialog("设置头像");
                }else {
                    head_portrait_bitmap = ((BitmapDrawable) img_portraitship.getDrawable()).getBitmap();
                    pageJumpWithData(ShipperPhoneActivity.class,ConstantNumber.NUMBER_SEVEN);
                }


                break;
            case R.id.lay_idship_certification:
                if (!isSetIdCard){
                    pageJumpWithData(ShipperIdCardActivity.class,ConstantNumber.NUMBER_SIXTEEN);
                }else {
                    ConstantNumber.ACTION_PAGE = ConstantNumber.NUMBER_TWO;
                    ShipperIdCardActivity.ID_CARD_POSITIVE = 1;
                    ShipperIdCardActivity.ID_CARD_REVERSE = 1;
                    idCard_positive_bitmap = ((BitmapDrawable)idCardship1.getDrawable()).getBitmap();
                    idCard_reverse_bitmap = ((BitmapDrawable)idCardship2.getDrawable()).getBitmap();
                    pageJumpWithData(ShipperIdCardActivity.class,ConstantNumber.NUMBER_SIXTEEN);

                }

                break;
            case R.id.tv_company_name:
                ConstantNumber.ACTION_PAGE = ConstantNumber.NUMBER_THIRTEEN;
                pageJumpWithData(ShipperEditextActivity.class,ConstantNumber.NUMBER_THIRTEEN);

                break;
            case R.id.tv_company_address:
                ConstantNumber.ACTION_PAGE = ConstantNumber.NUMBER_FOURTEEN;
                pageJumpWithData(ShipperEditextActivity.class,ConstantNumber.NUMBER_FOURTEEN);

                break;
            case R.id.lay_yourposition:
                ConstantNumber.ACTION_PAGE = ConstantNumber.NUMBER_FIFTEEN;
                pageJumpWithData(ShipperEditextActivity.class,ConstantNumber.NUMBER_FIFTEEN);

                break;
            case R.id.lay_business_card:
                ConstantNumber.CAMERA_TYPE = ConstantNumber.NUMBER_FOUR;
                if (!isSetBusinessCard){
                    showChoosePicDialog("设置名片");
                }else {
                    businessCardBitmap = ((BitmapDrawable) img_business_card.getDrawable()).getBitmap();
                    pageJumpWithData(ShipperPhoneActivity.class,ConstantNumber.NUMBER_EIGHT);
                }
                break;
            case R.id.lay_door_head:
                ConstantNumber.CAMERA_TYPE = ConstantNumber.NUMBER_FIVE;
                if (!isSetDoorPhone){
                    showChoosePicDialog("设置门头照");
                }else {
                    doorPhoneBitmap = ((BitmapDrawable) img_door_head.getDrawable()).getBitmap();
                    pageJumpWithData(ShipperPhoneActivity.class,ConstantNumber.NUMBER_NINE);
                }

                break;
            case R.id.lay_business_license:
                ConstantNumber.CAMERA_TYPE = ConstantNumber.NUMBER_SIX;
                if (!isSetBusinessLicense){
                    showChoosePicDialog("设置营业执照");
                }else {
                    businessLicenseBitmap = ((BitmapDrawable) img_business_license.getDrawable()).getBitmap();
                    pageJumpWithData(ShipperPhoneActivity.class,ConstantNumber.NUMBER_TEN);
                }

                break;
            case R.id.tv_contactship_customer:
                break;
            case R.id.btn_shipsubmit:
                name = tv_yournameship.getText().toString().trim();
                idCard = tv_yourIDcardship.getText().toString().trim();
                company_name = tv_company_name.getText().toString().trim();
                company_address = tv_company_address.getText().toString().trim();
                position = tv_yourposition.getText().toString().trim();

                if (name.equals("姓名")){
                    runOnuiToast("姓名不能为空");
                    return;
                }else if (idCard.equals("身份证号")){
                    runOnuiToast("身份证号不能为空");
                    return;
                }else if (company_name.equals("公司名称")){
                    runOnuiToast("公司名称不能为空");
                    return;
                }else if (company_address.equals("公司地址")){
                    runOnuiToast("公司名称不能为空");
                    return;
                }else if (position.equals("职位")){
                    runOnuiToast("职位不能为空");
                    return;
                }else if (imgPath.equals("")||imgPath==null){
                    runOnuiToast("头像未设置");
                    return;
                }else if (businessPath.equals("")||businessPath==null){
                    runOnuiToast("名片未设置");
                    return;
                }else if (doorPath.equals("")||doorPath==null){
                    runOnuiToast("门头照未设置");
                    return;
                }else if (licensePath.equals("")||licensePath==null){
                    runOnuiToast("营业执照未设置");
                    return;
                }else if (cardPath1.equals("")||cardPath1==null){
                    runOnuiToast("身份证正面照未设置");
                    return;
                }else if (cardPath2.equals("")||cardPath2==null){
                    runOnuiToast("身份证反面照未设置");
                    return;
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
        File fileHead = new File(imgPath);//头像
        File fileBusiness = new File(businessPath);//名片
        File fileDoor = new File(doorPath);//门头照
        File fileLicense = new File(licensePath);//营业执照
        File fileIDCard1 = new File(cardPath1);//身份证正面照
        File fileIDCard2 = new File(cardPath2);//身份证反面照
        Map<String,Object> map=new HashMap<>();        //传入自己的相应参数
        map.put("name", name);
        map.put("img", fileHead);
        map.put("card_id", idCard);
        map.put("card_img[]", fileIDCard1);
        map.put("card_img[]", fileIDCard2);
        map.put("company_name", company_name);
        map.put("company_add",company_address);
        map.put("job", position);
        map.put("door", fileDoor);
        map.put("business", fileBusiness);
        map.put("license", fileLicense);
        NetworkUrl networkUrl = new NetworkUrl();
        XUtil.UpLoadFile(networkUrl.SHIPPER_CERTIFICATION_URL, map, new MyCallBack<File>(){
            @Override
            public void onSuccess(File result) {
                super.onSuccess(result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pro.dismiss();
                        Toast.makeText(ShipperCertificationActivity.this, getResources().getString(R.string.up_load_success), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ShipperCertificationActivity.this, getResources().getString(R.string.networ_anomalies), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

    }


    private void runOnuiToast(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ShipperCertificationActivity.this, text, Toast.LENGTH_SHORT).show();
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
                        if (ConstantNumber.CAMERA_TYPE==ConstantNumber.NUMBER_THREE){
                            pathName = "head_portrait.jpg";
                        }else if (ConstantNumber.CAMERA_TYPE==ConstantNumber.NUMBER_FOUR){
                            pathName = "business_card.jpg";
                        }else if (ConstantNumber.CAMERA_TYPE==ConstantNumber.NUMBER_FIVE){
                            pathName = "door_picture.jpg";
                        }else if (ConstantNumber.CAMERA_TYPE==ConstantNumber.NUMBER_SIX){
                            pathName = "business_license.jpg";
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
                        switch (ConstantNumber.CAMERA_TYPE){
                            case ConstantNumber.NUMBER_THREE:
                                isSetHead = true;
                                setImageToView(data,img_portraitship,"head_portrait_pic"); // 让刚才选择裁剪得到的图片显示在界面上
                                imgPath = imagePath;
                                break;
                            case ConstantNumber.NUMBER_FOUR:
                                isSetBusinessCard = true;
                                setImageToView(data,img_business_card,"business_card_pic");
                                businessPath = imagePath;
                                break;
                            case ConstantNumber.NUMBER_FIVE:
                                isSetDoorPhone = true;
                                setImageToView(data,img_door_head,"door_picture_pic");
                                doorPath = imagePath;

                                break;
                            case ConstantNumber.NUMBER_SIX:
                                isSetBusinessLicense = true;
                                setImageToView(data,img_business_license,"business_license_pic");
                                licensePath = imagePath;
                                break;
                        }
                    }
                    break;
            }
        }else if (requestCode == ConstantNumber.NUMBER_ELEVEN && resultCode == ConstantNumber.NUMBER_ELEVEN){
            String name = data.getStringExtra("name");
            tv_yournameship.setText(name);
        }else if (requestCode == ConstantNumber.NUMBER_TWELVE && resultCode == ConstantNumber.NUMBER_TWELVE){
            String idCard = data.getStringExtra("idCard");
            tv_yourIDcardship.setText(idCard);
        }else if (requestCode == ConstantNumber.NUMBER_THIRTEEN && resultCode == ConstantNumber.NUMBER_THIRTEEN){
            String companyName = data.getStringExtra("companyName");
            tv_company_name.setText(companyName);
        }else if (requestCode == ConstantNumber.NUMBER_FOURTEEN && resultCode == ConstantNumber.NUMBER_FOURTEEN){
            String companyAddress = data.getStringExtra("companyAddress");
            tv_company_address.setText(companyAddress);
        }else if (requestCode == ConstantNumber.NUMBER_FIFTEEN && resultCode == ConstantNumber.NUMBER_FIFTEEN){
            String position = data.getStringExtra("position");
            tv_yourposition.setText(position);
        }else if (requestCode == ConstantNumber.NUMBER_SEVEN && resultCode == ConstantNumber.NUMBER_SEVEN){
            img_portraitship.setImageBitmap(ShipperPhoneActivity.head_portrait_bitmap);

        }else if (requestCode == ConstantNumber.NUMBER_EIGHT && resultCode == ConstantNumber.NUMBER_EIGHT){
            img_business_card.setImageBitmap(ShipperPhoneActivity.businessCardBitmap);
        }else if (requestCode == ConstantNumber.NUMBER_NINE && resultCode == ConstantNumber.NUMBER_NINE){
            img_door_head.setImageBitmap(ShipperPhoneActivity.doorPhoneBitmap);
        }else if (requestCode == ConstantNumber.NUMBER_TEN && resultCode == ConstantNumber.NUMBER_TEN){
            img_business_license.setImageBitmap(ShipperPhoneActivity.businessLicenseBitmap);
        }else if (requestCode == ConstantNumber.NUMBER_SIXTEEN && resultCode == ConstantNumber.NUMBER_SIXTEEN){
            idCardship1.setImageBitmap(ShipperIdCardActivity.idCard_positive_bitmap);
            idCardship2.setImageBitmap(ShipperIdCardActivity.idCard_reverse_bitmap);
            isSetIdCard = true;
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
        switch (ConstantNumber.CAMERA_TYPE){
            case ConstantNumber.NUMBER_THREE:
                intent.putExtra("outputX", 150);
                intent.putExtra("outputY", 150);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_SMALL_PICTURE);
                break;
            case ConstantNumber.NUMBER_FOUR:
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_SMALL_PICTURE);
                break;
            case ConstantNumber.NUMBER_FIVE:
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_SMALL_PICTURE);
                break;
            case ConstantNumber.NUMBER_SIX:
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
