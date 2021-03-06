package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Book;
import com.taotaohai.bean.ShopCarNum;
import com.taotaohai.myview.BadgeView;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;

public class ReFundListActivity extends BaseActivity {

    private Book book;
    private RelativeLayout rela_shopcar;
    private RelativeLayout rela_message;

    @Override
    protected void inithttp() {
        get("api/goodsorder/list/5");
        get("/api/shopCar/shop_car_num",20);
        get("/api/message/notReadList/0",50);
        get("/api/message/notReadList/1",51);
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        if (util.isSuccess(result)) {
            book = util.getgson(result, Book.class);
            initview();
        }
        if(postcode==20){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            if(shopCarNum.getData()!=0){
                BadgeView badgeView = new BadgeView(getApplicationContext(),rela_shopcar);
                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(9);// 设置文本大小
                badgeView.setText(shopCarNum.getData()+""); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }

        }
        if(postcode==50){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            if(shopCarNum.getData()!=0){
                BadgeView badgeView = new BadgeView(getApplicationContext(),rela_message);
                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(6);// 设置文本大小
                badgeView.setText(""); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }

        }
        if(postcode==51){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            if(shopCarNum.getData()!=0){
                BadgeView badgeView = new BadgeView(getApplicationContext(),rela_message);
                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(6);// 设置文本大小
                badgeView.setText(""); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_fund_list);
        rela_message = (RelativeLayout) findViewById(R.id.rela_message);
        rela_message.setOnClickListener((l) -> startActivity(new Intent(this, MessageActivity.class)));
        rela_shopcar = (RelativeLayout) findViewById(R.id.rela_shopcar);
        rela_shopcar.setOnClickListener((l) -> startActivity(new Intent(this, ShopCarActivity.class)));
        findViewById(R.id.back).setOnClickListener((l) -> finish());

        inithttp();
    }

    private void initview() {
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return book.getData2().getData().size();
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
                View view = getLayoutInflater().inflate(R.layout.item_book_refund, null);
                view.findViewById(R.id.btn_2).setOnClickListener(v -> startActivity(new Intent(ReFundListActivity.this, ReFundDetialActivity.class)
                        .putExtra("data", book.getData2().getData().get(position)))
                );
                TextView text_title = (TextView) view.findViewById(R.id.text_title);
                TextView text_content = (TextView) view.findViewById(R.id.text_content);
                TextView tv_guige = (TextView) view.findViewById(R.id.tv_guige);
                TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
                TextView tv_sigalmoney = (TextView) view.findViewById(R.id.tv_sigalmoney);
                TextView tv_all = (TextView) view.findViewById(R.id.tv_all);
                TextView tv_all2 = (TextView) view.findViewById(R.id.tv_all2);
                ImageView image_photo = (ImageView) view.findViewById(R.id.image_photo);
                GlideUtil.loadImg(book.getData2().getData().get(position).getExt().getImgId(), image_photo);

                text_title.setText(book.getData2().getData().get(position).getExt().getShopName());
                text_content.setText(book.getData2().getData().get(position).getExt().getGoodsName());
                tv_guige.setText(book.getData2().getData().get(position).getExt().getRemark());
                tv_count.setText(String.valueOf("x" + book.getData2().getData().get(position).getExt().getAcount()));
                tv_sigalmoney.setText("退款金额:" + book.getData2().getData().get(position).getTotalPrice());
                tv_all.setText(getRefundType(book.getData2().getData().get(position).getExt().getRefundType()));
                tv_all2.setText(getRefundState(book.getData2().getData().get(position).getRefundStatus()));
                return view;
            }
        });


    }

    public static String getRefundState(int state) {
        String State = "";
        switch (state) {
            case 1:
                State = "待审核";
                break;
            case 2:
                State = "已同意退款";
                break;
            case 3:
                State = "等待平台介入";
                break;
            case 4:
                State = "平台同意退款";
                break;
            case 5:
                State = "平台拒绝退款";
                break;

        }
        return State;
    }

    String getRefundType(int state) {
        String State = "";
        switch (state) {
            case 0:
                State = "仅退款";
                break;
            case 1:
                State = "退款退货";
                break;
        }
        return State;
    }
}
