package com.top.bryon.androidmutiprocessdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.top.bryon.androidmutiprocessdemo.ui.BaseActivity;
import com.top.bryon.androidmutiprocessdemo.ui.MainActivity;

import java.util.Random;

/**
 * 同一进程下Service与Activity之间的通信
 */
public class MyService extends Service {

    private IBinder mBinder = new LocalBinder();

    private Random random = new Random(100);
    private MainActivity.OnChangeListener onChangeListener;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(BaseActivity.TAG, "onBind");
        return mBinder;
    }

    public void notifyChange() {
        onChangeListener.onChange(random.nextInt());
    }

    public void add(MainActivity.OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }


    /**
     * 自己继承实现Binder的方式仅仅适用于该Serivce仅仅用于当前应用，且处于同一个进程中
     */
    public class LocalBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }
    public void getRandom() {
        notifyChange();
    }
}
