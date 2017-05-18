package com.returnlive.wuliu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.constant.NetworkUrl;
import com.returnlive.wuliu.fragment.GoodsFragment;
import com.returnlive.wuliu.fragment.MineFragment;
import com.returnlive.wuliu.fragment.OptionFragment;
import com.returnlive.wuliu.fragment.RouteFragment;
import com.returnlive.wuliu.utils.MyCallBack;
import com.returnlive.wuliu.utils.XUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 张梓彬
 * Data : 2017/5/17 0017
 * Time : 下午 5:32
 * Describe : 车主版MainActivity，用于被Fragment依附
 */
public class OwnerMainActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_main_goods)
    TextView tv_main_goods;
    @ViewInject(R.id.tv_main_route)
    TextView tv_main_route;
    @ViewInject(R.id.tv_main_release_options)
    TextView tv_main_release_options;
    @ViewInject(R.id.tv_main_me)
    TextView tv_main_me;
    private static final String TAG = "OwnerMainActivity";
    private GoodsFragment goodsFragment;
    private RouteFragment routeFragment;
    private OptionFragment optionFragment;
    private MineFragment mineFragment;
    private long exitTime = 0;//点击2次返回，退出程序
    private String url = "http://wuliu.returnlive.com/mobile/Info/outLogin/uid/{uid}/z_session_{uid}/{z_session_uid}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_main);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        goodsFragment = new GoodsFragment();
        routeFragment = new RouteFragment();
        optionFragment = new OptionFragment();
        mineFragment = new MineFragment();
        setReplaceFragment(goodsFragment);
        tv_main_goods.setSelected(true);
        tv_main_goods.setTextColor(getResources().getColor(R.color.textselsecond));
    }

    @Event(value = {R.id.tv_main_goods, R.id.tv_main_route, R.id.tv_main_release_options, R.id.tv_main_me})
    private void onClick(View view) {
        tv_main_goods.setSelected(false);
        tv_main_route.setSelected(false);
        tv_main_release_options.setSelected(false);
        tv_main_me.setSelected(false);
        tv_main_goods.setTextColor(getResources().getColor(R.color.textselfirst));
        tv_main_route.setTextColor(getResources().getColor(R.color.textselfirst));
        tv_main_release_options.setTextColor(getResources().getColor(R.color.textselfirst));
        tv_main_me.setTextColor(getResources().getColor(R.color.textselfirst));
        switch (view.getId()) {
            case R.id.tv_main_goods:
                setReplaceFragment(goodsFragment);
                tv_main_goods.setSelected(true);
                tv_main_goods.setTextColor(getResources().getColor(R.color.textselsecond));

                break;
            case R.id.tv_main_route:
                setReplaceFragment(routeFragment);
                tv_main_route.setSelected(true);
                tv_main_route.setTextColor(getResources().getColor(R.color.textselsecond));

                break;
            case R.id.tv_main_release_options:
                setReplaceFragment(optionFragment);
                tv_main_release_options.setSelected(true);
                tv_main_release_options.setTextColor(getResources().getColor(R.color.textselsecond));

                break;
            case R.id.tv_main_me:
                setReplaceFragment(mineFragment);
                tv_main_me.setSelected(true);
                tv_main_me.setTextColor(getResources().getColor(R.color.textselsecond));

                break;
        }
    }

    private void setReplaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.owner_main_fragment, fragment).commit();
    }


    //点击两次退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {//两秒内再次点击返回则退出
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        exitInterface();
                    }
                }).start();

            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    //用户退出接口
    private void exitInterface() {
        XUtil.Get(NetworkUrl.EXIT_SYSTEM, null, new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                System.exit(0);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                System.exit(0);

            }
        });

    }
}
