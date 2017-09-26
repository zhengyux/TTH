package com.taotaohai.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View view;

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
        return view;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rela1:
                break;
            case R.id.rela2:
                break;
            case R.id.rela3:
                break;
            case R.id.rela4:
                showDialog("快去分享给好友吧，burden");
                break;
            case R.id.rela5:
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
