package com.sos.www.util;

import android.content.Context;

import com.sos.www.bean.dao.DaoMaster;
import com.sos.www.bean.dao.DaoSession;
import com.sos.www.bean.dao.EmergencyContactDao;

public class DaoManager {
    private static final String DB_NAME = "system.db";
    private static DaoManager mInstance = null;
    private DaoMaster.DevOpenHelper mHelper;
    private Context mContext;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private DaoManager(){}

    public static DaoManager getInstance(){
        if (mInstance == null){
            synchronized (DaoManager.class){
                if (mInstance == null){
                    mInstance = new DaoManager();
                }
            }
        }
        return mInstance;
    }

    public void init (Context context){
        mContext = context;
        if (mHelper == null){
            mHelper = new DaoMaster.DevOpenHelper(mContext,DB_NAME,null);

        }
        if (daoMaster == null){
            daoMaster = new DaoMaster(mHelper.getWritableDatabase());
        }
        if (daoSession == null){
            daoSession = daoMaster.newSession();
        }
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }

    public EmergencyContactDao getEmergencyContactDao(){
        return daoSession.getEmergencyContactDao();
    }
}
