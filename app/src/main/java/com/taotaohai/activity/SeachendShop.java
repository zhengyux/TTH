package com.taotaohai.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.ShopCarNum;
import com.taotaohai.bean.ShopList;
import com.taotaohai.bean.ShopListSearch;
import com.taotaohai.myview.BadgeView;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.xutils.http.HttpMethod;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import static com.taotaohai.GlobalParams.NONOTICELOGIN;

public class SeachendShop extends BaseActivity implements View.OnClickListener {

    private XRefreshView xrefreshview;
    private RecyclerView recyclerView;
    private RelativeLayout rela_shopcar;
    private RelativeLayout rela_message;
    TIMConversation conversation;
    int msg=0;
    BadgeView badgeView2;

    String requesttype = "0";
    List<ShopList.Data> shoplist;
    private CommonAdapter adapter;
    String name = "";
    int upOneStata = 0;//记录上一次请求是什么~
    @ViewInject(R.id.tv_1)
    TextView tv_1;
    @ViewInject(R.id.tv_2)
    TextView tv_2;
    @ViewInject(R.id.tv_3)
    TextView tv_3;
    TextView tv_default;

    @Event(value = {R.id.tv_1, R.id.tv_2, R.id.tv_3})
    private void onChoose(View v) {
        if (tv_default == null && R.id.tv_1 == v.getId() || (tv_default != null && tv_default.getId() == v.getId())) {
            return;//已经是当前位置,点击不用有反应
        }
        if (tv_default == null) {
            tv_default = tv_1;
        }
        tv_default.setTextColor(getResources().getColor(R.color.text_black));
        switch (v.getId()) {
            case R.id.tv_1:
                tv_default = tv_1;
                requesttype = "0";
                break;
            case R.id.tv_2:
                tv_default = tv_2;
                requesttype = "1";
                break;
            case R.id.tv_3:
                requesttype = "2";
                tv_default = tv_3;
                break;
        }
        tv_default.setTextColor(getResources().getColor(R.color.them));
        switch (upOneStata) {
            case 0:
                getDataByName();
                break;
            case 1:
                getDataBycity(getintent("city"));
                break;
            case 2:
                getDataByDistance(getintent("distance"));
                break;
        }

    }

    private void unreadMsg(){

        long cnt = TIMManager.getInstance().getConversationCount();

        //遍历会话列表
        for(long i = 0; i < cnt; ++i) {
            //根据索引获取会话
            conversation =TIMManager.getInstance().getConversationByIndex(i);

            conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C,conversation.getPeer());

            msg+=conversation.getUnreadMessageNum();


        }
    }

    @Override
    protected void inithttp() {
        msg=0;
        unreadMsg();
        get("/api/message/notReadList/0",50);
        get("/api/message/notReadList/1",50);
        get("/api/shopCar/shop_car_num",20);
        if (getintent("name").length() != 0) {
            name = getintent("name");
            getDataByName();
        } else if (getintent("distance").length() != 0) {
            getDataByDistance(getintent("distance"));
        } else if (getintent("city").length() != 0) {
            getDataBycity(getintent("city"));
        }
    }

    private void getDataByName() {
        upOneStata = 0;
        has.clear();
        has.put("shopName", name);
        has.put("pageSize", "100");
        has.put("pageIndex", "0");
        has.put("type", requesttype);
        Http(HttpMethod.GET, "api/search/shop_name", has, 10);

    }

    private void getDataBycity(String cityId) {
        upOneStata = 1;
        has.clear();
        has.put("pageSize", "100");
        has.put("pageIndex", "0");
        has.put("type", requesttype);
        Http(HttpMethod.GET, "api/search/city/" + cityId, has, 0);
    }

    private void getDataByDistance(String distance) {
        upOneStata = 2;
        has.clear();
        has.put("pageSize", "100");
        has.put("pageIndex", "0");
        has.put("lat", String.valueOf(Home.LA));
        has.put("lng", String.valueOf(Home.LO));
        has.put("distance", distance);
        has.put("type", requesttype);
        Http(HttpMethod.GET, "api/search/rotation", has, 10);

    }



    @Override
    public void onError(Throwable ex, int postcode) {
        if (postcode == 998 || postcode == 999) {
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

        if(postcode==20){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            if(shopCarNum.getData()!=0){
                BadgeView badgeView = new BadgeView(getApplicationContext(),rela_shopcar);
                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(9);// 设置文本大小
                badgeView.setText(shopCarNum.getData()+""); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }

        }
        if(postcode==50){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            msg+=shopCarNum.getData();
            if(msg!=0){

                badgeView2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView2.setTextSize(6);// 设置文本大小
                badgeView2.setText(""); // 设置要显示的文本
                badgeView2.show();// 将角标显示出来
            }else {
                badgeView2.hide();
            }

        }


        if (postcode == 999) {
            startActivity(new Intent(SeachendShop.this, MessageActivity.class));
        }
        if (postcode == 998) {
            startActivity(new Intent(SeachendShop.this, ShopCarActivity.class));
        }

        if (postcode == 0) {
            ShopListSearch shop = util.getgson(result, ShopListSearch.class);
            shoplist = shop.getData();
        }
        if (postcode == 10) {
            ShopList shop = util.getgson(result, ShopList.class);
            shoplist = shop.getData();
        }
        if(shoplist!=null){
            initdata();//初始化数据
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seachendgoods);
        x.view().inject(this);//注解绑定
        inithttp();
        initview();
        badgeView2= new BadgeView(getApplicationContext(),rela_message);
    }

    private void initview() {
        EditText edit_search = (EditText) findViewById(R.id.edit_search);

        if (getIntent().getStringExtra("name") != null) {
            edit_search.setText(getIntent().getStringExtra("name"));
        } else if (getIntent().getStringExtra("cityname") != null) {
            edit_search.setText(getIntent().getStringExtra("cityname"));
        } else if (getIntent().getStringExtra("distance") != null) {

            switch (getIntent().getStringExtra("distance")) {
                case "0":
                    edit_search.setText("100km以内");
                    break;
                case "1":
                    edit_search.setText("300km以内");
                    break;
                case "2":
                    edit_search.setText("500km以内");
                    break;
                case "3":
                    edit_search.setText("不限");
                    break;
            }

        }
        edit_search.setSelection(edit_search.getText().length());
        edit_search.setOnEditorActionListener((v, d, e) -> {
                /*判断是否是“GO”键*/
            if (d == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                InputMethodManager imm = (InputMethodManager) v
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(
                            v.getApplicationWindowToken(), 0);
                }
                name = v.getText().toString();
                getDataByName();
                return true;
            }
            return false;
        });


        rela_message = (RelativeLayout) findViewById(R.id.rela_message);
        rela_message.setOnClickListener(v -> get("api/user/", 999));

        rela_shopcar = (RelativeLayout) findViewById(R.id.rela_shopcar);
        rela_shopcar.setOnClickListener(v -> get("api/user/", 998));
        findViewById(R.id.back).setOnClickListener(this);

        xrefreshview = (XRefreshView)

                findViewById(R.id.xrefreshview);

        recyclerView = (RecyclerView)

                findViewById(R.id.recycleview);
        xrefreshview.setPullLoadEnable(true);
        recyclerView.setHasFixedSize(true);//item改变的时候recycleview不会重新计算高度


    }

    private void initdata() {

        adapter = new CommonAdapter<ShopList.Data>(this, R.layout.item_hor_gride3, shoplist) {
            @Override
            protected void convert(ViewHolder holder, final ShopList.Data data, int position) {
                ImageView imageView = holder.getView(R.id.image_photo);
                GlideUtil.loadImg(data.getLogoIdAbsUrl(), imageView);

                holder.setText(R.id.tv_1, data.getName());
                holder.setText(R.id.tv_2, data.getTotalCommonLevel() + "分");
                holder.setText(R.id.tv_3, util.getdouboletwo(Home.LO, Home.LA, Double.valueOf(data.getLongitude()), Double.valueOf(data.getLatitude())) + "km");
                holder.setOnClickListener(R.id.rela_all, v -> startActivity(new Intent(SeachendShop.this, ShopActivity.class)
                        .putExtra("id", data.getId())
                ));
                LinearLayout lin_1 = holder.getView(R.id.lin_1);
                for (int i = 0; i < 3 && i < data.getShopIdentifies().size(); i++) {
                    TextView textView = (TextView) getLayoutInflater().inflate(R.layout.shop_textview, null);
                    textView.setText(data.getShopIdentifies().get(i).getName());
                    lin_1.addView(textView);
                }

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
        finish();
    }

}

