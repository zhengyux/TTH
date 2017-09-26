package com.taotaohai.listener;

import java.io.File;

/**
 * Created by Administrator on 2017/2/10.
 */

public interface OnHttpDownListener {
    public void onSuccess(File result, int postcode);

    public void onError(Throwable ex,int postcode);

    public void onFinished(int postcode);

    public void onLoading(int postcode, long total, long current, boolean isDownloading);

}
