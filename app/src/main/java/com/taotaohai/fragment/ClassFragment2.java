package com.taotaohai.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.google.gson.JsonObject;
import com.taotaohai.R;
import com.taotaohai.activity.GoodsDetialActivity;
import com.taotaohai.activity.MessageActivity;
import com.taotaohai.activity.ShopCarActivity;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.ClassPage;
import com.taotaohai.bean.Seach;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.xutils.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassFragment2 extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView text_click;
    private TextView first_click;
    private List<View> views;
    private List<TextView> texts;
    int position = 0;
    private XRefreshView xrefreshview;
    private RecyclerView recyclerView;
    private ClassPage classPage;
    private Seach seach;
    private CommonAdapter adapter;
    private int pageIndex = 0;
    private int pageSize = 100;
    private String id = "-1";
    String ordertype = "0";

    private static ClassFragment2 fragment;
    private EditText edit_search;

    public static ClassFragment2 newInstance() {
        return new ClassFragment2();
    }

    public ClassFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_class2, container, false);
        initview();
        inithttp();
        return view;
    }

    @Override
    public void inithttp() {
        super.inithttp();
        gohttp();
    }

    void gohttp() {

        has.clear();
        has.put("goodsName", edit_search.getText().toString());
        has.put("type", ordertype);
        has.put("pageSize", String.valueOf(pageSize));
        has.put("pageIndex", String.valueOf(pageIndex));
        Http(HttpMethod.GET, "api/search/goods_name", has, 1);
    }

    @Override
    public void onSuccess(String data, int postcode) {
        super.onSuccess(data, postcode);

        if (postcode == 1) {
            seach = util.getgson(data, Seach.class);
            if (seach.getSuccess()) {
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

    ImageView image_photo, imag_photo2;

    private void initview() {
        imag_photo2 = (ImageView) view.findViewById(R.id.imag_photo2);

        edit_search = (EditText) view.findViewById(R.id.edit_search);
        edit_search.setText(getActivity().getIntent().getStringExtra("name"));
        edit_search.setSelection(edit_search.getText().length());
        View v1 = view.findViewById(R.id.rela1);
        View v2 = view.findViewById(R.id.rela2);
        View v3 = view.findViewById(R.id.rela3);
        views = Arrays.asList(v1, v2, v3);//rela布局
        v1.setOnClickListener(this);
        v2.setOnClickListener(this);
        v3.setOnClickListener(this);

        view.findViewById(R.id.rela_message).setOnClickListener(this);
        view.findViewById(R.id.rela_shopcar).setOnClickListener(this);
        view.findViewById(R.id.back).setOnClickListener(this);

        TextView tv1 = (TextView) view.findViewById(R.id.tv_1);
        TextView tv2 = (TextView) view.findViewById(R.id.tv_2);
        TextView tv3 = (TextView) view.findViewById(R.id.tv_3);
        texts = Arrays.asList(tv1, tv2, tv3);//textview布局

        xrefreshview = (XRefreshView) view.findViewById(R.id.xrefreshview);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
        xrefreshview.setPullLoadEnable(true);
        recyclerView.setHasFixedSize(true);//item改变的时候recycleview不会重新计算高度
//        initdata();//初始化数据

        onClick(v1);
//        views.get(0).setBackgroundResource(R.drawable.bac_class_left);
        edit_search.setOnEditorActionListener((v, actionId, event) -> {
            /*判断是否是“GO”键*/
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                /*隐藏软键盘*/
                InputMethodManager imm = (InputMethodManager) v
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(
                            v.getApplicationWindowToken(), 0);
                }
                gohttp();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {
        initclass();
        switch (v.getId()) {
            case R.id.rela1:
                if (position != 0) {
                    views.get(0).setBackgroundColor(getResources().getColor(R.color.them));
                    texts.get(0).setTextColor(getResources().getColor(R.color.white));
                    position = 0;
                    ordertype = "0";
                    gohttp();
                }
                break;
            case R.id.rela2:
                views.get(1).setBackgroundColor(getResources().getColor(R.color.them));
                texts.get(1).setTextColor(getResources().getColor(R.color.white));
                if (position != 1) {
                    position = 1;
                    ordertype = "1";
                    gohttp();
                }
//
                break;
            case R.id.rela3:
                views.get(2).setBackgroundColor(getResources().getColor(R.color.them));
                texts.get(2).setTextColor(getResources().getColor(R.color.white));
                if (position != 2) {
                    position = 2;
                    ordertype = "2";
                    gohttp();
                }
                break;
            case R.id.rela_message:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.rela_shopcar:
                startActivity(new Intent(getActivity(), ShopCarActivity.class));
                break;
            case R.id.back:
                getActivity().finish();
                break;
        }

    }


    private void initclass() {
        for (int i = 0; i < 3; i++) {
            views.get(i).setBackground(null);
            texts.get(i).setTextColor(getResources().getColor(R.color.text_black));
        }
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
            text1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0)
                        return;
                    onclick2(list, text1);
                }
            });
            if (position != 0) {
                for (int i = 0; i < classPage.getData().get(position - 1).getChildren().size(); i++) {
                    View view1 = getActivity().getLayoutInflater().inflate(R.layout.item_class_list2, null);
                    final TextView text = (TextView) view1.findViewById(R.id.text);
                    text.setText(classPage.getData().get(position - 1).getChildren().get(i).getClassName());
                    final int finalI = i;
                    text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            id = classPage.getData().get(position - 1).getChildren().get(finalI).getId();
                            onclick(text, text1);
//                            gohttp();
                        }
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
        first_click = text1;
        first_click.setTextColor(getResources().getColor(R.color.white));
        first_click.setBackgroundColor(getResources().getColor(R.color.them));

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
        if (position == 0 && text_click != null) {//清空小弟变色
            text_click.setTextColor(getResources().getColor(R.color.text_black));
        }
    }

    private void initdata() {

        adapter = new CommonAdapter<Seach.Data>(getActivity(), R.layout.item_class_recycle, seach.getData()) {
            @Override
            protected void convert(ViewHolder holder, final Seach.Data data, int position) {
                holder.setOnClickListener(R.id.image_car, v -> {
                    image_photo = holder.getView(R.id.image_car);
                    JsonObject object = new JsonObject();
                    object.addProperty("goodsId", data.getId());
                    object.addProperty("count", "1");
                    Http(HttpMethod.POST, "api/shopCar", object.toString(), 99);
                });
                holder.setText(R.id.tv_money, "￥：" + data.getPrice());
                holder.setText(R.id.tv_name, data.getTitle());
                holder.setText(R.id.tv_unit, "/" + data.getUnit());
                holder.setText(R.id.tv_remaker, data.getRemark());
                if (data.getShopInfo() != null) {
                    holder.setText(R.id.tv_count, "已购买" + data.getSaleVolume() + "件");
                }
                holder.setOnClickListener(R.id.rela_all, (l) -> startActivity(new Intent(getActivity(), GoodsDetialActivity.class)
                        .putExtra("id", data.getId())));
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
}
