package com.taotaohai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.util.util;

public class RePassword extends BaseActivity {

    private EditText edit_old;
    private EditText edit_new;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        edit_old = (EditText) findViewById(R.id.edit_old);
        edit_new = (EditText) findViewById(R.id.edit_new);
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_old.getText().toString().trim().length() < 1 || edit_new.getText().toString().trim().length() < 1) {
                    showToast("密码不能为空");
                    return;
                }
                if (edit_old.getText().toString().trim().equals(edit_new.getText().toString().trim())) {
                    showToast("新旧密码不能相同");
                    return;
                }
                has.clear();
                has.put("oldPassword", edit_old.getText().toString().trim());
                has.put("newPassword", edit_new.getText().toString().trim());
                put("api/user/password2", has, 0);
            }
        });

    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        if (util.isSuccess(result)) {
            showToast("修改成功");
            finish();
        }
    }
}
