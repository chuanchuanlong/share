package com.cclong.sharelibrary;

import android.content.Context;
import android.text.TextUtils;
import android.util.Config;

import com.cclong.sharelibrary.beans.ShareConfig;
import com.cclong.sharelibrary.utils.channel.WalleChannelReader;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

public class UMTools {
    /**
     * 初始化友盟统计
     *
     * @param context
     * @param umKey
     * @param debug
     */
    public static void initUM(Context context, String umKey, boolean debug) {
        UMConfigure.init(context, umKey, WalleChannelReader.getChannel(context), UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(debug);
    }

    /**
     * 初始化友盟分享
     *
     * @param context
     * @param shareConfig
     */
    public static void initUMShare(Context context, ShareConfig shareConfig) {
        com.umeng.socialize.Config.isUmengSina = true;
        UMShareAPI.get(context);
        //微信 appid appsecret
        if (!TextUtils.isEmpty(shareConfig.getWechatKey())) {
            PlatformConfig.setWeixin(shareConfig.getWechatKey(), shareConfig.getWechatKey());
        }
        //新浪微博 appkey appsecret
        if (!TextUtils.isEmpty(shareConfig.getSinaKey())) {
            PlatformConfig.setSinaWeibo(shareConfig.getSinaKey(), shareConfig.getSinaKey(), "http://sns.whalecloud.com/sina2/callback");
        }
        // QQ和Qzone appid appkey
        if (!TextUtils.isEmpty(shareConfig.getQqKey()) && !TextUtils.isEmpty(shareConfig.getQqId())) {
            PlatformConfig.setQQZone(shareConfig.getQqId(), shareConfig.getQqKey());
        }
        //支付宝
        if (!TextUtils.isEmpty(shareConfig.getAlipayKey())) {
            PlatformConfig.setAlipay(shareConfig.getAlipayKey());
        }
        //钉钉
        if (!TextUtils.isEmpty(shareConfig.getDingKey())) {
            PlatformConfig.setDing(shareConfig.getDingKey());
        }
        com.umeng.socialize.Config.isJumptoAppStore = true;
    }
}
