package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
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
        LinearLayout lin_1 = (LinearLayout) findViewById(R.id.lin_1);
        LinearLayout lin_10 = (LinearLayout) findViewById(R.id.lin_10);
        tv_count = (TextView) findViewById(R.id.tv_count);
        rela_focus = findViewById(R.id.rela_focus);
        image_focus = findViewById(R.id.image_focus);
        rela_focus.setOnClickListener(this);
        tv_focus = (TextView) findViewById(R.id.tv_focus);
        ImageView image_photo = (ImageView) findViewById(R.id.image_photo);
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_scor = (TextView) findViewById(R.id.tv_scor);
        TextView tv_title20 = (TextView) findViewById(R.id.tv_title20);
        tv_name.setText(shop.getData().getName());
        GlideUtil.loadImg(shop.getData().getLogoIdAbsUrl(), image_photo);
        tv_scor.setText(shop.getData().getTotalCommonLevel() + "分");
        tv_title20.setText(shop.getData().getName());



        for (int i=0; i<shop.getData().getBusinessAbsUrlList().size(); i++){
            int zz = i;

            PhotoActivity.bitmap.add(shop.getData().getBusinessAbsUrlList().get(i));
            findViewById(R.id.licence).setOnClickListener((l) -> {

                startActivity(new Intent(this, PhotoActivity.class).putExtra("ID", zz));

            });
        }

        for (int i = 0; i < 3 && i < shop.getData().getShopIdentifies().size(); i++) {
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.shop_textview, null);
            textView.setText(shop.getData().getShopIdentifies().get(i).getName());
            lin_1.addView(textView);
        }


        for (int i = 0; i < shop.getData().getShopIdentifies().size(); i++) {
            View view = getLayoutInflater().inflate(R.layout.shopinfo_rela, null);
            TextView textView = (TextView) view.findViewById(R.id.text_name);
            textView.setText(shop.getData().getShopIdentifies().get(i).getName());
            int finalI = i;

            PhotoActivity.bitmap.add(shop.getData().getShopIdentifies().get(i).getImageAbsUrl());
            view.setOnClickListener((l) -> {
                startActivity(new Intent(this, PhotoActivity.class).putExtra("ID", finalI+shop.getData().getBusinessAbsUrlList().size())
                );
            });
            lin_10.addView(view);
        }
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
                    showToast("取消成功");
                } else {
                    focus();
                    showToast("关注成功");
                }
                get("api/follow/" + shop.getData().getId() + "/shop");
//                tv_count.setText(String.valueOf(count));
        }
    }

    public void onChat(View v) {
        startActivity(new Intent(ShopIntroducActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, shop.getData().getUser().getU_id()));
    }
}
