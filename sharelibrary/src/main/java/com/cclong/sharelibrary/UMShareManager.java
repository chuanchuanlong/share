package com.cclong.sharelibrary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.cclong.sharelibrary.enmus.SharePlatform;
import com.cclong.sharelibrary.factory.ShareFactory;
import com.cclong.sharelibrary.pops.BaseSharePopupwindow;
import com.cclong.sharelibrary.utils.PermisionManager;
import com.cclong.sharelibrary.utils.SharePlatformListUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

public class UMShareManager implements ICCLongPop {
    private static final String TAG = "UMShareManager";
    private Activity mActivity;
    private ShareCallback mCallback;
    private BaseSharePopupwindow mBasePopupwindow;
    private ShareMessageInfo.ShareMessageData mShareMessageData;
    private View mCurrentPlatformView;
    private Fragment mFragment;
    private final String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private SHARE_MEDIA[] mHidePlatform;
    private boolean mHideLink;

    private UMShareManager(Builder builder) {
        mShareMessageData = new ShareMessageInfo.ShareMessageData();
        mShareMessageData.bitmap = builder.bitmap;
        mShareMessageData.image = builder.image;
        mShareMessageData.link = builder.link;
        mShareMessageData.message = builder.message;
        mShareMessageData.title = builder.title;
        mShareMessageData.resId = builder.resId;
        mShareMessageData.shortLink = builder.shortLink;
        this.mActivity = (Activity) builder.context;
        this.mFragment = builder.mFragment;
        this.mBasePopupwindow = builder.mShareFactory.createPop(this.mActivity);
        this.mBasePopupwindow.setICCLongPop(this);
    }

    public void setShareMessageData(ShareMessageInfo.ShareMessageData shareMessageData) {
        this.mShareMessageData = shareMessageData;
    }

    public void sharePlatform(SHARE_MEDIA platform, ShareMessageInfo.ShareMessageData shareMessageData) {
        ShareAction shareAction = new ShareAction(mActivity);
        checkMsgLength(platform, shareMessageData);
        UMWeb web = new UMWeb(platform == SHARE_MEDIA.SINA ? shareMessageData.shortLink : shareMessageData.link);
        if (platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
            web.setTitle(TextUtils.isEmpty(shareMessageData.message) ? shareMessageData.title : shareMessageData.message);//标题
        } else {
            web.setTitle(TextUtils.isEmpty(shareMessageData.title) ? shareMessageData.message : shareMessageData.title);//标题
        }
        web.setDescription(shareMessageData.message);//描述
        web.setDescription(shareMessageData.message);//描述
        UMImage image = null;
        if (shareMessageData.resId == 0 && TextUtils.isEmpty(shareMessageData.image)) {
            image = new UMImage(mActivity, shareMessageData.bitmap);
        } else {
            image = shareMessageData.resId == 0 ? new UMImage(mActivity, shareMessageData.image) :
                    new UMImage(mActivity, shareMessageData.resId);
        }
        web.setThumb(image);
        shareAction.setPlatform(platform)
                .setCallback(umShareListener)
                .withMedia(web)
                .share();
    }

    private void checkMsgLength(SHARE_MEDIA platform, ShareMessageInfo.ShareMessageData shareMessageData) {
        if (platform == SHARE_MEDIA.SINA) {
            if (!TextUtils.isEmpty(shareMessageData.link)) {
                int len = shareMessageData.link.length() / 2;
                int msgLen = shareMessageData.message.length();
                int totalLen = len + msgLen;
                if (totalLen > 140) {
                    int excrescentLen = totalLen - 140;
                    shareMessageData.message = shareMessageData.message.substring(0, msgLen - excrescentLen - 2) + "...";
                }
            }
        }
    }

    /**
     * 弹出分享界面
     */
    public void showSharePlatForm() {
        if (!mBasePopupwindow.isShowing()) {
            mBasePopupwindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.e(TAG, "onResult==platform==" + platform.name());
            String platformText = "";
            switch (platform) {
                case SINA:
                    platformText = SharePlatform.SINA.getName();
                    break;
                case QQ:
                    platformText = SharePlatform.QQ.getName();
                    break;
                case WEIXIN:
                    platformText = SharePlatform.WECHAT.getName();
                    break;
                case WEIXIN_CIRCLE:
                    platformText = SharePlatform.WECHAT_CIRCLE.getName();
                    break;
                case DINGTALK:
                    platformText = SharePlatform.DING.getName();
                    break;
                case ALIPAY:
                    platformText = SharePlatform.ALIPAY.getName();
                    break;
                case SMS:
                    platformText = SharePlatform.SMS.getName();
                    break;
            }
            Toast.makeText(mActivity, platformText + " 分享成功啦", Toast.LENGTH_SHORT).show();
            if (mCallback != null) {
                mCallback.onSuccess();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.e("lsj", "onError==platform==" + platform.name() + ">>Throwable==" + toString());
            String platformText = "";
            switch (platform) {
                case SINA:
                    platformText = SharePlatform.SINA.getName();
                    break;
                case QQ:
                    platformText = SharePlatform.QQ.getName();
                    break;
                case WEIXIN:
                    platformText = SharePlatform.WECHAT.getName();
                    break;
                case WEIXIN_CIRCLE:
                    platformText = SharePlatform.WECHAT_CIRCLE.getName();
                    break;
                case DINGTALK:
                    platformText = SharePlatform.DING.getName();
                    break;
                case ALIPAY:
                    platformText = SharePlatform.ALIPAY.getName();
                    break;
                case SMS:
                    platformText = SharePlatform.SMS.getName();
                    break;
            }
            if (mCallback != null) {
                mCallback.onError();
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.e(TAG, "onCancel==platform==" + platform.name());
            String platformText = "";
            switch (platform) {
                case SINA:
                    platformText = "新浪微博";
                    break;
                case QQ:
                    platformText = "QQ";
                    break;
                case WEIXIN:
                    platformText = "微信";
                    break;
                case WEIXIN_CIRCLE:
                    platformText = "微信朋友圈";
                    break;
            }
            Toast.makeText(mActivity, platformText + " 分享取消了", Toast.LENGTH_SHORT)
                    .show();
            if (mCallback != null) {
                mCallback.onCancel();
            }
        }
    };

    @Override
    public void onPopCallback(Object o) {
        if (o != null && o instanceof SharePlatform) {
            SharePlatform sharePlatform = (SharePlatform) o;
            SHARE_MEDIA platform = null;
            switch (sharePlatform) {
                case QQ:
                    if (mFragment != null) {
                        PermisionManager.instance().requestPermision(mFragment, new PermisionManager.PermistionResult() {
                            @Override
                            public void onResult(boolean isSuccess) {
                                sharePlatform(SHARE_MEDIA.QQ, mShareMessageData);
                            }
                        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    } else {
                        PermisionManager.instance().requestPermision(mActivity, new PermisionManager.PermistionResult() {
                            @Override
                            public void onResult(boolean isSuccess) {
                                sharePlatform(SHARE_MEDIA.QQ, mShareMessageData);
                            }
                        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                    return;
                case WECHAT:
                    platform = SHARE_MEDIA.WEIXIN;
                    break;
                case WECHAT_CIRCLE:
                    platform = SHARE_MEDIA.WEIXIN_CIRCLE;
                    break;
                case SINA:
                    platform = SHARE_MEDIA.SINA;
                    break;
                case ALIPAY:
                    platform = SHARE_MEDIA.ALIPAY;
                    break;
                case DING:
                    platform = SHARE_MEDIA.DINGTALK;
                    break;
                case SMS:
                    platform = SHARE_MEDIA.SMS;
                    break;
            }
            sharePlatform(platform, mShareMessageData);
        }
    }


    public interface ShareCallback {
        void onSuccess();

        void onError();

        void onCancel();
    }

    public void setCallback(ShareCallback callback) {
        this.mCallback = callback;
    }


    public static class Builder {
        private String title;
        private String link;
        private String shortLink;
        private String message;
        private String image;
        private Bitmap bitmap;
        private int resId;
        private ShareFactory mShareFactory;
        private Context context;
        private Fragment mFragment;

        public Builder(Context context, String title) {
            this.title = title;
            this.context = context;
            if (this.mShareFactory == null) {
                mShareFactory = new ShareFactory.Builder(ShareFactory.RECYCLE_SHARE_FACTORY).create();
            }
        }

        public Builder setShareFactory(ShareFactory shareFactory) {
            this.mShareFactory = shareFactory;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setFragment(Fragment fragment) {
            this.mFragment = fragment;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setLink(String link) {
            this.link = link;
            return this;
        }

        public Builder setShortLink(String shortLink) {
            this.shortLink = shortLink;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setImage(String image) {
            this.image = image;
            return this;
        }

        public Builder setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
            return this;
        }

        public Builder setResId(int resId) {
            this.resId = resId;
            return this;
        }

        public UMShareManager create() {
            return new UMShareManager(this);
        }
    }

}
