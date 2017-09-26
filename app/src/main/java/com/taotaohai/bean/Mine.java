package com.taotaohai.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class Mine {

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

    public static class Ext {
        @SerializedName("age")
        private int age;
        @SerializedName("avatarId")
        private String avatarId;
        @SerializedName("avatarUrl")
        private String avatarUrl;
        @SerializedName("defaultAddress")
        private String defaultAddress;
        @SerializedName("gender")
        private boolean gender;
        @SerializedName("id")
        private String id;
        @SerializedName("inviteUser")
        private String inviteUser;
        @SerializedName("userId")
        private String userId;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAvatarId() {
            return avatarId;
        }

        public void setAvatarId(String avatarId) {
            this.avatarId = avatarId;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getDefaultAddress() {
            return defaultAddress;
        }

        public void setDefaultAddress(String defaultAddress) {
            this.defaultAddress = defaultAddress;
        }

        public boolean getGender() {
            return gender;
        }

        public void setGender(boolean gender) {
            this.gender = gender;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInviteUser() {
            return inviteUser;
        }

        public void setInviteUser(String inviteUser) {
            this.inviteUser = inviteUser;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class UserRoles {
    }

    public static class Data {
        @SerializedName("createDate")
        private String createDate;
        @SerializedName("email")
        private String email;
        @SerializedName("ext")
        private Ext ext;
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("phone")
        private String phone;
        @SerializedName("roleInfo")
        private String roleInfo;
        @SerializedName("status")
        private int status;
        @SerializedName("updateDate")
        private String updateDate;
        @SerializedName("userRoles")
        private List<UserRoles> userRoles;
        @SerializedName("username")
        private String username;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRoleInfo() {
            return roleInfo;
        }

        public void setRoleInfo(String roleInfo) {
            this.roleInfo = roleInfo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public List<UserRoles> getUserRoles() {
            return userRoles;
        }

        public void setUserRoles(List<UserRoles> userRoles) {
            this.userRoles = userRoles;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
