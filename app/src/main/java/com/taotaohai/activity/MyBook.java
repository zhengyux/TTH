package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Book;
import com.taotaohai.fragment.ItemBookFragment;
import com.taotaohai.util.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

public class MyBook extends BaseActivity implements OnTabSelectListener, View.OnClickListener,ItemBookFragment.OnListFragmentInteractionListener{
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private final String[] mTitles = {
            "全部", "待付款", "代发货"
            , "待收货", "待评价"
    };

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initview();
            }
        }, 2000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mMsvLayout.loading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMsvLayout.content();
            }
        }, 2000);
    }

    private void initview() {
        for (String title : mTitles) {
            mFragments.add(ItemBookFragment.newInstance(0));
        }
        findViewById(R.id.back).setOnClickListener(this);
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);

        SlidingTabLayout tab = ViewFindUtils.find(decorView, R.id.tab);
        tab.setViewPager(vp);
        tab.setOnTabSelectListener(this);
    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onListFragmentInteraction(Book.Data item) {
        startActivity(new Intent(MyBook.this, Bookdetial.class).putExtra("id", item.getId()));
    }

    @Override
    public void onListFragmentButton2(Book.Data item) {//第2个按钮

    }

    @Override
    public void onListFragmentButton1(Book.Data item) {//第一个按钮

    }

    @Override
    public void onListFragmentButton3(Book.Data mItem) {//第3个按钮

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
