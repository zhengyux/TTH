package com.taotaohai.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.taotaohai.R;
import com.taotaohai.activity.Search;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.widgets.MultipleStatusView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private XRefreshView xrefreshview;
    private MultipleStatusView mMsvLayout;

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        initview(view);
        return view;
    }

    private void initview(View view) {
        view.findViewById(R.id.search_).setOnClickListener(this);


        mMsvLayout = (MultipleStatusView) view.findViewById(R.id.msv_layout);
        mMsvLayout.loading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMsvLayout.content();
            }
        }, 1000);
        xrefreshview = (XRefreshView) view.findViewById(R.id.xrefreshview);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
        xrefreshview.setPullLoadEnable(true);
        recyclerView.setHasFixedSize(true);//item改变的时候recycleview不会重新计算高度
        initdata();//初始化数据




    }

    private void initdata() {

        List<String> lista = Arrays.asList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        CommonAdapter adapter = new CommonAdapter<String>(getActivity(), R.layout.item_list, lista) {
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
        switch (v.getId()){
            case R.id.search_:
                startActivityForResult(new Intent(getActivity(), Search.class),0);
        }
    }
}
