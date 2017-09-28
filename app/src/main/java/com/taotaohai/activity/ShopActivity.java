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

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        initview();
    }

    boolean isfocus = false;

    private void focus() {
        isfocus = true;
        image_focus.setVisibility(View.GONE);
        tv_focus.setText("已关注");
        rela_focus.setBackgroundResource(R.drawable.button_r2_glay);
    }

    private void unfocus() {
        isfocus = false;
        image_focus.setVisibility(View.VISIBLE);
        tv_focus.setText("关注");
        rela_focus.setBackgroundResource(R.drawable.button_r2);
    }

    private void initview() {

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
        initdata();//初始化数据


    }

    private void initdata() {

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


        List<String> lista = Arrays.asList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        CommonAdapter adapter = new CommonAdapter<String>(this, R.layout.item_hor_gride2, lista) {
            @Override
            protected void convert(ViewHolder holder, final String s, int position) {


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
                if (isfocus) {
                    unfocus();
                } else {
                    focus();
                }
                break;
            case R.id.relaclick_1:

                break;
            case R.id.relaclick_2:
                startActivity(new Intent(this, ShopIntroducActivity.class));
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
