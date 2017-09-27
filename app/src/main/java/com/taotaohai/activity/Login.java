package com.taotaohai.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.util.SPUtils;
import com.taotaohai.util.util;

import java.util.HashMap;

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
        if (!util.isMobileNO(phone.getText().toString().trim())) {
            showToast("请输入正确手机号码");
            return;
        }
        if (password.getText().toString().length() == 0) {
            showToast("请输入密码");
            return;
        }

        has.put("username", phone.getText().toString().trim());
        has.put("password", password.getText().toString().trim());
        post("api/auth/login", has, 0);

    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        if (util.isSuccess(result)) {
            showToast("登录成功");
            SPUtils.put(this, "username", phone.getText().toString().trim());
            SPUtils.put(this, "password", password.getText().toString().trim());
            finish();
        } else {
            showToast("账户名或密码错误");
        }
    }

    public void onFinish(View v) {
        finish();
    }

    public void onForget(View v) {
//        finish();
    }

    public void onRegist(View v) {
        startActivity(new Intent(this,Regist.class));
        finish();
    }
}
