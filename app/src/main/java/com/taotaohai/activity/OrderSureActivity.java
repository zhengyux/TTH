package com.taotaohai.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;

public class OrderSureActivity extends BaseActivity {

    @Override
    protected void inithttp() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sure);
        initview();
    }
    private void initview() {
        LinearLayout lin_goods = (LinearLayout) findViewById(R.id.lin_goods);
        for (int i = 0; i < 10; i++) {
            View view = getLayoutInflater().inflate(R.layout.item_sure, null);
            lin_goods.addView(view);
        }
        findViewById(R.id.tv_settlement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderSureActivity.this,MyBook.class));
            }
        });

    }
}
