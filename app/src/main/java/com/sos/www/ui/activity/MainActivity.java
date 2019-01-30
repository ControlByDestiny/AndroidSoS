package com.sos.www.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.sos.www.R;
import com.sos.www.bean.EmergencyContact;
import com.sos.www.bean.dao.DaoSession;
import com.sos.www.bean.dao.EmergencyContactDao;
import com.sos.www.constants.Constants;
import com.sos.www.ui.customview.CustomToolBar;
import com.sos.www.util.Base64Util;
import com.sos.www.util.DaoManager;
import com.sos.www.util.SharedPreferencesHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SharedPreferencesHelper sharedPreferencesHelper;
    private static final String FILE_NAME = Base64Util.encode("START_UP".getBytes());
    private boolean isFirstRun;
    private String KEY = "NOT_FIRST_RUN";
    private CustomToolBar toolbar;
    private ImageView rightIcon;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initListener();
    }

    private void initListener() {
        toolbar.setOnRightIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Constants.SETTING_ACTION);
                startActivity(intent);
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoManager daoManager = DaoManager.getInstance();
                daoManager.init(getApplicationContext());
                EmergencyContactDao emergencyContactDao = daoManager.getEmergencyContactDao();
                List<EmergencyContact> emergencyContacts = emergencyContactDao.queryBuilder().list();
                for(EmergencyContact emergencyContact : emergencyContacts){
                    Log.i(TAG,"name = " + emergencyContact.getName());
                    Log.i(TAG,"tel = " + emergencyContact.getTelNumber());
                    Log.i(TAG,"msg = " + emergencyContact.getMsgContent());
                }
            }
        });
    }

    private void init() {
        toolbar = findViewById(R.id.act_tb_main);
        btnStart = findViewById(R.id.act_btn_start);
        sharedPreferencesHelper = new SharedPreferencesHelper(this.getApplicationContext());
        sharedPreferencesHelper.open(FILE_NAME);
        isFirstRun = !sharedPreferencesHelper.getBoolean(KEY);
        if (isFirstRun) {
            //第一次运行
            Intent intent = new Intent();
            intent.setAction(Constants.NEW_ACTION);
            startActivityForResult(intent, 1024);
            sharedPreferencesHelper.putBoolean(KEY, isFirstRun);
        }
    }
}
