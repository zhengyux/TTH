package com.taotaohai.bean;

/**
 * Created by Administrator on 2017/8/24.
 */

public class Photo extends BaseBean {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String md5;

        public String getId() {
            return md5;
        }

        public void setId(String id) {
            this.md5 = id;
        }
    }
}
