package com.taotaohai.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.taotaohai.R;
import com.taotaohai.activity.ClassActivity;
import com.taotaohai.activity.GoodsDetialActivity;
import com.taotaohai.activity.Home;
import com.taotaohai.activity.Login;
import com.taotaohai.activity.MessageActivity;
import com.taotaohai.activity.SearchGoods;
import com.taotaohai.activity.ShopActivity;
import com.taotaohai.activity.ShopCarActivity;
import com.taotaohai.activity.ShopMoreActivity;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.Goods;
import com.taotaohai.bean.HotClass;
import com.taotaohai.bean.HotShop;
import com.taotaohai.bean.HotShopmore;
import com.taotaohai.bean.Ratation;
import com.taotaohai.bean.ShopCarNum;
import com.taotaohai.myview.BadgeView;
import com.taotaohai.myview.MyGridView;
import com.taotaohai.myview.VpSwipeRefreshLayout;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.SPUtils;
import com.taotaohai.util.util;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private Banner banner;
    private VpSwipeRefreshLayout swipe;
    List<View> text;
    private HotShop hotshop;
    private HotClass hotclass;
    private HotShopmore hotshop2;
    private MyGridView mygridview;
    private static HomeFragment fragment;
    private ImageView home_shopcar_image;
    private RelativeLayout rela_shopcar;
    private RelativeLayout rela_message;
    private RecyclerView home_recyclelistview;
    private MyRecyclerAdapter myRecyclerAdapter;
    BadgeView badgeView;
    BadgeView badgeView2;
    int pos = 0;
    int msg=0;
    private TextView down_load;//加载更多
    int pageSize = 10;
    int pageIndex = 0;//第多少个
    private HashMap<String, String> has = new HashMap<>();
    MyGridviewAdapter myGridviewAdapter = new MyGridviewAdapter();
    TIMConversation conversation;

    public static HomeFragment newInstance() {
        if (fragment == null) {
            fragment = new HomeFragment();
        }
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public LocationClient mLocationClient = null;
    ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);

        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        InitLocation();
        mLocationClient.start();
        //注册监听函数
        inithttp();
        initview();
        badgeView = new BadgeView(getActivity(), rela_shopcar);
        badgeView2 = new BadgeView(getActivity(), rela_message);



        return view;
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
    public void inithttp() {
        super.inithttp();

        msg=0;
        pageSize = 10;
        pageIndex = 0;
        unreadMsg();
        get("api/home/rotation", 0);//轮播图
        get("api/goods/hot_class_goods", 1);//热门小分类
        get("api/goods/hot_shop", 2);//热门商店
        get("/api/shopCar/shop_car_num", 20);//购物车数量
        get("/api/message/notReadList/0", 50);
        get("/api/message/notReadList/1", 50);

        initHotGoods();
    }

    private void initHotGoods() {
        has.clear();
        has.put("pageSize", String.valueOf(pageSize));
        has.put("pageIndex", String.valueOf(pageIndex));
        get("api/goods/hot_goods/" + pageIndex + "/" + pageSize, 3);//热门商品

    }


    @Override
    public void onSuccess(String data, int postcode) {
        super.onSuccess(data, postcode);

        switch (postcode) {
            case 0:
                initrato(data);
                break;
            case 22:
                 Goods goods = util.getgson(data, Goods.class);

                if(goods.getData().getStatus()==0){
                    startActivity(new Intent(getActivity(), GoodsDetialActivity.class)
                            .putExtra("id", ratation.getData().get(pos).getTarget())
                    );
                }else {
                    showToast("该商品已下架啦");
                }

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
            case 999:
                startActivityForResult(new Intent(getActivity(), MessageActivity.class), 1);
                break;
            case 998:
                startActivityForResult(new Intent(getActivity(), ShopCarActivity.class), 1);
                break;
            case 20:
                ShopCarNum shopCarNum = new ShopCarNum();
                shopCarNum = util.getgson(data, ShopCarNum.class);

                if (shopCarNum.getData() != 0) {

                    badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                    badgeView.setTextSize(9);// 设置文本大小
                    badgeView.setText(shopCarNum.getData()+""); // 设置要显示的文本
                    badgeView.show();// 将角标显示出来

                } else {
                    badgeView.hide();
                }
                break;
            case 50:
                ShopCarNum shopCarNum2 = new ShopCarNum();
                shopCarNum2 = util.getgson(data, ShopCarNum.class);
                msg+=shopCarNum2.getData();
                if (msg!=0) {

                    badgeView2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                    badgeView2.setTextSize(6);// 设置文本大小
                    badgeView2.setText(""); // 设置要显示的文本
                    badgeView2.show();// 将角标显示出来
                }else {
                    badgeView2.hide();
                }
                break;

        }


    }

    @Override
    public void onError(Throwable ex, int postcode) {
        if (postcode == 999 || postcode == 998) {
            String[] st = ex.toString().split("result:");
            if (st.length > 1) {
                util.isSuccess(util.getgson(st[1], BaseBean.class));
            }
            try {
                if (ex.toString().contains("401")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("温馨提示");
                    dialog.setMessage("是否进入登录页登录?");
                    dialog.setNegativeButton("前往", (dialog1, which) -> {
                        startActivity(new Intent(getActivity(), Login.class));
                    });
                    dialog.setNeutralButton("取消", (dialog1, which) -> {
                    });
                    dialog.show();
                }
            } catch (Exception e) {
            }
        }
    }


    private void inithotshopmore(String data) {


        if (pageIndex != 0) {
            HotShopmore hotshop3 = util.getgson(data, HotShopmore.class);
            for (int i = 0; i < hotshop3.getData().size(); i++) {
                hotshop2.getData().add(hotshop3.getData().get(i));

            }
        } else {
            hotshop2 = util.getgson(data, HotShopmore.class);
        }


        if (hotshop2.getSuccess()) {
            mygridview.setAdapter(myGridviewAdapter);
            myGridviewAdapter.notifyDataSetChanged();
            pageIndex += 1;
        }

    }

    private void inithotshop(String data) {
        hotshop = util.getgson(data, HotShop.class);
        if (hotshop.getSuccess()) {

            home_recyclelistview.setAdapter(myRecyclerAdapter);
        }

    }

    private void inithot(String data) {
        hotclass = util.getgson(data, HotClass.class);
        if (hotclass.getSuccess()) {
//            ((TextView) text.get(0)).setText(hotclass.getData().get(0).getTitle());
            ((TextView) text.get(0)).setText(hotclass.getData().get(0).getClassInfo().getClassName());
            if (hotclass.getData().get(0).getImagesUrl().size() > 0)
                GlideUtil.loadImg(hotclass.getData().get(0).getImagesUrl().get(0), ((ImageView) text.get(2)));

//            ((TextView) text.get(3)).setText(hotclass.getData().get(1).getTitle());
            ((TextView) text.get(3)).setText(hotclass.getData().get(1).getClassInfo().getClassName());
            if (hotclass.getData().get(1).getImagesUrl().size() > 0)
                GlideUtil.loadImg(hotclass.getData().get(1).getImagesUrl().get(0), ((ImageView) text.get(5)));

//            ((TextView) text.get(6)).setText(hotclass.getData().get(2).getTitle());
            ((TextView) text.get(6)).setText(hotclass.getData().get(2).getClassInfo().getClassName());
            if (hotclass.getData().get(2).getImagesUrl().size() > 0)
                GlideUtil.loadImg(hotclass.getData().get(2).getImagesUrl().get(0), ((ImageView) text.get(8)));

//            ((TextView) text.get(9)).setText(hotclass.getData().get(3).getTitle());
            ((TextView) text.get(9)).setText(hotclass.getData().get(3).getClassInfo().getClassName());
            if (hotclass.getData().get(3).getImagesUrl().size() > 0)
                GlideUtil.loadImg(hotclass.getData().get(3).getImagesUrl().get(0), ((ImageView) text.get(11)));


        }
    }


    Ratation ratation;

    private void initrato(String data) {
        ratation = util.getgson(data, Ratation.class);
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
            banner.setOnBannerListener((int position) -> {
                if (ratation.getData().get(position).getType() == 0) {
                    startActivity(new Intent(getActivity(), com.taotaohai.activity.bannerr.class)
                            .putExtra("url", ratation.getData().get(position).getTarget())
                    );
                } else {

                    get("api/goods/"+ratation.getData().get(position).getTarget(),22);
                    pos=position;


                }

            });

        }
    }

    @Override
    public void onFinished(int code) {
        super.onFinished(code);
        swipe.setRefreshing(false);
    }

    private void initview() {
        myRecyclerAdapter = new MyRecyclerAdapter();
        home_recyclelistview = (RecyclerView) view.findViewById(R.id.home_recyclelistview);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        home_recyclelistview.setLayoutManager(linearLayoutManager);
        down_load = (TextView) view.findViewById(R.id.down_load);
        down_load.setOnClickListener(this);
        home_shopcar_image = (ImageView) view.findViewById(R.id.home_shopcar_image);
        rela_shopcar = (RelativeLayout) view.findViewById(R.id.rela_shopcar);
        rela_message = (RelativeLayout) view.findViewById(R.id.rela_message);
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

        swipe = (VpSwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(() -> inithttp());


        view.findViewById(R.id.tv_search).setOnClickListener(this);
        rela_shopcar.setOnClickListener(this);
        rela_message.setOnClickListener(this);
        view.findViewById(R.id.rela_1).setOnClickListener(this);
        view.findViewById(R.id.rela_2).setOnClickListener(this);
        view.findViewById(R.id.rela_3).setOnClickListener(this);
        view.findViewById(R.id.rela_4).setOnClickListener(this);
        banner = (Banner) view.findViewById(R.id.banner);
        //设置图片加载器


        mygridview = (MyGridView) view.findViewById(R.id.mygridview);
        swipeTouch();

    }

    int lastX, lastY;


    private void swipeTouch() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.down_load:

                initHotGoods();

                break;

            case R.id.tv_search:
                startActivityForResult(new Intent(getActivity(), SearchGoods.class), 1);
                break;
            case R.id.rela_shopcar:
                get("api/user/", 998);

                break;
            case R.id.rela_message:

                if(SPUtils.contains(getActivity(),"hxid")){

                    if(!TIMManager.getInstance().getLoginUser().equals(SPUtils.get(getActivity(),"username",""))){

                        TIMManager.getInstance().login(SPUtils.get(getActivity(),"username","").toString(),SPUtils.get(getActivity(),"hxid","").toString(),new TIMCallBack() {
                            @Override
                            public void onError(int code, String desc) {
                                //错误码code和错误描述desc，可用于定位请求失败原因
                                //错误码code列表请参见错误码表

                                Log.e("tag", "登入聊天失败: "+code+"------"+desc );
                            }

                            @Override
                            public void onSuccess() {

                                Log.e("tag", "onSuccess: "+TIMManager.getInstance().getLoginUser().equals(SPUtils.get(getActivity(),"username","")) );

                            }
                        });


                    }


                }

                get("api/user/", 999);
                break;
            case R.id.rela_1:
                if (null != hotclass) {
                    startActivity(new Intent(getActivity(), ClassActivity.class)
                            .putExtra("id", hotclass.getData().get(0).getClassId())
                    );
                }

//                ((Home) getActivity()).initview2();
                break;
            case R.id.rela_2:
                if (null != hotclass) {
                    startActivity(new Intent(getActivity(), ClassActivity.class)
                            .putExtra("id", hotclass.getData().get(1).getClassId())
                    );
                }

//                ((Home) getActivity()).initview2();
                break;
            case R.id.rela_3:
                if (null != hotclass) {
                    startActivity(new Intent(getActivity(), ClassActivity.class)
                            .putExtra("id", hotclass.getData().get(2).getClassId())
                    );
                }

//                ((Home) getActivity()).initview2();
                break;
            case R.id.rela_4:
                if (null != hotclass) {
                    startActivity(new Intent(getActivity(), ClassActivity.class)
                            .putExtra("id", hotclass.getData().get(3).getClassId())
                    );
                }

//                ((Home) getActivity()).initview2();
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
            viewholder viewholder = null;
            if (convertView == null) {
                viewholder = new viewholder();
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_hor_gride, null);
                viewholder.su = convertView.findViewById(R.id.su);
                viewholder.imageView = (ImageView) convertView.findViewById(R.id.image_photo);
                viewholder.tv_tite = (TextView) convertView.findViewById(R.id.tv_tite);
                viewholder.tv_remorke = (TextView) convertView.findViewById(R.id.tv_remorke);
                viewholder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
                viewholder.tv_unit = (TextView) convertView.findViewById(R.id.tv_unit);
                viewholder.tv_people = (TextView) convertView.findViewById(R.id.tv_people);
                convertView.setTag(viewholder);
            } else {
                viewholder = (HomeFragment.viewholder) convertView.getTag();
            }
            final HotShopmore.Data data = hotshop2.getData().get(position);
            convertView.findViewById(R.id.rela_all).setOnClickListener(v -> startActivity(new Intent(getActivity(), GoodsDetialActivity.class)
                    .putExtra("id", data.getId())
            ));
            if (data.getSourceVideo().length() > 0) {
                viewholder.su.setVisibility(View.VISIBLE);
            } else {
                viewholder.su.setVisibility(View.GONE);
            }
            if (data.getImagesUrl() != null && data.getImagesUrl().size() > 0) {
                GlideUtil.loadImg(data.getImagesUrl().get(0), viewholder.imageView);
            }
            if (data.getClassInfo() != null) {
                viewholder.tv_tite.setText(data.getTitle());
            }
            if (data.getShopInfo() != null) {
                viewholder.tv_remorke.setText(data.getRemark());
                viewholder.tv_people.setText("已购买" + data.getSaleVolume()+"件");
            }
            viewholder.tv_money.setText("￥ " + data.getPrice());
            viewholder.tv_unit.setText("/" + data.getUnit());
            return convertView;
        }
    }

    class viewholder {
        View su;
        ImageView imageView;
        TextView tv_tite;
        TextView tv_remorke;
        TextView tv_money;
        TextView tv_unit;
        TextView tv_people;
    }


    public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyHolder> {


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = getActivity().getLayoutInflater().inflate(R.layout.item_hor, null);
            MyHolder holder = new MyHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

            holder.lin_1.removeAllViews();
            for (int i = 0; i < 3 && i < hotshop.getData().get(position).getShopIdentifies().size(); i++) {
                TextView textView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.shop_textview, null);
                textView.setText(hotshop.getData().get(position).getShopIdentifies().get(i).getName());
                holder.lin_1.addView(textView);

            }


            GlideUtil.loadImg(hotshop.getData().get(position).getLogoIdAbsUrl(), holder.image);
            holder.tv_title.setText(hotshop.getData().get(position).getName());
            holder.tv_scor.setText(hotshop.getData().get(position).getTotalCommonLevel() + "分");
            if(Home.LA!=0.0){

                holder.tv_juli.setText(util.getdouboletwo(Home.LO, Home.LA, Double.valueOf(hotshop.getData().get(position).getLongitude()), Double.valueOf(hotshop.getData().get(position).getLatitude())) + "km");
            }
            holder.lin_2.setOnClickListener((l) -> {


                startActivity(new Intent(getActivity(), ShopActivity.class).putExtra("id", hotshop.getData().get(position).getId()));
            });


        }

        @Override
        public int getItemCount() {
            return hotshop.getData().size();
        }


        class MyHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView tv_title;
            TextView tv_scor;
            TextView tv_juli;
            LinearLayout lin_1;
            LinearLayout lin_2;

            public MyHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.image);
                tv_title = (TextView) itemView.findViewById(R.id.tv_title);
                tv_scor = (TextView) itemView.findViewById(R.id.tv_scor);
                tv_juli = (TextView) itemView.findViewById(R.id.tv_juli);
                lin_1 = (LinearLayout) itemView.findViewById(R.id.lin_1);
                lin_2 = (LinearLayout) itemView.findViewById(R.id.rela_all);
            }
        }
    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
//            GlobalParams.latitude = location.getLatitude();
//            GlobalParams.longitude = location.getLongitude();
            if (hotshop != null) {
                myRecyclerAdapter.notifyDataSetChanged();
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        msg=0;
        unreadMsg();
        get("/api/shopCar/shop_car_num", 20);//购物车数量
        get("/api/message/notReadList/0", 50);
        get("/api/message/notReadList/1", 50);
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        super.onResume();

        msg=0;
        unreadMsg();
        get("/api/shopCar/shop_car_num", 20);
        get("/api/message/notReadList/0", 50);
        get("/api/message/notReadList/1", 50);
    }

}
