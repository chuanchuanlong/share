package com.cclong.sharelibrary.factory;

import android.app.Activity;

import com.cclong.sharelibrary.pops.SharePopupwindow;


public class RecycleShareFactory extends ShareFactory {
    public RecycleShareFactory(Builder builder) {
        super(builder);
    }

    @Override
    public SharePopupwindow createPop(Activity activity) {
        SharePopupwindow sharePopupwindow = new SharePopupwindow(activity);
        sharePopupwindow.setPlatforms(getPlatforms());
        sharePopupwindow.showTitle(isShowTitle());
        return sharePopupwindow;
    }
}
