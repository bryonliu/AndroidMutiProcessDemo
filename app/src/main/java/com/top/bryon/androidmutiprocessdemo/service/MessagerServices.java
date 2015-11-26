package com.top.bryon.androidmutiprocessdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.top.bryon.androidmutiprocessdemo.ui.BaseActivity;
import com.top.bryon.androidmutiprocessdemo.ui.MessengerServiceActivity;
import com.top.bryon.androidmutiprocessdemo.utils.ProcessUtils;

public class MessagerServices extends Service {
    private int time = 0;

    public MessagerServices() {
        Log.d(BaseActivity.TAG, "pid = " + ProcessUtils.getPid());
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(BaseActivity.TAG, "onBind");
        return mMessenger.getBinder();
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    /**
     * Command to the service to display a message
     */
    public static final int SERVICE_HELLO = 1;

    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SERVICE_HELLO:
                    Toast.makeText(getApplicationContext(), "hello service, i am client", Toast.LENGTH_SHORT).show();
                    Log.d(BaseActivity.TAG, "hello service, i am client");
                    Messenger clientMessager = msg.replyTo;

                    Message message = Message.obtain(null, MessengerServiceActivity.CLIENT_HELLO);

                    try {
                        clientMessager.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}

