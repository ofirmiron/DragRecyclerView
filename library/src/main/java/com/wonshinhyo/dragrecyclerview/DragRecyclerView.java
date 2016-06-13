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
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Drag);
        mHandleId = a.getResourceId(R.styleable.Drag_handle_id, -1);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        ImpDragAdapter dragAdapter = (ImpDragAdapter) super.getAdapter();
        mTouchHelperCallback = new DragTouchCallback((com.wonshinhyo.dragrecyclerview.OnDragListener) super.getAdapter());
        mItemTouchHelper = new ItemTouchHelper(mTouchHelperCallback);
        mItemTouchHelper.attachToRecyclerView(this);


        dragAdapter.setOnItemStartDragListener(new OnStartDragListener() {
            @Override
            public void onStartDrag(ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }
        });

        dragAdapter.setHandleId(mHandleId);
    }

    public void setHandleDragEnabled(boolean dragEnabled) {
        ((ImpDragAdapter) super.getAdapter()).setHandleDragEnabled(dragEnabled);
    }

    public void setLongPressDragEnabled(boolean set) {
        mTouchHelperCallback.setLongPressDragEnabled(set);
    }

    public void setSwipeEnabled(boolean set) {
        mTouchHelperCallback.setItemViewSwipeEnabled(set);
    }

}
