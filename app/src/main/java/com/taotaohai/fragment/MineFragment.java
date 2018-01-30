package com.taotaohai.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.taotaohai.R;
import com.taotaohai.activity.AddressManeger;
import com.taotaohai.activity.Login;
import com.taotaohai.activity.MessageActivity;
import com.taotaohai.activity.MyBook;
import com.taotaohai.activity.MyDataActivity;
import com.taotaohai.activity.MyfocusActivity;
import com.taotaohai.activity.ReFundListActivity;
import com.taotaohai.activity.Regist;
import com.taotaohai.activity.SetActivity;
import com.taotaohai.activity.ShopCarActivity;
import com.taotaohai.activity.ShopIn;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.LoginBean;
import com.taotaohai.bean.ShopCarNum;
import com.taotaohai.myview.BadgeView;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.MD5Utils;
import com.taotaohai.util.SPUtils;
import com.taotaohai.util.util;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView image_photo;
    private View tv_login, tv_regist;
    private TextView tv_name;
    private ImageView mine_car_image;
    private RelativeLayout rela_shopcar;
    BadgeView badgeView ;

    private static MineFragment fragment;

    public static MineFragment newInstance() {
        if (fragment == null) {
            fragment = new MineFragment();
        }
        return fragment;
    }

    public MineFragment() {
        // Required empty public constructor
    }

    private LoginBean loginBean = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mine, container, false);
        // Inflate the layout for this fragment
        initview();
        loginBean = null;
//        inithttp();
        badgeView = new BadgeView(getActivity(),rela_shopcar);
        return view;
    }

    public void onNewIntent(Intent intent) {
        loginBean = (LoginBean) intent.getSerializableExtra("login");
        if (loginBean != null)
            initdata();
    }

    @Override
    public void onStart() {
        super.onStart();
        inithttp();
    }

    @Override
    public void inithttp() {
        super.inithttp();
        get("/api/shopCar/shop_car_num",20);
        has.put("username", (String) SPUtils.get(getActivity(), "username", ""));
        has.put("password", (String) SPUtils.get(getActivity(), "password", ""));
        has.put("loginType", "1");
        post("api/auth/login", has, 0);
    }

    @Override
    public void onSuccess(String data, int postcode) {
        super.onSuccess(data, postcode);

        if(postcode==20){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(data,ShopCarNum.class);
            if(shopCarNum.getData()!="0"){

                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(9);// 设置文本大小
                badgeView.setText(shopCarNum.getData()); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }

        }


        loginBean = util.getgson(data, LoginBean.class);
        if (loginBean.getSuccess()) {
            initdata();
            EMClient.getInstance().login(loginBean.getData().getId(), MD5Utils.md5Password((String) SPUtils.get(getActivity(), "password", null)), new EMCallBack() {

                @Override
                public void onSuccess() {

                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @SuppressLint("WrongConstant")
                @Override
                public void onError(int code, String error) {
                }
            });
        }

    }

    @Override
    public void onError(Throwable ex, int postcode) {

    }

    private void initview() {
        mine_car_image = (ImageView) view.findViewById(R.id.mine_car_image);
        image_photo = (ImageView) view.findViewById(R.id.image_photo);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        rela_shopcar = (RelativeLayout) view.findViewById(R.id.rela_shopcar);
        rela_shopcar.setOnClickListener(this);
        view.findViewById(R.id.rela1).setOnClickListener(this);
        view.findViewById(R.id.rela2).setOnClickListener(this);
        view.findViewById(R.id.rela3).setOnClickListener(this);
        view.findViewById(R.id.rela4).setOnClickListener(this);
        view.findViewById(R.id.rela5).setOnClickListener(this);
        view.findViewById(R.id.rela6).setOnClickListener(this);
        view.findViewById(R.id.rela21).setOnClickListener(this);
        view.findViewById(R.id.rela22).setOnClickListener(this);
        view.findViewById(R.id.rela23).setOnClickListener(this);
        view.findViewById(R.id.rela24).setOnClickListener(this);
        view.findViewById(R.id.rela25).setOnClickListener(this);
        tv_login = view.findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);
        tv_regist = view.findViewById(R.id.tv_regist);
        tv_regist.setOnClickListener(this);
        image_photo.setOnClickListener(this);
        view.findViewById(R.id.rela_message).setOnClickListener(this);

        view.findViewById(R.id.allbooks).setOnClickListener(this);
    }

    private void initdata() {
        GlideUtil.loadRoundImg(loginBean.getData().getExt().getAvatarUrl(), image_photo);//偶然得到的glid再次封装，，贼好用
        tv_login.setVisibility(View.GONE);
        tv_regist.setVisibility(View.GONE);
        tv_name.setVisibility(View.VISIBLE);
        tv_name.setText(loginBean.getData().getName());

    }

    @Override
    public void onClick(View v) {
//        if (mine == null && v.getId() != R.id.tv_regist) {///如果没有登录，先登录
//            startActivityForResult(new Intent(getActivity(), Login.class), 10);
//            return;
//        }
        if (!(v.getId() == R.id.tv_regist || v.getId() == R.id.tv_login)) {
            if (loginBean == null) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("未登录");
                dialog.setMessage("是否进入登录页登录?");
                dialog.setNegativeButton("前往", (dialog1, which) -> {
                    startActivity(new Intent(getActivity(), Login.class));
                });
                dialog.setNeutralButton("取消", (dialog1, which) -> {
                });
                dialog.show();
                return;
            }

        }
        switch (v.getId()) {
            case R.id.rela1:
                break;
            case R.id.rela2:
                startActivity(new Intent(getActivity(), MyfocusActivity.class));

                break;
            case R.id.rela3:
                startActivity(new Intent(getActivity(), AddressManeger.class));
                break;
            case R.id.rela4://邀请好友
      //          showDialog("快去分享给好友吧，burden");
                showShare();
                break;
            case R.id.rela5:
                startActivity(new Intent(getActivity(), SetActivity.class));
                break;
            case R.id.rela6:
                startActivity(new Intent(getActivity(), ShopIn.class));
                break;
            case R.id.rela21:
                startActivity(new Intent(getActivity(), MyBook.class)
                        .putExtra("stata", 1)
                );
                break;
            case R.id.rela22:
                startActivity(new Intent(getActivity(), MyBook.class)
                        .putExtra("stata", 2)
                );
                break;
            case R.id.rela23:
                startActivity(new Intent(getActivity(), MyBook.class)
                        .putExtra("stata", 3)
                );
                break;
            case R.id.rela24:
                startActivity(new Intent(getActivity(), MyBook.class)
                        .putExtra("stata", 4)
                );
                break;
            case R.id.rela25:
                startActivity(new Intent(getActivity(), ReFundListActivity.class)
                        .putExtra("stata", 4)
                );
                break;
            case R.id.tv_login:
                startActivity(new Intent(getActivity(), Login.class));
                break;
            case R.id.tv_regist:
                startActivity(new Intent(getActivity(), Regist.class));
                break;
            case R.id.image_photo:

                Intent intent = new Intent(getActivity(), MyDataActivity.class);
                if (loginBean != null) ;
                intent.putExtra("photo", loginBean.getData().getExt().getAvatarUrl())
                        .putExtra("name", loginBean.getData().getName())
                        .putExtra("sex", loginBean.getData().getExt().getGender());
                startActivity(intent);
                break;
            case R.id.allbooks:
                startActivity(new Intent(getActivity(), MyBook.class)
                        .putExtra("stata", 0)
                );
                break;
            case R.id.rela_message:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.rela_shopcar:
                startActivity(new Intent(getActivity(), ShopCarActivity.class)
                        .putExtra("stata", 0)
                );
                break;

        }
    }

    /**
     * 一般dialog
     */
    protected void showDialog(String st) {
        backgroundAlpha(0.5f);
        final Dialog dialog = new Dialog(getActivity(), R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_home);
        TextView textView = (TextView) dialog.findViewById(R.id.information);
        textView.setText(st);
        dialog.findViewById(R.id.cancel).setOnClickListener(v -> dialog.dismiss());
        dialog.findViewById(R.id.circle).setOnClickListener(v -> {
            dialog.dismiss();
//            ((BaseActivity) getActivity()).shareToWx("", "", 0);
            ((BaseActivity) getActivity()).shareTowx("www.baidu.com", true);
        });
        dialog.findViewById(R.id.firend).setOnClickListener(v ->

                dialog.dismiss()
        );
        dialog.setOnDismissListener(dialog1 -> backgroundAlpha(1f));

        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loginBean = null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        get("/api/shopCar/shop_car_num",20);
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("淘淘海");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("邀请您加入淘淘海");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("淘淘海");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        get("/api/shopCar/shop_car_num",20);
    }
}
