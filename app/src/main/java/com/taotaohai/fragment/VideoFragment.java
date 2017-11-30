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
import com.taotaohai.activity.GoodsDetialActivity;
import com.taotaohai.activity.Search;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.Video;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.taotaohai.widgets.MultipleStatusView;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.xutils.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends BaseFragment implements View.OnClickListener {
    String key = "";
    private RecyclerView recyclerView;
    private XRefreshView xrefreshview;
    private MultipleStatusView mMsvLayout;
    private CommonAdapter adapter;
    private Video video;
    int totle = 0;

    private static VideoFragment fragment;

    public static VideoFragment newInstance() {
        if (fragment == null) {
            fragment = new VideoFragment();
        }
        return fragment;
    }

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void inithttp() {
        super.inithttp();
        inithttpdata();
    }

    void inithttpdata() {
        has.clear();
        has.put("pageSize", String.valueOf(pageSize));
        has.put("pageIndex", String.valueOf(pageIndex));
        if (key.length() == 0) {
            Http(HttpMethod.GET, "api/video", has, 0);
        } else {
            has.put("k", key);
            Http(HttpMethod.GET, "api/search/video", has, 0);
        }

    }

    @Override
    public void onSuccess(String data, int postcode) {
        super.onSuccess(data, postcode);

        if (postcode == 0) {
            if (pageIndex == 0) {
                video = util.getgson(data, Video.class);
                if (video.getSuccess()) {
                    initdata();
                    totle = video.getData().getTotal();
                }
                pageIndex += 10;//第多少个
            } else {
                Video video2 = util.getgson(data, Video.class);
                if (video2.getData().getData().size() > 0) {
                    video.getData().getData().addAll(video2.getData().getData());
                    adapter.notifyDataSetChanged();
                    xrefreshview.stopLoadMore();
                } else {
                    xrefreshview.setLoadComplete(true);
                }

            }
        }
    }

    @Override
    public void onFinished(int code) {
        super.onFinished(code);
        xrefreshview.stopRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        initview(view);
        inithttp();
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
//        initdata();//初始化数据


    }

    int pageSize = 10;
    int pageIndex = 0;//第多少个

    private void initdata() {
//        if (adapter != null) {
//            adapter.notifyDataSetChanged();
//            return;
//        }
        adapter = new CommonAdapter<Video.Data>(getActivity(), R.layout.item_list, video.getData().getData()) {
            @Override
            protected void convert(ViewHolder holder, final Video.Data data, int position) {
                NiceVideoPlayer niceVideoPlayer = holder.getView(R.id.niceplayer);
                niceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
                niceVideoPlayer.setUp(data.getVideoAbsUrl(), null);
                TxVideoPlayerController controller = new TxVideoPlayerController(getActivity());
                controller.textsetviso();
                GlideUtil.loadImg(data.getImageAbsUrl(), controller.imageView());
                niceVideoPlayer.setController(controller);
                holder.setText(R.id.tv_count, "播放" + data.getPlayNum() + "次");
                holder.setText(R.id.tv_title, data.getDescribe());
                holder.setText(R.id.tv_updata, data.getUploadTime() + "更新");
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
                pageIndex = 0;//第多少个
                inithttpdata();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                super.onLoadMore(isSilence);
                if (pageIndex >= totle) {
                    xrefreshview.setLoadComplete(true);
                    return;
                }
                inithttpdata();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_:
                key = "";
                startActivityForResult(new Intent(getActivity(), Search.class), 100);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            pageIndex = 0;
            key = data.getStringExtra("key");
            has.clear();
            has.put("k", key);
            has.put("pageSize", String.valueOf(pageSize));
            has.put("pageIndex", String.valueOf(pageIndex));
            Http(HttpMethod.GET, "api/search/video", has, 0);
        }
    }
}
