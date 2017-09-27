package com.taotaohai.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.ShopActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FocuShopkFragment extends Fragment {


    public FocuShopkFragment() {
        // Required empty public constructor
    }

    public static FocuShopkFragment getInstance() {
        return new FocuShopkFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_focu_shopk, container, false);
        initview(view);
        return view;
    }

    TextView showtext = null;

    private void initview(final View view) {
        ListView list = (ListView) view.findViewById(R.id.listview);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (showtext != null) {
                    showtext.setVisibility(View.GONE);
                    showtext = null;
                    return;
                }
                startActivity(new Intent(getActivity(), ShopActivity.class));
            }
        });
        list.setAdapter(new BaseAdapter() {
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
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null)
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.item_shop, null);
                TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                final TextView tv_cancle = (TextView) convertView.findViewById(R.id.tv_cancle);
                convertView.findViewById(R.id.image_more).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (showtext != null) {
                            showtext.setVisibility(View.GONE);
                        }
                        showtext = tv_cancle;
                        showtext.setVisibility(View.VISIBLE);
                    }
                });


                return convertView;
            }
        });


    }

}
