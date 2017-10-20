package com.taotaohai.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */

public class Video {

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
        @SerializedName("describe")
        private String describe;
        @SerializedName("id")
        private String id;
        @SerializedName("imageAbsUrl")
        private String imageAbsUrl;
        @SerializedName("imageId")
        private String imageId;
        @SerializedName("playNum")
        private int playNum;
        @SerializedName("sourceVideo")
        private String sourceVideo;
        @SerializedName("uploadTime")
        private String uploadTime;
        @SerializedName("videoAbsUrl")
        private String videoAbsUrl;
        @SerializedName("videoImg")
        private String videoImg;
        @SerializedName("videoUrl")
        private String videoUrl;

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageAbsUrl() {
            return imageAbsUrl;
        }

        public void setImageAbsUrl(String imageAbsUrl) {
            this.imageAbsUrl = imageAbsUrl;
        }

        public String getImageId() {
            return imageId;
        }

        public void setImageId(String imageId) {
            this.imageId = imageId;
        }

        public int getPlayNum() {
            return playNum;
        }

        public void setPlayNum(int playNum) {
            this.playNum = playNum;
        }

        public String getSourceVideo() {
            return sourceVideo;
        }

        public void setSourceVideo(String sourceVideo) {
            this.sourceVideo = sourceVideo;
        }

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }

        public String getVideoAbsUrl() {
            return videoAbsUrl;
        }

        public void setVideoAbsUrl(String videoAbsUrl) {
            this.videoAbsUrl = videoAbsUrl;
        }

        public String getVideoImg() {
            return videoImg;
        }

        public void setVideoImg(String videoImg) {
            this.videoImg = videoImg;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
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
