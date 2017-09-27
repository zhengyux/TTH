package com.taotaohai.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taotaohai.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FocuGoodsFragment extends Fragment {


    public FocuGoodsFragment() {
        // Required empty public constructor
    }

    public static FocuGoodsFragment getInstance() {
        return new FocuGoodsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_focu_goods, container, false);
        initview(view);
        return view;
    }


    private void initview(final View view) {
        ListView list = (ListView) view.findViewById(R.id.listview);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.item_goods, null);
                TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                TextView tv_people = (TextView) convertView.findViewById(R.id.tv_people);
                TextView tv_num = (TextView) convertView.findViewById(R.id.tv_num);
                TextView tv_money = (TextView) convertView.findViewById(R.id.tv_money);
                ImageView image_photo = (ImageView) convertView.findViewById(R.id.image_photo);
                final TextView tv_cancle = (TextView) convertView.findViewById(R.id.tv_cancle);

                return convertView;
            }
        });


    }

}
