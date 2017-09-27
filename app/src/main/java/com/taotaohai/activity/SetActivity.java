package com.taotaohai.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;


public class SetActivity extends BaseActivity {


    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        setTitle("设置");
        initview();

    }

    private void initview() {
//        startActivityForResult(new Intent(this, RePassword.class), 10);
    }

    public void onOne(View v) {
        startActivity(new Intent(this,RePassword.class));
    }

    public void onTwo(View v) {
        startActivity(new Intent(this, OpinionActivity.class));
    }

    public void onThree(View v) {
        showToast("已经是最新版本");
    }

    public void onFour(View v) {
        showDialog();
    }

    public void onFive(View v) {

    }


    protected void showDialog() {
        backgroundAlpha(0.5f);
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_layout_bo);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                backgroundAlpha(1);
            }
        });
        dialog.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4008888555"));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}