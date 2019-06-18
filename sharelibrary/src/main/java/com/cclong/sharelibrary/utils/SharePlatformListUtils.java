package com.cclong.sharelibrary.utils;

import com.cclong.sharelibrary.enmus.SharePlatform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharePlatformListUtils {
    public static List<SharePlatform> getDefaultPlatform() {
        List<SharePlatform> sharePlatforms = new ArrayList<>();
        sharePlatforms.add(SharePlatform.WECHAT);
        sharePlatforms.add(SharePlatform.WECHAT_CIRCLE);
        sharePlatforms.add(SharePlatform.QQ);
        sharePlatforms.add(SharePlatform.SINA);
        sharePlatforms.add(SharePlatform.ALIPAY);
        sharePlatforms.add(SharePlatform.DING);
        sharePlatforms.add(SharePlatform.SMS);
        return sharePlatforms;
    }

    public static List<SharePlatform> getMostPlatform() {
        List<SharePlatform> sharePlatforms = new ArrayList<>();
        sharePlatforms.add(SharePlatform.WECHAT);
        sharePlatforms.add(SharePlatform.WECHAT_CIRCLE);
        sharePlatforms.add(SharePlatform.QQ);
        sharePlatforms.add(SharePlatform.SINA);
        return sharePlatforms;
    }

    public static List<SharePlatform> getMostPlatform(SharePlatform... platforms) {
        return Arrays.asList(platforms);
    }
}
