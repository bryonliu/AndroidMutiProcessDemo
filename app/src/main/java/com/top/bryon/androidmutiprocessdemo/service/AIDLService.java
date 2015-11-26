package com.top.bryon.androidmutiprocessdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.top.bryon.androidmutiprocessdemo.INetCallback;
import com.top.bryon.androidmutiprocessdemo.ui.BaseActivity;
import com.top.bryon.androidmutiprocessdemo.utils.ProcessUtils;

public class AIDLService extends Service {

    private static final String TAG = BaseActivity.TAG;
    private RemoteCallbackList<INetCallback> mRemoteCallbackList = new RemoteCallbackList<>();

    public AIDLService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBind;
    }

    private IAIDLService.Stub mBind = new IAIDLService.Stub() {

        @Override
        public void hello(int index) throws RemoteException {
            Log.d(TAG, "obtain message from the client. The ProccssId is  " + ProcessUtils.getPid() + " The ThreadId is " + Thread.currentThread().getId());
            final int n = mRemoteCallbackList.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    INetCallback iNetCallback = mRemoteCallbackList.getBroadcastItem(i);
                    if (iNetCallback != null) {
                        iNetCallback.OnNetChanged(0);
                    } else {
                        Log.d(TAG, "obtain a null INetCallback from RemoteCallbackList");
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage(), e);
                }

            }
            mRemoteCallbackList.finishBroadcast();
        }

        @Override
        public void register(INetCallback callback) throws RemoteException {
            mRemoteCallbackList.register(callback);
            Log.d(TAG, "resigter callback");
        }

        @Override
        public void unregister(INetCallback callback) throws RemoteException {
            mRemoteCallbackList.unregister(callback);
            Log.d(TAG, "unresigter callback");
        }
    };
}
