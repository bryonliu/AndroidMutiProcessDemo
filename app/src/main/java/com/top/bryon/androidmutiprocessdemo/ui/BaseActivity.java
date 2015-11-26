package com.top.bryon.androidmutiprocessdemo.ui;

import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.top.bryon.androidmutiprocessdemo.utils.ProcessUtils;

/**
 * Created by bryonliu on 2015/11/16.
 */
public class BaseActivity extends AppCompatActivity {
    public static final String TAG = "MUTLI";
    protected static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "pid = " + ProcessUtils.getPid() + " PrcessName = " + ProcessUtils.getProcessName(this));
    }
}
