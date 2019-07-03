package com.cclong.sharelibrary.pops;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.cclong.sharelibrary.R;
import com.cclong.sharelibrary.adapters.OnItemClickListener;
import com.cclong.sharelibrary.adapters.ShareItemAdapter;
import com.cclong.sharelibrary.enmus.SharePlatform;

import java.util.List;

/**
 * @author ly
 * @brief 分享弹窗
 * @date 2018/4/28.
 */
public class SharePopupwindow extends BaseSharePopupwindow implements OnItemClickListener {
    private Activity activity;
    private RecyclerView mShareItemList;
    private TextView mTitle;
    /**
     * 每行平台个数
     */
    private int mItemCount = 4;
    private ShareItemAdapter mShareItemAdapter;

    public SharePopupwindow(Activity activity) {
        this.activity = activity;
        init(activity);
    }

    public void showTitle(boolean isShow) {
        if (mTitle != null) {
            mTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setPlatforms(List<SharePlatform> sharePlatforms) {
        super.setPlatforms(sharePlatforms);
        if (mShareItemAdapter != null) {
            mShareItemAdapter.setDatas(mPlatforms);
        }
    }

    @Override
    protected void init(Activity activity) {
        super.init(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.share_popupwindow_pic_text_layout, null);
        mShareItemList = (RecyclerView) conentView.findViewById(R.id.shareItemList);
        TextView tvCancel = (TextView) conentView.findViewById(R.id.tv_cancel);
        mTitle = (TextView) conentView.findViewById(R.id.title);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mShareItemList.setLayoutManager(new GridLayoutManager(activity, mItemCount));
        mShareItemAdapter = new ShareItemAdapter(activity);
        mShareItemAdapter.setOnItemClickListener(this);
        mShareItemList.setAdapter(mShareItemAdapter);
        setContentView(conentView);
    }

    @Override
    public void onItemClick(View view, ViewGroup parent, int position) {
        SharePlatform item = mShareItemAdapter.getItem(position);
        if (mICCLongPop != null) {
            mICCLongPop.onPopCallback(item);
        }
    }
}
