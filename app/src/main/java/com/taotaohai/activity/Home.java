package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.heima.tabview.library.TabView;
import com.heima.tabview.library.TabViewChild;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.fragment.ClassFragment;
import com.taotaohai.fragment.HomeFragment;
import com.taotaohai.fragment.MineFragment;
import com.taotaohai.fragment.VideoFragment;
import com.taotaohai.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class Home extends BaseActivity {


    private List<TabViewChild> tabViewChildList;
    private TabView tabView;

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
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mineFragment.onNewIntent(intent);
        if (getintent("type") != null) {
            initview2();
        }


    }

    private long mExitTime;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
