package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.heima.tabview.library.TabView;
import com.heima.tabview.library.TabViewChild;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.fragment.ClassFragment;
import com.taotaohai.fragment.HomeFragment;
import com.taotaohai.fragment.MineFragment;
import com.taotaohai.fragment.VideoFragment;
import com.taotaohai.util.SPUtils;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

public class Home extends BaseActivity implements VideoFragment.CallBackValue{


    private List<TabViewChild> tabViewChildList;
    private TabView tabView;
    public static Double LA=0.0;
    public static Double LO=0.0;

    @Override
    protected void inithttp() {
        has.put("username", (String) SPUtils.get(this, "username", ""));
        has.put("password", (String) SPUtils.get(this, "password", ""));
        post("api/auth/login", has, 0);
    }

    MineFragment mineFragment;

    private void initview() {
        tabViewChildList = new ArrayList<>();
        TabViewChild tabViewChild01 = new TabViewChild(R.mipmap.home1, R.mipmap.home11, "首页", HomeFragment.newInstance());
        TabViewChild tabViewChild02 = new TabViewChild(R.mipmap.home2, R.mipmap.home21, "分类", ClassFragment.newInstance());
        TabViewChild tabViewChild03 = new TabViewChild(R.mipmap.home3, R.mipmap.home31, "视频", VideoFragment.newInstance());
        TabViewChild tabViewChild04 = new TabViewChild(R.mipmap.home4, R.mipmap.home41, "我的", mineFragment = MineFragment.newInstance());
        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        tabViewChildList.add(tabViewChild04);

        tabView = (TabView) findViewById(R.id.tabView);

        tabView.setTextViewSelectedColor(getResources().getColor(R.color.them));
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        tabView.setTabViewChild(tabViewChildList, supportFragmentManager);
    }

    public void initview2() {
        TabViewChild tabViewChild02 = new TabViewChild(R.mipmap.home2, R.mipmap.home21, "分类", ClassFragment.newInstance());
        tabViewChildList.remove(1);
        tabViewChildList.add(1, tabViewChild02);
        tabView.setTabViewDefaultPosition(1);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        tabView.setTabViewChild(tabViewChildList, supportFragmentManager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initview();
        inithttp();
        getLoc();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mineFragment.onNewIntent(intent);
        if (getintent("type") != null) {
            initview2();
        }


    }


    public LocationClient mLocationClient = null;


    //定位
    public void getLoc() {

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(new Home.MyLocationListener());
        //注册监听函数
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        option.setCoorType("bd09ll");


        option.setScanSpan(0);


        option.setOpenGps(true);


        option.setLocationNotify(true);

        option.setIgnoreKillProcess(false);


        option.SetIgnoreCacheException(false);


        mLocationClient.setLocOption(option);

        mLocationClient.start();
    }


    private long mExitTime;

//对home监听
    @Override
    protected void onUserLeaveHint() {
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        super.onUserLeaveHint();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {




        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
                if(fragment==1){
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSuccess(String result, int postcode) {

    }

    @Override
    public void onError(Throwable ex, int postcode) {

    }

    int fragment = 0;

    @Override
    public void SendMessageValue(int intValue) {
            fragment=intValue;
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            LA = bdLocation.getLatitude();
            LO = bdLocation.getLongitude();
            Log.e("", "经纬度: "+ LO+"----"+LA);


        }
    }
}
