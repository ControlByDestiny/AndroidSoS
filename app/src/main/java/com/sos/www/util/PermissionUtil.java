package com.sos.www.util;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.sos.www.R;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class PermissionUtil {
    private static final String TAG = "PermissionUtil";
    private Context mContext;
    private final RxPermissions rxPermissions;
    private final String[] permissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.SEND_SMS,
            Manifest.permission.VIBRATE
    };
    private String[] needRequestPermissions ;

    public PermissionUtil(@NonNull FragmentActivity activity) {
        mContext = activity;
        rxPermissions = new RxPermissions(activity);
    }

    public PermissionUtil(@NonNull Fragment fragment) {
        mContext = fragment.getActivity();
        rxPermissions = new RxPermissions(fragment);
    }

    public void requestPermissions() {
        Log.i(TAG, "requestPermissions");
        permissionsFilter();
        if (needRequestPermissions.length == 0){
            return ;
        }
        rxPermissions.requestEach(needRequestPermissions).subscribe(new Consumer<Permission>() {
            private boolean hasShow = false;
            private boolean flag = false;
            @Override
            public void accept(final Permission permission) throws Exception {
                if (permission.granted) {
                    //权限申请通过
                    Log.i(TAG, permission.name + "-> Permissions Granted");
                } else if (permission.shouldShowRequestPermissionRationale) {
                    //某个权限被拒绝了，没有勾选不在询问
                    Log.i(TAG, permission.name + "->DENY ask again->shouldShowRequestPermissionRationale");
                    if (!hasShow) {
                        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                                .setTitle("提示")
                                .setMessage("该权限对系统正常运行十分重要，请确保授予权限")
                                .setIcon(mContext.getDrawable(R.mipmap.ic_launcher_round))
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        rxPermissions.request(permission.name).subscribe();
                                    }
                                }).create();
                        alertDialog.show();
                        hasShow = true;
                        flag = true;
                    }
                } else {
                    // 某个权限被拒绝了，勾选了不在询问
                    Log.i(TAG, permission.name + "->DENY Don't ask again->");
                    if (!flag){
                        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                                .setTitle("提示")
                                .setMessage("缺少系统运行所必需的权限，请前往设置中授予该权限")
                                .setIcon(mContext.getDrawable(R.mipmap.ic_launcher_round))
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                                        intent.setData(uri);
                                        mContext.startActivity(intent);
                                    }
                                })
                                .setNegativeButton("取消", null).create();

                        alertDialog.show();
                        flag = true;
                    }

                }
            }
        }).isDisposed();
    }

    private void permissionsFilter(){
        List<String> requestPermissions = new ArrayList<>();
        for(String permission : permissions){
            if ( !rxPermissions.isGranted(permission)){
                requestPermissions.add(permission);
            }
        }
        needRequestPermissions = new String[requestPermissions.size()];
        for (int i = 0; i < requestPermissions.size() ; i++){
            needRequestPermissions[i] = requestPermissions.get(i);
        }
    }
}
