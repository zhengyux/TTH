package com.taotaohai.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class Comment {
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

    public static class User {
        @SerializedName("u_id")
        private String u_id;
        @SerializedName("name")
        private String name;
        @SerializedName("username")
        private String username;
        @SerializedName("imgUrl")
        private String imgUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

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

    public static class ReplyComment {
        @SerializedName("comment")
        private String comment;
        @SerializedName("commentLevel")
        private int commentLevel;
        @SerializedName("gmtCreate")
        private String gmtCreate;
        @SerializedName("gmtModify")
        private String gmtModify;
        @SerializedName("goodsId")
        private String goodsId;
        @SerializedName("id")
        private String id;
        @SerializedName("lastChangeUser")
        private String lastChangeUser;
        @SerializedName("orderId")
        private String orderId;
        @SerializedName("parentId")
        private String parentId;
        @SerializedName("recordId")
        private String recordId;
        @SerializedName("replyComment")
        private String replyComment;
        @SerializedName("user")
        private User user;
        @SerializedName("userId")
        private String userId;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getCommentLevel() {
            return commentLevel;
        }

        public void setCommentLevel(int commentLevel) {
            this.commentLevel = commentLevel;
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

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public String getReplyComment() {
            return replyComment;
        }

        public void setReplyComment(String replyComment) {
            this.replyComment = replyComment;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class Data {
        @SerializedName("comment")
        private String comment;
        @SerializedName("commentLevel")
        private int commentLevel;
        @SerializedName("gmtCreate")
        private String gmtCreate;
        @SerializedName("gmtModify")
        private String gmtModify;
        @SerializedName("goodsId")
        private String goodsId;
        @SerializedName("id")
        private String id;
        @SerializedName("lastChangeUser")
        private String lastChangeUser;
        @SerializedName("orderId")
        private String orderId;
        @SerializedName("parentId")
        private String parentId;
        @SerializedName("recordId")
        private String recordId;
        @SerializedName("replyComment")
        private ReplyComment replyComment;
        @SerializedName("user")
        private User user;
        @SerializedName("userId")
        private String userId;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getCommentLevel() {
            return commentLevel;
        }

        public void setCommentLevel(int commentLevel) {
            this.commentLevel = commentLevel;
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

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public ReplyComment getReplyComment() {
            return replyComment;
        }

        public void setReplyComment(ReplyComment replyComment) {
            this.replyComment = replyComment;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
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
