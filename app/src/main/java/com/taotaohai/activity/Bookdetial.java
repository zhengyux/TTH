package com.taotaohai.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.Book;
import com.taotaohai.bean.BookDet;
import com.taotaohai.util.util;

import org.xutils.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

public class Bookdetial extends BaseActivity implements View.OnClickListener {
    TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9, tv_10, tv_11, tv_12, tv_13, tv_14, btn_1, btn_2, btn_3, tv_stata2, tv_stata;
    ImageView image_1;
    private BookDet bookd;
    Book.Data data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetial);
        data = (Book.Data) getIntent().getSerializableExtra("data");
        setTitle("订单详情");
        initview();
        stata = data.getCount();

//        inithttp();
    }

    private void initview() {
        tv_stata2 = (TextView) findViewById(R.id.tv_stata2);
        tv_stata = (TextView) findViewById(R.id.tv_stata);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_6 = (TextView) findViewById(R.id.tv_6);
        tv_7 = (TextView) findViewById(R.id.tv_7);
        tv_8 = (TextView) findViewById(R.id.tv_8);
        tv_9 = (TextView) findViewById(R.id.tv_9);
        tv_10 = (TextView) findViewById(R.id.tv_10);
        tv_11 = (TextView) findViewById(R.id.tv_11);
        tv_12 = (TextView) findViewById(R.id.tv_12);
        tv_13 = (TextView) findViewById(R.id.tv_13);
        tv_14 = (TextView) findViewById(R.id.tv_14);
        image_1 = (ImageView) findViewById(R.id.image_1);
        btn_1 = (TextView) findViewById(R.id.btn_1);
        btn_2 = (TextView) findViewById(R.id.btn_2);
        btn_3 = (TextView) findViewById(R.id.btn_3);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        tv_stata.setText(getstata(data.getExt().getOrderStatus()));
        if (data.getExt().getOrderStatus() == 1) {
        } else {
            tv_stata2.setText("");
        }
        tv_stata.setText(String.valueOf(data.getExt().getOrderStatus()));
        tv_1.setText("收货人：" + data.getExt().getLinkName());
        tv_2.setText("电话：" + data.getExt().getLinkTel());
        tv_3.setText("收货地址：" + data.getExt().getLinkAddress());
        tv_4.setText(data.getExt().getShopName());
        tv_5.setText(tv_stata2.getText().toString());
        tv_6.setText(data.getExt().getGoodsName());
        tv_7.setText(data.getExt().getRemark());
        tv_8.setText("￥" + data.getExt().getPrice());
        tv_9.setText("/" + data.getExt().getUnit());
        tv_10.setText("x" + data.getExt().getAcount());
        tv_11.setText("￥" + data.getExt().getPrice());
        tv_12.setText("￥" + data.getExt().getTotalPrice());
        tv_13.setText("￥" + data.getExt().getTotalPrice());
        tv_14.setText("订单编号:" + data.getExt().getOrderId() + "\n交易时间：" + data.getGmtCreate() + "\n成交时间：" + data.getGmtModify() + "\n交易时间：" + data.getGmtRefund());
        tv_1.setText("收货人：" + data.getExt().getLinkName());
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
//            getstata(bookd.getData().getOrderStatus());

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
                        case 5:
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
            Http(HttpMethod.DELETE, "api/GoodsOrder/" + bookd.getData().getOrderId(), 10);
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

    String getstata(int i) {

        switch (i) {
            case 1:
                btn_1.setText("取消订单");
                btn_2.setText("立即支付");
                btn_2.setTextColor(getResources().getColor(R.color.them));
                btn_2.setBackgroundResource(R.drawable.bac_class_them);
                return "待付款";
            case 2:
                btn_1.setText("售后/退款");
                btn_2.setText("提醒发货");
                return "代发货";
            case 3:
                btn_3.setVisibility(View.VISIBLE);
                btn_3.setText("查看物流");
                btn_1.setText("售后/退款");
                btn_2.setText("确认收货");
                return "待收货";
            case 4:
                btn_1.setText("再次购买");
                btn_2.setText("评价");
                return "待评价";
            default:
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);

        }
        return "关闭";
    }

    int stata = 0;

    public void conlick3() {//第3个按钮
        startActivity(new Intent(this, LogisActivity.class));
    }

    public void conlick2() {//第2个按钮
        switch (stata) {
            case 1:
                showpay("800");
                break;
            case 2:
                showToast("提醒成功");
                break;
            case 3:
                showDialog2("您确定已收到货？", "确定收货");
                break;
            case 4://再次购买
                startActivity(new Intent(Bookdetial.this, Evaluation.class));

                break;

        }
    }

    public void conlick1() {//第一个按钮
        switch (stata) {
            case 1:
                showchoose();
                break;
            case 2:
                startActivity(new Intent(Bookdetial.this, Refund.class).putExtra("data", data));
                break;
            case 3:
                startActivity(new Intent(Bookdetial.this, Refund.class).putExtra("data", data));
                break;
            case 4://再次购买
//                startActivity(new Intent(MyBook.this, Refund.class));
                break;

        }
    }

    private void showchoose() {
        final List<String> options1Items = new ArrayList<>();
        options1Items.clear();
        options1Items.add("托儿索");
        options1Items.add("儿童劫");
        options1Items.add("小学生之手");
        options1Items.add("德玛西亚大保健");
        options1Items.add("面对疾风吧");
        options1Items.add("天王盖地虎");
        options1Items.add("我发一米五");
        options1Items.add("爆刘继芬");

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String s = options1Items.get(options1);
//                button4.setText(s);
            }
        })
                .setSubCalSize(15)//确定和取消文字大小
                .setSubmitColor(R.color.glay_text)//确定按钮文字颜色
                .setCancelColor(R.color.them)//取消按钮文字颜色
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleText("选择退款原因")
                .setTitleSize(15)
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvOptions.setPicker(options1Items);
        pvOptions.show();
    }

    boolean iszfb = true;

    protected void showpay(String st) {
        backgroundAlpha(0.5f);
        final Dialog dialog = new Dialog(this, R.style.MyDialog_holo);
        dialog.setContentView(R.layout.dialog_pay);
        TextView textView = (TextView) dialog.findViewById(R.id.information);
        final View rela_1 = dialog.findViewById(R.id.rela_1);
        final View rela_2 = dialog.findViewById(R.id.rela_2);
        rela_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iszfb = true;
                rela_1.setBackgroundResource(R.drawable.button_r32);
                rela_2.setBackground(null);
            }
        });
        rela_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iszfb = false;
                rela_2.setBackgroundResource(R.drawable.button_r32);
                rela_1.setBackground(null);
            }
        });

        textView.setText(st);
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                backgroundAlpha(1f);
            }
        });
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                conlick1();
                break;
            case R.id.btn_2:
                conlick2();
                break;
            case R.id.btn_3:
                conlick3();
                break;
        }
    }
}
