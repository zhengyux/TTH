package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopCarActivity extends BaseActivity {

    private Mydapter adapter;
    boolean isall = false;
    private RadioButton radioall;

    @Override
    protected void inithttp() {

    }

    List<Boolean> isclicks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcar);
        for (int i = 0; i < 10; i++) {
            isclicks.add(false);
        }
        initview();


    }

    private void initview() {
        TextView tv_settlement = (TextView) findViewById(R.id.tv_settlement);
        tv_settlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopCarActivity.this,OrderSureActivity.class));
            }
        });
        ListView listView = (ListView) findViewById(R.id.list);
        adapter = new Mydapter();
        listView.setAdapter(adapter);
        radioall = (RadioButton) findViewById(R.id.radioall);
        radioall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isall) {
                    radioall.setChecked(false);
                    isclicks.clear();
                    for (int i = 0; i < 10; i++) {
                        isclicks.add(false);
                    }
                } else {
                    radioall.setChecked(true);
                    isclicks.clear();
                    for (int i = 0; i < 10; i++) {
                        isclicks.add(true);
                    }
                }
                adapter.notifyDataSetChanged();
                isall = !isall;
            }
        });

    }

    class Mydapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 10;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.item_shopcar, null);
            final RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.radio);
            final TextView tv_edit = (TextView) convertView.findViewById(R.id.tv_edit);
            final View rela_edit = convertView.findViewById(R.id.rela_edit);
            tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rela_edit.isShown()) {
                        rela_edit.setVisibility(View.GONE);
                        tv_edit.setText("编辑");
                    } else {
                        rela_edit.setVisibility(View.VISIBLE);
                        tv_edit.setText("完成");
                    }
                }
            });

            radioButton.setChecked(isclicks.get(position));
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isclicks.get(position)) {
                        radioButton.setChecked(false);
                    } else {
                        radioButton.setChecked(true);
                    }
                    isclicks.set(position, !isclicks.get(position));
                    all();
                }
            });

            return convertView;
        }
    }

    private void all() {
        for (int i = 0; i < isclicks.size(); i++) {
            if (!isclicks.get(i)) {
                isall = false;
                radioall.setChecked(false);
                return;
            }
        }
        radioall.setChecked(true);
        isall = true;
    }
}
