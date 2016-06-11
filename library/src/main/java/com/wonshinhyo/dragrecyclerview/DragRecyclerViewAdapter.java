package com.wonshinhyo.dragrecyclerview;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Shinhyo on 2016. 6. 10..
 */

public abstract class DragRecyclerViewAdapter extends RecyclerView.Adapter implements DragTouchCallback.OnDragListener {

    private static int mHandleId;
    private static OnClickListener mClickListener;
    protected final Context mContext;
    private OnStartDragListener mDragStartListener;
    private boolean isHandleDragEnabled = true;
    private DragTouchCallback.OnDragListener mDragListener;

    public DragRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public abstract ArrayList<Integer> getData();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder hol, int position) {
        final DragHolder holder = (DragHolder) hol;

        if (holder.mHandle == null || !isHandleDragEnabled) {
            return;
        }

        holder.mHandle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN
                        && mDragStartListener != null) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }


    @Override
    public boolean onMove(int fromPosition, int toPosition) {
        Collections.swap(getData(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);

        if (mDragListener != null) {
            mDragListener.onMove(fromPosition, toPosition);
        }
        return false;
    }

    @Override
    public void onSwiped(int position) {
        getData().remove(position);
        notifyItemRemoved(position);

        if (mDragListener != null) {
            mDragListener.onSwiped(position);
        }
    }

    void setOnItemStartDragListener(OnStartDragListener clickListener) {
        mDragStartListener = clickListener;
    }

    public void setOnItemDragListener(DragTouchCallback.OnDragListener dragListener) {
        mDragListener = dragListener;
    }

    public void setOnItemClickListener(OnClickListener clickListener) {
        mClickListener = clickListener;
    }

    void setHandleDragEnabled(boolean dragEnabled) {
        isHandleDragEnabled = dragEnabled;
    }

    void setHandleId(int handleId) {
        mHandleId = handleId;
    }

    interface OnClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    public static class DragHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        View mHandle;

        public DragHolder(View view) {
            super(view);
            mHandle = view.findViewById(mHandleId);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemClick(getAdapterPosition(), v);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemLongClick(getAdapterPosition(), v);
            }
            return true;
        }

    }

    public static class SimpleClickListener implements OnClickListener {

        @Override
        public void onItemClick(int position, View v) {
        }

        @Override
        public void onItemLongClick(int position, View v) {
        }
    }


}
