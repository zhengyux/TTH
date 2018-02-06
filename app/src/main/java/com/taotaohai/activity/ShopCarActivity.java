package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Car;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;

import org.xutils.http.HttpMethod;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShopCarActivity extends BaseActivity {

    private Mydapter adapter;
    boolean isall = false;
    private RadioButton radioall;
    private ListView listView;
    private Car car;
    private Car car_buy;
    private TextView tv_all, tv_settlment;
    private RelativeLayout rela_message;

    @Override
    protected void inithttp() {
        get("api/shopCar", 0);
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        switch (postcode){
            case 0:
                car = util.getgson(result, Car.class);
                car_buy = util.getgson(result, Car.class);
                car_buy.getData().getData().clear();
                if (car.getSuccess()) {
                    for (int i = 0; i < car.getData().getData().size(); i++) {
                        isclicks.add(false);
                    }
                    listView.setAdapter(adapter);
                }

                break;
        }


    }

    public void finish(View v){
        finish();
    }
    public void mesg(View v){
        startActivity(new Intent(this, MessageActivity.class));
    }

    List<Boolean> isclicks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcar);

        inithttp();
        initview();
        get("/api/message/notReadList/{type}",50);

    }

    private void initview() {
        rela_message = (RelativeLayout) findViewById(R.id.rela_message);
        TextView tv_settlement = (TextView) findViewById(R.id.tv_settlement);
        tv_settlement.setOnClickListener(v -> {
            if (car_buy.getData().getData().size() == 0) {
                showToast("请选择您要结算的商品");
                return;
            }

            startActivity(new Intent(ShopCarActivity.this, OrderSureActivity.class)
                    .putExtra("car", car_buy)
                    .putExtra("price", tv_all.getText().toString())
                    .putExtra("num", String.valueOf(num))
            );
        });
        listView = (ListView) findViewById(R.id.list);
        adapter = new Mydapter();

        radioall = (RadioButton) findViewById(R.id.radioall);
        tv_all = (TextView) findViewById(R.id.tv_all);

        radioall.setOnClickListener(v -> {
            if (isall) {
                radioall.setChecked(false);
                isclicks.clear();
                for (int i = 0; i < car.getData().getData().size(); i++) {
                    isclicks.add(false);
                }
            } else {
                radioall.setChecked(true);
                isclicks.clear();
                for (int i = 0; i < car.getData().getData().size(); i++) {
                    isclicks.add(true);
                }
            }
            getall();
            adapter.notifyDataSetChanged();
            isall = !isall;
        });

    }

    class Mydapter extends BaseAdapter {
        @Override
        public int getCount() {
            return car.getData().getData().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.item_shopcar, null);
            final RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.radio);
            final TextView tv_edit = (TextView) convertView.findViewById(R.id.tv_edit);
            final View rela_edit = convertView.findViewById(R.id.rela_edit);
            TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
            relativeLayout.setOnClickListener(l->{
                startActivity(new Intent(ShopCarActivity.this, GoodsDetialActivity.class)
                        .putExtra("id", car.getData().getData().get(position).getGoodsId()));
            });
            TextView tv_guige = (TextView) convertView.findViewById(R.id.tv_guige);
            TextView text_title = (TextView) convertView.findViewById(R.id.text_title);
            TextView text_content = (TextView) convertView.findViewById(R.id.text_content);
            TextView tv_util = (TextView) convertView.findViewById(R.id.tv_util);
            final TextView tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            final TextView tv_count1 = (TextView) convertView.findViewById(R.id.tv_count1);
            final ImageView image_photo = (ImageView) convertView.findViewById(R.id.image_photo);
            tv_price.setText(car.getData().getData().get(position).getPrice());
            text_content.setText(car.getData().getData().get(position).getGoodsName());
            tv_guige.setText(car.getData().getData().get(position).getRemark());
            tv_util.setText("/" + car.getData().getData().get(position).getUnit());
            text_title.setText(car.getData().getData().get(position).getShopName());
            text_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ShopCarActivity.this, ShopActivity.class)
                            .putExtra("id", car.getData().getData().get(position).getShopId()));
                }
            });
            GlideUtil.loadImg(car.getData().getData().get(position).getImgId(), image_photo);
            int count = car.getData().getData().get(position).getCount();
            if (count == 0) {
                car.getData().getData().get(position).setCount(1);
                count = car.getData().getData().get(position).getCount();
            }
            tv_count.setText(String.valueOf("x" + count));
            tv_count1.setText(String.valueOf(count));

            tv_edit.setOnClickListener(v -> {
                if (rela_edit.isShown()) {
                    rela_edit.setVisibility(View.GONE);
                    tv_edit.setText("编辑");
                } else {
                    rela_edit.setVisibility(View.VISIBLE);
                    tv_edit.setText("完成");
                }
            });
            convertView.findViewById(R.id.tv_delect).setOnClickListener(v -> {
                Http(HttpMethod.DELETE, "api/shopCar/" + car.getData().getData().get(position).getId(), 11);
                car.getData().getData().remove(position);
                isclicks.remove(position);
                all();
                notifyDataSetChanged();
            });
            convertView.findViewById(R.id.tv_reduct).setOnClickListener(v -> {

                if(car.getData().getData().get(position).getGoodsInfo().getUnitMin()>car.getData().getData().get(position).getCount()){
                    showToast("购买数不能小于最少起批数");
                }else {

                    if (car.getData().getData().get(position).getCount() > 1) {
                        car.getData().getData().get(position).setCount(car.getData().getData().get(position).getCount() - 1);
                        tv_count.setText(String.valueOf("x" + car.getData().getData().get(position).getCount()));
                        tv_count1.setText(String.valueOf(car.getData().getData().get(position).getCount()));
                        getall();
                        has.clear();
                        has.put("shopCarId", car.getData().getData().get(position).getId());
                        has.put("count", String.valueOf(car.getData().getData().get(position).getCount()));
                        put("api/shopCar", has, 10);
                    }
                }


            });
            convertView.findViewById(R.id.tv_add).setOnClickListener(v -> {

                if(car.getData().getData().get(position).getGoodsInfo().getStock()<=car.getData().getData().get(position).getCount()){
                    showToast("购买量大于库存");
                }else {

                        car.getData().getData().get(position).setCount(car.getData().getData().get(position).getCount() + 1);
                        tv_count.setText(String.valueOf("x" + car.getData().getData().get(position).getCount()));
                        tv_count1.setText(String.valueOf(car.getData().getData().get(position).getCount()));
                        getall();
                        has.clear();
                        has.put("shopCarId", car.getData().getData().get(position).getId());
                        has.put("count", String.valueOf(car.getData().getData().get(position).getCount()));
                        put("api/shopCar", has, 10);

                }


            });

            radioButton.setChecked(isclicks.get(position));
            radioButton.setOnClickListener(v -> {
                if (isclicks.get(position)) {
                    radioButton.setChecked(false);
                } else {
                    radioButton.setChecked(true);
                }
                isclicks.set(position, !isclicks.get(position));
                all();
            });

            return convertView;
        }
    }

    int num = 0;//一共多少件

    private void getall() {
        double count = 0;
        num = 0;
        car_buy.getData().getData().clear();
        for (int i = 0; i < isclicks.size(); i++) {
            if (isclicks.get(i)) {
                count = count + car.getData().getData().get(i).getCount() * Double.valueOf(car.getData().getData().get(i).getPrice());
                num = num + car.getData().getData().get(i).getCount();
                car_buy.getData().getData().add(car.getData().getData().get(i));
            }
        }


        tv_all.setText(String.valueOf(new DecimalFormat("0.00").format(count)));

    }

    private void all() {
        getall();
        for (int i = 0; i < isclicks.size(); i++) {
            if (!isclicks.get(i)) {
                isall = false;
                radioall.setChecked(false);
                return;
            }
        }
        radioall.setChecked(true);
        isall = true;
    }

}
