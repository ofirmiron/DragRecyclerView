package com.wonshinhyo.dragrecyclerview.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonshinhyo.dragrecyclerview.DragAdapter;
import com.wonshinhyo.dragrecyclerview.DragHolder;
import com.wonshinhyo.dragrecyclerview.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Shinhyo on 2016. 6. 10..
 */
class ExamAdapter extends DragAdapter {

    ExamAdapter(Context context, ArrayList<Integer> data) {
        super(context, data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder hol, int position) {
        super.onBindViewHolder(hol, position);
        Holder holder = (Holder) hol;
        holder.mText.setText(String.valueOf(getData().get(position)));

    }

    private final class Holder extends DragHolder {
        TextView mText;

        Holder(View view, int viewType) {
            super(view);
            mText = (TextView) view.findViewById(R.id.text);
        }
    }
}
