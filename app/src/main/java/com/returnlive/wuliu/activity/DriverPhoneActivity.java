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
 * 日期： 2017/5/23 0023
 * 时间： 下午 3:45
 * 描述： 司机认证照片显示页面
 */

public class DriverPhoneActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.img_driver_portrait)
    ImageView img_driver_portrait;
    @ViewInject(R.id.img_drive_license)
    ImageView img_drive_license;
    @ViewInject(R.id.img_driving_license)
    ImageView img_driving_license;
    @ViewInject(R.id.img_headstock_according)
    ImageView img_headstock_according;

    protected static final int CHOOSE_PICTURE = ConstantNumber.NUMBER_ZERO;
    protected static final int TAKE_PICTURE = ConstantNumber.NUMBER_ONE;
    private static final int CROP_SMALL_PICTURE = ConstantNumber.NUMBER_TWO;
    protected static Uri tempUri;
    public static String imagePath = "",portraitPath = "",driveLicensePath = "",drivingLicensePath = "",headstockAccordingPath = "";
    public static Bitmap portraitBitmap,driveLicenseBitmap,drivingLicenseBitmap,headstockAccordingBitmap;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_phone);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        img_driver_portrait.setVisibility(View.GONE);
        img_drive_license.setVisibility(View.GONE);
        img_driving_license.setVisibility(View.GONE);
        img_headstock_according.setVisibility(View.GONE);

        switch (ConstantNumber.DRIVER_ACTION_PAGE) {
            case ConstantNumber.NUMBER_FOUR:
                tv_title.setText(getResources().getString(R.string.head_portrait));
                img_driver_portrait.setVisibility(View.VISIBLE);
                img_driver_portrait.setImageBitmap(DriverCertificationActivity.portraitBitmap);
                break;
            case ConstantNumber.NUMBER_SIX:
                tv_title.setText(getResources().getString(R.string.drive_license));
                img_drive_license.setVisibility(View.VISIBLE);
                img_drive_license.setImageBitmap(DriverCertificationActivity.driveLicenseBitmap);

                break;
            case ConstantNumber.NUMBER_SEVEN:
                tv_title.setText(getResources().getString(R.string.driving_license));
                img_driving_license.setVisibility(View.VISIBLE);
                img_driving_license.setImageBitmap(DriverCertificationActivity.drivingLicenseBitmap);

                break;
            case ConstantNumber.NUMBER_EIGHT:
                tv_title.setText(getResources().getString(R.string.headstock_according));
                img_headstock_according.setVisibility(View.VISIBLE);
                img_headstock_according.setImageBitmap(DriverCertificationActivity.headstockAccordingBitmap);

                break;
        }
    }


    private void finishActivityWithData(int resultCode) {
        Intent intent = new Intent();
        setResult(resultCode, intent);
        finish();
    }


    protected void showChoosePicDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        String[] items = {"选择本地照片", "拍照"};
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
                        switch (ConstantNumber.DRIVER_ACTION_PAGE) {
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

                        tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/wuliu", pathName));
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
                        switch (ConstantNumber.DRIVER_ACTION_PAGE) {
                            case ConstantNumber.NUMBER_FOUR:
                                setImageToView(data, img_driver_portrait, "img_portrait"); // 让刚才选择裁剪得到的图片显示在界面上
                                portraitPath = imagePath;
                                break;
                            case ConstantNumber.NUMBER_SIX:
                                setImageToView(data, img_drive_license, "img_driverPic");
                                driveLicensePath = imagePath;

                                break;
                            case ConstantNumber.NUMBER_SEVEN:
                                setImageToView(data, img_driving_license, "img_drivingPic");
                                drivingLicensePath = imagePath;

                                break;
                            case ConstantNumber.NUMBER_EIGHT:
                                setImageToView(data, img_headstock_according, "img_carPic");
                                headstockAccordingPath = imagePath;

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
        switch (ConstantNumber.DRIVER_ACTION_PAGE) {
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


    protected void setImageToView(Intent data, ImageView imageView, String fileName) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            imageView.setImageBitmap(photo);
            uploadPic(photo, fileName);

        }
    }

    private void uploadPic(Bitmap bitmap, String fileName) {
        imagePath = ImageUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory() + "/wuliu", fileName);
    }

    @Event(value = {R.id.img_back, R.id.tv_save, R.id.img_driver_portrait, R.id.img_drive_license, R.id.img_driving_license, R.id.img_headstock_according})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_save:
                switch (ConstantNumber.DRIVER_ACTION_PAGE) {
                    case ConstantNumber.NUMBER_FOUR:
                        portraitBitmap = ((BitmapDrawable)img_driver_portrait.getDrawable()).getBitmap();
                        finishActivityWithData(ConstantNumber.NUMBER_FOUR);
                        break;
                    case ConstantNumber.NUMBER_SIX:
                        driveLicenseBitmap = ((BitmapDrawable)img_drive_license.getDrawable()).getBitmap();
                        finishActivityWithData(ConstantNumber.NUMBER_SIX);

                        break;
                    case ConstantNumber.NUMBER_SEVEN:
                        drivingLicenseBitmap = ((BitmapDrawable)img_driving_license.getDrawable()).getBitmap();
                        finishActivityWithData(ConstantNumber.NUMBER_SEVEN);

                        break;
                    case ConstantNumber.NUMBER_EIGHT:
                        headstockAccordingBitmap = ((BitmapDrawable)img_headstock_according.getDrawable()).getBitmap();
                        finishActivityWithData(ConstantNumber.NUMBER_EIGHT);

                        break;
                }

                break;
            case R.id.img_driver_portrait:
                showChoosePicDialog("设置头像");
                break;
            case R.id.img_drive_license:
                showChoosePicDialog("设置驾驶证");

                break;
            case R.id.img_driving_license:
                showChoosePicDialog("设置行驶证");

                break;
            case R.id.img_headstock_according:
                showChoosePicDialog("设置车头照");

                break;
        }
    }
}
