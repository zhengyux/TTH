package com.taotaohai.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;

public class Banner extends BaseActivity {

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        mWebView = (WebView) findViewById(R.id.webview);
        initWebView();
        mWebView.loadUrl(getintent("url"));
    }
}
