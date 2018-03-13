package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.City;
import com.taotaohai.bean.ShopCarNum;
import com.taotaohai.myview.BadgeView;
import com.taotaohai.myview.WordWrapView;
import com.taotaohai.util.SPUtils;
import com.taotaohai.util.util;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class SearchGoods extends BaseActivity {

    private City city;
    private static String list_history;
    @ViewInject(R.id.rela_shopcar)
    private RelativeLayout rela_shopcar;

    @Override
    protected void inithttp() {
        get("api/city");
        get("/api/shopCar/shop_car_num",20);
    }


    @Override
    public void onError(Throwable ex, int postcode) {

    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        city = util.getgson(result, City.class);
        if (city.getSuccess()) {
            initview();
        }
        if(postcode==20){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            if(shopCarNum.getData()!=0){
                BadgeView badgeView = new BadgeView(getApplicationContext(),rela_shopcar);
                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(9);// 设置文本大小
                badgeView.setText(shopCarNum.getData()+""); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }

        }
    }

    @ViewInject(R.id.tv_search)
    private TextView tv_search;
    @ViewInject(R.id.tv_cancle)
    private TextView tv_cancle;
    @ViewInject(R.id.chooseClick)
    private View chooseClick;
    @ViewInject(R.id.edit_search)
    private EditText edit_search;

    @Event(value = {R.id.tv_search, R.id.shopClick, R.id.goodsClick, R.id.chooseClick, R.id.tv_cancle})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                chooseClick.setVisibility(View.VISIBLE);
                break;
            case R.id.goodsClick:
                chooseClick.setVisibility(View.GONE);
                tv_search.setText("商品");
                break;
            case R.id.shopClick:
                chooseClick.setVisibility(View.GONE);
                tv_search.setText("店家");
                break;
            case R.id.chooseClick:
                chooseClick.setVisibility(View.GONE);
                break;
            case R.id.tv_cancle:
                if (tv_cancle.getText().toString().equals("取消")) {
                    finish();
                    return;
                }
                if (edit_search.getText().toString().length() > 0) {
                    list_history = edit_search.getText().toString() + "#~#" + list_history;
                    SPUtils.put(SearchGoods.this, "history", list_history);
                }
                if (tv_search.getText().toString().equals("商品")) {
                    startActivity(new Intent(SearchGoods.this, Seachend.class)
                            .putExtra("name", edit_search.getText().toString())
                    );
                } else {
                    startActivity(new Intent(SearchGoods.this, SeachendShop.class)
                            .putExtra("name", edit_search.getText().toString()));
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_goods);
        x.view().inject(this);//注解绑定
        inithttp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        list_history = (String) SPUtils.get(this, "history", "");
    }

    private void initview() {
        if (list_history != null) {
            String[] listhistory = list_history.split("#~#");
            WordWrapView wordwarp = (WordWrapView) findViewById(R.id.wordwarp);
            for (int i = 0; i < 5 && i < listhistory.length; i++) {
                TextView text = (TextView) getLayoutInflater().inflate(R.layout.textview, null);
                text.setText(listhistory[i]);
                text.setOnClickListener((l) -> {
                    if (tv_search.getText().toString().equals("商品")) {

                        startActivity(new Intent(SearchGoods.this, Seachend.class)
                                .putExtra("name", text.getText().toString())
                        );
                    } else {
                        startActivity(new Intent(SearchGoods.this, SeachendShop.class)
                                .putExtra("name", text.getText().toString()));
                    }
                });
                wordwarp.addView(text);
            }
        }

        WordWrapView wordwarp2 = (WordWrapView) findViewById(R.id.wordwarp2);
        for (City.Data e : city.getData()) {
            TextView text = (TextView) getLayoutInflater().inflate(R.layout.textview, null);
            text.setText(e.getCityName());
            text.setOnClickListener((l) -> {
                startActivity(new Intent(SearchGoods.this, SeachendShop.class)
                        .putExtra("city", e.getId()).putExtra("cityname",e.getCityName())
                );
            });
            wordwarp2.addView(text);
        }

        WordWrapView wordwarp3 = (WordWrapView) findViewById(R.id.wordwarp3);
        for (int i = 0; i < 4; i++) {
            TextView text = (TextView) getLayoutInflater().inflate(R.layout.textview, null);
            if (i == 0) {
                text.setText("100km以内");
            }
            if (i == 1) {
                text.setText("300km以内");
            }
            if (i == 2) {
                text.setText("500km以内");
            }
            if (i == 3) {
                text.setText("不限");
            }
            int finalI = i;
            text.setOnClickListener((l) -> {
                startActivity(new Intent(SearchGoods.this, SeachendShop.class)
                        .putExtra("distance", String.valueOf(finalI)));
            });
            wordwarp3.addView(text);
        }

        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 0) {
                    tv_cancle.setText("取消");
                } else {
                    tv_cancle.setText("确定");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    protected void onRestart() {
        finish();
        startActivity(new Intent(this,SearchGoods.class));
        super.onRestart();
    }
}
