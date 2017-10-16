package com.taotaohai.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class HotShop {

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

    public static class Data {
        @SerializedName("address")
        private String address;
        @SerializedName("brandId")
        private String brandId;
        @SerializedName("brc")
        private String brc;
        @SerializedName("businessList")
        private List<BusinessList> businessList;
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

        public List<BusinessList> getBusinessList() {
            return businessList;
        }

        public void setBusinessList(List<BusinessList> businessList) {
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

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
