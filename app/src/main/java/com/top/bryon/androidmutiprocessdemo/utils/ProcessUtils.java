package com.top.bryon.androidmutiprocessdemo.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by bryonliu on 2015/11/16.
 */
public class ProcessUtils {
    public static int getPid() {
        return android.os.Process.myPid();
    }

    public static String getProcessName(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = mActivityManager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : appProcessInfoList) {
            if (runningAppProcessInfo.pid == getPid()) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }
}
