package com.taotaohai.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.util.ScreenListener;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

public class bannerr extends BaseActivity {


    private ScreenListener screenListener ;
    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bannerr);
        mWebView = (WebView) findViewById(R.id.webview);
        initWebView();
        mWebView.loadUrl(getintent("url"));
        screen();
    }

    private void screen(){

        screenListener = new ScreenListener(getApplicationContext()) ;
        screenListener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                mWebView.onResume();
            }

            @Override
            public void onScreenOff() {
                mWebView.onPause();
            }

            @Override
            public void onUserPresent() {

            }
        });

    }


    @Override
    protected void onDestroy() {
        mWebView.destroy();
        mWebView = null;
        super.onDestroy();
    }
}
