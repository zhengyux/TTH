package com.taotaohai.fragment;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import com.baidu.location.LocationClientOption;
import com.taotaohai.GlobalParams;
import com.taotaohai.R;
import com.taotaohai.activity.GoodsDetialActivity;
import com.taotaohai.activity.Home;
import com.taotaohai.activity.MessageActivity;
import com.taotaohai.activity.SearchGoods;
import com.taotaohai.activity.ShopCarActivity;
import com.taotaohai.activity.ShopMoreActivity;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.HotClass;
import com.taotaohai.bean.HotShop;
import com.taotaohai.bean.HotShopmore;
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
    private MyListAdapter myListAdapter;
    private HotShopmore hotshop2;
    private MyGridView mygridview;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public LocationClient mLocationClient = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        InitLocation();
        mLocationClient.start();
        //注册监听函数
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
        get("api/goods/hot_goods", 3);//热门商店

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
            case 3:
                inithotshopmore(data);
                break;
        }

    }

    private void inithotshopmore(String data) {
        hotshop2 = util.getgson(data, HotShopmore.class);
        if (hotshop2.getSuccess()) {
            mygridview.setAdapter(new MyGridviewAdapter());
        }

    }

    private void inithotshop(String data) {
        hotshop = util.getgson(data, HotShop.class);
        if (hotshop.getSuccess()) {
            myListAdapter = new MyListAdapter();
            horizonlist.setAdapter(myListAdapter);
        }

    }

    private void inithot(String data) {
        hotclass = util.getgson(data, HotClass.class);
        if (hotclass.getSuccess()) {
            ((TextView) text.get(0)).setText(hotclass.getData().get(0).getTitle());
            ((TextView) text.get(1)).setText(hotclass.getData().get(0).getClassInfo().getClassName());
            if (hotclass.getData().get(0).getImagesUrl().size() > 0)
                GlideUtil.loadImg(hotclass.getData().get(0).getImagesUrl().get(0), ((ImageView) text.get(2)));

            ((TextView) text.get(3)).setText(hotclass.getData().get(1).getTitle());
            ((TextView) text.get(4)).setText(hotclass.getData().get(1).getClassInfo().getClassName());
            if (hotclass.getData().get(1).getImagesUrl().size() > 0)
                GlideUtil.loadImg(hotclass.getData().get(1).getImagesUrl().get(0), ((ImageView) text.get(5)));

            ((TextView) text.get(6)).setText(hotclass.getData().get(2).getTitle());
            ((TextView) text.get(7)).setText(hotclass.getData().get(2).getClassInfo().getClassName());
            if (hotclass.getData().get(2).getImagesUrl().size() > 0)
                GlideUtil.loadImg(hotclass.getData().get(2).getImagesUrl().get(0), ((ImageView) text.get(8)));

            ((TextView) text.get(9)).setText(hotclass.getData().get(3).getTitle());
            ((TextView) text.get(10)).setText(hotclass.getData().get(3).getClassInfo().getClassName());
            if (hotclass.getData().get(3).getImagesUrl().size() > 0)
                GlideUtil.loadImg(hotclass.getData().get(3).getImagesUrl().get(0), ((ImageView) text.get(11)));


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
        view.findViewById(R.id.rela_2).setOnClickListener(this);
        view.findViewById(R.id.rela_3).setOnClickListener(this);
        view.findViewById(R.id.rela_4).setOnClickListener(this);
        banner = (Banner) view.findViewById(R.id.banner);
        //设置图片加载器

        horizonlist = (HorizontalListView) view.findViewById(R.id.horizonlist);
        mygridview = (MyGridView) view.findViewById(R.id.mygridview);


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
                startActivityForResult(new Intent(getActivity(), Home.class)
                        , 1);
                break;
            case R.id.rela_2:
                ((Home) getActivity()).tabView.setTabViewDefaultPosition(1);
                break;
            case R.id.rela_3:
                ((Home) getActivity()).tabView.setTabViewDefaultPosition(1);
                break;
            case R.id.rela_4:
                startActivityForResult(new Intent(getActivity(), GoodsDetialActivity.class)
                                .putExtra("id", hotclass.getData().get(0).getId())
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
            return hotshop2.getData().size();
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
            View su = view.findViewById(R.id.su);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_photo);
            TextView tv_tite = (TextView) view.findViewById(R.id.tv_tite);
            TextView tv_remorke = (TextView) view.findViewById(R.id.tv_remorke);
            TextView tv_money = (TextView) view.findViewById(R.id.tv_money);
            TextView tv_unit = (TextView) view.findViewById(R.id.tv_unit);
            TextView tv_people = (TextView) view.findViewById(R.id.tv_people);


            HotShopmore.Data data = hotshop2.getData().get(position);
            if (data.getSourceVideo().length() > 0) {
                su.setVisibility(View.VISIBLE);
            } else {
                su.setVisibility(View.GONE);
            }
            if (data.getImagesUrl().size() > 0) {
                GlideUtil.loadImg(data.getImagesUrl().get(0), imageView);
            }
            tv_tite.setText(data.getShopInfo().getName());
            tv_remorke.setText(data.getShopInfo().getRemark());
            tv_money.setText("￥：" + data.getPrice());
            tv_unit.setText("/" + data.getUnit());
            tv_people.setText("已有" + data.getShopInfo().getTotalBuy() + "人购买");

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
            if (hotshop.getData().get(position).getBusinessAbsUrlList().size() > 0)
                GlideUtil.loadImg(hotshop.getData().get(position).getBusinessAbsUrlList().get(0), image);
            tv_title.setText(hotshop.getData().get(position).getName());
            tv_scor.setText(hotshop.getData().get(position).getTotalCommonLevel() + "分");
            tv_juli.setText(util.getdouboletwo(util.getDistance(GlobalParams.latitude, GlobalParams.longitude, Double.valueOf(hotshop.getData().get(position).getLatitude()), Double.valueOf(hotshop.getData().get(position).getLongitude())) / 1000) + "km");

            return view;
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            GlobalParams.latitude = location.getLatitude();
            GlobalParams.longitude = location.getLongitude();
            if (hotshop != null) {
                myListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        //option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
        int span = 0;
        option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }
}
