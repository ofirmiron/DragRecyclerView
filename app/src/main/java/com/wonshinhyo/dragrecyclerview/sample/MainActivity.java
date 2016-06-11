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

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SampleActivity.class);
        switch (v.getId()) {
            case R.id.btn1:
                intent.putExtra("mode", 0); //list
                break;
            case R.id.btn2:
                intent.putExtra("mode", 1); //grid
                break;
        }
        startActivity(intent);
    }
}
