package com.wonshinhyo.dragrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

/**
 * Created by Shinhyo on 2016. 6. 10..
 */

public class RecyclerView extends android.support.v7.widget.RecyclerView implements ImpRecycleView {
    private ItemTouchHelper mItemTouchHelper;

    private int mHandleId = -1;
    private DragTouchCallback mTouchHelperCallback;

    public RecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttrs(context, attrs);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAttrs(context, attrs);
    }

    private void setAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Drag);
        mHandleId = a.getResourceId(R.styleable.Drag_handle_id, -1);
    }

    ImpAdapter getDragAdapter() {
        return (ImpAdapter) getAdapter();
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);

        getDragAdapter().setHandleId(mHandleId);

        mTouchHelperCallback = new DragTouchCallback((com.wonshinhyo.dragrecyclerview.OnDragListener) super.getAdapter());
        mItemTouchHelper = new ItemTouchHelper(mTouchHelperCallback);
        mItemTouchHelper.attachToRecyclerView(this);
        getDragAdapter().setRecycleView(this);
    }

    public DragTouchCallback getTouchHelperCallback() {
        return mTouchHelperCallback;
    }

    public ItemTouchHelper getItemTouchHelper() {
        return mItemTouchHelper;
    }

}
