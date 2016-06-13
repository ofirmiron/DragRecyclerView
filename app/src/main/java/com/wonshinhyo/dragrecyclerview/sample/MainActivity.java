package com.wonshinhyo.dragrecyclerview.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        Class clazz = null;
        int mode = 0; // 0 list 1 grid
        switch (v.getId()) {
            case R.id.btn1:
                clazz = ExamActivity.class;
                mode = 0;
                break;
            case R.id.btn2:
                clazz = ExamActivity.class;
                mode = 1;
                break;
            case R.id.btn3:
                clazz = RealmExamActivity.class;
                mode = 0;
                break;
            case R.id.btn4:
                clazz = RealmExamActivity.class;
                mode = 1;
                break;
        }
        Intent intent = new Intent(this, clazz);
        intent.putExtra("mode", mode);
        startActivity(intent);
    }
}
