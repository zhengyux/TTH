package com.taotaohai.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.taotaohai.ConstantValue;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
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
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            finish();
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    if (BaseResp.ErrCode.ERR_OK == 0) {
//                        pay.getdata("1");
                    }
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
//                    PromptManager.showToast(getApplicationContext(), "支付已取消");
//                    pay.getdata("2");
                    //分享取消
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    //分享拒绝
//                    pay.getdata("3");
//                    PromptManager.showToast(getApplicationContext(), "支付失败");

                    break;
            }
        }

    }

//    public static void setonpay(inter_pay inter_pay) {
//        pay = (PayActivity) inter_pay;
//    }

    public interface inter_pay {
        public void getdata(String data);
    }

}