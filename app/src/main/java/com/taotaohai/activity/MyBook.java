package com.taotaohai.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bigkoo.pickerview.OptionsPickerView;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.JsonObject;
import com.taotaohai.ConstantValue;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Book;
import com.taotaohai.bean.Goods;
import com.taotaohai.bean.PayResult;
import com.taotaohai.bean.ShopCarNum;
import com.taotaohai.bean.WXpay;
import com.taotaohai.fragment.ItemBookFragment;
import com.taotaohai.myview.BadgeView;
import com.taotaohai.util.ViewFindUtils;
import com.taotaohai.util.util;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
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

public class MyBook extends BaseActivity implements OnTabSelectListener, View.OnClickListener, ItemBookFragment.OnListFragmentInteractionListener {
    private ArrayList<ItemBookFragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private final String[] mTitles = {
            "全部", "待付款", "待发货"
            , "待收货", "待评价"
    };
    private Dialog dialog;
    private ViewPager vp;
    private RelativeLayout mrelativeLayout2;//购物车
    private RelativeLayout mrelativeLayout3;
    TIMConversation conversation;
    int msg=0;
    BadgeView badgeView2;

    @Override
    protected void inithttp() {
        msg=0;
        unreadMsg();
        get("/api/shopCar/shop_car_num",20);
        get("/api/message/notReadList/0",50);
        get("/api/message/notReadList/1",50);
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
    protected void onResume() {
        inithttp();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book);
        initview();
        inithttp();
        badgeView2 = new BadgeView(getApplicationContext(),mrelativeLayout3);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mMsvLayout.loading();
//
//        new Handler().postDelayed(() -> mMsvLayout.content(), 2000);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mFragments.get(vp.getCurrentItem()).refresh();
    }

    private void initview() {
        for (int i = 0; i < 5; i++) {
            mFragments.add(ItemBookFragment.newInstance(i));
        }
        mrelativeLayout2 = (RelativeLayout) findViewById(R.id.mrelativeLayout2);
        mrelativeLayout2.setOnClickListener(this);
        mrelativeLayout3  = (RelativeLayout) findViewById(R.id.mrelativeLayout3);
        mrelativeLayout3.setOnClickListener(this);
        findViewById(R.id.msearch_).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        View decorView = getWindow().getDecorView();
        vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        SlidingTabLayout tab = ViewFindUtils.find(decorView, R.id.tab);
        tab.setViewPager(vp);
        tab.setOnTabSelectListener(this);
        vp.setCurrentItem(getIntent().getIntExtra("stata", 0));
    }


    @Override
    public void onTabSelect(int position) {
        mFragments.get(position).refresh();
    }

    @Override
    public void onTabReselect(int position) {
        mFragments.get(position).refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.mrelativeLayout2:
                startActivity(new Intent(this,ShopCarActivity.class));
                break;
            case R.id.mrelativeLayout3:
                startActivity(new Intent(this,MessageActivity.class));
                break;
            case R.id.msearch_:
                startActivity(new Intent(this,SeachBookActivity.class));
                break;
        }
    }

    @Override
    public void onListFragmentInteraction(Book.Data item) {

        startActivity(new Intent(MyBook.this, Bookdetial.class)
                .putExtra("data", item)
        );
    }

    Book.Data item;
    ItemBookFragment itemBookFragment;

    @Override
    public void onListFragmentButton3(Book.Data mItem) {//第3个按钮
        this.item = mItem;
        this.itemBookFragment = mFragments.get(mItem.getCount());
        //查看物流
        startActivity(new Intent(this, LogisActivity.class)
                .putExtra("IMAGE",mItem.getExt().getImgId())
                .putExtra("OrderExpressCompany",mItem.getExt().getOrderExpressCompany()).putExtra("OrderExpressNo",mItem.getExt().getOrderExpressNo()));
    }


    @Override
    public void onListFragmentButton2(Book.Data item) {//第2个按钮
        if (item.getCount() == 99||item.getCount()==6||item.getCount()==7) {
            this.itemBookFragment = mFragments.get(0);

        } else {
            this.itemBookFragment = mFragments.get(item.getCount());
        }

        this.item = item;
        switch (item.getCount()) {

            case 1://立即购买

                Http(HttpMethod.GET,"/api/goods/"+item.getGoodsId(),23);

                break;
            case 2:
                Http(HttpMethod.PUT, "api/goodsorder/remind/" + item.getId()+"/"+item.getUserId()+"/"+item.getShopId(), 11);//提醒发货
                break;
            case 3:
                showDialog2("您确定已收到货？", "确定收货");
                break;
            case 4://评价
                startActivity(new Intent(MyBook.this, Evaluation.class)
                        .putExtra("id", item.getExt().getOrderId())
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

    boolean isdelect = false;

    @Override
    public void onListFragmentButton1(Book.Data item) {//第一个按钮
//        if (item.getCount() > 4) {
//            showToast("状态异常");
//            return;
//        }
        this.itemBookFragment = mFragments.get(item.getCount());
        this.item = item;
        switch (item.getCount()) {
            case 1:
                showchoose();
                break;
            case 2:
                startActivity(new Intent(MyBook.this, Refund.class)
                        .putExtra("data", item)
                );
                break;
            case 3:
                startActivity(new Intent(MyBook.this, Refund.class)
                        .putExtra("data", item));
                break;
            case 4://再次购买

                get("api/goods/" + item.getGoodsId(), 222);


                break;
//            case 5://退款
//                startActivity(new Intent(MyBook.this, ReFundDetialActivity.class)
//                        .putExtra("data", item));
//                break;
            case 99://关闭
//                startActivity(new Intent(MyBook.this, Refund.class));
                break;

        }
    }

    private static final List<String> options1Items = new ArrayList<>();

    private void showchoose() {
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
                    Http(HttpMethod.PUT, "api/goodsorder/cancel/" + item.getId()+"/"+URLEncoder.encode(options1Items.get(options1),"utf-8"), 10);//取消订单

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


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    /**
     * 一般dialog
     */
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
                get("pay/getOrderInfo?payId=1&transactionType=APP&orderId=" + item.getExt().getOrderId(), 21);//1支付宝 2微信
            } else {
                get("pay/getOrderInfo?payId=2&transactionType=APP&orderId=" + item.getExt().getOrderId(), 22);//1支付宝 2微信
            }


        });
        dialog.setOnDismissListener(dialog -> backgroundAlpha(1f));
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    @Override
    protected void sure() {
        super.sure();
        if (isdelect) {
            Http(HttpMethod.DELETE, "api/goodsorder/" + item.getId(), 999);
            isdelect = false;
        } else {
            has.clear();
            Http(HttpMethod.PUT, "api/goodsorder/received/" + item.getId(), has, 15);
        }


    }

    @Override
    public void onError(Throwable ex, int postcode) {

    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);

        if(postcode==222){
            Goods goods = util.getgson(result, Goods.class);
            if (goods.getData().getStatus() == 1) {
                showToast("商品下架啦！");
            }else if (goods.getData().getStock()<item.getExt().getAcount()){
                showToast("库存不足无法购买");
            }else {
                JsonObject object = new JsonObject();
                object.addProperty("goodsId", item.getGoodsId());
                object.addProperty("count", item.getExt().getAcount());
                Http(HttpMethod.POST, "api/shopCar", object.toString(), 99);
            }
        }

        if(postcode==23){

            Goods goods = new Goods();
            goods= util.getgson(result,Goods.class);

            if(goods.getData().getStock()<item.getExt().getAcount()){
                showToast("库存不足无法购买");
                return;
            }else {
                showpay(item.getTotalPrice());
            }

        }

        if(postcode==20){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            if(shopCarNum.getData()!=0){
                BadgeView badgeView = new BadgeView(getApplicationContext(),mrelativeLayout2);
                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(9);// 设置文本大小
                badgeView.setText(shopCarNum.getData()+""); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }

        }
        if(postcode==50){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            msg+=shopCarNum.getData();
            if(msg!=0){

                badgeView2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView2.setTextSize(6);// 设置文本大小
                badgeView2.setText(""); // 设置要显示的文本
                badgeView2.show();// 将角标显示出来
            }else {
                badgeView2.hide();
            }

        }

        if(postcode ==99){
            startActivity(new Intent(MyBook.this,ShopCarActivity.class));
            finish();
            return;
        }
        if(postcode == 11){
            showToast("提醒成功");
            return;
        }
        if (postcode == 999) {
            itemBookFragment.inithttp();
            showToast("删除成功");
            return;
        }
        if (postcode == 15) {
            showToast("收货成功");
            mFragments.get(vp.getCurrentItem()).refresh();
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
        if (postcode == 10) {
            itemBookFragment.inithttp();
            showToast("取消成功");
        }

    }


    private static final int SDK_PAY_FLAG = 1;

    void payzfb(String info) {
        final String orderInfo = info;   // 订单信息
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(MyBook.this);
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
                        itemBookFragment.refresh();
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
}
