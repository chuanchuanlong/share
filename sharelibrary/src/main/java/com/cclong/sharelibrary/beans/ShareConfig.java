package com.cclong.sharelibrary.beans;

public class ShareConfig {
    String wechatKey;
    String wechatSecret;
    String qqKey;
    String sinaKey;
    String qqId;
    String alipayKey;
    String dingKey;

    public ShareConfig(Builder builder) {
        this.alipayKey = builder.alipayKey;
        this.wechatKey = builder.wechatKey;
        this.wechatSecret = builder.wechatSecret;
        this.qqKey = builder.qqKey;
        this.sinaKey = builder.sinaKey;
        this.qqId = builder.qqId;
        this.dingKey = builder.dingKey;
    }

    public String getWechatKey() {
        return wechatKey;
    }

    public String getQqKey() {
        return qqKey;
    }

    public String getSinaKey() {
        return sinaKey;
    }

    public String getQqId() {
        return qqId;
    }

    public String getAlipayKey() {
        return alipayKey;
    }

    public String getDingKey() {
        return dingKey;
    }

    public static class Builder {
        String wechatKey;
        String wechatSecret;
        String qqKey;
        String sinaKey;
        String qqId;
        String alipayKey;
        String dingKey;

        public Builder(String wechatKey) {
            this.wechatKey = wechatKey;
        }

        public Builder setWechatKey(String wechatKey) {
            this.wechatKey = wechatKey;
            return this;
        }

        public Builder setWechatSecret(String wechatSecret) {
            this.wechatSecret = wechatSecret;
            return this;
        }

        public Builder setQqKey(String qqKey) {
            this.qqKey = qqKey;
            return this;
        }

        public Builder setSinaKey(String sinaKey) {
            this.sinaKey = sinaKey;
            return this;
        }

        public Builder setQqId(String qqId) {
            this.qqId = qqId;
            return this;
        }

        public Builder setAlipayKey(String alipayKey) {
            this.alipayKey = alipayKey;
            return this;
        }

        public Builder setDingKey(String dingKey) {
            this.dingKey = dingKey;
            return this;
        }

        public ShareConfig create() {
            return new ShareConfig(this);
        }
    }
}
