package com.taotaohai;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Intent;

import com.taotaohai.activity.Home;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

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

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false); //是否输出debug日志，开启debug会影响性能。
//        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        UMShareAPI.get(this);
        Config.DEBUG = true;

    }

    {
//        PlatformConfig.setWeixin("微信appid", "微信appsecret");
//        PlatformConfig.setQQZone("qqappid", "QQappsecret");
//        PlatformConfig.setSinaWeibo("新浪appid", "新浪appsecret", "新浪回调地址");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");


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

}
