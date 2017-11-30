package com.taotaohai.activity;

import android.app.Activity;
import android.os.Bundle;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;

public class ClassActivity extends BaseActivity {

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
    }
}
