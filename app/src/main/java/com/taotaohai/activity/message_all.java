package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Message_all;
import com.taotaohai.util.util;

public class message_all extends BaseActivity {

    private ListView list;

    @Override
    protected void inithttp() {
        get("api/message/" + getintent("type"));
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        Message_all message_all = util.getgson(result, Message_all.class);
        if (message_all.getSuccess()) {
            list.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return message_all.getData().getData().size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = getLayoutInflater().inflate(R.layout.item_message, null);
                    TextView text = (TextView) view.findViewById(R.id.text);
                    TextView tv_1 = (TextView) view.findViewById(R.id.tv_1);
                    TextView tv_2 = (TextView) view.findViewById(R.id.tv_2);
                    text.setText(message_all.getData().getData().get(position).getPushTime());
                    tv_1.setText(message_all.getData().getData().get(position).getTitle());
                    tv_2.setText(message_all.getData().getData().get(position).getContent());
                    view.setOnClickListener((l) -> {
                                if (message_all.getData().getData().get(position).getTargetType() == 0) {
                                    if(null==message_all.getData().getData().get(position).getTarget()||message_all.getData().getData().get(position).getTarget().length()<=0){
                                        showToast("商品下架啦！");
                                    }else {
                                        startActivity(new Intent(message_all.this, GoodsDetialActivity.class)
                                                .putExtra("id", message_all.getData().getData().get(position).getTarget()));
                                    }

                                }else{
                                    startActivity(new Intent(message_all.this, Message_detialActivity.class)
                                            .putExtra("data", message_all.getData().getData().get(position)));
                                }


                            }
                    );
                    return view;
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_all);
        inithttp();
        setTitle("消息");
        list = (ListView) findViewById(R.id.list);
    }
}
