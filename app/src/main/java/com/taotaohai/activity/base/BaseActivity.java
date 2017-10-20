package com.taotaohai.activity.base;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.taotaohai.ConstantValue;
import com.taotaohai.GlobalParams;
import com.taotaohai.MyApplication;
import com.taotaohai.R;

import com.taotaohai.dilog_toast.CustomToast;
import com.taotaohai.dilog_toast.SpotsDialog;
import com.taotaohai.httputil.Http;
import com.taotaohai.httputil.IHttp;
import com.taotaohai.listener.OnHttpListener;
import com.taotaohai.util.AndroidBug54971Workaround;

import com.taotaohai.widgets.MultipleStatusView;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.taotaohai.GlobalParams.NET_CODE;


/**
 * 时间2017 05 10
 * 创建人：xzk
 * 包括：网络，dialog，toast，spoltdialog，
 * pullrecylerview+adapter，application对activity的管理...
 */
public abstract class BaseActivity extends AutoLayoutActivity implements OnHttpListener {
    TextView tv_title;
    TextView tv_two;
    public static HashMap<String, String> has = new HashMap<>();
    /*dialog*/
    Dialog dialog;

    public void setObject(Object object) {
        this.object = object;
    }

    Object object = "123";
    /**
     * @param savedInstanceState 用到了application对activity的管理
     */
    /*网络接口*/
    IHttp iHttp;
    public MultipleStatusView mMsvLayout;

    protected abstract void inithttp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        2);
            }
            i = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                        2);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    1);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        setContentView(R.layout.activity_base);
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setNavigationBarColor(Color.TRANSPARENT);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }

        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        iHttp = new Http(this);
//        iHttp = new Http(this, this);//要实现下载接口的时候用这个
        ((MyApplication) getApplication()).addActivity(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mMsvLayout = (MultipleStatusView) findViewById(R.id.msv_layout);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content));

    }

    @Override
    public void onError(Throwable ex, int postcode) {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void removeAllActivity() {
        ((MyApplication) getApplication()).removeAllActivity();
    }

    public void RemoveAllActivityExceptThis(Activity activity) {
        ((MyApplication) getApplication()).RemoveAllActivityExceptThis(activity);
    }
//    /*沉浸模式*/
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }

    public void post(String url, HashMap<String, String> hashMap, final int code) {
        showSpot();
//        Callback.Cancelable cancelable = Http.post(url, hashMap, this, code);请求可以取消
//        Http.post(url, hashMap, this, code);
        RequestParams p = new RequestParams(ConstantValue.URL + url);
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            p.addBodyParameter(key, val);
        }

        iHttp.Post(p, code);

    }

    public void put(String url, HashMap<String, String> hashMap, final int code) {
//        showSpot();
//        Callback.Cancelable cancelable = Http.post(url, hashMap, this, code);请求可以取消
//        Http.post(url, hashMap, this, code);
        RequestParams p = new RequestParams(ConstantValue.URL + url);
        if (hashMap != null) {
            Iterator iter = hashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                p.addBodyParameter(key, val);
            }
        }
        iHttp.Put(HttpMethod.PUT, p, code);
    }

    public void Http(HttpMethod moth, String url, HashMap<String, String> hashMap, final int code) {
        showSpot();
//        Callback.Cancelable cancelable = Http.post(url, hashMap, this, code);请求可以取消
//        Http.post(url, hashMap, this, code);
        RequestParams p = new RequestParams(ConstantValue.URL + url);
        if (hashMap != null) {
            Iterator iter = hashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                p.addBodyParameter(key, val);
            }
        }
        iHttp.Put(moth, p, code);

    }

    public void Http(HttpMethod moth, String url, String BodyContent, final int code) {
        showSpot();
//        Callback.Cancelable cancelable = Http.post(url, hashMap, this, code);请求可以取消
//        Http.post(url, hashMap, this, code);
        RequestParams p = new RequestParams(ConstantValue.URL + url);
        p.setBodyContent(BodyContent);
        iHttp.Put(moth, p, code);

    }

    public void Http(HttpMethod moth, String url, final int code) {
        showSpot();
//        Callback.Cancelable cancelable = Http.post(url, hashMap, this, code);请求可以取消
//        Http.post(url, hashMap, this, code);
        RequestParams p = new RequestParams(ConstantValue.URL + url);
        iHttp.Put(moth, p, code);
    }

    public void sendImage(String url, HashMap<String, String> hashMap, final int postcode, String data, Context context, int max_width, int max_height, String fileName) {
        if (postcode != GlobalParams.NOSPOT_CODE) {
            showSpot();
        }
//        Callback.Cancelable cancelable = Http.post(url, hashMap, this, code);请求可以取消
//        Http.post(url, hashMap, this, code);
        RequestParams p = new RequestParams(ConstantValue.URL + url);
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            p.addBodyParameter(key, val);
        }
        iHttp.sendImage(p, postcode, data, context, max_width, max_height, fileName);
    }

    public void sendImages(String url, HashMap<String, String> hashMap, final int postcode, List<String> data, Context context, int max_width, int max_height, String fileName) {
        if (postcode != GlobalParams.NOSPOT_CODE) {
            showSpot();
        }
//        Callback.Cancelable cancelable = Http.post(url, hashMap, this, code);请求可以取消
//        Http.post(url, hashMap, this, code);
        RequestParams p = new RequestParams(ConstantValue.URL + url);
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            p.addBodyParameter(key, val);
        }
        iHttp.sendImages(p, postcode, data, context, max_width, max_height, fileName);
    }

    public void get(String url, final int code) {
        showSpot();
//        Callback.Cancelable cancelable = Http.post(url, hashMap, this, code);请求可以取消
//        Http.post(url, hashMap, this, code);
        RequestParams p = new RequestParams(ConstantValue.URL + url);

        iHttp.Get(p, code);

    }

    public void get(String url) {
        showSpot();
//        Callback.Cancelable cancelable = Http.post(url, hashMap, this, code);请求可以取消
//        Http.post(url, hashMap, this, code);
        RequestParams p = new RequestParams(ConstantValue.URL + url);

        iHttp.Get(p, 0);

    }


    /**
     * 公用title
     */
    protected void setTitle(String title) {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RelativeLayout relativeLayout6 = (RelativeLayout) findViewById(R.id.relativeLayout6);
        if (Build.VERSION.SDK_INT >= 21) {
            relativeLayout6.setMinimumWidth(130);
        }
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(title);
    }

    protected void setTitleTwo(String titleTwo) {
        tv_two = (TextView) findViewById(R.id.tv_two);
        tv_two.setText(titleTwo);
        tv_two.setVisibility(View.VISIBLE);
        tv_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnTow();
            }
        });
    }

    protected void OnTow() {

    }

    /**
     * Toast
     */
    protected void showToast(String st) {
        CustomToast.showNormalToast(this, st);
    }

    /**
     * Toast可自定义样式
     */
    protected void showToastSpecial(String st) {
        View toastRoot = LayoutInflater.from(this).inflate(R.layout.toast_layout, null);
        TextView message = (TextView) toastRoot.findViewById(R.id.message);
        message.setText(st);
        //Toast可设置位置
        CustomToast.showDiverseToast(this, toastRoot, Gravity.CENTER);
    }

    /**
     * 一般dialog
     */
    protected void showDialog(String st, String title) {
        backgroundAlpha(0.5f);
        dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_layout);
        TextView textView = (TextView) dialog.findViewById(R.id.information);
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        tv_title.setText(title);
        textView.setText(st);
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                backgroundAlpha(1);
            }
        });
        dialog.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(1);
                sure();
            }
        });
        dialog.show();
    }
    protected void showDialog2(String st, String title) {
        backgroundAlpha(0.5f);
        dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_layout2);
        TextView textView = (TextView) dialog.findViewById(R.id.information);
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        tv_title.setText(title);
        textView.setText(st);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                backgroundAlpha(1);
            }
        });
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sure();
            }
        });
        dialog.show();
    }

    protected void showDialog(String st, final int code) {
        dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog);
        TextView textView = (TextView) dialog.findViewById(R.id.information);
        textView.setText(st);
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sure(code);
            }
        });
        dialog.show();
    }


    /*dialog点击确定，用dialog记得重新这个方法*/
    protected void sure() {
        dialog.dismiss();
    }

    protected void sure(int code) {
        if (code == NET_CODE) {
            inithttp();
        }
        dialog.dismiss();
    }

    protected SpotsDialog spotdialog;

    protected void setSpotNull() {
        if (spotdialog != null) {
            spotdialog.dismiss();
            spotdialog = null;
        }
    }

    protected void showSpot() {
        if (spotdialog != null) {
            if (spotdialog.isShowing()) {
                return;
            }
        }
        spotdialog = new SpotsDialog(this);
        spotdialog.show();
    }

    /*ondestory的时候对activity进行回收*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((MyApplication) getApplication()).removeActivity(this);
        if (object != null) {
            object = null;
        }
        spotdialog = null;
        dialog = null;
        iHttp = null;
        tv_two = null;
        tv_title = null;
        mWebView = null;
        iHttp = null;
    }

    /**
     * 网络请求
     */
    @Override
    public void onFinished(int code) {
        setSpotNull();
        if (object == null) {

        }
    }

    @Override
    public void onSuccess(String result, int postcode) {

    }

    protected void loginStata(String st) {

    }

    int QR_WIDTH = 500;
    int QR_HEIGHT = 500;


    protected WebView mWebView;

    protected void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        /* 设置为true表示支持使用js打开新的窗口 */
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
         /* 大部分网页需要自己保存一些数据,这个时候就的设置下面这个属性 */
        mWebView.getSettings().setDomStorageEnabled(true);
         /* 设置为使用webview推荐的窗口 */
        mWebView.getSettings().setUseWideViewPort(true);
         /* 设置网页自适应屏幕大小 ---这个属性应该是跟上面一个属性一起用 */
        mWebView.getSettings().setLoadWithOverviewMode(true);
        /* HTML5的地理位置服务,设置为true,启用地理定位 */
        mWebView.getSettings().setGeolocationEnabled(true);
        /* 设置是否允许webview使用缩放的功能,我这里设为false,不允许 */
        mWebView.getSettings().setBuiltInZoomControls(false);
        /* 提高网页渲染的优先级 */
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        /* 设置显示水平滚动条,就是网页右边的滚动条.我这里设置的不显示 */
        mWebView.setHorizontalScrollBarEnabled(true);
        /* 指定垂直滚动条是否有叠加样式 */
        mWebView.setVerticalScrollbarOverlay(true);
        /* 设置滚动条的样式 */
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    public String getintent(String st) {
        if (getIntent().getStringExtra(st) == null) {
            return "";
        }
        return getIntent().getStringExtra(st);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) { //按下的如果是BACK，同时没有重复
//            finish();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
