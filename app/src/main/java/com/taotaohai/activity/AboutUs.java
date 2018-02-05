package com.taotaohai.activity;

import android.os.Bundle;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;

public class AboutUs extends BaseActivity {

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setTitle("关于我们");
    }
}
