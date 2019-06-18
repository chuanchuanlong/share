package com.cclong.sharelibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class UMThirdLoginManager {
    private Activity mActivity;
    private UMThirdLoginListener mUMThirdLoginListener;
    private UMShareAPI mShareAPI;

    public void setUMThirdLoginListener(UMThirdLoginListener umThirdLoginListener) {
        mUMThirdLoginListener = umThirdLoginListener;
    }

    public UMThirdLoginManager(Context context) {
        mActivity = (Activity) context;
        if (mShareAPI == null)
            mShareAPI = UMShareAPI.get(mActivity);
    }

    public void thirdLogin(SHARE_MEDIA platform) {
        mShareAPI.doOauthVerify(mActivity, platform, umAuthListener);
    }


    public void deleteOauth(SHARE_MEDIA platform) {
        mShareAPI.deleteOauth(mActivity, platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                if (mUMThirdLoginListener != null) {
                    mUMThirdLoginListener.deleteOauth(share_media, map);
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }


    public boolean isInstall(SHARE_MEDIA platform) {
        return mShareAPI.isInstall(mActivity, platform);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (mUMThirdLoginListener != null) {
                mUMThirdLoginListener.loginResult(platform, data);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            if (mUMThirdLoginListener != null) {
                mUMThirdLoginListener.loginResult(platform, null);
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            if (mUMThirdLoginListener != null) {
                mUMThirdLoginListener.loginResult(platform, null);
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }


    public interface UMThirdLoginListener {
        void loginResult(SHARE_MEDIA platform, Map<String, String> data);

        void deleteOauth(SHARE_MEDIA platform, Map<String, String> data);
    }

}
