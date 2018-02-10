package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Address;
import com.taotaohai.util.util;

import org.xutils.http.HttpMethod;

public class AddressManeger extends BaseActivity {

    private ListView listView;
    private Address address;
    private MyAdapter myadapter;
    static String LinkName;
    static String LinkTel;
    static String Address;
    static String ID;
    static String TYPE;

    @Override
    protected void inithttp() {
        get("api/user/shipping/");
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        if (postcode == 0) {
            address = util.getgson(result, Address.class);
            if (address.getSuccess()) {
                for (int i = 0; i < address.getData().getData().size(); i++) {
                    if (address.getData().getData().get(i).getIsdefault()) {
                        clickposition = i;
                        break;
                    }
                }
                if (myadapter == null) {
                    myadapter = new MyAdapter();
                    listView.setAdapter(myadapter);
                } else {
                    myadapter.notifyDataSetChanged();
                }


            }
        }
        if (postcode == 11) {
            if (util.isSuccess(result)) {
                showToast("删除成功");
                get("api/user/shipping/");
            }
        }
        if (postcode == 10) {
            if (util.isSuccess(result)) {
                showToast("设置成功");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        inithttp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_maneger);
        setTitle("地址管理");
        initview();
    }

    private void initview() {
        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if("a"==getintent("type")||"a".equals(getintent("type"))){

                    LinkName = address.getData().getData().get(i).getLinkName();
                    LinkTel = address.getData().getData().get(i).getLinkName();
                    Address = address.getData().getData().get(i).getLinkProvince()+address.getData().getData().get(i).getLinkCity()+address.getData().getData().get(i).getLinkArea()+address.getData().getData().get(i).getLinkAddress();
                    ID = address.getData().getData().get(i).getId();
                    TYPE = "address";


                    finish();

                }


            }
        });


    }

    public void onAdd(View view) {
        startActivity(new Intent(this, AddAdressActivity.class));
    }

    int clickposition = -1;

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return address.getData().getData().size();
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

            final Address.Data data = address.getData().getData().get(position);
            tv_name.setText(data.getLinkName());
            tv_phone.setText("电话：" + data.getLinkTel());
            tv_address.setText(data.getLinkProvince() + data.getLinkCity() + data.getLinkArea() + data.getLinkAddress());


            if (position == clickposition) {
                checkBox.setChecked(true);
                checkBox.setFocusable(false);
            } else {
                checkBox.setChecked(false);
                checkBox.setFocusable(false);

            }
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("isdefault", "true");
                    jsonObject.addProperty("linkName", data.getLinkName());
                    jsonObject.addProperty("linkTel", data.getLinkTel());
                    jsonObject.addProperty("linkAddress", data.getLinkAddress());
                    jsonObject.addProperty("linkProvince", data.getLinkProvince());
                    jsonObject.addProperty("linkCity", data.getLinkCity());
                    jsonObject.addProperty("linkArea", data.getLinkArea());
                    Http(HttpMethod.POST, "api/user/userAddress/" + data.getId(), jsonObject.toString(), 10);

                    clickposition = position;
                    notifyDataSetChanged();
                }
            });

            convertView.findViewById(R.id.rela_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AddressManeger.this, AddAdressActivity.class)
                            .putExtra("id", data.getId())
                            .putExtra("city", data.getLinkProvince() + " " + data.getLinkCity() + " " + data.getLinkArea())
                            .putExtra("name", data.getLinkName())
                            .putExtra("tel", data.getLinkTel())
                            .putExtra("area", data.getLinkAddress())
                    );
                }
            });
            convertView.findViewById(R.id.rela_delect).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Http(HttpMethod.DELETE, "api/user/userAddress/" + data.getId(), 11);
//                    address.getData().getData().remove(position);
//                    notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }
}
