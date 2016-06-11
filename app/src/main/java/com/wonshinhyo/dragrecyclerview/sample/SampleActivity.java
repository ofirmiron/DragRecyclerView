package com.wonshinhyo.dragrecyclerview.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wonshinhyo.dragrecyclerview.DragRecyclerView;
import com.wonshinhyo.dragrecyclerview.DragRecyclerViewAdapter;
import com.wonshinhyo.dragrecyclerview.DragTouchCallback;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class SampleActivity extends AppCompatActivity {

    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        getSupportActionBar().setTitle("Sample");

        DragRecyclerView recyclerView = (DragRecyclerView) findViewById(R.id.recyclerview);
        if (getIntent().getIntExtra("mode", 0) == 0) { //list
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
            recyclerView.setLayoutManager(gridLayoutManager);
        }

        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration // com.yqritc:recyclerview-flexibledivider
                        .Builder(this)
                        .color(Color.parseColor("#727272"))
                        .size(1)
                        .build()
        );

        ArrayList<Integer> data = new ArrayList<>(); // dummy data
        for (int i = 0; i < 50; i++) {
            data.add(i);
        }

        mAdapter = new Adapter(this, data);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setHandleDragEnabled(true); // default true
        recyclerView.setLongPressDragEnabled(true); // default true
        recyclerView.setSwipeEnabled(true); // default true

        mAdapter.setOnItemClickListener(new DragRecyclerViewAdapter.SimpleClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                super.onItemClick(pos, v);
                Toast.makeText(SampleActivity.this, "onItemClick\npos: " + pos + " text: "
                        + mAdapter.getData().get(pos), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int pos, View v) {
                super.onItemLongClick(pos, v);
                Toast.makeText(SampleActivity.this, "onItemLongClick\npos: " + pos + " text: "
                        + mAdapter.getData().get(pos), Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnItemDragListener(new DragTouchCallback.SimpleDragListener() {
            @Override
            public boolean onMove(int fromPosition, int toPosition) {
                Log.d("drag", fromPosition + " -> " + toPosition);
                return super.onMove(fromPosition, toPosition);
            }

            @Override
            public void onSwiped(int pos) {
                super.onSwiped(pos);
                Toast.makeText(SampleActivity.this, "onSwiped\npos: " + pos + " text: "
                        + mAdapter.getData().get(pos), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
