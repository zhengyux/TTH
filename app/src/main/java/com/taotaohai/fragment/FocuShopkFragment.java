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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.ShopActivity;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.Focusshop;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;

import org.xutils.http.HttpMethod;

import java.util.List;

import static com.ta.utdid2.android.utils.DebugUtils.get;

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

                    final TextView tv_cancle = (TextView) convertView.findViewById(R.id.tv_cancle);
                    tv_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            get("api/follow/" + focusshop.getData().get(position).getId() + "/shop", 56);
                            focusshop.getData().remove(position);
                            notifyDataSetChanged();
                        }
                    });
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

    TextView showtext = null;

    private void initview(final View view) {
        list = (ListView) view.findViewById(R.id.listview);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (showtext != null) {
                    showtext.setVisibility(View.GONE);
                    showtext = null;
                    return;
                }
                startActivity(new Intent(getActivity(), ShopActivity.class)
                        .putExtra("id", focusshop.getData().get(position).getId())
                );
            }
        });

    }

}
