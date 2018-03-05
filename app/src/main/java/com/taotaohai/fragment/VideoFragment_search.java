package com.taotaohai.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.andview.refreshview.XRefreshView;
import com.taotaohai.R;
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

import static com.taotaohai.activity.Search.key;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment_search extends BaseFragment {
    private RecyclerView recyclerView;
    private XRefreshView xrefreshview;
    private MultipleStatusView mMsvLayout;
    private CommonAdapter adapter;
    private Video video;
    int totle = 0;

    private static VideoFragment_search fragment;

    public static VideoFragment_search newInstance() {
        if (fragment == null) {
            fragment = new VideoFragment_search();
        }
        return fragment;
    }

    public VideoFragment_search() {
        // Required empty public constructor
    }

    @Override
    public void inithttp() {
        super.inithttp();
        inithttpdata();
    }

    void inithttpdata() {
        mMsvLayout.loading();
        has.clear();
        has.put("pageSize", String.valueOf(pageSize));
        has.put("pageIndex", String.valueOf(pageIndex));
        has.put("k", key);
        Http(HttpMethod.GET, "api/search/video", has, 0);
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
                pageIndex += 1;//第多少个
            } else {
                Video video2 = util.getgson(data, Video.class);
                if (video2.getData().getData().size() > 0) {
                    video=video2;
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
        if (video == null) {
            mMsvLayout.error();
        } else if (video.getData().getData().size() == 0) {
            mMsvLayout.empty();
        } else {
            mMsvLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(Throwable ex, int postcode) {
        super.onError(ex, postcode);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_video_search, container, false);
//        addview(inflater, view, container);
        initview(view);
//        inithttp();
        return view;
    }

    private void initview(View view) {
        mMsvLayout = (MultipleStatusView) view.findViewById(R.id.msv_layout);
        mMsvLayout.setOnClickListener((l) -> {
            if (mMsvLayout.getViewStatus() == mMsvLayout.STATUS_ERROR) {
                inithttp();
            }
        });

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
                holder.setOnClickListener(R.id.tv_play, (l) -> {
                    if (niceVideoPlayer.isPlaying()) return;
//                    controller.onClick(null);
                    holder.setVisible(R.id.tv_play, false);
                    niceVideoPlayer.start();
                    get("api/video/player/" + data.getId(), 15);
                });

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


    public void gotosearch() {
        pageIndex = 0;
        has.clear();
        has.put("k", key);
        has.put("pageSize", String.valueOf(pageSize));
        has.put("pageIndex", String.valueOf(pageIndex));
        Http(HttpMethod.GET, "api/search/video", has, 0);
    }
}
