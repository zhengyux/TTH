package com.taotaohai.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.taotaohai.ConstantValue;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Book;
import com.taotaohai.bean.Goods;
import com.taotaohai.bean.PayResult;
import com.taotaohai.bean.WXpay;
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

public class SeachBookActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private RelativeLayout back;//返回键
    private EditText edit_search_book;//搜索框
    private TextView search_book_tv;//搜索按钮
    private ListView listview_seach_book; //订单列表
    private Book book;//订单实体类
    private Adapter adapter;//订单适配器
    private Dialog dialog;
    private  List<String> options1Items = new ArrayList<>();
    boolean isdelect = false;
    int pos=0;

    private void showchoose(int i) {
        options1Items.clear();
        options1Items.add("我不想买了");
        options1Items.add("信息填写错了，重新拍");
        options1Items.add("卖家缺货");
        options1Items.add("其他原因");

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                try {
                    Http(HttpMethod.PUT, "api/goodsorder/cancel/" + book.getData2().getData().get(i).getId()+"/"+URLEncoder.encode(options1Items.get(options1),"utf-8"), 10);//取消订单

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        })
                .setSubCalSize(15)//确定和取消文字大小
                .setSubmitColor(R.color.glay_text)//确定按钮文字颜色
                .setCancelColor(R.color.them)//取消按钮文字颜色
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleText("取消订单原因")
                .setTitleSize(15)
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvOptions.setPicker(options1Items);
        pvOptions.show();
    }

    @Override
    protected void inithttp() {

    }

    @Override
    public void onSuccess(String result, int postcode) {
        if(postcode==0){
                book= util.getgson(result,Book.class);
                adapter.notifyDataSetChanged();
                listview_seach_book.setAdapter(adapter);
            }
        if(postcode==222){
            Goods goods = util.getgson(result, Goods.class);
            if (goods.getData().getStatus() == 1) {
                showToast("商品下架啦！");
            } else {
                JsonObject object = new JsonObject();
                object.addProperty("goodsId", book.getData2().getData().get(pos).getGoodsId());
                object.addProperty("count", book.getData2().getData().get(pos).getExt().getAcount());
                Http(HttpMethod.POST, "api/shopCar", object.toString(), 99);
            }
        }
        if(postcode ==99){
            startActivity(new Intent(SeachBookActivity.this,ShopCarActivity.class));
            finish();
            return;
        }
        if(postcode == 11){
            showToast("提醒成功");
            return;
        }
        if (postcode == 10) {
            likeList();
            showToast("取消成功");
        }
        if (postcode == 15) {
            showToast("收货成功");
            likeList();
        }
        if (postcode == 999) {
            likeList();
            showToast("删除成功");
            return;
        }
        if(postcode==23){

            Goods goods = new Goods();
            goods= util.getgson(result,Goods.class);

            if(goods.getData().getStock()<book.getData2().getData().get(pos).getExt().getAcount()){
                showToast("库存不足无法购买");
                return;
            }else {
                showpay(book.getData2().getData().get(pos).getTotalPrice());
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
        }
        if (postcode == 22) {
            final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, ConstantValue.APP_ID);
// 将该app注册到微信
            WXpay wXpay = util.getgson(result, WXpay.class);
            msgApi.registerApp(ConstantValue.APP_ID);
            PayReq request = new PayReq();
            request.appId = wXpay.getData().getAppid();
            request.partnerId = wXpay.getData().getPartnerid();
            request.prepayId = wXpay.getData().getPrepayid();
            request.packageValue = wXpay.getData().getMpackage();
            request.nonceStr = wXpay.getData().getNoncestr();
            request.timeStamp = String.valueOf(wXpay.getData().getTimestamp());
            request.sign = wXpay.getData().getSign();
            msgApi.sendReq(request);
        }



    }



    @Override
    public void onError(Throwable ex, int postcode) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_book);
        initview();
    }

    private void initview() {
        adapter = new Adapter();
        back= (RelativeLayout) findViewById(R.id.back);
        back.setOnClickListener(this);
        edit_search_book = (EditText) findViewById(R.id.edit_search_book);
        search_book_tv = (TextView) findViewById(R.id.search_book_tv);
        search_book_tv.setOnClickListener(this);
        listview_seach_book = (ListView) findViewById(R.id.listview_seach_book);
        listview_seach_book.setOnItemClickListener(this);
    }

    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    protected void sure() {
        super.sure();
        if (isdelect) {
            Http(HttpMethod.DELETE, "api/goodsorder/" + book.getData2().getData().get(pos).getId(), 999);
            isdelect = false;
        } else {
            has.clear();
            Http(HttpMethod.PUT, "api/goodsorder/received/" + book.getData2().getData().get(pos).getId(), has, 15);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case  R.id.back:

                finish();

                break;
            case R.id.search_book_tv:

                if(null==edit_search_book.getText()||"".equals(edit_search_book.getText().toString().trim())){
                   showToast("输入搜索关键字");
                   return;
                }
                hintKbTwo();

               likeList();

                break;



        }
    }

    @Override
    protected void onResume() {
        if(null==edit_search_book.getText()||"".equals(edit_search_book.getText().toString().trim())){

        }else {
            likeList();
        }

        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        startActivity(new Intent(SeachBookActivity.this, Bookdetial.class)
                .putExtra("data", book.getData2().getData().get(i)));

    }
    private void likeList(){
        try {

            get("api/goodsorder/like_list/"+URLEncoder.encode(edit_search_book.getText().toString().trim(),"utf-8"),0);

        }catch (Exception e){
            e.printStackTrace();
        }
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
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.findViewById(R.id.sure).setOnClickListener(v -> {
            dialog.dismiss();
            if (paytype == 1) {
                get("pay/getOrderInfo?payId=1&transactionType=APP&orderId=" + book.getData2().getData().get(pos).getExt().getOrderId(), 21);//1支付宝 2微信
            } else {
                get("pay/getOrderInfo?payId=2&transactionType=APP&orderId=" + book.getData2().getData().get(pos).getExt().getOrderId(), 22);//1支付宝 2微信
            }


        });
        dialog.setOnDismissListener(dialog -> backgroundAlpha(1f));
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    private final int SDK_PAY_FLAG = 1;

    void payzfb(String info) {
        final String orderInfo = info;   // 订单信息
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(SeachBookActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
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
                        showToast("支付成功");
                        likeList();
                    } else if(TextUtils.equals(resultStatus, "8000")){

                        showToast("支付结果确认中");

                    }else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showToast("支付失败");
                    }
                    break;
                }

            }
        }
    };

    public class Adapter extends BaseAdapter{


        @Override
        public int getCount() {
            return book.getData2().getData().size();
        }

        @Override
        public Object getItem(int i) {
            return book.getData2().getData().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_book, null);
                viewHolder.text_title = (TextView) view.findViewById(R.id.text_title);
                viewHolder.text_content = (TextView) view.findViewById(R.id.text_content);
                viewHolder.text_stata = (TextView) view.findViewById(R.id.text_stata);
                viewHolder.btn_1 = (TextView) view.findViewById(R.id.btn_1);
                viewHolder.btn_2 = (TextView) view.findViewById(R.id.btn_2);
                viewHolder.btn_3 = (TextView) view.findViewById(R.id.btn_3);
                viewHolder.tv_all = (TextView) view.findViewById(R.id.tv_all);
                viewHolder.tv_count = (TextView) view.findViewById(R.id.tv_count);
                viewHolder.tv_sigalmoney = (TextView) view.findViewById(R.id.tv_sigalmoney);
                viewHolder.tv_guige = (TextView) view.findViewById(R.id.tv_guige);
                viewHolder.image_photo = (ImageView) view.findViewById(R.id.image_photo);



                view.setTag(viewHolder);
            } else {

                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.text_title.setText(book.getData2().getData().get(i).getExt().getShopName());
            viewHolder.text_content.setText(book.getData2().getData().get(i).getExt().getGoodsName());
            viewHolder.text_stata.setText(getstata(book.getData2().getData().get(i).getOrderStatus(), viewHolder.btn_1, viewHolder.btn_2, viewHolder.btn_3));
            viewHolder.tv_sigalmoney.setText("¥ " + book.getData2().getData().get(i).getExt().getPrice());
            viewHolder.tv_all.setText("¥ " + book.getData2().getData().get(i).getTotalPrice());
            viewHolder.tv_guige.setText(book.getData2().getData().get(i).getExt().getRemark());
            viewHolder.tv_count.setText("x" + book.getData2().getData().get(i).getExt().getAcount());
            book.getData2().getData().get(i).setCount(book.getData2().getData().get(i).getOrderStatus());
            Glide.with(getApplicationContext()).load(book.getData2().getData().get(i).getExt().getImgId()).error(R.mipmap.ic_bac).into(viewHolder.image_photo);
            viewHolder.btn_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pos=i;

                    switch (book.getData2().getData().get(i).getCount()) {
                        case 1:
                            showchoose(i);
                            break;
                        case 2:
                            startActivity(new Intent(SeachBookActivity.this, Refund.class)
                                    .putExtra("data", book.getData2().getData().get(i))
                            );
                            break;
                        case 3:
                            startActivity(new Intent(SeachBookActivity.this, Refund.class)
                                    .putExtra("data", book.getData2().getData().get(i)));
                            break;
                        case 4://再次购买

                            get("api/goods/" + book.getData2().getData().get(i).getGoodsId(), 222);

                            break;

                        case 99://关闭

                            break;

                    }



                }
            });
            viewHolder.btn_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pos=i;
                    switch (book.getData2().getData().get(i).getCount()) {

                        case 1://立即购买

                            Http(HttpMethod.GET,"/api/goods/"+book.getData2().getData().get(i).getGoodsId(),23);

                            break;
                        case 2://提醒发货
                            Http(HttpMethod.PUT, "api/goodsorder/remind/" + book.getData2().getData().get(i).getId()+"/"+book.getData2().getData().get(i).getUserId()+"/"+book.getData2().getData().get(i).getShopId(), 11);
                            break;
                        case 3:
                            showDialog2("您确定已收到货？", "确定收货");
                            break;
                        case 4://评价
                            startActivity(new Intent(SeachBookActivity.this, Evaluation.class)
                                    .putExtra("id", book.getData2().getData().get(i).getExt().getOrderId())
                            );
                            break;

                        case 6:
                            isdelect = true;
                            showDialog2("删除后不能恢复", "订单删除");
                            break;
                        case 7:
                            isdelect = true;
                            showDialog2("删除后不能恢复", "订单删除");
                            break;

                        case 99://删除
                            isdelect = true;
                            showDialog2("删除后不能恢复", "订单删除");
                            break;

                    }
                }
            });
            viewHolder.btn_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(SeachBookActivity.this, LogisActivity.class)
                            .putExtra("IMAGE",book.getData2().getData().get(i).getExt().getImgId())
                            .putExtra("OrderExpressCompany",book.getData2().getData().get(i).getExt().getOrderExpressCompany()).putExtra("OrderExpressNo",book.getData2().getData().get(i).getExt().getOrderExpressNo()));
                }
            });



            return view;
        }

        class ViewHolder{
            public  View mView;
            public  TextView text_title;
            public  TextView text_content;
            public  TextView text_stata;
            public  TextView btn_1;
            public  TextView btn_2;
            public  TextView btn_3;
            public  TextView tv_all;
            public  TextView tv_sigalmoney;
            public  TextView tv_guige;
            public  TextView tv_count;
            public  ImageView image_photo;

        }



        String getstata(int i, TextView btn_1, TextView btn_2, TextView btn_3) {
            btn_1.setVisibility(View.VISIBLE);
            btn_2.setVisibility(View.VISIBLE);
            btn_3.setVisibility(View.GONE);
            switch (i) {
                case 1:
                    btn_1.setText("取消订单");
                    btn_2.setText("立即支付");
                    btn_2.setTextColor(getApplicationContext().getResources().getColor(R.color.them));
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
                    btn_3.setVisibility(View.GONE);
                    return "待评价";
                case 5:
                    btn_1.setVisibility(View.INVISIBLE);
                    btn_2.setVisibility(View.INVISIBLE);
                    btn_3.setVisibility(View.GONE);

                    return "退款";

                case 6:

                    btn_1.setVisibility(View.GONE);
                    btn_3.setVisibility(View.GONE);
                    btn_2.setText("删除");
                    return "交易完成";

                case 7:

                    btn_1.setVisibility(View.GONE);
                    btn_3.setVisibility(View.GONE);
                    btn_2.setText("删除");

                    return "退款完成";
                case 99:
                    btn_1.setVisibility(View.GONE);
                    btn_3.setVisibility(View.GONE);
                    btn_2.setText("删除");
                    return "交易关闭";
            }
            return "";
        }
    }

}
