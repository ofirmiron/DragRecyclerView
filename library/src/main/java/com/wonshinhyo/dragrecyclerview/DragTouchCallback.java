package com.wonshinhyo.dragrecyclerview;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Shinhyo on 2016. 6. 11..
 */

public class DragTouchCallback extends ItemTouchHelper.Callback {

    private int mPosStart = -1, mPosEnd = -1;
    private boolean isLongDragEnabled = true;
    private boolean isSwipeEnabled = true;
    private OnDragListener mListener;

    DragTouchCallback(OnDragListener listener) {
        mListener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);

        switch (actionState) {
            case ItemTouchHelper.ACTION_STATE_IDLE:
                if (mPosStart == -1 || mPosEnd == -1) {
                    return;
                }
                mListener.onDrop(mPosStart, mPosEnd);
                mPosStart = -1;
                mPosEnd = -1;
                break;
            case ItemTouchHelper.ACTION_STATE_SWIPE:
                break;
            case ItemTouchHelper.ACTION_STATE_DRAG:
                break;
        }

    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        int from = viewHolder.getAdapterPosition();
        if (mPosStart == -1) {
            mPosStart = from;
        }
        mPosEnd = target.getAdapterPosition();
        mListener.onMove(from, mPosEnd);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mListener.onSwiped(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            viewHolder.itemView.setAlpha(1.0f - Math.abs(dX) / (float) viewHolder.itemView.getWidth());
            viewHolder.itemView.setTranslationX(dX);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setAlpha(1.0f);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return isLongDragEnabled;
    }

    public void setLongPressDragEnabled(boolean dragEnabled) {
        isLongDragEnabled = dragEnabled;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isSwipeEnabled;
    }

    public void setItemViewSwipeEnabled(boolean swipeEnabled) {
        isSwipeEnabled = swipeEnabled;
    }


}
