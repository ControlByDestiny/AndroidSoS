package com.sos.www.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sos.www.R;
import com.sos.www.bean.EmergencyContact;
import com.sos.www.bean.dao.EmergencyContactDao;
import com.sos.www.constants.Constants;
import com.sos.www.ui.adapter.ListAdapter;
import com.sos.www.ui.customview.CustomTextView;
import com.sos.www.ui.customview.CustomToolBar;
import com.sos.www.util.DaoManager;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    CustomToolBar customToolBar;
    private CustomTextView btnUpdate, btnAdd;
    private TextView tip;
    private ListView listView;
    private List<EmergencyContact> emergencyContacts;
    private List<EmergencyContact> origin = new ArrayList<>();
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
        initDatas();
        copyDatas(emergencyContacts,origin);
        listAdapter = new ListAdapter(SettingsActivity.this, emergencyContacts);
        listView.setAdapter(listAdapter);
        listView.setVisibility(ListView.GONE);
        tip.setVisibility(TextView.GONE);
        customToolBar.setRightIconEnabled(false);
        btnAdd.setOnItemClickListener(new CustomTextView.ItemClickListener() {
            @Override
            public void onItemClick(View v) {
                listView.setVisibility(ListView.GONE);
                Intent intent = new Intent(Constants.NEW_ACTION);
                startActivity(intent);
                finish();
            }
        });
        btnAdd.setNoneDivider();
        btnUpdate.setOnItemClickListener(new CustomTextView.ItemClickListener(){
            @Override
            public void onItemClick(View v) {
                listView.setVisibility(listView.getVisibility() == ListView.GONE ? ListView.VISIBLE : ListView.GONE);
                tip.setVisibility(tip.getVisibility() == TextView.GONE ? TextView.VISIBLE : TextView.GONE);
            }
        });
        listAdapter.setOnDataChangedListener(new ListAdapter.DataChangedListener() {
            @Override
            public void onDataChanged(int position) {
                EmergencyContact emergencyContact = origin.get(position);
                Log.i(TAG,emergencyContact.toString());
                Log.i(TAG,emergencyContacts.get(position).toString());
                boolean isEnabled = !emergencyContact.toString().equals(emergencyContacts.get(position).toString());
                customToolBar.setRightIconEnabled(isEnabled);
                Log.i(TAG,"isRightEnabled:"+ customToolBar.isRightEnabled());
            }
        });
        customToolBar.setOnLeftIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        customToolBar.setOnRightIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoManager daoManager = DaoManager.getInstance();
                daoManager.init(SettingsActivity.this);
                EmergencyContactDao emergencyContactDao = daoManager.getEmergencyContactDao();
                emergencyContactDao.insertOrReplaceInTx(emergencyContacts);
                Toasty.success(SettingsActivity.this,"保存成功！",Toasty.LENGTH_SHORT).show();
                finish();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG,"-----长按---position:"+position+ "id:"+id);
                showPopMenu(view,position);
                return true;
            }
        });
    }

    private void showPopMenu(View v, final int postion){
        PopupMenu popupMenu = new PopupMenu(SettingsActivity.this,v,Gravity.CENTER);
        popupMenu.inflate(R.menu.menu_longclick);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if ( item.getTitle().toString().equals("删除")){
                    //TODO
                    Log.i(TAG,"点了删除项");
                    DaoManager daoManager = DaoManager.getInstance();
                    daoManager.init(SettingsActivity.this);
                    EmergencyContactDao emergencyContactDao = daoManager.getEmergencyContactDao();
                    emergencyContactDao.delete(origin.get(postion));
                    origin.remove(postion);
                    emergencyContacts.remove(postion);
                    Toasty.success(SettingsActivity.this,"删除成功",Toasty.LENGTH_SHORT).show();
                    listAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
    }
    private void initDatas() {
        DaoManager daoManager = DaoManager.getInstance();
        daoManager.init(SettingsActivity.this);
        emergencyContacts = daoManager.getEmergencyContactDao().queryBuilder().list();
    }

    private void copyDatas(List<EmergencyContact> origin,List<EmergencyContact> target){
        for(int i = 0 ; i<origin.size();i++){
            EmergencyContact emergencyContact = new EmergencyContact();
            emergencyContact.setId(origin.get(i).getId());
            emergencyContact.setName(origin.get(i).getName());
            emergencyContact.setTelNumber(origin.get(i).getTelNumber());
            emergencyContact.setMsgContent(origin.get(i).getMsgContent());
            target.add(i,emergencyContact);
        }
    }
    private void initViews() {
        customToolBar = findViewById(R.id.act_tb_setting);
        listView = findViewById(R.id.act_setting_show);
        btnUpdate = findViewById(R.id.item_btn_setting_update);
        btnAdd = findViewById(R.id.item_btn_setting_add);
        tip = findViewById(R.id.act_setting_tip);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }
}
