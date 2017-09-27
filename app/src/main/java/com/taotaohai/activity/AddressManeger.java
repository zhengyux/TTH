package com.taotaohai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Check;

public class AddressManeger extends BaseActivity {

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_maneger);
        initview();
    }

    private void initview() {
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new MyAdapter());


    }

    public void onAdd(View view) {
        startActivity(new Intent(this, AddAdressActivity.class));
    }

    int clickposition = 0;

    class MyAdapter extends BaseAdapter {
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
                convertView = getLayoutInflater().inflate(R.layout.item_list_address, null);
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            TextView tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            TextView tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            if (position == clickposition) {
                checkBox.setChecked(true);
                checkBox.setFocusable(false);
            } else {
                checkBox.setChecked(false);
                checkBox.setFocusable(true);
            }
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (checkBox.isChecked()) {
//
//                    } else {
//
//                    }
                    clickposition = position;
                    notifyDataSetChanged();
                }
            });

            convertView.findViewById(R.id.rela_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            convertView.findViewById(R.id.rela_delect).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return convertView;
        }
    }
}
