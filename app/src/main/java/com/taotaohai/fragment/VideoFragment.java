package com.taotaohai.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.andview.refreshview.XRefreshView;
import com.taotaohai.R;
import com.taotaohai.activity.Login;
import com.taotaohai.activity.MessageActivity;
import com.taotaohai.activity.Search;
import com.taotaohai.activity.ShopCarActivity;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.ShopCarNum;
import com.taotaohai.bean.Video;
import com.taotaohai.myview.BadgeView;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.taotaohai.widgets.MultipleStatusView;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.xutils.http.HttpMethod;

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
    private ImageView v_car_image;
    private RelativeLayout screlativeLayout;
    private RelativeLayout msrelativeLayout;
    NiceVideoPlayer niceVideoPlayer;

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
        get("/api/shopCar/shop_car_num",20);
        inithttpdata();
    }

    void inithttpdata() {
        mMsvLayout.loading();
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
        if(postcode==999){
            startActivity(new Intent(getActivity(), ShopCarActivity.class));
        }
        if(postcode==998){
            startActivity(new Intent(getActivity(), MessageActivity.class));
        }

        if(postcode==20){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(data,ShopCarNum.class);
            if(shopCarNum.getData()!="0"){
                BadgeView badgeView = new BadgeView(getActivity(),screlativeLayout);
                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(9);// 设置文本大小
                badgeView.setText(shopCarNum.getData()); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }

        }

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
        if(postcode==998||postcode==999){

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_video, container, false);
//        addview(inflater, view, container);
        initview(view);
        inithttp();
        return view;
    }

    private void initview(View view) {
        view.findViewById(R.id.search_).setOnClickListener(this);
        screlativeLayout = (RelativeLayout) view.findViewById(R.id.vrelativeLayout2);
        screlativeLayout.setOnClickListener(this);
        v_car_image = (ImageView) view.findViewById(R.id.v_car_image);

        msrelativeLayout = (RelativeLayout) view.findViewById(R.id.vrelativeLayout3);
        msrelativeLayout.setOnClickListener(this);
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

                niceVideoPlayer = holder.getView(R.id.niceplayer);
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

    //切换fragment走的方法

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(niceVideoPlayer.isPlaying()){

                niceVideoPlayer.pause();

        }
        get("/api/shopCar/shop_car_num",20);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_:
                key = "";
                startActivity(new Intent(getActivity(), Search.class));
                break;


            case R.id.vrelativeLayout3:
                get("api/user/",998);

                break;

            case R.id.vrelativeLayout2:
                get("api/user/",999);

                break;
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        pageIndex = 0;
        pageSize = 10;
        video = null;
    }
}
