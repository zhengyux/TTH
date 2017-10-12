package com.taotaohai.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/10/12.
 */

public class Contact {


    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private String data;
    @SerializedName("message")
    private String message;
    @SerializedName("success")
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
