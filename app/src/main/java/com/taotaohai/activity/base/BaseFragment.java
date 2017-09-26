package com.taotaohai.activity.base;


import android.app.PendingIntent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.taotaohai.ConstantValue;
import com.taotaohai.activity.Home;
import com.taotaohai.dilog_toast.CustomToast;
import com.taotaohai.httputil.Http;
import com.taotaohai.httputil.IHttp;
import com.taotaohai.listener.OnHttpListener;


import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

public class BaseFragment extends Fragment implements OnHttpListener {

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
        ((BaseActivity)getActivity()).showSpot();
//        Callback.Cancelable cancelable = Http.post(url, hashMap, this, code);请求可以取消
//        Http.post(url, hashMap, this, code);
        RequestParams p = new RequestParams(ConstantValue.URL + url);

        iHttp.Get(p, code);

    }


    public void Http(HttpMethod moth, String url, HashMap<String, String> hashMap, final int code) {

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

    }

    @Override
    public void onFinished(int code) {
        ((BaseActivity)getActivity()).setSpotNull();
    }

    public void inithttp() {

    }

    /**
     * Toast
     */
    protected void showToast(String st) {
        CustomToast.showNormalToast(getActivity(), st);
    }
}
