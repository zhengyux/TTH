package com.taotaohai;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.hyphenate.easeui.EaseUI;
import com.mob.MobSDK;
import com.taotaohai.activity.Home;
import com.tencent.TIMConnListener;
import com.tencent.TIMConversation;
import com.tencent.TIMGroupReceiveMessageOpt;
import com.tencent.TIMLogLevel;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushListener;
import com.tencent.TIMOfflinePushNotification;
import com.tencent.TIMRefreshListener;
import com.tencent.TIMUserStatusListener;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.qalsdk.sdk.MsfSdkUtils;

import org.xutils.x;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Administrator on 2017/5/9.
 */

public class MyApplication extends Application {

    private List<Activity> activitys = new LinkedList<Activity>();
    private List<Service> services = new LinkedList<Service>();
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        MobSDK.init(this,"23dc05ae13bb0","3eb259ebc797e2837c186f481ea90581");
        x.Ext.init(this);
        x.Ext.setDebug(false); //是否输出debug日志，开启debug会影响性能。
//        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        IWXAPI wxApi;
        wxApi = WXAPIFactory.createWXAPI(this, ConstantValue.APP_ID, true);
        wxApi.registerApp(ConstantValue.APP_ID);

        EaseUI.getInstance().init(this,null);
        initTecentIM();
    }


    public static Context getContext() {
        return context;
    }

    //管理application
    public void addActivity(Activity activity) {
        activitys.add(activity);
    }

    public Activity getActivity() {
        return activitys.get(activitys.size() - 1);
    }

    public void removeActivity(Activity activity) {
        activitys.remove(activity);
        finisActivity(activity);
    }

    public void removeAllActivity() {
        ListIterator<Activity> iterator = activitys.listIterator();
        while (iterator.hasNext()) {
            Activity activity2 = iterator.next();
            if (activity2 != null) {
                activity2.finish();
            }
        }
        activitys.clear();
    }

    public void RemoveAllActivityExceptThis(Activity activity) {
        ListIterator<Activity> iterator = activitys.listIterator();
        while (iterator.hasNext()) {
            Activity activity2 = iterator.next();
            if (activity2 != null && activity != activity2) {
                activity2.finish();
            }
        }
    }


    public void addService(Service service) {
        services.add(service);
    }

    public void removeService(Service service) {
        services.remove(service);
    }

    public void closeApplication() {
        closeActivitys();
        closeServices();
    }

    private void closeActivitys() {
        ListIterator<Activity> iterator = activitys.listIterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && !activity.getClass().getSimpleName().equals(Home.class.getSimpleName())) {
                activity.finish();
            }
        }
    }

    public void closeAllActivity() {
        ListIterator<Activity> iterator = activitys.listIterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            activity.finish();
        }
    }

    private void closeServices() {
        ListIterator<Service> iterator = services.listIterator();
        while (iterator.hasNext()) {
            Service service = iterator.next();
            if (service != null) {
                stopService(new Intent(this, service.getClass()));
            }
        }
    }

    //关闭指定activity
    private final void finisActivity(Activity activity) {
        if (activity != null) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    //关闭指定类名activity
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activitys) {
            if (activity.getClass().equals(cls)) {
                finisActivity(activity);
            }
        }
    }
    private void initTecentIM(){

        if(MsfSdkUtils.isMainProcess(this)) {
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify){
                        //消息被设置为需要提醒
                        notification.doNotify(getApplicationContext(), R.mipmap.ic_launcher);
                    }
                }
            });
        }
//
//        //初始化SDK基本配置                   "sdkAppId"
//        TIMSdkConfig config = new TIMSdkConfig(ConstantValue.SDKAPP_ID)
//                .enableCrashReport(false)
//                .enableLogPrint(true)
//                .setLogLevel(TIMLogLevel.DEBUG)
//                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");
//
//初始化SDK
        TIMManager.getInstance().init(getApplicationContext(), ConstantValue.SDKAPP_ID);
    }

    private void setUserConfig(){

    }



}
