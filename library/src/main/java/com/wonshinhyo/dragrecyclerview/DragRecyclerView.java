package com.wonshinhyo.dragrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

/**
 * Created by Shinhyo on 2016. 6. 10..
 */

public class DragRecyclerView extends RecyclerView {

    private int mHandleId = -1;
    private ItemTouchHelper mItemTouchHelper;
    private DragTouchCallback mTouchHelperCallback;

    public DragRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttrs(context, attrs);
    }

    public DragRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAttrs(context, attrs);
    }

    private void setAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, com.wonshinhyo.dragrecyclerview.R.styleable.Drag);
        mHandleId = a.getResourceId(com.wonshinhyo.dragrecyclerview.R.styleable.Drag_handle_id, -1);
    }

    @Override
    public DragRecyclerViewAdapter getAdapter() {
        return (DragRecyclerViewAdapter) super.getAdapter();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        mTouchHelperCallback = new DragTouchCallback(getAdapter());
        mItemTouchHelper = new ItemTouchHelper(mTouchHelperCallback);
        mItemTouchHelper.attachToRecyclerView(this);

        getAdapter().setOnItemStartDragListener(new DragRecyclerViewAdapter.OnStartDragListener() {
            @Override
            public void onStartDrag(ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }
        });

        getAdapter().setHandleId(mHandleId);
    }

    public void setHandleDragEnabled(boolean dragEnabled) {
        getAdapter().setHandleDragEnabled(dragEnabled);
    }

    public void setLongPressDragEnabled(boolean set) {
        mTouchHelperCallback.setLongPressDragEnabled(set);
    }

    public void setSwipeEnabled(boolean set) {
        mTouchHelperCallback.setItemViewSwipeEnabled(set);
    }

}
