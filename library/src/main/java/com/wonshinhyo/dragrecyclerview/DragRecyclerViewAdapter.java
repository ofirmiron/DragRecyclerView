package com.wonshinhyo.dragrecyclerview;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.Collections;
import java.util.List;

/**
 * Created by Shinhyo on 2016. 6. 10..
 */

public abstract class DragRecyclerViewAdapter extends RecyclerView.Adapter implements ImpDragAdapter, OnDragListener {

    protected final Context mContext;
    private OnStartDragListener mDragStartListener;
    private boolean isHandleDragEnabled = true;
    private OnDragListener mDragListener;

    public DragRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public abstract List<Integer> getData();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder hol, int position) {
        final DragHolder holder = (DragHolder) hol;

        View handle = holder.getHandle();
        if (handle == null || !isHandleDragEnabled) {
            return;
        }
        handle.setOnTouchListener(new View.OnTouchListener() {
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

    @Override
    public void onDrop(int fromPosition, int toPosition) {
        if (mDragListener != null) {
            mDragListener.onDrop(fromPosition, toPosition);
        }
    }

    @Override
    public void setOnItemStartDragListener(OnStartDragListener clickListener) {
        mDragStartListener = clickListener;
    }

    @Override
    public void setOnItemDragListener(OnDragListener dragListener) {
        mDragListener = dragListener;
    }

    @Override
    public void setOnItemClickListener(OnClickListener clickListener) {
        DragHolder.mClickListener = clickListener;
    }

    @Override
    public void setHandleDragEnabled(boolean dragEnabled) {
        isHandleDragEnabled = dragEnabled;
    }

    @Override
    public void setHandleId(int handleId) {
        DragHolder.mHandleId = handleId;
    }


}
