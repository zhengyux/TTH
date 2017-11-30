package com.taotaohai.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class Goods {
    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private Data data;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
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

    public static class ClassInfo {
        @SerializedName("children")
        private String children;
        @SerializedName("className")
        private String className;
        @SerializedName("gmtCreate")
        private String gmtCreate;
        @SerializedName("gmtModify")
        private String gmtModify;
        @SerializedName("id")
        private String id;
        @SerializedName("lastChangeUser")
        private String lastChangeUser;
        @SerializedName("parentId")
        private String parentId;


        public String getChildren() {
            return children;
        }

        public void setChildren(String children) {
            this.children = children;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
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

        public String getLastChangeUser() {
            return lastChangeUser;
        }

        public void setLastChangeUser(String lastChangeUser) {
            this.lastChangeUser = lastChangeUser;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }
    }

    public static class User {
        @SerializedName("u_id")
        private String u_id;
        @SerializedName("name")
        private String name;
        @SerializedName("username")
        private String username;

        public String getU_id() {
            return u_id;
        }

        public void setU_id(String u_id) {
            this.u_id = u_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class ShopInfo {
        @SerializedName("address")
        private String address;
        @SerializedName("brandId")
        private String brandId;
        @SerializedName("brc")
        private String brc;
        @SerializedName("businessAbsUrlList")
        private List<String> businessAbsUrlList;
        @SerializedName("businessList")
        private List<String> businessList;
        @SerializedName("businessUrl")
        private String businessUrl;
        @SerializedName("creatorId")
        private String creatorId;
        @SerializedName("gmtCreate")
        private String gmtCreate;
        @SerializedName("gmtModify")
        private String gmtModify;
        @SerializedName("id")
        private String id;
        @SerializedName("lastChangeUser")
        private String lastChangeUser;
        @SerializedName("latitude")
        private String latitude;
        @SerializedName("legalName")
        private String legalName;
        @SerializedName("logo")
        private String logo;
        @SerializedName("logoId")
        private String logoId;
        @SerializedName("logoIdAbsUrl")
        private String logoIdAbsUrl;
        @SerializedName("longitude")
        private String longitude;
        @SerializedName("name")
        private String name;
        @SerializedName("principal")
        private String principal;
        @SerializedName("principalTel")
        private String principalTel;
        @SerializedName("remark")
        private String remark;
        @SerializedName("status")
        private int status;
        @SerializedName("totalBuy")
        private String totalBuy;
        @SerializedName("totalCommonLevel")
        private String totalCommonLevel;
        @SerializedName("totalGoods")
        private String totalGoods;
        @SerializedName("totalLike")
        private String totalLike;
        @SerializedName("totalSourceGoods")
        private String totalSourceGoods;
        @SerializedName("user")
        private User user;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getBrc() {
            return brc;
        }

        public void setBrc(String brc) {
            this.brc = brc;
        }

        public List<String> getBusinessAbsUrlList() {
            return businessAbsUrlList;
        }

        public void setBusinessAbsUrlList(List<String> businessAbsUrlList) {
            this.businessAbsUrlList = businessAbsUrlList;
        }

        public List<String> getBusinessList() {
            return businessList;
        }

        public void setBusinessList(List<String> businessList) {
            this.businessList = businessList;
        }

        public String getBusinessUrl() {
            return businessUrl;
        }

        public void setBusinessUrl(String businessUrl) {
            this.businessUrl = businessUrl;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
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

        public String getLastChangeUser() {
            return lastChangeUser;
        }

        public void setLastChangeUser(String lastChangeUser) {
            this.lastChangeUser = lastChangeUser;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLegalName() {
            return legalName;
        }

        public void setLegalName(String legalName) {
            this.legalName = legalName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLogoId() {
            return logoId;
        }

        public void setLogoId(String logoId) {
            this.logoId = logoId;
        }

        public String getLogoIdAbsUrl() {
            return logoIdAbsUrl;
        }

        public void setLogoIdAbsUrl(String logoIdAbsUrl) {
            this.logoIdAbsUrl = logoIdAbsUrl;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrincipal() {
            return principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }

        public String getPrincipalTel() {
            return principalTel;
        }

        public void setPrincipalTel(String principalTel) {
            this.principalTel = principalTel;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTotalBuy() {
            return totalBuy;
        }

        public void setTotalBuy(String totalBuy) {
            this.totalBuy = totalBuy;
        }

        public String getTotalCommonLevel() {
            return totalCommonLevel;
        }

        public void setTotalCommonLevel(String totalCommonLevel) {
            this.totalCommonLevel = totalCommonLevel;
        }

        public String getTotalGoods() {
            return totalGoods;
        }

        public void setTotalGoods(String totalGoods) {
            this.totalGoods = totalGoods;
        }

        public String getTotalLike() {
            return totalLike;
        }

        public void setTotalLike(String totalLike) {
            this.totalLike = totalLike;
        }

        public String getTotalSourceGoods() {
            return totalSourceGoods;
        }

        public void setTotalSourceGoods(String totalSourceGoods) {
            this.totalSourceGoods = totalSourceGoods;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    public static class Data {
        @SerializedName("classId")
        private String classId;
        @SerializedName("classInfo")
        private ClassInfo classInfo;
        @SerializedName("creatorId")
        private String creatorId;
        @SerializedName("describe")
        private String describe;
        @SerializedName("gmtCreate")
        private String gmtCreate;
        @SerializedName("gmtModify")
        private String gmtModify;
        @SerializedName("id")
        private String id;
        @SerializedName("imageIds")
        private List<String> imageIds;
        @SerializedName("imagesUrl")
        private List<String> imagesUrl;
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
        @SerializedName("saleVolume")
        private int saleVolume;
        @SerializedName("shopId")
        private String shopId;
        @SerializedName("shopInfo")
        private ShopInfo shopInfo;
        @SerializedName("sourceId")
        private String sourceId;
        @SerializedName("sourceText")
        private String sourceText;
        @SerializedName("sourceVideo")
        private String sourceVideo;
        @SerializedName("sourceVideoUrl")
        private String sourceVideoUrl;
        @SerializedName("stock")
        private int stock;
        @SerializedName("title")
        private String title;
        @SerializedName("totalCommonLevel")
        private String totalCommonLevel;
        @SerializedName("unit")
        private String unit;
        @SerializedName("unitMin")
        private int unitMin;
        @SerializedName("contentImgsUrl")
        private List<String> contentImgsUrl;
        @SerializedName("contentText")
        private String contentText;

        public String getContentText() {
            return contentText;
        }

        public void setContentText(String contentText) {
            this.contentText = contentText;
        }

        public List<String> getContentImgsUrl() {
            return contentImgsUrl;
        }

        public void setContentImgsUrl(List<String> contentImgsUrl) {
            this.contentImgsUrl = contentImgsUrl;
        }
        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public ClassInfo getClassInfo() {
            return classInfo;
        }

        public void setClassInfo(ClassInfo classInfo) {
            this.classInfo = classInfo;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
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


        public void setImageIds(List<String> imageIds) {
            this.imageIds = imageIds;
        }

        public List<String> getImagesUrl() {
            return imagesUrl;
        }

        public void setImagesUrl(List<String> imagesUrl) {
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

        public int getSaleVolume() {
            return saleVolume;
        }

        public void setSaleVolume(int saleVolume) {
            this.saleVolume = saleVolume;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public ShopInfo getShopInfo() {
            return shopInfo;
        }

        public void setShopInfo(ShopInfo shopInfo) {
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

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTotalCommonLevel() {
            return totalCommonLevel;
        }

        public void setTotalCommonLevel(String totalCommonLevel) {
            this.totalCommonLevel = totalCommonLevel;
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
