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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
 * 时间： 上午 10:24
 * 描述： 货主身份证照片页面
 */
public class ShipperIdCardActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.img_idCard_positive)
    ImageView img_idCard_positive;
    @ViewInject(R.id.img_idCard_reverse)
    ImageView img_idCard_reverse;

    protected static final int CHOOSE_PICTURE = ConstantNumber.NUMBER_ZERO;
    protected static final int TAKE_PICTURE = ConstantNumber.NUMBER_ONE;
    private static final int CROP_SMALL_PICTURE = ConstantNumber.NUMBER_TWO;
    protected static Uri tempUri;
    private String imagePath;
    private int whichIdCard = 0;
    public static int ID_CARD_POSITIVE = 0;
    public static int ID_CARD_REVERSE = 0;
    public static Bitmap idCard_positive_bitmap,idCard_reverse_bitmap;
    private static final String TAG = "ShipperIdCardActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card);
        x.view().inject(this);
        tv_title.setText(getResources().getString(R.string.identity_certification));
        if (ConstantNumber.ACTION_PAGE==ConstantNumber.NUMBER_TWO){
            img_idCard_positive.setImageBitmap(ShipperCertificationActivity.idCard_positive_bitmap);
            img_idCard_reverse.setImageBitmap(ShipperCertificationActivity.idCard_reverse_bitmap);
        }
    }

    @Event(value = {R.id.img_back, R.id.tv_save, R.id.img_idCard_positive, R.id.img_idCard_reverse})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_save:
                if (ID_CARD_POSITIVE!=1){
                    Toast.makeText(this, "身份证正面照您还没拍", Toast.LENGTH_SHORT).show();
                    return;
                }else if (ID_CARD_REVERSE!=1){
                    Toast.makeText(this, "身份证反面照您还没拍", Toast.LENGTH_SHORT).show();
                    return;
                }

                idCard_positive_bitmap = ((BitmapDrawable)img_idCard_positive.getDrawable()).getBitmap();
                idCard_reverse_bitmap = ((BitmapDrawable)img_idCard_reverse.getDrawable()).getBitmap();
                ID_CARD_POSITIVE = 0;
                ID_CARD_REVERSE = 0;
                finishActivityWithData(ConstantNumber.NUMBER_SIXTEEN);
                break;
            case R.id.img_idCard_positive:
                whichIdCard = 1;
                showChoosePicDialog("身份证正面照");
                break;
            case R.id.img_idCard_reverse:
                whichIdCard = 2;
                showChoosePicDialog("身份证反面照");
                break;
        }
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
                        if (whichIdCard==1){
                            pathName = "img_idCard_positive.jpg";
                        }else if (whichIdCard==2){
                            pathName = "img_idCard_reverse.jpg";
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
                       if (whichIdCard==1){
                           setImageToView(data,img_idCard_positive,"idCard_positive"); // 让刚才选择裁剪得到的图片显示在界面上
                           ShipperCertificationActivity.cardPath1 = imagePath;
                           ID_CARD_POSITIVE = 1;
                       }else if (whichIdCard==2){
                           setImageToView(data,img_idCard_reverse,"idCard_reverse");
                           ShipperCertificationActivity.cardPath2 = imagePath;
                           ID_CARD_REVERSE = 1;
                       }

                    }
                    break;
            }
        }

    }


    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("X", 1);
        intent.putExtra("Y", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
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

    private void finishActivityWithData(int resultCode){
        Intent intent = new Intent();
        setResult(resultCode, intent);
        finish();
    }
}
