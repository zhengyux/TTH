package com.taotaohai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Focus;
import com.taotaohai.bean.Shop;
import com.taotaohai.bean.ShopGoods;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Arrays;
import java.util.List;

public class ShopActivity extends BaseActivity implements View.OnClickListener {

    private XRefreshView xrefreshview;
    private RecyclerView recyclerView;
    private TextView tv_focus;
    private View rela_focus, image_focus;
    private View rela_class;
    private LinearLayout line_class;
    private ShopGoods shopGoods;
    private Shop shop;
    private ImageView image_photo;
    private TextView tv_name;
    private TextView tv_scor;
    private TextView tv_count;

    @Override
    protected void inithttp() {
        get("api/shop/" + getintent("id") + "/goods");
        get("api/shop/" + getintent("id"), 1);
        get("api/shop/follow/" + getintent("id") + "/1", 2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isfocus) {
            count--;
            focus();
        } else {
            count++;
            unfocus();
        }
        tv_count.setText(String.valueOf(count));
    }

    public static int count = 0;
    public static boolean isfocus = false;

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        if (postcode == 0) {
            shopGoods = util.getgson(result, ShopGoods.class);
            initdata();//初始化数据
        }
        if (postcode == 1) {
            shop = util.getgson(result, Shop.class);
            initdata();//初始化数据
            count = Integer.valueOf(shop.getData().getTotalLike());
        }
        if (postcode == 2) {
            Focus focus = util.getgson(result, Focus.class);
            if (focus.getData().getFollow() == 0) {
                isfocus = false;
                count++;
                unfocus();
            } else {
                isfocus = true;
                count--;
                focus();
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        initview();
        inithttp();
    }

    public void onMessage(View view) {
        startActivity(new Intent(this, MessageActivity.class));
    }

    public void onShopcar(View view) {
        startActivity(new Intent(this, ShopCarActivity.class));
    }


    private void focus() {
        count++;
        isfocus = true;
        image_focus.setVisibility(View.GONE);
        tv_focus.setText("已关注");
        rela_focus.setBackgroundResource(R.drawable.button_r2_glay);
    }

    private void unfocus() {
        count--;
        isfocus = false;
        image_focus.setVisibility(View.VISIBLE);
        tv_focus.setText("关注");
        rela_focus.setBackgroundResource(R.drawable.button_r2);
    }

    private void initview() {
        image_photo = (ImageView) findViewById(R.id.image_photo);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_scor = (TextView) findViewById(R.id.tv_scor);
        tv_count = (TextView) findViewById(R.id.tv_count);

        rela_focus = findViewById(R.id.rela_focus);
        image_focus = findViewById(R.id.image_focus);
        rela_focus.setOnClickListener(this);
        tv_focus = (TextView) findViewById(R.id.tv_focus);

        rela_class = findViewById(R.id.rela_class);
        line_class = (LinearLayout) findViewById(R.id.line_class);
        findViewById(R.id.rela_class).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.relaclick_1).setOnClickListener(this);
        findViewById(R.id.relaclick_2).setOnClickListener(this);
        findViewById(R.id.relaclick_3).setOnClickListener(this);

        xrefreshview = (XRefreshView) findViewById(R.id.xrefreshview);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        xrefreshview.setPullLoadEnable(true);
        recyclerView.setHasFixedSize(true);//item改变的时候recycleview不会重新计算高度


    }

    private void initdata() {
        GlideUtil.loadImg(shop.getData().getLogoIdAbsUrl(), image_photo);
        tv_name.setText(shop.getData().getName());
        tv_scor.setText(shop.getData().getTotalCommonLevel() + "分");
        tv_count.setText(shop.getData().getTotalLike());

        for (int i = 0; i < 3; i++) {
            View v = getLayoutInflater().inflate(R.layout.item_line, null);
            TextView text = (TextView) v.findViewById(R.id.text);
            text.setText("分类" + i);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            line_class.addView(v);
        }
        if (shopGoods.getData().size() == 0) {
            return;
        }
        CommonAdapter adapter = new CommonAdapter<ShopGoods.Data>(this, R.layout.item_hor_gride2, shopGoods.getData()) {
            @Override
            protected void convert(ViewHolder holder, final ShopGoods.Data data, int position) {
                ImageView imageView = holder.getView(R.id.image_photo);
                if (data.getImagesUrl() != null && data.getImagesUrl().size() > 0)
                    GlideUtil.loadImg(data.getImagesUrl().get(0), imageView);
                if (data.getSourceVideo() != null && data.getSourceVideo().length() > 0) {
                    holder.setVisible(R.id.image_1, true);
                } else {
                    holder.setVisible(R.id.image_1, false);
                }
                holder.setText(R.id.tv_1, data.getTitle());
                holder.setText(R.id.tv_2, data.getRemark());
                holder.setText(R.id.tv_3, "￥：" + data.getPrice());
                holder.setText(R.id.tv_4, "/" + data.getUnit());
                holder.setText(R.id.tv_5, "已有" + data.getSaleVolume() + "人购买");
                holder.setOnClickListener(R.id.rela_all, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ShopActivity.this, GoodsDetialActivity.class)
                                .putExtra("id", data.getId())
                        );
                    }
                });
            }
        };
        // 设置静默加载模式
        xrefreshview.setSilenceLoadMore(false);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        xrefreshview.setPinnedTime(1000);

        xrefreshview.setMoveForHorizontal(true);
        xrefreshview.setPullRefreshEnable(false);
//        recyclerviewAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
//		xRefreshView1.setPullLoadEnable(false);
        //设置静默加载时提前加载的item个数
//        xrefreshview.setPreLoadCount(4);
        xrefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                super.onRefresh(isPullDown);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xrefreshview.stopRefresh();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                super.onLoadMore(isSilence);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        xrefreshview.setLoadComplete(true);//已无更多数据
                        xrefreshview.stopLoadMore();//还有数据
                    }
                }, 1000);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rela_focus:
                get("api/follow/" + getintent("id") + "/shop");
                if (isfocus) {
                    unfocus();
                } else {
                    focus();
                }
                tv_count.setText(String.valueOf(count));
                break;
            case R.id.relaclick_1:

                break;
            case R.id.relaclick_2:
                startActivity(new Intent(this, ShopIntroducActivity.class)
                        .putExtra("data", shop)
                );
                break;
            case R.id.relaclick_3:
                if (rela_class.isShown()) {
                    rela_class.setVisibility(View.GONE);
                } else {
                    rela_class.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.rela_class:
                rela_class.setVisibility(View.GONE);
                break;

        }
    }
}
