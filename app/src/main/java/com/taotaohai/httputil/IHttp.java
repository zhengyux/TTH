package com.taotaohai.httputil;

import android.content.Context;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */

public interface IHttp {
    public Callback.Cancelable Post(RequestParams params, int postcode);

    public Callback.Cancelable Put(HttpMethod put, RequestParams params, int postcode);

    public Callback.Cancelable Get(RequestParams params, int postcode);

    public Callback.Cancelable sendImage(RequestParams params, final int postcode, String data, Context context, int max_width, int max_height, String fileName);

    public Callback.Cancelable sendImages(RequestParams params, final int postcode, List<String> data, Context context, int max_width, int max_height, String fileNames);

    public Callback.Cancelable sendFile(RequestParams params, final int postcode, String path, String fileName);

    public Callback.Cancelable downloadFile(RequestParams params, final int postcode, final String savePath);

}
