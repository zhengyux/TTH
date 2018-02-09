package com.taotaohai.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.hyphenate.easeui.EaseConstant;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.Focus;
import com.taotaohai.bean.Shop;
import com.taotaohai.bean.ShopCarNum;
import com.taotaohai.bean.ShopGoods;
import com.taotaohai.bean.ShopGoods2;
import com.taotaohai.bean.Shopclass;
import com.taotaohai.myview.BadgeView;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.taotaohai.widgets.MultipleStatusView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import static com.taotaohai.GlobalParams.NONOTICELOGIN;

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
    private LinearLayout lin_1;
    private Shopclass shopclass;
    private RelativeLayout rela_shopcar;
    private RelativeLayout rela_message;

    @Override
    protected void inithttp() {
        mMsvLayout.loading();
        get("api/shop/" + getintent("id") + "/goods",0);
        get("api/shop/" + getintent("id"), 1);
        get("api/shop/follow/" + getintent("id") + "/1", 2);
        get("api/shop/" + getintent("id") + "/class", 3);
        get("/api/shopCar/shop_car_num",20);
        get("/api/message/notReadList/0",50);
        get("/api/message/notReadList/1",51);
    }

    @Override
    public void onFinished(int code) {
        super.onFinished(code);
        if (code == 0 && shopGoods == null) {
            get("api/shop/" + getintent("id") + "/goods");
            return;
        }
        if (code == 1 && shop == null) {
            get("api/shop/" + getintent("id"), 1);
            return;
        }
        if (code == 3 && shopclass == null) {
            get("api/shop/" + getintent("id") + "/class", 3);
            return;
        }
        if (code != 2)
            mMsvLayout.content();
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
    public void onError(Throwable ex, int postcode) {
        if(postcode==998||postcode==995||postcode==997||postcode==996){
            String[] st = ex.toString().split("result:");
            if (st.length > 1) {
                util.isSuccess(util.getgson(st[1], BaseBean.class));
            }
            try {
                if (ex.toString().contains("401") & postcode != NONOTICELOGIN) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("未登录");
                    dialog.setMessage("是否进入登录页登录?");
                    dialog.setNegativeButton("前往", (dialog1, which) -> {
                        startActivity(new Intent(this, Login.class));
                        finish();
                    });
                    dialog.setNeutralButton("取消", (dialog1, which) -> {
                    });
                    dialog.show();
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);


        if(postcode==998){

            startActivity(new Intent(ShopActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, shop.getData().getUser().getU_id()));

        }
        if(postcode==997){
            get("api/follow/" + getintent("id") + "/shop",999);
        }
        if (postcode==996){
            startActivity(new Intent(this, MessageActivity.class));

        }
        if (postcode==995){
            startActivity(new Intent(this, ShopCarActivity.class));
        }

        if(postcode==20){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            if(shopCarNum.getData()!="0"){
                BadgeView badgeView = new BadgeView(getApplicationContext(),rela_shopcar);
                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(9);// 设置文本大小
                badgeView.setText(shopCarNum.getData()); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }

        }
        if(postcode==50){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            if(shopCarNum.getData()!="0"){
                BadgeView badgeView = new BadgeView(getApplicationContext(),rela_message);
                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(6);// 设置文本大小
                badgeView.setText(""); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }

        }
        if(postcode==51){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            if(shopCarNum.getData()!="0"){
                BadgeView badgeView = new BadgeView(getApplicationContext(),rela_message);
                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(6);// 设置文本大小
                badgeView.setText(""); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }

        }

        if(postcode==999){
            if (isfocus) {
                showToast("取消成功");
                unfocus();
            } else {
                showToast("关注成功");
                focus();
            }
            tv_count.setText(String.valueOf(count));
        }
        if (postcode == 0) {
            shopGoods = util.getgson(result, ShopGoods.class);
            initdata();//初始化数据

        }
        if (postcode == 1) {
            shop = util.getgson(result, Shop.class);
            if("shopin".equals(getintent("id2"))){
                startActivity(new Intent(this, ShopIntroducActivity.class)
                        .putExtra("data", shop));
                finish();

            }
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
        if (postcode == 3) {
            shopclass = util.getgson(result, Shopclass.class);
        }
        if (postcode == 4) {
            ShopGoods2 shopGoods2 = util.getgson(result, ShopGoods2.class);
            shopGoods.getData().clear();
            shopGoods.getData().addAll(shopGoods2.getData().getData());
            initdata2();//初始化数据
        }

    }

    private void initdata2() {
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
                holder.setText(R.id.tv_3, "￥ " + data.getPrice());
                holder.setText(R.id.tv_4, "/" + data.getUnit());
                holder.setText(R.id.tv_5, "已有" + data.getSaleVolume() + "人购买");
                holder.setOnClickListener(R.id.rela_all, v -> startActivity(new Intent(ShopActivity.this, GoodsDetialActivity.class)
                        .putExtra("id", data.getId())
                ));
            }
        };

        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        initview();
        inithttp();
    }

    public void onMessage(View view) {
        get("api/user/",996);
    }

    public void onShopcar(View view) {
        get("api/user/",995);

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
        rela_message = (RelativeLayout) findViewById(R.id.rela_message);
        rela_shopcar = (RelativeLayout) findViewById(R.id.rela_shopcar);
        lin_1 = (LinearLayout) findViewById(R.id.lin_1);
        mMsvLayout = (MultipleStatusView) findViewById(R.id.msv_layout);
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
        // 设置静默加载模式
        xrefreshview.setSilenceLoadMore(false);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        xrefreshview.setPinnedTime(1000);

        xrefreshview.setMoveForHorizontal(true);
        xrefreshview.setPullRefreshEnable(false);

    }

    private void initdata() {
        GlideUtil.loadImg(shop.getData().getLogoIdAbsUrl(), image_photo);
        tv_name.setText(shop.getData().getName());
        tv_scor.setText(shop.getData().getTotalCommonLevel() + "分");
        tv_count.setText(shop.getData().getTotalLike());


        lin_1.removeAllViews();
        for (int i = 0; i < 3 && i<shop.getData().getShopIdentifies().size(); i++) {
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.shop_textview, null);
            textView.setText(shop.getData().getShopIdentifies().get(i).getName());

            lin_1.addView(textView);
        }
        line_class.removeAllViews();
        for (int i = 0; i < shopclass.getData().size(); i++) {
            View v = getLayoutInflater().inflate(R.layout.item_line, null);
            TextView text = (TextView) v.findViewById(R.id.text);
            text.setText(shopclass.getData().get(i).getClassName());
            int finalI = i;
            text.setOnClickListener(v1 -> {
                get("api/shop/" + getintent("id") + "/class/" + shopclass.getData().get(finalI).getId() + "/goods", 4);
                rela_class.setVisibility(View.GONE);
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
                holder.setText(R.id.tv_3, "￥ " + data.getPrice());
                holder.setText(R.id.tv_4, "/" + data.getUnit());
                holder.setText(R.id.tv_5, "已有" + data.getSaleVolume() + "人购买");
                holder.setOnClickListener(R.id.rela_all, v -> startActivity(new Intent(ShopActivity.this, GoodsDetialActivity.class)
                        .putExtra("id", data.getId())
                ));
            }
        };

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

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back:
                finish();
                break;
            case R.id.rela_focus:
                get("api/user/",997);

                break;
            case R.id.relaclick_1:
                get("api/user/",998);
                break;
            case R.id.relaclick_2:
                startActivity(new Intent(this, ShopIntroducActivity.class)
                        .putExtra("data", shop));
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
