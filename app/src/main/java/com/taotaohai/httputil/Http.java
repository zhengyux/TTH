package com.taotaohai.httputil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.taotaohai.ConstantValue;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.listener.OnHttpDownListener;
import com.taotaohai.listener.OnHttpListener;
import com.taotaohai.util.FileSizeUtil;
import com.taotaohai.util.MD5Utils;
import com.taotaohai.util.SPUtils;
import com.taotaohai.util.util;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */

public class Http implements IHttp {
    private OnHttpListener mOnloginListener;
    private OnHttpDownListener mOnHttpDownListener;

    public Http(OnHttpListener mOnloginListener, OnHttpDownListener mOnHttpDownListener) {
        this.mOnloginListener = mOnloginListener;
        this.mOnHttpDownListener = mOnHttpDownListener;

    }

    public Http(OnHttpListener mOnloginListener) {
        this.mOnloginListener = mOnloginListener;
    }

    public Http(OnHttpDownListener mOnHttpDownListener) {
        this.mOnHttpDownListener = mOnHttpDownListener;
    }

    //post请求
    @Override
    public Callback.Cancelable Post(RequestParams params, final int postcode) {
        params.setConnectTimeout(1000 * 10);
        Callback.Cancelable cancelable = x.http().post(params, new MyCallback(postcode));
        return cancelable;
    }

    @Override
    public Callback.Cancelable Put(HttpMethod put, RequestParams params, int postcode) {
        params.setConnectTimeout(1000 * 10);
        Callback.Cancelable cancelable = x.http().request(put, params, new MyCallback(postcode));
        return cancelable;
    }

    //get请求
    @Override
    public Callback.Cancelable Get(RequestParams params, final int postcode) {
        params.setConnectTimeout(1000 * 10);
        Callback.Cancelable cancelable = x.http().get(params, new MyCallback(postcode));
        return cancelable;
    }

    //图片上传
    @Override
    public Callback.Cancelable sendImage(RequestParams params, final int postcode, String data, Context context, int max_width, int max_height, String fileName) {
        BitmapFactory.Options options = null;
        int inSimpleSize = 1;
        if (data == null) {
            Toast.makeText(context, "选择图片文件出错", Toast.LENGTH_SHORT).show();
            return null;
        }
        options = FileSizeUtil.getBitmapOptions(data);
        int imgMax = Math.max(options.outWidth, options.outHeight);
        if (max_width <= imgMax) {
            inSimpleSize = Math.max(max_width, imgMax) / Math.min(max_width, imgMax);
        }
        if (max_height <= imgMax) {
            inSimpleSize = inSimpleSize * Math.max(max_height, imgMax) / Math.min(max_height, imgMax);
        }
//        params.addBodyParameter("username", "18750222148");
//        params.addBodyParameter("password", MD5Utils.md5Password("123456"));
        params.addBodyParameter(fileName,
                FileSizeUtil.compressBitmap(context,
                        data,
                        Bitmap.CompressFormat.JPEG,
                        max_width,
                        max_height,
                        false)
        );
        Callback.Cancelable cancelable = x.http().post(params, new MyCallback(postcode));
        return cancelable;
    }

    @Override
    public Callback.Cancelable sendImages(RequestParams params, int postcode, List<String> data, Context context, int max_width, int max_height, String fileNames) {

//        params.addBodyParameter("username", "18750222148");
//        params.addBodyParameter("password", MD5Utils.md5Password("123456"));
        for (int i = 0; i < data.size(); i++) {
            params.addBodyParameter(fileNames,
                    FileSizeUtil.compressBitmap(context,
                            data.get(i),
                            Bitmap.CompressFormat.JPEG,
                            max_width,
                            max_height,
                            false)
            );
        }

        Callback.Cancelable cancelable = x.http().post(params, new MyCallback(postcode));
        return cancelable;
    }

    //文件上传
    @Override
    public Callback.Cancelable sendFile(RequestParams params, final int postcode, String path, String fileName) {

        params.addBodyParameter("username", "18750222148");
        params.addBodyParameter("password", MD5Utils.md5Password("123456"));
        params.addBodyParameter(fileName, new File(path), null);
        Callback.Cancelable cancelable = x.http().post(params, new MyCallback(postcode));
        return cancelable;
    }

    //文件下载
    @Override
    public Callback.Cancelable downloadFile(RequestParams params, final int postcode, final String savePath) {

        //自定义保存路径，Environment.getExternalStorageDirectory()：SD卡的根目录
        params.setSaveFilePath(savePath);
        //自动为文件命名
        params.setAutoRename(true);
        Callback.Cancelable cancelable = x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
//                //apk下载完成后，调用系统的安装方法
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
//                getActivity().startActivity(intent);
                mOnHttpDownListener.onSuccess(result, postcode);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mOnHttpDownListener.onError(ex, postcode);
                String[] st = ex.toString().split("result:");
                if (st.length > 1) {
                    util.isSuccess(util.getgson(st[1], BaseBean.class), (BaseActivity) mOnloginListener);
                }
                try {
                    if (ex.toString().contains("401")) {
                        if (SPUtils.get((BaseActivity) mOnloginListener, "username", null) != null) {
                            RequestParams p = new RequestParams(ConstantValue.URL + "api/auth/login");
                            p.addBodyParameter("username", (String) SPUtils.get((BaseActivity) mOnloginListener, "username", null));
                            p.addBodyParameter("password", (String) SPUtils.get((BaseActivity) mOnloginListener, "username", null));
                            Post(p, 0);
                        }

                    }
                } catch (Exception e) {
                }

            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                mOnHttpDownListener.onFinished(postcode);
            }

            //网络请求之前回调
            @Override
            public void onWaiting() {
            }

            //网络请求开始的时候回调
            @Override
            public void onStarted() {
            }

            //下载的时候不断回调的方法
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //当前进度和文件总大小
                mOnHttpDownListener.onLoading(postcode, total, current, isDownloading);
            }
        });
        return cancelable;
    }

    //不造同一个轮子
    class MyCallback implements Callback.CommonCallback<String> {
        int postcode;

        public MyCallback(int postcode) {
            this.postcode = postcode;
        }

        @Override
        public void onSuccess(String result) {
            mOnloginListener.onSuccess(result, postcode);
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            mOnloginListener.onError(ex, postcode);
            String[] st = ex.toString().split("result:");
            if (st.length > 1) {
                util.isSuccess(util.getgson(st[1], BaseBean.class), (BaseActivity) mOnloginListener);
            }
            try {
                if (ex.toString().contains("401")) {
                    if (SPUtils.get((BaseActivity) mOnloginListener, "username", null) != null) {
                        RequestParams p = new RequestParams(ConstantValue.URL + "api/auth/login");
                        p.addBodyParameter("username", (String) SPUtils.get((BaseActivity) mOnloginListener, "username", null));
                        p.addBodyParameter("password", (String) SPUtils.get((BaseActivity) mOnloginListener, "username", null));
                        Post(p, 0);
                    }

                }
            } catch (Exception e) {
            }

        }

        @Override
        public void onCancelled(CancelledException cex) {
            mOnloginListener.onFinished(postcode);
        }

        @Override
        public void onFinished() {
            mOnloginListener.onFinished(postcode);
        }
    }
}
