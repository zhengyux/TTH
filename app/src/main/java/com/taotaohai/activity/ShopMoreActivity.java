package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.taotaohai.GlobalParams;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.ShopList;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Arrays;
import java.util.List;

public class ShopMoreActivity extends BaseActivity implements View.OnClickListener {

    private XRefreshView xrefreshview;
    private RecyclerView recyclerView;
    private ShopList shopList;

    @Override
    protected void inithttp() {
        get("api/shop");
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        shopList = util.getgson(result, ShopList.class);
        initdata();//初始化数据
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopmore);
        initview();
        inithttp();
    }

    private void initview() {
        findViewById(R.id.rela_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopMoreActivity.this, MessageActivity.class));
            }
        });
        findViewById(R.id.rela_shopcar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopMoreActivity.this, ShopCarActivity.class));
            }
        });
        xrefreshview = (XRefreshView) findViewById(R.id.xrefreshview);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        xrefreshview.setPullLoadEnable(true);
        recyclerView.setHasFixedSize(true);//item改变的时候recycleview不会重新计算高度


    }

    private void initdata() {

        CommonAdapter adapter = new CommonAdapter<ShopList.Data>(this, R.layout.item_hor_gride3, shopList.getData()) {
            @Override
            protected void convert(ViewHolder holder, final ShopList.Data data, int position) {
                ImageView imageView = holder.getView(R.id.image_photo);
                GlideUtil.loadImg(data.getLogoIdAbsUrl(), imageView);
                holder.setText(R.id.tv_1, data.getName());
                holder.setText(R.id.tv_2, data.getTotalCommonLevel() + "分");
                holder.setText(R.id.tv_3, util.getdouboletwo(GlobalParams.latitude, GlobalParams.longitude, Double.valueOf(data.getLatitude()), Double.valueOf(data.getLongitude())) + "km");
                holder.setOnClickListener(R.id.rela_all, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ShopMoreActivity.this, ShopActivity.class)
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
    }
}
