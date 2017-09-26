package com.taotaohai.dilog_toast;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {
    private static TextView mTextView;
    private static ImageView mImageView;

    public static void showDiverseToast(Context context, View view, int gravity) {
        //Toast的初始化
        Toast toastStart = new Toast(context);
        //获取屏幕高度  
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题  
        toastStart.setGravity(gravity, 0, height / 5);
        toastStart.setDuration(Toast.LENGTH_LONG);
        toastStart.setView(view);
        toastStart.show();
    }

    public static void showNormalToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}  