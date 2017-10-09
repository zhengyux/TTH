package com.taotaohai.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewHeader;
import com.andview.refreshview.XScrollView;
import com.bumptech.glide.Glide;
import com.taotaohai.R;
import com.taotaohai.activity.GoodsDetialActivity;
import com.taotaohai.activity.MessageActivity;
import com.taotaohai.activity.SearchGoods;
import com.taotaohai.activity.ShopCarActivity;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.myview.HorizontalListView;
import com.taotaohai.myview.MyGridView;
import com.taotaohai.util.GlideUtil;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private View view;

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
        initview();
        return view;
    }

    private void initview() {
        view.findViewById(R.id.tv_search).setOnClickListener(this);
        view.findViewById(R.id.rela_shopcar).setOnClickListener(this);
        view.findViewById(R.id.rela_message).setOnClickListener(this);
        view.findViewById(R.id.rela_1).setOnClickListener(this);
        Banner banner = (Banner) view.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new

                GlideImageLoader());
        //设置图片集合
        List<String> images = Arrays.asList("http://pic1.bbzhi.com/dongwubizhi/maomigougoudequguaishentai/animal_funny_cats_dogs_31452_9.jpg",
                "http://pic1.bbzhi.com/dongwubizhi/xiaogougoudejiaoyou/animal_mx069_pretty_puppies_puppy_garden_adventure_15202_11.jpg",
                "http://pic5.bbzhi.com/dongwubizhi/maorongrongxiaogougoubizhi/maorongrongxiaogougoubizhi_413538_11.jpg",
                "http://img.61gequ.com/allimg/2011-4/201142013451843547.jpg"
        );
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        HorizontalListView horizonlist = (HorizontalListView) view.findViewById(R.id.horizonlist);
        MyGridView mygridview = (MyGridView) view.findViewById(R.id.mygridview);
        horizonlist.setAdapter(new MyListAdapter());
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
                startActivityForResult(new Intent(getActivity(), GoodsDetialActivity.class), 1);
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
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_hor, null);
            return view;
        }
    }

}
