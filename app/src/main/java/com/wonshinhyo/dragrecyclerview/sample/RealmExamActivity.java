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
    private static final int SIZE = 10;

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
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .initialData(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmList<Dummy> dummies = new RealmList<>();
                        for (int i = 0; i < SIZE; i++) {
                            dummies.add(new Dummy(i, i, i));
                        }
                        realm.copyToRealm(dummies);
                    }
                })
                .build();

        mRealm = Realm.getInstance(realmConfiguration);

        final RealmList<Dummy> list = new RealmList<>();
        for (Dummy dummy : mRealm.where(Dummy.class).findAllSorted("sort")) {
            Log.w("test", "dummy " + dummy.getId() + " - " + dummy.getNum() + " - " + dummy.getSort());
            list.add(dummy);
        }


        mAdapter = new RealmExamAdapter(this, list, true);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setHandleDragEnabled(true); // default true
        mAdapter.setLongPressDragEnabled(false); // default true
        mAdapter.setSwipeEnabled(true); // default true

        mAdapter.setOnItemClickListener(new SimpleClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                super.onItemClick(v, pos);
                Toast.makeText(RealmExamActivity.this, "onItemClick\npos: " + pos + " text: "
                        + mAdapter.getData().get(pos), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View v, int pos) {
                super.onItemLongClick(v, pos);
                Toast.makeText(RealmExamActivity.this, "onItemLongClick\npos: " + pos + " text: "
                        + mAdapter.getData().get(pos), Toast.LENGTH_SHORT).show();
            }
        });


        mAdapter.setOnItemDragListener(new SimpleDragListener() {

            @Override
            public void onDrop(int fromPosition, int toPosition) {
                super.onDrop(fromPosition, toPosition);
                Log.e("test", "------------------------------");
                Log.e("test", fromPosition + "->" + toPosition);

                mRealm.beginTransaction();
                OrderedRealmCollection collection = mAdapter.getData();
                for (int i = 0; i < collection.size(); i++) {
                    Dummy dummy = (Dummy) collection.get(i);
                    if (dummy.getSort() == i) {
                        continue;
                    }
                    dummy.setSort(i);
                }
                mRealm.commitTransaction();
            }

            @Override
            public void onSwiped(int pos) {
                super.onSwiped(pos);
                Log.d("drag", "onSwiped " + pos);
                Toast.makeText(RealmExamActivity.this, "onSwiped\npos: " + pos, Toast.LENGTH_SHORT).show();

                mRealm.beginTransaction();
                mRealm.where(Dummy.class).equalTo("sort", pos).findAllSorted("sort").deleteAllFromRealm();
                mRealm.commitTransaction();

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
        mRealm.beginTransaction();
        mRealm.where(Dummy.class).findAll().deleteAllFromRealm();

        RealmList<Dummy> dummies = new RealmList<>();
        for (int i = 0; i < SIZE; i++) {
            dummies.add(new Dummy(i, i, i));
        }
        mRealm.copyToRealm(dummies);
        mRealm.commitTransaction();

        mAdapter.getData().clear();
        mAdapter.getData().addAll(dummies);
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
