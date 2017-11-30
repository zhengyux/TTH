package com.taotaohai.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Message_all;

public class Message_detialActivity extends BaseActivity {

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes);
        Message_all.Data data = (Message_all.Data) getIntent().getSerializableExtra("data");
        setTitle("推送详情");
        TextView tv_1 = (TextView) findViewById(R.id.tv_1);
        TextView tv_2 = (TextView) findViewById(R.id.tv_2);
        TextView tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_1.setText(data.getTitle());
        tv_2.setText(data.getPushTime());
        tv_3.setText(data.getContent());
    }
}
