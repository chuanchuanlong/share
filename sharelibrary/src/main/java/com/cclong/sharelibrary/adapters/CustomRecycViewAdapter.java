package com.cclong.sharelibrary.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;


/**
 * Created by chuanchuanlong on 16/6/26.
 */
public abstract class CustomRecycViewAdapter<T> extends RecyclerView.Adapter {
    List<T> mAllDatas;
    public OnItemClickListener mOnItemClickListener;
    public Context mContext;
    protected LayoutInflater mInflater;
    public CustomRecycViewAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setDatas(List<T> allDatas) {
        mAllDatas = allDatas;
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mAllDatas;
    }

    public int getItemCounts() {
        return mAllDatas != null ? mAllDatas.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public T getItem(int position) {
        return mAllDatas.get(position);
    }

    public abstract CustomViewHolder onCreateCoustomViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindCustomViewHolder(CustomViewHolder holder, int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateCoustomViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        onBindCustomViewHolder((CustomViewHolder) holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, null, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getItemCounts();
    }

    public abstract class CustomViewHolder extends RecyclerView.ViewHolder {

        public CustomViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bindData(int position);

    }
}
