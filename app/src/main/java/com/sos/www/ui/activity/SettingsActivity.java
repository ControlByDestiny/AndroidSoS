package com.sos.www.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.sos.www.R;

public class SettingsActivity extends AppCompatActivity {
    CheckBox btnName,btnTel,btnMsg;
    LinearLayout nameDetail,telDetail,msgDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnName = findViewById(R.id.act_btn_show_name);
        btnTel = findViewById(R.id.act_btn_show_tel);
        btnMsg = findViewById(R.id.act_btn_show_msg);

        nameDetail = findViewById(R.id.name_detail);
        telDetail = findViewById(R.id.tel_detail);
        msgDetail = findViewById(R.id.msg_detail);

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
}
