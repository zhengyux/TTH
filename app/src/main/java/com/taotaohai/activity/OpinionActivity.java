package com.taotaohai.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.util.util;

import java.util.HashMap;

public class OpinionActivity extends BaseActivity {

    private EditText edit_text;
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
                tv_num.setText(s.length() + "/150");
            }
        });
    }

    public void onsubmet(View view) {
        if (edit_text.getText().toString().trim().length() == 0) {
            showToast("提交内容不能为空");
            return;
        }
        HashMap<String, String> has = new HashMap<>();
        has.put("advice", edit_text.getText().toString().trim());
        post("api/advice/content", has, 0);

    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        BaseBean bean = util.getgson(result, BaseBean.class);
        if (util.isSuccess(bean, getApplication())) {
            showToast("提交完成");
            finish();
        }
    }
}
