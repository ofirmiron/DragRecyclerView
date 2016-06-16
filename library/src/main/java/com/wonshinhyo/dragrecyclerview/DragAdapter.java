package com.wonshinhyo.dragrecyclerview;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * Created by Shinhyo on 2016. 6. 14..
 */
public abstract class DragAdapter extends android.support.v7.widget.RecyclerView.Adapter implements ImpAdapter, OnDragListener {

    private final Context mContext;
    private List mData;
    private boolean isHandleDragEnabled = true;
    private OnDragListener mDragListener;
    private DragRecyclerView mRecyclerView;

    public DragAdapter(Context context, List data) {
        mContext = context;
        mData = data;
    }

    @Override
    public void onBindViewHolder(DragRecyclerView.ViewHolder hol, int position) {
        final DragHolder holder = (DragHolder) hol;

        View handle = holder.getHandle();
        if (handle == null || !isHandleDragEnabled) {
            return;
        }
        handle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mRecyclerView.getItemTouchHelper().startDrag(holder);
                }
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {

//        Collections.swap(getData(), fromPosition, toPosition);

        if (mDragListener != null) {
            mDragListener.onMove(fromPosition, toPosition);
        }

        notifyItemMoved(fromPosition, toPosition);
    }


    @Override
    public void onSwiped(int position) {
        mData.remove(position);
        notifyItemRemoved(position);

        if (mDragListener != null) {
            mDragListener.onSwiped(position);
        }
    }

    @Override
    public void onDrop(int fromPosition, int toPosition) {

        List list = getData();
        list.add(toPosition, list.remove(fromPosition));

        if (mDragListener != null) {
            mDragListener.onDrop(fromPosition, toPosition);
        }
    }

    public List getData() {
        return mData;
    }

    public void setOnItemDragListener(OnDragListener dragListener) {
        mDragListener = dragListener;
    }

    public void setOnItemClickListener(OnClickListener clickListener) {
        DragHolder.mClickListener = clickListener;
    }

    public void setHandleId(int handleId) {
        DragHolder.mHandleId = handleId;
    }

    public void setHandleDragEnabled(boolean dragEnabled) {
        isHandleDragEnabled = dragEnabled;
    }

    public void setRecycleView(DragRecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public void setLongPressDragEnabled(boolean set) {
        mRecyclerView.getTouchHelperCallback().setLongPressDragEnabled(set);
    }

    public void setSwipeEnabled(boolean set) {
        mRecyclerView.getTouchHelperCallback().setItemViewSwipeEnabled(set);
    }


}
