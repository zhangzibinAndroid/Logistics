package com.returnlive.wuliu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.constant.ConstantNumber;
import com.returnlive.wuliu.utils.ImageUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.Calendar;

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
    private String imagePath;


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
                pageJumpWithData(EditextActivity.class,ConstantNumber.NUMBER_ELEVEN);
                break;
            case R.id.lay_yourIDcardship:
                ConstantNumber.ACTION_PAGE = ConstantNumber.NUMBER_TWELVE;
                pageJumpWithData(EditextActivity.class,ConstantNumber.NUMBER_TWELVE);

                break;
            case R.id.lay_portraitship:
                showChoosePicDialog("设置头像");
                ConstantNumber.CAMERA_TYPE = ConstantNumber.NUMBER_THREE;
                Log.e(TAG, "ConstantNumber.CAMERA_TYPE== "+ConstantNumber.CAMERA_TYPE );

                break;
            case R.id.lay_idship_certification:
                break;
            case R.id.tv_company_name:
                ConstantNumber.ACTION_PAGE = ConstantNumber.NUMBER_THIRTEEN;
                pageJumpWithData(EditextActivity.class,ConstantNumber.NUMBER_THIRTEEN);

                break;
            case R.id.tv_company_address:
                ConstantNumber.ACTION_PAGE = ConstantNumber.NUMBER_FOURTEEN;
                pageJumpWithData(EditextActivity.class,ConstantNumber.NUMBER_FOURTEEN);

                break;
            case R.id.lay_yourposition:
                ConstantNumber.ACTION_PAGE = ConstantNumber.NUMBER_FIFTEEN;
                pageJumpWithData(EditextActivity.class,ConstantNumber.NUMBER_FIFTEEN);

                break;
            case R.id.lay_business_card:
                showChoosePicDialog("设置名片");
                ConstantNumber.CAMERA_TYPE = ConstantNumber.NUMBER_FOUR;
                Log.e(TAG, "ConstantNumber.CAMERA_TYPE== "+ConstantNumber.CAMERA_TYPE );
                break;
            case R.id.lay_door_head:
                showChoosePicDialog("设置门头照");
                ConstantNumber.CAMERA_TYPE = ConstantNumber.NUMBER_FIVE;
                Log.e(TAG, "ConstantNumber.CAMERA_TYPE== "+ConstantNumber.CAMERA_TYPE );

                break;
            case R.id.lay_business_license:
                showChoosePicDialog("设置营业执照");
                ConstantNumber.CAMERA_TYPE = ConstantNumber.NUMBER_SIX;
                Log.e(TAG, "ConstantNumber.CAMERA_TYPE== "+ConstantNumber.CAMERA_TYPE );

                break;
            case R.id.tv_contactship_customer:
                break;
            case R.id.btn_shipsubmit:
                break;
        }
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
                                setImageToView(data,img_portraitship,"head_portrait_pic"); // 让刚才选择裁剪得到的图片显示在界面上
                                break;
                            case ConstantNumber.NUMBER_FOUR:
                                setImageToView(data,img_business_card,"business_card_pic");
                                break;
                            case ConstantNumber.NUMBER_FIVE:
                                setImageToView(data,img_door_head,"door_picture_pic");
                                break;
                            case ConstantNumber.NUMBER_SIX:
                                setImageToView(data,img_business_license,"business_license_pic");
                                break;
                        }
                    }
                    break;
            }
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
        Log.e(TAG, "imagePath: "+imagePath);
    }



}
