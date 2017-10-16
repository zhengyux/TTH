package com.taotaohai.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class HotClass {


    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private List<Data> data;
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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
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
        @SerializedName("classId")
        private String classId;
        @SerializedName("classInfo")
        private String classInfo;
        @SerializedName("creatorId")
        private String creatorId;
        @SerializedName("desck")
        private String desck;
        @SerializedName("descv")
        private String descv;
        @SerializedName("gmtCreate")
        private String gmtCreate;
        @SerializedName("gmtModify")
        private String gmtModify;
        @SerializedName("id")
        private String id;
        @SerializedName("imageIds")
        private String imageIds;
        @SerializedName("imagesUrl")
        private String imagesUrl;
        @SerializedName("imgId")
        private String imgId;
        @SerializedName("lastChangeUser")
        private String lastChangeUser;
        @SerializedName("price")
        private String price;
        @SerializedName("quality")
        private int quality;
        @SerializedName("remark")
        private String remark;
        @SerializedName("shopId")
        private String shopId;
        @SerializedName("shopInfo")
        private String shopInfo;
        @SerializedName("sourceId")
        private String sourceId;
        @SerializedName("sourceText")
        private String sourceText;
        @SerializedName("sourceVideo")
        private String sourceVideo;
        @SerializedName("sourceVideoUrl")
        private String sourceVideoUrl;
        @SerializedName("title")
        private String title;
        @SerializedName("unit")
        private String unit;
        @SerializedName("unitMin")
        private int unitMin;

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getClassInfo() {
            return classInfo;
        }

        public void setClassInfo(String classInfo) {
            this.classInfo = classInfo;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public String getDesck() {
            return desck;
        }

        public void setDesck(String desck) {
            this.desck = desck;
        }

        public String getDescv() {
            return descv;
        }

        public void setDescv(String descv) {
            this.descv = descv;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageIds() {
            return imageIds;
        }

        public void setImageIds(String imageIds) {
            this.imageIds = imageIds;
        }

        public String getImagesUrl() {
            return imagesUrl;
        }

        public void setImagesUrl(String imagesUrl) {
            this.imagesUrl = imagesUrl;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getQuality() {
            return quality;
        }

        public void setQuality(int quality) {
            this.quality = quality;
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

        public String getShopInfo() {
            return shopInfo;
        }

        public void setShopInfo(String shopInfo) {
            this.shopInfo = shopInfo;
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getSourceText() {
            return sourceText;
        }

        public void setSourceText(String sourceText) {
            this.sourceText = sourceText;
        }

        public String getSourceVideo() {
            return sourceVideo;
        }

        public void setSourceVideo(String sourceVideo) {
            this.sourceVideo = sourceVideo;
        }

        public String getSourceVideoUrl() {
            return sourceVideoUrl;
        }

        public void setSourceVideoUrl(String sourceVideoUrl) {
            this.sourceVideoUrl = sourceVideoUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getUnitMin() {
            return unitMin;
        }

        public void setUnitMin(int unitMin) {
            this.unitMin = unitMin;
        }
    }
}
