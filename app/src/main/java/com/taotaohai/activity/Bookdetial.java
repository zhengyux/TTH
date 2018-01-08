package com.taotaohai.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bigkoo.pickerview.OptionsPickerView;
import com.hyphenate.easeui.EaseConstant;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.Book;
import com.taotaohai.bean.BookDet;
import com.taotaohai.bean.PayResult;
import com.taotaohai.bean.WXpay;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.HttpMethod;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bookdetial extends BaseActivity implements View.OnClickListener {
    TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9, tv_10, tv_11, tv_12, tv_13, tv_14, btn_1, btn_2, btn_3, tv_stata2, tv_stata;
    ImageView image_1;
    private BookDet bookd;
    Book.Data data;
    private Dialog dialog;


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

    int remainTime = 0;

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
        tv_stata.setText(getstata(data.getOrderStatus()));

        if (data.getOrderStatus() == 1 || data.getOrderStatus() == 2) {
            remainTime = data.getGmtRemaining();
            tv_stata2.post(new Runnable() {
                @Override
                public void run() {
                    if (remainTime < 0) {
                        return;
                    }
                    tv_stata2.setText(getStringtime(remainTime));
                    remainTime--;
                    tv_stata2.postDelayed(this, 1000);
                }
            });
        } else

        {
            tv_stata2.setText("");
        }
        tv_1.setText("收货人：" + data.getExt().getLinkName());
        tv_2.setText("电话：" + data.getExt().getLinkTel());
        tv_3.setText("收货地址：" + data.getExt().getLinkAddress());
        tv_4.setText(data.getExt().getShopName());
        tv_5.setText(tv_stata.getText().toString());
        tv_6.setText(data.getExt().getGoodsName());
        tv_7.setText(data.getExt().getRemark());
        tv_8.setText("￥" + data.getExt().getPrice());
        tv_9.setText("/" + data.getExt().getUnit());
        tv_10.setText("x" + data.getExt().getAcount());
        tv_11.setText("￥" + data.getExt().getPrice());
        tv_12.setText("￥" + data.getTotalPrice());
        tv_13.setText("￥" + data.getExt().getTotalPrice());
        StringBuffer buffer = new StringBuffer();


        if (data.getExt().getDealTime() == null)
            buffer.append("订单编号: " + data.getExt().getOrderId() + "\n创建时间：" + data.getGmtCreate());
        if (data.getExt().getGmtDelivery() != null)
            buffer.append("\n支付交易号：" + data.getExt().getGmtDelivery());
        if (data.getExt().getGmtDelivery() != null)
            buffer.append(data.getGmtCreate() + "\n付款时间：" + data.getExt().getDealTime());
        if (data.getExt().getOrderStatus() == 99 && data.getExt().getGmtModify() != null)
            buffer.append(data.getExt().getGmtModify());
        tv_14.setText(buffer.toString());
        tv_1.setText("收货人：" + data.getExt().getLinkName());
        GlideUtil.loadImg(data.getExt().getImgId(), image_1);
    }

    protected void inithttp() {
        get("api/GoodsOrder/detail/" + getintent("id"));
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        if (postcode == 15) {
            showToast("收货成功");
            finish();
            return;
        }
        if (postcode == 10) {
            BaseBean baseBean = util.getgson(result, BaseBean.class);
            if (util.isSuccess(baseBean, this)) {
                showToast("删除成功");
                finish();
            }
            return;
        }
        if (postcode == 11) {
            showToast("取消成功");
            finish();
            return;
        }
        if (postcode == 21) {
            try {
                JSONObject jsonObject = new JSONObject(result).getJSONObject("data");
                String appid = jsonObject.getString("app_id");
                String biz = jsonObject.getString("biz_content");
                String charset = jsonObject.getString("charset");
                String format = jsonObject.getString("format");
                String method = jsonObject.getString("method");
                String notifyUrl = jsonObject.getString("notify_url");
                String sign = jsonObject.getString("sign");
                String signType = jsonObject.getString("sign_type");
                String timestamp = jsonObject.getString("timestamp");
                String version = jsonObject.getString("version");
                StringBuilder sb = new StringBuilder()
                        .append("app_id=").append(URLEncoder.encode(appid, charset))
                        .append("&biz_content=").append(URLEncoder.encode(biz, charset))
                        .append("&charset=").append(URLEncoder.encode(charset, charset))
                        .append("&format=").append(URLEncoder.encode(format, charset))
                        .append("&method=").append(URLEncoder.encode(method, charset))
                        .append("&notify_url=").append(URLEncoder.encode(notifyUrl, charset))
                        .append("&sign_type=").append(URLEncoder.encode(signType, charset))
                        .append("&timestamp=").append(URLEncoder.encode(timestamp, charset))
                        .append("&version=").append(URLEncoder.encode(version, charset))
                        .append("&sign=").append(URLEncoder.encode(sign, charset));
                payzfb(sb.toString());
                dialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return;
        }
        if (postcode == 22) {
            final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
// 将该app注册到微信
            WXpay wXpay = util.getgson(result, WXpay.class);
            msgApi.registerApp(wXpay.getData().getAppid());
            PayReq request = new PayReq();
            request.appId = wXpay.getData().getAppid();
            request.partnerId = wXpay.getData().getPartnerid();
            request.prepayId = wXpay.getData().getPrepayid();
            request.packageValue = wXpay.getData().getMpackage();
            request.nonceStr = wXpay.getData().getNoncestr();
            request.timeStamp = String.valueOf(wXpay.getData().getTimestamp());
            request.sign = wXpay.getData().getSign();
//            msgApi.registerApp("wxd930ea5d5a258f4f");
//            PayReq request = new PayReq();
//            request.appId = "wxd930ea5d5a258f4f";
//            request.partnerId = "1900000109";
//            request.prepayId= "1101000000140415649af9fc314aa427";
//            request.packageValue = "Sign=WXPay";
//            request.nonceStr= "1101000000140429eb40476f8896f4c9";
//            request.timeStamp= "1398746574";
//            request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
            msgApi.sendReq(request);
            return;
        }


        bookd = util.getgson(result, BookDet.class);
        if (util.isSuccess(bookd, this)) {
            tv_1.setText("收货人：" + bookd.getData().getUsername());
            tv_2.setText("电话：" + bookd.getData().getLinkTel());
            tv_3.setText("收货地址：" + bookd.getData().getLinkAddress());
            tv_4.setText(bookd.getData().getGoodsName());
//            tv_5.setText(bookd.getData().getColor() + "," + bookd.getData().getSize() + "," + bookd.getData().getAcount() + "件");
            tv_6.setText("¥" + bookd.getData().getPrice());
            tv_7.setText("¥" + bookd.getData().getTotalPrice());
            tv_8.setText("订单编号：" + bookd.getData().getOrderId() + "\n交易单号：" + "\n成交时间：" + bookd.getData().getGmtCreate() + "\n支付方式：" + gettype(bookd.getData().getPayType()) + "\n发货时间：");
//            getstata(bookd.getData().getOrderStatus());

            btn_1.setOnClickListener(v -> {
                dialog_count = 0;
                showDialog("删除订单", "确定要删除本条订单吗");
            });
            btn_2.setOnClickListener(v -> {
                switch (bookd.getData().getOrderStatus()) {
                    case 5:
                        Log.e("tga", "onSuccess: 5");
                        dialog_count = 0;
                        showDialog("删除订单", "确定要删除本条订单吗");
                        break;
                    case 2:
                        dialog_count = 1;
                        Log.e("tga", "onSuccess: 2");
                        showDialog("申请退款", "确定要申请退款吗");
                        break;
                    case 1:
                        dialog_count = 1;
                        Log.e("tga", "onSuccess: 1");
                        showDialog("申请退款", "确定要申请退款吗");
                        break;
                    case 3:
                        dialog_count = 1;
                        Log.e("tga", "onSuccess: 3");
                        showDialog("申请退款", "确定要申请退款吗");
                        break;
                    case 4:
                        Log.e("tga", "onSuccess: 4");
                        startActivity(new Intent(Bookdetial.this, Evaluation.class)
                                .putExtra("id", data.getExt().getOrderId())
                        );
                        break;
                }
            });
        }
    }

    private static final int SDK_PAY_FLAG = 1;

    void payzfb(String info) {
        final String orderInfo = info;   // 订单信息
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(Bookdetial.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showToast("支付失败");
                    }
                    break;
                }

            }
        }
    };
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
        if (dialog_count == 3) {
            Http(HttpMethod.PUT, "api/goodsorder/received/" + data.getId(), has, 15);
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
                return "待发货";
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
            case 5:
                btn_1.setVisibility(View.GONE);
                btn_2.setVisibility(View.GONE);
                return "退款";
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
                showpay(String.valueOf(data.getExt().getTotalPrice()));
                break;
            case 2:
                showToast("提醒成功");
                break;
            case 3:
                Log.e("tag", "conlick2: 3");
                dialog_count = 3;
                showDialog2("您确定已收到货？", "确定收货");
                break;
            case 4://再次购买
                Log.e("tag", "conlick2: 4");
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
        options1Items.add("品质问题");
        options1Items.add("产品与描述不符");
        options1Items.add("数量、重量不符");
        options1Items.add("其他");

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                Http(HttpMethod.PUT, "api/goodsorder/cancel/" + data.getId(), 11);//取消订单
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
    int paytype = 1;

    protected void showpay(String st) {
        backgroundAlpha(0.5f);
        dialog = new Dialog(this, R.style.MyDialog_holo);
        dialog.setContentView(R.layout.dialog_pay);
        TextView textView = (TextView) dialog.findViewById(R.id.information);
        final View rela_1 = dialog.findViewById(R.id.rela_1);
        final View rela_2 = dialog.findViewById(R.id.rela_2);
        rela_1.setOnClickListener(v -> {
            iszfb = true;
            paytype = 1;
            rela_1.setBackgroundResource(R.drawable.button_r32);
            rela_2.setBackground(null);
        });
        rela_2.setOnClickListener(v -> {
            iszfb = false;
            paytype = 2;
            rela_2.setBackgroundResource(R.drawable.button_r32);
            rela_1.setBackground(null);
        });

        textView.setText(st);
        dialog.findViewById(R.id.cancel).setOnClickListener(v -> dialog.dismiss());
        dialog.findViewById(R.id.sure).setOnClickListener(v -> {
            dialog.dismiss();
            if (paytype == 1) {
                get("pay/getOrderInfo?payId=1&transactionType=APP&orderId=" + data.getExt().getOrderId(), 21);//1支付宝 2微信
            } else {
                get("pay/getOrderInfo?payId=2&transactionType=APP&orderId=" + data.getExt().getOrderId(), 22);//1支付宝 2微信
            }
        });
        dialog.setOnDismissListener(dialog1 -> backgroundAlpha(1f));
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

    String getStringtime(int remainTime) {
        int day = remainTime / 86400;
        int hour = remainTime % 86400 / 3600;
        int minutes = remainTime % 3600 / 60;
        int seconds = remainTime % 60;
        String time = "还有";
        if (day != 0) {
            time = time + day + "天";
        }
        if (day != 0 || hour != 0) {
            time = time + hour + ":";
        }
        if (day != 0 || hour != 0 || minutes != 0) {
            time = time + minutes + ":";
        }
        time = time + seconds + "交易关闭";
        return time;
    }

    public void onChat(View view) {
        if (data.getGoodsInfo() != null) {
            startActivity(new Intent(this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, data.getGoodsInfo().getUserId()));
        } else {
            startActivity(new Intent(this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, data.getUserId()));
        }

    }

}
