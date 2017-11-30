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
import com.taotaohai.activity.ShopActivity;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.Focusshop;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;

/**
 * A simple {@link Fragment} subclass.
 */
public class FocuShopkFragment extends BaseFragment {
    private ListView list;
    private Focusshop focusshop;

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
        inithttp();
        return view;
    }

    @Override
    public void inithttp() {
        super.inithttp();
        get("api/follow/shop/followList", 1);
    }

    @Override
    public void onSuccess(String data, int postcode) {
        super.onSuccess(data, postcode);
        focusshop = util.getgson(data, Focusshop.class);
        if (focusshop.getSuccess()) {
            list.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return focusshop.getData().size();
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
                        convertView = getActivity().getLayoutInflater().inflate(R.layout.item_shop, null);
                    TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                    TextView tv_scor = (TextView) convertView.findViewById(R.id.tv_scor);
                    ImageView image_photo = (ImageView) convertView.findViewById(R.id.image_photo);
                    tv_name.setText(focusshop.getData().get(position).getName());
                    tv_scor.setText(focusshop.getData().get(position).getTotalCommonLevel() + "分");
                    GlideUtil.loadImg(focusshop.getData().get(position).getLogoIdAbsUrl(), image_photo);

                    LinearLayout lin_1 = (LinearLayout) convertView.findViewById(R.id.lin_1);
                    for (int i = 0; i < 3 && i < focusshop.getData().get(position).getShopIdentifies().size(); i++) {
                        TextView textView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.shop_textview, null);
                        textView.setText(focusshop.getData().get(position).getShopIdentifies().get(i).getName());
                        lin_1.addView(textView);
                    }

                    final TextView tv_cancle = (TextView) convertView.findViewById(R.id.tv_cancle);
                    tv_cancle.setOnClickListener(v -> {
                        get("api/follow/" + focusshop.getData().get(position).getId() + "/shop", 56);
                        showToast("取消关注成功");
                        focusshop.getData().remove(position);
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

    TextView showtext = null;

    private void initview(final View view) {
        list = (ListView) view.findViewById(R.id.listview);
        list.setOnItemClickListener((parent, view1, position, id) -> {
            if (showtext != null) {
                showtext.setVisibility(View.GONE);
                showtext = null;
                return;
            }
            startActivity(new Intent(getActivity(), ShopActivity.class)
                    .putExtra("id", focusshop.getData().get(position).getId())
            );
        });

    }

}
