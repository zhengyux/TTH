package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.fragment.ClassFragment;
import com.taotaohai.fragment.HomeFragment;
import com.taotaohai.fragment.MineFragment;
import com.taotaohai.fragment.VideoFragment;
import com.heima.tabview.library.TabView;
import com.heima.tabview.library.TabViewChild;

import java.util.ArrayList;
import java.util.List;

public class Home extends BaseActivity {

    @Override
    protected void inithttp() {

    }

    MineFragment mineFragment;

    protected void initview() {
        List<TabViewChild> tabViewChildList = new ArrayList<>();
        TabViewChild tabViewChild01 = new TabViewChild(R.mipmap.home1, R.mipmap.home11, "首页", HomeFragment.newInstance());
        TabViewChild tabViewChild02 = new TabViewChild(R.mipmap.home2, R.mipmap.home21, "分类", ClassFragment.newInstance());
        TabViewChild tabViewChild03 = new TabViewChild(R.mipmap.home3, R.mipmap.home31, "视频", VideoFragment.newInstance());
        TabViewChild tabViewChild04 = new TabViewChild(R.mipmap.home4, R.mipmap.home41, "我的", mineFragment=MineFragment.newInstance());
        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        tabViewChildList.add(tabViewChild04);
        TabView tabView = (TabView) findViewById(R.id.tabView);
        tabView.setTextViewSelectedColor(getResources().getColor(R.color.them));
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        tabView.setTabViewChild(tabViewChildList, supportFragmentManager);
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int position, ImageView currentImageIcon, TextView currentTextView) {
//                Toast.makeText(getApplicationContext(), "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initview();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mineFragment.onNewIntent(intent);

    }
}
