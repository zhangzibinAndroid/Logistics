package com.returnlive.wuliu.activity;

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

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.constant.ConstantNumber;
import com.returnlive.wuliu.utils.ImageUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

/**
 * 作者： 张梓彬
 * 日期： 2017/5/22 0022
 * 时间： 下午 5:04
 * 描述： 货主认证照片显示页面
 */
public class ShipperPhoneActivity extends AppCompatActivity {


    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.img_head_portrait)
    ImageView img_head_portrait;
    @ViewInject(R.id.img_business_card)
    ImageView img_business_card;
    @ViewInject(R.id.img_door_picture)
    ImageView img_door_picture;
    @ViewInject(R.id.img_business_license)
    ImageView img_business_license;

    protected static final int CHOOSE_PICTURE = ConstantNumber.NUMBER_ZERO;
    protected static final int TAKE_PICTURE = ConstantNumber.NUMBER_ONE;
    private static final int CROP_SMALL_PICTURE = ConstantNumber.NUMBER_TWO;
    protected static Uri tempUri;
    public static String imagePath = "",imgPath = "",businessPath = "",doorPath = "",licensePath = "";
    public static Bitmap head_portrait_bitmap,businessCardBitmap,doorPhoneBitmap,businessLicenseBitmap;
    private static final String TAG = "ShipperPhoneActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        x.view().inject(this);
        initView();

    }

    private void initView() {
        img_head_portrait.setVisibility(View.GONE);
        img_business_card.setVisibility(View.GONE);
        img_door_picture.setVisibility(View.GONE);
        img_business_license.setVisibility(View.GONE);

        switch (ConstantNumber.CAMERA_TYPE){
            case ConstantNumber.NUMBER_THREE:
                img_head_portrait.setVisibility(View.VISIBLE);
                tv_title.setText(getResources().getString(R.string.head_portrait));
                img_head_portrait.setImageBitmap(ShipperCertificationActivity.head_portrait_bitmap);
                break;
            case ConstantNumber.NUMBER_FOUR:
                img_business_card.setVisibility(View.VISIBLE);
                tv_title.setText(getResources().getString(R.string.business_card));
                img_business_card.setImageBitmap(ShipperCertificationActivity.businessCardBitmap);
                break;
            case ConstantNumber.NUMBER_FIVE:
                img_door_picture.setVisibility(View.VISIBLE);
                tv_title.setText(getResources().getString(R.string.door_head));
                img_door_picture.setImageBitmap(ShipperCertificationActivity.doorPhoneBitmap);
                break;
            case ConstantNumber.NUMBER_SIX:
                img_business_license.setVisibility(View.VISIBLE);
                tv_title.setText(getResources().getString(R.string.business_license));
                img_business_license.setImageBitmap(ShipperCertificationActivity.businessLicenseBitmap);
                break;
        }

    }


    @Event(value = {R.id.img_back, R.id.img_head_portrait, R.id.img_business_card, R.id.img_door_picture, R.id.img_business_license,R.id.tv_save})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_save:
                if (ConstantNumber.CAMERA_TYPE==ConstantNumber.NUMBER_THREE){
                    head_portrait_bitmap = ((BitmapDrawable)img_head_portrait.getDrawable()).getBitmap();
                    finishActivityWithData(ConstantNumber.NUMBER_SEVEN);
                    ShipperCertificationActivity.imgPath = imgPath;

                }else if (ConstantNumber.CAMERA_TYPE==ConstantNumber.NUMBER_FOUR){
                    businessCardBitmap = ((BitmapDrawable)img_business_card.getDrawable()).getBitmap();
                    finishActivityWithData(ConstantNumber.NUMBER_EIGHT);
                    ShipperCertificationActivity.businessPath = businessPath;


                }else if (ConstantNumber.CAMERA_TYPE==ConstantNumber.NUMBER_FIVE){
                    doorPhoneBitmap = ((BitmapDrawable)img_door_picture.getDrawable()).getBitmap();
                    finishActivityWithData(ConstantNumber.NUMBER_NINE);
                    ShipperCertificationActivity.doorPath = doorPath;


                }else if (ConstantNumber.CAMERA_TYPE==ConstantNumber.NUMBER_SIX){
                    businessLicenseBitmap = ((BitmapDrawable)img_business_license.getDrawable()).getBitmap();
                    finishActivityWithData(ConstantNumber.NUMBER_TEN);
                    ShipperCertificationActivity.licensePath = licensePath;

                }
                break;
            case R.id.img_head_portrait:
                showChoosePicDialog("设置头像");
                break;
            case R.id.img_business_card:
                showChoosePicDialog("设置名片");
                break;
            case R.id.img_door_picture:
                showChoosePicDialog("设置门头照");
                break;
            case R.id.img_business_license:
                showChoosePicDialog("设置营业执照");
                break;
        }
    }


    private void finishActivityWithData(int resultCode){
        Intent intent = new Intent();
        setResult(resultCode, intent);
        finish();
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
                                setImageToView(data,img_head_portrait,"head_portrait_pic"); // 让刚才选择裁剪得到的图片显示在界面上
                                imgPath = imagePath;

                                break;
                            case ConstantNumber.NUMBER_FOUR:
                                setImageToView(data,img_business_card,"business_card_pic");
                                businessPath = imagePath;

                                break;
                            case ConstantNumber.NUMBER_FIVE:
                                setImageToView(data,img_door_picture,"door_picture_pic");
                                doorPath = imagePath;

                                break;
                            case ConstantNumber.NUMBER_SIX:
                                setImageToView(data,img_business_license,"business_license_pic");
                                licensePath = imagePath;

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
    }

}
