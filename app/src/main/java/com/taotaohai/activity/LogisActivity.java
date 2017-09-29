package com.taotaohai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;

public class LogisActivity extends BaseActivity {

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logis);
        initview();
    }

    private void initview() {
        LinearLayout lin_add = (LinearLayout) findViewById(R.id.lin_add);
        for (int i = 0; i < 10; i++) {
            View view = getLayoutInflater().inflate(R.layout.item_linadd, null);
            TextView tv_lin1 = (TextView) view.findViewById(R.id.tv_lin1);
            TextView tv_lin2 = (TextView) view.findViewById(R.id.tv_lin2);
            TextView tv_dot = (TextView) view.findViewById(R.id.tv_dot);
            TextView tv_stata = (TextView) view.findViewById(R.id.tv_stata);
            TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
            if (i == 0) {
                tv_lin1.setVisibility(View.GONE);
            } else {
                tv_lin1.setBackgroundResource(R.color.galy);
                tv_lin2.setBackgroundResource(R.color.galy);
                tv_dot.setBackground(getResources().getDrawable(R.drawable.run_glay));
                tv_stata.setTextColor(getResources().getColor(R.color.galy));
                tv_time.setTextColor(getResources().getColor(R.color.galy));
            }
            if (i == 9) {
                tv_lin2.setVisibility(View.GONE);
            }
            lin_add.addView(view);
        }

    }
}
