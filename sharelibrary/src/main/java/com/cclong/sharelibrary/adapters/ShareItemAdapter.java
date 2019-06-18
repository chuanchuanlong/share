package com.cclong.sharelibrary.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cclong.sharelibrary.R;
import com.cclong.sharelibrary.enmus.SharePlatform;

public class ShareItemAdapter extends CustomRecycViewAdapter<SharePlatform> {

    public ShareItemAdapter(Context context) {
        super(context);
    }

    @Override
    public CustomViewHolder onCreateCoustomViewHolder(ViewGroup parent, int viewType) {
        return new ShareViewHolder(mInflater.inflate(R.layout.share_adatper_share_item, parent, false));
    }

    @Override
    public void onBindCustomViewHolder(CustomViewHolder holder, int position) {
        holder.bindData(position);
    }

    class ShareViewHolder extends CustomViewHolder {
        TextView name;
        ImageView icon;
        public ShareViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            icon = itemView.findViewById(R.id.icon);
        }

        @Override
        public void bindData(int position) {
            SharePlatform item = getItem(position);
            name.setText(item.getName());
            icon.setImageResource(item.getRes());
        }
    }
}
