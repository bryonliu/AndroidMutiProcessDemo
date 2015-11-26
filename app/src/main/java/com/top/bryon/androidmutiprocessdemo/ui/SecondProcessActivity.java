package com.top.bryon.androidmutiprocessdemo.ui;

import android.os.Bundle;
import android.util.Log;

import com.top.bryon.androidmutiprocessdemo.R;

public class SecondProcessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SecondProcessActivity:" + count++);
        setContentView(R.layout.activity_second_process);
    }
}
