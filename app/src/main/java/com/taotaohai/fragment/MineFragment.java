package com.taotaohai.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.LoginBean;
import com.taotaohai.bean.Mine;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.SPUtils;
import com.taotaohai.util.util;

import static android.R.attr.password;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView image_photo;
    private View tv_login, tv_regist;
    private TextView tv_name;


    public static MineFragment newInstance() {
        return new MineFragment();
    }

    public MineFragment() {
        // Required empty public constructor
    }

    LoginBean loginBean = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mine, container, false);
        // Inflate the layout for this fragment
        initview();
//        inithttp();
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
        has.put("username", (String) SPUtils.get(getActivity(),"username",""));
        has.put("password",  (String) SPUtils.get(getActivity(),"password",""));
        post("api/auth/login", has, 0);
    }

    @Override
    public void onSuccess(String data, int postcode) {
        super.onSuccess(data, postcode);
        loginBean = util.getgson(data, LoginBean.class);
        if (loginBean.getSuccess()) {
            initdata();
        }

    }

    @Override
    public void onError(Throwable ex, int postcode) {
        super.onError(ex, postcode);
    }

    private void initview() {
        image_photo = (ImageView) view.findViewById(R.id.image_photo);
        tv_name = (TextView) view.findViewById(R.id.tv_name);

        view.findViewById(R.id.rela1).setOnClickListener(this);
        view.findViewById(R.id.rela2).setOnClickListener(this);
        view.findViewById(R.id.rela3).setOnClickListener(this);
        view.findViewById(R.id.rela4).setOnClickListener(this);
        view.findViewById(R.id.rela5).setOnClickListener(this);
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
        view.findViewById(R.id.rela_shopcar).setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.rela1:
                break;
            case R.id.rela2:
                startActivity(new Intent(getActivity(), MyfocusActivity.class));

                break;
            case R.id.rela3:
                startActivity(new Intent(getActivity(), AddressManeger.class));
                break;
            case R.id.rela4:
                showDialog("快去分享给好友吧，burden");
                break;
            case R.id.rela5:
                startActivity(new Intent(getActivity(), SetActivity.class));

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
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                backgroundAlpha(1f);
            }
        });

        dialog.show();
    }


}
