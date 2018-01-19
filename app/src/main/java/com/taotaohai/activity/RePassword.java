package com.taotaohai.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.util.util;

public class RePassword extends BaseActivity {

    private EditText edit_old;
    private EditText edit_new;
    private EditText edit_new2;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_password);
        setTitle("修改密码");
        edit_old = (EditText) findViewById(R.id.edit_old);
        edit_new = (EditText) findViewById(R.id.edit_new);
        edit_new2 = (EditText) findViewById(R.id.edit_new2);
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

                if("".equals(edit_new2.getText().toString().trim())||null==edit_new2.getText()){
                    showToast("请再次输入新密码");
                }

                if (!edit_new2.getText().toString().trim().equals(edit_new.getText().toString().trim())) {
                    showToast("两次密码输入不一致");
                    return;
                }
                if(6>edit_new2.getText().toString().trim().length()||edit_new2.getText().toString().trim().length()>16){
                    showToast("密码要求6-16位");
                    return;
                }
                if(isNumeric(edit_new2.getText().toString().trim())){
                    showToast("密码不能为纯数字");
                    return;
                }
                if(isChar(edit_new2.getText().toString().trim())){
                    showToast("密码不能为纯字母");
                    return;
                }
                has.clear();
                has.put("oldPassword", edit_old.getText().toString().trim());
                has.put("newPassword", edit_new.getText().toString().trim());
                put("api/user/password2", has, 0);
            }
        });

    }

    public  boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;}


    public  boolean isChar(String   data) {
        {
            for (int i = data.length(); --i >= 0; ) {
                char c = data.charAt(i);
                if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        }
    }


    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        if (util.isSuccess(result)) {
            showToast("修改成功");
            finish();
        } else {
            showToast("原密码错误");
        }
    }
}
