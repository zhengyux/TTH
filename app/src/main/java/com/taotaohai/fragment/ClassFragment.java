package com.taotaohai.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.google.gson.JsonObject;
import com.taotaohai.R;
import com.taotaohai.activity.GoodsDetialActivity;
import com.taotaohai.activity.Login;
import com.taotaohai.activity.MessageActivity;
import com.taotaohai.activity.ShopCarActivity;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.ClassGoods;
import com.taotaohai.bean.ClassPage;
import com.taotaohai.bean.ShopCarNum;
import com.taotaohai.myview.BadgeView;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.SPUtils;
import com.taotaohai.util.util;
import com.taotaohai.widgets.MultipleStatusView;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.xutils.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView text_click;
    private TextView first_click;
    private List<View> views;
    private List<TextView> texts;
    private List<ImageView> images;
    int position = -1;
    private XRefreshView xrefreshview;
    private RecyclerView recyclerView;
    private ClassPage classPage;
    private ClassGoods classGoods;
    private CommonAdapter adapter;
    private String ordertype = "gmt_create";
    private String ordertype2 = "desc";
    private int pageIndex = 0;
    private int pageSize = 100;
    private String id = "-1";
    private ImageView class_car_image;
    private RelativeLayout rela_shopcar;
    private RelativeLayout rela_message;
    BadgeView badgeView;
    BadgeView badgeView2;
    private ExpandableListView expandableListView ;
    private TextView tv_all_goods;
    private MyexplistAdapter myexplistAdapter;
    int pos[];
    TIMConversation conversation;
    int msg=0;

    private MultipleStatusView mMsvLayout;

    private static ClassFragment fragment;

    public static ClassFragment newInstance() {
        return new ClassFragment();
    }

    public ClassFragment() {
        // Required empty public constructor
    }

    private void unreadMsg(){
        long cnt = TIMManager.getInstance().getConversationCount();

        //遍历会话列表
        for(long i = 0; i < cnt; ++i) {
            //根据索引获取会话
            conversation =TIMManager.getInstance().getConversationByIndex(i);

            conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C,conversation.getPeer());

            msg+=conversation.getUnreadMessageNum();


        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_class, container, false);
        initview();
        inithttp();


        badgeView = new BadgeView(getActivity(),rela_shopcar);
        badgeView2 = new BadgeView(getActivity(),rela_message);
        ExpandableListViewClick();
        tv_all_goods.setBackgroundColor(getResources().getColor(R.color.top_bar_normal_bg));
        return view;
    }





    private void ExpandableListViewClick(){

        tv_all_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_all_goods.setBackgroundColor(getResources().getColor(R.color.top_bar_normal_bg));

                id="-1";
                gohttp();
                for (int i = 0; i < myexplistAdapter.getGroupCount(); i++) {
                    expandableListView.collapseGroup(i);
                }

            }
        });




        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long l) {
                tv_all_goods.setBackgroundColor(getResources().getColor(R.color.class_bac));
                id = classPage.getData().get(groupPosition).getChildren().get(childPosition).getId();

                if(pos[groupPosition]!=childPosition){
                    if(pos[groupPosition]!=-1){
                        classPage.getData().get(groupPosition).getChildren().get(pos[groupPosition]).setSelect(false);
                    }
                    classPage.getData().get(groupPosition).getChildren().get(childPosition).setSelect(true);
                    pos[groupPosition]=childPosition;
                }

                myexplistAdapter.notifyDataSetChanged();
                gohttp();

                return true;
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                tv_all_goods.setBackgroundColor(getResources().getColor(R.color.class_bac));
                id=classPage.getData().get(i).getId();
                if(classPage.getData().get(i).getChildren().size()>0){
                    if(pos[i]!=-1){
                        classPage.getData().get(i).getChildren().get(pos[i]).setSelect(false);
                    }
                }

                myexplistAdapter.notifyDataSetChanged();
                pos[i]=-1;
                gohttp();

                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                for (int i = 0; i < myexplistAdapter.getGroupCount(); i++) {
                    if (groupPosition != i) {
                        expandableListView.collapseGroup(i);
                    }
                }

            }
        });


    }

    @Override
    public void inithttp() {
        super.inithttp();
        mMsvLayout.loading();
        unreadMsg();
        get("api/goods/class", 0);
        get("/api/shopCar/shop_car_num",20);
        get("/api/message/notReadList/0",50);
        get("/api/message/notReadList/1",50);

        gohttp();
    }

    @Override
    public void onFinished(int code) {
        super.onFinished(code);
        if (code == 0 && classPage == null) {
            get("api/goods/class", 0);
            return;
        }
        if (code == 1 && classGoods == null) {
            gohttp();
            return;
        }
        if (classPage != null && classGoods != null) {
            mMsvLayout.content();
        }


    }

    void gohttp() {
        has.clear();
        has.put("sorts[0].name", ordertype);
        has.put("sorts[0].order", ordertype2);
        has.put("paging", "0");
//        has.put("pageIndex", String.valueOf(pageIndex));
//        has.put("pageSize", String.valueOf(pageSize));
        Http(HttpMethod.GET, "api/goods/type/" + id, has, 1);
    }

    @Override
    public void onError(Throwable ex, int postcode) {
        if(postcode==99||postcode==999||postcode==998){
            String[] st = ex.toString().split("result:");
            if (st.length > 1) {
                util.isSuccess(util.getgson(st[1], BaseBean.class));
            }
            try {
                if (ex.toString().contains("401")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("温馨提示");
                    dialog.setMessage("是否进入登录页登录?");
                    dialog.setNegativeButton("前往", (dialog1, which) -> {
                        startActivity(new Intent(getActivity(), Login.class));
                    });
                    dialog.setNeutralButton("取消", (dialog1, which) -> {
                    });
                    dialog.show();
                }
            } catch (Exception e) {
            }
        }

    }

    @Override
    public void onSuccess(String data, int postcode) {
        super.onSuccess(data, postcode);
        if(postcode==999){
            startActivity(new Intent(getActivity(), ShopCarActivity.class));

        }
        if (postcode==998){
            startActivity(new Intent(getActivity(), MessageActivity.class));
        }
        if(postcode==20){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(data,ShopCarNum.class);
            if(shopCarNum.getData()!=0){

                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(9);// 设置文本大小
                badgeView.setText(shopCarNum.getData()+""); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }else {
                badgeView.hide();
            }

        }
        if(postcode==50){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(data,ShopCarNum.class);
            msg+=shopCarNum.getData();
            if (msg!=0) {
                badgeView2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView2.setTextSize(6);// 设置文本大小
                badgeView2.setText(""); // 设置要显示的文本
                badgeView2.show();// 将角标显示出来
            }else {
                badgeView2.hide();
            }

        }
        if(postcode==51){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(data,ShopCarNum.class);
            if(shopCarNum.getData()!=0){
                badgeView2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView2.setTextSize(6);// 设置文本大小
                badgeView2.setText(""); // 设置要显示的文本
                badgeView2.show();// 将角标显示出来
            }else {
                badgeView2.hide();
            }

        }

        if (postcode == 0) {

            classPage = util.getgson(data, ClassPage.class);
            pos=new int[classPage.getData().size()];
            for (int i=0;i<pos.length;i++){
                pos[i]=-1;
            }
            if (classPage.getSuccess()) {
                myexplistAdapter = new MyexplistAdapter();
                expandableListView.setAdapter(myexplistAdapter);
            }
        }
        if (postcode == 1) {
            classGoods = util.getgson(data, ClassGoods.class);
            if (classGoods.getSuccess()) {
                initdata();
            }
        }
        if (postcode == 99) {
            if (util.isSuccess(data)) {
//                View view = getActivity().getLayoutInflater().inflate(R.layout.toast, null);
//                CustomToast.showDiverseToast(getActivity(), view, Gravity.TOP);
                addtocar(image_photo, imag_photo2);
                get("/api/shopCar/shop_car_num",20);
            } else {
                showToast("加入商品失败，商品数量不足");
            }
        }


    }

    private void initview() {
        tv_all_goods = (TextView) view.findViewById(R.id.tv_all_goods);
        expandableListView = (ExpandableListView) view.findViewById(R.id.exp_list);
        expandableListView.setGroupIndicator(null);
        class_car_image = (ImageView) view.findViewById(R.id.class_car_image);
        mMsvLayout = (MultipleStatusView) view.findViewById(R.id.msv_layout);

        rela_shopcar = (RelativeLayout) view.findViewById(R.id.rela_shopcar);
        rela_message = (RelativeLayout) view.findViewById(R.id.rela_message);
        rela_message.setOnClickListener(this);
        rela_shopcar.setOnClickListener(this);
        imag_photo2 = (ImageView) view.findViewById(R.id.imag_photo2);
        View v1 = view.findViewById(R.id.rela1);
        View v2 = view.findViewById(R.id.rela2);
        View v3 = view.findViewById(R.id.rela3);
        views = Arrays.asList(v1, v2, v3);//rela布局
        v1.setOnClickListener(this);
        v2.setOnClickListener(this);
        v3.setOnClickListener(this);

        TextView tv1 = (TextView) view.findViewById(R.id.tv_1);
        TextView tv2 = (TextView) view.findViewById(R.id.tv_2);
        TextView tv3 = (TextView) view.findViewById(R.id.tv_3);
        texts = Arrays.asList(tv1, tv2, tv3);//textview布局
        ImageView image1 = (ImageView) view.findViewById(R.id.image1);
        ImageView image2 = (ImageView) view.findViewById(R.id.image2);
        ImageView image3 = (ImageView) view.findViewById(R.id.image3);
        ImageView image4 = (ImageView) view.findViewById(R.id.image4);
        images = Arrays.asList(image1, image2, image3, image4);//image布局

        xrefreshview = (XRefreshView) view.findViewById(R.id.xrefreshview);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
        xrefreshview.setPullLoadEnable(true);
        recyclerView.setHasFixedSize(true);//item改变的时候recycleview不会重新计算高度

        onClick(v1);

    }

    @Override
    public void onClick(View v) {
        initclass();
        switch (v.getId()) {
            case R.id.rela_shopcar:
                get("api/user/",999);
            break;

            case R.id.rela_message:

                if(SPUtils.contains(getActivity(),"hxid")){

                    if(!TIMManager.getInstance().getLoginUser().equals(SPUtils.get(getActivity(),"username",""))){

                        TIMManager.getInstance().login(SPUtils.get(getActivity(),"username","").toString(),SPUtils.get(getActivity(),"hxid","").toString(),new TIMCallBack() {
                            @Override
                            public void onError(int code, String desc) {
                                //错误码code和错误描述desc，可用于定位请求失败原因
                                //错误码code列表请参见错误码表


                            }

                            @Override
                            public void onSuccess() {


                            }
                        });


                    }


                }

                get("api/user/",998);

            case R.id.rela1:

                views.get(0).setBackgroundResource(R.drawable.bac_class_left);
                texts.get(0).setTextColor(getResources().getColor(R.color.white));
                position = -1;
                ordertype = "gmt_create";
                gohttp();
                break;
            case R.id.rela2:
                views.get(1).setBackgroundColor(getResources().getColor(R.color.them));
                texts.get(1).setTextColor(getResources().getColor(R.color.white));
                if (position == 1) {
                    images.get(1).setImageResource(R.mipmap.clickbutton);
                    position = -1;
                    ordertype2 = "desc";
                    ordertype = "price";

                } else {
                    images.get(0).setImageResource(R.mipmap.clickup);
                    position = 1;
                    ordertype = "price";
                    ordertype2 = "asc";
                }
                gohttp();
                break;
            case R.id.rela3:
                views.get(2).setBackgroundResource(R.drawable.bac_class_right);
                texts.get(2).setTextColor(getResources().getColor(R.color.white));
                if (position == 2) {
                    images.get(3).setImageResource(R.mipmap.clickbutton);
                    position = -1;
                    ordertype2 = "desc";
                    ordertype = "sale_volume";
                } else {
                    images.get(2).setImageResource(R.mipmap.clickup);
                    position = 2;
                    ordertype2 = "asc";
                    ordertype = "sale_volume";
                }
                gohttp();
                break;
        }

    }


    private void initclass() {
        for (int i = 0; i < 3; i++) {
            views.get(i).setBackground(null);
            texts.get(i).setTextColor(getResources().getColor(R.color.text_black));
        }
        images.get(0).setImageResource(R.mipmap.unclickup);
        images.get(1).setImageResource(R.mipmap.unclickbuttom);
        images.get(2).setImageResource(R.mipmap.unclickup);
        images.get(3).setImageResource(R.mipmap.unclickbuttom);
    }



    ImageView image_photo;
    ImageView imag_photo2;

    private void initdata() {
        adapter = new CommonAdapter<ClassGoods.Data>(getActivity(), R.layout.item_class_recycle, classGoods.getData2().getData()) {
            @Override
            protected void convert(ViewHolder holder, final ClassGoods.Data data, int position) {
                holder.setOnClickListener(R.id.image_car, v -> {
                    image_photo = holder.getView(R.id.image_car);
                    JsonObject object = new JsonObject();
                    object.addProperty("goodsId", data.getId());
                    object.addProperty("count", data.getUnitMin());
                    Http(HttpMethod.POST, "api/shopCar", object.toString(), 99);

                });
                holder.setOnClickListener(R.id.rela_all, (l) -> startActivity(new Intent(getActivity(), GoodsDetialActivity.class)
                        .putExtra("id", data.getId())));
                holder.setText(R.id.tv_money, "￥ " + data.getPrice());
                holder.setText(R.id.tv_name, data.getTitle());
                holder.setText(R.id.tv_unit, "/" + data.getUnit());
                holder.setText(R.id.tv_remaker, data.getRemark());
                if (data.getShopInfo() != null)
                    holder.setText(R.id.tv_count, "已购买" + data.getSaleVolume() + "件");
                ImageView imageView = holder.getView(R.id.image_photo);
                if (data.getImagesUrl().size() > 0)
                    GlideUtil.loadImg(data.getImagesUrl().get(0), imageView);
            }
        };

        // 设置静默加载模式
        xrefreshview.setSilenceLoadMore(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        xrefreshview.setPinnedTime(1000);
        xrefreshview.setMoveForHorizontal(true);
        xrefreshview.setPullRefreshEnable(false);
//        recyclerviewAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
//		xRefreshView1.setPullLoadEnable(false);
        //设置静默加载时提前加载的item个数
//        xrefreshview.setPreLoadCount(4);
        xrefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener()

        {
            @Override
            public void onRefresh(boolean isPullDown) {
                super.onRefresh(isPullDown);
                new Handler().postDelayed(() -> xrefreshview.stopRefresh(), 1000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                super.onLoadMore(isSilence);
                new Handler().postDelayed(() -> {
//                        xrefreshview.setLoadComplete(true);//已无更多数据
                    xrefreshview.stopLoadMore();//还有数据
                }, 1000);
            }
        });


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        msg=0;
        unreadMsg();
        get("/api/shopCar/shop_car_num",20);
        get("/api/message/notReadList/0",50);
        get("/api/message/notReadList/1",50);
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
        msg=0;
        unreadMsg();
        get("/api/shopCar/shop_car_num",20);
        get("/api/message/notReadList/0",50);
        get("/api/message/notReadList/1",50);

    }



    class MyexplistAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return classPage.getData().size();
        }

        @Override
        public int getChildrenCount(int i) {
            return classPage.getData().get(i).getChildren().size();
        }

        @Override
        public Object getGroup(int i) {
            return classPage.getData().get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return classPage.getData().get(i).getChildren().get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean b, View convertView, ViewGroup parent) {
            GroupViewHolder groupViewHolder;

            if (convertView == null) {
                groupViewHolder = new GroupViewHolder();
                convertView =  getActivity().getLayoutInflater().inflate(R.layout.item_class_list, null);
                groupViewHolder.tv_g = (TextView) convertView.findViewById(R.id.text1);
                convertView.setTag(groupViewHolder);
            }else {
                groupViewHolder = (GroupViewHolder) convertView.getTag();
            }


                    groupViewHolder.tv_g.setText(classPage.getData().get(groupPosition).getClassName());




            if(b){
                groupViewHolder.tv_g.setBackgroundColor(getResources().getColor(R.color.top_bar_normal_bg));
            }else {
                groupViewHolder.tv_g.setBackgroundColor(getResources().getColor(R.color.class_bac));
            }


            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
            ChildViewHolder childViewHolder;

            if (convertView == null) {
                childViewHolder = new ChildViewHolder();
                convertView =  getActivity().getLayoutInflater().inflate(R.layout.item_class_list2, null);
                childViewHolder.tv_c = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(childViewHolder);
            }else {
                childViewHolder = (ChildViewHolder) convertView.getTag();
            }






            childViewHolder.tv_c.setSelected(classPage.getData().get(groupPosition).getChildren().get(childPosition).isSelect());


            childViewHolder.tv_c.setText(classPage.getData().get(groupPosition).getChildren().get(childPosition).getClassName());




            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        class GroupViewHolder{
            TextView tv_g;

        }
        class ChildViewHolder{

            TextView tv_c;
        }
    }



}
