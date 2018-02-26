package com.taotaohai.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.LoginBean;
import com.taotaohai.util.MD5Utils;
import com.taotaohai.util.SPUtils;
import com.taotaohai.util.util;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;


public class Login extends BaseActivity {

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        intitview();
    }

    EditText phone, password;

    private void intitview() {
        phone = (EditText) findViewById(R.id.edit_phone);
        password = (EditText) findViewById(R.id.edit_password);


    }

    public void onLogin(View v) {

        if (password.getText().toString().length() == 0) {
            showToast("请输入密码");
            return;
        }

        has.put("username", phone.getText().toString().trim());
        has.put("password", password.getText().toString().trim());
        has.put("loginType", "1");
        post("api/auth/login", has, 0);

    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        LoginBean loginBean = util.getgson(result, LoginBean.class);
        if (loginBean.getSuccess()) {
            showToast("登录成功");
            SPUtils.put(this, "username", phone.getText().toString().trim());
            SPUtils.put(this, "password", password.getText().toString().trim());
            SPUtils.put(this, "hxid", loginBean.getData().getId());
            //登入
            TIMManager.getInstance().login(loginBean.getData().getId(), MD5Utils.md5Password(password.getText().toString().trim()), new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {
                    //错误码code和错误描述desc，可用于定位请求失败原因
                    //错误码code列表请参见错误码表
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "登录聊天服务器失败", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onSuccess() {

                }
            });
            startActivity(new Intent(Login.this, Home.class));
            finish();


        }
    }

    @Override
    public void onError(Throwable ex, int postcode) {
        super.onError(ex, postcode);
        showToast("用户名或密码错误");
    }

    public void onFinish(View v) {
        finish();
    }

    public void onForget(View v) {
        startActivity(new Intent(this, ForgetPassword.class));
//        finish();
    }

    public void onRegist(View v) {
        startActivity(new Intent(this, Regist.class));
//        finish();
    }
}
