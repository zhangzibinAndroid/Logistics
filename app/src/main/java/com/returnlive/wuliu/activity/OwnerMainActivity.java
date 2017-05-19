package com.returnlive.wuliu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.constant.NetworkUrl;
import com.returnlive.wuliu.fragment.CarGoodsFragment;
import com.returnlive.wuliu.fragment.GoodsFragment;
import com.returnlive.wuliu.fragment.MineFragment;
import com.returnlive.wuliu.fragment.OptionOwnerFragment;
import com.returnlive.wuliu.fragment.OptionShipperFragment;
import com.returnlive.wuliu.fragment.RouteOwnerFragment;
import com.returnlive.wuliu.fragment.RouteShipperFragment;
import com.returnlive.wuliu.utils.MyCallBack;
import com.returnlive.wuliu.utils.XUtil;
import com.zhy.autolayout.AutoLinearLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 张梓彬
 *         Data : 2017/5/17 0017
 *         Time : 下午 5:32
 *         Describe : 车主版MainActivity，用于被Fragment依附
 */
public class OwnerMainActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_main_goods)
    TextView tv_main_goods;
    @ViewInject(R.id.tv_main_route)
    TextView tv_main_route;
    @ViewInject(R.id.tv_main_release_options)
    TextView tv_main_release_options;
    @ViewInject(R.id.tv_main_me)
    public TextView tv_main_me;
    private static final String TAG = "OwnerMainActivity";
    @ViewInject(R.id.tv_main_cars)
    TextView tv_main_cars;
    @ViewInject(R.id.tv_main_route2)
    TextView tv_main_route2;
    @ViewInject(R.id.tv_main_release_goods)
    TextView tv_main_release_goods;
    @ViewInject(R.id.tv_main_me2)
    public TextView tv_main_me2;
    public AutoLinearLayout lay_ship;
    public AutoLinearLayout lay_owner;
    private GoodsFragment goodsFragment;
    private RouteOwnerFragment routeOwnerFragment;
    private OptionOwnerFragment optionOwnerFragment;
    private CarGoodsFragment carGoodsFragment;
    private RouteShipperFragment routeShipperFragment;
    private OptionShipperFragment optionShipperFragment;
    private MineFragment mineFragment;
    private long exitTime = 0;//点击2次返回，退出程序


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_main);
        x.view().inject(this);
        initView();
    }

    public void showLay(boolean isOwner){
        if (isOwner) {
            lay_owner.setVisibility(View.VISIBLE);
            lay_ship.setVisibility(View.GONE);
        }else {
            lay_owner.setVisibility(View.GONE);
            lay_ship.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initView() {
        goodsFragment = new GoodsFragment();
        routeOwnerFragment = new RouteOwnerFragment();
        optionOwnerFragment = new OptionOwnerFragment();
        carGoodsFragment = new CarGoodsFragment();
        routeShipperFragment = new RouteShipperFragment();
        optionShipperFragment = new OptionShipperFragment();
        mineFragment = new MineFragment();
        setReplaceFragment(mineFragment);
        tv_main_me.setSelected(true);
        tv_main_me.setTextColor(getResources().getColor(R.color.textselsecond));
        tv_main_me2.setSelected(true);
        tv_main_me2.setTextColor(getResources().getColor(R.color.textselsecond));
    }

    @Event(value = {R.id.tv_main_goods, R.id.tv_main_route, R.id.tv_main_release_options, R.id.tv_main_me, R.id.tv_main_cars, R.id.tv_main_route2, R.id.tv_main_release_goods, R.id.tv_main_me2})
    private void onClick(View view) {
        tv_main_goods.setSelected(false);
        tv_main_route.setSelected(false);
        tv_main_release_options.setSelected(false);
        tv_main_me.setSelected(false);

        tv_main_cars.setSelected(false);
        tv_main_route2.setSelected(false);
        tv_main_release_goods.setSelected(false);
        tv_main_me2.setSelected(false);

        tv_main_goods.setTextColor(getResources().getColor(R.color.textselfirst));
        tv_main_route.setTextColor(getResources().getColor(R.color.textselfirst));
        tv_main_release_options.setTextColor(getResources().getColor(R.color.textselfirst));
        tv_main_me.setTextColor(getResources().getColor(R.color.textselfirst));

        tv_main_cars.setTextColor(getResources().getColor(R.color.textselfirst));
        tv_main_route2.setTextColor(getResources().getColor(R.color.textselfirst));
        tv_main_release_goods.setTextColor(getResources().getColor(R.color.textselfirst));
        tv_main_me2.setTextColor(getResources().getColor(R.color.textselfirst));
        switch (view.getId()) {
            case R.id.tv_main_goods:
                setReplaceFragment(goodsFragment);
                tv_main_goods.setSelected(true);
                tv_main_goods.setTextColor(getResources().getColor(R.color.textselsecond));

                break;
            case R.id.tv_main_route:
                setReplaceFragment(routeOwnerFragment);
                tv_main_route.setSelected(true);
                tv_main_route.setTextColor(getResources().getColor(R.color.textselsecond));

                break;
            case R.id.tv_main_release_options:
                setReplaceFragment(optionOwnerFragment);
                tv_main_release_options.setSelected(true);
                tv_main_release_options.setTextColor(getResources().getColor(R.color.textselsecond));

                break;
            case R.id.tv_main_me:
                setReplaceFragment(mineFragment);
                tv_main_me.setSelected(true);
                tv_main_me.setTextColor(getResources().getColor(R.color.textselsecond));
                break;
            case R.id.tv_main_cars:
                tv_main_cars.setSelected(true);
                tv_main_cars.setTextColor(getResources().getColor(R.color.textselsecond));
                setReplaceFragment(carGoodsFragment);

                break;
            case R.id.tv_main_route2:
                tv_main_route2.setSelected(true);
                tv_main_route2.setTextColor(getResources().getColor(R.color.textselsecond));
                setReplaceFragment(routeShipperFragment);


                break;
            case R.id.tv_main_release_goods:
                tv_main_release_goods.setSelected(true);
                tv_main_release_goods.setTextColor(getResources().getColor(R.color.textselsecond));
                setReplaceFragment(optionShipperFragment);

                break;
            case R.id.tv_main_me2:
                tv_main_me2.setSelected(true);
                tv_main_me2.setTextColor(getResources().getColor(R.color.textselsecond));
                setReplaceFragment(mineFragment);

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
        XUtil.Get(NetworkUrl.EXIT_SYSTEM, null, new MyCallBack<String>() {
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
