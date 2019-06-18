package com.cclong.sharelibrary.enmus;

import com.cclong.sharelibrary.R;

public enum SharePlatform {
    QQ("QQ", 1, R.drawable.umeng_socialize_qq),
    WECHAT("微信", 2, R.drawable.umeng_socialize_wechat),
    WECHAT_CIRCLE("朋友圈", 3, R.drawable.umeng_socialize_wxcircle),
    SINA("新浪微博", 4, R.drawable.umeng_socialize_sina),
    ALIPAY("支付宝", 5, R.drawable.umeng_socialize_alipay),
    DING("钉钉", 6, R.drawable.umeng_socialize_ding),
    SMS("短信", 7, R.drawable.umeng_socialize_sms),
    LINK_COPY("链接", 10, R.drawable.icon_share_link);
    String name;
    int id;
    int res;
    SharePlatform(String name, int id, int res) {
        this.name = name;
        this.id = id;
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public SharePlatform setName(String name) {
        this.name = name;
        return this;
    }

    public int getId() {
        return id;
    }

    public SharePlatform setId(int id) {
        this.id = id;
        return this;
    }

    public int getRes() {
        return res;
    }

    public SharePlatform setRes(int res) {
        this.res = res;
        return this;
    }
}
