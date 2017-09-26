package com.taotaohai.bean;

/**
 * Created by Administrator on 2017/5/19.
 */

public class BaseBean {
    protected String message;
    protected boolean success;
    protected int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
