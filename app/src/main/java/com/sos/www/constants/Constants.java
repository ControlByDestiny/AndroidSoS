package com.sos.www.constants;

import com.sos.www.util.Base64Util;

public class Constants {
    public static final String MAIN_ACTION = "android.intent.action.MAIN";
    public static final String NEW_ACTION = "com.sos.www.newContact";
    public static final String SETTING_ACTION = "com.sos.www.setting";
    public static final int RESULT_CODE = 512;
    public static final int REQUEST_CODE=1024;
    public static final String FILE_NAME = Base64Util.encode("START_UP".getBytes());
}
