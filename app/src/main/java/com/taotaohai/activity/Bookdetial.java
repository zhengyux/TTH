package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.BookDet;
import com.taotaohai.util.util;

import org.xutils.http.HttpMethod;

public class Bookdetial extends BaseActivity {
    TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, btn_1, btn_2, btn_3;
    ImageView image_1;
    private BookDet bookd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetial);
        setTitle("订单详情");
        initview();
        inithttp();
    }

    private void initview() {
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_6 = (TextView) findViewById(R.id.tv_6);
        tv_7 = (TextView) findViewById(R.id.tv_7);
        tv_8 = (TextView) findViewById(R.id.tv_8);
        image_1 = (ImageView) findViewById(R.id.image_1);
        btn_1 = (TextView) findViewById(R.id.btn_1);
        btn_2 = (TextView) findViewById(R.id.btn_2);
        btn_3 = (TextView) findViewById(R.id.btn_3);

    }

    protected void inithttp() {
        get("api/GoodsOrder/detail/" + getintent("id"));
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        if (postcode == 10) {
            BaseBean baseBean = util.getgson(result, BaseBean.class);
            if (util.isSuccess(baseBean, this)) {
                showToast("删除成功");
                finish();
            }
        }

        bookd = util.getgson(result, BookDet.class);
        if (util.isSuccess(bookd, this)) {
            tv_1.setText("收货人：" + bookd.getData().getUsername());
            tv_2.setText("电话：" + bookd.getData().getLinkTel());
            tv_3.setText("收货地址：" + bookd.getData().getLinkAddress());
            tv_4.setText(bookd.getData().getGoodsName());
            tv_5.setText(bookd.getData().getColor() + "," + bookd.getData().getSize() + "," + bookd.getData().getAcount() + "件");
            tv_6.setText("¥" + bookd.getData().getPrice());
            tv_7.setText("¥" + bookd.getData().getTotalPrice());
            tv_8.setText("订单编号：" + bookd.getData().getOrderId() + "\n交易单号：" + "\n成交时间：" + bookd.getData().getGmtCreate() + "\n支付方式：" + gettype(bookd.getData().getPayType()) + "\n发货时间：");
            getstata(bookd.getData().getOrderStatus());

            btn_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_count = 0;
                    showDialog("删除订单", "确定要删除本条订单吗");
                }
            });
            btn_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (bookd.getData().getOrderStatus()) {
                        case 6:
                            dialog_count = 0;
                            showDialog("删除订单", "确定要删除本条订单吗");
                            break;
                        case 2:
                            dialog_count = 1;
                            showDialog("申请退款", "确定要申请退款吗");
                            break;
                        case 1:
                            dialog_count = 1;
                            showDialog("申请退款", "确定要申请退款吗");
                            break;
                        case 3:
                            dialog_count = 1;
                            showDialog("申请退款", "确定要申请退款吗");
                            break;
                        case 4:
                            startActivity(new Intent(Bookdetial.this, Evaluation.class)
                                    .putExtra("id", bookd.getData().getOrderId())
                                    .putExtra("goodid", bookd.getData().getGoodsId())
                                    .putExtra("goods", bookd.getData().getColor() + bookd.getData().getSize())
                            );
                            break;
                    }
                }
            });
        }
    }

    int dialog_count = -1;

    @Override
    protected void sure() {
        super.sure();
        if (dialog_count == 0) {
            Http(HttpMethod.DELETE, "api/GoodsOrder/delete/" + bookd.getData().getOrderId(), 10);
        }
        if (dialog_count == 1) {
            Http(HttpMethod.PUT, "api/GoodsOrder/cancleOrder/" + bookd.getData().getOrderId(), 11);
        }
        if (dialog_count == 2) {
            Http(HttpMethod.PUT, "api/GoodsOrder/cancleOrder/" + bookd.getData().getOrderId(), 12);
        }

    }

    String gettype(String st) {
        if (st.equals("1")) {
            return "微信";
        }
        if (st.equals("2")) {
            return "支付宝";
        }
        return "余额";
    }

    void getstata(int i) {

        switch (i) {
            case 1:
                btn_1.setText("取消订单");
                btn_2.setText("立即支付");
                btn_2.setTextColor(getResources().getColor(R.color.them));
                btn_2.setBackgroundResource(R.drawable.bac_class_them);
                break;
            case 2:
                btn_1.setText("售后/退款");
                btn_2.setText("提醒商家发货");
                break;
            case 3:
                btn_3.setVisibility(View.VISIBLE);
                btn_3.setText("查看物流");
                btn_1.setText("售后/退款");
                btn_2.setText("确认收货");
                break;
            case 4:
                btn_1.setText("再次购买");
                btn_2.setText("评价");
                break;
        }
    }

}
