package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;

public class Search extends BaseActivity {


    private TextView tv_go;
    private EditText editText;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tv_go = (TextView) findViewById(R.id.textView18);
        editText = (EditText) findViewById(R.id.edit_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    tv_go.setText("取消");
                } else {
                    tv_go.setText("确定");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void onCancle(View view) {
        if (tv_go.getText().equals("确定")) {
            Intent intent = new Intent();
            intent.putExtra("key", editText.getText().toString());
            setResult(0, intent);
        }
        finish();
    }
}
