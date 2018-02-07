package com.taotaohai.activity;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.taotaohai.ConstantValue;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Car;
import com.taotaohai.bean.Defult;
import com.taotaohai.bean.OrderInfo;
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
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class OrderSureActivity extends BaseActivity {

    private Car car;
    View img_add, rela_address;
    TextView tv_name, tv_phone, tv_address;
    private Defult defult;
    List<String> list = null;
    private Dialog dialog;
    private TextView tv_price;
    private TextView tv_price2;
    private TextView tv_count;
    private EditText osedit;

    @Override
    protected void inithttp() {

    }

    private void getaddress() {
        get("api/user/shipping_def/");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sure);
        setTitle("提交订单");
        inithttp();
        initview();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getaddress();
    }

    private void initview() {
        osedit = (EditText) findViewById(R.id.os_edit);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_address = (TextView) findViewById(R.id.tv_address);
        img_add = findViewById(R.id.img_add);
        rela_address = findViewById(R.id.rela_address);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_price2 = (TextView) findViewById(R.id.tv_price2);
        car = (Car) getIntent().getSerializableExtra("car");
        list = (List<String>) getIntent().getSerializableExtra("list");
        tv_count = (TextView) findViewById(R.id.tv_count);
        if (car != null) {
            DecimalFormat df = new DecimalFormat("#####0.00");
            tv_price.setText(df.format(Double.valueOf(getintent("price"))));
            tv_price2.setText(df.format(Double.valueOf(getintent("price"))));
            tv_count.setText("共" + getintent("num") + "件商品 ：小计");
            LinearLayout lin_goods = (LinearLayout) findViewById(R.id.lin_goods);
            for (int i = 0; i < car.getData().getData().size(); i++) {
                View view = getLayoutInflater().inflate(R.layout.item_sure, null);
                TextView text_content = (TextView) view.findViewById(R.id.text_content);
                TextView tv_guige = (TextView) view.findViewById(R.id.tv_guige);
                TextView tv_sigalmoney = (TextView) view.findViewById(R.id.tv_sigalmoney);
                TextView tv_28 = (TextView) view.findViewById(R.id.tv_28);
                TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
                ImageView image_photo = (ImageView) view.findViewById(R.id.image_photo);

                GlideUtil.loadImg(car.getData().getData().get(i).getImgId(), image_photo);
                text_content.setText(car.getData().getData().get(i).getGoodsName());
                tv_guige.setText(car.getData().getData().get(i).getRemark());
                tv_28.setText("/" + car.getData().getData().get(i).getUnit());
                tv_sigalmoney.setText("￥" + car.getData().getData().get(i).getPrice());
                tv_num.setText("x" + car.getData().getData().get(i).getCount());
                lin_goods.addView(view);
            }
        } else {
            DecimalFormat df = new DecimalFormat("#####0.00");
            tv_price.setText(df.format(Double.valueOf(list.get(3)) * Double.valueOf(list.get(5))));
            tv_price2.setText(df.format(Double.valueOf(list.get(3)) * Double.valueOf(list.get(5))));
            tv_count.setText("共" + list.get(5) + "件商品 ：小计");

            LinearLayout lin_goods = (LinearLayout) findViewById(R.id.lin_goods);
            View view = getLayoutInflater().inflate(R.layout.item_sure, null);
            TextView text_content = (TextView) view.findViewById(R.id.text_content);
            TextView tv_guige = (TextView) view.findViewById(R.id.tv_guige);
            TextView tv_sigalmoney = (TextView) view.findViewById(R.id.tv_sigalmoney);
            TextView tv_28 = (TextView) view.findViewById(R.id.tv_28);
            TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
            ImageView image_photo = (ImageView) view.findViewById(R.id.image_photo);

            GlideUtil.loadImg(list.get(0), image_photo);
            text_content.setText(list.get(1));
            tv_guige.setText(list.get(2));
            tv_28.setText("/" + list.get(4));
            tv_sigalmoney.setText("￥" + list.get(3));
            tv_num.setText("x" + list.get(5));
            lin_goods.addView(view);
        }
        findViewById(R.id.tv_settlement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (car == null) {

                    JsonObject json = new JsonObject();
                    JsonArray jsonaray = new JsonArray();
                    JsonArray jsonaray2 = new JsonArray();
                    jsonaray.add(Integer.valueOf(list.get(5)));
                    jsonaray2.add(list.get(6));
                    json.add("goodsIds", jsonaray2);
                    json.add("counts", jsonaray);
                    if(null!=osedit.getText()||!"".equals(osedit.getText().toString().trim())){
                        json.addProperty("editRemark",osedit.getText().toString().trim());
                    }
                    if (defult == null || defult.getData() == null) {
                        showToast("请添加收货地址");
                        return;
                    }
                    json.addProperty("addressId", defult.getData().getId());

                    Http(HttpMethod.POST, "api/goodsorder/buy_now/", json.toString(), 15);
                } else {
                    JsonObject json = new JsonObject();
                    JsonArray jsonaray = new JsonArray();
                    JsonArray jsonaray2 = new JsonArray();
                    for (int i = 0; i < car.getData().getData().size(); i++) {
                        jsonaray.add(car.getData().getData().get(i).getCount());
                        jsonaray2.add(String.valueOf(car.getData().getData().get(i).getGoodsId()));
                    }
                    json.add("goodsIds", jsonaray2);
                    json.add("counts", jsonaray);
                    if (defult == null) {
                        showToast("请添加收货地址");
                        return;
                    }
                    json.addProperty("addressId", defult.getData().getId());
                    if(null!=osedit.getText()||!"".equals(osedit.getText().toString().trim())){
                        json.addProperty("editRemark",osedit.getText().toString().trim());
                    }

                    Http(HttpMethod.POST, "api/goodsorder/buy_now/", json.toString(), 15);
                }
//                startActivity(new Intent(OrderSureActivity.this, MyBook.class));
            }
        });
    }

    public void onaddress(View view) {
        startActivity(new Intent(this, AddressManeger.class));
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        if (postcode == 0) {
            defult = util.getgson(result, Defult.class);
            if (defult.getSuccess()) {
                if (defult.getData() != null) {
                    tv_name.setText("收货人: " + defult.getData().getLinkName());
                    tv_phone.setText("电话: " + defult.getData().getLinkTel());
                    tv_address.setText(defult.getData().getLinkProvince() + defult.getData().getLinkCity() + defult.getData().getLinkArea() + defult.getData().getLinkAddress());
                }
            }
        }
        if (postcode == 15) {
            OrderInfo orderInfo = util.getgson(result, OrderInfo.class);
            if (orderInfo.getSuccess()) {
                showpay(tv_price.getText().toString(), orderInfo.getData().getOrderInfo());
            } else {
                showToast(orderInfo.getMessage());
            }
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
            IWXAPI msgApi = WXAPIFactory.createWXAPI(this, ConstantValue.APP_ID);
            msgApi.registerApp(ConstantValue.APP_ID);

// 将该app注册到微信
            WXpay wXpay = util.getgson(result, WXpay.class);
           // msgApi.registerApp(wXpay.getData().getAppid());

            PayReq request = new PayReq();
            request.appId = wXpay.getData().getAppid();
            request.partnerId = wXpay.getData().getPartnerid();
            request.prepayId = wXpay.getData().getPrepayid();
            request.packageValue = wXpay.getData().getMpackage();
            request.nonceStr = wXpay.getData().getNoncestr();
            request.timeStamp = String.valueOf(wXpay.getData().getTimestamp());
            request.sign = wXpay.getData().getSign();
            msgApi.sendReq(request);
            return;
        }

    }

    @Override
    public void onError(Throwable ex, int postcode) {
        super.onError(ex, postcode);
        if (postcode==15){
            Log.e("tag", "onError: "+ex.toString() );
        }
    }

    void payzfb(String info) {
        final String orderInfo = info;   // 订单信息
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(OrderSureActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            Message msg = new Message();
            msg.what = 1;
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
                case 1: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();


                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        startActivity(new Intent(OrderSureActivity.this, MyBook.class).putExtra("stata",2));
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showToast("支付失败");
                        startActivity(new Intent(OrderSureActivity.this, MyBook.class).putExtra("stata",1));
                        finish();
                    }
                    break;
                }

            }
        }
    };

    boolean iszfb = true;
    int paytype = 1;

    protected void showpay(String st, String orderid) {
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
            for (int i = 0; i < car.getData().getData().size(); i++) {
                if(car.getData().getData().get(i).getGoodsInfo().getStock()<=car.getData().getData().get(i).getCount()){
                    showToast("购买量大于库存");
                }else {
                    dialog.dismiss();
                    if (paytype == 1) {
                        get("pay/getOrderInfo?payId=1&transactionType=APP&orderId=" + orderid, 21);//1支付宝 2微信
                    } else {
                        get("pay/getOrderInfo?payId=2&transactionType=APP&orderId=" + orderid, 22);//1支付宝 2微信
                    }
                }
            }


        });
        dialog.setOnDismissListener(dialog1 -> backgroundAlpha(1f));
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    @Override
    public void onFinished(int code) {
        super.onFinished(code);
        if (code == 0) {
            if (defult == null || defult.getData() == null) {
                img_add.setVisibility(View.VISIBLE);
                rela_address.setVisibility(View.GONE);
            } else {
                img_add.setVisibility(View.GONE);
                rela_address.setVisibility(View.VISIBLE);
            }
        }
    }
}
