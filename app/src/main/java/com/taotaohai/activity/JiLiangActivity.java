package com.taotaohai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;

public class JiLiangActivity extends BaseActivity {

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji_liang);
        setTitle("计量告知");
        TextView text1 = (TextView) findViewById(R.id.text1);
        text1.setText(getintent("data"));
    }
}
