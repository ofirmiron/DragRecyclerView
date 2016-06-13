package com.wonshinhyo.dragrecyclerview.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wonshinhyo.dragrecyclerview.DragRecyclerView;
import com.wonshinhyo.dragrecyclerview.SimpleClickListener;
import com.wonshinhyo.dragrecyclerview.SimpleDragListener;
import com.wonshinhyo.dragrecyclerview.sample.item.Dummy;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class RealmExamActivity extends AppCompatActivity {
    private RealmExamAdapter mAdapter;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        getSupportActionBar().setTitle("Realm Sample");

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


        // dummy data
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        mRealm = Realm.getInstance(realmConfiguration);
        if (mRealm.where(Dummy.class).count() <= 0) {
            setData();
        }

        final RealmList<Dummy> list = new RealmList<>();
        for (Dummy dummy : mRealm.where(Dummy.class).findAllSorted("id")) {
            list.add(dummy);
        }

        //
        mAdapter = new RealmExamAdapter(this, list, true);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setHandleDragEnabled(true); // default true
        recyclerView.setLongPressDragEnabled(true); // default true
        recyclerView.setSwipeEnabled(true); // default true

        mAdapter.setOnItemClickListener(new SimpleClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                super.onItemClick(pos, v);
                Toast.makeText(RealmExamActivity.this, "onItemClick\npos: " + pos + " text: "
                        + mAdapter.getData().get(pos), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int pos, View v) {
                super.onItemLongClick(pos, v);
                Toast.makeText(RealmExamActivity.this, "onItemLongClick\npos: " + pos + " text: "
                        + mAdapter.getData().get(pos), Toast.LENGTH_SHORT).show();
            }
        });


        mAdapter.setOnItemDragListener(new SimpleDragListener() {
            @Override
            public boolean onMove(int fromPosition, int toPosition) {
                Log.d("drag", "onMove " + fromPosition + " -> " + toPosition);
                return super.onMove(fromPosition, toPosition);
            }

            @Override
            public void onDrop(int fromPosition, int toPosition) {
                super.onDrop(fromPosition, toPosition);
                Log.i("drag", "onDrop " + fromPosition + " -> " + toPosition);

                RealmList<Dummy> list = new RealmList<>();
                OrderedRealmCollection collection = mAdapter.getData();
                for (int i = 0; i < collection.size(); i++) {
                    Dummy old = (Dummy) collection.get(i);
                    list.add(new Dummy(i, old.getNum()));
                }
                mRealm.beginTransaction();
                mRealm.copyToRealmOrUpdate(list);
                mRealm.commitTransaction();

            }

            @Override
            public void onSwiped(int pos) {
                super.onSwiped(pos);
                Toast.makeText(RealmExamActivity.this, "onSwiped\npos: " + pos + " text: "
                        + mAdapter.getData().get(pos), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setData() {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmList<Dummy> dummies = new RealmList<>();
                for (int i = 0; i < 10; i++) {
                    dummies.add(new Dummy(i, i));
                }
                mRealm.copyToRealm(dummies);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "reset");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("test", "onOptionsItemSelected");

        mRealm.beginTransaction();
//        RealmList<Dummy> list = new RealmList<>();
//        RealmResults<Dummy> dummies = mRealm.where(Dummy.class).findAllSorted("id");
//
//        for (int i = 0; i <dummies.size(); i++) {
//            Dummy dummy = dummies.get(i);
//            Log.d("test", dummy.getId() + "/" + dummy.getNum());
//            dummy.setId(i);
//            dummy.setNum(i);
//            list.add(dummy);
//            Log.i("test", dummy.getId() + "/" + dummy.getNum());
//        }
//        mRealm.copyToRealmOrUpdate(list);
//

        OrderedRealmCollection li = mAdapter.getData();
        for (int i = 0; i < li.size(); i++) {
            Dummy dummy = (Dummy) li.get(i);
            Log.d("test", dummy.getId() + "/" + dummy.getNum());
            dummy.setId(i);
            dummy.setNum(i);
        }
        mRealm.commitTransaction();
        mAdapter.notifyDataSetChanged();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
    }
}
