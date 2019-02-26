package com.sos.www.util;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class SmsUtil {
    private static final String TAG = "SmsUtil";
    private static final String SENT_SMS_ACTION = "SENT_SMS_ACTION";
    private Intent sent;
    private PendingIntent sentPI;
    private Context mContext;
    private SmsManager smsManager;
    private boolean canVibrate = false;

    public SmsUtil(Context context) {
        mContext = context;
        sent = new Intent(SENT_SMS_ACTION);
        sentPI = PendingIntent.getBroadcast(mContext, 0, sent, PendingIntent.FLAG_UPDATE_CURRENT);
        smsManager = SmsManager.getDefault();
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int result = getResultCode();
                switch (result) {
                    case RESULT_OK:
                        Log.i(TAG, "----发送成功----");
                        Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                        if (vibrator.hasVibrator() && canVibrate) {
                            Log.i(TAG, "----可以振动----");
                            vibrator.vibrate(500);
                            canVibrate = false;
                        }
                        break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION));
    }

    public void sentMessage(String tel, String msg) {
        canVibrate = true;
        Log.i(TAG, "To:" + tel + " - " + msg);
        ArrayList<String> divideContents = smsManager.divideMessage(msg);
        ArrayList<PendingIntent> sentPIs = new ArrayList<>();
        for(int i = 0 ; i<divideContents.size(); i++){
            sentPIs.add(sentPI);

        }
        smsManager.sendMultipartTextMessage(tel, null, divideContents, sentPIs, null);
    }

}
