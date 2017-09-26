package com.taotaohai.util;

import android.content.Context;

import com.taotaohai.bean.BaseBean;
import com.google.gson.Gson;


import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/5/19.
 */

public class util {
    static public <T> T getgson(String st, Class<T> tClass) {
        Gson gson = new Gson();
        T t = gson.fromJson(st, (Type) tClass);
        return t;
    }

    static public <T> T getgson(String st) {
        Gson gson = new Gson();
        T t = gson.fromJson(st, (Type) BaseBean.class);
        return t;
    }

    public static boolean isSuccess(BaseBean baseBean, Context context) {
        if (baseBean.getMessage() != null && baseBean.getMessage().length() > 1) {
            T.showShort(context, baseBean.getMessage());
        }
        if (baseBean.isSuccess()) {
            return true;
        }
        return false;
    }

    public static boolean isSuccess(BaseBean baseBean) {

        if (baseBean.isSuccess()) {
            return true;
        }
        return false;
    }
}
