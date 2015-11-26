package com.top.bryon.androidmutiprocessdemo.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.top.bryon.androidmutiprocessdemo.R;
import com.top.bryon.androidmutiprocessdemo.service.MyService;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private MyService myService;
    private boolean isBound;

    private Button btnBindService, btnJump2AnthorProcessActivity, btnJump2MessengerServiceActivity,
                   btnJump2AIDLActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnBindService = (Button) findViewById(R.id.btn_blindMyservice);
        btnBindService.setOnClickListener(this);
        btnJump2AnthorProcessActivity = (Button) findViewById(R.id.btn_jump2SecondProcess);
        btnJump2AnthorProcessActivity.setOnClickListener(this);

        btnJump2MessengerServiceActivity = (Button) findViewById(R.id.btn_jump2MessengerServiceActicity);
        btnJump2MessengerServiceActivity.setOnClickListener(this);
        btnJump2AIDLActivity = (Button) findViewById(R.id.btn_jump2AIDLActvity);
        btnJump2AIDLActivity.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(serviceConnection);
        }
    }

    private void jump2SecondProcessActivity() {
        Log.d(TAG, "Maintivity:" + count++);
        findViewById(R.id.btn_jump2SecondProcess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondProcessActivity.class);
                startActivity(intent);
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            myService = binder.getService();
            myService.add(onChangeListener);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            isBound = false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_blindMyservice:
                bindservice();
                break;
            case R.id.btn_jump2SecondProcess:
                jump2SecondProcessActivity();
                break;
            case R.id.btn_jump2MessengerServiceActicity:
                jump2MessengerServiceActivity();
                break;
            case R.id.btn_jump2AIDLActvity:
                jump2AIDLACtivity();
            default:break;
        }
    }

    private void jump2AIDLACtivity() {
        Intent intent = new Intent(this, AIDLACtivity.class);
        startActivity(intent);
    }

    private void jump2MessengerServiceActivity() {
        Intent intent = new Intent(this, MessengerServiceActivity.class);
        startActivity(intent);
    }

    /**
     *
     */
    private void bindservice() {
        if (!isBound) {
            Intent intent = new Intent(this, MyService.class);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            myService.getRandom();
            //Toast.makeText(this, myService.getRandom() + "", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnChangeListener {
        void onChange(int random);
    }

    private OnChangeListener onChangeListener = new OnChangeListener() {
        @Override
        public void onChange(int random) {
            Toast.makeText(MainActivity.this, random + "", Toast.LENGTH_SHORT).show();
        }
    };
}
