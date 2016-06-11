package com.wonshinhyo.dragrecyclerview.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonshinhyo.dragrecyclerview.DragRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by Shinhyo on 2016. 6. 10..
 */
class Adapter extends DragRecyclerViewAdapter {

    private final ArrayList<Integer> mData;

    Adapter(Context context, ArrayList<Integer> data) {
        super(context);
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.row, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder hol, int position) {
        super.onBindViewHolder(hol, position);
        final Holder holder = (Holder) hol;
        holder.mText.setText(String.valueOf(mData.get(position)));
    }

    @Override
    public ArrayList<Integer> getData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    private static final class Holder extends DragHolder {

        TextView mText;

        Holder(View view, int viewType) {
            super(view);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            mText = (TextView) view.findViewById(R.id.text);

        }


    }

}
