package com.taotaohai.util;

import android.content.Context;
import android.text.TextUtils;

import com.taotaohai.bean.BaseBean;
import com.google.gson.Gson;


import java.lang.reflect.Type;
import java.text.DecimalFormat;

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

    public static boolean isSuccess(String result) {
        Gson gson = new Gson();
        BaseBean baseBean = gson.fromJson(result, (Type) BaseBean.class);
        if (baseBean.isSuccess()) {
            return true;
        }
        return false;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0,5-9]))\\d{8}$";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    private final static double PI = 3.14159265358979323; // 圆周率
    private final static double R = 6371229; // 地球的半径

    public static double getDistance(double longt1, double lat1, double longt2, double lat2) {
        double x, y, distance;
        x = (longt2 - longt1) * PI * R
                * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
        y = (lat2 - lat1) * PI * R / 180;
        distance = Math.hypot(x, y);
        return distance;
    }

    public static String getdouboletwo(double longt1) {
        DecimalFormat df = new DecimalFormat("#####0.0");
        return df.format(longt1);
    }
}

