package com.taotaohai.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.google.gson.JsonObject;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.util.util;

import org.xutils.http.HttpMethod;

import java.util.HashMap;

public class OpinionActivity extends BaseActivity {

    private EditText edit_text, ed_email;
    private TextView tv_num;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        setTitle("意见反馈");
        edit_text = (EditText) findViewById(R.id.edit_text);
        ed_email = (EditText) findViewById(R.id.ed_email);
        tv_num = (TextView) findViewById(R.id.tv_num);
        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_num.setText(s.length() + "/200");
            }
        });
    }

    public void onsubmet(View view) {
        if (edit_text.getText().toString().trim().length() == 0) {
            showToast("提交内容不能为空");
            return;
        }
        HashMap<String, String> has = new HashMap<>();

        JsonObject json=new JsonObject();
        json.addProperty("content", edit_text.getText().toString().trim());
        json.addProperty("email", ed_email.getText().toString().trim());
        Http(HttpMethod.POST,"api/base/feedback", json.toString(), 0);

    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        BaseBean bean = util.getgson(result, BaseBean.class);
        if (util.isSuccess(bean, getApplication())) {
            finish();
        }
    }
}
