package com.kotlin.loyal.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    public BaseViewHolder(View itemView, OnItemClickListener itemClickListener) {
        this(itemView, itemClickListener, null);
    }

    public BaseViewHolder(View itemView, OnItemClickListener itemClickListener, OnItemLongClickListener itemLongClickListener) {
        super(itemView);
        mOnItemClickListener = itemClickListener;
        itemView.setOnClickListener(this);
        mItemLongClickListener = itemLongClickListener;
        itemView.setOnLongClickListener(this);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, getLayoutPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mItemLongClickListener != null) {
            mItemLongClickListener.onItemLongClick(v, getLayoutPosition());
        }
        return true;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }
}