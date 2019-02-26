package com.sos.www.util;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;


public class LocationUtil {
    private static final String TAG = "LocationUtil";
    //声明mLocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;
    private Context mContext;

    public LocationUtil(Context context) {
        mContext = context;
    }

    public void getLoactionData(final AMapLocationListener aMapLocationListener) {
        mlocationClient = new AMapLocationClient(mContext);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(aMapLocationListener);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为60000ms ==>60s
        mLocationOption.setInterval(60000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
    }

    public void startService() {
        if (mlocationClient != null && !mlocationClient.isStarted()) {
            mlocationClient.startLocation();
        }
    }

    public void stopService() {
        if (mlocationClient != null && mlocationClient.isStarted()) {
            Log.i(TAG,"----------------stopService-------");
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
    }
}
