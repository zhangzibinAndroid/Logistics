package com.returnlive.wuliu.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.entity.UpdateInfo;
import com.returnlive.wuliu.utils.UpdateInfoService;
import com.returnlive.wuliu.utils.XUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;


public class SettingActivity extends AppCompatActivity {


    @ViewInject(R.id.chb_open_voice_broadcast_remind)
    CheckBox chb_open_voice_broadcast_remind;
    @ViewInject(R.id.tv_version)
    TextView tvVersion;
    // 更新版本要用到的一些信息
    private UpdateInfo info;
    private ProgressDialog pBar;
    private boolean isLoading = false;//防止重复加载
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        x.view().inject(this);
        initView();
    }

    /**
     * 初始化配置
     */
    private void initView() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                UpdateInfoService updateInfoService = new UpdateInfoService(
                        SettingActivity.this);
                try {
                    info = updateInfoService.getUpDateInfo();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isNeedUpdate()) {
                                tvVersion.setText("有新版本" + info.getVersion() + ",请更新体验");
                            } else {
                                tvVersion.setText(getResources().getString(R.string.version_newest));
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        isLoading = false;
    }

    private void versionUpdata() {
        isLoading = true;
        new Thread() {
            public void run() {
                try {
                    UpdateInfoService updateInfoService = new UpdateInfoService(
                            SettingActivity.this);
                    info = updateInfoService.getUpDateInfo();
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 如果有更新就提示
            if (isNeedUpdate()) {
                showUpdateDialog();
            }
        }

        ;
    };


    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("有新版本" + info.getVersion());
        builder.setMessage(info.getDescription());
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    downFile(info.getUrl());
                } else {
                    Toast.makeText(SettingActivity.this, "SD卡不可用，请插入SD卡",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                isLoading = false;

            }

        });
        builder.create().show();
    }

    private boolean isNeedUpdate() {
        String v = info.getVersion(); // 最新版本的版本号
        if (v.equals(getVersion())) {
            return false;
        } else {
            return true;
        }
    }

    // 获取当前版本的版本号
    private String getVersion() {
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号未知";

        }
    }

    private void downloadprogressfile(String url) {        //文件下载地址
        RequestParams param  = new RequestParams(url);
        param.setSaveFilePath(Environment.getExternalStorageDirectory()+"/abc/abc.apk");
        x.http().get(param, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File file) {
                down();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                pBar.setMax((int) total);
                pBar.setProgress((int) current);

            }
        });
    }


    void downFile(final String url) {
        pBar = new ProgressDialog(SettingActivity.this);    //进度条，在下载的时候实时更新进度，提高用户友好度
        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pBar.setTitle("正在下载");
        pBar.setMessage("请稍候...");
        pBar.setProgress(0);
        pBar.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadprogressfile(url);
            }
        }).start();
    }

    void down() {
        handler.post(new Runnable() {
            public void run() {
                pBar.cancel();
                update();
            }
        });
    }

    void update() {
        isLoading = false;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory() + "/wuliu", "wuliu.apk")),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }


    @Event(value = {R.id.img_back, R.id.tv_modify_login_password, R.id.tv_modify_pay_password, R.id.lay_voice_broadcast_time, R.id.lay_help, R.id.lay_version, R.id.tv_about, R.id.tv_exit})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.tv_modify_login_password:
                break;
            case R.id.tv_modify_pay_password:
                break;
            case R.id.lay_voice_broadcast_time:
                break;
            case R.id.lay_help:
                pageJump(HelpCenterActivity.class);
                break;
            case R.id.lay_version:
                if (!isLoading){
                    versionUpdata();
                }
                break;
            case R.id.tv_about:
                pageJump(AboutActivity.class);
                break;
            case R.id.tv_exit:
                break;
        }
    }


    public void pageJump(Class<?> cls) {
        Intent intent = new Intent(getApplicationContext(), cls);
        startActivity(intent);
    }
}
