package com.taotaohai.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class Book extends BaseBean {

    /*   "acount":1,
                "color":"白色",
                "gmtCreate":null,
                "gmtDelivery":null,
                "gmtModify":null,
                "goodsId":"1",
                "goodsName":"连衣裙",
                "goodsSpec":"",
                "id":"",
                "lastChangeUser":"",
                "linkAddress":"",
                "linkName":"",
                "linkTel":"",
                "md5":"f4b69af0b284e4c5ac05b6b9976843ca",
                "orderExpressCompany":"",
                "orderExpressNo":"",
                "orderId":"1",
                "orderStatus":3,
                "payType":"",
                "price":0,
                "properties":null,
                "shopName":"优衣库（湖里）",
                "size":"M",
                "submitTime":null,
                "totalPrice":99.99,
                "username":""*/
    private List<Data> data;

    public Book(List<Integer> list) {
        super();
        if (data == null) {
            data = new ArrayList<>();
        }
        for (int i = 0; i < list.size(); i++) {
            data.add(new Data().setCount(list.get(i)));
        }
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {
        private int count;

        public int getCount() {
            return count;
        }

        public Data setCount(int count) {
            this.count = count;
            return this;
        }

        private int acount;
        private String color;
        private String gmtCreate;
        private String gmtDelivery;
        private String gmtModify;
        private String gmtRefund;
        private String goodsId;
        private String goodsName;
        private String goodsSpec;
        private String id;
        private String lastChangeUser;
        private String linkAddress;
        private String linkName;
        private String linkTel;
        private String md5;
        private String orderExpressCompany;
        private String orderExpressNo;
        private String orderId;
        private int orderStatus;
        private String pageIndex;
        private String pageSize;
        private String payType;
        private String price;
        private String properties;
        private String refundStatus;
        private String shopName;
        private String size;
        private String submitTime;
        private String totalPrice;
        private String username;

        public String getGmtRefund() {
            return gmtRefund;
        }

        public void setGmtRefund(String gmtRefund) {
            this.gmtRefund = gmtRefund;
        }

        public String getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(String pageIndex) {
            this.pageIndex = pageIndex;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public String getRefundStatus() {
            return refundStatus;
        }

        public void setRefundStatus(String refundStatus) {
            this.refundStatus = refundStatus;
        }

        public int getAcount() {
            return acount;
        }

        public void setAcount(int acount) {
            this.acount = acount;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
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

        public String getGoodsSpec() {
            return goodsSpec;
        }

        public void setGoodsSpec(String goodsSpec) {
            this.goodsSpec = goodsSpec;
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

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
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

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProperties() {
            return properties;
        }

        public void setProperties(String properties) {
            this.properties = properties;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
