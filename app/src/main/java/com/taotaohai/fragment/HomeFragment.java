package com.taotaohai.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewHeader;
import com.andview.refreshview.XScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.taotaohai.R;
import com.taotaohai.activity.GoodsDetialActivity;
import com.taotaohai.activity.MessageActivity;
import com.taotaohai.activity.SearchGoods;
import com.taotaohai.activity.ShopCarActivity;
import com.taotaohai.activity.ShopMoreActivity;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.HotClass;
import com.taotaohai.bean.HotShop;
import com.taotaohai.bean.Ratation;
import com.taotaohai.myview.HorizontalListView;
import com.taotaohai.myview.MyGridView;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private Banner banner;
    private SwipeRefreshLayout swipe;
    List<View> text;
    private HorizontalListView horizonlist;
    private HotShop hotshop;
    private HotClass hotclass;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        inithttp();
        initview();
        return view;
    }

    @Override
    public void inithttp() {
        super.inithttp();
        get("api/home/rotation", 0);//轮播图
        get("api/goods/hot_class_goods", 1);//热门小分类
        get("api/goods/hot_shop", 2);//热门商店

    }

    @Override
    public void onSuccess(String data, int postcode) {
        super.onSuccess(data, postcode);
        switch (postcode) {
            case 0:
                initrato(data);
                break;
            case 1:
                inithot(data);
                break;
            case 2:
                inithotshop(data);
                break;
        }

    }

    private void inithotshop(String data) {
        hotshop = util.getgson(data, HotShop.class);
        if (hotshop.getSuccess()) {
            horizonlist.setAdapter(new MyListAdapter());
        }


    }

    private void inithot(String data) {
        hotclass = util.getgson(data, HotClass.class);
        if (hotclass.getSuccess()) {
            ((TextView) text.get(0)).setText(hotclass.getData().get(0).getTitle());
            ((TextView) text.get(1)).setText(hotclass.getData().get(0).getClassId());
            GlideUtil.loadImg(hotclass.getData().get(0).getImagesUrl(), ((ImageView) text.get(2)));

            ((TextView) text.get(3)).setText(hotclass.getData().get(1).getTitle());
            ((TextView) text.get(4)).setText(hotclass.getData().get(1).getClassId());
            GlideUtil.loadImg(hotclass.getData().get(1).getImagesUrl(), ((ImageView) text.get(5)));

            ((TextView) text.get(6)).setText(hotclass.getData().get(2).getTitle());
            ((TextView) text.get(7)).setText(hotclass.getData().get(2).getClassId());
            GlideUtil.loadImg(hotclass.getData().get(2).getImagesUrl(), ((ImageView) text.get(8)));

            ((TextView) text.get(9)).setText(hotclass.getData().get(3).getTitle());
            ((TextView) text.get(10)).setText(hotclass.getData().get(3).getClassId());
            GlideUtil.loadImg(hotclass.getData().get(3).getImagesUrl(), ((ImageView) text.get(11)));


        }
    }

    private void initrato(String data) {
        Ratation ratation = util.getgson(data, Ratation.class);
        if (ratation.getSuccess()) {
            banner.setImageLoader(new
                    GlideImageLoader());
            //设置图片集合
            List<String> images = new ArrayList<>();
            for (int i = 0; i < ratation.getData().size(); i++) {
                images.add(ratation.getData().get(i).getImageAbsUrl());
            }
            banner.setImages(images);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }
    }

    @Override
    public void onFinished(int code) {
        super.onFinished(code);
        swipe.setRefreshing(false);
    }

    private void initview() {
        text = Arrays.asList(
                view.findViewById(R.id.tv_11),
                view.findViewById(R.id.tv_12),
                view.findViewById(R.id.tv_13),
                view.findViewById(R.id.tv_21),
                view.findViewById(R.id.tv_22),
                view.findViewById(R.id.tv_23),
                view.findViewById(R.id.tv_31),
                view.findViewById(R.id.tv_32),
                view.findViewById(R.id.tv_33),
                view.findViewById(R.id.tv_41),
                view.findViewById(R.id.tv_42),
                view.findViewById(R.id.tv_43)
        );
        view.findViewById(R.id.rela_more).setOnClickListener(this);

        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                inithttp();
            }
        });
        view.findViewById(R.id.tv_search).setOnClickListener(this);
        view.findViewById(R.id.rela_shopcar).setOnClickListener(this);
        view.findViewById(R.id.rela_message).setOnClickListener(this);
        view.findViewById(R.id.rela_1).setOnClickListener(this);
        banner = (Banner) view.findViewById(R.id.banner);
        //设置图片加载器

        horizonlist = (HorizontalListView) view.findViewById(R.id.horizonlist);
        MyGridView mygridview = (MyGridView) view.findViewById(R.id.mygridview);

        mygridview.setAdapter(new MyGridviewAdapter());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                startActivityForResult(new Intent(getActivity(), SearchGoods.class), 1);
                break;
            case R.id.rela_shopcar:
                startActivityForResult(new Intent(getActivity(), ShopCarActivity.class), 1);
                break;
            case R.id.rela_message:
                startActivityForResult(new Intent(getActivity(), MessageActivity.class), 1);
                break;
            case R.id.rela_1:
                startActivityForResult(new Intent(getActivity(), GoodsDetialActivity.class)
                        .putExtra("id",hotclass.getData().get(0).getId())
                        , 1);
                break;
            case R.id.rela_more:
                startActivityForResult(new Intent(getActivity(), ShopMoreActivity.class), 1);
                break;
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


    private class MyGridviewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
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
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_hor_gride, null);
            return view;
        }
    }

    private class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return hotshop.getData().size();
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
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_hor, null);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            TextView tv_scor = (TextView) view.findViewById(R.id.tv_scor);
            TextView tv_juli = (TextView) view.findViewById(R.id.tv_juli);
            GlideUtil.loadImg(hotshop.getData().get(position).getLogoId(), image);
            tv_title.setText(hotshop.getData().get(position).getName());

            return view;
        }
    }

}
