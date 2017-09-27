package com.taotaohai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.fragment.BookFragment;
import com.taotaohai.fragment.FocuGoodsFragment;
import com.taotaohai.fragment.FocuShopkFragment;
import com.taotaohai.util.ViewFindUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyfocusActivity extends BaseActivity implements OnTabSelectListener {
    private List<Fragment> mFragments = Arrays.asList(FocuShopkFragment.getInstance(), FocuGoodsFragment.getInstance());
    private MyPagerAdapter mAdapter;
    private final String[] mTitles = {
            "关注的商家", "关注的商品"
    };

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfocus);
        setTitle("我的关注");
        initview();
    }

    private void initview() {
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);

        SlidingTabLayout tab = ViewFindUtils.find(decorView, R.id.tab);
        final float scale = getResources().getDisplayMetrics().density;

        tab.setTabWidth((getWindowManager().getDefaultDisplay().getWidth() - 0.5f) / scale / 2);//传过去的是dp要赚回来，我真是醉了，这傻逼怎么这样写，不知道dp不通用么，日了小狗狗了
        tab.setViewPager(vp);
        tab.setOnTabSelectListener(this);
    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
