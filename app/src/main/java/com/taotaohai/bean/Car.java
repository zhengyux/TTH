package com.taotaohai.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */

public class Car implements Serializable{


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

    public static class Data implements Serializable{
        @SerializedName("count")
        private int count;
        @SerializedName("goodsId")
        private String goodsId;
        @SerializedName("goodsName")
        private String goodsName;
        @SerializedName("id")
        private String id;
        @SerializedName("imgId")
        private String imgId;
        @SerializedName("price")
        private String price;
        @SerializedName("remark")
        private String remark;
        @SerializedName("shopId")
        private String shopId;
        @SerializedName("shopName")
        private String shopName;
        @SerializedName("unit")
        private String unit;
        @SerializedName("userId")
        private String userId;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgId() {
            return imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class Data2 implements Serializable{
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
