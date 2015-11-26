package com.top.bryon.androidmutiprocessdemo.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.top.bryon.androidmutiprocessdemo.R;
import com.top.bryon.androidmutiprocessdemo.service.MessagerServices;

/**
 * 使用Messenger 和Handler 实现与Service之间的通信,本质上还是依赖与IBinder
 * 优点：
 * 1 可以用于不同进程之间通信，
 * 2 不用考虑多线程问题
 * 3 比AIDL简单
 * 缺点：
 * 通信是串行执行
 */
public class MessengerServiceActivity extends BaseActivity implements View.OnClickListener {

    private Button btnBindService;
    private boolean bound;
    private Messenger messener;

    private Messenger clientMessenger = new Messenger(new ServiceMsgHandler());

    public static final int CLIENT_HELLO = 0;

    @Override
    protected void onStart() {
        super.onStart();
        bindService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(serviceConection);
        }
    }

    private void bindService() {
        Intent intent = new Intent(this, MessagerServices.class);
        bindService(intent, serviceConection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_service);
        initView();
    }

    private void initView() {
        btnBindService = (Button) findViewById(R.id.btn_blindService);
        btnBindService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_blindService:
                communication();
                break;
        }
    }

    private void communication() {
        if (!bound) {
            bindService();
        } else {
            Message message = Message.obtain(null, MessagerServices.SERVICE_HELLO, 0, 0);
            message.replyTo = clientMessenger;

            try {
                messener.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private ServiceConnection serviceConection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            bound = true;
            messener = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            bound = false;
            messener = null;
        }
    };


    /***
     * 处理Service 发过来的消息
     */
    private class ServiceMsgHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CLIENT_HELLO:
                    Toast.makeText(MessengerServiceActivity.this, "Hello client,i am service", Toast.LENGTH_SHORT).show();
                    Log.d(BaseActivity.TAG, "Hello client,i am service");
                    break;
                default:
                    break;
            }
        }
    }
}
