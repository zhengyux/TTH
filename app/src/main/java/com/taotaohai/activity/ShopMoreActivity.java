package com.taotaohai.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.taotaohai.GlobalParams;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.ShopCarNum;
import com.taotaohai.bean.ShopList;
import com.taotaohai.myview.BadgeView;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.xutils.http.HttpMethod;

import java.util.HashMap;

import static com.taotaohai.GlobalParams.NONOTICELOGIN;

public class ShopMoreActivity extends BaseActivity implements View.OnClickListener {

    private XRefreshView xrefreshview;
    private RecyclerView recyclerView;
    private ShopList shopList;
    private CommonAdapter adapter;
    private String locationProvider;
    private RelativeLayout rela_shopcar;
    private TextView tv_distance;
    private TextView tv_ar;
    private TextView tv_score;


    @Override
    protected void inithttp() {
        get("api/shop");
        get("/api/shopCar/shop_car_num",20);
    }

    @Override
    public void onError(Throwable ex, int postcode) {
        if(postcode==999||postcode==998){
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
        if(postcode==999){
            startActivity(new Intent(ShopMoreActivity.this, MessageActivity.class));
        }

        if(postcode==998){
            startActivity(new Intent(ShopMoreActivity.this, ShopCarActivity.class));

        }

        shopList = util.getgson(result, ShopList.class);
        initdata();//初始化数据
        switch (postcode) {
            case 999:


                break;

            case 998:

                break;
            case 0:
                shopList = null;
                shopList = util.getgson(result, ShopList.class);
                adapter.notifyDataSetChanged();
                break;

            case 2:

                shopList = null;
                shopList = util.getgson(result, ShopList.class);
                adapter.notifyDataSetChanged();

                break;

            case 3:
                shopList = null;
                shopList = util.getgson(result, ShopList.class);
                adapter.notifyDataSetChanged();
                break;
            case 20:




                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopmore);
        initview();
        inithttp();
        getLoc();
    }

    private void initview() {
        findViewById(R.id.rela_message).setOnClickListener(v ->
                get("api/user/",999)
                );
        rela_shopcar = (RelativeLayout) findViewById(R.id.rela_shopcar);
        rela_shopcar.setOnClickListener(v ->
                        get("api/user/",998)
                );
        findViewById(R.id.back).setOnClickListener(this);
        tv_ar= (TextView) findViewById(R.id.tv_ar);
        tv_ar.setOnClickListener(this);
        tv_distance= (TextView) findViewById(R.id.tv_distance);
        tv_distance.setOnClickListener(this);
        tv_score= (TextView) findViewById(R.id.tv_score);
        tv_score.setOnClickListener(this);
        xrefreshview = (XRefreshView) findViewById(R.id.xrefreshview);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        xrefreshview.setPullLoadEnable(true);
        recyclerView.setHasFixedSize(true);//item改变的时候recycleview不会重新计算高度


    }

    private void initdata() {

        adapter = new CommonAdapter<ShopList.Data>(this, R.layout.item_hor_gride3, shopList.getData()) {
            @Override
            protected void convert(ViewHolder holder, final ShopList.Data data, int position) {
                ImageView imageView = holder.getView(R.id.image_photo);
                GlideUtil.loadImg(data.getLogoIdAbsUrl(), imageView);
                LinearLayout lin_1 = holder.getView(R.id.lin_1);
                for (int i = 0; i < 3 && i < data.getShopIdentifies().size(); i++) {
                    TextView textView = (TextView) getLayoutInflater().inflate(R.layout.shop_textview, null);
                    textView.setText(data.getShopIdentifies().get(i).getName());
                    lin_1.addView(textView);
                }
                holder.setText(R.id.tv_1, data.getName());
                holder.setText(R.id.tv_2, data.getTotalCommonLevel() + "分");
                holder.setText(R.id.tv_3, util.getdouboletwo(GlobalParams.latitude, GlobalParams.longitude, Double.valueOf(data.getLatitude()), Double.valueOf(data.getLongitude())) + "km");
                holder.setOnClickListener(R.id.rela_all, v -> startActivity(new Intent(ShopMoreActivity.this, ShopActivity.class)
                        .putExtra("id", data.getId())
                ));
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
                new Handler().postDelayed(() -> xrefreshview.stopRefresh(), 1000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                super.onLoadMore(isSilence);
                new Handler().postDelayed(() -> {
//                        xrefreshview.setLoadComplete(true);//已无更多数据
                    xrefreshview.stopLoadMore();//还有数据
                }, 1000);
            }
        });


    }

    public LocationClient mLocationClient = null;

    public void getLoc() {

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(new MyLocationListener());
        //注册监听函数
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
//可选，设置返回经纬度坐标类型，默认gcj02
//gcj02：国测局坐标；
//bd09ll：百度经纬度坐标；
//bd09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(0);
//可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
//可选，设置是否收集Crash信息，默认收集，即参数为false


        mLocationClient.setLocOption(option);
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.start();
        }


    HashMap hashMap1 = new HashMap();

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_ar:

                get("api/shop",0);
                tv_ar.setTextColor(getResources().getColor(R.color.btn_blue_normal));
                tv_distance.setTextColor(getResources().getColor(R.color.black_deep));
                tv_score.setTextColor(getResources().getColor(R.color.black_deep));
                break;
            case R.id.tv_distance:


                    if(hashMap1.size()!=0){
                        Http(HttpMethod.GET,"api/shop",hashMap1,2);

                    }
                tv_ar.setTextColor(getResources().getColor(R.color.black_deep));
                tv_distance.setTextColor(getResources().getColor(R.color.btn_blue_normal));
                tv_score.setTextColor(getResources().getColor(R.color.black_deep));





                break;
            case R.id.tv_score:

                HashMap hashMap = new HashMap();
                hashMap.put("type","2");

                Http(HttpMethod.GET,"api/shop",hashMap,3);
                tv_ar.setTextColor(getResources().getColor(R.color.black_deep));
                tv_distance.setTextColor(getResources().getColor(R.color.black_deep));
                tv_score.setTextColor(getResources().getColor(R.color.btn_blue_normal));
                break;

        }

    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            hashMap1.put("type","1");
            hashMap1.put("lat",String.valueOf(bdLocation.getLatitude()));
            hashMap1.put("lng",String.valueOf(bdLocation.getLongitude()));
            Log.e("tag", "onReceiveLocation: "+hashMap1.toString() );
        }
    }
}
