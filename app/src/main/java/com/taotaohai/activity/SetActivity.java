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
import com.taotaohai.bean.Contact;
import com.taotaohai.util.SPUtils;
import com.taotaohai.util.util;
import com.tencent.TIMManager;


public class SetActivity extends BaseActivity {


    private Contact contact;

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
        startActivity(new Intent(this, RePassword.class));
    }

    public void onTwo(View v) {
        startActivity(new Intent(this, OpinionActivity.class));
    }

    public void onThree(View v) {
        showToast("已经是最新版本");
    }

    public void onThree1(View v) {
        showToast("已清除");
    }

    public void onFour(View v) {

        get("api/base/contact", 3);
    }

    public void onFive(View v) {
        startActivity(new Intent(this, AboutUs.class));

    }

    public void onLogout(View v) {
        has.clear();
        post("api/auth/logout", has, 2);
    }


    protected void showDialog() {
        backgroundAlpha(0.5f);
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_layout_bo);
        TextView information = (TextView) dialog.findViewById(R.id.information);
        information.setText(contact.getData());
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
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.getData()));
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

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        if (postcode == 2) {
            if (util.isSuccess(result)) {
                SPUtils.remove(this, "username");
                SPUtils.remove(this, "password");
                SPUtils.remove(this, "hxid");
                removeAllActivity();
                TIMManager.getInstance().logout(null);//登出
                startActivity(new Intent(this, Login.class));
            }
            return;
        }
        contact = util.getgson(result, Contact.class);
        if (contact.getSuccess()) {
            showDialog();
        }
    }
}