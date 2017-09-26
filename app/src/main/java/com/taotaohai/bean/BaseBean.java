package com.taotaohai.bean;

/**
 * Created by Administrator on 2017/5/19.
 */

public class BaseBean {
    protected String message;
    protected boolean success;
    protected String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
