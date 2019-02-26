package com.sos.www.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class CustomBootReceiver extends BroadcastReceiver {
    private static final String TAG = "CustomBootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "-------------------onReceive-------------");
        Log.i(TAG, "-------------------Waiting...-------------");
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.i(TAG, "-----"+intent.getAction()+"--");
            Log.i(TAG, "---------------------监听到了开机-------------");
            //启动后台按键监听服务
        }
//        if (Intent.ACTION_REBOOT.equals(intent.getAction())) {
//            Log.i(TAG, "-----"+intent.getAction()+"--");
//            Log.i(TAG, "---------------------监听到了重启-------------");
//        }
    }
}
