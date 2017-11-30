package com.taotaohai.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.GoodsDetialActivity;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.Focusgoods;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;

/**
 * A simple {@link Fragment} subclass.
 */
public class FocuGoodsFragment extends BaseFragment {


    private Focusgoods focusgoods;
    private ListView list;

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
        inithttp();
        return view;
    }

    @Override
    public void inithttp() {
        super.inithttp();
        get("api/follow/goods/followList", 1);
    }

    TextView showtext = null;

    @Override
    public void onSuccess(String data, int postcode) {
        super.onSuccess(data, postcode);
        focusgoods = util.getgson(data, Focusgoods.class);
        if (focusgoods.getSuccess()) {
            list.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return focusgoods.getData().size();
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
                    LinearLayout lin_1 = (LinearLayout) convertView.findViewById(R.id.lin_1);
                    TextView tv_people = (TextView) convertView.findViewById(R.id.tv_people);
                    TextView tv_num = (TextView) convertView.findViewById(R.id.tv_num);
                    TextView tv_money = (TextView) convertView.findViewById(R.id.tv_money);
                    ImageView image_photo = (ImageView) convertView.findViewById(R.id.image_photo);
                    TextView tv_unit = (TextView) convertView.findViewById(R.id.tv_unit);
                    tv_name.setText(focusgoods.getData().get(position).getTitle());
                    tv_people.setText("已有" + focusgoods.getData().get(position).getSaleVolume() + "人购买");
                    tv_money.setText("￥" + focusgoods.getData().get(position).getPrice());
                    tv_num.setText(focusgoods.getData().get(position).getRemark());
                    tv_unit.setText("/" + focusgoods.getData().get(position).getUnit());
                    if (focusgoods.getData().get(position).getImagesUrl().size() > 0)
                        GlideUtil.loadImg(focusgoods.getData().get(position).getImagesUrl().get(0), image_photo);


                    final TextView tv_cancle = (TextView) convertView.findViewById(R.id.tv_cancle);
                    tv_cancle.setOnClickListener(v -> {
                        get("api/follow/" + focusgoods.getData().get(position).getId() + "/goods", 56);
                        showToast("取消关注成功");
                        focusgoods.getData().remove(position);
                        notifyDataSetChanged();
                    });
                    convertView.findViewById(R.id.image_more).setOnClickListener(v -> {
                        if (showtext != null) {
                            showtext.setVisibility(View.GONE);
                        }
                        showtext = tv_cancle;
                        showtext.setVisibility(View.VISIBLE);
                    });


                    return convertView;
                }
            });
        }
    }

    private void initview(final View view) {
        list = (ListView) view.findViewById(R.id.listview);
        list.setOnItemClickListener((parent, view1, position, id) -> startActivity(new Intent(getActivity(), GoodsDetialActivity.class)
                .putExtra("id", focusgoods.getData().get(position).getId())
        ));


    }

}
