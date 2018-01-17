package com.taotaohai.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.KuaiDi;
import com.taotaohai.util.util;

import org.xutils.http.HttpMethod;

public class LogisActivity extends BaseActivity {

private KuaiDi kuaiDi;
private TextView textView32;//承运来源
private TextView textView33;//运单编号
    private TextView text30;//配送状态


    @Override
    protected void inithttp() {
        JsonObject object = new JsonObject();

        object.addProperty("company",getintent("OrderExpressCompany"));
        object.addProperty("number",getintent("OrderExpressNo"));
        Http(HttpMethod.POST,"/api/KuaiDiPost/queryExpress",object.toString(),1);
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        switch (postcode){
            case 1:
                kuaiDi= util.getgson(result,KuaiDi.class);
                initview();

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logis);
        setTitle("物流信息");
        inithttp();


    }

    private void initview() {
        textView32 = (TextView) findViewById(R.id.textView32);
        textView32.setText("承运来源："+kuaiDi.getData().getCom());
        textView33 = (TextView) findViewById(R.id.textView33);
        textView33.setText("快递单号："+kuaiDi.getData().getNu());
        text30 = (TextView) findViewById(R.id.text30);

        switch (kuaiDi.getData().getState()){
            case "1":
                text30.setText("已揽件");
                break;
            case "0":
                text30.setText("派送中");
                break;
        }



        findViewById(R.id.back).setOnClickListener(l->finish());


        LinearLayout lin_add = (LinearLayout) findViewById(R.id.lin_add);
        for (int i = 0; i < kuaiDi.getData().getData2().size(); i++) {
            View view = getLayoutInflater().inflate(R.layout.item_linadd, null);
            TextView tv_lin1 = (TextView) view.findViewById(R.id.tv_lin1);
            TextView tv_lin2 = (TextView) view.findViewById(R.id.tv_lin2);
            TextView tv_dot = (TextView) view.findViewById(R.id.tv_dot);
            TextView tv_stata = (TextView) view.findViewById(R.id.tv_stata);
            TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_stata.setText(kuaiDi.getData().getData2().get(i).getContext());
            tv_time.setText(kuaiDi.getData().getData2().get(i).getTime());
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
