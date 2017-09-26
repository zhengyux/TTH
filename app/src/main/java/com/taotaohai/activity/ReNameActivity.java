package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;


public class ReNameActivity extends BaseActivity {

    private EditText edit_name;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_name);
        setTitle("修改昵称");
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_name.setText(getIntent().getStringExtra("name"));
        edit_name.setSelection(edit_name.getText().length());
    }

    public void onSure(View view) {
        Intent data = new Intent();
        data.putExtra("name", edit_name.getText().toString());
        setResult(10, data);
        finish();
    }
}
