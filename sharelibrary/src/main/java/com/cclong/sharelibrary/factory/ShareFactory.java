package com.cclong.sharelibrary.factory;

import android.app.Activity;
import android.text.TextUtils;

import com.cclong.sharelibrary.enmus.SharePlatform;
import com.cclong.sharelibrary.pops.SharePopupwindow;
import com.cclong.sharelibrary.utils.SharePlatformListUtils;

import java.util.List;

public abstract class ShareFactory {

    public static final String RECYCLE_SHARE_FACTORY = "RecycleShareFactory";
    private boolean mShowTitle;
    private List<SharePlatform> mPlatforms;
    private String mFactoryName;

    public boolean isShowTitle() {
        return mShowTitle;
    }

    public List<SharePlatform> getPlatforms() {
        return mPlatforms;
    }

    public String getFactoryName() {
        return mFactoryName;
    }

    public ShareFactory(Builder builder) {
        this.mShowTitle = builder.mShowTitle;
        this.mPlatforms = builder.mPlatforms;
        this.mFactoryName = builder.mFactoryName;
    }

    public static ShareFactory create(String name) {
        return new Builder(name).create();
    }

    public abstract SharePopupwindow createPop(Activity activity);

    public static class Builder {
        private boolean mShowTitle;
        private List<SharePlatform> mPlatforms;
        private String mFactoryName;

        public Builder(String factoryName) {
            this.mFactoryName = factoryName;
            mPlatforms = SharePlatformListUtils.getDefaultPlatform();
        }

        public Builder setShowTitle(boolean mShowTitle) {
            this.mShowTitle = mShowTitle;
            return this;
        }

        public Builder setPlatforms(List<SharePlatform> mPlatforms) {
            this.mPlatforms = mPlatforms;
            return this;
        }

        public ShareFactory create() {
            if (TextUtils.equals(mFactoryName, RECYCLE_SHARE_FACTORY)) {
                return new RecycleShareFactory(this);
            }
            return new RecycleShareFactory(this);
        }
    }
}
