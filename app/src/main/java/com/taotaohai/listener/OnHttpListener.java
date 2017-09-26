package com.taotaohai.listener;
/**
 * Created by Administrator on 2017/2/10.
 */

public interface OnHttpListener {
    public void onSuccess(String data, int postcode);
    public void onError(Throwable ex, int postcode);
    public void onFinished(int postcode);

}
