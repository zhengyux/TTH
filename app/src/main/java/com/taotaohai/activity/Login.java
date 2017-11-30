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

public class Login extends BaseActivity {

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        EMClient.getInstance().login("ylll", "111", new EMCallBack() {//回调
//            @Override
//            public void onSuccess() {
//                EMClient.getInstance().groupManager().loadAllGroups();
//                EMClient.getInstance().chatManager().loadAllConversations();
//                Log.d("main", "登录聊天服务器成功！");
//            }
//
//            @Override
//            public void onProgress(int progress, String status) {
//
//            }
//
//            @Override
//            public void onError(int code, String message) {
//                Log.d("main", "登录聊天服务器失败！");
//            }
//        });

        intitview();
    }

    EditText phone, password;

    private void intitview() {
        phone = (EditText) findViewById(R.id.edit_phone);
        password = (EditText) findViewById(R.id.edit_password);


    }

    public void onLogin(View v) {
//        if (!util.isMobileNO(phone.getText().toString().trim())) {
//            showToast("请输入正确手机号码");
//            return;
//        }
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
            if (EMClient.getInstance().isConnected()) {
                startActivity(new Intent(Login.this, Home.class));
//                        .putExtra("login", loginBean));
                finish();
                return;
            }
            EMClient.getInstance().login(loginBean.getData().getId(), MD5Utils.md5Password(password.getText().toString().trim()), new EMCallBack() {
                @Override
                public void onSuccess() {
                    startActivity(new Intent(Login.this, Home.class)
//                            .putExtra("login", loginBean)
                    );
                    finish();
                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @SuppressLint("WrongConstant")
                @Override
                public void onError(int code, String error) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "login failed", 0).show());
                }
            });
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
