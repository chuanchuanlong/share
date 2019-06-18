package com.cclong.sharelibrary;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.fest.fashionfenke.entity.ShortLinkBean;
import com.fest.fashionfenke.net.NetApi;
import com.fest.fashionfenke.net.NetConstants;
import com.ssfk.app.bean.Response;
import com.ssfk.app.net.INetworkResultListener;
import com.ssfk.app.net.NetImpl;

import java.util.Map;

/**
 * Created by Amy on 2016/11/10 0010.
 */
public class RequestShortLinkManager {
    private static final int ACTION_GETSHORTENURL = 1;
    private NetImpl mNetImpl;
    private Context mContext;
    private String mUrl;
    private OnShortLinkLinstener mOnShortLinkLinstener;

    public static RequestShortLinkManager getInstance() {
        return new RequestShortLinkManager();
    }

    private NetImpl getNetImpl() {
        if (mNetImpl == null) {
            mNetImpl = new NetImpl(mContext, mINetworkResultListener, ((Activity) mContext).getClass().getName());
        }
        return mNetImpl;
    }

    public void getShortLink(Context context, String url, OnShortLinkLinstener onShortLinkLinstener) {
        if (!TextUtils.isEmpty(url)) {
            mOnShortLinkLinstener = onShortLinkLinstener;
            mContext = context;
            mUrl = url;
            Map<String, String> params = NetApi.getParams();
            params.put("url", url);
            getNetImpl().connection(ACTION_GETSHORTENURL, NetApi.getPostNetTask(NetConstants.GETSHORTENURL, params, ShortLinkBean.class));
        }
    }

    public INetworkResultListener mINetworkResultListener = new INetworkResultListener() {
        @Override
        public void onNetworkResult(int action, Response response) {
            switch (action) {
                case ACTION_GETSHORTENURL:
                    if (response.isSuccess()) {
                        ShortLinkBean shortLinkBean = (ShortLinkBean) response;
                        if (shortLinkBean.data != null) {
                            if (!TextUtils.isEmpty(shortLinkBean.data.short_url)) {
                                mOnShortLinkLinstener.onShortLinkData(shortLinkBean.data.short_url);
                            } else {
                                mOnShortLinkLinstener.onShortLinkData(mUrl);
                            }
                        } else {
                            mOnShortLinkLinstener.onShortLinkData(mUrl);
                        }
                    }else{
                        mOnShortLinkLinstener.onShortLinkData(mUrl);
                    }
                    break;
            }
        }
    };

    public interface OnShortLinkLinstener {
        void onShortLinkData(String url);
    }

}


