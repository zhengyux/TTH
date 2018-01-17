package com.taotaohai.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class Book implements Serializable {


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

    public Data2 getData2() {
        return data;
    }

    public void setData2(Data2 data2) {
        this.data = data2;
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

    public static class Ext implements Serializable {


        @SerializedName("dealId")
        private String dealId;
        @SerializedName("acount")
        private int acount;
        @SerializedName("dealTime")
        private String dealTime;
        @SerializedName("gmtCreate")
        private String gmtCreate;
        @SerializedName("gmtDelivery")
        private String gmtDelivery;
        @SerializedName("gmtModify")
        private String gmtModify;
        @SerializedName("goodsName")
        private String goodsName;
        @SerializedName("id")
        private String id;
        @SerializedName("imgId")
        private String imgId;
        @SerializedName("lastChangeUser")
        private String lastChangeUser;
        @SerializedName("linkAddress")
        private String linkAddress;
        @SerializedName("linkName")
        private String linkName;
        @SerializedName("linkTel")
        private String linkTel;
        @SerializedName("name")
        private String name;
        @SerializedName("orderExpressCompany")
        private String orderExpressCompany;
        @SerializedName("orderExpressNo")
        private String orderExpressNo;
        @SerializedName("orderId")
        private String orderId;
        @SerializedName("orderStatus")
        private int orderStatus;
        @SerializedName("payType")
        private int payType;
        @SerializedName("price")
        private String price;
        @SerializedName("refundImgId")
        private String refundImgId;
        @SerializedName("refundReason")
        private String refundReason;
        @SerializedName("refundReasonDetail")
        private String refundReasonDetail;
        @SerializedName("refundType")
        private int refundType;
        @SerializedName("remark")
        private String remark;
        @SerializedName("shopName")
        private String shopName;
        @SerializedName("totalPrice")
        private int totalPrice;
        @SerializedName("unit")
        private String unit;


        public String getDealId() {
            return dealId;
        }

        public void setDealId(String dealId) {
            this.dealId = dealId;
        }


        public int getAcount() {
            return acount;
        }

        public void setAcount(int acount) {
            this.acount = acount;
        }

        public String getDealTime() {
            return dealTime;
        }

        public void setDealTime(String dealTime) {
            this.dealTime = dealTime;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getGmtDelivery() {
            return gmtDelivery;
        }

        public void setGmtDelivery(String gmtDelivery) {
            this.gmtDelivery = gmtDelivery;
        }

        public String getGmtModify() {
            return gmtModify;
        }

        public void setGmtModify(String gmtModify) {
            this.gmtModify = gmtModify;
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

        public String getLastChangeUser() {
            return lastChangeUser;
        }

        public void setLastChangeUser(String lastChangeUser) {
            this.lastChangeUser = lastChangeUser;
        }

        public String getLinkAddress() {
            return linkAddress;
        }

        public void setLinkAddress(String linkAddress) {
            this.linkAddress = linkAddress;
        }

        public String getLinkName() {
            return linkName;
        }

        public void setLinkName(String linkName) {
            this.linkName = linkName;
        }

        public String getLinkTel() {
            return linkTel;
        }

        public void setLinkTel(String linkTel) {
            this.linkTel = linkTel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrderExpressCompany() {
            return orderExpressCompany;
        }

        public void setOrderExpressCompany(String orderExpressCompany) {
            this.orderExpressCompany = orderExpressCompany;
        }

        public String getOrderExpressNo() {
            return orderExpressNo;
        }

        public void setOrderExpressNo(String orderExpressNo) {
            this.orderExpressNo = orderExpressNo;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRefundImgId() {
            return refundImgId;
        }

        public void setRefundImgId(String refundImgId) {
            this.refundImgId = refundImgId;
        }

        public String getRefundReason() {
            return refundReason;
        }

        public void setRefundReason(String refundReason) {
            this.refundReason = refundReason;
        }

        public String getRefundReasonDetail() {
            return refundReasonDetail;
        }

        public void setRefundReasonDetail(String refundReasonDetail) {
            this.refundReasonDetail = refundReasonDetail;
        }

        public int getRefundType() {
            return refundType;
        }

        public void setRefundType(int refundType) {
            this.refundType = refundType;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }

    public static class Data implements Serializable {
        private int count;

        @SerializedName("ext")
        private Ext ext;
        @SerializedName("gmtCreate")
        private String gmtCreate;
        @SerializedName("gmtModify")
        private String gmtModify;
        @SerializedName("gmtRefund")
        private String gmtRefund;
        @SerializedName("gmtRemaining")
        private int gmtRemaining;
        @SerializedName("goodsId")
        private String goodsId;
        @SerializedName("id")
        private String id;
        @SerializedName("lastChangeUser")
        private String lastChangeUser;
        @SerializedName("orderStatus")
        private int orderStatus;
        @SerializedName("payType")
        private int payType;
        @SerializedName("refundStatus")
        private int refundStatus;
        @SerializedName("remarks")
        private String remarks;
        @SerializedName("shopId")
        private String shopId;
        @SerializedName("submitTime")
        private String submitTime;
        @SerializedName("totalPrice")
        private String totalPrice;
        @SerializedName("userId")
        private String userId;
        @SerializedName("goodsInfo")
        private goodsInfo goodsInfo;

        public Book.goodsInfo getGoodsInfo() {
            return goodsInfo;
        }

        public void setGoodsInfo(Book.goodsInfo goodsInfo) {
            this.goodsInfo = goodsInfo;
        }

        public int getGmtRemaining() {
            return gmtRemaining;
        }

        public void setGmtRemaining(int gmtRemaining) {
            this.gmtRemaining = gmtRemaining;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

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

        public String getGmtRefund() {
            return gmtRefund;
        }

        public void setGmtRefund(String gmtRefund) {
            this.gmtRefund = gmtRefund;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLastChangeUser() {
            return lastChangeUser;
        }

        public void setLastChangeUser(String lastChangeUser) {
            this.lastChangeUser = lastChangeUser;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getRefundStatus() {
            return refundStatus;
        }

        public void setRefundStatus(int refundStatus) {
            this.refundStatus = refundStatus;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getSubmitTime() {
            return submitTime;
        }

        public void setSubmitTime(String submitTime) {
            this.submitTime = submitTime;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class Data2 implements Serializable {
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

    public static class goodsInfo implements Serializable {
        private String goosId;
        private String shopId;
        private String userId;

        public String getGoosId() {
            return goosId;
        }

        public void setGoosId(String goosId) {
            this.goosId = goosId;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }


}
