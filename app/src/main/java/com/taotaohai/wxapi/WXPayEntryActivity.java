package com.taotaohai.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.taotaohai.ConstantValue;
import com.taotaohai.activity.MyBook;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, ConstantValue.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {


            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:

//                        pay.getdata("1");
                        Toast.makeText(getApplicationContext(),"支付成功",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(WXPayEntryActivity.this, MyBook.class));
                        finish();

                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:

                    Toast.makeText(getApplicationContext(),"取消支付",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(WXPayEntryActivity.this, MyBook.class));
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_COMM:

                    Toast.makeText(getApplicationContext(),"支付失败",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(WXPayEntryActivity.this, MyBook.class));
                    finish();
                    break;
            }
        }

    }




