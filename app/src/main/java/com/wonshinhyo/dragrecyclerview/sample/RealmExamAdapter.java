package com.wonshinhyo.dragrecyclerview.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonshinhyo.dragrecyclerview.DragHolder;
import com.wonshinhyo.dragrecyclerview.realm.DragRecyclerViewAdapter;
import com.wonshinhyo.dragrecyclerview.sample.item.Dummy;

import io.realm.RealmList;

/**
 * Created by Shinhyo on 2016. 6. 10..
 */
class RealmExamAdapter extends DragRecyclerViewAdapter {

    public RealmExamAdapter(Context context, RealmList data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.row, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder hol, int position) {
        super.onBindViewHolder(hol, position);
        final Holder holder = (Holder) hol;
        holder.mText.setText(String.valueOf(((Dummy) getData().get(position)).getNum()));
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }

    private static final class Holder extends DragHolder {

        TextView mText;

        Holder(View view, int viewType) {
            super(view);
            mText = (TextView) view.findViewById(R.id.text);
        }
    }

}
