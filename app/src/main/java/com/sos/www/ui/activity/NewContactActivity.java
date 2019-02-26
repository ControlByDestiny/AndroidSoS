package com.sos.www.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.sos.www.R;
import com.sos.www.bean.EmergencyContact;
import com.sos.www.bean.dao.EmergencyContactDao;
import com.sos.www.constants.Constants;
import com.sos.www.ui.customview.CustomToolBar;
import com.sos.www.util.DaoManager;
import com.sos.www.util.SharedPreferencesHelper;

import es.dmoral.toasty.Toasty;

public class NewContactActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText etName,etTel,etMsg;
    private CustomToolBar toolBar;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private boolean isFirstRun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        initViews();
        sharedPreferencesHelper = new SharedPreferencesHelper(this.getApplicationContext());
        sharedPreferencesHelper.open(Constants.FILE_NAME);
        isFirstRun = !sharedPreferencesHelper.getBoolean("NOT_FIRST_RUN");
        if (!isFirstRun) {
            toolBar.setLeftIcon(getDrawable(R.drawable.ic_back));
            toolBar.setOnLeftIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        toolBar.setOnRightIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"toolBar.setOnRightIconClickListener");
                String name = etName.getText().toString();
                String tel = etTel.getText().toString();
                String msg = etMsg.getText().toString();
                if (TextUtils.isEmpty(tel)) {
                    Toasty.error(NewContactActivity.this,"联系方式不能为空！",Toasty.LENGTH_SHORT).show();
                    return ;
                }
                if (TextUtils.isEmpty(msg)){
                    Toasty.error(NewContactActivity.this,"信息不能为空！",Toasty.LENGTH_SHORT).show();
                    return ;
                }

                DaoManager daoManager = DaoManager.getInstance();
                daoManager.init(getApplicationContext());

                EmergencyContactDao emergencyContactDao = daoManager.getEmergencyContactDao();
                EmergencyContact emergencyContact = new EmergencyContact();
                emergencyContact.setName(name);
                emergencyContact.setTelNumber(tel);
                emergencyContact.setMsgContent(msg);

                emergencyContactDao.insertOrReplace(emergencyContact);
                Toasty.success(NewContactActivity.this,"添加成功",Toasty.LENGTH_SHORT).show();
                setResult(Constants.RESULT_CODE);
                finish();
            }
        });
    }
    private void initViews(){
        toolBar = findViewById(R.id.act_tb_new);
        etName = findViewById(R.id.act_et_new_name);
        etTel = findViewById(R.id.act_et_new_tel);
        etMsg = findViewById(R.id.act_et_new_msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isFirstRun){
                return true;
            }else{
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
