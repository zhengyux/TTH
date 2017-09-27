package com.taotaohai.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.AddressManeger;
import com.taotaohai.activity.Login;
import com.taotaohai.activity.MyBook;
import com.taotaohai.activity.MyDataActivity;
import com.taotaohai.activity.MyfocusActivity;
import com.taotaohai.activity.Regist;
import com.taotaohai.activity.SetActivity;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.Mine;
import com.taotaohai.util.util;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private Mine mine;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mine, container, false);
        // Inflate the layout for this fragment
        initview();
        inithttp();
        return view;
    }

    @Override
    public void inithttp() {
        super.inithttp();
        get("api/user/", 0);
    }

    @Override
    public void onSuccess(String data, int postcode) {
        super.onSuccess(data, postcode);
        mine = util.getgson(data, Mine.class);
        if (mine.getSuccess()) {

        }

    }

    @Override
    public void onError(Throwable ex, int postcode) {
        super.onError(ex, postcode);
    }

    private void initview() {
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
        view.findViewById(R.id.tv_login).setOnClickListener(this);
        view.findViewById(R.id.tv_regist).setOnClickListener(this);
        view.findViewById(R.id.image_photo).setOnClickListener(this);
        view.findViewById(R.id.allbooks).setOnClickListener(this);
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
                startActivityForResult(new Intent(getActivity(), SetActivity.class), 10);

                break;
            case R.id.rela21:
                break;
            case R.id.rela22:
                break;
            case R.id.rela23:
                break;
            case R.id.rela24:
                break;
            case R.id.rela25:
                break;
            case R.id.tv_login:
                startActivityForResult(new Intent(getActivity(), Login.class), 10);
                break;
            case R.id.tv_regist:
                startActivityForResult(new Intent(getActivity(), Regist.class), 11);
                break;
            case R.id.image_photo:

                Intent intent = new Intent(getActivity(), MyDataActivity.class);
                if (mine != null) ;
                intent.putExtra("photo", mine.getData().getExt().getAvatarUrl())
                        .putExtra("name", mine.getData().getUsername());
                startActivityForResult(intent, 10);
                break;
            case R.id.allbooks:
                startActivity(new Intent(getActivity(), MyBook.class));

                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            inithttp();
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
