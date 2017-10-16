package com.taotaohai.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Goods;
import com.taotaohai.fragment.HomeFragment;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoodsDetialActivity extends BaseActivity implements View.OnClickListener {
    int stata = 1;
    TextView tv_defult;
    View view_defult;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_num;
    private View lin_1;
    private View lin_2;
    private View lin_3;
    private MyAdapter adapter;
    private ImageView image_like;
    private View rela_buy;
    private TextView tv_gotoshop;
    private Banner banner;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detial);
        initview();
        get("api/goods/0ub70x");
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        Goods goods = util.getgson(result, Goods.class);
        if (goods.getSuccess()) {
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            List<String> images = new ArrayList<>();
            for (int i = 0; i < goods.getData().getImagesUrl().size(); i++) {
                images.add(goods.getData().getImagesUrl().get(i));
            }
            banner.setImages(images);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }


    }

    private void initview() {
        image_like = (ImageView) findViewById(R.id.image_like);
        rela_buy = findViewById(R.id.rela_buy);
        tv_num = (TextView) findViewById(R.id.tv_num);
        ListView listview = (ListView) findViewById(R.id.listview);
        View headview = getLayoutInflater().inflate(R.layout.detial_head, null);
        tv_gotoshop = (TextView) headview.findViewById(R.id.tv_gotoshop);
        tv_gotoshop.setOnClickListener(this);
        banner = (Banner) headview.findViewById(R.id.banner);

        tv_1 = (TextView) headview.findViewById(R.id.tv_1);
        tv_2 = (TextView) headview.findViewById(R.id.tv_2);
        tv_3 = (TextView) headview.findViewById(R.id.tv_3);

        lin_1 = headview.findViewById(R.id.lin_1);
        lin_2 = headview.findViewById(R.id.lin_2);
        lin_3 = headview.findViewById(R.id.lin_3);
        tv_defult = tv_1;
        view_defult = lin_1;
        headview.findViewById(R.id.rela_click_1).setOnClickListener(this);
        headview.findViewById(R.id.rela_click_2).setOnClickListener(this);
        headview.findViewById(R.id.rela_click_3).setOnClickListener(this);
        listview.addHeaderView(headview);
        adapter = new MyAdapter();
        listview.setAdapter(adapter);
    }

    boolean isLike = false;

    public void onLike(View view) {
        if (isLike) {
            image_like.setImageResource(R.mipmap.xinno);
        } else {
            image_like.setImageResource(R.mipmap.xinyes);
        }
        isLike = !isLike;
    }

    public void onDismis(View view) {
        rela_buy.setVisibility(View.GONE);
    }

    public void onBuy(View view) {
        rela_buy.setVisibility(View.VISIBLE);
    }

    public void onDefult(View view) {
        //zzmkbls
    }

    int count = 1;

    public void onAdd(View view) {
        count++;
        tv_num.setText(String.valueOf(count));
    }

    public void onReduc(View view) {
        if (count > 1) {
            count--;
        }
        tv_num.setText(String.valueOf(count));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rela_click_1:
                stata = 1;
                setdefult();
                break;
            case R.id.rela_click_2:
                stata = 2;
                setdefult();
                break;
            case R.id.rela_click_3:
                stata = 3;
                setdefult();
                break;
            case R.id.tv_gotoshop:
                startActivity(new Intent(this, ShopActivity.class));
                break;

        }

    }

    void setdefult() {
        switch (stata) {
            case 1:
                tv_defult.setTextColor(getResources().getColor(R.color.text_black));
                view_defult.setVisibility(View.GONE);
                tv_defult = tv_1;
                view_defult = lin_1;
                tv_defult.setTextColor(getResources().getColor(R.color.them));
                view_defult.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_defult.setTextColor(getResources().getColor(R.color.text_black));
                view_defult.setVisibility(View.GONE);
                tv_defult = tv_2;
                view_defult = lin_2;
                tv_defult.setTextColor(getResources().getColor(R.color.them));
                view_defult.setVisibility(View.VISIBLE);
                break;
            case 3:
                tv_defult.setTextColor(getResources().getColor(R.color.text_black));
                view_defult.setVisibility(View.GONE);
                tv_defult = tv_3;
                view_defult = lin_3;
                tv_defult.setTextColor(getResources().getColor(R.color.them));
                view_defult.setVisibility(View.VISIBLE);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (stata == 3) {
                return 10;
            }
            return 1;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (stata == 1) {
                view = getLayoutInflater().inflate(R.layout.item_cpxq, null);
            }
            if (stata == 2) {
                view = getLayoutInflater().inflate(R.layout.item_list2, null);
            }
            if (stata == 3) {
                view = getLayoutInflater().inflate(R.layout.item_devlop, null);
            }

            return view;
        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
//            Glide.with(context).load(path).into(imageView);
            GlideUtil.loadImg((String) path, imageView);
            //用fresco加载图片简单用法，记得要写下面的createImageView方法
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);
        }
    }
}
