package com.taotaohai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Shop;
import com.taotaohai.util.GlideUtil;

public class ShopIntroducActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_focus;
    private View rela_focus, image_focus;
    private View rela_class;
    private TextView tv_count;

    @Override
    protected void inithttp() {

    }

    Shop shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_introduc);
        setTitle("商家详情");
        shop = (Shop) getIntent().getSerializableExtra("data");
        initview();
        if (ShopActivity.isfocus) {
            ShopActivity.count--;
            focus();
        } else {
            ShopActivity.count++;
            unfocus();
        }
        tv_count.setText(String.valueOf(ShopActivity.count));
    }

    private void initview() {
        tv_count = (TextView) findViewById(R.id.tv_count);
        rela_focus = findViewById(R.id.rela_focus);
        image_focus = findViewById(R.id.image_focus);
        rela_focus.setOnClickListener(this);
        tv_focus = (TextView) findViewById(R.id.tv_focus);
        ImageView image_photo = (ImageView) findViewById(R.id.image_photo);
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_scor = (TextView) findViewById(R.id.tv_scor);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        GlideUtil.loadImg(shop.getData().getLogoIdAbsUrl(), image_photo);
        tv_scor.setText(shop.getData().getTotalCommonLevel() + "分");
        tv_title.setText(shop.getData().getName());

    }


    private void focus() {
        ShopActivity.count++;
        ShopActivity.isfocus = true;
        image_focus.setVisibility(View.GONE);
        tv_focus.setText("已关注");
        rela_focus.setBackgroundResource(R.drawable.button_r2_glay);
    }

    private void unfocus() {
        ShopActivity.count--;
        ShopActivity.isfocus = false;
        image_focus.setVisibility(View.VISIBLE);
        tv_focus.setText("关注");
        rela_focus.setBackgroundResource(R.drawable.button_r2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rela_focus:
                if (ShopActivity.isfocus) {
                    unfocus();
                } else {
                    focus();
                }
                get("api/follow/" + shop.getData().getId() + "/shop");
//                tv_count.setText(String.valueOf(count));
        }
    }
}
