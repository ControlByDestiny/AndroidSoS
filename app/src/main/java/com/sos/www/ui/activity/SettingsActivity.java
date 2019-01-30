package com.sos.www.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sos.www.R;
import com.sos.www.bean.EmergencyContact;
import com.sos.www.ui.customview.CustomToolBar;
import com.sos.www.util.DaoManager;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class SettingsActivity extends AppCompatActivity {
    CheckBox btnName,btnTel,btnMsg;
    LinearLayout nameDetail,telDetail,msgDetail;
    CustomToolBar customToolBar;
    EditText etName,etTel,etMsg;
    private List<EmergencyContact> emergencyContacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        initDatas();
        initListeners();

    }

    private void initDatas() {
        DaoManager daoManager = DaoManager.getInstance();
        daoManager.init(SettingsActivity.this);
        emergencyContacts = daoManager.getEmergencyContactDao().queryBuilder().list();
        etName.setText(emergencyContacts.get(0).getName());
        etTel.setText(emergencyContacts.get(0).getTelNumber());
        etMsg.setText(emergencyContacts.get(0).getMsgContent());
    }

    private void initListeners() {
        customToolBar.setOnLeftIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        customToolBar.setOnRightIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.success(SettingsActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nameDetail.setVisibility(isChecked?LinearLayout.VISIBLE:LinearLayout.GONE);
            }
        });

        btnTel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                telDetail.setVisibility(isChecked?LinearLayout.VISIBLE:LinearLayout.GONE);
            }
        });

        btnMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                msgDetail.setVisibility(isChecked?LinearLayout.VISIBLE:LinearLayout.GONE);
            }
        });
    }

    private void initViews() {
        btnName = findViewById(R.id.act_btn_show_name);
        btnTel = findViewById(R.id.act_btn_show_tel);
        btnMsg = findViewById(R.id.act_btn_show_msg);

        nameDetail = findViewById(R.id.name_detail);
        telDetail = findViewById(R.id.tel_detail);
        msgDetail = findViewById(R.id.msg_detail);

        customToolBar = findViewById(R.id.act_tb_setting);

        etName = findViewById(R.id.act_et_setting_name);
        etTel = findViewById(R.id.act_et_setting_tel);
        etMsg = findViewById(R.id.act_et_setting_msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }
}
