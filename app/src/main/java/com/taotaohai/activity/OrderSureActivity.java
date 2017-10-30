package com.taotaohai.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.Car;
import com.taotaohai.bean.Defult;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;

import org.xutils.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

public class OrderSureActivity extends BaseActivity {

    private Car car;
    View img_add, rela_address;
    TextView tv_name, tv_phone, tv_address;
    private Defult defult;
    List<String> list = null;

    @Override
    protected void inithttp() {

    }

    private void getaddress() {
        get("api/user/shipping_def/");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sure);
        setTitle("提交订单");
        inithttp();
        initview();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getaddress();
    }

    private void initview() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_address = (TextView) findViewById(R.id.tv_address);
        img_add = findViewById(R.id.img_add);
        rela_address = findViewById(R.id.rela_address);

        car = (Car) getIntent().getSerializableExtra("car");
        list = (List<String>) getIntent().getSerializableExtra("list");
        if (car != null) {
            TextView tv_price = (TextView) findViewById(R.id.tv_price);
            TextView tv_price2 = (TextView) findViewById(R.id.tv_price2);
            TextView tv_count = (TextView) findViewById(R.id.tv_count);
            tv_price.setText(getintent("price"));
            tv_price2.setText(getintent("price"));
            tv_count.setText("共" + getintent("num") + "件商品 ：小计");

            LinearLayout lin_goods = (LinearLayout) findViewById(R.id.lin_goods);
            for (int i = 0; i < car.getData().getData().size(); i++) {
                View view = getLayoutInflater().inflate(R.layout.item_sure, null);
                TextView text_content = (TextView) view.findViewById(R.id.text_content);
                TextView tv_guige = (TextView) view.findViewById(R.id.tv_guige);
                TextView tv_sigalmoney = (TextView) view.findViewById(R.id.tv_sigalmoney);
                TextView tv_28 = (TextView) view.findViewById(R.id.tv_28);
                TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
                ImageView image_photo = (ImageView) view.findViewById(R.id.image_photo);

                GlideUtil.loadImg(car.getData().getData().get(i).getImgId(), image_photo);
                text_content.setText(car.getData().getData().get(i).getShopName());
                tv_guige.setText(car.getData().getData().get(i).getRemark());
                tv_28.setText("/" + car.getData().getData().get(i).getUnit());
                tv_sigalmoney.setText("￥" + car.getData().getData().get(i).getPrice());
                tv_num.setText("x" + car.getData().getData().get(i).getCount());
                lin_goods.addView(view);
            }
        } else {
            TextView tv_price = (TextView) findViewById(R.id.tv_price);
            TextView tv_price2 = (TextView) findViewById(R.id.tv_price2);
            TextView tv_count = (TextView) findViewById(R.id.tv_count);
            tv_price.setText(String.valueOf(Double.valueOf(list.get(3)) * Double.valueOf(list.get(5))));
            tv_price2.setText(String.valueOf(Double.valueOf(list.get(3)) * Double.valueOf(list.get(5))));
            tv_count.setText("共" + list.get(5) + "件商品 ：小计");

            LinearLayout lin_goods = (LinearLayout) findViewById(R.id.lin_goods);
            View view = getLayoutInflater().inflate(R.layout.item_sure, null);
            TextView text_content = (TextView) view.findViewById(R.id.text_content);
            TextView tv_guige = (TextView) view.findViewById(R.id.tv_guige);
            TextView tv_sigalmoney = (TextView) view.findViewById(R.id.tv_sigalmoney);
            TextView tv_28 = (TextView) view.findViewById(R.id.tv_28);
            TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
            ImageView image_photo = (ImageView) view.findViewById(R.id.image_photo);

            GlideUtil.loadImg(list.get(0), image_photo);
            text_content.setText(list.get(1));
            tv_guige.setText(list.get(2));
            tv_28.setText("/" + list.get(4));
            tv_sigalmoney.setText("￥" + list.get(3));
            tv_num.setText("x" + list.get(5));
            lin_goods.addView(view);
        }
        findViewById(R.id.tv_settlement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (car == null) {

                    JsonObject json = new JsonObject();
                    JsonArray jsonaray = new JsonArray();
                    JsonArray jsonaray2 = new JsonArray();
                    jsonaray.add(Integer.valueOf(list.get(5)));
                    jsonaray2.add(list.get(6));
                    json.add("goodsIds", jsonaray2);
                    json.add("counts", jsonaray);
                    if (defult == null) {
                        showToast("请添加收货地址");
                        return;
                    }
                    json.addProperty("addressId", defult.getData().getId());

                    Http(HttpMethod.POST, "api/goodsorder/buy_now/", json.toString(), 15);
                } else {
                    JsonObject json = new JsonObject();
                    JsonArray jsonaray = new JsonArray();
                    JsonArray jsonaray2 = new JsonArray();
                    for (int i = 0; i < car.getData().getData().size(); i++) {
                        jsonaray.add(car.getData().getData().get(i).getCount());
                        jsonaray2.add(String.valueOf(car.getData().getData().get(i).getGoodsId()));
                    }
                    json.add("goodsIds", jsonaray2);
                    json.add("counts", jsonaray);
                    if (defult == null) {
                        showToast("请添加收货地址");
                        return;
                    }
                    json.addProperty("addressId", defult.getData().getId());

                    Http(HttpMethod.POST, "api/goodsorder/buy_now/", json.toString(), 15);
                }
//                startActivity(new Intent(OrderSureActivity.this, MyBook.class));
            }
        });
    }

    public void onaddress(View view) {
        startActivity(new Intent(this, AddressManeger.class));
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        if (postcode == 0) {
            defult = util.getgson(result, Defult.class);
            if (defult.getSuccess()) {
                if (defult.getData() != null) {
                    tv_name.setText("收货人: " + defult.getData().getLinkName());
                    tv_phone.setText("电话: " + defult.getData().getLinkTel());
                    tv_address.setText(defult.getData().getLinkProvince() + defult.getData().getLinkCity() + defult.getData().getLinkArea() + defult.getData().getLinkAddress());
                    rela_address.setVisibility(View.VISIBLE);
                } else {
                    img_add.setVisibility(View.VISIBLE);
                }
            }
        }
        if (postcode == 15) {
            BaseBean baseBean = util.getgson(result);
            if (util.isSuccess(baseBean, this)) {
                showToast("提交成功");
                startActivity(new Intent(this, MyBook.class));
                finish();
            }
        }


    }
}
