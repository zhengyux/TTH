package com.taotaohai.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;

public class ReFundDetialActivity extends BaseActivity {
    TextView tv_14;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_fund_detial);
        initview();
    }

    private void initview() {

        tv_14 = (TextView) findViewById(R.id.tv_14);
        String st = "退款原因：123456<br />退款金额：<font color=#fa8d00>1578156</font><br />申请时间：123456<br />退款编号：2013-05-15";
        tv_14.setText(Html.fromHtml(st));

    }
}
