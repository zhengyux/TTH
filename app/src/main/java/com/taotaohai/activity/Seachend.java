package com.taotaohai.activity;

import android.os.Bundle;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;

public class Seachend extends BaseActivity {

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seachend);
    }

    @Override
    public void onError(Throwable ex, int postcode) {

    }
}
