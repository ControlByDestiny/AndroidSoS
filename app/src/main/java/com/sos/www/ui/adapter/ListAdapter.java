package com.sos.www.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sos.www.R;
import com.sos.www.bean.EmergencyContact;
import com.sos.www.constants.Constants;

import java.util.List;


public class ListAdapter extends BaseAdapter {
    private DataChangedListener mDataChangedListener;
    private static final String TAG = "ListAdapter";
    private List<EmergencyContact> mData;
    private Context mContext;

    public ListAdapter(Context context, List<EmergencyContact> emergencyContactList) {
        mContext = context;
        mData = emergencyContactList;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view;

        EmergencyContact emergencyContact = mData.get(position);
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.setting_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.btnName = view.findViewById(R.id.act_btn_show_name);
            viewHolder.btnTel = view.findViewById(R.id.act_btn_show_tel);
            viewHolder.btnMsg = view.findViewById(R.id.act_btn_show_msg);

            viewHolder.nameDetail = view.findViewById(R.id.name_detail);
            viewHolder.telDetail = view.findViewById(R.id.tel_detail);
            viewHolder.msgDetail = view.findViewById(R.id.msg_detail);

            viewHolder.etName = view.findViewById(R.id.act_et_setting_name);
            viewHolder.etTel = view.findViewById(R.id.act_et_setting_tel);
            viewHolder.etMsg = view.findViewById(R.id.act_et_setting_msg);
            initListeners(viewHolder, position);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.etName.setText(emergencyContact.getName());
        viewHolder.etTel.setText(emergencyContact.getTelNumber());
        viewHolder.etMsg.setText(emergencyContact.getMsgContent());
        return view;
    }

    private void initListeners(final ViewHolder viewHolder, final int position) {

        viewHolder.btnName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewHolder.nameDetail.setVisibility(isChecked ? LinearLayout.VISIBLE : LinearLayout.GONE);
            }
        });

        viewHolder.btnTel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewHolder.telDetail.setVisibility(isChecked ? LinearLayout.VISIBLE : LinearLayout.GONE);
            }
        });

        viewHolder.btnMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewHolder.msgDetail.setVisibility(isChecked ? LinearLayout.VISIBLE : LinearLayout.GONE);
            }
        });
        viewHolder.etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            private String getFocus = mData.get(position).getName();
            private String loseFocus = mData.get(position).getName();

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i(TAG, "  hasFocus:" + hasFocus);
                Log.i(TAG, "  text:" + viewHolder.etName.getText().toString());
                if (!hasFocus) {
                    loseFocus = viewHolder.etName.getText().toString();
                } else {
                    getFocus = viewHolder.etName.getText().toString();
                }
                if (!getFocus.equals(loseFocus)) {
                    if (mDataChangedListener != null) {
                        mData.get(position).setName(viewHolder.etName.getText().toString());
                        mDataChangedListener.onDataChanged(position);
                    }
                }
            }
        });
        viewHolder.etTel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            private String getFocus = mData.get(position).getTelNumber();
            private String loseFocus = mData.get(position).getTelNumber();

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i(TAG, "  hasFocus:" + hasFocus);
                Log.i(TAG, "  text:" + viewHolder.etTel.getText().toString());
                if (!hasFocus) {
                    loseFocus = viewHolder.etTel.getText().toString();
                } else {
                    getFocus = viewHolder.etTel.getText().toString();
                }
                if (!getFocus.equals(loseFocus)) {
                    if (mDataChangedListener != null) {
                        mData.get(position).setTelNumber(viewHolder.etTel.getText().toString());
                        mDataChangedListener.onDataChanged(position);
                    }
                }
            }
        });
        viewHolder.etMsg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            private String getFocus = mData.get(position).getMsgContent();
            private String loseFocus = mData.get(position).getMsgContent();

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i(TAG, "  hasFocus:" + hasFocus);
                Log.i(TAG, "  text:" + viewHolder.etMsg.getText().toString());
                if (!hasFocus) {
                    loseFocus = viewHolder.etMsg.getText().toString();
                } else {
                    getFocus = viewHolder.etMsg.getText().toString();
                }
                if (!getFocus.equals(loseFocus)) {
                    if (mDataChangedListener != null) {
                        mData.get(position).setMsgContent(viewHolder.etMsg.getText().toString());
                        mDataChangedListener.onDataChanged(position);
                    }
                }
            }
        });
    }

    public void setOnDataChangedListener(DataChangedListener dataChangedListener) {
        this.mDataChangedListener = dataChangedListener;
    }

    private class ViewHolder {
        CheckBox btnName, btnTel, btnMsg;
        LinearLayout nameDetail, telDetail, msgDetail;
        EditText etName, etTel, etMsg;
    }

    public interface DataChangedListener {
        void onDataChanged(int position);
    }
}
