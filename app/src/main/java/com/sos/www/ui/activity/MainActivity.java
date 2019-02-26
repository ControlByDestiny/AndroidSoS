package com.sos.www.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.sos.www.R;
import com.sos.www.bean.EmergencyContact;
import com.sos.www.bean.dao.EmergencyContactDao;
import com.sos.www.constants.Constants;
import com.sos.www.receiver.KeyListener;
import com.sos.www.ui.customview.CustomToolBar;
import com.sos.www.util.DaoManager;
import com.sos.www.util.LocationUtil;
import com.sos.www.util.PermissionUtil;
import com.sos.www.util.SharedPreferencesHelper;
import com.sos.www.util.SmsUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SharedPreferencesHelper sharedPreferencesHelper;
    public static final int FLAG_RECEIVER_INCLUDE_BACKGROUND = 0x01000000;

    private boolean isFirstRun;
    private String KEY = "NOT_FIRST_RUN";
    private CustomToolBar toolbar;
    private Button btnStart;
    private PermissionUtil permissionUtil;
    private LocationUtil locationUtil = null;
    private boolean isStarted = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionUtil = new PermissionUtil(this);
        locationUtil = new LocationUtil(MainActivity.this);
        init();
        initListener();
        initReceiver();

    }

    @SuppressLint("WrongConstant")
    private void initReceiver() {
        KeyListener customBootReceiver = new KeyListener();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(1000);
        intentFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(customBootReceiver,intentFilter,FLAG_RECEIVER_INCLUDE_BACKGROUND);
        } else {
            registerReceiver(customBootReceiver,intentFilter);
        }
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
                Log.i(TAG,"------------onClick-------------");
                if (isStarted){
                    Log.i(TAG,"------------isStarted-------------");
                    return ;
                }
                Log.i(TAG,"------------START LOCATION-------------");
                DaoManager daoManager = DaoManager.getInstance();
                daoManager.init(getApplicationContext());
                EmergencyContactDao emergencyContactDao = daoManager.getEmergencyContactDao();
                final List<EmergencyContact> emergencyContacts = emergencyContactDao.queryBuilder().list();

                final SmsUtil smsUtil = new SmsUtil(MainActivity.this);
                locationUtil.getLoactionData(new AMapLocationListener() {
                    @Override
                    public void onLocationChanged(AMapLocation aMapLocation) {
                        if (aMapLocation != null) {
                            if (aMapLocation.getErrorCode() == 0) {
                                for (EmergencyContact emergencyContact : emergencyContacts){
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append(emergencyContact.getName());
                                    stringBuilder.append(",");
                                    stringBuilder.append(emergencyContact.getMsgContent());
                                    stringBuilder.append("。");
                                    if(aMapLocation.getAddress()!= null){
                                        stringBuilder.append("我现在的位置是：");
                                        stringBuilder.append(aMapLocation.getAddress());
                                    }
                                    stringBuilder.append("。");
                                    stringBuilder.append("我当前所在位置的经度为：");
                                    stringBuilder.append(aMapLocation.getLongitude());
                                    stringBuilder.append("，");
                                    stringBuilder.append("我当前所在位置的纬度为：");
                                    stringBuilder.append( aMapLocation.getLatitude());
                                    stringBuilder.append( "。");
                                    stringBuilder.append( SimpleDateFormat.getDateTimeInstance().format(System.currentTimeMillis()));
                                    String msg = stringBuilder.toString();
                                    Log.i(TAG,msg);
                                    smsUtil.sentMessage(emergencyContact.getTelNumber(),msg);
                                }
                            } else {
                                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                                Log.e("AmapError","location Error, ErrCode:"
                                        + aMapLocation.getErrorCode() + ", errInfo:"
                                        + aMapLocation.getErrorInfo());
                            }
                        }
                    }
                });
                locationUtil.startService();
                isStarted = true;
            }
        });
        btnStart.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i(TAG,"------------------LONG---------------------------");
                locationUtil.stopService();
                isStarted = false;
                return true;
            }
        });
    }

    private void init() {
        toolbar = findViewById(R.id.act_tb_main);
        btnStart = findViewById(R.id.act_btn_start);
        sharedPreferencesHelper = new SharedPreferencesHelper(this.getApplicationContext());
        sharedPreferencesHelper.open(Constants.FILE_NAME);
        isFirstRun = !sharedPreferencesHelper.getBoolean(KEY);
        if (isFirstRun) {
            //第一次运行
            Log.i(TAG,"First Time Run Application");
            Intent intent = new Intent();
            intent.setAction(Constants.NEW_ACTION);
            startActivityForResult(intent, Constants.REQUEST_CODE);

        } else {
            permissionUtil.requestPermissions();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE && resultCode == Constants.RESULT_CODE){
            Log.i(TAG,"Already Add Information");
            sharedPreferencesHelper.putBoolean(KEY, isFirstRun);
            permissionUtil.requestPermissions();
        }
    }
}
