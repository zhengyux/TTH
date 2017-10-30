package com.taotaohai.activity;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.utils.Log;

import android.app.Activity;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Log.d("546746546", "onPayFinish,errCode=" + resp.errCode);
//            AlertDialog.Builderbuilder=newAlertDialog.Builder(this);
//            builder.setTitle(R.string.app_tip);
        }
    }
}