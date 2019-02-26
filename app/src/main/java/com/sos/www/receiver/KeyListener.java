package com.sos.www.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class KeyListener extends BroadcastReceiver {
    private static final String TAG = "KeyListener";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"--onReceive--" + intent.getAction());
        String reason = intent.getStringExtra("reason");
        if (intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)){
            Log.i(TAG,"Intent.ACTION_CLOSE_SYSTEM_DIALOGS : " + intent.getStringExtra("reason"));

            if (reason != null){
                if (reason.equalsIgnoreCase("globalactions")){

                    //监听电源长按键的方法：
                    Intent myIntent = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                    myIntent.putExtra("myReason", true);
                    context.sendOrderedBroadcast(myIntent, null);
                    Log.i(TAG,"电源  键被长按");

                }else if (reason.equalsIgnoreCase("homekey")){

                    //监听Home键的方法
                    //在这里做一些你自己想要的操作,比如重新打开自己的锁屏程序界面，这样子就不会消失了
                    Log.i(TAG,"Home 键被触发");

                }else if (reason.equalsIgnoreCase("recentapps")){

                    //监听Home键长按的方法
                    Log.i(TAG,"Home 键被长按");
                }
            }
        }
    }
}
