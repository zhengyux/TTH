package com.taotaohai.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.taotaohai.myview.XListView;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import org.xutils.http.HttpMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends BaseFragment implements View.OnClickListener,XListView.IXListViewListener {
    String key = "";

    private Video video;
    int totle = 0;

    private RelativeLayout screlativeLayout;
    private RelativeLayout msrelativeLayout;
    private Myadapter myadapter;
    private XListView xListView;
    CallBackValue callBackValue;
    BadgeView badgeView ;
    BadgeView badgeView2 ;
    int pageSize = 10;
    int pageIndex = 0;//第多少个


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
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackValue =(CallBackValue) getActivity();
    }

    @Override
    public void inithttp() {
        super.inithttp();
        get("/api/shopCar/shop_car_num",20);
        get("/api/message/notReadList/0",50);
        get("/api/message/notReadList/1",51);
        inithttpdata();
    }

    void inithttpdata() {
//        mMsvLayout.loading();
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

                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(9);// 设置文本大小
                badgeView.setText(shopCarNum.getData()); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }else {
                badgeView.hide();
            }

        }
        if(postcode==50){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(data,ShopCarNum.class);
            if(shopCarNum.getData()!="0"){

                badgeView2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView2.setTextSize(6);// 设置文本大小
                badgeView2.setText(""); // 设置要显示的文本
                badgeView2.show();// 将角标显示出来
            }else {
                badgeView2.hide();
            }

        }
        if(postcode==51){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(data,ShopCarNum.class);
            if(shopCarNum.getData()!="0"){
                badgeView2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView2.setTextSize(6);// 设置文本大小
                badgeView2.setText(""); // 设置要显示的文本
                badgeView2.show();// 将角标显示出来
            }else {
                badgeView2.hide();
            }

        }

        if (postcode == 0) {
            if (pageIndex == 0) {
                video = util.getgson(data, Video.class);
                if (video.getSuccess()) {
  //                  initdata();
                    xListView.setAdapter(myadapter);
                    totle = video.getData().getTotal();
                }
                pageIndex += 1;//第多少个
            } else {
                Video video2 = util.getgson(data, Video.class);
                if (video2.getData().getData().size() > 0) {
         //           video.getData().getData().addAll(video2.getData().getData());
                    video=video2;
                    myadapter.notifyDataSetChanged();
                    xListView.stopLoadMore();
                    xListView.stopRefresh();
                } else {
                    myadapter.notifyDataSetChanged();
                    xListView.stopLoadMore();
                    xListView.stopRefresh();
                }
            }
        }
    }

    @Override
    public void onFinished(int code) {
        super.onFinished(code);
        xListView.stopRefresh();
        if (video == null) {

        } else if (video.getData().getData().size() == 0) {

        } else {

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
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_video, container, false);
        initview(view);
        inithttp();
        callBackValue.SendMessageValue(1);
        badgeView = new BadgeView(getActivity(),screlativeLayout);
        badgeView2 = new BadgeView(getActivity(),msrelativeLayout);
        return view;
    }


    private void initview(View view) {
        xListView = (XListView) view.findViewById(R.id.xlistview);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        xListView.setRefreshTime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ").format(new Date(System.currentTimeMillis()) ));
        myadapter = new Myadapter();

        view.findViewById(R.id.search_).setOnClickListener(this);
        screlativeLayout = (RelativeLayout) view.findViewById(R.id.vrelativeLayout2);
        screlativeLayout.setOnClickListener(this);


        msrelativeLayout = (RelativeLayout) view.findViewById(R.id.vrelativeLayout3);
        msrelativeLayout.setOnClickListener(this);
        isWifi();

    }




    //切换fragment走的方法

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        callBackValue.SendMessageValue(1);
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
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

    private  void isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {

            return;
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("温馨提示");
        dialog.setMessage("当前网络不是WIFI状态");
        dialog.setNegativeButton("确定", (dialog1, which) -> {

        });
        dialog.setNeutralButton("取消", (dialog1, which) -> {
        });
        dialog.show();
    }

    @Override
    public void onRefresh() {
        pageIndex = 0;//第多少个
        xListView.setRefreshTime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ").format(new Date(System.currentTimeMillis()) ));
        inithttpdata();
    }

    @Override
    public void onLoadMore() {
        pageIndex = video.getData().getData().size();//第多少个
        inithttpdata();
        xListView.stopLoadMore();
    }


    class Myadapter extends BaseAdapter{


        @Override
        public int getCount() {
            return video.getData().getData().size();
        }

        @Override
        public Object getItem(int i) {
            return video.getData().getData().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHolder viewHolder ;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_list, null);
                viewHolder.niceVideoPlayer = (NiceVideoPlayer) view.findViewById(R.id.niceplayer);
                viewHolder.tv_count = (TextView) view.findViewById(R.id.tv_count);
                viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
                viewHolder.tv_updata = (TextView) view.findViewById(R.id.tv_updata);
                viewHolder.tv_play = (TextView) view.findViewById(R.id.tv_play);
                viewHolder.videoshare= (TextView) view.findViewById(R.id.videoshare);




                view.setTag(viewHolder);
            }else {

                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.tv_play.setOnClickListener((l)->{
                viewHolder.niceVideoPlayer.start();
                get("api/video/player/" + video.getData().getData().get(i).getId(), 15);
                viewHolder.tv_play.setClickable(false);
            });
            viewHolder.niceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
            viewHolder.niceVideoPlayer.setUp(video.getData().getData().get(i).getVideoAbsUrl(), null);
            TxVideoPlayerController controller = new TxVideoPlayerController(getActivity());
            controller.textsetviso();
            GlideUtil.loadImg(video.getData().getData().get(i).getImageAbsUrl(), controller.imageView());
            viewHolder.niceVideoPlayer.setController(controller);
            viewHolder.tv_count.setText("播放" + video.getData().getData().get(i).getPlayNum() + "次");
            viewHolder.tv_title.setText(video.getData().getData().get(i).getDescribe());
            viewHolder.tv_updata.setText(video.getData().getData().get(i).getUploadTime() + "更新");
            viewHolder.videoshare.setOnClickListener(l ->{showShare(i);} );
            return view;
        }


        class ViewHolder {
            NiceVideoPlayer niceVideoPlayer;
            TextView tv_count;
            TextView tv_title;
            TextView tv_updata;
            TextView tv_play;
            TextView videoshare;
        }

        private void showShare(int i) {

            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();
            // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
            //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks.setTitle("淘淘海视频分享");
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl("http://www.taotaohai.com/video/share/"+video.getData().getData().get(i).getId()+".html");
            // text是分享文本，所有平台都需要这个字段
            oks.setText(video.getData().getData().get(i).getDescribe());
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
          //  oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
            oks.setImageUrl(video.getData().getData().get(i).getImageAbsUrl());
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl("http://www.taotaohai.com/video/share/"+video.getData().getData().get(i).getId()+".html");
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment("淘淘海");
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite("淘淘海");
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl("http://www.taotaohai.com/video/share/"+video.getData().getData().get(i).getId()+".html");
            // 启动分享GUI
            oks.show(getActivity());
        }

    }
    //定义一个回调接口
    public interface CallBackValue{
        public void SendMessageValue(int intValue);
    }

    @Override
    public void onResume() {
        super.onResume();
        get("/api/shopCar/shop_car_num",20);
        get("/api/message/notReadList/0",50);
        get("/api/message/notReadList/1",51);
    }
}
