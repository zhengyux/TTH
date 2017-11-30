package com.taotaohai.bean;

import com.google.gson.annotations.SerializedName;
import com.taotaohai.bean.ShopGoods.Data;

import java.util.List;

/**
 * Created by Administrator on 2017/10/22.
 */

public class ShopGoods2 {


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

    public static class ClassInfo {
        @SerializedName("children")
        private String children;
        @SerializedName("classId")
        private String classId;
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

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
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

    public static class ShopIdentifies {
    }

    public static class User {
        @SerializedName("u_id")
        private String u_id;
        @SerializedName("avatarId")
        private String avatarId;
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

        public String getAvatarId() {
            return avatarId;
        }

        public void setAvatarId(String avatarId) {
            this.avatarId = avatarId;
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
        @SerializedName("businessAbsUrlList")
        private List<String> businessAbsUrlList;
        @SerializedName("businessList")
        private List<String> businessList;
        @SerializedName("businessUrl")
        private String businessUrl;
        @SerializedName("cityId")
        private String cityId;
        @SerializedName("commission")
        private String commission;
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
        @SerializedName("logo_home")
        private String logo;
        @SerializedName("logoId")
        private String logoId;
        @SerializedName("logoIdAbsUrl")
        private String logoIdAbsUrl;
        @SerializedName("longitude")
        private String longitude;
        @SerializedName("money")
        private int money;
        @SerializedName("name")
        private String name;
        @SerializedName("principal")
        private String principal;
        @SerializedName("principalTel")
        private String principalTel;
        @SerializedName("remark")
        private String remark;
        @SerializedName("shopIdentifies")
        private List<ShopIdentifies> shopIdentifies;
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

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
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

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
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

        public List<ShopIdentifies> getShopIdentifies() {
            return shopIdentifies;
        }

        public void setShopIdentifies(List<ShopIdentifies> shopIdentifies) {
            this.shopIdentifies = shopIdentifies;
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
