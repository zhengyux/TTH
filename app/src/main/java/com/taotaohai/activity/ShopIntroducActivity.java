package com.taotaohai.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.Shop;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.tencent.TIMConversationType;

import static com.taotaohai.GlobalParams.NONOTICELOGIN;

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


//
//        for (int i=0; i<shop.getData().getBusinessAbsUrlList().size(); i++){
//            int zz = i;
//
//            PhotoActivity.bitmap.add(shop.getData().getBusinessAbsUrlList().get(zz));

            findViewById(R.id.licence).setOnClickListener((l) -> {

                for(int z=0; z<shop.getData().getBusinessAbsUrlList().size(); z++){
                    PhotoActivity.bitmap.add(shop.getData().getBusinessAbsUrlList().get(z));

                }
                startActivity(new Intent(this, PhotoActivity.class).putExtra("ID", 0));

            });
  //      }

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


            view.setOnClickListener((l) -> {
                PhotoActivity.bitmap.add(shop.getData().getShopIdentifies().get(finalI).getImageAbsUrl());
                startActivity(new Intent(this, PhotoActivity.class).putExtra("ID", finalI)
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
        showToast("关注成功");
    }

    private void unfocus() {
        ShopActivity.count--;
        ShopActivity.isfocus = false;
        image_focus.setVisibility(View.VISIBLE);
        tv_focus.setText("关注");
        rela_focus.setBackgroundResource(R.drawable.button_r2);
        showToast("取消成功");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rela_focus:

                get("api/user/",995);


//                tv_count.setText(String.valueOf(count));
        }
    }

    public void onChat(View v) {
        get("api/user/",994);

    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);

        if(postcode==994){
            ChatActivity.navToChat(this,shop.getData().getService().getUsername(), TIMConversationType.C2C);
         //   startActivity(new Intent(ShopIntroducActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, shop.getData().getUser().getU_id()));
        }

        if(postcode==995){
            get("api/follow/" + shop.getData().getId() + "/shop",999);
        }

        if(postcode==999){
            if (ShopActivity.isfocus) {
                showToast("取消成功");
                unfocus();
            } else {
                showToast("关注成功");
                focus();
            }
            tv_count.setText(String.valueOf(ShopActivity.count));
        }
    }

    @Override
    public void onError(Throwable ex, int postcode) {
        if(postcode==995||postcode==994){
            String[] st = ex.toString().split("result:");
            if (st.length > 1) {
                util.isSuccess(util.getgson(st[1], BaseBean.class));
            }
            try {
                if (ex.toString().contains("401") & postcode != NONOTICELOGIN) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("未登录");
                    dialog.setMessage("是否进入登录页登录?");
                    dialog.setNegativeButton("前往", (dialog1, which) -> {
                        startActivity(new Intent(this, Login.class));
                        finish();
                    });
                    dialog.setNeutralButton("取消", (dialog1, which) -> {
                    });
                    dialog.show();
                }
            } catch (Exception e) {
            }
        }
    }
}
