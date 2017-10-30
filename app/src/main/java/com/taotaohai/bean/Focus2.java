package com.taotaohai.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/10/23.
 */

public class Focus2 {
    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private boolean data;
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

    public boolean getData() {
        return data;
    }

    public void setData(boolean data) {
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
