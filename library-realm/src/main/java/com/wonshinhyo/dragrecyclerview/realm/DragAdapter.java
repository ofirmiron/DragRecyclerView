package com.wonshinhyo.dragrecyclerview.realm;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wonshinhyo.dragrecyclerview.DragHolder;
import com.wonshinhyo.dragrecyclerview.ImpAdapter;
import com.wonshinhyo.dragrecyclerview.OnClickListener;
import com.wonshinhyo.dragrecyclerview.OnDragListener;
import com.wonshinhyo.dragrecyclerview.RecyclerView;

import java.util.Collections;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Shinhyo on 2016. 6. 10..
 */

public class DragAdapter extends RealmRecyclerViewAdapter implements ImpAdapter, OnDragListener {

    private boolean isHandleDragEnabled = true;
    private OnDragListener mDragListener;
    private RecyclerView mRecyclerView;


    public DragAdapter(Context context, OrderedRealmCollection data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }


    @Override
    public android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

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

    public void setRecycleView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public void setLongPressDragEnabled(boolean set) {
        mRecyclerView.getTouchHelperCallback().setLongPressDragEnabled(set);
    }

    public void setSwipeEnabled(boolean set) {
        mRecyclerView.getTouchHelperCallback().setItemViewSwipeEnabled(set);
    }

}
