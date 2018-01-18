package com.taotaohai.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.google.gson.JsonObject;
import com.taotaohai.R;
import com.taotaohai.activity.GoodsDetialActivity;
import com.taotaohai.activity.Login;
import com.taotaohai.activity.MessageActivity;
import com.taotaohai.activity.ShopCarActivity;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.ClassGoods;
import com.taotaohai.bean.ClassPage;
import com.taotaohai.bean.ShopCarNum;
import com.taotaohai.myview.BadgeView;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.taotaohai.widgets.MultipleStatusView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.xutils.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView text_click;
    private TextView first_click;
    private List<View> views;
    private List<TextView> texts;
    private List<ImageView> images;
    int position = -1;
    private XRefreshView xrefreshview;
    private RecyclerView recyclerView;
    private ListView list;
    private ClassPage classPage;
    private ClassGoods classGoods;
    private CommonAdapter adapter;
    private String ordertype = "gmt_create";
    private String ordertype2 = "desc";
    private int pageIndex = 0;
    private int pageSize = 100;
    private String id = "-1";
    private ImageView class_car_image;
    private RelativeLayout rela_shopcar;


    private MultipleStatusView mMsvLayout;

    private static ClassFragment fragment;

    public static ClassFragment newInstance() {
        return new ClassFragment();
    }

    public ClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_class, container, false);
        initview();
        inithttp();
        return view;
    }

    @Override
    public void inithttp() {
        super.inithttp();
        mMsvLayout.loading();
        get("api/goods/class", 0);
        get("/api/shopCar/shop_car_num",20);
        gohttp();
    }

    @Override
    public void onFinished(int code) {
        super.onFinished(code);
        if (code == 0 && classPage == null) {
            get("api/goods/class", 0);
            return;
        }
        if (code == 1 && classGoods == null) {
            gohttp();
            return;
        }
        if (classPage != null && classGoods != null) {
            mMsvLayout.content();
        }


    }

    void gohttp() {
        has.clear();
        has.put("sorts[0].name", ordertype);
        has.put("sorts[0].order", ordertype2);
        has.put("paging", "0");
//        has.put("pageIndex", String.valueOf(pageIndex));
//        has.put("pageSize", String.valueOf(pageSize));
        Http(HttpMethod.GET, "api/goods/type/" + id, has, 1);
    }

    @Override
    public void onError(Throwable ex, int postcode) {
        if(postcode==99||postcode==999||postcode==998){
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

    @Override
    public void onSuccess(String data, int postcode) {
        super.onSuccess(data, postcode);
        if(postcode==999){
            startActivity(new Intent(getActivity(), ShopCarActivity.class));

        }
        if (postcode==998){
            startActivity(new Intent(getActivity(), MessageActivity.class));
        }
        if(postcode==20){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(data,ShopCarNum.class);
            if(shopCarNum.getData()!="0"){
                BadgeView badgeView = new BadgeView(getActivity(),rela_shopcar);
                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(9);// 设置文本大小
                badgeView.setText(shopCarNum.getData()); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }

        }

        if (postcode == 0) {

            classPage = util.getgson(data, ClassPage.class);
            if (classPage.getSuccess()) {
                list = (ListView) view.findViewById(R.id.listview);
                list.setAdapter(new ListAdapter());
            }
        }
        if (postcode == 1) {
            classGoods = util.getgson(data, ClassGoods.class);
            if (classGoods.getSuccess()) {
                initdata();
            }
        }
        if (postcode == 99) {
            if (util.isSuccess(data)) {
//                View view = getActivity().getLayoutInflater().inflate(R.layout.toast, null);
//                CustomToast.showDiverseToast(getActivity(), view, Gravity.TOP);
                addtocar(image_photo, imag_photo2);
            } else {
                showToast("加入商品失败，商品数量不足");
            }
        }


    }

    private void initview() {
        class_car_image = (ImageView) view.findViewById(R.id.class_car_image);
        mMsvLayout = (MultipleStatusView) view.findViewById(R.id.msv_layout);
//        mMsvLayout.setOnClickListener((l) -> {
//            if (mMsvLayout.getViewStatus() == mMsvLayout.STATUS_ERROR) {
//                inithttp();
//            }
//        });
        view.findViewById(R.id.rela_message).setOnClickListener(this);
        rela_shopcar = (RelativeLayout) view.findViewById(R.id.rela_shopcar);
        rela_shopcar.setOnClickListener(this);
        imag_photo2 = (ImageView) view.findViewById(R.id.imag_photo2);
        View v1 = view.findViewById(R.id.rela1);
        View v2 = view.findViewById(R.id.rela2);
        View v3 = view.findViewById(R.id.rela3);
        views = Arrays.asList(v1, v2, v3);//rela布局
        v1.setOnClickListener(this);
        v2.setOnClickListener(this);
        v3.setOnClickListener(this);

        TextView tv1 = (TextView) view.findViewById(R.id.tv_1);
        TextView tv2 = (TextView) view.findViewById(R.id.tv_2);
        TextView tv3 = (TextView) view.findViewById(R.id.tv_3);
        texts = Arrays.asList(tv1, tv2, tv3);//textview布局
        ImageView image1 = (ImageView) view.findViewById(R.id.image1);
        ImageView image2 = (ImageView) view.findViewById(R.id.image2);
        ImageView image3 = (ImageView) view.findViewById(R.id.image3);
        ImageView image4 = (ImageView) view.findViewById(R.id.image4);
        images = Arrays.asList(image1, image2, image3, image4);//image布局

        xrefreshview = (XRefreshView) view.findViewById(R.id.xrefreshview);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
        xrefreshview.setPullLoadEnable(true);
        recyclerView.setHasFixedSize(true);//item改变的时候recycleview不会重新计算高度
//        initdata();//初始化数据

        onClick(v1);
//        views.get(0).setBackgroundResource(R.drawable.bac_class_left);

    }

    @Override
    public void onClick(View v) {
        initclass();
        switch (v.getId()) {
            case R.id.rela_shopcar:
                get("api/user/",999);
            break;

            case R.id.rela_message:
                get("api/user/",998);

            case R.id.rela1:

                views.get(0).setBackgroundResource(R.drawable.bac_class_left);
                texts.get(0).setTextColor(getResources().getColor(R.color.white));
                position = -1;
                ordertype = "gmt_create";
                gohttp();
                break;
            case R.id.rela2:
                views.get(1).setBackgroundColor(getResources().getColor(R.color.them));
                texts.get(1).setTextColor(getResources().getColor(R.color.white));
                if (position == 1) {
                    images.get(1).setImageResource(R.mipmap.clickbutton);
                    position = -1;
                    ordertype2 = "desc";
                    ordertype = "price";

                } else {
                    images.get(0).setImageResource(R.mipmap.clickup);
                    position = 1;
                    ordertype = "price";
                    ordertype2 = "asc";
                }
                gohttp();
                break;
            case R.id.rela3:
                views.get(2).setBackgroundResource(R.drawable.bac_class_right);
                texts.get(2).setTextColor(getResources().getColor(R.color.white));
                if (position == 2) {
                    images.get(3).setImageResource(R.mipmap.clickbutton);
                    position = -1;
                    ordertype2 = "desc";
                    ordertype = "sale_volume";
                } else {
                    images.get(2).setImageResource(R.mipmap.clickup);
                    position = 2;
                    ordertype2 = "asc";
                    ordertype = "sale_volume";
                }
                gohttp();
                break;
        }

    }


    private void initclass() {
        for (int i = 0; i < 3; i++) {
            views.get(i).setBackground(null);
            texts.get(i).setTextColor(getResources().getColor(R.color.text_black));
        }
        images.get(0).setImageResource(R.mipmap.unclickup);
        images.get(1).setImageResource(R.mipmap.unclickbuttom);
        images.get(2).setImageResource(R.mipmap.unclickup);
        images.get(3).setImageResource(R.mipmap.unclickbuttom);
    }

    class ListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return classPage.getData().size() + 1;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = getActivity().getLayoutInflater().inflate(R.layout.item_class_list, null);
            } else {
                view = convertView;
            }
            final TextView text1 = (TextView) view.findViewById(R.id.text1);

            final LinearLayout list = (LinearLayout) view.findViewById(R.id.list);
            if (position == 0) {
                first_click = text1;
                first_click.setTextColor(getResources().getColor(R.color.white));
                first_click.setBackgroundColor(getResources().getColor(R.color.them));
            } else {
                text1.setText(classPage.getData().get(position - 1).getClassName());
            }
            text1.setOnClickListener(v -> {
                onclick2(list, text1);
                if (position == 0) {
                    id = "-1";
                } else {
                    id = classPage.getData().get(position - 1).getId();
                }
                gohttp();
            });
            if (position != 0) {
                for (int i = 0; i < classPage.getData().get(position - 1).getChildren().size(); i++) {
                    View view1 = getActivity().getLayoutInflater().inflate(R.layout.item_class_list2, null);
                    final TextView text = (TextView) view1.findViewById(R.id.text);
                    text.setText(classPage.getData().get(position - 1).getChildren().get(i).getClassName());
                    final int finalI = i;
                    text.setOnClickListener(v -> {
                        id = classPage.getData().get(position - 1).getChildren().get(finalI).getId();
                        onclick(text, text1);
                        gohttp();
                    });
                    list.addView(view1);
                }
            }
            return view;
        }
    }

    void onclick(TextView text, TextView text1) {
        first_click.setTextColor(getResources().getColor(R.color.text_black));
        first_click.setBackgroundColor(getResources().getColor(R.color.class_bac));
//        first_click = text1;
//        first_click.setTextColor(getResources().getColor(R.color.white));
//        first_click.setBackgroundColor(getResources().getColor(R.color.them));

        if (text_click != null) {
            text_click.setTextColor(getResources().getColor(R.color.text_black));
        }
        text_click = text;
        text_click.setTextColor(getResources().getColor(R.color.them));
    }

    void onclick2(View list, TextView text1) {
        if (list.isShown()) {
            list.setVisibility(View.GONE);
        } else {
            list.setVisibility(View.VISIBLE);
        }
        first_click.setTextColor(getResources().getColor(R.color.text_black));
        first_click.setBackgroundColor(getResources().getColor(R.color.class_bac));
        first_click = text1;
        first_click.setTextColor(getResources().getColor(R.color.white));
        first_click.setBackgroundColor(getResources().getColor(R.color.them));
        if (text_click != null) {//清空小弟变色
            text_click.setTextColor(getResources().getColor(R.color.text_black));
        }
    }

    ImageView image_photo;
    ImageView imag_photo2;

    private void initdata() {
        adapter = new CommonAdapter<ClassGoods.Data>(getActivity(), R.layout.item_class_recycle, classGoods.getData2().getData()) {
            @Override
            protected void convert(ViewHolder holder, final ClassGoods.Data data, int position) {
                holder.setOnClickListener(R.id.image_car, v -> {
                    image_photo = holder.getView(R.id.image_car);
                    JsonObject object = new JsonObject();
                    object.addProperty("goodsId", data.getId());
                    object.addProperty("count", "1");
                    Http(HttpMethod.POST, "api/shopCar", object.toString(), 99);
                    get("/api/shopCar/shop_car_num",20);
                });
                holder.setOnClickListener(R.id.rela_all, (l) -> startActivity(new Intent(getActivity(), GoodsDetialActivity.class)
                        .putExtra("id", data.getId())));
                holder.setText(R.id.tv_money, "￥ " + data.getPrice());
                holder.setText(R.id.tv_name, data.getTitle());
                holder.setText(R.id.tv_unit, "/" + data.getUnit());
                holder.setText(R.id.tv_remaker, data.getRemark());
                if (data.getShopInfo() != null)
                    holder.setText(R.id.tv_count, "已购买" + data.getSaleVolume() + "件");
                ImageView imageView = holder.getView(R.id.image_photo);
                if (data.getImagesUrl().size() > 0)
                    GlideUtil.loadImg(data.getImagesUrl().get(0), imageView);
            }
        };

        // 设置静默加载模式
        xrefreshview.setSilenceLoadMore(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        xrefreshview.setPinnedTime(1000);
        xrefreshview.setMoveForHorizontal(true);
        xrefreshview.setPullRefreshEnable(false);
//        recyclerviewAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
//		xRefreshView1.setPullLoadEnable(false);
        //设置静默加载时提前加载的item个数
//        xrefreshview.setPreLoadCount(4);
        xrefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener()

        {
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


}
