package com.wonshinhyo.dragrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Shinhyo on 2016. 6. 13..
 */
public class DragHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public static int mHandleId;
    public static OnClickListener mClickListener;
    private View mHandle;

    public DragHolder(View view) {
        super(view);
        mHandle = view.findViewById(mHandleId);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }

    public View getHandle() {
        return mHandle;
    }

    @Override
    public void onClick(View v) {
        if (mClickListener != null) {
            mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mClickListener != null) {
            mClickListener.onItemLongClick(v, getAdapterPosition());
        }
        return true;
    }

}
