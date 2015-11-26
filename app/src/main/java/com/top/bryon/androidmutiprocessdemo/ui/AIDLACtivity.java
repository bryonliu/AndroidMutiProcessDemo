package com.top.bryon.androidmutiprocessdemo.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.top.bryon.androidmutiprocessdemo.INetCallback;
import com.top.bryon.androidmutiprocessdemo.R;
import com.top.bryon.androidmutiprocessdemo.service.AIDLService;
import com.top.bryon.androidmutiprocessdemo.service.IAIDLService;
import com.top.bryon.androidmutiprocessdemo.utils.ProcessUtils;

public class AIDLACtivity extends BaseActivity {

    private IAIDLService mIAIDLService;
    private boolean mboud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidlactivity);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_blindService:
                bindservic();
                break;
            default:
                break;
        }
    }

    private void bindservic() {
        if (!mboud) {
            Intent intent = new Intent(this, AIDLService.class);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mIAIDLService.hello(1);
                        Log.d(TAG,Thread.currentThread().getId()+"");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private INetCallback iNetCallback = new INetCallback.Stub() {
        @Override
        public void OnNetChanged(int state) throws RemoteException {
            Log.d(TAG, "obtain message from the service. The ProccssId is  " + ProcessUtils.getPid() + " The ThreadId is " + Thread.currentThread().getId());
        }
    };
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mIAIDLService = IAIDLService.Stub.asInterface(service);
            try {
                mIAIDLService.register(iNetCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.d(TAG, e.getMessage(), e);
            }
            mboud = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mboud = false;
            mIAIDLService = null;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        mboud = false;
        mIAIDLService = null;
        unbindService(serviceConnection);
    }
}
