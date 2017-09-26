package com.taotaohai.fragment;


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
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.myview.Mylistview;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

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
        return view;
    }

    private void initview() {
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
        initdata();//初始化数据

        onClick(v1);
//        views.get(0).setBackgroundResource(R.drawable.bac_class_left);
        ListView list = (ListView) view.findViewById(R.id.listview);
        list.setAdapter(new ListAdapter());
    }

    @Override
    public void onClick(View v) {
        initclass();
        switch (v.getId()) {
            case R.id.rela1:

                views.get(0).setBackgroundResource(R.drawable.bac_class_left);
                texts.get(0).setTextColor(getResources().getColor(R.color.white));
                position = -1;

                break;
            case R.id.rela2:
                views.get(1).setBackgroundColor(getResources().getColor(R.color.them));
                texts.get(1).setTextColor(getResources().getColor(R.color.white));
                if (position == 1) {
                    images.get(1).setImageResource(R.mipmap.clickbutton);
                    position = -1;
                } else {
                    images.get(0).setImageResource(R.mipmap.clickup);
                    position = 1;
                }
                break;
            case R.id.rela3:
                views.get(2).setBackgroundResource(R.drawable.bac_class_right);
                texts.get(2).setTextColor(getResources().getColor(R.color.white));
                if (position == 2) {
                    images.get(3).setImageResource(R.mipmap.clickbutton);
                    position = -1;
                } else {
                    images.get(2).setImageResource(R.mipmap.clickup);
                    position = 2;
                }
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
            return 10;
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
            }
            text1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclick2(list, text1);
                }
            });
            if (position != 0) {
                for (int i = 0; i < 3; i++) {
                    View view1 = getActivity().getLayoutInflater().inflate(R.layout.item_class_list2, null);
                    final TextView text = (TextView) view1.findViewById(R.id.text);
                    text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onclick(text, text1);
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

        List<String> lista = Arrays.asList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        CommonAdapter adapter = new CommonAdapter<String>(getActivity(), R.layout.item_class_recycle, lista) {
            @Override
            protected void convert(ViewHolder holder, final String s, int position) {


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
}
