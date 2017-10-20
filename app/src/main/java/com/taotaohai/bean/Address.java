package com.taotaohai.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */

public class Address {

    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private Data2 data;
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

    public Data2 getData() {
        return data;
    }

    public void setData(Data2 data) {
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

    public static class Data {
        @SerializedName("gmtCreate")
        private String gmtCreate;
        @SerializedName("gmtModify")
        private String gmtModify;
        @SerializedName("id")
        private String id;
        @SerializedName("isdefault")
        private boolean isdefault;
        @SerializedName("linkAddress")
        private String linkAddress;
        @SerializedName("linkArea")
        private String linkArea;
        @SerializedName("linkCity")
        private String linkCity;
        @SerializedName("linkName")
        private String linkName;
        @SerializedName("linkProvince")
        private String linkProvince;
        @SerializedName("linkTel")
        private String linkTel;
        @SerializedName("userId")
        private String userId;

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getGmtModify() {
            return gmtModify;
        }

        public void setGmtModify(String gmtModify) {
            this.gmtModify = gmtModify;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean getIsdefault() {
            return isdefault;
        }

        public void setIsdefault(boolean isdefault) {
            this.isdefault = isdefault;
        }

        public String getLinkAddress() {
            return linkAddress;
        }

        public void setLinkAddress(String linkAddress) {
            this.linkAddress = linkAddress;
        }

        public String getLinkArea() {
            return linkArea;
        }

        public void setLinkArea(String linkArea) {
            this.linkArea = linkArea;
        }

        public String getLinkCity() {
            return linkCity;
        }

        public void setLinkCity(String linkCity) {
            this.linkCity = linkCity;
        }

        public String getLinkName() {
            return linkName;
        }

        public void setLinkName(String linkName) {
            this.linkName = linkName;
        }

        public String getLinkProvince() {
            return linkProvince;
        }

        public void setLinkProvince(String linkProvince) {
            this.linkProvince = linkProvince;
        }

        public String getLinkTel() {
            return linkTel;
        }

        public void setLinkTel(String linkTel) {
            this.linkTel = linkTel;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class Data2 {
        @SerializedName("data")
        private List<Data> data;
        @SerializedName("total")
        private int total;

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
