package com.taotaohai.activity.base;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.taotaohai.ConstantValue;
import com.taotaohai.R;
import com.taotaohai.activity.Login;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.dilog_toast.CustomToast;
import com.taotaohai.httputil.Http;
import com.taotaohai.httputil.IHttp;
import com.taotaohai.listener.OnHttpListener;
import com.taotaohai.util.util;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

public class BaseFragment extends Fragment implements OnHttpListener {
    public static HashMap<String, String> has = new HashMap<>();
    /*网络接口*/
    IHttp iHttp;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iHttp = new Http(this);

    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    public void get(String url, final int code) {
        ((BaseActivity) getActivity()).showSpot();
//        Callback.Cancelable cancelable = Http.post(url, hashMap, this, code);请求可以取消
//        Http.post(url, hashMap, this, code);
        RequestParams p = new RequestParams(ConstantValue.URL + url);

        iHttp.Get(p, code);

    }


    public void Http(HttpMethod moth, String url, HashMap<String, String> hashMap, final int code) {
        ((BaseActivity) getActivity()).showSpot();
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

    public void post(String url, HashMap<String, String> hashMap, final int code) {
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

    public void Http(HttpMethod moth, String url, String BodyContent, final int code) {
//        Callback.Cancelable cancelable = Http.post(url, hashMap, this, code);请求可以取消
//        Http.post(url, hashMap, this, code);
        RequestParams p = new RequestParams(ConstantValue.URL + url);
        p.setBodyContent(BodyContent);
        iHttp.Put(moth, p, code);

    }

    @Override
    public void onSuccess(String data, int postcode) {

    }

    @Override
    public void onError(Throwable ex, int postcode) {
        String[] st = ex.toString().split("result:");
        if (st.length > 1) {
            util.isSuccess(util.getgson(st[1], BaseBean.class));
        }
        try {
            if (ex.toString().contains("401")) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("温馨提示");
                dialog.setMessage("是否进入登录页登录?");
                dialog.setNegativeButton("前往", (dialog1, which) -> {
                    startActivity(new Intent(getActivity(), Login.class));
                });
                dialog.setNeutralButton("取消", (dialog1, which) -> {
                });
                dialog.show();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onFinished(int code) {
        ((BaseActivity) getActivity()).setSpotNull();
    }

    public void inithttp() {

    }

    /**
     * Toast
     */
    protected void showToast(String st) {
        CustomToast.showNormalToast(getActivity(), st);
    }

    protected void addtocar(ImageView image_photo, ImageView imag_photo2) {
        int[] local = new int[2];
        image_photo.getLocationOnScreen(local);
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        imag_photo2.setVisibility(View.VISIBLE);
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(imag_photo2, "translationX",
                local[0], local[0]);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(imag_photo2, "translationY",
                local[1], 100);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(imag_photo2, "alpha",
                1.0f, 0.5f);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(imag_photo2, "scaleX",
                1.0f, 0f);
        ObjectAnimator anim5 = ObjectAnimator.ofFloat(imag_photo2, "scaleY",
                1.0f, 0f);
//        ObjectAnimator anim3 = ObjectAnimator.ofFloat(image_photo,
//                "x", cx, 0f);
//        ObjectAnimator anim4 = ObjectAnimator.ofFloat(image_photo,
//                "x", cx);

        /**
         * anim1，anim2,anim3同时执行
         * anim4接着执行
         */
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2).with(anim3).with(anim4).with(anim5);
        animSet.setDuration(1000);
        animSet.start();

    }
    protected void addview(LayoutInflater inflater,RelativeLayout view, ViewGroup container){
        View view2 = inflater.inflate(R.layout.optimize, container, false);
        view.addView(view2);
    }
}
