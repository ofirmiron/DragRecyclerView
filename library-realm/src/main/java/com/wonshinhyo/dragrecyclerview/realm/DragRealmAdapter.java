package com.wonshinhyo.dragrecyclerview.realm;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.wonshinhyo.dragrecyclerview.DragHolder;
import com.wonshinhyo.dragrecyclerview.DragRecyclerView;
import com.wonshinhyo.dragrecyclerview.ImpAdapter;
import com.wonshinhyo.dragrecyclerview.OnClickListener;
import com.wonshinhyo.dragrecyclerview.OnDragListener;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Shinhyo on 2016. 6. 10..
 */

public abstract class DragRealmAdapter extends RealmRecyclerViewAdapter implements ImpAdapter, OnDragListener {

    private boolean isHandleDragEnabled = true;
    private OnDragListener mDragListener;
    private DragRecyclerView mRecyclerView;


    public DragRealmAdapter(Context context, OrderedRealmCollection data, boolean autoUpdate) {
        super(context, data, autoUpdate);
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
        return getData().size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {

        if (mDragListener != null) {
            mDragListener.onMove(fromPosition, toPosition);
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onDrop(int fromPosition, int toPosition) {

        OrderedRealmCollection list = getData();
        list.add(toPosition, list.remove(fromPosition));

        if (mDragListener != null) {
            mDragListener.onDrop(fromPosition, toPosition);
        }
    }

    @Override
    public void onSwiped(int position) {

        getData().remove(position);

        if (mDragListener != null) {
            mDragListener.onSwiped(position);
        }
        notifyItemRemoved(position);

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
