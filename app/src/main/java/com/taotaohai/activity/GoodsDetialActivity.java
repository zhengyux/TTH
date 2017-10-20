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

import com.bumptech.glide.Glide;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Goods;
import com.taotaohai.fragment.HomeFragment;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoodsDetialActivity extends BaseActivity implements View.OnClickListener {
    int stata = 1;
    TextView tv_defult, tv_title, tv_num, tv_count, tv_util, tv_dis, tv_dis2, tv_name, tv_scor, tv_goods, tv_source, tv_people;
    View view_defult;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private View lin_1;
    private View lin_2;
    private View lin_3;
    private MyAdapter adapter;
    private ImageView image_like, image_photo;
    private View rela_buy;
    private TextView tv_gotoshop;
    private Banner banner;
    private Goods goods;
    private View rela_click_2;
    private ListView listview;

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
        goods = util.getgson(result, Goods.class);
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
            tv_title.setText(goods.getData().getTitle());
            tv_count.setText("￥" + goods.getData().getPrice());
            tv_util.setText("/" + goods.getData().getUnit());
            tv_dis.setText(goods.getData().getRemark());
            tv_dis2.setText(goods.getData().getUnitMin() + goods.getData().getUnit() + "起批");
            tv_name.setText(goods.getData().getShopInfo().getName());
            tv_num.setText("已有" + goods.getData().getShopInfo().getTotalBuy() + "人" + "购买");
            tv_scor.setText(goods.getData().getTotalCommonLevel() + "分");
            tv_goods.setText(goods.getData().getShopInfo().getTotalGoods());
            tv_source.setText(goods.getData().getShopInfo().getTotalSourceGoods());
            tv_people.setText(goods.getData().getShopInfo().getTotalLike());

            GlideUtil.loadImg(goods.getData().getShopInfo().getLogoIdAbsUrl(), image_photo);
            if (goods.getData().getSourceVideo().length() < 5) {
                rela_click_2.setVisibility(View.GONE);
            }
            adapter = new MyAdapter();
            listview.setAdapter(adapter);
        }


    }

    private void initview() {
        listview = (ListView) findViewById(R.id.listview);
        View headview = getLayoutInflater().inflate(R.layout.detial_head, null);

        image_like = (ImageView) headview.findViewById(R.id.image_like);
        rela_buy = headview.findViewById(R.id.rela_buy);
        tv_num = (TextView) headview.findViewById(R.id.tv_num);
        tv_title = (TextView) headview.findViewById(R.id.tv_title);
        tv_dis = (TextView) headview.findViewById(R.id.tv_dis);
        tv_dis2 = (TextView) headview.findViewById(R.id.tv_dis2);
        tv_name = (TextView) headview.findViewById(R.id.tv_name);
        tv_scor = (TextView) headview.findViewById(R.id.tv_scor);
        tv_goods = (TextView) headview.findViewById(R.id.tv_goods);
        tv_source = (TextView) headview.findViewById(R.id.tv_source);
        tv_people = (TextView) headview.findViewById(R.id.tv_people);
        tv_util = (TextView) headview.findViewById(R.id.tv_util);
        tv_count = (TextView) headview.findViewById(R.id.tv_count);
        image_photo = (ImageView) headview.findViewById(R.id.image_photo);
        headview.findViewById(R.id.tv_jiliang).setOnClickListener(this);


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
        rela_click_2 = headview.findViewById(R.id.rela_click_2);
        rela_click_2.setOnClickListener(this);
        headview.findViewById(R.id.rela_click_3).setOnClickListener(this);
        headview.findViewById(R.id.left_images).setOnClickListener(this);
        headview.findViewById(R.id.shopcar).setOnClickListener(this);
        headview.findViewById(R.id.message).setOnClickListener(this);

        listview.addHeaderView(headview);

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
            case R.id.left_images:
                finish();
                break;
            case R.id.message:
                startActivity(new Intent(this, MessageActivity.class));
                break;
            case R.id.shopcar:
                startActivity(new Intent(this, ShopCarActivity.class));
                break;
            case R.id.tv_jiliang:
                startActivity(new Intent(this, JiLiangActivity.class));
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
                ImageView image_logo = (ImageView) view.findViewById(R.id.image_logo);
                TextView tv_address = (TextView) view.findViewById(R.id.tv_address);
                TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                tv_name.setText(goods.getData().getTitle());
                tv_address.setText(goods.getData().getShopInfo().getAddress());
                if (goods.getData().getShopInfo().getBusinessAbsUrlList().size() > 0) {
                    GlideUtil.loadImg(goods.getData().getShopInfo().getBusinessAbsUrlList().get(0), image_logo);
                }
            }
            if (stata == 2) {
                view = getLayoutInflater().inflate(R.layout.item_list2, null);
                TextView tv_title = (TextView) view.findViewById(R.id.title);
                tv_title.setText(goods.getData().getSourceText());
                NiceVideoPlayer mNiceVideoPlayer = (NiceVideoPlayer) view.findViewById(R.id.nice_video_player);
                mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
                mNiceVideoPlayer.setUp(goods.getData().getSourceVideoUrl(), null);
                TxVideoPlayerController controller = new TxVideoPlayerController(GoodsDetialActivity.this);
//                Glide.with(GoodsDetialActivity.this).load()
//                        .error(R.mipmap.tt_mr)
//                        .into(controller.imageView());
                mNiceVideoPlayer.setController(controller);

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

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }
}
