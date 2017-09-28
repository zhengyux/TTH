package com.taotaohai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;

public class ShopIntroducActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_focus;
    private View rela_focus, image_focus;
    private View rela_class;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_introduc);
        setTitle("商家详情");
        initview();
    }

    private void initview() {
        rela_focus = findViewById(R.id.rela_focus);
        image_focus = findViewById(R.id.image_focus);
        rela_focus.setOnClickListener(this);
        tv_focus = (TextView) findViewById(R.id.tv_focus);

    }

    boolean isfocus = false;

    private void focus() {
        isfocus = true;
        image_focus.setVisibility(View.GONE);
        tv_focus.setText("已关注");
        rela_focus.setBackgroundResource(R.drawable.button_r2_glay);
    }

    private void unfocus() {
        isfocus = false;
        image_focus.setVisibility(View.VISIBLE);
        tv_focus.setText("关注");
        rela_focus.setBackgroundResource(R.drawable.button_r2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rela_focus:
                if (isfocus) {
                    unfocus();
                } else {
                    focus();
                }
        }
    }
}
