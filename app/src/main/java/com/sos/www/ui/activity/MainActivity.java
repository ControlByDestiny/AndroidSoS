package com.sos.www.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sos.www.R;
import com.sos.www.util.Base64Util;
import com.sos.www.util.SharedPreferencesHelper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SharedPreferencesHelper sharedPreferencesHelper;
    private static final String FILE_NAME = Base64Util.encode("START_UP".getBytes());
    private boolean isFirstRun;
    private String KEY ="NOT_FIRST_RUN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"MainActivity-onCreate");
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"MainActivity-onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"MainActivity-onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"MainActivity-onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"MainActivity-onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"MainActivity-onDestroy");
    }

    private void init(){
        sharedPreferencesHelper = new SharedPreferencesHelper(this.getApplicationContext());
        sharedPreferencesHelper .open(FILE_NAME);
        isFirstRun = ! sharedPreferencesHelper.getBoolean(KEY);
        if(isFirstRun){
            //第一次运行
            Intent intent = new Intent();
            intent.setAction("android.new.contact");
            startActivityForResult(intent,1024);
            sharedPreferencesHelper.putBoolean(KEY,isFirstRun);
            finish();
        }
    }
}
