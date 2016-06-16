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
import com.wonshinhyo.dragrecyclerview.SimpleClickListener;
import com.wonshinhyo.dragrecyclerview.SimpleDragListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class ExamActivity extends AppCompatActivity {

    private ExamAdapter mAdapter;

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
        for (int i = 0; i < 10; i++) {
            data.add(i);
        }

        mAdapter = new ExamAdapter(this, data);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setHandleDragEnabled(true); // default true
        mAdapter.setLongPressDragEnabled(true); // default true
        mAdapter.setSwipeEnabled(true); // default true

        mAdapter.setOnItemClickListener(new SimpleClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                super.onItemClick(v, pos);
                Toast.makeText(ExamActivity.this, "onItemClick\npos: " + pos + " text: "
                        + mAdapter.getData().get(pos), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View v, int pos) {
                super.onItemClick(v, pos);
                Toast.makeText(ExamActivity.this, "onItemLongClick\npos: " + pos + " text: "
                        + mAdapter.getData().get(pos), Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnItemDragListener(new SimpleDragListener() {

            @Override
            public void onDrop(int fromPosition, int toPosition) {
                super.onDrop(fromPosition, toPosition);
                Log.i("drag", "onDrop " + fromPosition + " -> " + toPosition);
            }

            @Override
            public void onSwiped(int pos) {
                super.onSwiped(pos);
                Log.d("drag", "onSwiped " + pos);
                Toast.makeText(ExamActivity.this, "onSwiped\npos: " + pos, Toast.LENGTH_SHORT).show();
            }
        });


    }

}
