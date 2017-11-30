package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;


import com.taotaohai.R;
import com.taotaohai.adapter.ViewPagerAdapter;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity implements OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;

    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((boolean) SPUtils.get(this, "guid", false)) {
            startActivity(new Intent(this, Home.class));
            finish();
        } else {
            SPUtils.put(this, "guid", true);
        }
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) > 0) {
            /**为了防止重复启动多个闪屏页面**/
            finish();
            return;
        }
        setContentView(R.layout.city_guide);
        // 初始化页面
        initViews();
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.what_new_four, null));
        views.add(inflater.inflate(R.layout.what_new_two, null));

        views.add(inflater.inflate(R.layout.what_new_one, null));

        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views, this);

        vp = (ViewPager) findViewById(R.id.viewpager_guide);
        vp.setAdapter(vpAdapter);
        // 绑定回调
        vp.setOnPageChangeListener(this);
    }


    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1
                || currentIndex == position) {
            return;
        }
        currentIndex = position;
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    // 当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
    }

}
